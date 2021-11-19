package ru.problem;

import ru.graph.Vertex;
import ru.utilities.CircleType;
import ru.utilities.Pillar;

public class ProblemState {

    public static boolean touches(Vertex vertex, Vertex vertex1) {
        Pillar pillar_v = vertex.getPillar();
        Pillar pillar = vertex1.getPillar();
        CircleType circle_v = vertex.getCircleType();
        CircleType circle_this = vertex1.getCircleType();
        double distance = Math.sqrt(Math.pow(pillar_v.getX() - pillar.getX(), 2)
                + Math.pow(pillar_v.getY() - pillar.getY(), 2));
        double sum_sq_r = circle_v.getR() + circle_this.getR();
        return distance <= sum_sq_r;
    }

    public static boolean touchesY(Vertex vertex_graph) {
        int pillar_y = vertex_graph.getPillar().getY();
        int circle_y = pillar_y - vertex_graph.getCircleType().getR();
        return circle_y <= 0;
    }

    public static boolean isSolution(Vertex vertex_graph, int W) {
        int pillar_y = vertex_graph.getPillar().getY();
        int circle_y = pillar_y + vertex_graph.getCircleType().getR();
        return circle_y >= W;
    }

    public static double distance(Pillar pillar_v, Pillar pillar) {
        return Math.sqrt(Math.pow(pillar_v.getX() - pillar.getX(), 2)
                + Math.pow(pillar_v.getY() - pillar.getY(), 2));
    }

}
