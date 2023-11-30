import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Environment {
    public final List<Cell> cells;
    private int foodUnits;

    public int getFoodUnits() {
        return foodUnits;
    }

    public Environment() {
        this.cells = new CopyOnWriteArrayList<>(); // Thread-safe list
        this.foodUnits = 0;
    }

    public void addCell(Cell cell){
        cells.add(cell);
    }

    public boolean consumeFoodUnitOk(){
        if(foodUnits > 0){
            foodUnits--;
            return true;
        }
        return false;
    }

    public void addFoodUnits(int units){
        foodUnits += units;
    }

    public SexualCell findReadySexualCell(SexualCell requester) {
        for (Cell cell : cells) {
            if (cell != requester && cell instanceof SexualCell) {
                SexualCell sc = (SexualCell) cell;
                if (sc.isReadyToMultiply()) {
                    return sc;
                }
            }
        }
        return null;
    }

    // New simulateTurn() method for the threaded environment
    public void simulateTurn() {
        System.out.println("Food units left in environment at this turn: " + foodUnits);
        for(Cell cell : cells){
            System.out.println(cell);
            cell.cycle();
        }
        // Removing dead cells from the environment
        cells.removeIf(cell -> !cell.isAlive());

        System.out.println("Cells left: " + cells.size());

        // Example of an environment-wide action: Add food units periodically
        addFoodUnits(2); // Example value, adjust as needed

        // Additional environment-wide actions can be added here
    }

    public void stopAllCells(){
        for(Cell cell : cells){
            cell.interrupt();
        }
    }
}