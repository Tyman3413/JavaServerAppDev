import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        Car[] cars = new Car[5];

        cars[0] = new Car(1, "Toyota", "Camry", 2020, "Белый", 25000.0, "A 111 AA 11");
        cars[1] = new Car(2, "Honda", "Civic", 2019, "Красный", 22000.0, "B 222 BB 22");
        cars[2] = new Car(3, "Ford", "Focus", 2021, "Синий", 23000.0, "C 333 CC 33");
        cars[3] = new Car(4, "Toyota", "Corolla", 2015, "Серебристый", 26000.0, "D 444 DD 44");
        cars[4] = new Car(5, "Nissan", "Altima", 2018, "Черный", 24000.0, "E 555 E 55");

        String targetBrand = "Toyota";
        int minAge = 5;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int targetYear = 2020;
        double minPrice = 24000.0;

        System.out.println("Список автомобилей марки " + targetBrand + ": ");
        for (Car car : cars) {
            if (car.getBrand().equals(targetBrand)) {
                System.out.println(car);
            }
        }

        System.out.println("");

        System.out.println("Список автомобилей марки " + targetBrand + ", которые эксплуатируются больше " + minAge + " лет: ");
        for (Car car : cars) {
            if (car.getBrand().equals(targetBrand)) {
                int carAge = currentYear - car.getYear();
                if (carAge > minAge) {
                    System.out.println(car);
                }
            }
        }
        
        System.out.println("");

        System.out.println("Список автомобилей " + targetYear + " года выпуска, цена которых больше " + minPrice + ": ");
        for (Car car : cars) {
            if (car.getYear() == targetYear && car.getPrice() > minPrice) {
                System.out.println(car);
            }
        }
    }
}