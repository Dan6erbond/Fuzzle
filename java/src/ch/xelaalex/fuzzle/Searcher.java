package ch.xelaalex.fuzzle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;

/**
 * This Class contains logic to find Values that fit to a String, i.e. find Movies according to a Search String.
 *
 * @author XelaaleX1234
 * @version 1.1
 */
public class Searcher {
    /**
     * Method overload calls {@link ch.xelaalex.fuzzle.Searcher#find(Collection, String, boolean, double)} with default
     * values. {@code boolean returnAll: false, double coverageMultiplier: 0.02975} in the very most cases this overload
     * function is the best option. More info about the Default values can be found at the main function
     * {@link ch.xelaalex.fuzzle.Searcher#find(Collection, String, boolean, double)}.
     *
     * @param options The Options that should be scanned, use every Collection you like.
     * @param search  The String to be searched for.
     * @return returns a {@code Collection<Option>} containing the Results which the Search yielded. Internally all Data
     * is stored using a {@code HashSet<Option>}.
     */
    public static Collection<Option> find(Collection<Option> options, String search) {
        return find(options, search, false, 0.02975);
    }

    /**
     * Method overload calls {@link ch.xelaalex.fuzzle.Searcher#find(Collection, String, boolean, double)} with default
     * values. {@code boolean returnAll: false}
     *
     * @param options            The Options that should be scanned, use every Collection you like.
     * @param search             The String to be searched for.
     * @param coverageMultiplier A value which determines the importance of how much the key of an option covers the
     *                           search String. A lower value might yield more or better results if you have many keys
     *                           which can be written differently i.e. Spider-Man and spiderman, but if you need more
     *                           accurate matches and the coverage is an important factor a higher value might be
     *                           favorable. A value somewhere in the middle that should work in most cases would be
     *                           0.02975 you can also call {@link ch.xelaalex.fuzzle.Searcher#find(Collection, String)}
     *                           or {@link ch.xelaalex.fuzzle.Searcher#find(Collection, String, boolean)} which
     *                           use this value by default. You should not use negative values, 0 since they might lead
     *                           to unexpected behaviour, in most cases values higher than 2 won't achieve better results
     *                           although higher values are safe. If you're trying to achieve better results it
     *                           might be worth to play around with this value.
     * @return returns a {@code Collection<Option>} containing the Results which the Search yielded. Internally all Data
     * is stored using a {@code HashSet<Option>}.
     */
    public static Collection<Option> find(Collection<Option> options, String search, double coverageMultiplier) {
        return find(options, search, false, coverageMultiplier);
    }

    /**
     * Method overload calls {@link ch.xelaalex.fuzzle.Searcher#find(Collection, String, boolean, double)} with default
     * values. {@code double coverageMultiplier: 0.02975} More info about the Default values can be found at the main function
     * {@link ch.xelaalex.fuzzle.Searcher#find(Collection, String, boolean, double)}.
     *
     * @param options   The Options that should be scanned, use every Collection you like.
     * @param search    The String to be searched for.
     * @param returnAll determines whether if a perfect match for the String was found the program should
     *                  continue to search. If this value is false and a perfect match was found the Searcher
     *                  will stop to search and only return the matched value.
     * @return returns a {@code Collection<Option>} containing the Results which the Search yielded. Internally all Data
     * is stored using a {@code HashSet<Option>}.
     */
    public static Collection<Option> find(Collection<Option> options, String search, boolean returnAll) {
        return find(options, search, returnAll, 0.02975);
    }

    /**
     * The main Method which is called by the overloading functions. Usually the overloading function which calls this
     * one is the favorable options {@link ch.xelaalex.fuzzle.Searcher#find(Collection, String)} if you prefer to always
     * return all values you can also call {@link ch.xelaalex.fuzzle.Searcher#find(Collection, String, boolean)}.
     *
     * @param options            The Options that should be scanned, use every Collection you like.
     * @param search             The String to be searched for.
     * @param returnAll          determines whether if a perfect match for the String was found the program should
     *                           continue to search. If this value is false and a perfect match was found the Searcher
     *                           will stop to search and only return the matched value.
     * @param coverageMultiplier A value which determines the importance of how much the key of an option covers the
     *                           search String. A lower value might yield more or better results if you have many keys
     *                           which can be written differently i.e. Spider-Man and spiderman, but if you need more
     *                           accurate matches and the coverage is an important factor a higher value might be
     *                           favorable. A value somewhere in the middle that should work in most cases would be
     *                           0.02975 you can also call {@link ch.xelaalex.fuzzle.Searcher#find(Collection, String)}
     *                           or {@link ch.xelaalex.fuzzle.Searcher#find(Collection, String, boolean)} which
     *                           use this value by default. You should not use negative values, 0 since they might lead
     *                           to unexpected behaviour, in most cases values higher than 2 won't achieve better results
     *                           although higher values are safe. If you're trying to achieve better results it
     *                           might be worth to play around with this value.
     * @return returns a {@code Collection<Option>} containing the Results which the Search yielded. Internally all Data
     * is stored using a {@code HashSet<Option>}.
     */
    public static Collection<Option> find(Collection<Option> options, String search, boolean returnAll, double coverageMultiplier) {
        HashSet<String> parts = new HashSet<>(378);
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

        ArrayList<Option> results = new ArrayList<>(options.size());

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
            ArrayList<String> wordMatches = new ArrayList<>(200);
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
            results.sort(Comparator.comparingDouble(Option::getAccuracy));
            results.sort(Comparator.comparingInt(Option::getCat));
        }
        return results;
    }
}
