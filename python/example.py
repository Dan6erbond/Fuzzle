### Author: Dan6erbond ###
###  Date: 08.06.2019  ###
###    Version: 1.1    ###
import fuzzle

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

search = "GitHib" # the searched string

results = fuzzle.find(options, search)

if len(results) == 0:
    print("Couldn't find any results!")
elif results[0]["match"]: # if it was a 100% identical match
    print("Found {}!".format(results[0]["key"]))
else:
    for result in results:
        print(result["key"])
