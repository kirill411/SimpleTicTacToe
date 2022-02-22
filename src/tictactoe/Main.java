package tictactoe;


import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToe game = new TicTacToe(scanner);
        game.play();
        scanner.close();
    }
}

class TicTacToe {

    private enum gameStatus {
        NOT_FINISHED(""), DRAW("Draw"), X_WINS("X wins"), O_WINS("O wins");
        final String message;
        gameStatus(String s) {
            this.message = s;
        }
    }

    private static Scanner scanner;
    private final char[][] gameField = new char[3][3];
    private gameStatus status = gameStatus.NOT_FINISHED;

    private int moves = 0;
    private char playersTurn = 'X';

    TicTacToe(Scanner sc) {
        scanner = sc;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameField[i][j] = ' ';
            }
        }
    }

    void play() {
        while (status == gameStatus.NOT_FINISHED) {
            printGameField();
            do {
                System.out.print("Enter the coordinates: ");
            } while (!makeMove(scanner.nextLine()));
            changePlayersTurn();
            analyzeGameField();
        }
        printGameField();
        System.out.println(status.message);
    }

    private void analyzeGameField() {
        int threeX = 'X' + 'X' + 'X';
        int threeO = 'O' + 'O' + 'O';
        int mainDiagonalSum = 0;
        int secondaryDiagonalSum = 0;

        for (int i = 0; i < gameField.length; i++) {
            int horizontalSum = 0;
            int verticalSum = 0;

            for (int j = 0; j < gameField[i].length; j++) {
                horizontalSum += gameField[i][j];
                verticalSum += gameField[j][i];
                if (i == j) {
                    mainDiagonalSum += gameField[i][j];
                }
                if (i + j == gameField.length - 1) {
                    secondaryDiagonalSum += gameField[i][j];
                }
            }
            if (horizontalSum == threeX
                    || verticalSum == threeX
                    || mainDiagonalSum == threeX
                    || secondaryDiagonalSum == threeX) {
                status = gameStatus.X_WINS;
                return;
            }
            if (horizontalSum == threeO
                    || verticalSum == threeO
                    || mainDiagonalSum == threeO
                    || secondaryDiagonalSum == threeO) {
                status = gameStatus.O_WINS;
                return;
            }
        }
        if (moves == 9) {
            status = gameStatus.DRAW;
        }
    }

    boolean makeMove(String input) {
        try {
            String[] array = input.split(" ");
            int x = Integer.parseInt(array[0]);
            int y = Integer.parseInt(array[1]);
            if (gameField[x - 1][y - 1] == ' ') {
                gameField[x - 1][y - 1] = playersTurn;
                moves++;
                return true;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Coordinates should be from 1 to 3!");
        }
        return false;
    }

    private void changePlayersTurn() {
        playersTurn = playersTurn == 'X' ? 'O' : 'X';
    }

    void printGameField() {
        System.out.println("---------");
        for (char[] chars : gameField) {
            System.out.printf("| %s %s %s |\n", chars[0], chars[1], chars[2]);
        }
        System.out.println("---------");
    }

    static int pidor(int suka) {
        return Integer.parseInt(String.valueOf(suka)
                .chars()
                .map(c -> (c - '0') * (c - '0'))
                .mapToObj(String::valueOf)
                .collect(Collectors.joining()));
    }
}
