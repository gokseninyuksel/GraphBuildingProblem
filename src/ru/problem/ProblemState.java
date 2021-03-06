package ru.problem;

import ru.graph.Vertex;
import ru.utilities.CircleType;
import ru.utilities.Pillar;

/**
 * Static class for doing checks on vertices.
 */
public class ProblemState {

    /**
     * Check if vertex and vertex 1 touch to each other.
     * @param vertex Vertex source
     * @param vertex1 Vertex other
     * @return True if they touch
     *         False otherwise.
     */
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

    /**
     * Check if vertex_graph touches to Y.
     * @param vertex_graph Vertex source
     * @return True if vertex_graph touches to Y.
     *         False otherwise.
     */
    public static boolean touchesY(Vertex vertex_graph) {
        int pillar_y = vertex_graph.getPillar().getY();
        int circle_y = pillar_y - vertex_graph.getCircleType().getR();
        return circle_y <= 0;
    }
    /**
     * Check if vertex_graph touches to W.
     * @param vertex_graph Vertex source
     * @param W W from the problem
     * @return True if vertex_graph touches to W.
     *         False otherwise.
     */
    public static boolean isSolution(Vertex vertex_graph, int W) {
        int pillar_y = vertex_graph.getPillar().getY();
        int circle_y = pillar_y + vertex_graph.getCircleType().getR();
        return circle_y >= W;
    }

    /**
     * Calculate the euclidean distance between pillar_v and pillar.
     * @param pillar_v Pillar source
     * @param pillar pillar other
     * @return euclidean distance between pillar_v and pillar.
     */
    public static double distance(Pillar pillar_v, Pillar pillar) {
        return Math.sqrt(Math.pow(pillar_v.getX() - pillar.getX(), 2)
                + Math.pow(pillar_v.getY() - pillar.getY(), 2));
    }

}
