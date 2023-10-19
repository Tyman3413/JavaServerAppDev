package Task1;

import java.util.concurrent.locks.ReentrantLock;
import java.io.FileWriter;
import java.io.IOException;

public class Lab8ReentrantLock2 {
    private static int counter = 0; // счетчик
    private static final int iterations = 100000; // количество итераций цикла для каждого потока
    private static final int[] threads = { 1, 2, 4, 8 }; // набор потоков
    private static final ReentrantLock lock = new ReentrantLock(); // создаем экземпляр ReentrantLock

    public static void main(String[] args) {
        // Получаем информацию о системе
        String systemInfo = System.getProperty("os.name") + " " + System.getProperty("os.version");
        systemInfo += "\nProcessor: " + System.getenv("PROCESSOR_IDENTIFIER");
        systemInfo += "\nMemory: " + System.getenv("TOTALPHYSICALMEMORY") + " bytes";

        try (FileWriter fileWriter = new FileWriter("Lab8.txt")) {
            // Записываем информацию о системе в файл
            fileWriter.write("System Info:\n" + systemInfo + "\n\n");

            // Начало цикла, в котором мы будем выполнять эксперимент для разного числа
            // потоков из массива
            for (int numThreads : threads) {
                // Записываем текущее время в миллисекундах перед запуском эксперимента для
                // измерения времени выполнения.
                long startTime = System.currentTimeMillis();

                // Создаем массив потоков для текущего числа потоков
                Thread[] threads = new Thread[numThreads];

                // Создаем и запускаем потоки, которые выполняют задачу IncrementDecrementTask
                for (int i = 0; i < numThreads; i++) {
                    threads[i] = new Thread(new IncrementDecrementTask());
                    threads[i].start();
                }

                // Ожидаем завершения всех потоков
                for (int i = 0; i < numThreads; i++) {
                    threads[i].join();
                }

                // Записываем текущее время в миллисекундах после завершения эксперимента для
                // измерения времени выполнения
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime; // вычисляем время выполнения эксперимента

                // Записываем результаты в файл
                fileWriter.write("Threads: " + numThreads + "\n");
                fileWriter.write("Final Counter Value: " + counter + "\n");
                fileWriter.write("Execution Time: " + executionTime + " milliseconds\n\n");

                // Сбрасываем счетчик для следующего запуска
                counter = 0;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // обработка исключений
        }
    }

    // Задача инкремента и декремента
    private static class IncrementDecrementTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < iterations; i++) {
                lock.lock(); // блокировка
                try {
                    counter++;
                } finally {
                    lock.unlock(); // освобождение блокировки
                }
                lock.lock(); // блокировка
                try {
                    counter--;
                } finally {
                    lock.unlock(); // освобождение блокировки
                }
            }
        }
    }
}

/*
 * Этот код выполнит программу для разного числа потоков (1, 2, 4 и 8), измерит
 * время выполнения и сохранит результаты в файле Lab8.txt.
 */