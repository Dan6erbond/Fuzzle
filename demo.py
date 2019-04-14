import json
import searcher
import searcher_new
import sys
from datetime import datetime

non_bmp_map = dict.fromkeys(range(0x10000, sys.maxunicode + 1), 0xfffd)

games = list()
with open("data/games/games.json", encoding="utf8") as f:
    games = json.loads(f.read())
print("{} games".format(len(games)))

movies = list()
with open("data/movies/movies.json", encoding="utf8") as f:
    movies = json.loads(f.read())
print("{} movies".format(len(movies)))

companies = list()
with open("data/economy/companies.json", encoding="utf8") as f:
    companies = json.loads(f.read())
print("{} companies".format(len(companies)))

countries = list()
with open("data/places/countries.json", encoding="utf8") as f:
    countries = json.loads(f.read())
print("{} countries".format(len(countries)))

while True:    
    try:
        category = input("What would you like to search through? [games, movies, companies, countries] ")
        data = list()
        if category.startswith("game"):
            data = games
        elif category.startswith("movie"):
            data = movies
        elif category.startswith("compan"):
            data = companies
        else:
            data = countries
            
        search = input("Enter search query: ")
        if search != "":
            time_started = datetime.now()
            data_old = [d["key"] if isinstance(d, dict) else d for d in data]
            results = searcher.find(data_old, search)
            max_results = min(len(results), 20)
            time_ended = datetime.now()
##            for result in results[:max_results]:
##                print(result["key"])
            print("Searcher: {} results | {}\n".format(len(results), time_ended - time_started))

            time_started = datetime.now()
            results = searcher_new.find(data, search, return_all=True, coverage_multiplier=0.05)
            max_results = min(len(results), 50)
            time_ended = datetime.now()
            for result in results[:max_results]:
                print(result["key"].translate(non_bmp_map))
                # if "tags" in result: print(result["tags"])
            print("New Searcher: {} results | {}".format(len(results), time_ended - time_started))
    except KeyboardInterrupt:
        exit(0)
