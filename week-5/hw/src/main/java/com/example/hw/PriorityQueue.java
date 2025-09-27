/**
 * An interface for a Priority Queue data structure.
 * It defines the essential operations for inserting and retrieving elements
 * based on their priority.
 *
 * @param <T> The type of elements held in this priority queue.
 */
public interface PriorityQueue<T> {

    /**
     * Adds an element to the priority queue.
     *
     * @param element The element to be added.
     */
    void insert(T element);

    /**
     * Retrieves and removes the element with the highest priority.
     * In the context of the assignment, this is the store with the highest sales.
     *
     * @return The element with the highest priority, or null if the queue is empty.
     */
    T getHighestSalesStore();

    /**
     * Checks if the priority queue is empty.
     *
     * @return true if the priority queue contains no elements, false otherwise.
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the priority queue.
     *
     * @return The size of the priority queue.
     */
    int size();
}