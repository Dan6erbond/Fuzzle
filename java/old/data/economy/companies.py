import json

companies = list()
##values = set()

##with open("2014-fortune-500.json", encoding="utf8") as f:
##    l = json.loads(f.read())
##    for c in l["companies"]:
##        values.add(c.strip())

with open("Inc5000_2017_JSON.txt", encoding="utf8") as f:
    l = json.loads(f.read())
    for c in l:
        if "company" in c:
            tags = list()
            if "industry" in c:
                tags.append(c["industry"].strip())
            if "state_l" in c:
                tags.append(c["state_l"].strip())
            if "city" in c:
                tags.append(c["city"].strip())
            
            d = {
                "key": c["company"].strip(),
                "tags": tags
                }
            companies.append(d)

##with open("industries.json", encoding="utf8") as f:
##    l = json.loads(f.read())
##    for c in l["industries"]:
##        values.add(c.strip())

with open("companies.json", "w+", encoding="utf8") as f:
    f.write(json.dumps(companies, indent=4))
