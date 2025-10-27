### **Comprehensive Study Guide: Algorithms and Data Structures**

This guide covers fundamental sorting algorithms, priority queues, and hash tables, focusing on their operational logic, characteristics, and performance analysis (Big O notation).

---

### **Part 1: Foundational Concepts**

Before diving into specific algorithms, it's crucial to understand these key classification terms.

*   **Comparison vs. Non-Comparison Sort:**
    *   **Comparison Sorts** determine the order of elements by comparing them to each other (e.g., using `<` or `>`). Examples include Quicksort, Heapsort, and Selection Sort. The theoretical best-case time complexity for comparison sorts is **O(n log n)**.
    *   **Non-Comparison Sorts** use other properties of the data (like their integer value or range) to sort them without direct comparisons. This allows them to achieve faster runtimes, like linear time. An example is Counting Sort.

*   **In-Place vs. Not-In-Place:**
    *   An **In-Place** algorithm transforms the input using a negligible amount of extra storage space (typically O(1) or O(log n)). The input is usually overwritten by the output. Examples: Selection Sort, Heapsort, Quicksort.
    *   A **Not-In-Place** algorithm requires significant extra space to store data during its execution. Example: Counting Sort, which requires extra arrays for counts and output.

*   **Stable vs. Unstable Sort:**
    *   A **Stable** sorting algorithm maintains the relative order of equal-value elements. If two items have the same value, their order in the output array will be the same as in the input array. Examples: Counting Sort, Insertion Sort.
    *   An **Unstable** sorting algorithm does not guarantee the relative order of equal-value elements. Their original order might be changed. Examples: Quicksort, Heapsort, Selection Sort.

---

### **Part 2: Sorting Algorithms**

#### **1. Selection Sort**

*   **Overview:** An elementary, in-place sorting algorithm. It conceptually divides the array into a sorted portion and an unsorted portion, and iteratively expands the sorted portion.
*   **How it Works:**
    1.  Start at the first element.
    2.  Traverse the rest of the array (the unsorted portion) to find the minimum element.
    3.  Swap the minimum element with the first element of the unsorted portion.
    4.  Move the boundary of the sorted portion one element to the right.
    5.  Repeat until the entire array is sorted.
*   **Performance:**
    *   **Time Complexity:** **O(n²)** in all cases (Best, Average, and Worst). The two nested loops (one to select the position, one to find the minimum) dominate the runtime, regardless of the initial order of the data.
    *   **Space Complexity:** **O(1)** (in-place).
*   **Characteristics:**
    *   **In-Place:** Yes.
    *   **Stable:** No, by default. The swap operation can change the relative order of equal elements.
    *   **Comparison-based:** Yes.

#### **2. Heapsort**

*   **Overview:** A more efficient comparison-based sort that uses a **binary heap** data structure.
*   **How it Works:**
    1.  **Heapify:** Build a **Max-Heap** from the input array. A Max-Heap is a complete binary tree where the value of each parent node is greater than or equal to its children. This places the largest element at the root of the heap (index 0).
    2.  **Sort:**
        *   Swap the root element with the last element in the heap.
        *   Reduce the size of the heap by one (effectively moving the largest element to its final sorted position at the end of the array).
        *   Call `heapifyDown` on the new root to restore the max-heap property.
        *   Repeat this process until the heap size is 1.
*   **Performance:**
    *   **Time Complexity:** **O(n log n)** in all cases (Best, Average, and Worst). Building the heap takes O(n), and each of the `n` `heapifyDown` operations takes O(log n).
    *   **Space Complexity:** **O(1)** (in-place).
*   **Characteristics:**
    *   **In-Place:** Yes.
    *   **Stable:** No. Swaps can alter the relative order of equal elements.
    *   **Comparison-based:** Yes.

#### **3. Counting Sort**

*   **Overview:** A non-comparison, linear-time sorting algorithm that is efficient only when the range of input data is relatively small.
*   **How it Works:**
    1.  **Find Range:** Determine the maximum value (`k`) in the input array.
    2.  **Count Frequencies:** Create a `count` array of size `k+1`. Iterate through the input array and store the frequency of each element in the `count` array (e.g., `count[5] = 2` if the number 5 appears twice).
    3.  **Calculate Cumulative Counts:** Modify the `count` array so that each index stores the sum of its original value and all previous counts. This cumulative value now represents the final sorted position of that element.
    4.  **Build Output Array:** Iterate through the input array in reverse (to ensure stability). Place each element into a new `output` array at the position indicated by the `count` array, and decrement the count for that element.
*   **Performance:**
    *   **Time Complexity:** **O(n + k)**, where `n` is the number of elements and `k` is the range of values. It becomes inefficient if `k` is much larger than `n`.
    *   **Space Complexity:** **O(n + k)** (not in-place), for the `count` and `output` arrays.
*   **Characteristics:**
    *   **In-Place:** No.
    *   **Stable:** Yes.
    *   **Comparison-based:** No.

#### **4. Quicksort**

*   **Overview:** A highly efficient, divide-and-conquer algorithm. It works by partitioning an array around a **pivot** element.
*   **How it Works (Lomuto Partitioning):**
    1.  **Choose Pivot:** Select a pivot element (e.g., the first or a random element).
    2.  **Partition:** Reorder the array so that all elements smaller than the pivot are on its left, and all elements larger are on its right. After this step, the pivot is in its final sorted position.
    3.  **Recurse:** Recursively apply the above steps to the sub-arrays on the left and right of the pivot.
*   **Randomized Quicksort:** To avoid the worst-case scenario (which often occurs on sorted or nearly sorted data), the pivot can be chosen randomly. This makes the O(n²) worst-case extremely unlikely in practice.
*   **Performance:**
    *   **Time Complexity:**
        *   **Average & Best Case:** **O(n log n)**. On average, the pivot will divide the array into roughly equal halves.
        *   **Worst Case:** **O(n²)**. Occurs when the pivot is consistently the smallest or largest element, creating highly unbalanced partitions.
    *   **Space Complexity:** **O(log n)**. This is for the recursive call stack space, so it is not strictly in-place but is very space-efficient.
*   **Characteristics:**
    *   **In-Place:** Yes (by convention).
    *   **Stable:** No. The partitioning swaps can change the relative order of equal elements.
    *   **Comparison-based:** Yes.

---

### **Part 3: Data Structures**

#### **1. Priority Queues & Heaps**

*   **Priority Queue (ADT):** An abstract data type similar to a regular queue but where each element has a "priority." Elements with higher priority are dequeued before elements with lower priority.
    *   **Key Operations:** `add(item)`, `getMin()` (or `getMax()`).
*   **Heap:** The ideal data structure for implementing a priority queue.
    *   **Structure:** A **complete** or **almost-complete binary tree**. In an array-based implementation, this means there are no gaps in the array.
    *   **Heap Property:**
        *   **Min-Heap:** The value of any node is less than or equal to the values of its children. The root holds the minimum value.
        *   **Max-Heap:** The value of any node is greater than or equal to the values of its children. The root holds the maximum value.
    *   **Array Implementation:** A heap can be efficiently stored in an array. For an element at index `i`:
        *   **Parent:** `(i - 1) / 2`
        *   **Left Child:** `2 * i + 1`
        *   **Right Child:** `2 * i + 2`
*   **Heap Operations & Complexity:**
    *   **`add(item)` (O(log n)):** Add the element to the end of the array, then perform **`heapifyUp`** (bubble up) by repeatedly swapping it with its parent until the heap property is restored.
    *   **`getMin()` (O(log n)):**
        1.  Save the root value to be returned.
        2.  Replace the root with the last element in the heap.
        3.  Decrement the heap size.
        4.  Perform **`heapifyDown`** (bubble down) by repeatedly swapping the new root with its smaller child until the heap property is restored.

#### **2. Hash Tables (Map ADT)**

*   **Overview:** A data structure that implements a **Map ADT** (associative array) by mapping keys to values. It offers very fast average-case lookup, insertion, and deletion times.
*   **Core Components:**
    *   **Hash Function:** A function that converts a key into an array index (a "bucket" number). A good hash function distributes keys evenly.
    *   **Buckets:** An array of containers that store the values.
    *   **Collision:** Occurs when two different keys map to the same bucket index.
*   **Collision Resolution: Separate Chaining**
    *   Each bucket is a linked list (or another data structure).
    *   When a collision occurs, the new key-value pair is simply added to the linked list at that bucket index.
*   **Load Factor & Resizing:**
    *   **Load Factor (α) = n / m** (where `n` = number of items, `m` = number of buckets). It represents the average number of items per bucket.
    *   **Resizing:** When the load factor exceeds a certain threshold (e.g., 0.75), the hash table's performance degrades. To fix this, a new, larger array is created (often double the size, preferably a prime number), and all existing key-value pairs are re-hashed into the new table.
*   **Performance:**
    *   **Time Complexity:**
        *   **Average Case:** **O(1)**. With a good hash function and proper resizing, insertion, deletion, and lookup are constant time. The `add` operation has an **amortized** O(1) cost, meaning the expensive resizing operations are averaged out over many cheap insertions.
        *   **Worst Case:** **O(n)**. Occurs if all keys hash to the same bucket, turning the structure into a single linked list.
    *   **When Not to Use:** Real-time systems with strict time requirements may not tolerate the occasional O(n) cost of a resize during an `add` operation.

---

### **Part 4: Sorting Algorithm Comparison Summary**

| Algorithm | Avg Time Complexity | Worst Time Complexity | Space Complexity | Stable | In-Place | Notes |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Selection Sort**| O(n²) | O(n²) | O(1) | No | Yes | Simple but inefficient for large datasets. |
| **Heapsort** | O(n log n) | O(n log n) | O(1) | No | Yes | Great worst-case performance, good for in-place sorting. |
| **Counting Sort**| O(n + k) | O(n + k) | O(n + k) | Yes | No | Extremely fast but only for integers in a small range (`k`). |
| **Quicksort** | O(n log n) | O(n²) | O(log n) | No | Yes | Often the fastest in practice. Worst-case is rare with randomization. |
