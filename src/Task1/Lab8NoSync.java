package Task1;

public class Lab8NoSync {
    private static int counter = 0; // счетчик
    private static final int iterations = 100000; // количество итераций цикла для каждого потока
    private static final int n = 5; // потоки первого типа
    private static final int m = 5; // потоки второго типа

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); // время начала выполнения программы

        Thread[] incrementThreads = new Thread[n]; // создаем массив потоков первого типа
        Thread[] decrementThreads = new Thread[m]; // создаем массив потоков второго типа

        // Создаем цикл для потоков первого типа
        for (int i = 0; i < n; i++) {
            incrementThreads[i] = new Thread(new IncrementTask()); // создаем поток
            incrementThreads[i].start(); // запускаем поток
        }

        // Создаем цикл для потоков второго типа
        for (int i = 0; i < m; i++) {
            decrementThreads[i] = new Thread(new DecrementTask()); // создаем поток
            decrementThreads[i].start(); // запускаем поток
        }

        // Ждем завершение всех потоков
        try {
            for (int i = 0; i < n; i++) {
                incrementThreads[i].join(); // ждем завершение потока
            }

            for (int i = 0; i < m; i++) {
                decrementThreads[i].join(); // ждем завершение потока
            }
        } catch (InterruptedException e) {
            e.printStackTrace(); // обрабатываем исключение
        }

        long endTime = System.currentTimeMillis(); // время завершения выполнения программы
        System.out.println("Final counter value: " + counter); // выводим значение счетчика
        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds"); // выводим финальное время
                                                                                          // выполнения программы
    }

    // Задача для потоков первого типа
    private static class IncrementTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < iterations; i++) {
                int temp = counter;
                temp++;
                counter = temp;
            }
        }
    }

    // Задача для потоков второго типа
    private static class DecrementTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < iterations; i++) {
                int temp = counter;
                temp--;
                counter = temp;
            }
        }
    }
}

/*
 * Мы имеем общий счетчик, и два типа потоков: одни инкрементируют счетчик, а
 * другие декрементируют его. Задача состоит в том, чтобы понять, как
 * многопоточность может повлиять на результат.
 */