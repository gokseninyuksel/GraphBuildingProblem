package ru.utilities;

import java.util.Objects;

public class CircleType {
    private final int cost;
    private final int r;
    public CircleType(int r,int cost){
        this.cost = cost;
        this.r = r;
    }
    public int getCost(){
        return cost;
    }
    public int getR(){
        return r;
    }

    @Override
    public String toString() {
        return "R : " + r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CircleType that = (CircleType) o;
        return cost == that.cost && r == that.r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost, r);
    }
}
