import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static Random rnd;

    public static boolean isOrienLegal(int orientation){
        return (orientation == 0 || orientation == 1);
    }

    public static boolean isTileInBoard(int row, int col, int x, int y){
        if(x >= row || x < 0) return false;
        if(y >= col || y < 0) return false;
        return true;
    }

    public static boolean isSubOverlapHor(int x,int y, int size, char[][] array){
        for(int i = 0 ; i < size ; i++){
            if(array[x][y+i] != 'X' || array[x][y+i] != '—' ) return false;
        }
        return true;
    }

    public static boolean isSubOverlapVer(int x, int y, int size, char[][] array){
        for(int i = 0 ; 0 < size ; i++){
            if(array[x+i][y] != 'X' || array[x+i][y] != '—') return false;
        }
        return true;
    }

    public static boolean isSubAdjHor (int x , int y, int size, char[][] array){
        for(int i = 0 ; i < size ; i++){
            if(array[x][y+i] == 'X') return false;
        }
        return true;
    }

    public static boolean isSubAdjVer (int x, int y, int size, char[][] array){
        for(int i = 0 ; i < size ; i++){
            if(array[x+i][y] == '—') return false;
        }
        return true;
    }

    public static boolean isSubInBoardHor(int y,int col,int size){
        if (y+size-1>=col) return false;
        return true;
    }

    public static boolean isSubInBoardVer(int x, int row, int size){
        if(x+size-1 >= row) return false;
        return true;
    }

    public static void putSubHor(int x, int y, int size,int sub, char[][] array){
        for (int  i = 0 ; i < size ; i++){
            array[x][y+i] = (char)sub;
        }
    }

    public static void putSubVer(int x, int y, int size,int sub, char[][] array){
        for (int  i = 0 ; i < size ; i++){
            array[x+i][y] = (char)sub;
        }
    }

    public static void putXHor(int x, int y, int size,int row , int col, char[][] array){
        for(int i = -1; i <= size ; i++){
            for(int j = -1 ; j <= 1 ; j++ ){
                if( (j+x >= 0) && (j+x <row) && (i+y >= 0) && (i+y <col)){
                    if(array[j+x][i+y] == '—') array[j+x][i+y] = 'X';
                }
            }
        }
    }

    public static void putXVer(int x, int y, int size,int row , int col, char[][] array){
        for(int i = -1; i <= size ; i++){
            for(int j = -1 ; j <= 1 ; j++ ){
                if( (i+x >= 0) && (i+x <row) && (j+y >= 0) && (j+y <col)){
                    if(array[i+x][j+y] == '—') array[i+x][j+y] = 'X';
                }
            }
        }
    }

    public static void dash2DArray(int row, int col, char[][] array){
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                array[i][j] = '—';
            }
        }
    }

    public static void initializeBattleshipState(int[] init, int[][] data){
        int index = 0;
        for ( int i = 0; i < data.length; i++){
            for ( int j = 0; j < data[i][0]; j++){

                init[index] = data[i][1];
                index += 1;

            }
        }
    }

    public static void setBoardPC(int row, int col, int[] pcBattleshipState, char[][] pcBoard,int quantitySum){
        int i = 0, x, y ,orientation;
        while (i< quantitySum){

            while (true){
                x = rnd.nextInt(row);
                y =rnd.nextInt(col);
                orientation = rnd.nextInt(2);

                if (orientation == 0){
                    if(!isSubInBoardHor(y,col,pcBattleshipState[i])){
                        continue;
                    } else if (!isSubOverlapHor(x, y, pcBattleshipState[i], pcBoard)) {
                        continue;
                    } else if (!isSubAdjHor(x, y , pcBattleshipState[i], pcBoard)) {
                        continue;
                    }
                }
                else {
                    if(!isSubInBoardVer(x,row,pcBattleshipState[i])){
                        continue;
                    } else if (!isSubOverlapVer(x, y, pcBattleshipState[i], pcBoard)) {
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

    public static void fromXtodash(char[][] board){

        for ( int i = 0; i < board.length; i++){
            for ( int j = 0; j < board[0].length; j++){

                if ( board[i][j] == 'X' ) { board[i][j] = '—';}

            }
        }
    }

    public static void organizeBattleshipData(int[][] eachBattleshipData, String[] battleshipsDataSplit ){
        for (int i = 0; i < battleshipsDataSplit.length; i++){

            String[] eachOne = battleshipsDataSplit[i].split("X");

            int quantity = Integer.parseInt(eachOne[0]);
            int size = Integer.parseInt(eachOne[1]);

            eachBattleshipData[i][0] = quantity;
            eachBattleshipData[i][1] = size;
        }
    }

    public static void printGameBoard(char[][] board){

        // according to the board we have defined in the game:
        // 'M' -> 'X'
        // 'H' -> 'V'
        // a number, represents a battleship -> '—'
        // '—' -> '—'

        char[][] to_print = new char[(board.length) + 1][(board[0].length) + 1];
        to_print[0][0] = ' ';
        // enumerate columns
        for(int i = 1; i < to_print[0].length; i++){
            to_print[0][i]  = (char)(i - 1);
        }
        // enumerate rows - how to correct the spacement when there is only one number?
        for(int i = 1; i < to_print.length; i++){
            to_print[i][0] = (char)(i-1);
        }

        // put the board data inside the to_print board
        for(int i = 1; i < to_print.length; i++){
            for(int j = 1; j < to_print[0].length; j++){

                to_print[i][j] = board[i-1][j-1];

            }
        }

        // we'll firstly print the first row, that indicates the boards columns
        for(int i = 0; i < to_print[0].length; i++){
            System.out.print(to_print[0][i] + ' ');
        }

        for(int i = 1; i < to_print.length; i++){ // goes through each line

            // print the number that indicates each one of the boards rows
            System.out.print(to_print[i][0] + ' ');

            for( int j = 1; j < to_print[0].length; j++){ // goes through each column

                if( to_print[i][j] == 'M'){
                    System.out.print('—' + ' ');
                } else if ( to_print[i][j] == 'H'){
                    System.out.print('X' + ' ');
                } else if (to_print[i][j] == '—'){
                    System.out.print('—' +' ');
                } else {System.out.print('#' + ' ');}

            }
            // after we finish printing each line, skip down to start printing the next one
            System.out.println();
        }
        // after we finish printing each board, print a clear line
        System.out.println();

    }

    public static void printGuessingBoard(char[][] board){

        // according to the board we have defined in the game:
        // 'M' -> '—'
        // 'H' -> 'X'
        // a number, represents a battleship -> '#'
        // '—' -> '—'

        char[][] to_print = new char[(board.length) + 1][(board[0].length) + 1];
        to_print[0][0] = ' ';
        // enumerate columns
        for(int i = 1; i < to_print[0].length; i++){
            to_print[0][i]  = (char)(i - 1);
        }
        // enumerate rows - how to correct the spacement when there is only one number?
        for(int i = 1; i < to_print.length; i++){
            to_print[i][0] = (char)(i-1);
        }
        // put the board data inside the to_print board
        for(int i = 1; i < to_print.length; i++){
            for(int j = 1; j < to_print[0].length; j++){

                to_print[i][j] = board[i-1][j-1];

            }
        }
        // we'll firstly print the first row, that indicates the boards columns
        for(int i = 0; i < to_print[0].length; i++){
            System.out.print(board[0][i] + ' ');
        }

        for(int i = 1; i < to_print.length; i++){ // goes through each line

            // print the number that indicates each one of the boards rows
            System.out.print(to_print[i][0] + ' ');

            for( int j = 1; j < to_print[0].length; j++){ // goes through each column

                if( to_print[i][j] == 'M'){
                    System.out.print('X' + ' ');
                } else if ( to_print[i][j] == 'H'){
                    System.out.print('V' + ' ');
                } else{System.out.print('—' + ' ');}

            }
            // after we finish printing each line, skip down to start printing the next one
            System.out.println();
        }
        // after we finish printing each board, print a clear line
        System.out.println();

    }

    public static void battleshipGame() {

        // get board size
        System.out.println("Enter the board size");
        String sizeOfBoard = scanner.nextLine();
        // after getting from user the board size, we'll divide the string by the given separator "X"
        // this way we'll get how many rows and columns our boards will have
        String[] sizeOfBoardSplit= sizeOfBoard.split("X");
        // in the array sizeOfBoardSplit, the number of rows and column will be defined as a string
        // so to get its value in the integer data type we'll use the function parseInt
        int row = Integer.parseInt(sizeOfBoardSplit[0]);
        int col = Integer.parseInt(sizeOfBoardSplit[1]);

        // make 2 boards - initialized to '—' - one for user and one for pc
        char[][] userBoard = new char[row][col];
        char[][] pcBoard = new char[row][col];

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
        for (int i = 0; i < eachBattleshipData.length; i++){
             quantitySum += eachBattleshipData[i][0];
        }

        int[] userBattleshipState = new int[quantitySum];
        int[] pcBattleshipState = new int[quantitySum];

        initializeBattleshipState(userBattleshipState, eachBattleshipData);
        initializeBattleshipState(pcBattleshipState, eachBattleshipData);
          // create 2 arrays, for each one of the players
            // each array will represent the user's battleships state
            // from the smaller to the biggest battleship - each character is a battleship


        // "DESIGN" of each board - user and pc
          // getting the coordinations for each battleship
        int x, y, orientation,  i = 0;
        String placement;
        String[] placementSplit;
        while (i < quantitySum){
            System.out.println("Your current game board:");

            //needs implementation printGameBoard(); -> after each legal battleship placed and before the first placement

            System.out.println("Enter location and orientation for battleship of size " + userBattleshipState[i]);
            // we'll check if the coordinations are legal, if not, the user will have to give new different ones
            // a message to get input won't appear this time
            while (true){
                placement = scanner.nextLine();
                placementSplit = placement.split(",");
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
                    } else if (!isSubOverlapHor(x, y, userBattleshipState[i], userBoard)) {
                        System.out.println("Battleship overlaps another battleship, try again!");
                        continue;

                    } else if (!isSubAdjHor(x, y, userBattleshipState[i], userBoard)) {
                        System.out.println("Adjacent battleship detected, try again!");
                        continue;
                    }
                }
                else {
                    if(!isSubInBoardVer(x, row, userBattleshipState[i])){
                        System.out.println("Battleship exceeds the boundaries of the board, try again!");
                        continue;
                    } else if (!isSubOverlapVer(x, y, userBattleshipState[i], userBoard)) {
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
            }
            else{
                putSubVer(x, y, userBattleshipState[i], i, userBoard);
                putXVer(x ,y ,userBattleshipState[i], row, col, userBoard);
            }
            i++;

        }

        // randomly set the computer's battleships in its board
        setBoardPC(row,col,pcBattleshipState,pcBoard,quantitySum);

        // after the battleships have been set, we'll get rid of the "service "X"" we used to make sure there wouldn't be any adjacent battleships
        fromXtodash(userBoard);
        fromXtodash(pcBoard);


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
