package ch.xelaalex.fuzzle.Searcher;

public class Option {
    private String key;
    private String[] tags;
    private int coverage = 0;

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

    public void setCoverage(int coverage) {
        this.coverage = coverage;
    }

    public Option(String key, String[] tags) {
        this.key = key;
        this.tags = tags;
    }

    public int getCoverage() {
        return coverage;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("key: " + key);
        for (int i = 0; i < tags.length; i++) {
            string.append("tag ").append(i).append(" : ").append(tags[i]);
        }
        string.append(coverage);
        return string.toString();
    }
}
