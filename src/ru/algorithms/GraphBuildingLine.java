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
    private Map<Pillar, List<Vertex>> layers;
    ArrayList<Vertex> vertices;
    HashMap<Pair<Pillar, CircleType>, Vertex> map;

    public GraphBuildingLine(){
        this.graph = new Graph();
        this.problem = Problem.getInstance();
        this.starting_vertex = new Vertex(new Pair<>(new StartingPillar(), new NoCircle()));
        this.end_vertex = new Vertex(new Pair<>(new  EndPillar(), new NoCircle()));
        this.graph.addVertex(this.starting_vertex);
        this.graph.addVertex(this.end_vertex);
        this.map = new LinkedHashMap<>();
    }
    @Override
    public void build() {
        final List<Pillar> pillarList = problem.getPillars();
        final List<CircleType> circleTypes = problem.getCircles();
        pillarList.sort(Comparator.comparingInt(Pillar::getY));
        circleTypes.sort((o1, o2) -> {

            if(o1.getR() == o2.getR()){
                return -Integer.compare(o1.getCost(),o2.getCost());
            }
            return -Integer.compare(o1.getR(), o2.getR());

        });
        final long start = System.nanoTime();
        initGraph(pillarList, circleTypes);
        createDownEdges(pillarList,circleTypes);
        CircleType highest_circle = circleTypes.get(0);
        for(int index = 0; index < pillarList.size() - 1; index++){
            int highest = pillarList.size() -1;
            Pillar to = pillarList.get(index);
            int pointer_pillar = circleTypes.size() - 1;
            int value = to.getY() + 2 * highest_circle.getR();
            Pair<Integer, Pillar> canConnect = binarySearch(value, pillarList, highest, index + 1);
            for(CircleType circleType: circleTypes) {
                if (canConnect != null) {
                    double searched = ProblemState.distance(to, canConnect.getT()) - circleType.getR();
                    Pair<Integer, CircleType> canTouch = pointerSearch(pointer_pillar, searched, circleTypes);
                    if (canTouch != null) {
                        Vertex fromTouches = map.get(new Pair<>(canConnect.getT(), canTouch.getT()));
                        Vertex to_Vertex = map.get(new Pair<>(to, circleType));
                        graph.addEdge(to_Vertex, fromTouches, canTouch.getT().getCost());
                        pointer_pillar = canTouch.getK();
                    } else {
                        break;
                    }
                } else {
                    break;
                }
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

    private Pair<Integer,Pillar> binarySearch(int key, List<Pillar> pillars, int highest, int index) {
        if(index == highest){
            return new Pair<>(index,pillars.get(index));
        }
        return search(key, pillars, index, highest, index);
    }


    public Pair<Integer,Pillar> search(int key, List<Pillar> pillars, int low, int high, int index) {
        int middle = low  + ((high - low) / 2);

        if(key < pillars.get(index).getY()) {
            return null;
        }
        if(key > pillars.get(pillars.size() - 1).getY()){
            return new Pair<>(pillars.size() - 1, pillars.get(pillars.size() - 1));
        }
        if (high <= low)
            return returns(pillars,high,key);
        else if (key == pillars.get(middle).getY())
            return new Pair<>(middle,pillars.get(middle));
        else if (key > pillars.get(middle).getY())
            return search(key, pillars, middle + 1, high, index);
        else
            return search(key,pillars,low, middle, index);

    }

    private Pair<Integer, Pillar> returns(List<Pillar> pillars, int high, int value) {
        return new Pair<>(high - 1,pillars.get(high - 1));
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
