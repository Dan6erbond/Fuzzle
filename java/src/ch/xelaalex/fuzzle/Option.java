package ch.xelaalex.fuzzle;

/**
 * The equivalent to dictionaries in the python version. Instances of these Objects are used to Store values such as
 * the key and tags but also values the Searcher discovered.
 *
 * @author XelaaleX1234
 * @version 1.1
 */

public class Option {

    private String key;
    private String[] tags;
    private double coverage = 0;
    private int cat = 0;
    private double accuracy = 0;
    private boolean match = false;

    /**
     * The Constructor of this Class is used to initiate a new instance with a new value.
     *
     * @param key  the main value to be searched for.
     * @param tags additional values to be searched for.
     */
    public Option(String key, String[] tags) {
        this.key = key;
        this.tags = tags;
    }

    /**
     * The Key is the main value of this Class it's the main value that's searched for in
     * {@link ch.xelaalex.fuzzle.Searcher}.
     *
     * @return returns the Key assigned to the The Object.
     */
    public String getKey() {
        return key;
    }

    /**
     * The Key is the main value of this Class it's the main value that's searched for in
     * {@link ch.xelaalex.fuzzle.Searcher}.
     *
     * @param key assign a new key to the instance.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * The tags are additional values which are searched for in {@link ch.xelaalex.fuzzle.Searcher}.
     *
     * @return returns the tags that were assigned to the instance.
     */
    public String[] getTags() {
        return tags;
    }

    /**
     * The tags are additional values which are searched for in {@link ch.xelaalex.fuzzle.Searcher}.
     *
     * @param tags assignes new tags to the instance, previously set tags are overwritten.
     */
    public void setTags(String[] tags) {
        this.tags = tags;
    }

    /**
     * The Coverage value is assigned by the searcher it saves how much of the search this value covers.
     *
     * @return returns the coverage of this instance.
     */
    public double getCoverage() {
        return coverage;
    }

    /**
     * The Coverage is assigned by the searcher, normally there is no reason to manipulate this value.
     *
     * @param coverage assignes the new Coverage value to the instance.
     */
    public void setCoverage(double coverage) {
        this.coverage = coverage;
    }

    /**
     * You can get the key of the instance with this. You can use this for multiple things i.e. to Display the search
     * results.
     *
     * @return returns the key of the instance.
     */
    @Override
    public String toString() {
        return key;
    }

    /**
     * Match is set to true if the Key matches perfectly with the Search.
     *
     * @return returns if this instance is a perfect match.
     */
    public boolean getMatch() {
        return match;
    }

    /**
     * This value is usually set by the Searcher. If the key is identical with the Search String this value will be set
     * to true.
     *
     * @param match returns if this instance is a match.
     */
    public void setMatch(boolean match) {
        this.match = match;
    }

    /**
     * The Accuracy is a value that is Determined by the Searcher. The higher the value the more likely this is the value
     * that was searched for.
     *
     * @return returns the accuracy of this instance.
     */
    public double getAccuracy() {
        return accuracy;
    }

    /**
     * This Value is usually set by the Searcher. The category of an instance defines for which reason the instance
     * was included into the results.
     *
     * @param accuracy assigns a value to the accuracy of this instance.
     */
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    /**
     * This Value is usually set by the Searcher. The category of an instance defines for which reason the instance
     * was included into the results.
     *
     * @return returns the Category.
     */
    public int getCat() {
        return cat;
    }

    /**
     * This Value is usually set by the Searcher. The category of an instance defines for which reason the instance
     * was included into the results.
     *
     * @param cat the category.
     */
    public void setCat(int cat) {
        this.cat = cat;
    }
}
