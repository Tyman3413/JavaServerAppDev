package Task2;

import java.util.concurrent.*;

public class Lab8Savages1 {
    private static final int savages = 15; // количество дикарей
    private static final int potCapacity = 10; // вместимость кастрюли

    private static final Semaphore potSemaphore = new Semaphore(0); // Семафор полной кастрюли. Начальное значение - 0
    private static final Semaphore emptyPotSemaphore = new Semaphore(1); // Семафор пустой кастрюли. Начальное значение
                                                                         // - 1
    private static final Semaphore mutex = new Semaphore(1); // Мютекс для обеспечения взаимного исключения. Начальное
                                                             // значение - 1

    public static void main(String[] args) {
        // Создаем исполнитель (executor) с фиксированным числом потоков
        ExecutorService executor = Executors.newFixedThreadPool(savages + 1);

        // Создаем задачу для повара
        Runnable cookTask = () -> {
            try {
                while (true) {
                    // Повар готовит порции пищи, ожидая 2 секунды
                    System.out.println("Cook is cooking...");
                    Thread.sleep(2000); // повар готовит порции

                    emptyPotSemaphore.acquire(); // получение разрешения на пополнение кастрюли
                    fillPot(); // наполнение кастрюли
                    potSemaphore.release(); // даем знать дикарям, что кастрюля наполнена
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // обработка исключения
            }
        };

        // Создаем задачу для дикарей
        Runnable savageTask = () -> {
            try {
                // Проверяем, есть ли порции в кастрюле. Если кастрюля пуста, он будит повара, а
                // затем ожидает, пока повар наполнит кастрюлю. После этого дикарь ест и затем
                // спит 1 секунду
                while (true) {
                    mutex.acquire(); // обеспечение взаимного исключения между дикарями
                    if (potSemaphore.availablePermits() == 0) {
                        System.out.println("Savage wakes up to cook.");
                        emptyPotSemaphore.release();
                        potSemaphore.acquire();
                    }
                    eat();
                    mutex.release();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // обработка исключения
            }
        };

        // Отправляем задачу повара на выполнение с использованием исполнителя
        executor.submit(cookTask);

        // Отправляем задачи для дикарей на выполнение с использованием исполнителя
        for (int i = 0; i < savages; i++) {
            executor.submit(savageTask);
        }

        // Приостанавливаем выполнение программы на 10 секунд, чтобы показать работу
        // потоков.
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Завершаем работу исполнителя после завершения работы всех задач
        executor.shutdownNow();
    }

    // Метод, который выводит сообщение о наполнении кастрюли
    private static void fillPot() {
        System.out.println("Cook fills the pot with " + potCapacity + " portions.");
    }

    // Метод, который выводит сообщение о том, что дикарь ест
    private static void eat() {
        System.out.println("Savage is eating...");
    }
}

/*
 * В этой программе используются семафоры для синхронизации между дикарями и
 * поваром. Когда дикарь хочет поесть, он проверяет, есть ли порции в кастрюле,
 * и если нет, он будит повара, который наполняет кастрюлю. После этого дикарь
 * берет порцию и ест.
 * 
 * Этот код демонстрирует симуляцию поведения дикарей и повара в соответствии с
 * описанными ограничениями задачи.
 */