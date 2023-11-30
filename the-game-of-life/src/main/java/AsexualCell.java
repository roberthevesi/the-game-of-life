public class AsexualCell extends Cell{
    public AsexualCell(int t_starve, int t_full, boolean isHungry, boolean isAlive, int health, int times_eaten, Environment environment) {
        super(t_starve, t_full, isHungry, isAlive, health, times_eaten, environment);
    }

    @Override
    public void multiply() {
        try {
            EventPublisher.publish("Asexual cell has multiplied into two hungry cells!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!isAlive)
            return;

        System.out.println("Asexual cell has multiplied into two hungry cells!");
        // multiply through division: delete current cell, create two new hungry cells
        AsexualCell c1 = new AsexualCell(3, 6, true, true, 0, 0, environment);
        AsexualCell c2 = new AsexualCell(4, 3, true, true, 0, 0, environment);

        c1.start(); // start the new threads
        c2.start();

        environment.addCell(c1);
        environment.addCell(c2);
    }

}