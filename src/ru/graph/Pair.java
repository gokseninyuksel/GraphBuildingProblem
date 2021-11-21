package ru.graph;

import java.util.Objects;

/**
 * Simple Pair class to represent a tuple. It is generic, so it can be reusable.
 * @param <K>
 * @param <T>
 */
public class Pair<K,T>  {
    K k;
    T t;
    public Pair(K k , T t){
        this.k = k;
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public K getK() {
        return k;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "k=" + k +
                ", t=" + t +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return this.k.equals(pair.k) && this.t.equals(pair.t);
    }

    @Override
    public int hashCode() {
        return Objects.hash(k, t);
    }
}
