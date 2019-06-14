import re

option = "python"
search = "pthon"

max_mistakes = 2
longest = ""
for i in range(len(search)-1):
    s = search[:i] + ".{0," + str(max_mistakes) + "}" + search[i+1:]
    if re.search(s, option):
        longest = s
        break

print(longest)
