import json
import searcher
import fuzzle
import sys
from datetime import datetime
from whoosh import scoring
from whoosh.qparser import QueryParser
from whoosh.index import create_in
from whoosh.fields import Schema, TEXT, KEYWORD

non_bmp_map = dict.fromkeys(range(0x10000, sys.maxunicode + 1), 0xfffd)

games = list()
with open("../data/games/games.json", encoding="utf8") as f:
    games = json.loads(f.read())
print("{} games".format(len(games)))

movies = list()
with open("../data/movies/movies.json", encoding="utf8") as f:
    movies = json.loads(f.read())
print("{} movies".format(len(movies)))

companies = list()
with open("../data/economy/companies.json", encoding="utf8") as f:
    companies = json.loads(f.read())
print("{} companies".format(len(companies)))

countries = list()
with open("../data/places/countries.json", encoding="utf8") as f:
    countries = json.loads(f.read())
print("{} countries\n".format(len(countries)))

def test_performance(query_str, data):
    schema = Schema(key=TEXT(stored=True), tags=KEYWORD)

    ix = create_in("whoosh", schema)
    writer = ix.writer()

    for d in data:
        writer.add_document(key=d["key"], tags=", ".join(d["tags"]))
    writer.commit()

    time_started = datetime.now()
    with ix.searcher(weighting=scoring.Frequency) as s:
        query = QueryParser("key", ix.schema).parse(query_str)
        results = s.search(query, limit=None)
        time_ended = datetime.now()
        tr = results[0]["key"] if len(results) > 0 else "No results"
        print("Whoosh: {} results | {} - Top Result: {}".format(len(results), time_ended - time_started, tr))

    time_started = datetime.now()
    data_old = [d["key"] if isinstance(d, dict) else d for d in data]
    results = searcher.find(data_old, query_str)
    time_ended = datetime.now()
    tr = results[0]["key"] if len(results) > 0 else "No results"
    print("Searcher: {} results | {} - Top Result: {}".format(len(results), time_ended - time_started, tr))

    time_started = datetime.now()
    results = fuzzle.find(data, query_str, coverage_multiplier=0.05)
    time_ended = datetime.now()
    tr = results[0]["key"] if len(results) > 0 else "No results"
    print("New Searcher: {} results | {} - Top Result: {}\n".format(len(results), time_ended - time_started, tr))

query = "Quantum of Solace"
print("Searching for {} in movies.".format(query))
test_performance(query, movies)

query = "Spider"
print("Searching for {} in movies.".format(query))
test_performance(query, movies)

query = "United States of America"
print("Searching for {} in countries.".format(query))
test_performance(query, countries)

query = "Suhr"
print("Searching for {} in countries.".format(query))
test_performance(query, countries)

query = "Food"
print("Searching for {} in companies.".format(query))
test_performance(query, companies)

query = "California"
print("Searching for {} in companies.".format(query))
test_performance(query, companies)

query = "Grand Theft Auto"
print("Searching for {} in games.".format(query))
test_performance(query, games)

query = "Linux"
print("Searching for {} in games.".format(query))
test_performance(query, games)
