package ru.algorithms;

import ru.graph.Pair;
import ru.graph.Vertex;
import ru.utilities.CircleType;
import ru.utilities.Pillar;

import java.util.HashMap;

public class Factory {

    public static Vertex createVertex(Pillar pillar, CircleType type){
        Pair<Pillar,CircleType> pair = new Pair<>(pillar,type);
        return new Vertex(pair);
    }


}
