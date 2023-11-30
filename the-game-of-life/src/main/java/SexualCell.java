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
        try {
            EventPublisher.publish("Two cells mated and resulted in a new hungry cell!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Implementing synchronized mating mechanism
        synchronized (environment) {
            if (!isAlive || !readyToMultiply) return;

            // Find a partner cell that is ready to multiply
            SexualCell partner = environment.findReadySexualCell(this);
            if (partner != null) {
                System.out.println("Two sexual cells have multiplied into a third hungry cell!");
                SexualCell offspring = new SexualCell(T_starve, T_full, true, true, health, times_eaten, environment, false);
                offspring.start(); // start the new thread

                environment.addCell(offspring);

                readyToMultiply = false;
                partner.setReadyToMultiply(false);
            }
        }
    }
}
