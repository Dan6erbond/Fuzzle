import json

with open("steamgames-detail.json", encoding="utf8") as f:
    games = json.loads(f.read())
    print(len(games))
    '''
    new_games = list()
    for game in games:
        if "key" in game and "tags" in game:
            continue
        new_games.append(game)

    with open("steamgames-detail.json", "w+", encoding="utf8") as nf:
        nf.write(json.dumps(new_games, indent=4))
        '''
