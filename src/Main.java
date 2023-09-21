import java.util.List;

public class Main {
    public static void main(String[] args) {
        Train train = new Train();

        PassengerWagon wagon1 = new PassengerWagon(100, 100, 3);
        PassengerWagon wagon2 = new PassengerWagon(45, 80, 2);
        CargoWagon wagon3 = new CargoWagon(200);
        PassengerWagon wagon4 = new PassengerWagon(55, 120, 4);

        train.addWagon(wagon1);
        train.addWagon(wagon2);
        train.addWagon(wagon3);
        train.addWagon(wagon4);

        int totalPassengerCapacity = train.getTotalPassengerCapacity();
        int totalBaggageCapacity = train.getTotalBaggageCapacity();

        System.out.println("Общая численность пассажиров передвижного состава: " + totalPassengerCapacity);
        System.out.println("Общая вместимость багажа передвижного состава: " + totalBaggageCapacity);
        System.out.println("");

        train.sortWagonsByComfortLevel();

        System.out.println("Вагоны по уровню комфортности:");
        for (Wagon wagon : train.getWagons()) {
            System.out.println(wagon.getName() + ": " + wagon.getComfortLevel());
        }
        System.out.println("");

        int minCapacity = 40;
        int maxCapacity = 60;

        List<PassengerWagon> wagonsInRange = train.findWaggonsByPassengerCapacity(minCapacity, maxCapacity);

        System.out.println("Вагоны с численностью пассажиров от " + minCapacity + " до " + maxCapacity + ":");
        for (PassengerWagon wagon : wagonsInRange) {
            System.out.println(wagon.getName() + ": " + wagon.getPassengerCapacity() + " мест");
        }
    }
}