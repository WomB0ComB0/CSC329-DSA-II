package com.example.hw;

/**
 * Represents an edge in an undirected weighted graph.
 * Connects vertex v1 and v2 with a given weight.
 */
public class Edge {
    // Package-private scope as per assignment instructions
    int v1;
    int v2;
    int weight;

    /**
     * Constructor to create an edge.
     * @param v1 The first vertex.
     * @param v2 The second vertex.
     * @param weight The weight of the edge.
     */
    public Edge(int v1, int v2, int weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %d)", v1, v2, weight);
    }
}
