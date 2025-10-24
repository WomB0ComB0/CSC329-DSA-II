public class SelectionSort {
    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) { return; }
    
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }
    
    public static void printArray(int[] arr) {
        for (int value: arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        SelectionSort sorter = new SelectionSort();
        int[] data = { 10, 7, 19, 5, 16 };
        System.out.println("Original array");
        printArray(data);
        sorter.sort(data);
        System.out.println("Sorted array");
        printArray(data);
    }
};