package Task2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Lab8Savages2 {
    private static final int savages = 15; // количество дикарей
    private static final int potCapacity = 10; // вместимость кастрюли

    // Создаем три семафора: mutex для обеспечения взаимного исключения при доступе
    // к кастрюле и другим общим ресурсам, emptyPotSemaphore для уведомления повара
    // о том, что кастрюля пуста (начальное значение 0), и fullPotSemaphore для
    // уведомления дикарей о том, что кастрюля готова (начальное значение 0).
    private static final Semaphore mutex = new Semaphore(1);
    private static final Semaphore emptyPotSemaphore = new Semaphore(0);
    private static final Semaphore fullPotSemaphore = new Semaphore(0);

    private static int servingsLeft = 0; // Количество оставшихся порций в кастрюле

    public static void main(String[] args) {
        // Создаем исполнитель с фиксированным числом потоков
        ExecutorService executor = Executors.newFixedThreadPool(savages);

        // Создаем задачу для повара
        Runnable cookTask = () -> {
            try {
                // Повар готовит порции пищи, ожидая 2 секунды, затем использует мьютекс
                // для получения разрешения на доступ к кастрюле, устанавливает количество
                // оставшихся порций, выводит сообщение о наполнении кастрюли и уведомляет
                // дикарей.
                while (true) {
                    System.out.println("Cook is cooking...");
                    Thread.sleep(2000);

                    mutex.acquire();
                    servingsLeft = potCapacity;
                    System.out.println("Cook fills the pot with " + potCapacity + " portions.");
                    mutex.release();

                    fullPotSemaphore.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // обработка исключений
            }
        };

        // Создаем задачу для дикарей
        Runnable savageTask = () -> {
            try {
                // Дикарь ожидает разрешения, чтобы получить доступ к кастрюле, затем
                // захватывает мьютекс для обеспечения взаимного исключения при доступе к
                // порциям пищи. Если в кастрюле есть порции, дикарь ест и уменьшает количество
                // оставшихся порций. Если порции закончились, дикарь будит повара.
                while (true) {
                    fullPotSemaphore.acquire();
                    mutex.acquire();
                    if (servingsLeft > 0) {
                        eat();
                        servingsLeft--;
                    }
                    mutex.release();

                    if (servingsLeft == 0) {
                        System.out.println("Savage wakes up the cook.");
                        emptyPotSemaphore.release();
                    }

                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // обработка исключений
            }
        };

        // Отправляем задачу повара на выполнение с использованием исполнителя
        executor.submit(cookTask);

        // Отправляем задачи для дикарей на выполнение с использованием исполнителя
        for (int i = 0; i < savages; i++) {
            executor.submit(savageTask);
        }

        // Приостанавливаем выполнение программы на 10 секунд, чтобы показать работу
        // потоков
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Завершаем работу исполнителя после завершения работы всех задач
        executor.shutdownNow();
    }

    // Метод, который выводит сообщение о том, что дикарь ест
    private static void eat() {
        System.out.println("Savage is eating.");
    }
}

/*
 * В этой программе мы используем мьютекс mutex, чтобы обеспечить взаимное
 * исключение при доступе к кастрюле и другим общим ресурсам. Дикари используют
 * семафоры emptyPotSemaphore и fullPotSemaphore для синхронизации доступа к
 * кастрюле.
 * 
 * Этот код обеспечивает справедливое распределение порций между дикарями, так
 * что каждый из них в конечном итоге сможет съесть порцию из кастрюли, и никто
 * не будет есть чаще других.
 */