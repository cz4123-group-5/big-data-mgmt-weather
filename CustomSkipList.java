import java.util.*;

public class CustomSkipList<T> implements Iterable<T> {
    private final int MAX_LEVEL = 32;
    private final double P = 0.5;
    private final Random random = new Random();
    private Node<T> head;
    private int level;
    private int size; // Added size field
    private ArrayList<Long> sortedTimestamps; // Added sorted timestamps list


    public CustomSkipList() {
        this.head = new Node<>(null, MAX_LEVEL);
        this.level = 1;
        this.size = 0;
        this.sortedTimestamps = new ArrayList<>();
    }

    private int randomLevel() {
        int level = 1;
        while (random.nextDouble() < P && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    public void add(int index, T value) { // primitive, don't use
        Node<T>[] update = new Node[MAX_LEVEL];
        Node<T> current = head;

        int currentIndex = -1;
        for (int i = level - 1; i >= 0; i--) {
            while (current.forward[i] != null && currentIndex + current.length[i] < index) {
                currentIndex += current.length[i];
                current = current.forward[i];
            }
            update[i] = current;
        }

        int newLevel = randomLevel();
        if (newLevel > level) {
            for (int i = level; i < newLevel; i++) {
                update[i] = head;
            }
            level = newLevel;
        }

        Node<T> newNode = new Node<>(value, newLevel);
        for (int i = 0; i < newLevel; i++) {
            newNode.forward[i] = update[i].forward[i];
            update[i].forward[i] = newNode;

            if (i == 0) {
                newNode.length[i] = update[i].length[i] - (index - currentIndex);
                update[i].length[i] = index - currentIndex;
            } else {
                newNode.length[i] = update[i].length[i];
                update[i].length[i] = 1;
            }
        }

        for (int i = newLevel; i < level; i++) {
            update[i].length[i]++;
        }

        size++; // Increment size when an element is inserted
    }

//    public void add(MyData data, MyData[] dataArray) {
//        int index = Collections.binarySearch(sortedTimestamps, data.timestamp);
//        if (index < 0) {
//            index = -index - 1;
//        }
//        sortedTimestamps.add(index, data.timestamp);
//        insert(index, data);
//    }

    public T get(int index) {
        Node<T> current = head;
        int currentIndex = -1;
        for (int i = level - 1; i >= 0; i--) {
            while (current.forward[i] != null && currentIndex + current.length[i] < index) {
                currentIndex += current.length[i];
                current = current.forward[i];
            }
        }

        if (current.forward[0] != null && currentIndex + current.length[0] == index) {
            return current.forward[0].value;
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new SkipListIterator();
    }

    private class SkipListIterator implements Iterator<T> {
        private Node<T> current;

        public SkipListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current.forward[0] != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = current.forward[0];
            return current.value;
        }
    }
}
