public class Main {
    public static void main(String[] args) {
//        AsexualCell cellA = new AsexualCell(5, 5, false, 0, 0);
//        SexualCell cellS = new SexualCell(3, 4, false, 0, 0);


        Environment environment = new Environment();

        environment.addCell(new AsexualCell(4, 3, false, true, 0, 0, environment));
        environment.addCell(new AsexualCell(5, 6, false, true, 0, 0, environment));
        environment.addCell(new AsexualCell(2, 5, false, true, 0, 0, environment));
        environment.addCell(new SexualCell(4, 3, false, true, 0, 0, environment, false));
        environment.addCell(new SexualCell(2, 4, false, true, 0, 0, environment, false));
        environment.addCell(new SexualCell(3, 4, false, true, 0, 0, environment, false));

        environment.addFoodUnits(50);

        for(int i=0; i<100; i++){
            environment.simulateTurn();
            System.out.println("Turn " + (i + 1) + " completed.");
        }
    }
}