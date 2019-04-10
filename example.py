### Author: Dan6erbond ###
###  Date: 10.04.2019  ###
###    Version: 1.1    ###
import searcher_new as searcher

### defining a list of options with tags ###
options = [
    {
        "key": "GitHub",
        "tags": ["Nat Friedman", "Tom Preston-Werner", "Chris Wanstrath", "Scott Chacon", "P. J. Hyett"]
        },
    {
        "key": "Google",
        "tags": ["Alphabet Inc.", "YouTube", "Sundar Pichai", "Larry Page", "Sergey Bin"]
        },
    {
        "key": "Reddit",
        "tags": ["Steve Huffman", "Alexis Ohanian", "Aaron Swartz"]
        }
    ]

search = "Steve" # the searched string

results = searcher.find(options, search)

if len(results) == 0:
    print("Couldn't find any results!")
elif results[0]["match"]:
    print("Found {}!".format(results[0]["key"]))
else:
    for result in results:
        print(result["key"])
