// * Базовый класс подвижного железнодорожного транспорта

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Wagon {
    private static int wagonCount = 0; // номер вагона
    private String name; // уникальное имя вагона
    private int passengerCapacity; // число пассажиров
    private int baggageCapacity; // число багажных мест
    private int comfortLevel; // уровень комфорта

    // Конструктор
    public Wagon(int passengerCapacity, int baggageCapacity, int comfortLevel) {
        this.name = "Вагон " + (++wagonCount);
        this.passengerCapacity = passengerCapacity;
        this.baggageCapacity = baggageCapacity;
        this.comfortLevel = comfortLevel;
    }

    // Геттеры для получения информации о вагоне
    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public int getBaggageCapacity() {
        return baggageCapacity;
    }

    public int getComfortLevel() {
        return comfortLevel;
    }

    public String getName() {
        return name;
    }
}

// * Класс пассажирского поезда
class PassengerWagon extends Wagon {
    public PassengerWagon(int passengerCapacity, int baggageCapacity, int comfortLevel) {
        super(passengerCapacity, baggageCapacity, comfortLevel);
    }
}

// * Класс грузового поезда
class CargoWagon extends Wagon {
    public CargoWagon(int baggageCapacity) {
        super(0, baggageCapacity, 0);
    }
}

// * Класс поезда, состоящего из вагонов
class Train {
    private List<Wagon> wagons = new ArrayList<>(); // список вагонов передвижного состава

    // Метод добавления вагона к составу
    public void addWagon(Wagon wagon) {
        wagons.add(wagon);
    }

    // Метод возвращает количество вагонов в передвижном составе
    public List<Wagon> getWagons() {
        return wagons;
    }

    // Метод возвращает общую вместимость пассажиров передвижного состава
    public int getTotalPassengerCapacity() {
        int totalPassengerCapacity = 0;

        for (Wagon wagon : wagons) {
            if (wagon instanceof PassengerWagon) {
                totalPassengerCapacity += wagon.getPassengerCapacity();
            }
        }

        return totalPassengerCapacity;
    }

    // Метод возвращает общую вместимость багажа передвижного состава
    public int getTotalBaggageCapacity() {
        int totalBaggageCapacity = 0;

        for (Wagon wagon : wagons) {
            totalBaggageCapacity += wagon.getBaggageCapacity();
        }

        return totalBaggageCapacity;
    }

    // Метод сортировки вагонов по уровню комфортности
    public void sortWagonsByComfortLevel() {
        wagons.sort(Comparator.comparing(Wagon::getComfortLevel));
    }

    // Метод нахождения вагонов в заданном диапазоне вместимости пассажиров
    public List<PassengerWagon> findWaggonsByPassengerCapacity(int minCapacity, int maxCapacity) {
        List<PassengerWagon> result = new ArrayList<>();

        for (Wagon wagon : wagons) {
            if (wagon instanceof PassengerWagon) {
                int capacity = wagon.getPassengerCapacity();

                if (capacity >= minCapacity && capacity <= maxCapacity) {
                    result.add((PassengerWagon) wagon);
                }
            }
        }

        return result;
    }
}