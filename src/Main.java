import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static Random rnd;


    public static void battleshipGame() {

        // get board size
        System.out.println("Enter the board size");
        int row=0, col=0;
        String sizeOfBoard = scanner.nextLine();
        String sizeOfBoardSplit[]= sizeOfBoard.split("X");





          // make 2 boards - initialized to '-'

        // get battleship sizes + numbers of each battleship
          // create 2 arrays, for each one of the players
            // each array will represent the user's battleships state
              // from the smaller to the biggest battleship - each character is a battleship

        // "design" of each board - user and pc
          // getting the coordinations for each battleship
            // creating 5 functions that will check if the coordination is legal
              // all boards are initialized to '-' and the design happens from the smallest to the biggest battleship
              // on each tile where there is a battleship, the index of said battleship will be written in the tile
              // on the tiles around each battleship, for now, we'll put a sign "X" that will tell us it's an illegal tile
                // in the user's board, before the first battleship and after each lega battleship, we'll print their current board

          // after both boards are ready for the game, after all battleships have been placed, we'll change the "X"'s to "-"

        // GAME STARTS!
          // "M" == miss; "H" == hit

        // user's attack
          // print guessing user's board - every cell in the pc's board that isn't "M" or "H"
            // for "M" -> "X"
            // for "H" -> "V"
          // get tile to attack from user
            // check if it is illegal (not inside the board) or if it has been attacked (is marked with a "M" or "H")
            //  if it's legal, we'll print what the attack caused: miss, hit or drowning
            // chang the board accordingly

        // pc's attack
          // the computer's attack will be random, if the computer attacks a tile that has been attacked, we'll just ask for another
          // tile until we get a legal one
            // print what tile was attacked by the computer, and what it caused: miss, hit or drowning
            // change the board accordingly
            // print our current board game

        // AFTER EACH ATTACK - not only we'll edit the board of who was attacked
          // before that, if it was a hit we'll firstly change the battleship's state in our array
          // if it makes their state go to zero we had a drowning
          // after each drowning we'll check is any of the players got all of their battleships drowned

        // how will we work with our boards?
          //


        // TODO: Add your code here (and add more methods).
    }


    public static void main(String[] args) throws IOException {
        String path = args[0];
        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Total of " + numberOfGames + " games.");

        for (int i = 1; i <= numberOfGames; i++) {
            scanner.nextLine();
            int seed = scanner.nextInt();
            rnd = new Random(seed);
            scanner.nextLine();
            System.out.println("Game number " + i + " starts.");
            battleshipGame();
            System.out.println("Game number " + i + " is over.");
            System.out.println("------------------------------------------------------------");
        }
        System.out.println("All games are over.");
    }
}
