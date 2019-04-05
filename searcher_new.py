MAX = 0.3

def find(options, search):
    search = search.lower().strip()
    
    parts = list()
    for size in range(1,len(search)+1):
        for i in range(0,len(search)):
            if i not in parts:
                parts.append(search[i:i+size])

    parts.sort(key = lambda s: len(s), reverse=True)
    
    results = list()
    for option in options:
        key = option
        option = option.lower().strip()

        best = ""
        for part in parts:
            if part in option:
                best = part
                break
        coverage = len(best) / len(search)

        if coverage < 0.75:
            continue

        match = 1 if option == search else 0
        word_matches = set()
        starts_with = 2 if option.startswith(search) else 0
        starts_with_word = False
        starts_with_option = 1 if search.startswith(option) else 0
        option_in_search = 1 if option in search else 0
        search_in_option = 2 if search in option else 0
        
        for word in option.split(" "):
            for word1 in search.split(" "):
                if word1 == word or word.startswith(word1) or word.endswith(word1) or word1.startswith(word) or word1.endswith(word):
                    if option.startswith(word):
                        starts_with_word = True
                    word_matches.add(word)

        starts_with_word = 2 if starts_with_word else 0
        
        possible_accuracy = 1 + 2 + 1 + 1 + 2 + 2
        accuracy = (match + starts_with + starts_with_word + starts_with_option + option_in_search + search_in_option) / possible_accuracy
        
        cat = 5
        if match == 1:
            return [{"key": key, "match": True}]
        elif search_in_option == 2:
            cat = 1
        elif starts_with_word == 2:
            cat = 2
        elif starts_with == 2:
            cat = 3
        elif option_in_search == 1:
            cat = 4
        elif starts_with_option == 1:
            cat = 5

        results.append({
            "key": key,
            "coverage": coverage,
            "accuracy": accuracy,
            "cat": cat,
            "match": match == 1
            })

    results.sort(key = lambda i: i["accuracy"], reverse=True)
    results.sort(key = lambda i: i["cat"])

    return results
