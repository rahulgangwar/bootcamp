## Class Diagram
```mermaid
classDiagram
    class Board {
        -char[][] grid
        -int totalMoves
        +Board()
        +makeMove(int, int, char)
        +isFull() : boolean
        +hasWinner() : boolean
        +printBoard()
    }

    class Player {
        -String name
        -char symbol
        +Player(String, char)
        +getName() : String
        +getSymbol() : char
    }

    class Game {
        -Player player1
        -Player player2
        -Board board
        -Player currentPlayer
        +Game(Player, Player)
        +play()
    }

    Board --> Player
    Game --> Board
    Game --> Player