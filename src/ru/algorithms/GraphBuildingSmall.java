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

    /**
     * Creating the graph from the small problem.
     */
    public void build() {
        //Get the pillars and circles from the problem.
        List<Pillar> pillars = problem.getPillars();
        List<CircleType> circles = problem.getCircles();
        //Creating the array of vertices, so we do not need to check for start and end vertex
        //While adding the edges
        ArrayList<Vertex> vertices = new ArrayList<>();

        //Add vertices to the graph
        init_graph(pillars, circles, vertices);
        this.graph.addVertex(end_vertex);
        this.graph.addVertex(this.starting_vertex);


        //Loop through every vertex twice and check if they touch to each other.
        for (Vertex root : vertices) {
            for (Vertex other : vertices) {
                //If root vertex is connected to start
                if (ProblemState.touchesY(root))
                    graph.addEdge(starting_vertex, root, root.getCost());
                //If root vertex is connected to W
                if (ProblemState.isSolution(root, problem.getW()))
                    graph.addEdge(root, end_vertex, 0);

                //If root vertex and other vertex does not have same pillar, check if they connect
                if (!root.getPillar().equals(other.getPillar()) && ProblemState.touches(root, other))
                    graph.addEdge(root, other, other.getCost());
            }
        }

    }

    /**
     * Initialize the vertices for graph by looping through the possible pillars and circles.
     * Add each vertex to the arraylist of vertices.
     * @param pillars Pillar list from the problem
     * @param circles CircleType list from the problem-
     * @param vertices Vertices list
     */
    private void init_graph(List<Pillar> pillars, List<CircleType> circles, ArrayList<Vertex> vertices) {
        for (Pillar pillar : pillars) {
            for (CircleType circle : circles) {
                Vertex v = Factory.createVertex(pillar,circle);
                this.graph.addVertex(v);
                vertices.add(v);
            }
        }
    }

    /**
     * Run the Dijkstra's algorithm starting from the start vertex, which is Y = 0.
     */
    public void run(){
        //If there exist ac path from start to end run dijkstra
        if(graph.pathExists(this.starting_vertex, this.end_vertex)) {
            Dijkstras.run(graph, starting_vertex);
        }
    }

    /**
     * Return the end vertex to check for the cost Y = W
     * @return End vertex
     */
    public Vertex lowestCost(){
      return end_vertex;
    }

}
