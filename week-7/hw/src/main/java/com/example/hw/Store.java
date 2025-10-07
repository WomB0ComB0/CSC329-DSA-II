/**
 * Represents a store with an owner and sales data.
 * This class is used as the data element in the priority queue and heapsort implementation.
 */
public class Store {
    private String owner;
    private double sales;

    /**
     * Constructs a new Store instance.
     *
     * @param owner The name of the store's owner.
     * @param sales The total sales amount for the store.
     */
    public Store(String owner, double sales) {
        this.owner = owner;
        this.sales = sales;
    }

    /**
     * Copy constructor for the Store class.
     * Creates a new Store instance that is a copy of the given store.
     *
     * @param other The Store object to copy.
     */
    public Store(Store other) {
        this.owner = other.owner;
        this.sales = other.sales;
    }

    /**
     * Gets the owner of the store.
     *
     * @return The store owner's name.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Gets the sales of the store.
     *
     * @return The store's sales amount.
     */
    public double getSales() {
        return sales;
    }

    /**
     * Sets the sales for the store.
     * Note: This method is primarily included to demonstrate the deep copy functionality.
     * Modifying a Store's sales after it has been inserted into a heap
     * without re-heapifying will break the heap property.
     *
     * @param sales The new sales amount.
     */
    public void setSales(double sales) {
        this.sales = sales;
    }

    /**
     * Returns a string representation of the Store object.
     *
     * @return A formatted string containing the owner and sales.
     */
    @Override
    public String toString() {
        return String.format("Store{Owner='%s', Sales=%.2f}", owner, sales);
    }
}