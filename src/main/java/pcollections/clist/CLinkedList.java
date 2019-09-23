package pcollections.clist;

import pcollections.exception.DoubleInitializationException;
import pcollections.exception.EmptyObjectException;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.ListIterator;

import static pcollections.enums.Constants.ONE;
import static pcollections.enums.Constants.ZERO;


public final class CLinkedList<I> extends AbstractSequentialList<I> implements CList<I> {
    private static final CLinkedList<Object> SINGLE_INSTANCE = new CLinkedList<>();

    private final I firstItem;

    private final CLinkedList<I> restItems;

    private final int length;

    private CLinkedList() {
        if (SINGLE_INSTANCE != null) {
            throw new DoubleInitializationException();
        }
        firstItem = null;
        restItems = null;
        length = ZERO;
    }

    private CLinkedList(final I firstItem, final CLinkedList<I> restItems) {
        this.firstItem = firstItem;
        this.restItems = restItems;

        length = restItems.length + ONE;
    }

    public I getFirstItem() {
        return firstItem;
    }

    public CLinkedList<I> getRestItems() {
        return restItems;
    }

    public static <E> CLinkedList<E> empty() {
        return (CLinkedList<E>) SINGLE_INSTANCE;
    }

    @Override
    public ListIterator<I> listIterator(int index) {
        return null;
    }

    @Override
    public int size() {
        return ZERO;
    }

    @Override
    public CLinkedList<I> prepend(I i) {
        return new CLinkedList<>(i, this);
    }

    @Override
    public CLinkedList<I> prependAll(Collection<? extends I> list) {
        CLinkedList<I> result = this;
        for (I i : list) {
            result = result.prepend(i);
        }
        return result;
    }

    @Override
    public CLinkedList<I> prependTo(int index, I e) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return prepend(e);
        }
        return new CLinkedList<>(firstItem, restItems.prependTo(index - 1, e));
    }

    @Override
    public CLinkedList<I> prependAll(int i, Collection<? extends I> list) {
        if (restItems == null) {
            throw new EmptyObjectException();
        }
        if (i < 0 || i > length) {
            throw new IndexOutOfBoundsException();
        }
        if (i == 0) {
            return prependAll(list);
        }
        return new CLinkedList<>(firstItem, restItems.prependAll(i - 1, list));
    }

    @Override
    public CLinkedList<I> delete(I i) {
        if (firstItem == null || restItems == null) {
            throw new EmptyObjectException();
        }
        if (firstItem.equals(i)) {
            return restItems;
        }

        CLinkedList<I> newRest = restItems.delete(i);
        if (newRest == restItems) {
            return this;
        }
        return new CLinkedList<>(firstItem, newRest);
    }

    @Override
    public CLinkedList<I> deleteAll(Collection<? extends I> list) {
        if (restItems == null) {
            throw new EmptyObjectException();
        }
        if (length == 0) {
            return this;
        }
        if (list.contains(firstItem)) {
            return restItems.deleteAll(list);
        }

        CLinkedList<I> newRest = restItems.deleteAll(list);
        if (newRest == restItems) {
            return this;
        }
        return new CLinkedList<I>(firstItem, newRest);
    }

    @Override
    public CList<I> delete(int i) {
        return null;
    }

    @Override
    public CList<I> subCList(int start, int end) {
        return null;
    }

    @Override
    public CList<I> subCList(int start) {
        return null;
    }
}

