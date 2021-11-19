package ru.graph;

import java.util.*;

public class Graph {
    private final Map<Vertex, HashSet<Pair<Vertex, Integer>>> edges;
    private final HashSet<Vertex> vertices;

    public Graph() {
        edges = new HashMap<>();
        vertices = new HashSet<>();
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
        edges.put(vertex, new HashSet<>());
    }

    public void addEdge(Vertex vertex, Vertex vertex1, int weight) {
        edges.get(vertex).add(new Pair<>(vertex1, weight));
    }

    public HashSet<Pair<Vertex, Integer>> getSuc(Vertex vertex) {
        return edges.get(vertex);
    }

    public Map<Vertex, HashSet<Pair<Vertex, Integer>>> getEdges() {
        return edges;
    }

    public HashSet<Vertex> getVertices() {
        return vertices;
    }
    public boolean pathExists(Vertex vertex_now, Vertex searched){
        return bfs(vertex_now,searched);
    }

    private boolean bfs(Vertex vertex_now, Vertex searched) {
        Queue<Vertex> queue = new LinkedList<>();
        vertex_now.visit();
        queue.offer(vertex_now);
        while(!queue.isEmpty()){
            Vertex v = queue.poll();
            if(v.equals(searched)){
                return true;
            }
            for(Pair<Vertex,Integer> suc: this.getSuc(v)){
                if(suc.getK().isNotVisited()){
                    suc.getK().visit();
                    queue.offer(suc.getK());
                }
            }
        }
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
