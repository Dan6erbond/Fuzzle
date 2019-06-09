# Fuzzle
Scaleable search engine with multiple versions and basic mistype correction.

[![Discord](https://img.shields.io/badge/See_it_in_action-purple.svg?logo=discord&logoColor=white&labelColor=697ec4&color=7289da)](https://discord.gg/njYdKYH)

## Key Features
 - Searching through strings.
 - Ignoring low coverage results.
 - Sorting results by accuracy.
 - Support for dictionaries.

## Algorithm
A concise documentation of the algorithm can be found [here](https://dan6erbond.github.io/fuzzle.html).

## Versions

Currently Fuzzle exists in three different languages:
 - [Python](python)
 - [JavaScript](javascript)
 - [Java](java)

# Data

To test the search engine, data was gathered from multiple sources to ensure features like tags, mistype correction and coverage work as expected. All the data was restructured into JSON lists/dictionaries for cross-platform compatability and is being expanded upon as new data is added to the sources.

### Movies
A list of 28 795 movies from 1990 to the present day with the source being a JSON file found [here](https://raw.githubusercontent.com/prust/wikipedia-movie-data/master/movies.json) which was restructured to turn the movies' names into keys and their cast into the tags. This allows you to search for a movie not only by name, but also by actor.

### Games

The set of games was scraped from the Steam API. It currently contains around 27 thousand games which were then loaded into the [games.json](/data/games/games.json) file containing the game's name as the `key` and the following as `tags`:

 - **Categories:** The SteamAPI has (so far) returned 27 unique categories including `captions available`, `multi-player`, `online multi-player`, `includes source SDK`, `includes level editor`, `in-app purchases`, `shared/split screen`, `full controller support`, `MMO`, `online co-op`, `cross-platform multiplayer`, `partial controller support`, `steam achievements`, `local co-op`, `steam leaderboards`, `stats`, `commentary available`, `steam turn notifications`, `steam workshop`, `steam cloud`, `single-player`, `steam trading cards`, `co-op`, `local multi-player`.
 - **Genres:** The data contained 30 unique genres so far including `action`, `utilities`, `gore`, `strategy`, `animation & modeling`, `photo editing`, `education`, `sports`,`simulation`, `web publishing`, `documentary`, `sexual content`, `software training`, `tutorial`, `indie`, `rpg`, `massively multiplayer`, `design & illustration`, `game development`, `video production`, `nudity`, `audio production`, `casual`, `free to play`, `racing`, `adventure`, `violent`, `early access` and `accounting`.
 - **Developer(s) and publisher(s).**
 - **Platform(s):** Currently steam stores these values as booleans and the three available options are `windows`, `linux` and `macos`.
   
Since this dataset is quite large and may prove useful in your own projects, the current state of the data as well as the scraper and it's dependancies were archived in the [ZIP-Folder](/data/games.zip) and can be downloaded for you to freely use!
   
### Companies
A list of 5002 companies with their respective industry, state and city as tags which allows searches such as "california" or "food" to yield brands that do not contain the searched keyword in their name but instead are based in a specific state, city or are active in a certain industry.

### Countries
A list of countries (presumably with duplicate values) with most of their major cities added as tags to allow finding a country by searching for a city.

## Links
 - **[SteamAPI](https://store.steampowered.com/api):** The source of the list of games.
 - **[Awesome JSON Datasets](https://github.com/jdorfman/awesome-json-datasets):** Source of the list of movies as well as countries (without the cities).
 - **[Cities of the World](https://github.com/lutangar/cities.json):** List of cities with their corresponding Country Code which was migrated to [countries.json](/data/places/countries.json).
 - **[Inc 5000](https://sethwaite.com/download-inc-5000-2017-data-set/):** A ranking of the 5000 quickest growing privately-held companies in America.
 
### Logo
The Logo for Fuzzle was created by @lydocia who has her own GitHub profile as well as a [website](https://www.lydocia.com).

## Roadmap
 - [x] Removing irrelevant results.
 - [x] Supporting tags.
 - [x] Mistype correction.
 - [ ] Prioritizing fields.
 - [ ] Support for custom objects.
 - [x] Returning 100% matches at first position followed by rest.
 - [ ] Models for different search types.
