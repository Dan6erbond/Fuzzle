package ch.xelaalex.fuzzle.Searcher;

import java.util.HashSet;

public class Searcher {
    public void find(StackedDataSet<Option> options, String search) {
        find(options, search, false, 0.02975);
    }

    public void find(StackedDataSet<Option> options, String search, double coverageMultiplier) {
        find(options, search, false, coverageMultiplier);
    }

    public void find(StackedDataSet<Option> options, String search, boolean returnAll) {
        find(options, search, false, 0.02975);
    }

    public void find(StackedDataSet<Option> options, String search, boolean returnAll, double coverageMultiplier) {
        HashSet<String> parts = new HashSet<>();
        char[] arrSearch = search.toCharArray();
        for (int i = 0; i < search.length(); i++) {
            for (int j = 0; j < search.length() + 1; j++) {
                String part = "";
                for (int k = i; k < j; k++) {
                    part += arrSearch[k];
                }
                parts.add(part);
            }
        }

        String[] words = search.split(" ");
        double maxCoverage = 1 - search.length() * coverageMultiplier;

        StackedDataSet<Option> results = new StackedDataSet<>(Option.class, options.size());

        for (Option option : options) {
            String key = option.getKey().toLowerCase().trim();
            String[] tags = option.getTags();
            int best = 0;
            for (String part : parts) {
                if (key.contains(part) && part.length() >= best) best = part.length();
            }
            option.setCoverage(best / search.length());
            results.add(option);
        }
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).toString());
        }
    }
}
