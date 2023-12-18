public class SexualCell extends Cell{
    public volatile boolean readyToMultiply;

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
        this.setReadyToMultiply(true);
        try {
            environment.getSemaphore().acquire();
            String message = "SexualCell:multiply:" + this.getCellId();
            EventPublisher.publish(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            environment.getSemaphore().release();
        }
    }
}
