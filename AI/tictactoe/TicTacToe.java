import java.io.*;
import java.util.*;
import java.util.Scanner;

public class TicTacToe {
    //count the number of expanded nodes
    public static int nodes = 0;

    //scan all the items and call other functions to run the game
    public static void recInput() {
        String[][] array = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                array[i][k] = " ";
            }
        }

        System.out.println("======================");
        System.out.println("Select the AIs opponent:");
        System.out.println("[1] Human");
        System.out.println("[2] AI");

        Scanner myObj = new Scanner(System.in);
        System.out.print("==> ");

        String choice = myObj.nextLine();

        if (choice.equals("1")) {
            printUser();
            checkBoard(array);
        }
        if (choice.equals("2")) {
            aVa();
            aiPlays(array);
        }

    }
    public static void printUser() {
        System.out.println("======================");
        System.out.println("   |   |   ");
        System.out.println("---+---+---");
        System.out.println("   |   |   ");
        System.out.println("---+---+---");
        System.out.println("   |   |   ");
        System.out.println("Player 1's turn...");
    }
    public static void aVa() {
        System.out.println("======================");
        System.out.println("   |   |   ");
        System.out.println("---+---+---");
        System.out.println("   |   |   ");
        System.out.println("---+---+---");
        System.out.println("   |   |   ");
        System.out.println("AI 1's turn...");
    }
    //this function makes the ai play with each other 
    public static void aiPlays(String[][] array) {
        String winner = null;
        while (winner == null) {

            int ai1[] = bestMoveAi(array);
            array[ai1[0]][ai1[1]] = "X";

            System.out.println("\n======================");
            for (int k=0; k < 3; k++) {
                if (k != 2) {
                    System.out.print(array[0][k] + "  |");
                }
                else {
                    System.out.print(array[0][k]);
                }
            }
            System.out.println("\n---+---+---");
            for (int k=0; k < 3; k++) {
                if (k != 2) {
                    System.out.print(array[1][k] + "  |");
                }
                else {
                    System.out.print(array[1][k]);
                }
            }
            System.out.println("\n---+---+---");
            for (int k=0; k < 3; k++) {
                if (k != 2) {
                    System.out.print(array[2][k] + "  |");
                }
                else {
                    System.out.print(array[2][k]);
                }
            }
            System.out.println("\nAI 2's turn");
            System.out.println("Nodes expanded: " + nodes);

            int ai2[] = bestMoveAi2(array);
            array[ai2[0]][ai2[1]] = "O";

            winner = whoWon(array);
            if (winner != null) {
                System.out.println("\n======================");
                for (int k=0; k < 3; k++) {
                    if (k != 2) {
                        System.out.print(array[0][k] + "  |");
                    }
                    else {
                        System.out.print(array[0][k]);
                    }
                }
                System.out.println("\n---+---+---");
                for (int k=0; k < 3; k++) {
                    if (k != 2) {
                        System.out.print(array[1][k] + "  |");
                    }
                    else {
                        System.out.print(array[1][k]);
                    }
                }
                System.out.println("\n---+---+---");
                for (int k=0; k < 3; k++) {
                    if (k != 2) {
                        System.out.print(array[2][k] + "  |");
                    }
                    else {
                        System.out.print(array[2][k]);
                    }
                }
            }
        }
        System.out.println("\nAI " + winner + " has won!");
    }

    //this function recieves user input and runs the game, it prompts for inputs
    public static void checkBoard(String[][] array) {
        String winner = whoWon(array);
        if (winner != null) {
            if (winner.equals("tie")) {
                System.out.println("Tie Game");
            }
            else {
                System.out.println("\nPlayer " + winner + " has won!");
            }
            
        } 
        else {
            int check = 0;
            int p1 = 0;
            int p2 = 0;
            //repeat if incorrect spot
            while (1 > check) {
                Scanner myObj = new Scanner(System.in);
                System.out.print("\nEnter row [0 to 2]: ");
                p1 = myObj.nextInt();
                Scanner myObj2 = new Scanner(System.in);
                System.out.print("Enter col [0 to 2]: ");
                p2 = myObj2.nextInt();
                if (array[p1][p2].equals(" ")) {
                    check = 2;
                }
                else {
                    System.out.println("That space is already occupied, try again.");
                }
            }
            //place user input on board
            array[p1][p2] = "X";

            System.out.println("======================");

            for (int k=0; k < 3; k++) {
                if (k != 2) {
                    System.out.print(array[0][k] + "  |");
                }
                else {
                    System.out.print(array[0][k]);
                }
            }
            System.out.println("\n---+---+---");
            for (int k=0; k < 3; k++) {
                if (k != 2) {
                    System.out.print(array[1][k] + "  |");
                }
                else {
                    System.out.print(array[1][k]);
                }
            }
            System.out.println("\n---+---+---");
            for (int k=0; k < 3; k++) {
                if (k != 2) {
                    System.out.print(array[2][k] + "  |");
                }
                else {
                    System.out.print(array[2][k]);
                }
            }
            //now call addAi to make ai place its turn on board
            addAi(array, 1);
        }
    }

    //this picks the best move and returns the coords for that move to add ai
    public static int[] bestMoveAi(String[][] array) {
        //start off with a small value
        int bScore = Integer.MIN_VALUE;
        //array for coords
        int move[] = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                //if spot is empty
                if (array[i][k] == " ") {
                    //fill it with o and run the ai algo make sure depth is 3 so it doesn't always find the best solution
                    array[i][k] = "O";
                    int score = bfs(array, 3, false);
                    //reset array
                    array[i][k] = " ";
                    //store the best score and return coords
                    if (score > bScore) {
                        move[0] = i;
                        move[1] = k;
                        bScore = score;
                    }
                }
            }
        }
        return move;
    }

    //this function performs depth limited search, it decrements from level 3 as it recursively calls itself and -1 from the depth
    public static int bfs(String[][] array, int depth, boolean check) {
        String result = whoWon(array);
        if (result != null) {
            nodes++;
            if (result.equals("X")) {
                return -1;
            }
            if (result.equals("O")) {
                return 1;
            }
            if (result.equals("tie")) {
                return 0;
            }
        }
        //we want to maximize the ai so its smart and strategically places its move
        if (check) {
            int bScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int k = 0; k < 3; k++) {
                    if (array[i][k] == " ") {
                        array[i][k] = "O";
                        bScore = Math.max(bfs(array, depth - 1, false), bScore);
                        array[i][k] = " ";
                    }
                }
            }
            return bScore;
        }
        //this is for the "human", all possible places the human can go
        else {
            int bScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int k = 0; k < 3; k++) {
                    if (array[i][k] == " ") {
                        array[i][k] = "X";
                        bScore = Math.min(bfs(array, depth - 1, true), bScore);
                        array[i][k] = " ";
                    }
                }
            }
            return bScore;
        }

    }

    //this adds the best move picked by the ai to the board and prints the board
    public static void addAi(String[][] array, int who) {
        int[] move = bestMoveAi(array);
        if (array[move[0]][move[1]] == " ") {
            array[move[0]][move[1]] = "O";
        }

        System.out.println("\n======================");
        for (int k=0; k < 3; k++) {
            if (k != 2) {
                System.out.print(array[0][k] + "  |");
            }
            else {
                System.out.print(array[0][k]);
            }
        }
        System.out.println("\n---+---+---");
        for (int k=0; k < 3; k++) {
            if (k != 2) {
                System.out.print(array[1][k] + "  |");
            }
            else {
                System.out.print(array[1][k]);
            }
        }
        System.out.println("\n---+---+---");
        for (int k=0; k < 3; k++) {
            if (k != 2) {
                System.out.print(array[2][k] + "  |");
            }
            else {
                System.out.print(array[2][k]);
            }
        }
        System.out.println("\nPlayer 2's turn");
        System.out.println(move[0] + "," + move[1]);
        System.out.println("Nodes expanded: " + nodes);

        checkBoard(array);
    }


    //this function checks all stages and possible 3 in a row to determine who won or whether its a tie
    public static String whoWon(String[][] array) {
        String winner = null;
        for (int i=0; i < 3; i++) {
            if (!array[0][i].equals(" ") && array[0][i].equals(array[1][i]) && array[1][i].equals(array[2][i])) {
                winner = array[0][i];
            }
        }
        for (int i=0; i < 3; i++) {
            if (!array[i][0].equals(" ") && array[i][0].equals(array[i][1]) && array[i][1].equals(array[i][2])) {
                winner = array[i][0];
            }
        }
        if (!array[0][0].equals(" ") && array[0][0].equals(array[1][1]) && array[1][1].equals(array[2][2])) {
            winner = array[0][0];
        }
        if (!array[2][0].equals(" ") && array[2][0].equals(array[1][1]) && array[1][1].equals(array[0][2])) {
            winner = array[2][0];
        }
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                if (!array[i][k].equals(" ")) {
                    count++;
                }

            }
        }
        if (winner == null && count == 9) {
            return "tie";
        }
        return winner;
    }
        //these 2 functions are specifically for ai vs ai because the recursive search is set to true instead of false 
    //this way it will always try to find the best solution for itself
    public static int[] bestMoveAi2(String[][] array) {
        //start off with a small value
        int bScore = Integer.MIN_VALUE;
        //array for coords
        int move[] = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                //if spot is empty
                if (array[i][k] == " ") {
                    //fill it with o and run the ai algo make sure depth is 3 so it doesn't always find the best solution
                    array[i][k] = "X";
                    int score = bfs2(array, 3, false);
                    //reset array
                    array[i][k] = " ";
                    //store the best score and return coords
                    if (score > bScore) {
                        move[0] = i;
                        move[1] = k;
                        bScore = score;
                    }
                }
            }
        }
        return move;
    }

    public static int bfs2(String[][] array, int depth, boolean check) {
        String result = whoWon(array);
        //different values because we want the ai to try to block X
        if (result != null) {
            nodes++;
            if (result.equals("X")) {
                return 1;
            }
            if (result.equals("O")) {
                return -1;
            }
            if (result.equals("tie")) {
                return 0;
            }
        }
        //X and O are reversed so it'll be somewhat autonomous
        //we want to maximize the ai so its smart and strategically places its move
        if (check) {
            int bScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int k = 0; k < 3; k++) {
                    if (array[i][k] == " ") {
                        array[i][k] = "O";
                        bScore = Math.min(bfs(array, depth - 1, true), bScore);
                        array[i][k] = " ";
                    }
                }
            }
            return bScore;
        }
        //this is for the "human", all possible places the human can go
        else {
            int bScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int k = 0; k < 3; k++) {
                    if (array[i][k] == " ") {
                        array[i][k] = "X";
                        bScore = Math.max(bfs(array, depth - 1, false), bScore);
                        array[i][k] = " ";
                    }
                }
            }
            return bScore;
        }
    }
}
