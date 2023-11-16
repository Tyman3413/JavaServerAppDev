package com.company.lab4;

import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    public static void main(String[] args) {
        try {
            // Указываем группу и порт для multicast-сети
            InetAddress group = InetAddress.getByName("233.0.0.1");
            int port = 1502;

            // Создаем MulticastSocket и присоединяемся к указанной группе и интерфейсу
            MulticastSocket socket = new MulticastSocket(port);
            socket.joinGroup(new InetSocketAddress(group, port),
                    NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));

            // Создаем таймер для периодической отправки сообщений
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        // Читаем текстовое сообщение из файла
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                new FileInputStream("message.txt"), "UTF-8"));
                        String line;
                        StringBuilder messageBuilder = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            messageBuilder.append(line).append("\n");
                        }
                        String message = messageBuilder.toString();
                        reader.close();

                        // Отправляем сообщение в multicast-группу
                        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), group, port);
                        socket.send(packet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 10000); // Отправляем каждые 10 секунд
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}