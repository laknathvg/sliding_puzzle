// Represents a cell in the map
class Cell {
    int A, B;  // Coordinates of the cell
    boolean isObstacle;  // Indicates if the cell is an obstacle (rock or a wall)

    Cell(int A, int B, boolean isObstacle) {
        this.A = A;
        this.B = B;
        this.isObstacle = isObstacle;
    }
}
