package com.example.hw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an undirected, weighted graph using an adjacency list implementation.
 */
public class MyGraph {
    // Member variable for the list of vertices
    private final List<Integer> vertices;
    // Member variable for the adjacency lists (map of vertex to list of edges)
    private final Map<Integer, List<Edge>> adjLists;

    /**
     * Default constructor. Initializes the vertex list and adjacency list map.
     */
    public MyGraph() {
        this.vertices = new ArrayList<>();
        this.adjLists = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph.
     * @param v The integer label of the vertex to add.
     */
    public void addVertex(int v) {
        if (!adjLists.containsKey(v)) {
            vertices.add(v);
            Collections.sort(vertices); // Keep vertex list sorted for consistent output
            adjLists.put(v, new ArrayList<>());
        }
    }

    /**
     * Adds an undirected edge between two vertices.
     * @param v1 The first vertex.
     * @param v2 The second vertex.
     * @param weight The weight of the edge.
     */
    public void addEdge(int v1, int v2, int weight) {
        // Ensure vertices exist in the graph before adding an edge
        if (!adjLists.containsKey(v1) || !adjLists.containsKey(v2)) {
            throw new IllegalArgumentException("Both vertices must exist in the graph.");
        }
        Edge edge = new Edge(v1, v2, weight);
        // Add the edge to the adjacency lists of BOTH vertices for an undirected graph
        adjLists.get(v1).add(edge);
        adjLists.get(v2).add(edge);
    }

    // Getter methods to allow other classes to access graph data
    public List<Integer> getVertices() {
        return this.vertices;
    }

    public List<Edge> getAdjList(int v) {
        return this.adjLists.get(v);
    }

    // Utility method to display the graph for verification
    public void printGraph() {
        System.out.println("V = " + vertices.toString().replace("[", "{ ").replace("]", " }"));
        System.out.println("Adj Lists");
        for (int v : vertices) {
            System.out.println(v + ": " + getAdjList(v));
        }
    }
}
