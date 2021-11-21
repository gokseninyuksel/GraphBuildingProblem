package ru.graph;

import java.util.*;

public class Graph {
    private final Map<Vertex, HashSet<Pair<Vertex, Integer>>> edges;
    private final HashSet<Vertex> vertices;

    public Graph() {
        edges = new HashMap<>();
        vertices = new HashSet<>();
    }

    /**
     * Add a vertex to the graph.
     * @param vertex Vertex to place in the graph
     */
    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
        edges.put(vertex, new HashSet<>());
    }

    /**
     * Add a directed edge from vertex to vertex1, with weight.
     * @param vertex Vertex source
     * @param vertex1 Vertex to
     * @param weight Weight of the edge
     */
    public void addEdge(Vertex vertex, Vertex vertex1, int weight) {
        edges.get(vertex).add(new Pair<>(vertex1, weight));
    }

    /**
     * Get the successors of the vertex
     * @param vertex Vertex source
     * @return Successors of the vertex
     */
    public HashSet<Pair<Vertex, Integer>> getSuc(Vertex vertex) {
        return edges.get(vertex);
    }

    /**
     * Return the edges in the graph.
     * @return HashMap containing Edges.
     */
    public Map<Vertex, HashSet<Pair<Vertex, Integer>>> getEdges() {
        return edges;
    }

    /**
     * Get the vertices of the graph.
     * @return The HashSet containing vertices.
     */
    public HashSet<Vertex> getVertices() {
        return vertices;
    }

    /**
     * Check if a path exists from vertex_now to the vertex searched. Returns true if there is a path.
     * False otherwise.
     * @param vertex_now Vertex source
     * @param searched Vertex searched
     * @return True if there is a path.
     *          False otherwise.
     */
    public boolean pathExists(Vertex vertex_now, Vertex searched){
        return bfs(vertex_now,searched);
    }

    /**
     * Simple bfs algorithm to check if there is a path from vertex_now to searched
     * @param vertex_now Vertex source
     * @param searched Vertex searched
     * @return True if there is a path.
     *          False otherwise.
     */
    private boolean bfs(Vertex vertex_now, Vertex searched) {
        //Initialize a queue
        Queue<Vertex> queue = new LinkedList<>();
        //Visit the source vertex
        vertex_now.visit();
        queue.offer(vertex_now);
        while(!queue.isEmpty()){
            Vertex v = queue.poll();
            //If the pooled vertex is the searched vertex, return true
            if(v.equals(searched)){
                return true;
            }
            //Get the successors of the pooled vertex and offer it to the queue.
            for(Pair<Vertex,Integer> suc: this.getSuc(v)){
                if(suc.getK().isNotVisited()){
                    suc.getK().visit();
                    queue.offer(suc.getK());
                }
            }
        }

        //If nothing matched with the searched, return false.
        return false;
    }


    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (Vertex v : edges.keySet())
        {
            builder.append(v.toString());
            builder.append("\n" + " Vertex ");
            for (HashSet<Pair<Vertex, Integer>> edge: edges.values()){
                builder.append(edge.toString());
            }
        }
        return builder.toString();
    }
}
