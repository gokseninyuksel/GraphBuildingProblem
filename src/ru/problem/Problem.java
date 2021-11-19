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


    /**
     * Parse the max circle from the input, parse the max pillar from the input
     */
    private Problem(){
        this.pillars = new ArrayList<>();
        this.circles = new ArrayList<>();
    }

    public static Problem getInstance() {
        if ( problem == null){
            problem = new Problem();
        }
        return problem;
    }

    public void addPillar(int x, int y){
        this.pillars.add(new Pillar(x,y));
    }
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
