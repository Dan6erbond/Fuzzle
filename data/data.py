import json
import json

values = set()
data = list()
    
with open("reddits.json") as f:
    l = json.loads(f.read())
    for d in l["data"]["children"]:
        values.add(d["data"]["display_name"])
        
##with open("country-by-alphabet-letters.json") as f:
##    l = json.loads(f.read())
##    for a in l:
##        for c in a:
##            for b in a[c]:
##                for d in a[c][b]:
##                    values.add(d["country"].strip())

with open("names.json") as f:
    for v in json.loads(f.read()):
        values.add(v.strip())

with open("languages/programming-languages.txt") as f:
    for v in json.loads(f.read()):
        values.add(v.strip())

with open("places/cities.json", encoding="utf8") as f:
    l = json.loads(f.read())
    for c in l:
        values.add(c["name"].strip())

with open("economy/companies.json", encoding="utf8") as f:
    l = json.loads(f.read())
    for c in l["companies"]:
        values.add(c.strip())

with open("economy/Inc5000_2017_JSON.txt", encoding="utf8") as f:
    l = json.loads(f.read())
    for c in l:
        if "company" in c: values.add(c["company"].strip())
        if "industry" in c: values.add(c["industry"].strip())
        if "state_l" in c: values.add(c["state_l"].strip())

with open("economy/industries.json", encoding="utf8") as f:
    l = json.loads(f.read())
    for c in l["industries"]:
        values.add(c.strip())

with open("movies/movies.json", encoding="utf8") as f:
    l = json.loads(f.read())
    for m in l:
        data.append({
            "key": m["title"].strip(),
            "tags": m["cast"]
            })

with open("places/country-by-abbreviation.json", encoding="utf8") as f:
    l = json.loads(f.read())
    for c in l:
        data.append({
            "key": c["country"],
            "tags": [c["abbreviation"]]
            })

## data.extend(values)

with open("data.json", "w+", encoding="utf8") as f:
    f.write(json.dumps(data))
