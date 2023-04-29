import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static Random rnd;

    /**
     * @param orientation
     * Returns if the orientation received is legal or not
     */
    public static boolean isOrienLegal(int orientation){
        return (orientation == 0 || orientation == 1);
    }

    public static boolean isTileInBoard(int row, int col, int x, int y){
        return (!((x >= row || x < 0) || (y >= col || y < 0)));
    }

    public static boolean isSubOverlapHor(int x,int y, int size, String[][] array){
        for(int i = 0 ; i < size ; i++){
            if(!(array[x][y+i].equals("X") || array[x][y+i].equals("—"))) return true;
        }
        return false;
    }

    public static boolean isSubOverlapVer(int x, int y, int size, String[][] array){
        for(int i = 0 ; i < size ; i++){
            if(!(array[x+i][y].equals("X") || array[x+i][y].equals("—"))) return true;
        }
        return false;
    }

    public static boolean isSubAdjHor (int x , int y, int size, String[][] array){
        for(int i = 0; i < size; ++i) {
            if (array[x][y + i].equals("X")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSubAdjVer (int x, int y, int size, String[][] array){
        for(int i = 0; i < size; ++i) {
            if (array[x + i][y].equals("X")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSubInBoardHor(int y,int col,int size){
        return !(y+size-1 >= col);
    }

    public static boolean isSubInBoardVer(int x, int row, int size){
        return !(x+size-1 >= row);
    }

    public static void putSubHor(int x, int y, int size,int sub, String[][] array){
        for (int  i = 0 ; i < size ; i++){
            array[x][y+i] = Integer.toString(sub);
        }
    }

    public static void putSubVer(int x, int y, int size,int sub, String[][] array){
        for (int  i = 0 ; i < size ; i++){
            array[x+i][y] = Integer.toString(sub);
        }
    }

    public static void putXHor(int x, int y, int size,int row , int col, String[][] array){
        for(int i = -1; i <= size ; i++){
            for(int j = -1 ; j <= 1 ; j++ ){
                if( (j+x >= 0) && (j+x <row) && (i+y >= 0) && (i+y <col)){
                    if(array[j+x][i+y].equals("—")) array[j+x][i+y] = "X";
                }
            }
        }
    }

    public static void putXVer(int x, int y, int size,int row , int col, String[][] array){
        for(int i = -1; i <= size ; i++){
            for(int j = -1 ; j <= 1 ; j++ ){
                if( (i+x >= 0) && (i+x <row) && (j+y >= 0) && (j+y <col)){
                    if(array[i+x][j+y].equals("—")) array[i+x][j+y] = "X";
                }
            }
        }
    }

    /**
     *
     * input: the size of the board and a board
     * it fills the entire board with dashes
     * output: none
     */
    public static void dash2DArray(int row, int col, String[][] array){
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                array[i][j] = "—";
            }
        }
    }

    /**
     *
     * input: gets a 1D array and a 2D array containing information on the battleships
     * fills the 1D array by size of battleship
     * output: none
     */
    public static void initializeBattleshipState(int[] init, int[][] data){
        int index = 0;
        for ( int i = 0; i < data.length; i++){
            for ( int j = 0; j < data[i][0]; j++){
                init[index] = data[i][1];
                index += 1;
            }
        }
    }

    /**
     *
     * input: the size of the board, a board, how many battleships are there and of what kinds
     * chooses randomly where the PC shall place its battleships
     * output: none
     */
    public static void setBoardPC(int row, int col, int[] pcBattleshipState, String[][] pcBoard,int quantitySum){
        int i = 0, x, y ,orientation;
        while (i < quantitySum){

            while (true){
                x = rnd.nextInt(row);
                y = rnd.nextInt(col);
                orientation = rnd.nextInt(2);

                if (orientation == 0){
                    if(!isSubInBoardHor(y,col,pcBattleshipState[i])){
                        continue;
                    } else if (isSubOverlapHor(x, y, pcBattleshipState[i], pcBoard)) {
                        continue;
                    } else if (isSubAdjHor(x, y , pcBattleshipState[i], pcBoard)) {
                        continue;
                    }
                }
                else {
                    if(!isSubInBoardVer(x,row,pcBattleshipState[i])){
                        continue;
                    } else if (isSubOverlapVer(x, y, pcBattleshipState[i], pcBoard)) {
                        continue;
                    } else if (!isSubAdjVer(x, y , pcBattleshipState[i], pcBoard)) {
                        continue;
                    }
                }
                break;
            }
            if(orientation == 0){
                putSubHor(x,y,pcBattleshipState[i],i,pcBoard);
                putXHor(x , y ,pcBattleshipState[i], row, col, pcBoard);
            }
            else{
                putSubVer(x,y,pcBattleshipState[i],i,pcBoard);
                putXVer(x , y ,pcBattleshipState[i], row, col, pcBoard);
            }
            i++;

        }

    }

    /**
     *
     * input: a board
     * changes every dash on the array to an 'X'
     * output: none
     */
    public static void fromXtodash(String[][] board){

        for ( int i = 0; i < board.length; i++){
            for ( int j = 0; j < board[0].length; j++){
                if ( board[i][j].equals("X")){
                    board[i][j] = "—";
                }
            }
        }
    }

    /**
     *
     * input: a 2D array and an array containing battleships data in a aXb format
     * extracts the data from the 1D array and puts it in the 2D array
     * output: none
     */
    public static void organizeBattleshipData(int[][] eachBattleshipData, String[] battleshipsDataSplit ){
        for (int i = 0; i < battleshipsDataSplit.length; i++){

            String[] eachOne = battleshipsDataSplit[i].split("X");

            int quantity = Integer.parseInt(eachOne[0]);
            int size = Integer.parseInt(eachOne[1]);

            eachBattleshipData[i][0] = quantity;
            eachBattleshipData[i][1] = size;
        }
    }
    /**
     *
     * input: a board
     * prints the board that was inserted into the function according to how a regular board is printed
     * output: none
     */
    public static void printGameBoard(String[][] board){

        System.out.println("Your current game board:");

        int row = board.length;
        int col = board[0].length;
        int print_row = row+1;
        int print_col = col+1;

        String[][] to_print = new String[print_row][print_col];

        // enumerate columns
        for (int i = 1; i < to_print[0].length; i++) {
            to_print[0][i] = Integer.toString(i - 1);
        }

        // enumerate rows - gotta be careful with the spacements!
        for (int i = 1; i < to_print.length; i++) {
            if (row >= 100) {
                to_print[0][0] = "   ";
                if (i < 10) {
                    to_print[i][0] = (" " + Integer.toString(i - 1));
                } else if (i < 100) {
                    to_print[i][0] = ("  " + Integer.toString(i - 1));
                } else {
                    to_print[i][0] = Integer.toString(i - 1);
                }
            } else if (row >= 10) {
                to_print[0][0] = "  ";
                if (i <= 10) {
                    to_print[i][0] = (" " + Integer.toString(i - 1));
                } else {
                    to_print[i][0] = Integer.toString(i - 1);
                }
            }else{
                to_print[0][0] = " ";
                to_print[i][0] = Integer.toString(i - 1);
            }
        }

        // pass all the info from char to String in the new board - ready to print
        // 'M' -> '—'
        // 'H' -> 'X'
        // a number, represents a battleship -> '#'
        // '—' -> '—'
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                if(board[i][j].equals("M") || board[i][j].equals("—")){
                    to_print[i + 1][j + 1] = "–";
                } else if (board[i][j].equals("H")) {
                    to_print[i + 1][j + 1] = "X";
                } else if (board[i][j].equals("X")) {
                    to_print[i + 1][j + 1] = "–";
                }    else to_print[i + 1][j + 1] = "#";

            }
        }

        // print the printing board that is now ready!
        for (int i = 0; i < to_print.length; i++) {
            for (int j = 0; j < to_print[0].length; j++) {

                if(j == 0){
                    System.out.print(to_print[i][j]);
                } else{
                    System.out.print(" " + to_print[i][j]);
                }

            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     *
     * input: a board
     * prints the board that was inserted into the function according to how a guessing board is printed
     * output: none
     */
    public static void printGuessingBoard(String[][] board){

        System.out.println("Your current guessing board:");

        int row = board.length;
        int col = board[0].length;

        String[][] to_print = new String[row + 1][col + 1];

        // enumerate columns
        for (int i = 1; i < to_print[0].length; i++) {
            to_print[0][i] = Integer.toString(i - 1);
        }

        // enumerate rows - gotta be careful with the spacements!
        for (int i = 1; i < to_print.length; i++) {
            if (row >= 100) {
                to_print[0][0] = "   ";
                if (i < 10) {
                    to_print[i][0] = (" " + Integer.toString(i - 1));
                } else if (i < 100) {
                    to_print[i][0] = ("  " + Integer.toString(i - 1));
                } else {
                    to_print[i][0] = Integer.toString(i - 1);
                }
            } else if (row >= 10) {
                to_print[0][0] = "  ";
                if (i <= 10) {
                    to_print[i][0] = (" " + Integer.toString(i - 1));
                } else {
                    to_print[i][0] = Integer.toString(i - 1);
                }
            } else{
                to_print[0][0] = " ";
                to_print[i][0] = Integer.toString(i-1);
            }
        }

        // pass all the info from char to String in the new board - ready to print
        // 'M' -> 'X'
        // 'H' -> 'V'
        // a number, represents a battleship -> '—'
        // '—' -> '—'
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                if(board[i][j].equals("M")){
                    to_print[i + 1][j + 1] = "X";
                } else if (board[i][j].equals("H")) {
                    to_print[i + 1][j + 1] = "V";
                } else to_print[i + 1][j + 1] = "–";
            }
        }

        // print the printing board that is now ready!

        for (int i = 0; i < to_print.length; i++) {
            for (int j = 0; j < to_print[0].length; j++) {

                if(j == 0){
                    System.out.print(to_print[i][j]);
                } else{
                    System.out.print(" " + to_print[i][j]);
                }

            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     *
     * input: a point on the board and the board itself
     * checks if it's possible to attack the point on the board (excluding if it was attacked already)
     * output: T/F
     */
    public static boolean ilegalAttack(int x, int y, String[][] board){
        return (x < 0 || y < 0 || x >= board.length || y >= board[0].length);
    }

    /**
     *
     * input: a point on the board and the board itself
     * checks if the point on the board was already attacked
     * output: T/F
     */
    public static boolean tileAttacked(int x, int y, String[][] board){
        return(board[x][y].equals("M") || board[x][y].equals("H"));
    }

    /**
     * gets the PC's board and its battleship states
     * lets the user choose where to attack and calls attackPc()
     * output: none
     */
    public static void attackFromUser(String[][] pcBoard, int[] pcBattleshipState){
        // print user's guessing board
        printGuessingBoard(pcBoard);
        System.out.println("Enter a tile to attack");
        String attackCoordinates;
        int attack_x, attack_y;
        String[] getCoordinates;

        // get attack coordinates from user

            while(true){  // get new coordinate every time, until we get a legal one!

                attackCoordinates = scanner.nextLine();
                getCoordinates = attackCoordinates.split(", ");
                attack_x = Integer.parseInt(getCoordinates[0]);
                attack_y = Integer.parseInt(getCoordinates[1]);

                if (ilegalAttack(attack_x, attack_y, pcBoard)) {
                    System.out.println("Illegal tile, try again!");
                } else if (tileAttacked(attack_x, attack_y, pcBoard)) {
                    // if there is a 'M' or 'H' in the attacked tile
                    System.out.println("Tile already attacked, try again!");
                }
                else {
                    break;
                }
            }
            attackPc(attack_x, attack_y, pcBoard, pcBattleshipState);
    }

    /**
     *
     * input: gets the point of attack, the board and the PC's battleships state
     * commences the attack on the PC by changing its board and its states
     * output: none
     */
    public static void attackPc(int x, int y, String[][] pcBoard, int[] pcBattleshipData){
        if( pcBoard[x][y].equals("—")){
            System.out.println("That is a miss!");
            pcBoard[x][y] = "M";
        } else{ // the cell won't be 'M' or 'H' because it passed the validity check already, so it's a battleship!
            int index = Integer.parseInt(pcBoard[x][y]);
            // update the pc's array
            pcBattleshipData[index] -= 1;
            // update the pc's board
            pcBoard[x][y] = "H";
            if(pcBattleshipData[index] == 0){
                int r = 0;
                // get how many battleships are left
                for(int i = 0; i < pcBattleshipData.length; i++){
                    if(pcBattleshipData[i] != 0){
                        r += 1;
                    }
                }
                System.out.println("That is a hit!");
                System.out.println("The computer's battleship has been drowned, " + r + " more battleships to go!");

            }else{
                System.out.println("That is a hit!");
            }
        }
    }

    /**
     *
     * input: gets the size of the board, the board , and the battleship data of the user
     * chooses random point to attack and calls the function to commence the actual attack
     * output: none
     */
    public static void attackFromPc(int row, int col, String[][] userBoard, int[] userBattleshipData){

        int x = rnd.nextInt(row);
        int y = rnd.nextInt(col);
        while(ilegalAttack(x, y, userBoard) || tileAttacked(x, y, userBoard)){
            x = rnd.nextInt(row);
            y = rnd.nextInt(col);
        }
        attackUser(x, y, userBoard, userBattleshipData);
    }
    /**
    * input: gets the where the PC attacked, the board and the users battleship current state
    * completes the attack function by changing the board of the user and the state of the one of the battleships if needed
    * output: none
     */
    public static void attackUser(int x, int y, String[][] userBoard, int[] userBattleshipData){

        System.out.println("The computer attacked (" + x + ", " + y + ")");

        if(userBoard[x][y].equals("—")){
            System.out.println("That is a miss!");
            userBoard[x][y] = "M";
            printGameBoard(userBoard);
        } else{ // the cell won't be 'M' or 'H' because it passed the validity check already, so it's a battleship!

                int index = Integer.parseInt(userBoard[x][y]);
            // update the user's array
            userBattleshipData[index] -= 1;
            // update the user's board
            userBoard[x][y] = "H";
            if(userBattleshipData[index] == 0){
                int r = 0;
                // get how many battleships are left
                for(int i = 0; i < userBattleshipData.length; i++){
                    if(userBattleshipData[i] != 0){
                        r += 1;
                    }
                }
                System.out.println("That is a hit!");
                System.out.println("Your battleship has been drowned, you have left " + r + " more battleships!");
                printGameBoard(userBoard);
            }else{
                System.out.println("That is a hit!");
                printGameBoard(userBoard);
            }
        }
    }
    /**
     * input: an array of containing how many pieces of the battleships are still intact
     * checks if the entire array contains only zeroes
     * output: T/F
     */
    public static boolean haveBattleshipsLeft(int[] someBattleshipData){
        int r = 0;
        for ( int i = 0; i < someBattleshipData.length; i++){
            if(someBattleshipData[i] != 0){
                r += 1;
            }
        }
        return (r != 0);
    }
    /**
     * input: quantitySum, row, col, userBoard (2D array), userBattleshipState (1D array)
     * lets the user set up his board
     * output: none
     */
    public static void setBoardUser(int quantitySum, int row, int col, String[][] userBoard, int[] userBattleshipState){
        int x, y, orientation,  i = 0;
        String placement;
        String[] placementSplit;
        printGameBoard(userBoard);

        while (i < quantitySum){

            System.out.println("Enter location and orientation for battleship of size " + userBattleshipState[i]);
            // we'll check if the coordinations are legal, if not, the user will have to give new different ones
            // a message to get input won't appear this time
            while (true){
                placement = scanner.nextLine();
                placementSplit = placement.split(", ");
                x = Integer.parseInt(placementSplit[0]);
                y = Integer.parseInt(placementSplit[1]);
                orientation = Integer.parseInt(placementSplit[2]);

                // creating 5 functions that will check if the coordination is legal

                if(!isOrienLegal(orientation)){
                    System.out.println("Illegal orientation, try again!");
                    continue;
                } else if (!isTileInBoard(row, col, x, y)) {
                    System.out.println("Illegal tile, try again!");
                    continue;
                }
                if (orientation == 0){
                    if(!isSubInBoardHor(y, col, userBattleshipState[i])){
                        System.out.println("Battleship exceeds the boundaries of the board, try again!");
                        continue;
                    } else if (isSubOverlapHor(x, y, userBattleshipState[i], userBoard)) {
                        System.out.println("Battleship overlaps another battleship, try again!");
                        continue;

                    } else if (isSubAdjHor(x, y, userBattleshipState[i], userBoard)) {
                        System.out.println("Adjacent battleship detected, try again!");
                        continue;
                    }
                }
                else {
                    if(!isSubInBoardVer(x, row, userBattleshipState[i])){
                        System.out.println("Battleship exceeds the boundaries of the board, try again!");
                        continue;
                    } else if (isSubOverlapVer(x, y, userBattleshipState[i], userBoard)) {
                        System.out.println("Battleship overlaps another battleship, try again!");
                        continue;

                    } else if (!isSubAdjVer(x, y, userBattleshipState[i], userBoard)) {
                        System.out.println("Adjacent battleship detected, try again!");
                        continue;
                    }
                }
                break;
            }
            // when we get legal orientations, we'll place the submarines in the board!
            // on the tiles around each battleship, for now, we'll put a sign "X" that will tell us it's an illegal tile
            // on each tile where there is a battleship, the index of said battleship will be written in the tile
            // the index according to the userBattleshipState/pcBattleshipState array created earlier

            if(orientation == 0){
                putSubHor(x, y, userBattleshipState[i], i, userBoard);
                putXHor(x, y, userBattleshipState[i], row, col, userBoard);
                printGameBoard(userBoard);
            }
            else{
                putSubVer(x, y, userBattleshipState[i], i, userBoard);
                putXVer(x ,y ,userBattleshipState[i], row, col, userBoard);
                printGameBoard(userBoard);
            }
            i++;

        }

    }
    /**
     * input: none
     * acts as the base of the game and calls the relevant fucntions for the game to work
     * output: none
     */
    public static void battleshipGame() {

        // get board size
        System.out.println("Enter the board size");
        String sizeOfBoard = scanner.nextLine();

        String[] sizeOfBoardSplit = sizeOfBoard.split("X");
        // in the array sizeOfBoardSplit, the number of rows and column will be defined as a string
        // so to get its value in the integer data type we'll use the function parseInt
        int row = Integer.parseInt(sizeOfBoardSplit[0]);
        int col = Integer.parseInt(sizeOfBoardSplit[1]);

        // make 2 boards - initialized to '—' - one for user and one for pc
        String[][] userBoard = new String[row][col];
        String[][] pcBoard = new String[row][col];

        dash2DArray(row, col, userBoard);
        dash2DArray(row, col, pcBoard);

        // get battleship sizes + numbers of each battleship
        System.out.println("Enter the battleships sizes");
        String battleshipsData = scanner.nextLine();
        // we'll create an array in which each of the objects in it will represent the quantity of a certain
        // size of battleship, as described on the information about the game
        String[] battleshipsDataSplit = battleshipsData.split(" ");

        int[][] eachBattleshipData = new int[battleshipsDataSplit.length][2];

        organizeBattleshipData(eachBattleshipData, battleshipsDataSplit);

        int quantitySum = 0;
        for (int i = 0; i < eachBattleshipData.length; i++) {
            quantitySum += eachBattleshipData[i][0];
        }

        int[] userBattleshipState = new int[quantitySum];
        int[] pcBattleshipState = new int[quantitySum];

        //create 2 arrays, for each one of the players! each array represents their battleships states
        // each cell represents how many "parts" are left from each battleship
        initializeBattleshipState(userBattleshipState, eachBattleshipData);
        initializeBattleshipState(pcBattleshipState, eachBattleshipData);


        // "DESIGN" of each board - user and pc

        // set the user's board
        setBoardUser(quantitySum, row, col, userBoard, userBattleshipState);
        // randomly set the computer's battleships in its board
        setBoardPC(row, col, pcBattleshipState, pcBoard, quantitySum);

        // after the battleships have been set, we'll get rid of the "service 'X'" we used to make sure there wouldn't be any adjacent battleships
        fromXtodash(userBoard);
        fromXtodash(pcBoard);

        // * GAME STARTS! *
        // "M" == miss; "H" == hit

        // while there are battleships left for both of the players: the game keeps on going
        // otherwise someone wins!
        while (haveBattleshipsLeft(userBattleshipState) && haveBattleshipsLeft(pcBattleshipState)) {

            // user's attack
            attackFromUser(pcBoard, pcBattleshipState);
            if (!haveBattleshipsLeft(pcBattleshipState)) {
                System.out.println("You won the game!");
                break;
            }

                //pc's attack
            attackFromPc(row, col, userBoard, userBattleshipState);
            if (!haveBattleshipsLeft(userBattleshipState)) {
                System.out.println("You lost ):");
            }


        }
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
