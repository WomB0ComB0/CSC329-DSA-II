import java.util.Arrays;

public class CountingSort {

    /**
     * Sorts an array of non-negative integers using Counting Sort.
     * @param arr The array to be sorted.
     */
    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        // Find the maximum value in the array to determine the range.
        int max = Arrays.stream(arr).max().orElse(0);

        // Create a count array to store the frequency of each element.
        // The size is max + 1 to accommodate the element with value 'max'.
        int[] count = new int[max + 1];

        // Create an output array to store the sorted elements.
        int[] output = new int[arr.length];

        // 1. Store the count of each character.
        for (int i = 0; i < arr.length; i++) {
            count[arr[i]]++;
        }

        // 2. Change count[i] so that count[i] now contains the actual
        //    position of this element in the output array.
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        // 3. Build the output character array.
        // To make it stable, we are operating in reverse order.
        for (int i = arr.length - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }

        // Copy the sorted elements into the original array.
        System.arraycopy(output, 0, arr, 0, arr.length);
    }
    
    public static void main(String[] args) {
        CountingSort sorter = new CountingSort();
        int[] data = {5, 2, 9, 0, 5, 2};
        System.out.println("Original array:");
        SelectionSort.printArray(data);
        sorter.sort(data);
        System.out.println("Sorted array:");
        SelectionSort.printArray(data);
    }
}