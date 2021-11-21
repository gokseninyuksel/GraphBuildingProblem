package ru.utilities;

/**
 * Algebraic data type to represent starting pillar which is Y = 0.
 */
public class StartingPillar extends Pillar{
    public StartingPillar() {
        super(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }
}
