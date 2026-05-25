package com.example.websockets;

import org.glassfish.tyrus.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server("localhost", 8025, "/ws", null, NotificationEndpoint.class);

        try {
            server.start();
            System.out.println("WebSocket server running at ws://localhost:8025/ws/notifications");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
