def find(options, search, coverage_multiplier=0.02975):
    search = search.lower().strip()
    
    parts = list()
    for size in range(1,len(search)+1):
        for i in range(0,len(search)):
            if i not in parts:
                parts.append(search[i:i+size])

    words = search.split(" ")

    parts.sort(key = lambda s: len(s), reverse=True)

    # max_coverage = 1 - len(search.split(" ")) * coverage_multiplier # 0.4 for Substar
    max_coverage = 1 - len(search) * coverage_multiplier # 0.02975 for Substar
    
    results = list()
    for option in options:
        if isinstance(option, str):
            option = {"key": option}

        key = option["key"].lower().strip()
        tags = option["tags"] if "tags" in option else list()

        best = ""
        for part in parts:
            if part in key:
                best = part
                break
        coverage = len(best) / len(search)

        match = 1 if key == search else 0
        word_matches = set()
        tag_matches = set()
        starts_with = 2 if key.startswith(search) else 0
        starts_with_word = 0
        starts_with_key = 1 if search.startswith(key) else 0
        key_in_search = 1 if key in search else 0
        search_in_key = 2 if search in key else 0
        
        for word in words:
            for word1 in key.split(" "):
                if word == word1 or word1.startswith(word) or word1.endswith(word) or word.startswith(word1) or word.endswith(word1):
                    if key.startswith(word1):
                        starts_with_word = 2
                    word_matches.add(word1)

            for tag in tags:
                if word == tag:
                    tag_matches.add(tag)
        
        possible_accuracy = 1 + 2 + 1 + 1 + 2 + 2 + len(words)
        accuracy = (match + starts_with + starts_with_word + starts_with_key + key_in_search + search_in_key + len(word_matches)) / possible_accuracy

        if coverage < max_coverage and len(tag_matches) == 0:
            continue

        cat = 5
        if match == 1:
            option["match"] = True
            return [option]
        elif search_in_key == 2:
            cat = 1
        elif starts_with_word == 2:
            cat = 2
        elif starts_with == 2:
            cat = 3
        elif key_in_search == 1:
            cat = 4
        elif starts_with_key == 1:
            cat = 5
        elif len(tag_matches) != 0:
            cat = 6

        option["coverage"] = coverage
        option["accuracy"] = accuracy
        option["cat"] = cat
        option["match"] = match == 1
        results.append(option)

    results.sort(key = lambda i: i["accuracy"], reverse=True)
    results.sort(key = lambda i: i["cat"])

    return results
