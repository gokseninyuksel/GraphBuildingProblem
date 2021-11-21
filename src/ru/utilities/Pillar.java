package ru.utilities;

import java.util.Objects;

/**
 * Class to represent pillar.
 */
public class Pillar implements Comparable<Pillar> {
    private final int x;
    private final int y;
    public Pillar(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    @Override
    public String toString() {
        return "x:" + x +
                ", y:" + y ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pillar pillar = (Pillar) o;
        return x == pillar.x && y == pillar.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Pillar o) {
        return Integer.compare(o.getY(), this.getY());
    }
}
