import json

movies = list()

with open("movies.json", encoding="utf8") as f:
    l = json.loads(f.read())
    for m in l:
        movies.append({
            "key": m["title"].strip(),
            "tags": m["cast"]
            })

with open("movies.json", "w+", encoding="utf8") as f:
    f.write(json.dumps(movies, indent=4))
