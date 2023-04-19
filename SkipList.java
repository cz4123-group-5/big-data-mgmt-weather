import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SkipList implements Iterable<Integer> {
    private static final int MAX_LEVEL = 32;
    private static final double P = 0.5;
    private final Random rand = new Random();
    private final Node head = new Node(MAX_LEVEL, null, null);
    private final Comparator<Integer> comparator;

    /**
     * Constructor for SkipList
     * @param comparator the comparator to use
     */
    public SkipList(Comparator<Integer> comparator) {
        this.comparator = comparator;
    }

    /**
     * Adds a new value to the skip list
     * @param value the value to add
     * @return true if the value was added, false if the value already exists
     */
    public boolean add(Integer value) {
        Node[] update = new Node[MAX_LEVEL];
        Node node = head;

        for (int i = MAX_LEVEL - 1; i >= 0; i--) {
            while (node.next[i] != null && comparator.compare(node.next[i].value, value) < 0) {
                node = node.next[i];
            }
            update[i] = node;
        }

        node = node.next[0];

        if (node != null && node.value.equals(value)) {
            return false;
        }

        int level = randomLevel();
        if (level > head.level) {
            for (int i = head.level; i < level; i++) {
                update[i] = head;
            }
            head.level = level;
        }

        node = new Node(level, value, null);
        for (int i = 0; i < level; i++) {
            node.next[i] = update[i].next[i];
            update[i].next[i] = node;
        }
        return true;
    }

    /**
     * Removes a value from the skip list
     * @return the level
     */
    private int randomLevel() {
        int level = 1;
        while (rand.nextDouble() < P && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new SkipListIterator();
    }

    private static class Node {
        Integer value;
        int level;
        Node[] next;

        public Node(int level, Integer value, Node next) {
            this.value = value;
            this.level = level;
            this.next = new Node[level];
            for (int i = 0; i < level; i++) {
                this.next[i] = next;
            }
        }
    }

    private class SkipListIterator implements Iterator<Integer> {
        private Node current;

        public SkipListIterator() {
            current = head;
        }

        /**
         *
         * @return true if the next element exists
         */
        @Override
        public boolean hasNext() {
            return current.next[0] != null;
        }

        @Override
        public Integer next() {
            current = current.next[0];
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported.");
        }
    }

    public static class TimestampComparator implements Comparator<Integer> {
        private final List<LocalDateTime> timestamps;

        /**
         * Constructor for TimestampComparator
         * @param timestamps the timestamps to compare
         */
        public TimestampComparator(List<LocalDateTime> timestamps) {
            this.timestamps = timestamps;
        }

        /**
         * Compares two timestamps
         * @param index1 the index of the first timestamp to be compared.
         * @param index2 the index of the second timestamp to be compared.
         * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
         */
        @Override
        public int compare(Integer index1, Integer index2) {
            return timestamps.get(index1).compareTo(timestamps.get(index2));
        }
    }
}