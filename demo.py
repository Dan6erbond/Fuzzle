import json
import searcher
import searcher_new
from datetime import datetime

data = list()
with open("data/data.json", encoding="utf8") as f:
    data = json.loads(f.read())

while True:
    try:
        search = input("Enter search query: ")
        if search != "":
            time_started = datetime.now()
            data_old = [d["key"] if isinstance(d, dict) else d for d in data]
            results = searcher.find(data_old, search)
            max_results = min(len(results), 20)
            time_ended = datetime.now()
            for result in results[:max_results]:
                print(result["key"])
            print("Searcher: {} results | {}\n".format(len(results), time_ended - time_started))

            time_started = datetime.now()
            results = searcher_new.find(data, search, return_all=True, coverage_multiplier=0.04)
            max_results = min(len(results), 20)
            time_ended = datetime.now()
            for result in results[:max_results]:
                print(result["key"])
                if "tags" in result: print(result["tags"])
            print("New Searcher: {} results | {}".format(len(results), time_ended - time_started))
        else:
            pass
    except KeyboardInterrupt:
        exit(0)
