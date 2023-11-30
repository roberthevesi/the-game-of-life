import java.util.Random;

public abstract class Cell extends Thread{
    protected int T_starve;
    protected int T_full;
    protected boolean isHungry;
    protected boolean isAlive;
    protected int health;
    protected int times_eaten;
    protected int rounds_since_last_eaten;
    protected final Environment environment;

    @Override
    public String toString() {
        return "Cell{" +
                "ID=" + this.hashCode() +
                ", T_starve=" + T_starve +
                ", T_full=" + T_full +
                ", rounds_since_last_eaten=" + rounds_since_last_eaten +
                ", isHungry=" + isHungry +
                ", isAlive=" + isAlive +
                ", health=" + health +
                ", times_eaten=" + times_eaten +
                ", environment=" + environment +
                '}';
    }

    public Cell(int t_starve, int t_full, boolean isHungry, boolean isAlive, int health, int times_eaten, Environment environment) {
        T_starve = t_starve;
        T_full = t_full;
        this.isHungry = isHungry;
        this.isAlive = isAlive;
        this.health = health;
        this.times_eaten = times_eaten;
        this.environment = environment;
        this.rounds_since_last_eaten = 0;
    }

    @Override
    public void run() {
        while(isAlive) {
            try {
                cycle();
                Thread.sleep(1000); // simulate time delay for each cycle
            } catch (InterruptedException e) {
                System.out.println("Cell with hashCode=" + this.hashCode() + " was interrupted!");
                isAlive = false;
            }
        }
    }

    public abstract void multiply();

    protected void cycle(){
        if(!isAlive){
            this.interrupt();
            return;
        }

        if (environment.consumeFoodUnitOk()) {
//            System.out.println("Cell with hash ");
            if (isHungry)
                isHungry = false;

            health += T_full;
            times_eaten++;
            rounds_since_last_eaten = 0;

            if (times_eaten % 10 == 0) {
                multiply();
            }
        }
        else{
            if (isHungry) {
                health--;

                if ((rounds_since_last_eaten - T_full) >= T_starve) {
                    die();
                }
            }
            else{
                if(rounds_since_last_eaten >= T_full)
                    isHungry = true;
            }
            rounds_since_last_eaten++;
        }
    }

    protected void die(){
        System.out.println("Cell with hashCode=" + this.hashCode() + " has died!");
        isAlive = false;

        Random random = new Random();
        int randomFoodUnits = random.nextInt(5) + 1;

        environment.addFoodUnits(randomFoodUnits);
    }
}
