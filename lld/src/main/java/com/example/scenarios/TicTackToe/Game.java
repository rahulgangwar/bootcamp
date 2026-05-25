package com.example.scenarios.TicTackToe;

import java.util.Scanner;

public class Game {
    private final Player player1;
    private final Player player2;
    private final Board board;
    private Player currentPlayer;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = new Board();
        this.currentPlayer = player1;
    }

    public void play() {
        board.print();

        while (!board.isFull() && !board.hasWinner()) {
            System.out.println(currentPlayer + "'s turn");
            int row = getValidInput("Enter row: ");
            int col = getValidInput("Enter col: ");

            try {
                board.makeMove(row, col, currentPlayer.getSymbol());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Move");
                continue;
            }

            switchPlayer();
            board.print();
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private int getValidInput(String message) {
        Scanner scanner = new Scanner(System.in);
        int input;

        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input >= 0 && input <= 2) {
                    return input;
                }
            } else {
                scanner.next();
            }
            System.out.println("Invalid input! Please enter a number between 0 and 2.");
        }
    }
}
