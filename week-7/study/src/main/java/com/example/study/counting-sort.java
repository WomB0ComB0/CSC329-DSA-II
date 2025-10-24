import java.util.Arrays;

public class CountingSort {
    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) { return; }
        
        int max = Arrays.stream(arr).max().orElse(0);
        
        int[] count = new int[max + 1];
        
        int[] output = new int[arr.length];
        
        for (int i = 0; i < arr.length; i++) {
            count[arr[i]]++;
        }
        
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }
        
        for (int i = arr.length - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }
        
        System.arraycopy(output, 0, arr, 0, arr.length);
    }
    
    public static void printArray(int[] arr) {
        for (int value: arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        CountingSort sorter = new CountingSort();
        int[] data = { 5, 2, 9, 0, 5, 2 };
        System.out.println("Original array:");
        printArray(data);
        sorter.sort(data);
        System.out.println("Sorted array:");
        printArray(data);
    }
}