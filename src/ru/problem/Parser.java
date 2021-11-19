package ru.problem;

import ru.algorithms.GraphBuilder;
import ru.algorithms.GraphBuildingSmall;
import ru.algorithms.GraphBuildingLarge;
import ru.algorithms.GraphBuildingLine;

import java.util.Scanner;

public class Parser {
    private final Problem problem;

    public Parser(){
        this.problem = Problem.getInstance();
    }

    public void parse(){
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ");
        int count_N = Integer.parseInt(line[0]);
        int circle_N = Integer.parseInt(line[1]);
        this.problem.setW(Integer.parseInt(line[2]));
        int sum = count_N + circle_N;
        for (int i = 0; i < sum; i++) {
            String[] lines = scanner.nextLine().split(" ");
            if (count_N != 0) {
                this.problem.addPillar(Integer.parseInt(lines[0]), Integer.parseInt(lines[1]));
                count_N--;
            } else {
                this.problem.addCircle(Integer.parseInt(lines[0]), Integer.parseInt(lines[1]));
            }
        }
        scanner.close();
    }
    public void run() {
        GraphBuilder bob;
        if(problem.getCircles().size() <= 50)
            bob = new GraphBuildingSmall();
        else if(problem.getCircles().size() > 50 && problem.getCircles().size() <= 400)
            bob = new GraphBuildingLarge();
        else
            bob = new GraphBuildingLine();
        bob.build();
        bob.run();
        String lowest = bob.lowestCost().isNotVisited() ? "Impossible" : String.valueOf(bob.lowestCost().getDistance());
        System.out.println(lowest);
    }

}
