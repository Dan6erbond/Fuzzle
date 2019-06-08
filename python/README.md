# Fuzzle.py
Python version of the Fuzzle search algorithm.

![Python Version](https://img.shields.io/badge/python-3.5+-blue.svg)
![Version](https://img.shields.io/badge/version-0.7-orange.svg)

## Using
The search engine can be used by forking a version of the repository and dragging [Fuzzle](fuzzle.py) file into your project folder. Through an import you will have access to it's only (current!) member, a function, `find()` which takes three arguments:
 - **options:** Here you feed the function a list of either dictionaries or strings. If you are feeding it dictionaries, they will have to have the attributes `key` and `tags`. Tags can be present, but it isn't required. `tags` is a list of strings which is the fallback if the searcher does not find the searched value in the `key`.
 - **search:** Represents the term you're looking for which the searcher will use and split up into single parts to find how coverage and accuracy results to return in order from best to worst.
 - **return_all (optional):** Boolean whether only a full-match should be returned or all similar results. Does not consider a tag-match as a full-match (yet) and is defaulted to `True`.
 - **coverage_multiplier (optional):** This value is defaulted to 0.02975, increasing it will result in more results and lowering it will require the search to be found 100% in the options you want returned.
 
The `find()` function returns a list of results in the form of dictionaries. If you fed it a list of strings into it you will find those strings in the `key` field of each result and if you feed dictionaries the result will contain the original fields as well as the new fields `accuracy`, `category` and `match`.

`match` is a boolean which, when `True`, will be the only result the searcher returns and the `accuracy` goes from 0 to 1 with 1 being the highest accuracy and 0 the lowest. `category` is a field that the searcher uses to sort the results with each category meaning the following. Always the lowest category available for the result will be stored in the field `category` which stand all have their own meanings:

**Category 0:** Full-match.

**Category 1:** The *full* search was found in the result.

**Category 2:** The result starts with the search.

**Category 3:** The search is in the tags.

**Category 4:** The result starts with a word in the search.

**Category 5:** (Parts of) the key were found in the search.

**Category 6:** The key starts with the search.

**Category 7:** A word in the search was found in a tag.

## Example
The [Demo File](demo.py) can be downloaded along with the [data](data) folder which contains a few datasets that can be used to test the searcher. Simply running the [Demo File](demo.py) allows you to pick a category and search through the data.

A simple example which can be copy-pasted to understand how the engine is used:

```python
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
```

## Performance
Comparing the performance to a fairly commonly used search engine in Python, [Whoosh](https://whoosh.readthedocs.io/en/latest/), as well as the [old search engine](searcher.py) yields that Fuzzle has a lot of headroom for returning results faster but seems to be significantly easier to setup given you use dictionaries for your dataset. Whoosh is faster but does not find results for every search because it seems to be limited to using a single field at once to search through. In addition Fuzzle leaves a lot of options open and has basic tolerance for mistypes:

```
27799 games
28795 movies
5002 companies
240 countries

Searching for Quantum of Solace in movies.
Whoosh: 1 results | 0:00:00.051874 - Top Result: Quantum of Solace
Searcher: 3103 results | 0:00:00.269897 - Top Result: Quantum of Solace
New Searcher: 1 results | 0:00:00.810883 - Top Result: Quantum of Solace

Searching for Spider in movies.
Whoosh: 18 results | 0:00:00.010347 - Top Result: Spider-Man: Into the Spider-Verse
Searcher: 20 results | 0:00:00.110669 - Top Result: The Spider
New Searcher: 21 results | 0:00:00.237390 - Top Result: Spider-Man: Into the Spider-Verse

Searching for United States of America in countries.
Whoosh: 0 results | 0:00:00.027957 - Top Result: No results
Searcher: 7 results | 0:00:00.002023 - Top Result: United States Minor Outlying Islands
New Searcher: 240 results | 0:00:00.152008 - Top Result: United States

Searching for Suhr in countries.
Whoosh: 0 results | 0:00:00.027925 - Top Result: No results
Searcher: 0 results | 0:00:00.000946 - Top Result: No results
New Searcher: 1 results | 0:00:00.035931 - Top Result: Switzerland

Searching for Food in companies.
Whoosh: 3 results | 0:00:00.004017 - Top Result: Global Food Solutions
Searcher: 17 results | 0:00:00.015984 - Top Result: Global Food Solutions
New Searcher: 139 results | 0:00:00.031944 - Top Result: Foodmate US

Searching for California in companies.
Whoosh: 1 results | 0:00:00.003030 - Top Result: California Energy Solutions
Searcher: 2 results | 0:00:00.014961 - Top Result: California Energy Solutions
New Searcher: 671 results | 0:00:00.056847 - Top Result: California Energy Solutions

Searching for Grand Theft Auto in games.
Whoosh: 8 results | 0:00:00.014989 - Top Result: Grand Theft Auto V
Searcher: 4289 results | 0:00:00.305347 - Top Result: Grand Theft Auto
New Searcher: 1 results | 0:00:00.453955 - Top Result: Grand Theft Auto

Searching for Linux in games.
Whoosh: 1 results | 0:00:00.014960 - Top Result: Arma: Cold War Assault Mac/Linux
Searcher: 32 results | 0:00:00.456336 - Top Result: Arma: Cold War Assault Mac/Linux
New Searcher: 5497 results | 0:00:00.258848 - Top Result: Arma: Cold War Assault Mac/Linux
```

## Roadmap
 - [ ] Discord bot framework.
 - [x] Mistype correction.
