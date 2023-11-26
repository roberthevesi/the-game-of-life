import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Environment {
    private final List<Cell> cells;
    private int foodUnits;

    public int getFoodUnits() {
        return foodUnits;
    }

    public Environment() {
        this.cells = new CopyOnWriteArrayList<>(); // Thread-safe list
        this.foodUnits = 0;
    }

    public synchronized void addCell(Cell cell){
        cells.add(cell);
    }

    public synchronized boolean consumeFoodUnitOk(){
        if(foodUnits > 0){
            foodUnits--;
            return true;
        }
        return false;
    }

    public synchronized void addFoodUnits(int units){
        foodUnits += units;
    }

    public void simulateTurn(){
        System.out.println("Food units left in environment at this turn: " + foodUnits);
        for(Cell cell : cells){
            System.out.println(cell);
            cell.cycle();
        }

        List<SexualCell> readyCells = cells.stream()
                .filter(c -> c instanceof SexualCell && ((SexualCell) c).isReadyToMultiply())
                .map(c -> (SexualCell) c)
                .toList();

        for (int i = 0; i < readyCells.size() - 1; i += 2) {
            SexualCell cell1 = readyCells.get(i);
            SexualCell cell2 = readyCells.get(i + 1);

            System.out.println("Two sexual cells have multiplied into a third hungry cell!");

            addCell(new SexualCell(5, 5, true, true, 0, 0, this, false));

            cell1.readyToMultiply = false;
            cell2.readyToMultiply = false;
        }

        cells.removeIf(cell -> !cell.isAlive); // remove dead
    }
}
