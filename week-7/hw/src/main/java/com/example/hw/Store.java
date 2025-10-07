package com.example.hw;

import java.util.Objects;

/** Represents a store with an owner and sales data. Used as the data element in the Hash Table. */
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
   * Copy constructor for the Store class. Creates a new Store instance that is a copy of the given
   * store.
   *
   * @param other The Store object to copy.
   */
  public Store(Store other) {
    // Deep copy of primitive/String fields
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
   *
   * @param sales The new sales amount.
   */
  public void setSales(double sales) {
    this.sales = sales;
  }

  /**
   * Generates a hash code for the Store object based ONLY on the owner. This method is used as the
   * hashing transform function in the HashTable. Modulo operation is NOT performed here.
   *
   * @return The hash code value.
   */
  @Override
  public int hashCode() {
    // Use Java's Objects.hash method to generate a hash value based only on the owner.
    return Objects.hash(owner);
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
