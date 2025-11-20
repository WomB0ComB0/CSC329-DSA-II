package com.example.lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Solves the "Algorithms Lab – Graph – Shortest Path (Dijkstra)" assignment.
 * This class performs a step-by-step trace of Dijkstra's algorithm on five different graphs,
 * showing the state of the distance and previous arrays after each vertex is visited.
 *
 * @author Arthur Hoskey (Modeled by AI)
 * @version 2023
 */
public class DijkstraLab {

    // A constant to represent infinity, used for initializing distances.
    private static final int INFINITY = Integer.MAX_VALUE;

    /**
     * A simple inner class to represent a weighted edge in a graph.
     * This helps in organizing the edge data for each problem.
     */
    private static class Edge {
        int source;
        int destination;
        int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    /**
     * Main method to run the Dijkstra trace for all parts of the lab.
     */
    public static void main(String[] args) {
        // --- Part 1 ---
        int[] vertices1 = {0, 1, 2};
        List<Edge> edges1 = Arrays.asList(
                new Edge(0, 1, 1),
                new Edge(0, 2, 4),
                new Edge(1, 2, 2)
        );
        runDijkstra("Part 1", vertices1, edges1);

        // --- Part 2 ---
        int[] vertices2 = {0, 1, 2, 3};
        List<Edge> edges2 = Arrays.asList(
                new Edge(0, 1, 2),
                new Edge(0, 2, 5),
                new Edge(1, 2, 2),
                new Edge(1, 3, 6)
        );
        runDijkstra("Part 2", vertices2, edges2);

        // --- Part 3 ---
        int[] vertices3 = {0, 1, 2, 3, 4};
        List<Edge> edges3 = Arrays.asList(
                new Edge(0, 1, 1),
                new Edge(0, 2, 3),
                new Edge(1, 3, 2),
                new Edge(1, 4, 6),
                new Edge(2, 3, 1),
                new Edge(3, 4, 2)
        );
        runDijkstra("Part 3", vertices3, edges3);

        // --- Part 4 ---
        int[] vertices4 = {0, 1, 2, 3, 4, 5};
        List<Edge> edges4 = Arrays.asList(
                new Edge(0, 1, 5),
                new Edge(0, 2, 1),
                new Edge(1, 3, 6),
                new Edge(2, 3, 1),
                new Edge(2, 4, 4),
                new Edge(3, 4, 3),
                new Edge(3, 5, 7),
                new Edge(4, 5, 4)
        );
        runDijkstra("Part 4", vertices4, edges4);


        // --- Part 5 ---
        int[] vertices5 = {0, 1, 2, 3, 4, 5};
        List<Edge> edges5 = Arrays.asList(
                new Edge(0, 1, 5),
                new Edge(0, 2, 1),
                new Edge(1, 3, 1),
                new Edge(1, 4, 1),
                new Edge(2, 3, 5),
                new Edge(2, 4, 1),
                new Edge(3, 5, 4),
                new Edge(4, 5, 5)
        );
        runDijkstra("Part 5", vertices5, edges5);
    }

    /**
     * Executes Dijkstra's algorithm on a given graph and prints the step-by-step results.
     *
     * @param partName The name of the lab part (e.g., "Part 1").
     * @param vertices An array of the graph's vertices.
     * @param allEdges A list of all edges in the graph.
     */
    public static void runDijkstra(String partName, int[] vertices, List<Edge> allEdges) {
        System.out.println("=================================================");
        System.out.println(partName + " - Dijkstra's Algorithm Trace");
        System.out.println("=================================================");
        System.out.println("Starting Vertex: 0\n");

        // Determine the size needed for arrays based on the max vertex label.
        int maxVertex = 0;
        for (int v : vertices) {
            if (v > maxVertex) {
                maxVertex = v;
            }
        }
        int numVertices = maxVertex + 1;

        // --- Step 1: Initialization ---
        int[] distance = new int[numVertices];
        int[] previous = new int[numVertices];
        List<Integer> unvisited = new ArrayList<>();

        for (int v : vertices) {
            distance[v] = INFINITY; // Set distance to infinity
            previous[v] = -1;      // Set previous to undefined (-1)
            unvisited.add(v);      // Add vertex to the unvisited list
        }

        // The distance from the starting vertex (0) to itself is 0.
        distance[0] = 0;

        System.out.println("Initialization:");
        printState(vertices, distance, previous);

        // --- Step 2: Main Loop ---
        // The algorithm continues until all vertices have been visited.
        while (!unvisited.isEmpty()) {
            // Find the unvisited vertex with the smallest distance.
            int currentVertex = -1;
            int minDistance = INFINITY;
            for (int v : unvisited) {
                if (distance[v] <= minDistance) {
                    minDistance = distance[v];
                    currentVertex = v;
                }
            }

            // Remove the selected vertex from the unvisited list.
            unvisited.remove(Integer.valueOf(currentVertex));

            // --- Step 3: Relaxation ---
            // For the current vertex, consider all of its unvisited neighbors.
            for (Edge edge : allEdges) {
                int neighbor = -1;
                // Find edges originating from the current vertex
                if (edge.source == currentVertex) {
                    neighbor = edge.destination;
                } else if (edge.destination == currentVertex) {
                    neighbor = edge.source; // For undirected graphs
                }

                // If a valid, unvisited neighbor is found
                if (neighbor != -1 && unvisited.contains(neighbor)) {
                    // Calculate the new potential shorter distance.
                    int newDist = distance[currentVertex] + edge.weight;

                    // If a shorter path is found, update the distance and previous arrays.
                    if (newDist < distance[neighbor]) {
                        distance[neighbor] = newDist;
                        previous[neighbor] = currentVertex;
                    }
                }
            }

            // --- Step 4: Print the state after visiting the vertex ---
            System.out.printf("After Visiting Vertex %d:\n", currentVertex);
            printState(vertices, distance, previous);
        }
        System.out.println("Algorithm Finished for " + partName + ".\n");
    }

    /**
     * A helper method to print the current state of the distance and previous arrays in a formatted table.
     *
     * @param vertices The array of vertices to iterate over for printing.
     * @param distance The current distance array.
     * @param previous The current previous array.
     */
    private static void printState(int[] vertices, int[] distance, int[] previous) {
        // Print header
        System.out.println("+--------+----------+----------+");
        System.out.println("| Vertex | Distance | Previous |");
        System.out.println("+--------+----------+----------+");

        // Print rows for each vertex
        for (int v : vertices) {
            String distStr = (distance[v] == INFINITY) ? "∞" : String.valueOf(distance[v]);
            System.out.printf("|   %d    | %-8s |    %d     |\n", v, distStr, previous[v]);
        }
        System.out.println("+--------+----------+----------+");
        System.out.println(); // Add a newline for better spacing
    }
}