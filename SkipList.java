import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiConsumer;

public class SkipList<T> implements Iterable<T> {
    private final ConcurrentSkipListMap<Integer, T> skipListMap = new ConcurrentSkipListMap<>();

    @Override
    public Iterator<T> iterator() {
        return skipListMap.values().iterator();
    }

    public T get(int index) {
        return skipListMap.get(index);
    }

    public T set(int index, T value) {
        return skipListMap.put(index, value);
    }

    public void add(int index, T value) {
        skipListMap.tailMap(index).forEach((key, val) -> skipListMap.put(key + 1, val));
        skipListMap.put(index, value);
    }

    public T remove(int index) {
        T removedValue = skipListMap.remove(index);
        skipListMap.tailMap(index).forEach((key, val) -> skipListMap.put(key - 1, val));
        return removedValue;
    }

    public int size() {
        return skipListMap.size();
    }

    public boolean isEmpty() {
        return skipListMap.isEmpty();
    }
}