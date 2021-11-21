package ru.algorithms;

import ru.graph.Pair;
import ru.graph.Vertex;
import ru.utilities.CircleType;
import ru.utilities.Pillar;

import java.util.HashMap;

public class Factory {

    /**
     * Static factory method to create a vertex from given pillar and circle type.
     * @param pillar Pillar to place circle on
     * @param type circle to place
     * @return new Vertex with pillar and type tuple
     */
    public static Vertex createVertex(Pillar pillar, CircleType type){
        Pair<Pillar,CircleType> pair = new Pair<>(pillar,type);
        return new Vertex(pair);
    }


}
