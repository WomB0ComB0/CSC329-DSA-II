public class SelectionSort {

    /**
     * Sorts an array of integers using the Selection Sort algorithm.
     * @param arr The array to be sorted.
     */
    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return; // The array is already sorted or empty
        }

        int n = arr.length;

        // One by one, move the boundary of the unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in the unsorted array
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // Swap the found minimum element with the first element
            // of the unsorted part.
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    /**
     * A helper method to print the elements of an array.
     * @param arr The array to print.
     */
    public static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SelectionSort sorter = new SelectionSort();
        int[] data = {10, 7, 19, 5, 16};
        System.out.println("Original array:");
        printArray(data);
        sorter.sort(data);
        System.out.println("Sorted array:");
        printArray(data);
    }
}