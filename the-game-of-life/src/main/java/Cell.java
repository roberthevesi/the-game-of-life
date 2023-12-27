import java.util.Random;
import java.util.UUID;

public abstract class Cell extends Thread{
    protected String cellId;
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
        this.cellId = UUID.randomUUID().toString();
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
        try {
            String message = "x:die:" + this.getCellId();
            EventPublisher.publish(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public int getT_starve() {
        return T_starve;
    }

    public void setT_starve(int t_starve) {
        T_starve = t_starve;
    }

    public int getT_full() {
        return T_full;
    }

    public void setT_full(int t_full) {
        T_full = t_full;
    }

    public boolean isHungry() {
        return isHungry;
    }

    public void setHungry(boolean hungry) {
        isHungry = hungry;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getTimes_eaten() {
        return times_eaten;
    }

    public void setTimes_eaten(int times_eaten) {
        this.times_eaten = times_eaten;
    }

    public int getRounds_since_last_eaten() {
        return rounds_since_last_eaten;
    }

    public void setRounds_since_last_eaten(int rounds_since_last_eaten) {
        this.rounds_since_last_eaten = rounds_since_last_eaten;
    }

    public Environment getEnvironment() {
        return environment;
    }
}
