package ch.xelaalex.fuzzle.Searcher;

public class Searcher {
    public StackedDataSet<Option> find(StackedDataSet<Option> options, String search) {
        return find(options, search, false, 0.02975);
    }

    public StackedDataSet<Option> find(StackedDataSet<Option> options, String search, double coverageMultiplier) {
        return find(options, search, false, coverageMultiplier);
    }

    public StackedDataSet<Option> find(StackedDataSet<Option> options, String search, boolean returnAll) {
        return find(options, search, returnAll, 0.02975);
    }

    public StackedDataSet<Option> find(StackedDataSet<Option> options, String search, boolean returnAll, double coverageMultiplier) {
        StackedDataSet<String> parts = new StackedDataSet<>(378);
        char[] arrSearch = search.toCharArray();
        coverageLoop:
        for (int i = 0; i < search.length(); i++) {
            for (int j = 0; j < search.length() + 1; j++) {
                if (i >= 27)
                    break coverageLoop;
                StringBuilder part = new StringBuilder();
                for (int k = i; k < j; k++) {
                    part.append(arrSearch[k]);
                }
                if (!part.toString().equals(""))
                    parts.add(part.toString());
            }
        }

        String[] words = search.split(" ");
        double maxCoverage = 1 - search.length() * coverageMultiplier;

        StackedDataSet<Option> results = new StackedDataSet<>(options.size());

        for (Option option : options) {
            String key = option.getKey().toLowerCase().trim();
            String[] tags = option.getTags();
            double best = 0;
            for (String part : parts) {
                if (key.contains(part) && part.length() >= best) best = part.length();
            }
            double coverage = best / search.length();

            double match = 0;
            if (key.equals(search)) match = 1;
            StackedDataSet<String> wordMatches = new StackedDataSet<>(200);
            boolean tagMatch = false;
            boolean tagOccurrence = false;
            double startsWith = 0;
            if (key.startsWith(search)) startsWith = 2;
            double startsWithWord = 0;
            double startsWithKey = 0;
            if (search.startsWith(key)) startsWithKey = 1;
            double keyInSearch = 0;
            if (search.contains(key)) keyInSearch = 1;
            double searchInKey = 0;
            if (key.contains(search)) searchInKey = 2;

            for (String word : words) {
                for (String word1 : key.split(" ")) {
                    if (word.equals(word1) || word1.startsWith(word) || word1.endsWith(word) || word.startsWith(word1) || word.endsWith(word1)) {
                        if (key.startsWith(word1)) {
                            startsWithWord = 2;
                        }
                        wordMatches.add(word1);
                    }
                }
                for (String tag : tags) {
                    tag = tag.toLowerCase();
                    if (search.equals(tag)) {
                        tagMatch = true;
                    }
                    if (tag.contains(word)) {
                        tagOccurrence = true;
                    }
                }
            }

            int possibleAccuracy = 1 + 2 + 1 + 1 + 2 + 2 + words.length;
            double accuracy = (match + startsWith + startsWithWord + startsWithKey + keyInSearch + searchInKey + wordMatches.size()) / possibleAccuracy;

            if (coverage < maxCoverage && !tagMatch && !tagOccurrence) {
                continue;
            }

            int cat;
            if (match == 1) {
                cat = 0;
            } else if (searchInKey == 2)
                cat = 1;
            else if (startsWith == 2)
                cat = 2;
            else if (tagMatch)
                cat = 3;
            else if (startsWithWord == 2)
                cat = 4;
            else if (keyInSearch == 1)
                cat = 5;
            else if (startsWithKey == 1)
                cat = 6;
            else if (tagOccurrence)
                cat = 7;
            else {
                continue;
            }

            option.setCoverage(coverage);
            option.setAccuracy(accuracy);
            option.setCat(cat);
            option.setMatch(match == 1);

            if (!returnAll && match == 1) {
                results.clear();
                results.add(option);
                return results;
            }
            results.add(option);
        }
        if (results.size() > 0) {
            //results.sort(Comparator.comparingInt(Option::getAccuracy));
            //results.sort(Comparator.comparingInt(Option::getCat));
        }
        return results;
    }
}
