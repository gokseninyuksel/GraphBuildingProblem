package ru.algorithms;

import ru.graph.Graph;
import ru.graph.Pair;
import ru.graph.Vertex;
import ru.problem.Problem;
import ru.problem.ProblemState;
import ru.utilities.*;

import java.util.*;

public class GraphBuildingLarge implements GraphBuilder{
    private final Graph graph;
    private final Problem problem;
    private final Vertex starting_vertex;
    private final Vertex end_vertex;
    //Map to retrive vertex object from pillar and circleType
    private final HashMap<Pair<Pillar,CircleType>, Vertex> map;

    public GraphBuildingLarge(){
        this.graph = new Graph();
        this.problem = Problem.getInstance();
        this.starting_vertex = new Vertex(new Pair<>(new StartingPillar(), new NoCircle()));
        this.end_vertex = new Vertex(new Pair<>(new EndPillar(), new NoCircle()));
        this.graph.addVertex(this.starting_vertex);
        this.graph.addVertex(this.end_vertex);
        this.map = new LinkedHashMap<>();
    }
    /**
     * Creating the graph from the small problem.
     */
    public void build() {
        final List<Pillar> pillarList = problem.getPillars();
        final List<CircleType> circleTypes = problem.getCircles();

        //Sort the circleTypes depending on R in descending order.
        circleTypes.sort((o1, o2) -> -Integer.compare(o1.getR(), o2.getR()));

        //Initialize the graph.
        initGraph(pillarList, circleTypes);

        //Create the up edges
        createUpEdges(pillarList, circleTypes);
        for(Pillar from: pillarList){
            for(Pillar to: pillarList ){
                //Initialize a pointer that points to the smallest circle.
                int pointer_pillar = circleTypes.size() - 1;
                for(CircleType circleType: circleTypes) {
                    Vertex vertexFrom = map.get(new Pair<>(from, circleType));
                    if (!from.equals(to)) {
                        //Initialize a key that we are searching by using the distance formula
                        double searched = ProblemState.distance(from, to) - circleType.getR();
                        Pair<Integer,CircleType> canTouch = pointerSearch(pointer_pillar,searched,circleTypes);
                        if (canTouch != null ) {
                            Vertex fromTouches = map.get(new Pair<>(to, canTouch.getT()));
                            graph.addEdge(vertexFrom, fromTouches, canTouch.getT().getCost());
                            //Pointer becomes the index that we found the circle.
                            pointer_pillar = canTouch.getK();
                        }
                        else{
                            break;
                        }

                    }
                }
            }
        }


    }

    /**
     * Starting from pointer, loop to the head of the list
     * and search for a circle that has bigger or equal radius to the searched.
     * @param pointer Starting index for the search
     * @param searched radius that we are searching for
     * @param circleTypes List of circleTypes that is sorted by R in descending order.
     * @return tuple of found circle and the index that we found the circle.
     */
    private Pair<Integer, CircleType> pointerSearch(int pointer, double searched, List<CircleType> circleTypes) {

        for(int a = pointer; a >=  0; a--){
             if(circleTypes.get(a).getR() >= searched){
                    return new Pair<>(a, circleTypes.get(a));
             }
        }
        return null;
    }

    /**
     * Initialize the vertices for graph by looping through the possible pillars and circles.
     * Add each vertex to the arraylist of vertices.
     * @param pillarList List of pillars from the problem
     * @param circleTypes List of circleTypes that is sorted by R in descending order.
     */
    private void initGraph(List<Pillar> pillarList, List<CircleType> circleTypes) {
        for(Pillar pillar: pillarList){
            for(CircleType circleType: circleTypes){
                Vertex vertex = Factory.createVertex(pillar,circleType);
                Pair<Pillar,CircleType> pair = new Pair<>(pillar,circleType);
                graph.addVertex(vertex);
                map.put(pair,vertex);
                if(ProblemState.touchesY(vertex)){
                    graph.addEdge(starting_vertex,vertex,vertex.getCost());
                }
                if(ProblemState.isSolution(vertex, problem.getW())){
                    graph.addEdge(vertex, this.end_vertex, 0);
                }
            }
        }
    }

    /**
     * Create the upwards edges on the same pillar.
     * @param pillarList List of pillars from the problem
     * @param circleTypes List of circleTypes that is sorted by R in descending order.
     */
    private void createUpEdges(List<Pillar> pillarList, List<CircleType> circleTypes) {
        for (Pillar pillar : pillarList) {
            for (int j = 0; j < circleTypes.size() - 1; j++) {
                Pair<Pillar,CircleType> pair0 = new Pair<>(pillar,circleTypes.get(j));
                Pair<Pillar,CircleType> pair1 = new Pair<>(pillar,circleTypes.get(j + 1));
                Vertex vertex = map.get(pair0);
                Vertex prev_vertex = map.get(pair1);
                graph.addEdge(prev_vertex, vertex, vertex.getCost() - prev_vertex.getCost());
            }
        }
    }

    @Override
    public void run() {
        if(graph.pathExists(this.starting_vertex, this.end_vertex)) {
            Dijkstras.run(graph, starting_vertex);
        }
    }

    @Override
    public Vertex lowestCost() {
        return end_vertex;
    }
}
