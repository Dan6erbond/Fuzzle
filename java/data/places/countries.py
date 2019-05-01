import json

countries = list()
cs = set()
countries_old = set()
        
##with open("country-by-alphabet-letters.json") as f:
##    l = json.loads(f.read())
##    for a in l:
##        for c in a:
##            for b in a[c]:
##                for d in a[c][b]:
##                    countries_old.add(d["country"].strip())
##
##print(len(countries_old))

##with open("country-by-abbreviation.json", encoding="utf8") as f:
##    l = json.loads(f.read())
##    for co in l:
##        tags = [co["abbreviation"]]
##
##        with open("cities.json", encoding="utf8") as f:
##            l = json.loads(f.read())
##            for c in l:
##                if c["country"] in tags:
##                    tags.append(c["name"].strip())
##
##        country = {
##            "key": co["country"],
##            "tags": tags
##            }
##        
##        countries.append(country)
##        cs.add(co["country"])
##
##with open("countries.json", "w+", encoding="utf8") as f:
##    f.write(json.dumps(countries, indent=4))

with open("countries.json", encoding="utf8") as f:
    countries = json.loads(f.read())
    for country in countries:
        cs.add(country["key"])

print(sorted(list(cs)))
