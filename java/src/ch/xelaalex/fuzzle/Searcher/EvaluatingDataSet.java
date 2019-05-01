package ch.xelaalex.fuzzle.Searcher;

import java.util.concurrent.CompletableFuture;

/**
 * The Dataset class allows To store a high count of values while staying efficient by taking workload
 * from the cpu and instead completing tasks in a more Ram heavy manner. The Dataset keeps a small Array of Evaluable so it
 * can make an estimation of a value. Note that this Dataset is made too be as efficient as possible and kicks out
 * irrelevant Evaluable which makes it unsuited for most use cases and should never be used if all Evaluable matters.
 * This version is built for Searcher.
 *
 * @author XelaaleX1234
 */

public class EvaluatingDataSet {
    private Evaluable[] evaluableSet;
    private Evaluable[] storage;
    private Evaluable[] strippedData;
    private boolean changed = true;
    private int index = 0;
    private int addCounter = 0;
    private final int RESET_VALUE;

    /**
     * The <code>EvaluatingDataSet</code> is used to store values efficiently. After a first evaluation irrelevant Evaluable
     * is thrown out to improve Performance. The new Results Value is compared to some stored Values if it's value is
     * higher than one of the stored Results are saved. The evaluableSet has a max. size which is per Default
     * <code>Integer.MAX_SIZE - 8</code>, exceeding this value will result in seemingly random exceptions, because of
     * this EvaluatingDataSet provides no exception handling too increase performance.
     *
     * @param storageSize The Constructor Argument int storageSize sets the size of the Storage lower Values will
     *                    improve the performance on bigger Datasets while a higher Value might yield more Results.
     *                    usually it's best to set the value to about 1.5 times the number of results you are interested
     *                    in. other results will be found but important Results that are not included in the storage
     *                    size you specified might be thrown out early. Higher values than 20-30 are not recommended
     *                    and it's probably more efficient to choose the Cpu heavy or balanced Version.
     */
    public EvaluatingDataSet(int storageSize) {
        //considered maximal safe size for an Array
        evaluableSet = new Evaluable[Integer.MAX_VALUE - 8];
        storage = new Evaluable[storageSize];
        RESET_VALUE = (storage.length - (storage.length % 2)) / 2;
    }

    /**
     * Instead of generating a new empty Array, you can already set a Evaluable[] on initialization which is more efficient
     * as using {@link EvaluatingDataSet#setAll(Evaluable[]) setAll(Evaluable[])}.
     *
     * @param storageSize The Constructor Argument int storageSize sets the size of the Storage lower Values will
     *                    improve the performance on bigger Datasets while a higher Value might yield more Results.
     *                    usually it's best to set the value to about 1.5 times the number of results you are interested
     *                    in. other results will be found but important Results that are not included in the storage
     *                    size you specified might be thrown out early. Higher values than 20-30 are not recommended
     *                    and it's probably more efficient to choose the Cpu heavy or balanced Version.
     * @param evaluableSet     the Array to be used to store Evaluable, if it has existing values these will be kept.
     */
    public EvaluatingDataSet(int storageSize, Evaluable[] evaluableSet) {
        //considered maximal safe size for an Array
        this.evaluableSet = evaluableSet;
        storage = new Evaluable[storageSize];
        RESET_VALUE = (storage.length - (storage.length % 2)) / 2;
    }

    /**
     * @param storageSize storageSize sets the size of the Storage lower Values will
     *                    improve the performance on bigger Datasets while a higher Value might yield more Results.
     * @param maxSize     maxSize sets a limit of how much the Dataset can store usually this is {@code Integer.MAX_VALUE -8}
     *                    since this is the highest considered safe length for a regular Array. Setting a lower Value
     *                    might improve cpu performance marginally and ram usage a bit if you already know
     */
    public EvaluatingDataSet(int storageSize, int maxSize) {
        //considered maximal safe size for an Array
        evaluableSet = new Evaluable[maxSize];
        storage = new Evaluable[storageSize];
        RESET_VALUE = (storage.length - (storage.length % 2)) / 2;
    }

    /**
     * With add you can add Evaluable. This Method runs in it's own Thread to make this Method less heavy for the cpu to
     * handle.
     *
     * @param evaluable the <code>Evaluable</code> object is stored in the Dataset temporarily and if it's considered important
     *             it will be stored.
     */
    public void add(Evaluable evaluable) {
        changed = true;
        addCounter++;
        if (addCounter >= storage.length) {
            for (int i = RESET_VALUE; i < storage.length; i++) {
                storage[i - RESET_VALUE] = storage[i];
            }
        }
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 10; i++) {
                if (storage[i] == null || storage[i].getValue() <= evaluable.getValue()) {
                    storage[i] = evaluable;
                    break;
                }
            }
        });
    }

    /**
     * @param index the index of the Evaluable object.
     * @return returns the Evaluable object at a given index.
     */
    public Evaluable get(int index) {
        return evaluableSet[index];
    }

    /**
     * Returns the Datasets array stripped if you're looping through it afterwards you might consider using the
     * {@link EvaluatingDataSet#toArray(boolean) non-stripped version}.
     *
     * @return Calls {@code toArray(true)} for ease of use.
     */
    public Evaluable[] toArray() {
        return toArray(true);
    }

    /**
     * @param strip Decides whether the stripped or not stripped version of the Array is returned. The stripped version
     *              gets rid of the empty positions at the end. In some cases the non stripped version can be more
     *              efficient, eg. if you directly loop through the evaluableSet afterwards because you want too print it on
     *              the console you can actually safe a loop of writing the Evaluable too a new Array.
     * @return returns the Array that the Dataset uses to store all Evaluable
     */
    public Evaluable[] toArray(boolean strip) {
        if (strip) {
            if (changed) {
                for (int i = 0; i < evaluableSet.length; i++) {
                    if (evaluableSet[i] == null) {
                        strippedData = new Evaluable[i];
                    }
                }
                for (int i = 0; i < strippedData.length; i++) {
                    strippedData[i] = evaluableSet[i];
                }
            }
            return strippedData;
        } else {
            return evaluableSet;
        }
    }

    /**
     * Adds an Array to the EvaluatingDataSet
     *
     * @param evaluableSet The Evaluable that gets added on top of the existing Array it won't be evaluated in any kind of manner.
     *                If you just want to override the existing Evaluable in the set {@link EvaluatingDataSet#setAll setAll()}
     *                is way more efficient though.
     */
    public void addAll(Evaluable[] evaluableSet) {
        for (int i = 0; i < evaluableSet.length; i++) {
            if (evaluableSet[i] == null) break;
            this.evaluableSet[i + index] = evaluableSet[i];
        }
        index += evaluableSet.length;
        changed = true;
    }

    /**
     * Adds another Dataset to this Dataset
     *
     * @param evaluatingDataSet the Dataset to be added
     */
    public void addAll(EvaluatingDataSet evaluatingDataSet) {
        addAll(evaluatingDataSet.toArray(false));
    }

    /**
     * Replaces the Datasets array with the given one. Note that the maximal size of the Array will be changed to
     * whatever size the given array has.
     *
     * @param evaluableSet the array to be set
     */
    public void setAll(Evaluable[] evaluableSet) {
        this.evaluableSet = evaluableSet;
        changed = true;
    }
}
