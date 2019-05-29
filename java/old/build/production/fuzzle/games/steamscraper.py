import json
import os
import urllib.request
import time
import games as gamemanager

games = list()
appids = list()
newids = list()

with open("steamgames-detail.json", encoding="utf8") as f:
    games = json.loads(f.read())
    print(len(games))

with open("appids.txt", encoding="utf8") as f:
    appids = f.read().splitlines()
    print(len(appids))

def get_game_data(id):
    try:
        url = "https://store.steampowered.com/api/appdetails?appids={}".format(id)
        contents = urllib.request.urlopen(url).read()
        data = json.loads(contents)[id]
        if data["success"]:
            data = data["data"]
            if data["type"] == "game":
                return data
        else:
            return None
    except Exception as e:
        print(type(e))
        return e
    
def write_games():
    with open("steamgames-detail.json", "w+", encoding="utf8") as f:
        f.write(json.dumps(games, indent=4))
    gamemanager.write_games()

def write_files():
    string = "\n\n"

    path = "steamgames-detail.json"
    with open(path, encoding="utf8") as f:
        games = json.loads(f.read())
        size = os.path.getsize(path) * 1e-6
        string += "# {}MB - {}".format(round(size, 2), len(games))

    path = "appids.txt"
    with open(path, encoding="utf8") as f:
        appids = f.read().splitlines()
        size = os.path.getsize(path) * 1e-3
        string += "\n# {}KB - {}".format(round(size, 2), len(appids))

    with open("files.txt", "a+", encoding="utf8") as f:
        f.write(string)

    print(string)

url = "http://api.steampowered.com/ISteamApps/GetAppList/v0001/?format=json"
steamgames = json.loads(urllib.request.urlopen(url).read())
apps = steamgames["applist"]["apps"]["app"]
print(len(apps))

url = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?format=json"
steamgames = json.loads(urllib.request.urlopen(url).read())
apps.extend(steamgames["applist"]["apps"])
print(len(apps))

for app in apps:
    id = str(app["appid"])
    
    if id in appids:
        continue
    
    appids.append(id)
    newids.append(id)
    
    data = get_game_data(id)
    
    if isinstance(data, dict):
        print(data["name"])
        games.append(data)
    elif data is None: # unsuccessful request (not ratelimit)
        continue
    else:
        write_games()
        with open("appids.txt", "a+") as f:
            f.write("{}\n".format("\n".join(newids)))
            newids = list()
        write_files()
        time.sleep(4*60)

write_games()
with open("appids.txt", "a+") as f:
    f.write("{}\n".format("\n".join(newids)))
write_files()
time.sleep(4*60)
