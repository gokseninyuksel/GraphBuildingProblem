package ru.algorithms;

import ru.graph.Vertex;

/**
 * Interface for initializing graph building algorithms depending on the
 * input size.
 */
public interface GraphBuilder {
    void build();
    void run();
    Vertex lowestCost();
}
