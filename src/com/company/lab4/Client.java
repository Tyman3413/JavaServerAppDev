package com.company.lab4;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class Client {
    public static void main(String[] args) {
        try {
            // Указываем группу и порт для multicast-сети
            InetAddress group = InetAddress.getByName("233.0.0.1");
            int port = 1502;

            // Создаем MulticastSocket и присоединяемся к указанной группе и интерфейсу
            MulticastSocket socket = new MulticastSocket(port);
            socket.joinGroup(new InetSocketAddress(group, port),
                    NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));

            // Создаем список для хранения сообщений
            LinkedList<String> messageQueue = new LinkedList<>();

            // Создаем отдельный поток для приема сообщений
            Thread messageReceiver = new Thread(() -> {
                while (true) {
                    try {
                        // Создаем буфер для приема данных
                        byte[] buffer = new byte[1024];

                        // Создаем DatagramPacket для приема пакетов данных
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);

                        // Преобразуем полученные данные в текстовое сообщение
                        String message = new String(packet.getData(), 0, packet.getLength());

                        // Проверяем, не было ли такого сообщения ранее
                        if (!messageQueue.contains(message)) {
                            messageQueue.add(message);

                            // Если сообщений больше 5, удаляем самое старое
                            if (messageQueue.size() > 5) {
                                messageQueue.poll();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Запускаем поток для приема сообщений
            messageReceiver.start();

            while (true) {
                // Выводим последние сообщения в консоль
                System.out.println("Recent Messages:");
                for (String message : messageQueue) {
                    System.out.println(message);
                }

                try {
                    Thread.sleep(1000); // Обновление каждые 10 секунд
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
