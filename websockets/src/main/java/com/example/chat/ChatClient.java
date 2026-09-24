package com.example.chat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Scanner;
import java.util.concurrent.CompletionStage;

public class ChatClient implements WebSocket.Listener {

    private final String username;
    private WebSocket webSocket;

    public ChatClient(String username) {
        this.username = username;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        this.webSocket = webSocket;

        System.out.println("Connected to chat server.");
        System.out.println("Type your message and press Enter.");
        System.out.println("Press Ctrl+C to exit.");

        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println();
        System.out.println(data);
        System.out.print("> ");
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String username = scanner.nextLine();

        ChatClient chatClient = new ChatClient(username);
        HttpClient httpClient = HttpClient.newHttpClient();
        chatClient.webSocket =
                httpClient
                        .newWebSocketBuilder()
                        .buildAsync(URI.create("ws://localhost:8080"), chatClient)
                        .join();

        chatClient.startReadingInput();
    }

    private void startReadingInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String message = scanner.nextLine();
            webSocket.sendText(username + ": " + message, true);
        }
    }
}
