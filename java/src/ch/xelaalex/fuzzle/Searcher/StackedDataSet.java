package ch.xelaalex.fuzzle.Searcher;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * The <code>StackedDataSet</code> allows too safe big amounts of Data while staying efficient. Data gets added on
 * top of the stack and can't be removed afterwards
 *
 * @author XelaaleX1234
 */

//Todo update all java doc Comments implement unfinished methods and check if everything corresponds to the java docs

public class StackedDataSet<E> implements Set<E> {
    /**
     * The <code>stack</code> Array is used to save the actual Data.
     */
    private E[] stack;
    /**
     * The <code>cache</code> Array caches a
     * {@link StackedDataSet#toArray() stripped} version of the
     * {@link StackedDataSet#stack stack}.
     */
    private E[] cache;
    /**
     * knows if the {@link StackedDataSet#cache cache}. is up to date
     */
    private boolean changed = true;
    /*
     * knows the current index of the stack
     */
    private int index = 0;

    private int maxSize;

    private Class<E> genClass;

    private int cacheIndex = 0;

    /**
     * The stack is set to it's default value: <code>Integer.MAX_VALUE - 8</code>.
     */
    public StackedDataSet() {
        //considered maximal safe size for an Array
        this.maxSize = Integer.MAX_VALUE - 8;
    }

    /**
     * @param maxSize maxSize sets a limit of how much the Dataset can store usually this is {@code Integer.MAX_VALUE -8}
     *                since this is the highest considered safe length for a regular Array. Setting a lower Value
     *                might improve cpu performance marginally and ram usage a bit if you already know
     */
    public StackedDataSet(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Adds a the value on top of the stack
     *
     * @param o the <code>Generified Object</code> is stored in the {@link StackedDataSet#stack stack}.
     */
    public boolean add(Object o) throws ClassCastException, NullPointerException, IllegalArgumentException {
        if (stack == null) {
            @SuppressWarnings("unchecked") final E[] stack = (E[]) Array.newInstance(o.getClass(), maxSize);
            this.stack = stack;
        }
        stack[index] = (E) o;
        changed = true;
        index++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < index; i++) {
            if (stack[i].equals(o)) {
                for (int j = i; j < index; j++) {
                    stack[j] = stack[j + 1];
                    changed = true;
                    cacheIndex = i;
                }
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean addAll(Collection c) {
        //Todo
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        //Todo
        return false;
    }

    /**
     * @param index the index of the Evaluable object.
     * @return returns the Evaluable object at a given index.
     */
    public E get(int index) {
        return stack[index];
    }

    /**
     * Returns the stack array stripped if you're looping through it afterwards you might consider using the
     * {@link StackedDataSet#toArray(boolean) non-stripped version}.
     *
     * @return Calls {@code toArray(true)} for ease of use.
     */
    public E[] toArray() {
        return toArray(true);
    }


    @Override
    public void clear() {
        @SuppressWarnings("unchecked") final E[] stack = (E[]) Array.newInstance(genClass, maxSize);
        this.stack = stack;
    }

    @Override
    public boolean removeAll(Collection c) {
        //Todo
        return false;
    }


    @Override
    public boolean containsAll(Collection c) {
        final Object[] tempArray = c.toArray();
        for (int i = 0; i < tempArray.length; i++) {
            if (!this.contains(tempArray[i])) return false;
        }
        return true;
    }

    @Override
    public Object[] toArray(Object[] a) {
        //Todo
        return new Object[0];
    }

    /**
     * @param strip Decides whether the stripped or not stripped version of the Array is returned. The stripped version
     *              gets rid of the empty positions at the end. In some cases the non stripped version can be more
     *              efficient, eg. if you directly loop through the evaluableSet afterwards because you want too print it on
     *              the console you can actually safe a loop of writing the Evaluable too a new Array.
     * @return returns the Array that the Dataset uses to store all Evaluable
     */
    public E[] toArray(boolean strip) {
        if (strip) {
            if (changed) {
                @SuppressWarnings("unchecked") final E[] cache = (E[]) Array.newInstance(genClass, index + 1);
                for (int i = cacheIndex; i < cache.length; i++) {
                    cache[i] = stack[i];
                }
                this.cache = cache;
                cacheIndex = index;
            }
            return cache;
        }
        return stack;
    }

    /**
     * Adds an Array to the StackedDataSet
     *
     * @param eArray The Evaluable that gets added on top of the existing Array it won't be evaluated in any kind of manner.
     *               If you just want to override the existing Evaluable in the set {@link StackedDataSet#setAll setAll()}
     *               is way more efficient though.
     */
    public void addAll(E[] eArray) {
        for (int i = 0; i < eArray.length; i++) {
            if (eArray[i] == null) break;
            this.stack[i + index] = eArray[i];
        }
        index += eArray.length;
        changed = true;
    }

    /**
     * Replaces the Dataset with the given one.
     *
     * @param set the set this stacked set will be overwritten with.
     */
    public void setAll(Set<E> set) {
        this.stack = set.toArray(stack);
        changed = true;
    }

    public int size() {
        return index;
    }

    @Override
    public boolean isEmpty() {
        return index == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < index; i++) {
            if (stack[i].equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        //Todo
        return null;
    }
}
