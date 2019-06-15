import fuzzle

options = ["reddit", "subreddit", "diretide", "automod", "hire mods", "disboard"]
search = "edit"

print("Search:", search)
print("Options:", ", ".join(options))

print("\nResults:")
results = fuzzle.find(options, search, return_all=True)
for i in range(len(results)):
    print("{}.".format(i+1), results[i]["key"], "| pseudo-string:", results[i]["construct"])
