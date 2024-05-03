import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
// Represents the map as a grid of cells
class MapGrid {
    int  height,width ;  // Dimensions of the map
    Cell[][] grid;      // 2D array representing the map
    char[][] mapData;   // Input map data

    MapGrid() {
        // Initialize the map with default size
        height = 0;
        width = 0;
    }

    // Method to parse map data from a file
    void parseFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
            width = Math.max(width, line.length());
            height++;
        }
        scanner.close();

        // Initialize the map grid based on the dimensions
        grid = new Cell[height][width];
        mapData = new char[height][width];

        // Populate the map grid with cell information
        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                mapData[y][x] = c;
                boolean isObstacle = c == '0';
                grid[y][x] = new Cell(x + 1, y + 1, isObstacle);
            }
        }
    }

    // Method to check if a given position is valid (within bounds and not an obstacle)
    boolean isValidPosition(int x, int y) {
        return x >= 1 && x <= width && y >= 1 && y <= height && !grid[y - 1][x - 1].isObstacle;
    }

    // Method to find the start cell in the map
    Cell findStartCell() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (mapData[y][x] == 'S' || mapData[y][x] == 's') {
                    return grid[y][x];
                }
            }
        }
        return null;
    }

    // Method to find the finish cell in the map
    Cell findFinishCell() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (mapData[y][x] == 'F' || mapData[y][x] == 'f') {
                    return grid[y][x];
                }
            }
        }
        return null;
    }

    // Method to find the shortest path using BFS
    List<Cell> findShortestPath(Cell start, Cell finish) {
        HashMap<Cell, Cell> parentMap = new HashMap<>();  // Map to store parent-child relationship
        Queue<Cell> queue = new LinkedList<>();        // Queue for BFS traversal
        Set<Cell> visited = new HashSet<>();           // Set to track visited cells

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            if (current == finish) {
                break;  // Found the finish cell, exit loop
            }

            for (Cell neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        // Reconstruct the path from start to finish
        List<Cell> path = new ArrayList<>();
        for (Cell current = finish; current != null; current = parentMap.get(current)) {
            path.add(current);
        }
        Collections.reverse(path);
        return path;

    }

    // Method to get neighbors of a given cell
    List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int[] dx = {1, 0, 0, -1}; // Directions: right, down, up, left
        int[] dy = {0, 1, -1, 0};

        for (int i = 0; i < 4; i++) {
            int nx = cell.A;
            int ny = cell.B;

            // Slide in the current direction until hitting a wall or obstacle
            while (isValidPosition(nx + dx[i], ny + dy[i])) {
                nx += dx[i];
                ny += dy[i];
                if (mapData[ny - 1][nx - 1] == '.') {
                    // If the next cell is an ice tile, continue sliding in the same direction
                    continue;
                } else {
                    break;             // If the next cell is not an ice tile, break the loop
                }
            }

            // Add the final valid position as a neighbor
            if (!(nx == cell.A && ny == cell.B)) { // Exclude current cell
                neighbors.add(grid[ny - 1][nx - 1]);
            }
        }
        return neighbors;
    }

    // Method to print the steps of the solution
    void printSolution(List<Cell> path) {
        System.out.println("Steps to reach the finish:");
        System.out.println("1. Start at (" + path.get(0).A + ", " + path.get(0).B + ")");
        for (int i = 1; i < path.size(); i++) {
            Cell current = path.get(i - 1);
            Cell next = path.get(i);
            int dx = next.A - current.A;
            int dy = next.B - current.B;
            String direction = "";
            if (dx > 0) {
                direction = "right";
            } else if (dx < 0) {
                direction = "left";
            } else if (dy > 0) {
                direction = "down";
            } else if (dy < 0) {
                direction = "up";
            }
            if (!direction.isEmpty()) {
                System.out.println((i + 1) + ". Move " + direction + " to (" + next.A + ", " + next.B + ")");
            } else {
                System.out.println((i + 1) + ". Move to (" + next.A + ", " + next.B + ")");
            }
        }
        System.out.println((path.size() + 1) + ". Done!");
    }
}
