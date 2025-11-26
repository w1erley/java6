package collections;

import equipment.Equipment;
import java.util.*;

/**
 * Реалізація Set<Equipment> на базі масиву.
 * - Початкова місткість 15
 * - Ріст місткості на 30% (мінімум +1)
 * - Порядок елементів не гарантується (це Set)
 */
public class EquipmentArraySet implements Set<Equipment> {
    private static final int INITIAL_CAPACITY = 15;

    private Equipment[] data;
    private int size;
    private int modCount; // для fail-fast ітератора

    public EquipmentArraySet() {
        this.data = new Equipment[INITIAL_CAPACITY];
    }

    public EquipmentArraySet(Equipment single) {
        this();
        if (single != null) add(single);
    }

    public EquipmentArraySet(Collection<? extends Equipment> coll) {
        this();
        if (coll != null) addAll(coll);
    }

    // --- helpers ---

    private void ensureCapacity(int minCapacity) {
        if (data.length >= minCapacity) return;
        int grow = Math.max(1, (int) Math.ceil(data.length * 0.30));
        int newCap = Math.max(data.length + grow, minCapacity);
        data = Arrays.copyOf(data, newCap);
    }

    private int indexOf(Object o) {
        if (o == null) return -1;
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i])) return i;
        }
        return -1;
    }

    // --- Set interface ---

    @Override public int size() { return size; }
    @Override public boolean isEmpty() { return size == 0; }

    @Override public boolean contains(Object o) { return indexOf(o) >= 0; }

    @Override public Iterator<Equipment> iterator() {
        final int expectedMod = modCount;
        return new Iterator<Equipment>() {
            int cursor = 0;
            int lastRet = -1;

            @Override public boolean hasNext() {
                if (expectedMod != modCount) throw new ConcurrentModificationException();
                return cursor < size;
            }

            @Override public Equipment next() {
                if (expectedMod != modCount) throw new ConcurrentModificationException();
                if (cursor >= size) throw new NoSuchElementException();
                lastRet = cursor;
                return data[cursor++];
            }

            @Override public void remove() {
                if (lastRet < 0) throw new IllegalStateException();
                if (expectedMod != modCount) throw new ConcurrentModificationException();
                EquipmentArraySet.this.remove(data[lastRet]);
                cursor = lastRet;
                lastRet = -1;
            }
        };
    }

    @Override public Object[] toArray() { return Arrays.copyOf(data, size); }

    @Override public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            // створити масив потрібного типу
            return (T[]) Arrays.copyOf(data, size, a.getClass());
        }
        System.arraycopy(data, 0, a, 0, size);
        if (a.length > size) a[size] = null;
        return a;
    }

    @Override public boolean add(Equipment e) {
        if (e == null) throw new NullPointerException("null elements not allowed");
        if (contains(e)) return false; // set унікальний
        ensureCapacity(size + 1);
        data[size++] = e;
        modCount++;
        return true;
    }

    @Override public boolean remove(Object o) {
        int idx = indexOf(o);
        if (idx < 0) return false;
        // зрушення "дірки"
        int move = size - idx - 1;
        if (move > 0) System.arraycopy(data, idx + 1, data, idx, move);
        data[--size] = null;
        modCount++;
        return true;
    }

    @Override public boolean containsAll(Collection<?> c) {
        if (c == null) return true;
        for (Object x : c) if (!contains(x)) return false;
        return true;
    }

    @Override public boolean addAll(Collection<? extends Equipment> c) {
        boolean changed = false;
        if (c == null) return false;
        for (Equipment e : c) if (add(e)) changed = true;
        return changed;
    }

    @Override public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        if (c == null) {
            if (size > 0) { clear(); return true; }
            return false;
        }
        for (int i = 0; i < size; ) {
            if (!c.contains(data[i])) {
                remove(data[i]);
                changed = true;
            } else {
                i++;
            }
        }
        return changed;
    }

    @Override public boolean removeAll(Collection<?> c) {
        if (c == null || c.isEmpty()) return false;
        boolean changed = false;
        for (Object x : c) if (remove(x)) changed = true;
        return changed;
    }

    @Override public void clear() {
        Arrays.fill(data, 0, size, null);
        size = 0;
        modCount++;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(", ");
            sb.append(data[i]);
        }
        return sb.append("]").toString();
    }
}