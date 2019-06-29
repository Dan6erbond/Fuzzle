package ch.xelaalex.fuzzle.Searcher;

public class Option {
    private String key;
    private String[] tags;
    private double coverage = 0;
    private int cat = 0;
    private double accuracy = 0;
    private boolean match = false;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setCoverage(double coverage) {
        this.coverage = coverage;
    }

    public Option(String key, String[] tags) {
        this.key = key;
        this.tags = tags;
    }

    public double getCoverage() {
        return coverage;
    }

    @Override
    public String toString() {
        return key;
    }

    public String toStringWithTags() {
        StringBuilder string = new StringBuilder(key + ": ");
        for (String tag : tags) {
            string.append(tag).append(", ");
        }
        return string.toString();
    }

    public String toInformalString() {
        return key + ": cat " + cat + " coverage: " + coverage + " accuracy: " + accuracy;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public int getCat() {
        return cat;
    }
}
