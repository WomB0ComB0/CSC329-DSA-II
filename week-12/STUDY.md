# Graph Algorithms Study Guide

## 1. Graph Basics

### Graph Definition
- **Graph G = (V, E)** where:
    - V(G) = set of vertices (nodes)
    - E(G) = set of edges (connections)

### Types of Graphs
- **Undirected**: Edges have no direction
- **Directed (Digraph)**: Edges have direction from one vertex to another
- **Weighted**: Edges carry numerical values
- **Complete Graph**:
    - Undirected: n(n-1)/2 edges
    - Directed: n(n-1) edges

### Key Terms
- **Adjacent vertices**: Connected by an edge
- **Path**: Sequence of vertices connecting two nodes
- **Degree**: Number of edges connected to a vertex

## 2. Graph Representation: Adjacency List

Each vertex maintains a list of its adjacent vertices/edges.

**Example:**
```
Vertex 0: [(0,1,1), (0,2,3)]
Vertex 1: [(0,1,1), (1,3,2), (1,4,6)]
```

**Implementation**: Use HashMap to map vertices to LinkedLists of edges

---

## 3. Depth-First Search (DFS)

### Concept
Goes deep into one branch before exploring others (uses STACK)

### Algorithm Steps
1. Clear all marks (set all vertices to unvisited)
2. Push start vertex onto stack
3. While stack not empty AND not found:
    - Pop vertex from stack
    - If it's the destination: done!
    - Else: mark as visited, write vertex
    - Push all UNMARKED adjacent vertices onto stack

### Key Points
- **Data Structure**: Stack (LIFO)
- **Marking is crucial**: Prevents infinite loops between vertices
- **Not optimal**: May follow poor paths
- **Time Complexity**: O(n + m) where n = vertices, m = edges

### Example Trace
```
Graph: 0→1, 0→3, 3→4, 4→5, 5→2
Start: 0, Destination: 2
Order visited: 0, 3, 4, 5, 2
```

---

## 4. Breadth-First Search (BFS)

### Concept
Explores all neighbors at current level before going deeper (uses QUEUE)

### Algorithm Steps
1. Clear all marks
2. Enqueue start vertex
3. While queue not empty AND not found:
    - Dequeue vertex
    - If it's the destination: done!
    - Else: mark as visited, write vertex
    - Enqueue all UNMARKED adjacent vertices

### Key Points
- **Data Structure**: Queue (FIFO)
- **Better for shortest paths**: Explores level by level
- **Time Complexity**: O(n + m)

### BFS vs DFS
- **Only difference**: Stack vs Queue
- BFS finds shorter paths in unweighted graphs
- DFS uses less memory in deep graphs

---

## 5. Connected Components

### Concept
Divides graph into pieces where vertices in same component can reach each other

### Algorithm
```
Initialize all vertices as unvisited
Set numComponents = 0
For each vertex v:
    If v is unvisited:
        Increment numComponents
        Do BFS from v (marks all reachable vertices)
```

### Key Points
- Uses BFS to explore each component
- **Time Complexity**: O(n + m)
- Minimum components: 1 (fully connected)
- Maximum components: n (no edges)

### Strongly Connected Components (Directed Graphs)
Two vertices are in same component if paths exist in BOTH directions

---

## 6. Minimum Spanning Tree (Prim's Algorithm)

### Concept
Find subgraph connecting all vertices with minimum total edge weight (no cycles)

### MST Properties
- Connected
- Acyclic
- Weighted
- All vertices included
- Minimum total weight

### Prim's Algorithm (Greedy)
1. Add all vertices to MST (initially no edges)
2. Pick starting vertex, mark as visited
3. While unvisited vertices exist:
    - Find minimum weight edge from visited to unvisited vertex
    - Add edge to MST
    - Mark destination vertex as visited

### Key Points
- **Greedy**: Always chooses lowest weight edge
- **Time Complexity**: O(n²) for simple version
    - Can improve to O((m+n) log n) with binary heap
- **Application**: Connecting houses/cities with minimum cable

### Example
```
Graph: 0-1(1), 0-2(3), 1-3(2), 2-3(1), 3-4(2)
Start: 0
MST edges: (0,1), (1,3), (2,3), (3,4)
Total weight: 6
```

---

## 7. Shortest Path (Dijkstra's Algorithm)

### Concept
Find shortest path from starting vertex to every other vertex (single-source)

### Algorithm Steps
1. Set all distances to ∞, starting vertex to 0
2. Set all vertices as unvisited
3. While unvisited vertices exist:
    - Select unvisited vertex with minimum distance (currV)
    - Mark currV as visited
    - For each unvisited neighbor n:
        - Calculate possibleDist = dist[currV] + edge weight
        - If possibleDist < dist[n]: update dist[n]

### Key Concepts
- **Relaxation**: Progressively improve distance estimates
- **Previous array**: Tracks path (which vertex to follow back to start)
- **Time Complexity**: O(n²) for simple version
    - Can improve to O((m+n) log n) with binary heap

### Important Notes
- Only calculates distances from ONE starting vertex
- Different from MST: minimizes each path individually, not total weight
- Works with disconnected graphs (unvisited vertices stay at ∞)

### Example Trace
```
Graph: 0-1(1), 1-2(1), 0-2(3), 1-3(2), 3-4(2)
Start: 0

Initial: [0, ∞, ∞, ∞, ∞]
After 0: [0, 1, 3, ∞, ∞]
After 1: [0, 1, 2, 3, ∞]
After 2: [0, 1, 2, 3, ∞]
After 3: [0, 1, 2, 3, 5]
Final:   [0, 1, 2, 3, 5]
```

---

## 8. Key Differences

### DFS vs BFS
- DFS: Stack, goes deep, may find poor paths
- BFS: Queue, level by level, better for shortest paths

### MST vs Shortest Path
- **MST**: Minimizes total weight of tree
- **Shortest Path**: Minimizes individual paths from source
- MST paths to vertices may NOT be shortest

### Connected Components vs Search
- Connected Components: Groups vertices, uses BFS
- Search: Finds path between two specific vertices

---

## Study Tips

1. **Practice tracing**: Work through examples step-by-step
2. **Draw graphs**: Visualize algorithms in action
3. **Compare algorithms**: Understand when to use each
4. **Remember data structures**: Stack=DFS, Queue=BFS
5. **Know complexities**: All main algorithms are O(n+m) or O(n²)
6. **Mark visited vertices**: Critical for preventing infinite loops

## Common Mistakes to Avoid

❌ Forgetting to mark vertices as visited
❌ Confusing MST with shortest path
❌ Using wrong data structure (stack vs queue)
❌ Not understanding greedy approach in Prim's
❌ Forgetting relaxation in Dijkstra's
❌ Confusing directed vs undirected graphs

---

## Quick Reference

| Algorithm | Data Structure | Time Complexity | Use Case |
|-----------|---------------|-----------------|----------|
| DFS | Stack | O(n + m) | Path finding, deep exploration |
| BFS | Queue | O(n + m) | Shortest path (unweighted), level order |
| Connected Components | BFS | O(n + m) | Find graph pieces |
| Prim's MST | Priority concepts | O(n²) simple | Minimum spanning tree |
| Dijkstra | Priority concepts | O(n²) simple | Shortest paths (weighted) |