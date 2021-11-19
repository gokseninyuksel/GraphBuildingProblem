package ru.algorithms;

import ru.graph.Graph;
import ru.graph.Pair;
import ru.graph.Vertex;
import ru.problem.Problem;
import ru.problem.ProblemState;
import ru.utilities.*;

import java.util.*;

public class GraphBuildingSmall implements GraphBuilder {
    private final Graph graph;
    private final Problem problem;
    private final Vertex starting_vertex;
    private final Vertex end_vertex;
    public GraphBuildingSmall(){
        this.graph = new Graph();
        this.problem = Problem.getInstance();
        this.starting_vertex = new Vertex(new Pair<>(new StartingPillar(), new NoCircle()));
        this.end_vertex = new Vertex(new Pair<>(new EndPillar(), new NoCircle()));
    }
    public void build() {
        List<Pillar> pillars = problem.getPillars();
        List<CircleType> circles = problem.getCircles();
        pillars.sort(Comparator.comparingInt(Pillar::getY));
        ArrayList<Vertex> vertices = new ArrayList<>();
        init_graph(pillars, circles, vertices);
        this.graph.addVertex(end_vertex);
        this.graph.addVertex(this.starting_vertex);
        for (Vertex root : vertices) {
            for (Vertex other : vertices) {
                if (ProblemState.touchesY(root))
                    graph.addEdge(starting_vertex, root, root.getCost());
                if (ProblemState.isSolution(root, problem.getW()))
                    graph.addEdge(root, end_vertex, 0);
                if (!root.getPillar().equals(other.getPillar()) && ProblemState.touches(root, other))
                    graph.addEdge(root, other, other.getCost());
            }
        }

    }

    private void init_graph(List<Pillar> pillars, List<CircleType> circles, ArrayList<Vertex> vertices) {
        for (Pillar pillar : pillars) {
            for (CircleType circle : circles) {
                Vertex v = Factory.createVertex(pillar,circle);
                this.graph.addVertex(v);
                vertices.add(v);
            }
        }
    }

    public void run(){
        if(graph.pathExists(this.starting_vertex, this.end_vertex)) {
            Dijkstras.run(graph, starting_vertex);
        }
    }

    public Vertex lowestCost(){
      return end_vertex;
    }

}
