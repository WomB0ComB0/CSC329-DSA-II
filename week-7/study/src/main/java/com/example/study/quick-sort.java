public class Quicksort {

    /**
     * The main function that implements Quicksort.
     * @param arr   The array to be sorted.
     * @param low   Starting index.
     * @param high  Ending index.
     */
    public void sort(int[] arr, int low, int high) {
        if (low < high) {
            // pi is partitioning index, arr[pi] is now at the right place
            int pi = partition(arr, low, high);

            // Recursively sort elements before partition and after partition
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }

    /**
     * This function takes the last element as pivot, places the pivot element
     * at its correct position in the sorted array, and places all smaller
     * (smaller than pivot) to the left of the pivot and all greater elements
     * to the right of the pivot.
     * @param arr   The array to be partitioned.
     * @param low   The starting index.
     * @param high  The ending index.
     * @return The partitioning index.
     */
    private int partition(int[] arr, int low, int high) {
        // In this implementation, we choose the last element as the pivot.
        int pivot = arr[high];
        int i = (low - 1); // Index of the smaller element

        for (int j = low; j < high; j++) {
            // If the current element is smaller than or equal to the pivot
            if (arr[j] <= pivot) {
                i++;

                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    public static void main(String[] args) {
        Quicksort sorter = new Quicksort();
        int[] data = { 50, 40, 10, 70, 60, 20, 90, 80, 30 };
        System.out.println("Original array:");
        SelectionSort.printArray(data);
        sorter.sort(data, 0, data.length - 1);
        System.out.println("Sorted array:");
        SelectionSort.printArray(data);
    }
}
