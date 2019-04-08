import json
import searcher
import searcher_new
from datetime import datetime

values = set()

tags = [
    "reddit the frontpage of the internet",
    {
        "key": "reddit",
        "tags": ["frontpage"]
    }

]
    
with open("reddits.json") as f:
    l = json.loads(f.read())
    for d in l["data"]["children"]:
        values.add(d["data"]["display_name"])
        
with open("country-by-alphabet-letters.json") as f:
    l = json.loads(f.read())
    for a in l:
        for c in a:
            for b in a[c]:
                for d in a[c][b]:
                    values.add(d["country"].strip())

with open("names.json") as f:
    for v in json.loads(f.read()):
        values.add(v.strip())

with open("languages.txt") as f:
    for v in json.loads(f.read()):
        values.add(v.strip())

with open("cities.json", encoding="utf8") as f:
    l = json.loads(f.read())
    for c in l:
        values.add(c["name"].strip())

with open("companies.json", encoding="utf8") as f:
    l = json.loads(f.read())
    for c in l["companies"]:
        values.add(c.strip())

with open("Inc5000_2017_JSON.txt", encoding="utf8") as f:
    l = json.loads(f.read())
    for c in l:
        if "company" in c: values.add(c["company"].strip())
        if "industry" in c: values.add(c["industry"].strip())
        if "state_l" in c: values.add(c["state_l"].strip())

with open("industries.json", encoding="utf8") as f:
    l = json.loads(f.read())
    for c in l["industries"]:
        values.add(c.strip())

with open("movies.json", encoding="utf8") as f:
    l = json.loads(f.read())
    for m in l:
        values.add(m["title"].strip())

tags.extend(values)

print(len(values))

while True:
    try:
        search = input("Enter search query: ")
        if search != "":
            '''
            time_started = datetime.now()
            results = searcher.find(tags, search)
            time_ended = datetime.now()
            for result in results:
                print(result["key"])
            print("Searcher: {} results | {}\n".format(len(results), time_ended - time_started))
            '''
            time_started = datetime.now()
            results = searcher_new.find(tags, search, 0.04)
            time_ended = datetime.now()
            for result in results:
                print(result["key"])
            print("New Searcher: {} results | {}".format(len(results), time_ended - time_started))
        else:
            pass
    except KeyboardInterrupt:
        exit(0)
