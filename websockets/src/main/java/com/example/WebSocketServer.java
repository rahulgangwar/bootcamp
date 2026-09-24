package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class WebSocketServer {

    private final int port;

    public WebSocketServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(
                        "TCP connection received from " + socket.getRemoteSocketAddress());
                handle(socket);
            }
        }
    }

    public void handle(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String line;

        System.out.println("----- HTTP REQUEST -----");
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            System.out.println(line);
        }
        System.out.println("------------------------");
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        WebSocketServer server = new WebSocketServer(8080);
        server.start();
    }
}
