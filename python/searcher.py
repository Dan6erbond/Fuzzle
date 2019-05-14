def find(options, search, prioritize_words=True):
    search = search.lower().strip()
    
    results = list()
    for option in options:
        key = option
        option = option.lower().strip()
        
        for word in option.split(" "):
            for word1 in search.split(" "):
                if word1 == word:
                    cat = 2 if option.startswith(word) and prioritize_words else 3 if prioritize_words else 4
                    results.append({
                        "key": key,
                        "cat": cat
                    })
                elif word1.startswith(word):
                    results.append({
                        "key": key,
                        "cat": 7
                    })
                elif word.startswith(word1):
                    results.append({
                        "key": key,
                        "cat": 7
                    })  
        if option == search:
            results.append({
                "key": key,
                "cat": 1
            })
        elif option.startswith(search):
            results.append({
                "key": key,
                "cat": 4 if prioritize_words else 3
            })
        elif search.startswith(option):
            results.append({
                "key": key,
                "cat": 5
            })
        elif option in search:
            results.append({
                "key": key,
                "cat": 8
            })
        elif search in option:
            results.append({
                "key": key,
                "cat": 6
            })

    parts = set()
    for size in range(1,len(search)+1):
        for i in range(0,len(search)):
            parts.add(search[i:i+size])

    for result in results:
        key = result["key"].lower().strip()
        best = ""
        for part in parts:
            if part in key and len(part) > len(best):
                best = part
        result["accuracy"] = len(best) / len(search) * (1 / result["cat"])

    results = sorted(results, key=lambda k: k["accuracy"], reverse=True)

    keys = set()
    new_results = list()
    for result in results:
        if result["key"] not in keys:
            keys.add(result["key"])
            new_results.append(result)
    
    return new_results
