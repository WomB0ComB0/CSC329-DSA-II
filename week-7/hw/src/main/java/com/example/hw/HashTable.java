package com.example.hw;

/**
 * A Hash Table implementation using separate chaining with a custom List class for buckets. Stores
 * instances of the Store class. DO NOT USE Java Collection Classes for the hash table or the list.
 */
class HashTable {

  private List[] buckets;
  private int numberOfElements;
  private static final double MAX_LOAD_FACTOR = 0.75;

  // 3.a. Default constructor. Starts with 4 buckets.
  public HashTable() {
    this.buckets = new List[4];
    for (int i = 0; i < buckets.length; i++) {
      buckets[i] = new List();
    }
    this.numberOfElements = 0;
  }

  // 3.b. Copy constructor (DEEP COPY)
  public HashTable(HashTable other) {
    this.numberOfElements = other.numberOfElements;
    this.buckets = new List[other.buckets.length];

    // Deep copy of the array of List objects
    for (int i = 0; i < buckets.length; i++) {
      // Use the List's copy constructor to ensure a DEEP COPY
      // of the linked list and its Stores
      this.buckets[i] = new List(other.buckets[i]);
    }
  }

  // 3.c. HashTable copyInstance() - DEEP COPY of the current instance
  public HashTable copyInstance() {
    return new HashTable(this);
  }

  // Internal method to calculate the bucket index
  private int hashFunction(Store s) {
    // 2. Hash Function: Store.hashCode() % number_of_buckets
    // Math.abs is used to handle potential negative hash codes from Java's hashCode()
    return Math.abs(s.hashCode() % buckets.length);
  }

  // Internal method for rehashing when resizing
  private void rehash(int newCapacity) {

    // 1. Create a temporary array of new lists
    List[] newBuckets = new List[newCapacity];
    for (int i = 0; i < newCapacity; i++) {
      newBuckets[i] = new List();
    }

    // 2. Iterate through old buckets and move stores
    for (List oldList : buckets) {
      // Use List.toArray() to safely get all stores (as deep copies)
      Store[] listStores = oldList.toArray();
      for (Store s : listStores) {
        // Calculate new index using the NEW capacity
        int newIndex = Math.abs(s.hashCode() % newCapacity);

        // Add the store to the new list (List.add performs deep copy)
        newBuckets[newIndex].add(s);
      }
    }

    // 3. Update the buckets array. The numberOfElements remains the same.
    this.buckets = newBuckets;
  }

  // 3.d. void add(Store s) – Adds a store to the hash table.
  public void add(Store s) {
    // Check load factor BEFORE adding
    double loadFactor = (double) numberOfElements / buckets.length;
    if (loadFactor > MAX_LOAD_FACTOR) {
      // Double the size and rehash
      System.out.println("\n--- RESIZING HASH TABLE (Load Factor > 0.75) ---");
      System.out.println("Old size: " + buckets.length + ", New size: " + (buckets.length * 2));
      rehash(buckets.length * 2);
      System.out.println("--- RESIZE COMPLETE ---");
    }

    // Hash and add the store
    int index = hashFunction(s);
    buckets[index].add(s); // List.add performs deep copy
    numberOfElements++;
  }

  // 3.e. Store find(String owner) – Searches the hash table for the store with the given owner.
  public Store find(String owner) {
    // Create a dummy store just to call the hashCode method
    Store dummyStore = new Store(owner, 0.0);

    int index = hashFunction(dummyStore);

    // Search the specific list
    // List.find returns a deep copy of the Store, or null
    return buckets[index].find(owner);
  }

  // 3.f. void show() – Prints all data in the hash table, organized by bucket.
  public void show() {
    System.out.println(
        "--- Hash Table Contents (Size: "
            + buckets.length
            + ", Elements: "
            + numberOfElements
            + ", Load Factor: "
            + String.format("%.2f", (double) numberOfElements / buckets.length)
            + ") ---");
    for (int i = 0; i < buckets.length; i++) {
      System.out.print("Bucket " + i + ": ");
      System.out.println(buckets[i].toString());
    }
    System.out.println("------------------------------------------------------------------");
  }
}
