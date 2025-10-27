import java.util.LinkedList;

public class HashTable<K, V> {

    // Inner class to store key-value pairs
    private class HashNode {
        K key;
        V value;

        HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<HashNode>[] buckets;
    private int numBuckets;
    private int size; // Number of key-value pairs

    public HashTable() {
        this(10); // Default initial capacity
    }

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        this.numBuckets = capacity;
        this.buckets = new LinkedList[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            buckets[i] = new LinkedList<>();
        }
        this.size = 0;
    }

    // Hash function to find an index for a key
    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        // The modulo operator ensures the index is within the bounds of the array size
        return Math.abs(hashCode % numBuckets);
    }

    /**
     * Adds a key-value pair to the hash table.
     * If the key already exists, its value is updated.
     * @param key The key.
     * @param value The value.
     */
    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashNode> chain = buckets[bucketIndex];

        // Check if the key is already present
        for (HashNode node : chain) {
            if (node.key.equals(key)) {
                node.value = value; // Update value
                return;
            }
        }

        // Insert new key-value pair
        size++;
        chain.add(new HashNode(key, value));

        // Optional: Resize the hash table if the load factor exceeds a threshold
        // Load factor = size / numBuckets
        if ((1.0 * size) / numBuckets >= 0.75) {
            resize();
        }
    }

    /**
     * Retrieves the value for a given key.
     * @param key The key.
     * @return The value associated with the key, or null if the key is not found.
     */
    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashNode> chain = buckets[bucketIndex];

        for (HashNode node : chain) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }

        // If key not found
        return null;
    }

    /**
     * Removes a key-value pair from the hash table.
     * @param key The key to be removed.
     * @return The value of the removed key, or null if the key was not found.
     */
    public V remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashNode> chain = buckets[bucketIndex];

        HashNode toRemove = null;
        for (HashNode node : chain) {
            if (node.key.equals(key)) {
                toRemove = node;
                break;
            }
        }

        if (toRemove != null) {
            chain.remove(toRemove);
            size--;
            return toRemove.value;
        }

        // If key not found
        return null;
    }
    
    // A helper method to resize the hash table
    @SuppressWarnings("unchecked")
    private void resize() {
        LinkedList<HashNode>[] oldBuckets = buckets;
        numBuckets *= 2; // Double the capacity
        this.buckets = new LinkedList[numBuckets];
        this.size = 0;

        for (int i = 0; i < numBuckets; i++) {
            buckets[i] = new LinkedList<>();
        }

        for (LinkedList<HashNode> oldChain : oldBuckets) {
            for (HashNode node : oldChain) {
                put(node.key, node.value);
            }
        }
    }
    
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }
    
    public static void main(String[] args) {
        HashTable<String, Integer> map = new HashTable<>();
        map.put("Yankees", 1);
        map.put("Phillies", 2);
        map.put("Red Sox", 3);
        map.put("Mets", 4);

        System.out.println("Size of map is: " + map.size());
        System.out.println("Value for 'Red Sox' is: " + map.get("Red Sox"));
        System.out.println("Removing 'Phillies'...");
        map.remove("Phillies");
        System.out.println("Value for 'Phillies' after removal: " + map.get("Phillies"));
        System.out.println("Size of map is now: " + map.size());
    }
}