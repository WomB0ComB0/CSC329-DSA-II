import java.util.LinkedList;

public class HashTable<K, V> {
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
    private int size;
    
    public HashTable() {
        this(10); // Default capacity is 10
    }
    
    @SupressWwarnings("unchecked")
    public HashTable(int capacity) {
        this.numBuckets = capacity;
        this.buckets = new LinkedList[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            buckets[i] = new LinkedList<>();
        }
        this.size = 0;
    }
    
    private int getBucketIndex(K key) {
        iint hashCode = key.hashCode();
        
        return Math.abs(hashCode % numBuckets);
    }
    
    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashNode> bucket = buckets[bucketIndex];
        
        for (HashNode node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        
        bucket.add(new HashNode(key, value));
        size++;
        
        if ((double) size / numBuckets > 0.75) {
            resize();
        }
    }
    
    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashNode> bucket = buckets[bucketIndex];
        
        for (HashNode node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        
        return null;
    }
    
    public V remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashNode> bucket = buckets[bucketIndex];
        
        for (HashNode node : bucket) {
            if (node.key.equals(key)) {
                bucket.remove(node);
                size--;
                return node.value;
            }
        }
        
        return null;
    }
    
    private void resize() {
        int newCapacity = numBuckets * 2;
        LinkedList<HashNode>[] newBuckets = new LinkedList[newCapacity];
        
        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new LinkedList<>();
        }
        
        for (LinkedList<HashNode> bucket : buckets) {
            for (HashNode node : bucket) {
                int newIndex = getBucketIndex(node.key, newCapacity);
                newBuckets[newIndex].add(node);
            }
        }
        
        buckets = newBuckets;
        numBuckets = newCapacity;
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
        System.out.println("Value for 'Red Sox': " + map.get("Red Sox"));
        System.out.println("Removing 'Phillies'...");
        
        map.remove("Phillies");
        System.out.println(map.get("Phillies"));
        System.out.println(map.size());
    }
}