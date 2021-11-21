package ru.algorithms;

import ru.graph.Graph;
import ru.graph.Pair;
import ru.graph.Vertex;

import java.util.*;

public class Dijkstras {
    /**
     * Run the dijkstras algorithm on the specified graph, starting from the source vertex.
     * @param graph Graph to run dijkstra
     * @param source starting vertex
     */
    public static void run(Graph graph, Vertex source){

        //Initialize a priority queue with max size of number of vertices.
        PriorityQueue<Vertex> queue = new PriorityQueue<>(graph.getEdges().keySet().size(), Comparator.comparingInt(Vertex::getDistance));

        //Initialize the conditions for dijkstra's algorithm.
        for (Vertex vertex: graph.getVertices()){
            vertex.setDistance(1000000000);
            vertex.setParent(null);
            queue.offer(vertex);
        }
        //Set the distance of the starting vertex to 0
        source.setDistance(0);

        //Priority Queue implementation is not stable sometimes, so explicitly reduce the key.
        queue.remove(source);
        queue.add(source);

        //Main loop of Dijkstra's algorithm
        while (!queue.isEmpty()){
            Vertex U = queue.poll();
            for(Pair<Vertex, Integer> suc : graph.getSuc(U)){
                int alt = U.getDistance() + suc.getT();
                if (alt < suc.getK().getDistance()) {
                    suc.getK().setDistance(alt);
                    suc.getK().setParent(U);
                    queue.add(suc.getK());
                }
            }
        }
    }
}
