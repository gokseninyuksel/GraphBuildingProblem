package ru.algorithms;

import ru.graph.Graph;
import ru.graph.Pair;
import ru.graph.Vertex;
import ru.problem.Problem;
import ru.problem.ProblemState;
import ru.utilities.*;

import java.util.*;

public class GraphBuildingLine implements GraphBuilder{
    private final Graph graph;
    private final Problem problem;
    private final Vertex starting_vertex;
    private final Vertex end_vertex;
    private CircleType circle;
    HashMap<Pair<Pillar, CircleType>, Vertex> map;
    HashMap<Vertex, Pillar> connects;
    public GraphBuildingLine(){
        this.graph = new Graph();
        this.problem = Problem.getInstance();
        this.starting_vertex = new Vertex(new Pair<>(new StartingPillar(), new NoCircle()));
        this.end_vertex = new Vertex(new Pair<>(new  EndPillar(), new NoCircle()));
        this.graph.addVertex(this.starting_vertex);
        this.graph.addVertex(this.end_vertex);
        this.map = new LinkedHashMap<>();
        this.connects = new LinkedHashMap<>();
    }
    @Override
    public void build() {
        final List<Pillar> pillarList = problem.getPillars();
        final List<CircleType> circleTypes = problem.getCircles();
        pillarList.sort(Comparator.comparingInt(Pillar::getY));
        circleTypes.sort((o1, o2) -> -Integer.compare(o1.getR(), o2.getR()));
        final long start = System.nanoTime();
        initGraph(pillarList, circleTypes);
        createDownEdges(pillarList,circleTypes);
        circle = circleTypes.get(circleTypes.size() - 1);
        for(Pillar pillar: pillarList){
            for(CircleType circle: circleTypes) {
            }
            }

    }

    private Pair<Integer, CircleType> pointerSearch(int pointer_pillar, double searched, List<CircleType> circleTypes) {
        for(int a = pointer_pillar; a >=  0; a--){
            if(circleTypes.get(a).getR() >= searched){
                return new Pair<>(a, circleTypes.get(a));
            }
        }
        return null;


    }
    private Pillar binarySearch(List<Pillar> pillarList, int key, Vertex searched, List<CircleType> circleTypes){

        if (key <= pillarList.get(0).getY())
            return null;

        if (key >= pillarList.get(pillarList.size() - 1).getY())
            return pillarList.get(pillarList.size() - 1);

        // Start binary search algorithm
        int start = 0;
        int end = pillarList.size() - 1;
        int mid = 0;

        // Keep dividing array till further division is not possible
        while (end - start != 1) {
            // Calculate middle index
            mid = (start + end) / 2;
            // Check if middle element is equal to target
            if (key  == pillarList.get(mid).getY())
                // If yes return middle element as middle element = target
                return pillarList.get(mid);
            // Check if target is smaller to middle element. If yes, take first half of array including mid
            if (key  < pillarList.get(mid).getY())
                end = mid;
            // Check if target is greater to middle element. If yes, take first half of array including mid
            if (key  > pillarList.get(mid).getY())
                start = mid;
        }
        // Now you will have only two numbers in array in which target number will fall
        return pillarList.get(start);
//

    }

    private Pillar search(List<Pillar> pillarList, int key, int low, int high, Vertex searched) {
        int middle = low  + ((high - low) / 2);
        if (key >= pillarList.get(pillarList.size() - 1).getY()){
            return pillarList.get(pillarList.size() -1);
        }
        if(high == 0){
            return pillarList.get(high + 1);
        }
        else if(high <= low){
            return pillarList.get(low - 1);
        }
        if(key == pillarList.get(middle).getY()){
            return pillarList.get(middle);
        }
        else if(key < pillarList.get(middle).getY()){
            return search(pillarList,key,low, middle - 1, searched);
        }
        else{
            return search(pillarList,key,middle + 1, high, searched);
        }
    }

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
    private void createDownEdges(List<Pillar> pillarList, List<CircleType> circleTypes) {
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
        if(graph.pathExists(starting_vertex,end_vertex)){
            Dijkstras.run(graph,starting_vertex);
        }
    }

    @Override
    public Vertex lowestCost() {
        return this.end_vertex;
    }
}
