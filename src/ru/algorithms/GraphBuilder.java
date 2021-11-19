package ru.algorithms;

import ru.graph.Vertex;

public interface GraphBuilder {
    void build();
    void run();
    Vertex lowestCost();
}
