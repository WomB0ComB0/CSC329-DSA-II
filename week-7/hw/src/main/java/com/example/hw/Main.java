package com.example.hw;

import java.io.StringReader;
import java.util.Scanner;

public class Main {

  /**
   * Simulates reading from an input file based on the specified format and populates the hash
   * table.
   *
   * @param data The string containing the simulated file content (Owner \n Sales).
   * @return A populated HashTable.
   */
  public static HashTable readAndPopulateHashTable(String data) {
    HashTable hashTable = new HashTable();
    // Use StringReader to treat the string as a file input stream
    Scanner scanner = new Scanner(new StringReader(data));

    System.out.println("--- Reading Input and Populating Hash Table ---");

    while (scanner.hasNextLine()) {
      try {
        String owner = scanner.nextLine().trim();

        if (owner.isEmpty() && !scanner.hasNextLine()) {
          break;
        }

        String salesLine = scanner.nextLine().trim();
        double sales = Double.parseDouble(salesLine);

        Store newStore = new Store(owner, sales);

        System.out.println("Adding: " + newStore);
        hashTable.add(newStore);

      } catch (Exception e) {
        System.err.println("Error reading data. Check input format.");
        break;
      }
    }

    scanner.close();
    System.out.println("Population complete.");
    return hashTable;
  }

  public static void main(String[] args) {
    // Part 4: Input File Format Simulation
    // This input data is designed to force a resize:
    // Initial size: 4. LF > 0.75 check at 4th element (4/4 = 1.0 > 0.75). Resizes to 8.
    // New size: 8. LF > 0.75 check at 7th element (7/8 = 0.875 > 0.75). Resizes to 16.
    String inputData =
        "Walmart\n"
            + "1000.50\n"
            + "Target\n"
            + "850.25\n"
            + "Costco\n"
            + "1200.00\n"
            + "Kroger\n"
            + "500.75\n"
            + // Store #4: Triggers first resize (4 -> 8)
            "Publix\n"
            + "700.00\n"
            + "Wegmans\n"
            + "950.00\n"
            + "Aldi\n"
            + "400.00\n"
            + // Store #7: Triggers second resize (8 -> 16)
            "Lidl\n"
            + "300.00";

    // 1. Read data and populate the hash table
    HashTable mainHashTable = readAndPopulateHashTable(inputData);

    // 2. Print all data in the hash table
    System.out.println("\n*** HASH TABLE AFTER FULL POPULATION (mainHashTable) ***");
    mainHashTable.show();

    // 3. Demonstrate HashTable methods:

    // a. Copy constructor (DEEP COPY)
    System.out.println("\n*** DEMONSTRATING COPY CONSTRUCTOR (DEEP COPY) ***");
    HashTable copiedHashTable = new HashTable(mainHashTable);
    System.out.println("Copied Table (before modification):");
    copiedHashTable.show();

    // Test deep copy by modifying the *copied* table
    Store newStore = new Store("CopyTestStore", 999.99);
    copiedHashTable.add(newStore);
    System.out.println("\nModifying Copied Table (Adding 'CopyTestStore')...");
    System.out.println("Original Table (should be unchanged):");
    mainHashTable.show();
    System.out.println("Copied Table (should have 'CopyTestStore'):");
    copiedHashTable.show();

    // b. copyInstance() (DEEP COPY)
    System.out.println("\n*** DEMONSTRATING copyInstance() (DEEP COPY) ***");
    HashTable instanceCopy = mainHashTable.copyInstance();
    System.out.println("Instance Copy Table (before modification):");
    instanceCopy.show();

    // Test deep copy by modifying the *instance copy* table
    Store instanceTestStore = new Store("InstanceTestStore", 111.11);
    instanceCopy.add(instanceTestStore);
    System.out.println("\nModifying Instance Copy Table (Adding 'InstanceTestStore')...");
    System.out.println("Original Table (should be unchanged):");
    mainHashTable.show();
    System.out.println("Instance Copy Table (should have 'InstanceTestStore'):");
    instanceCopy.show();

    // c. find(String owner)
    System.out.println("\n*** DEMONSTRATING find(String owner) ***");
    String findOwner1 = "Target";
    Store foundStore1 = mainHashTable.find(findOwner1);

    if (foundStore1 != null) {
      System.out.println("Successfully found Store for " + findOwner1 + ": " + foundStore1);

      // Test for deep copy protection: Modify the returned Store object
      double originalSales = foundStore1.getSales();
      foundStore1.setSales(0.00); // Modifying the returned deep copy

      Store checkStore = mainHashTable.find(findOwner1); // Find again to check internal state
      System.out.println("\nChecking Deep Copy Protection:");
      System.out.println("  Modified returned object's sales is now: " + foundStore1.getSales());
      System.out.println(
          "  Original store sales in table should be "
              + originalSales
              + " and is: "
              + checkStore.getSales());
    } else {
      System.out.println("Store for " + findOwner1 + " not found.");
    }

    String findOwner2 = "NonExistentStore";
    Store foundStore2 = mainHashTable.find(findOwner2);
    if (foundStore2 == null) {
      System.out.println("\nStore for " + findOwner2 + " correctly returned null.");
    }
  }
}
