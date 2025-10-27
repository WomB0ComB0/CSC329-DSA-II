import java.util.Arrays;

public class MinPriorityQueue {

    private int[] heap;
    private int size;
    private int capacity;

    public MinPriorityQueue(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
    }

    // Helper methods to get parent and child indices
    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    // Helper method to swap two elements in the heap
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Inserts a new key 'k' into the priority queue.
     * @param k The key to insert.
     */
    public void add(int k) {
        if (size == capacity) {
            // For simplicity, we'll throw an exception. A real implementation might resize.
            throw new IllegalStateException("Heap is full");
        }
        // Insert the new key at the end
        heap[size] = k;
        int i = size;
        size++;

        // Fix the min-heap property if it is violated
        heapifyUp(i);
    }

    /**
     * Extracts the minimum element from the priority queue.
     * @return The minimum element.
     */
    public int getMin() {
        if (size <= 0) {
            throw new IllegalStateException("Heap is empty");
        }
        if (size == 1) {
            size--;
            return heap[0];
        }

        // Store the minimum value, and remove it from the heap
        int root = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);

        return root;
    }

    // Restores the min-heap property by moving an element up
    private void heapifyUp(int i) {
        while (i != 0 && heap[parent(i)] > heap[i]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    // Restores the min-heap property by moving an element down
    private void heapifyDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && heap[left] < heap[smallest]) {
            smallest = left;
        }
        if (right < size && heap[right] < heap[smallest]) {
            smallest = right;
        }

        if (smallest != i) {
            swap(i, smallest);
            heapifyDown(smallest);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        MinPriorityQueue pq = new MinPriorityQueue(10);
        pq.add(8);
        pq.add(3);
        pq.add(5);
        pq.add(9);
        pq.add(2);

        System.out.println("Extracting elements from Min-Priority Queue:");
        while (!pq.isEmpty()) {
            System.out.print(pq.getMin() + " ");
        }
        System.out.println();
    }
}
