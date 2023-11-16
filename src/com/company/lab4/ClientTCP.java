package com.company.lab4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class ClientTCP {
    public static void main(String[] args) {
        try {
            // Указываем группу и порт для multicast-сети
            InetAddress group = InetAddress.getByName("233.0.0.1");
            int port = 1502;

            // Создаем MulticastSocket и присоединяемся к указанной группе и интерфейсу
            MulticastSocket socket = new MulticastSocket(port);
            socket.joinGroup(new InetSocketAddress(group, port),
                    NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));

            while (true) {
                // Создаем буфер для приема данных
                byte[] buffer = new byte[1024];

                // Создаем DatagramPacket для приема пакетов данных
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                // Преобразуем полученные данные в текстовое сообщение
                String message = new String(packet.getData(), packet.getOffset(), packet.getLength());

                // отображение в консоли
                System.out.println("Received: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
