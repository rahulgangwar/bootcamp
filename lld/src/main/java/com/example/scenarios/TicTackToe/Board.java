package com.example.scenarios.TicTackToe;

public class Board {
    private static final char BLANK = '.';
    private final char[][] grid;
    private int totalMoves;

    public Board() {
        this.grid = new char[3][3];
        this.totalMoves = 0;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                grid[row][col] = BLANK;
            }
        }
        totalMoves = 0;
    }

    public synchronized void makeMove(int row, int col, char symbol) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3 || grid[row][col] != BLANK) {
            throw new IllegalArgumentException("Invalid move!");
        }
        grid[row][col] = symbol;
        totalMoves++;
    }

    public boolean isFull() {
        return totalMoves == 9;
    }

    public boolean hasWinner() {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (grid[row][0] != BLANK
                    && grid[row][0] == grid[row][1]
                    && grid[row][1] == grid[row][2]) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (grid[0][col] != BLANK
                    && grid[0][col] == grid[1][col]
                    && grid[1][col] == grid[2][col]) {
                return true;
            }
        }

        // Check diagonals
        if (grid[0][0] != BLANK && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) {
            return true;
        }
        return grid[0][2] != BLANK && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0];
    }

    public void print() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }
}
