import json

def write_games():
    dicts = list()
    cats = set()
    genres = set()
    ids = set()

    with open("steamgames-detail.json", encoding="utf8") as f:
        games = json.loads(f.read())
        for game in games:
            tags = list()

            if "name" not in game or "steam_appid" not in game:
                continue

            if game["steam_appid"] in ids:
                continue

            ids.add(game["steam_appid"])
            
            if "categories" in game:
                for category in game["categories"]:
                    category = category["description"]
                    tags.append(category)
                    cats.add(category)
            if "platforms" in game:
                for platform in game["platforms"]:
                    if game["platforms"][platform]:
                        tags.append(platform)
            if "genres" in game:
                for genre in game["genres"]:
                    genre = genre["description"]
                    tags.append(genre)
                    genres.add(genre)
            if "developers" in game:
                tags.extend(game["developers"])
            if "publishers" in game:
                tags.extend(game["publishers"])

            game = {
                "key": game["name"],
                "appid": game["steam_appid"],
                "tags": tags
                }

            dicts.append(game)

    with open("games.json", "w+", encoding="utf8") as f:
        f.write(json.dumps(dicts, indent=4))

##    print(len(genres))
##    print(genres)
##    print(len(cats))
##    print(cats)
##    print("{} games".format(len(dicts)))

# write_games()
