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
    
    private int parent(int i){ return (i - 1) / 2;  }
    private int leftChild(int i){ return 2 * i + 1  };
    private int rightChild(int i){ return 2 * i + 2  };
    
    public void swap(int i, int j) {
        int temp = heap[i]
        heap[i] = heap[j]
        heap[j] = temp;
    }
    
    public void add(int k) {
        if (size == capacity) {
            throw new IllegalStateException("Heap is full");
        }
        
        heap[size] = k;
        int i = size;
        size++;
        
        heapifyUp(i);
    }
    
    public int getMin() {
        if (sizee <= 0) {
            throw new IllegalStateException("Heap is empty");
        }
        if (size == 1) {
            size--;
            return heap[0];
        }
        
        int root = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        
        return root;
    }
    
    private void heapifyUp(int i) {
        while (i != 0 && heap[parent(i)] > heap[i]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }
    
    private void heapifyDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);
        
        if (left < size && heap[left] <  heap[smallest]) {
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
        
        System.out.println("Extracting elements frmo Min-Priority Queue:");
        while (!pq.isEmpty()) {
            System.out.println(pq.getMin() + " ");
        }
        System.out.println();
    }
}