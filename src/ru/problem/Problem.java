package ru.problem;

import ru.utilities.CircleType;
import ru.utilities.Pillar;

import java.util.ArrayList;
import java.util.List;

public class Problem {
    private final ArrayList<Pillar> pillars;
    private final ArrayList<CircleType> circles;
    private int W;
    private static Problem problem = null;

    private Problem(){
        this.pillars = new ArrayList<>();
        this.circles = new ArrayList<>();
    }

    /**
     * Singleton design pattern.
     * @return instance of the problem
     */
    public static Problem getInstance() {
        if ( problem == null){
            problem = new Problem();
        }
        return problem;
    }

    /**
     * Add a pillar with x and y coordinates
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void addPillar(int x, int y){
        this.pillars.add(new Pillar(x,y));
    }
    /**
     * Add a circle type with cost and r.
     * @param cost Cost of the circle
     * @param r Radius of the circle
     */
    public void addCircle(int cost,int r){
        this.circles.add(new CircleType(cost,r));
    }
    public void setW(int W){
        this.W = W;
    }
    public int getW(){
        return W;
    }
    public List<Pillar> getPillars(){
        return pillars;
    }
    public List<CircleType> getCircles(){
        return circles;
    }
}
