import java.util.Random;

public abstract class Cell {
    protected int T_starve;
    protected int T_full;
    protected boolean isHungry;
    protected boolean isAlive;
    protected int health;
    protected int times_eaten;
    protected final Environment environment;


    public Cell(int t_starve, int t_full, boolean isHungry, boolean isAlive, int health, int times_eaten, Environment environment) {
        T_starve = t_starve;
        T_full = t_full;
        this.isHungry = isHungry;
        this.isAlive = isAlive;
        this.health = health;
        this.times_eaten = times_eaten;
        this.environment = environment;
    }

    public abstract void multiply();

    public void cycle(){
        if(!isAlive)
            return;

//        if(isHungry){
//            if(environment.consumeFoodUnitOk()){
//                isHungry = false;
//                health = T_full;
//                times_eaten++;
//
//                if(times_eaten % 10 == 0){
//                    multiply();
//                }
//            }
//            else{
//                health--;
//
//                if(-T_starve == health){
//                    die();
//                }
//            }
//        }
//        else{
//            if(environment.consumeFoodUnitOk()){
//                health += T_full;
//                times_eaten++;
//
//                if(times_eaten % 10 == 0){
//                    multiply();
//                }
//            }
//        }

        if (environment.consumeFoodUnitOk()) {
            if (isHungry) {
                isHungry = false;
                health = T_full;
            } else {
                health += T_full;
            }
            times_eaten++;

            if (times_eaten % 10 == 0) {
                multiply();
            }
        } else if (isHungry) {
            health--;

            if (health == -T_starve) {
                die();
            }
        }




//        checkAndConsumeFood();

//        if(isHungry)
    }

    public void die(){
        isAlive = false;

        Random random = new Random();
        int randomFoodUnits = random.nextInt(5) + 1;

        environment.addFoodUnits(randomFoodUnits);
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

    public boolean isAlive() {
        return isAlive;
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

    public Environment getEnvironment() {
        return environment;
    }
}
