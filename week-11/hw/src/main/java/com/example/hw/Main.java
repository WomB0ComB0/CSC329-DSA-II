package com.example.hw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

    // =========================================================================
    // Part 4 – Connected Components
    // =========================================================================
    /**
     * Calculates the connected components of a graph using Breadth-First Search.
     * Implemented based on the algorithm from the lecture slides.
     * @param g The graph to analyze.
     * @return An integer array where the index represents the vertex and the
     *         value represents its component number.
     */
    public static int[] calculateConnectedComponents(MyGraph g) {
        List<Integer> vertices = g.getVertices();
        int numVertices = 0;
        if (!vertices.isEmpty()) {
            numVertices = Collections.max(vertices) + 1;
        }

        int[] compMap = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        int numComponents = 0;

        for (int v : vertices) {
            if (!visited[v]) {
                numComponents++;
                Queue<Integer> bfsQueue = new LinkedList<>();
                bfsQueue.add(v);
                visited[v] = true;

                while (!bfsQueue.isEmpty()) {
                    int currV = bfsQueue.remove();
                    compMap[currV] = numComponents;

                    for (Edge e : g.getAdjList(currV)) {
                        int neighbor = (e.v1 == currV) ? e.v2 : e.v1;
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            bfsQueue.add(neighbor);
                        }
                    }
                }
            }
        }
        return compMap;
    }

    // =========================================================================
    // Part 5 – Minimum Spanning Tree Methods (Prim's Algorithm)
    // =========================================================================
    /**
     * Gets the edge with the minimum weight on the "frontier" between visited
     * and unvisited vertices. A frontier edge connects a visited vertex to an
     * unvisited one.
     * @param g The graph.
     * @param visited A boolean array indicating which vertices have been visited.
     * @return The minimum weight frontier edge.
     */
    public static Edge getMinFrontierEdge(MyGraph g, boolean[] visited) {
        Edge minEdge = new Edge(-1, -1, Integer.MAX_VALUE);

        for (int v : g.getVertices()) {
            if (visited[v]) { // Only check edges from visited vertices
                for (Edge e : g.getAdjList(v)) {
                    // Check if one vertex is visited and the other is not
                    boolean isV1Visited = visited[e.v1];
                    boolean isV2Visited = visited[e.v2];
                    if (isV1Visited != isV2Visited) { // This is a frontier edge
                        if (e.weight < minEdge.weight) {
                            minEdge = e;
                        }
                    }
                }
            }
        }
        return minEdge;
    }

    /**
     * Generates a Minimum Spanning Tree (MST) for a given graph using Prim's algorithm.
     * @param g The source graph.
     * @param startingVertex The vertex to start building the MST from.
     * @return A new MyGraph instance representing the MST.
     */
    public static MyGraph minimumSpanningTree(MyGraph g, int startingVertex) {
        MyGraph mst = new MyGraph();
        List<Integer> vertices = g.getVertices();
        int numVertices = 0;
        if (!vertices.isEmpty()) {
           numVertices = Collections.max(vertices) + 1;
        }

        boolean[] visited = new boolean[numVertices];
        int visitedCount = 0;

        // Add all vertices to the MST graph structure initially
        for(int v : vertices) {
            mst.addVertex(v);
        }

        // Start with the given starting vertex
        if(vertices.contains(startingVertex)) {
            visited[startingVertex] = true;
            visitedCount++;
        }

        while(visitedCount < vertices.size()) {
            Edge minEdge = getMinFrontierEdge(g, visited);
            if(minEdge.v1 == -1) {
                // No more reachable vertices
                break;
            }

            mst.addEdge(minEdge.v1, minEdge.v2, minEdge.weight);

            // Mark the new vertex as visited
            if (!visited[minEdge.v1]) {
                visited[minEdge.v1] = true;
                visitedCount++;
            }
            if (!visited[minEdge.v2]) {
                visited[minEdge.v2] = true;
                visitedCount++;
            }
        }
        return mst;
    }

    // =========================================================================
    // Part 6 – Shortest Path Methods (Dijkstra's Algorithm)
    // =========================================================================
    /**
     * Searches the list of unvisited vertices for the one with the minimum
     * distance to the starting vertex.
     * @param g The graph.
     * @param unvisitedList A list of vertices yet to be processed.
     * @param dist The array of current shortest distances from the source.
     * @return The unvisited vertex with the minimum distance.
     */
    public static int getMinDistVertex(MyGraph g, List<Integer> unvisitedList, int[] dist) {
        int minDist = Integer.MAX_VALUE;
        int minVertex = -1;

        for (int v : unvisitedList) {
            if (dist[v] < minDist) {
                minDist = dist[v];
                minVertex = v;
            }
        }
        return minVertex;
    }

    /**
     * Calculates the shortest paths from a starting vertex to all other vertices
     * using Dijkstra's algorithm.
     * @param g The graph.
     * @param startingVertex The starting vertex for the paths.
     */
    public static void shortestPath(MyGraph g, int startingVertex) {
        List<Integer> vertices = g.getVertices();
        int numVertices = 0;
        if(!vertices.isEmpty()){
            numVertices = Collections.max(vertices) + 1;
        }
        
        int[] dist = new int[numVertices];
        int[] previous = new int[numVertices];
        List<Integer> unvisitedList = new ArrayList<>();

        // Initialization
        for (int v : vertices) {
            dist[v] = Integer.MAX_VALUE;
            previous[v] = -1;
            unvisitedList.add(v);
        }
        dist[startingVertex] = 0;

        while (!unvisitedList.isEmpty()) {
            int currV = getMinDistVertex(g, unvisitedList, dist);
            
            // If getMinDistVertex returns -1, it means remaining vertices are unreachable
            if (currV == -1) {
                break; 
            }

            unvisitedList.remove(Integer.valueOf(currV));

            for (Edge e : g.getAdjList(currV)) {
                int neighbor = (e.v1 == currV) ? e.v2 : e.v1;
                if (unvisitedList.contains(neighbor)) {
                    int possibleDist = dist[currV] + e.weight;
                    if (possibleDist < dist[neighbor]) {
                        dist[neighbor] = possibleDist;
                        previous[neighbor] = currV;
                    }
                }
            }
        }

        // Print the results as specified
        System.out.println("Shortest Path Data");
        System.out.println("Starting Vertex : " + startingVertex);
        System.out.println();
        System.out.println("Vertex\tDist");
        for(int v : vertices) {
            System.out.println(v + "\t" + dist[v]);
        }
        System.out.println();
        System.out.println("Vertex\tPrevious");
        for(int v : vertices) {
            System.out.println(v + "\t" + previous[v]);
        }
    }


    // =========================================================================
    // Part 7 – Main
    // =========================================================================
    public static void main(String[] args) {
        // --- Connected Components Test ---
        System.out.println("--- Connected Components Test ---");
        MyGraph ccGraph = new MyGraph();
        for (int i = 0; i <= 9; i++) {
            ccGraph.addVertex(i);
        }
        ccGraph.addEdge(0, 2, 2);
        ccGraph.addEdge(1, 3, 1);
        ccGraph.addEdge(2, 5, 6);
        ccGraph.addEdge(3, 6, 1);
        ccGraph.addEdge(4, 5, 2);
        ccGraph.addEdge(4, 7, 2);
        ccGraph.addEdge(5, 8, 1);
        ccGraph.addEdge(6, 9, 1);

        int[] components = calculateConnectedComponents(ccGraph);
        System.out.println("Connected Components");
        System.out.println("Vertex\tComp #");
        for (int i = 0; i < components.length; i++) {
            if(ccGraph.getVertices().contains(i)){
                 System.out.println(i + "\t" + components[i]);
            }
        }
        System.out.println("\n-----------------------------------\n");


        // --- Shortest Path and MST Test ---
        System.out.println("--- Shortest Path and MST Test ---");
        MyGraph spMstGraph = getGraph();

        System.out.println("Source Graph");
        spMstGraph.printGraph();
        System.out.println();
        
        // Call shortestPath
        shortestPath(spMstGraph, 0);
        System.out.println("\n-----------------------------------\n");

        // Call minimumSpanningTree and show the result
        System.out.println("MST Graph");
        MyGraph mstResult = minimumSpanningTree(spMstGraph, 0);
        mstResult.printGraph();
    }

    private static MyGraph getGraph() {
        MyGraph spMstGraph = new MyGraph();
        for (int i = 0; i <= 9; i++) {
            spMstGraph.addVertex(i);
        }
        spMstGraph.addEdge(0, 1, 1);
        spMstGraph.addEdge(0, 2, 15);
        spMstGraph.addEdge(0, 4, 4);
        spMstGraph.addEdge(1, 3, 5);
        spMstGraph.addEdge(2, 4, 1);
        spMstGraph.addEdge(2, 6, 6);
        spMstGraph.addEdge(3, 5, 5);
        spMstGraph.addEdge(3, 6, 1);
        spMstGraph.addEdge(4, 7, 2);
        spMstGraph.addEdge(5, 7, 1);
        spMstGraph.addEdge(5, 8, 8);
        spMstGraph.addEdge(6, 9, 1);
        spMstGraph.addEdge(7, 8, 1);
        spMstGraph.addEdge(8, 9, 1);
        return spMstGraph;
    }
}
