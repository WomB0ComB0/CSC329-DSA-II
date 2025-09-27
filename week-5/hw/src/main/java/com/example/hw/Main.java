import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Main class to run the heap-based priority queue and heapsort assignment.
 * It reads store data from a file, sorts it using an in-place heapsort algorithm,
 * prints the result, and demonstrates the deep copy functionality of the
 * PriorityQueueHeap class.
 */
public class Main {

    private static final int NUM_STORES = 1000;
    private static final String INPUT_FILE_NAME = "store_data.txt";

    /**
     * The main entry point for the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Prepare the input file
        try {
            createSampleDataFile(INPUT_FILE_NAME, NUM_STORES);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not create the sample data file.");
            e.printStackTrace();
            return;
        }

        // Part 1: Read data into a Store array
        Store[] stores = new Store[NUM_STORES];
        try {
            readStoresFromFile(INPUT_FILE_NAME, stores);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file not found: " + INPUT_FILE_NAME);
            return;
        }

        System.out.println("--- Heapsort Demonstration ---");
        System.out.println("Original first 10 stores:");
        printStoreArray(stores, 10);

        // Part 2: Run the heapsort method
        heapsort(stores);

        System.out.println("\nSorted first 10 stores (Highest to Lowest Sales):");
        // Part 3: Print the sorted store array
        printStoreArray(stores, 10);

        // Part 4: Demonstrate that copy constructor and copyInstance work
        System.out.println("\n--- Deep Copy Demonstration ---");
        demonstrateDeepCopy();
    }

    /**
     * Sorts an array of Store instances by sales from highest to lowest using an
     * in-place heapsort algorithm.
     *
     * @param pa The array of Store objects to be sorted.
     */
    public static void heapsort(Store[] pa) {
        int n = pa.length;

        // 1. Build a max heap from the input array (heapify)
        // Start from the last non-leaf node and move up to the root
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(pa, n, i);
        }

        // 2. Extract elements one by one from the heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root (the max element) to the end of the sorted portion
            swap(pa, 0, i);

            // Call siftDown on the reduced heap (size is i)
            siftDown(pa, i, 0);
        }

        // 3. The array is now sorted from lowest to highest. Reverse it for highest to lowest.
        reverseArray(pa);
    }

    /**
     * Helper method for heapsort. Restores the max heap property for a subtree
     * rooted at a given index. This version works on a raw array.
     *
     * @param arr The array representing the heap.
     * @param n The size of the heap within the array.
     * @param i The index of the root of the subtree to sift down.
     */
    private static void siftDown(Store[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // left child
        int right = 2 * i + 2; // right child

        // If left child is larger than root
        if (left < n && arr[left].getSales() > arr[largest].getSales()) {
            largest = left;
        }

        // If right child is larger than the largest so far
        if (right < n && arr[right].getSales() > arr[largest].getSales()) {
            largest = right;
        }

        // If largest is not the root, swap them and continue sifting down
        if (largest != i) {
            swap(arr, i, largest);
            siftDown(arr, n, largest);
        }
    }

    /**
     * Swaps two elements in a Store array.
     *
     * @param arr The array in which to swap elements.
     * @param i   Index of the first element.
     * @param j   Index of the second element.
     */
    private static void swap(Store[] arr, int i, int j) {
        Store temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Reverses the order of elements in a Store array.
     *
     * @param arr The array to reverse.
     */
    private static void reverseArray(Store[] arr) {
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            swap(arr, start, end);
            start++;
            end--;
        }
    }

    /**
     * Demonstrates the deep copy functionality of the PriorityQueueHeap's
     * copy constructor and copyInstance method.
     */
    private static void demonstrateDeepCopy() {
        // Create an original Store object and a priority queue
        Store originalStore = new Store("Test Owner", 5000.0);
        PriorityQueueHeap pq1 = new PriorityQueueHeap();
        pq1.insert(originalStore);
        pq1.insert(new Store("Another Owner", 10000.0));

        System.out.println("Original PQ size: " + pq1.size());

        // 1. Test the Copy Constructor
        PriorityQueueHeap pqCopy = new PriorityQueueHeap(pq1);
        System.out.println("Copied PQ size (via constructor): " + pqCopy.size());

        // 2. Test the copyInstance method
        PriorityQueueHeap pqInstanceCopy = pq1.copyInstance();
        System.out.println("Copied PQ size (via copyInstance): " + pqInstanceCopy.size());


        // Modify the original Store object *after* copying.
        // If the copies are deep, they should contain the original sales value (5000.0).
        originalStore.setSales(99999.99);

        // Remove the max element from the original queue
        System.out.println("\nRemoving max element from original queue: " + pq1.getHighestSalesStore());
        System.out.println("Original PQ size after removal: " + pq1.size());
        System.out.println("Copied PQ size (should be unchanged): " + pqCopy.size());
        System.out.println("Instance Copy PQ size (should be unchanged): " + pqInstanceCopy.size());

        // Verify that the copies are independent and were deep copies
        System.out.println("\nMax element from constructor copy (should have original sales): " + pqCopy.getHighestSalesStore());
        System.out.println("Max element from copyInstance copy (should have original sales): " + pqInstanceCopy.getHighestSalesStore());

        Store s = pqCopy.getHighestSalesStore();
        System.out.println("Remaining element from constructor copy (sales should be 5000.0, not 99999.99): " + s);

        if (Math.abs(s.getSales() - 5000.0) < 0.01) {
            System.out.println("SUCCESS: Deep copy confirmed. The copied object's sales were not affected by modifying the original object.");
        } else {
            System.out.println("FAILURE: This appears to be a shallow copy.");
        }
    }

    /**
     * Reads store data from a text file into an array of Store objects.
     *
     * @param filename The name of the file to read from.
     * @param stores   The array to populate with Store objects.
     * @throws FileNotFoundException if the file cannot be found.
     */
    private static void readStoresFromFile(String filename, Store[] stores) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            for (int i = 0; i < stores.length && scanner.hasNextLine(); i++) {
                String owner = scanner.nextLine();
                double sales = Double.parseDouble(scanner.nextLine());
                stores[i] = new Store(owner, sales);
            }
        }
    }

    /**
     * Prints a specified number of elements from a Store array.
     *
     * @param stores The array of stores to print.
     * @param count  The number of stores to print from the beginning of the array.
     */
    private static void printStoreArray(Store[] stores, int count) {
        for (int i = 0; i < count && i < stores.length; i++) {
            System.out.printf("  %d: %s%n", i + 1, stores[i]);
        }
    }

    /**
     * Creates a sample data file with a specified number of stores.
     * Each store has a randomly generated owner name and sales figure.
     *
     * @param filename  The name of the file to create.
     * @param numStores The number of stores to generate.
     * @throws FileNotFoundException if the file cannot be created.
     */
    private static void createSampleDataFile(String filename, int numStores) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            Random rand = new Random();
            for (int i = 0; i < numStores; i++) {
                writer.println("Owner_" + (i + 1)); // Owner
                writer.println(1000 + (99000 * rand.nextDouble())); // Sales (1000 to 100000)
            }
        }
    }
}