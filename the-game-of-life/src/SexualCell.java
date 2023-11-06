public class SexualCell extends Cell{
    public boolean readyToMultiply;

    public SexualCell(int t_starve, int t_full, boolean isHungry, boolean isAlive, int health, int times_eaten, Environment environment, boolean readyToMultiply) {
        super(t_starve, t_full, isHungry, isAlive, health, times_eaten, environment);
        this.readyToMultiply = readyToMultiply;
    }

    public boolean isReadyToMultiply() {
        return readyToMultiply;
    }

    public void setReadyToMultiply(boolean readyToMultiply) {
        this.readyToMultiply = readyToMultiply;
    }

    @Override
    public void multiply() {

        // multiply through mating, create a new hungry cell

    }
}
