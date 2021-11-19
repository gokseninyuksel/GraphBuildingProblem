package ru.algorithms;

import ru.graph.Graph;
import ru.graph.Pair;
import ru.graph.Vertex;

import java.util.*;

public class Dijkstras {

    public static void run(Graph graph, Vertex source){

        PriorityQueue<Vertex> queue = new PriorityQueue<>(graph.getEdges().keySet().size(), Comparator.comparingInt(Vertex::getDistance));
        for (Vertex vertex: graph.getVertices()){
            vertex.setDistance(1000000000);
            vertex.setParent(null);
            queue.offer(vertex);
        }
        source.setDistance(0);
        queue.remove(source);
        queue.add(source);
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
