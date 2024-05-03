import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Create a map instance and parse data from the input file
            MapGrid map;
            try {
                map = new MapGrid();
                map.parseFromFile("benchmark_series/benchmark_series/puzzle_160.txt");
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + e.getMessage());
                return; // Exit the program if the file is not found
            }

            // Find start and finish cells
            Cell start = map.findStartCell();
            Cell finish = map.findFinishCell();

            // If start and finish cells are found, find the shortest path and print the solution
            if (start != null && finish != null) {
                List<Cell> path = map.findShortestPath(start, finish);
                map.printSolution(path);
            } else {
                System.out.println("Start or finish cell not found.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

    }
}
