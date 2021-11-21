package ru.graph;

import ru.utilities.CircleType;
import ru.utilities.Pillar;

import java.util.Objects;

/**
 * Vertex class to represent the vertices of the graph. Which is tuple of pillar and circleType.
 */
public class Vertex{
    private final Pair<Pillar, CircleType> state;
    private int distance;
    private boolean visited = false;

    public Vertex(Pair<Pillar, CircleType> state){
        this.state = state;
    }
    public Pillar getPillar(){
        return state.getK();
    }
    public CircleType getCircleType(){
        return state.getT();
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }
    public int getDistance() {
        return distance;
    }
    @Override
    public String toString() {
        return
                 state.toString() + " " + this.hashCode()  + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return vertex.state.equals(state);

    }
    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
    public void visit() {
        this.visited = true;
    }
    public boolean isNotVisited() {
        return !this.visited;
    }
    public int getCost() {
        return this.state.getT().getCost();
    }

    public void setParent(Vertex parent) {
    }

}
