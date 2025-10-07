package com.example.hw;

/**
 * A singly-linked, unordered list that contains instances of Store. Implements a custom list; DO
 * NOT USE a Java Collection Class.
 */
class List {

  // Inner class for the singly-linked list node
  private static class ListNode {
    Store store;
    ListNode next;

    /**
     * Constructor for a ListNode. Assumes the Store instance passed is already a deep copy if
     * coming from an external source.
     *
     * @param store The Store instance to be held in this node.
     */
    ListNode(Store store) {
      this.store = store;
      this.next = null;
    }
  }

  private ListNode head;
  private int size;

  // 2.a. Default constructor
  public List() {
    this.head = null;
    this.size = 0;
  }

  // 2.b. Copy constructor (DEEP COPY)
  public List(List other) {
    this(); // Initialize head to null, size to 0
    if (other != null && other.head != null) {
      ListNode currentOther = other.head;
      ListNode tailThis = null;

      while (currentOther != null) {
        // Create a new node with a DEEP COPY of the Store
        // We are prepending here (simple list copy)
        ListNode newNode = new ListNode(new Store(currentOther.store));

        if (this.head == null) {
          this.head = newNode;
          tailThis = this.head;
        } else {
          tailThis.next = newNode;
          tailThis = tailThis.next;
        }
        currentOther = currentOther.next;
        this.size++;
      }
    }
  }

  // 2.c. List copyInstance() - DEEP COPY of the current instance
  public List copyInstance() {
    return new List(this);
  }

  // 2.d. void add(Store s) – Adds a store to the START of the list.
  public void add(Store s) {
    // Create a deep copy of the incoming Store to prevent external modification issues.
    ListNode newNode = new ListNode(new Store(s));
    newNode.next = head;
    head = newNode;
    size++;
  }

  // 2.e. Store find(String owner) – Searches the list for the store with the given owner.
  public Store find(String owner) {
    if (owner == null) return null;

    ListNode current = head;
    while (current != null) {
      if (owner.equals(current.store.getOwner())) {
        // Return a DEEP COPY of the Store instance to protect the list's internal data.
        return new Store(current.store);
      }
      current = current.next;
    }
    return null;
  }

  public int size() {
    return size;
  }

  /**
   * Converts the list into an array of deep-copied Store instances. Used internally by the
   * HashTable for rehashing.
   *
   * @return An array of Store objects (deep copies).
   */
  public Store[] toArray() {
    Store[] stores = new Store[size];
    ListNode current = head;
    int i = 0;
    while (current != null) {
      // Return a deep copy of the Store
      stores[i++] = new Store(current.store);
      current = current.next;
    }
    return stores;
  }

  /** Returns a string representation of the list contents. */
  @Override
  public String toString() {
    if (head == null) {
      return "[]";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    ListNode current = head;
    while (current != null) {
      sb.append(current.store.toString());
      if (current.next != null) {
        sb.append(", ");
      }
      current = current.next;
    }
    sb.append("]");
    return sb.toString();
  }
}
