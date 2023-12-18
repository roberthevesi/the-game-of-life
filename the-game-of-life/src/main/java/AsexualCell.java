public class AsexualCell extends Cell{
    public AsexualCell(int t_starve, int t_full, boolean isHungry, boolean isAlive, int health, int times_eaten, Environment environment) {
        super(t_starve, t_full, isHungry, isAlive, health, times_eaten, environment);
    }

    @Override
    public void multiply() {
        try {
//            EventPublisher.publish("Asexual cell should multiply into two hungry cells!");
            String message = "AsexualCell:multiply:" + this.getCellId();
            EventPublisher.publish(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
