class Node<T> {
    T value;
    Node<T>[] forward;
    int[] length;

    Node(T value, int level) {
        this.value = value;
        this.forward = new Node[level];
        this.length = new int[level];
    }
}