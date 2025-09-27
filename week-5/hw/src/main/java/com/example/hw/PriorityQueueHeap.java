import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A heap-based implementation of the PriorityQueue interface storing Store instances.
 * This implementation uses an array to build a max heap, where priority is determined
 * by the store's sales (higher sales mean higher priority).
 * The heap is implemented from scratch and does not use any Java Collection classes.
 */
public class PriorityQueueHeap implements PriorityQueue<Store> {

    private static final int DEFAULT_CAPACITY = 10;
    private Store[] heap;
    private int size;

    /**
     * Default constructor.
     * Initializes an empty priority queue with a default initial capacity.
     */
    public PriorityQueueHeap() {
        this.heap = new Store[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Copy constructor.
     * Creates a new PriorityQueueHeap that is a DEEP COPY of the other instance.
     * This means that not only the heap array is new, but all Store objects within it
     * are also new instances, ensuring that modifications to one queue do not affect the other.
     *
     * @param other The PriorityQueueHeap instance to copy.
     */
    public PriorityQueueHeap(PriorityQueueHeap other) {
        this.size = other.size;
        this.heap = new Store[other.heap.length];
        // Perform a deep copy of each element
        for (int i = 0; i < other.size; i++) {
            this.heap[i] = new Store(other.heap[i]); // Use Store's copy constructor
        }
    }

    /**
     * Creates and returns a new PriorityQueueHeap instance that is a DEEP COPY
     * of the current instance.
     *
     * @return A new, independent PriorityQueueHeap instance with the same content.
     */
    public PriorityQueueHeap copyInstance() {
        return new PriorityQueueHeap(this);
    }

    @Override
    public void insert(Store element) {
        ensureCapacity();
        heap[size] = element;
        siftUp(size);
        size++;
    }

    @Override
    public Store getHighestSalesStore() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty.");
        }
        Store max = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null; // Help garbage collector
        size--;
        siftDown(0);
        return max;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Restores the max heap property by moving an element up the tree.
     * This is called after an insertion to place the new element in its correct position.
     *
     * @param index The index of the element to sift up.
     */
    private void siftUp(int index) {
        int parentIndex = (index - 1) / 2;
        while (index > 0 && heap[index].getSales() > heap[parentIndex].getSales()) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    /**
     * Restores the max heap property by moving an element down the tree.
     * This is called after removing the root to fix the heap structure.
     *
     * @param index The index of the element to sift down.
     */
    private void siftDown(int index) {
        int leftChildIndex;
        int rightChildIndex;
        int largerChildIndex;

        while (index < size / 2) { // Only need to check parent nodes
            leftChildIndex = 2 * index + 1;
            rightChildIndex = 2 * index + 2;
            largerChildIndex = leftChildIndex;

            // Find the index of the larger child
            if (rightChildIndex < size && heap[rightChildIndex].getSales() > heap[leftChildIndex].getSales()) {
                largerChildIndex = rightChildIndex;
            }

            // If the current node is smaller than its largest child, swap them
            if (heap[index].getSales() < heap[largerChildIndex].getSales()) {
                swap(index, largerChildIndex);
                index = largerChildIndex; // Move down to the child's position
            } else {
                break; // The heap property is satisfied
            }
        }
    }

    /**
     * Swaps two elements in the heap array.
     *
     * @param i The index of the first element.
     * @param j The index of the second element.
     */
    private void swap(int i, int j) {
        Store temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Ensures the internal array has enough capacity to add a new element.
     * If the array is full, it is resized to double its current capacity.
     */
    private void ensureCapacity() {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
    }
}