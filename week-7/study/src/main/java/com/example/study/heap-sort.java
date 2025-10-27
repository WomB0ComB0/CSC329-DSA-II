public class Heapsort {

    /**
     * Main function to perform heapsort.
     * @param arr The array to be sorted.
     */
    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;

        // 1. Build a max heap from the input data.
        // We start from the last non-leaf node and go up to the root.
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // 2. One by one, extract elements from the heap.
        for (int i = n - 1; i > 0; i--) {
            // Move the current root (max element) to the end of the array.
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Call max heapify on the reduced heap.
            heapify(arr, i, 0);
        }
    }

    /**
     * To heapify a subtree rooted with node i which is an index in arr[].
     * n is the size of the heap.
     * @param arr The array representing the heap.
     * @param n The size of the heap.
     * @param i The index of the root of the subtree to heapify.
     */
    void heapify(int[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;

        // If the left child is larger than the root
        if (leftChild < n && arr[leftChild] > arr[largest]) {
            largest = leftChild;
        }

        // If the right child is larger than the largest so far
        if (rightChild < n && arr[rightChild] > arr[largest]) {
            largest = rightChild;
        }

        // If the largest is not the root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    public static void main(String[] args) {
        Heapsort sorter = new Heapsort();
        int[] data = { 10, 7, 19, 5, 16 };
        System.out.println("Original array:");
        SelectionSort.printArray(data);
        sorter.sort(data);
        System.out.println("Sorted array:");
        SelectionSort.printArray(data);
    }
}
