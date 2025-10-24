public class HeapSort {
    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) { return }

        int n = arr.length;

       for (int i = n / 2 - 1; i >= 0; i--) {
           heapify(arr, n, i);
       }

       for (int i = n - 1; i > 0; i--) {
           int temp = arr[0];
           arr[0] = arr[i];
           arr[i] = temp;
           heapify(arr, i, 0);
       }
    }

    void heapify(int[] arr, int n, int i) {
        int largest = i;
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;

        if (leftChild < n && arr[leftChild] > arr[largest]) {
            largest = leftChild;
        }

        if (rightChild < n && arr[rightChild] > arr[largest]) {
            largest = rightChild;
        }

        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            heapify(arr, n, largest);
        }
    }

    public static void printArray(int[] arr) {
        for (int value: arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        HeapSort sorter = new HeapSort();
        int[] arr = { 10, 7, 19, 5, 16};
        System.out.println("Original array:");
        printArray(arr);
        System.out.println("Sorted array:");
        sorter.sort(arr);
        printArray(arr);
    }
}
