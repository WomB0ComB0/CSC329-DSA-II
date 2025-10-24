public class QuickSort {
    public void sort(int[] arr, int low, int high) {
        if (low <  high) {
            int pi = partition(arr, low, high);
            
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }
    
    public int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }

    public static void printArray(int[] arr) {
        for (int value: arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        QuickSort sorter = new QuickSort();
        int[] data = { 50,40, 10, 70, 60, 20, 90, 80, 30 };
        System.out.println("Original array:");
        printArray(data);
        sorter.sort(data, 0, data.length - 1);
        System.out.println("Sorted array");
        printArray(data);
    }
}