package treasurehunt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameSetUp {

    Scanner sc = new Scanner(System.in);
    int turn;
    int[][] board;
    int[][] treasureBoard;
    int pos;
    Player players[];
    int treasureCount;

    public GameSetUp() throws FileNotFoundException {
        //this is where the game will basically happen. here the game gonna be played in a loop
        //that will keep going as long as everyone have dig points left and as long as there are treasures in the map

        int index = 0;
        Player player;
        printHeader();
        printRules();
        validationRules();
        System.out.println("Welcome to the game: ");

        for (int i = 0; i < players.length; i++) {
            System.out.println(players[i].getName());
        }

        makeBoard();//make the logic board
        settingTreasure();//take the map that has the treasure in it
        while (true) {
            player = players[index];//amount of players start at 0.
            int sumDigPoints = 0;
            System.out.println("There are " + treasureCount + " Treasures left.");
            printBoard();//print the visual board, the one the user will see and input

            for (int i = 0; i < players.length; i++) {
                sumDigPoints += players[i].getDigPoints();

            }
            if (sumDigPoints == 0 || treasureCount == 0) {//if statement to stop the game when the players have run out
                break;                                    //of dig points or when there are no more treasures to be found
            }

            System.out.println("Pirate " + player.getName() + " your turn");
            System.out.println("You have " + player.getDigPoints() + " Dig Points left.");
            System.out.println("You have " + player.getPiratePoints() + " Pirate Points.");

            if (player.getDigPoints() > 0) {//check if he still have digpoints so he can play
                if (getUserInput()) { 
                    player.addPiratePoint();
                }
                player.decreaseDigPoint();//subtract 1 digpoint after hes made the move.
            }

            if (index < players.length - 1) {//add the players
                index++;

            } else {
                index = 0;
            }
        }
        for (int i = 0; i < players.length; i++) {
            //a for loop just to print some info like digpoints and piratepoints the players have everytime its their turn.
            System.out.println("Pirate " + players[i].getName() + " your turn");
            System.out.println("You have " + players[i].getDigPoints() + " Dig Points left.");
            System.out.println("You have " + players[i].getPiratePoints() + " Pirate Points left.");
            System.out.println("\n");
        }
        
        Player tmp;
    //bubble sort to sort whoever have more pirate points
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < players.length - 1; j++) { 
        
                if (players[j].getPiratePoints() < players[j + 1].getPiratePoints()) {

                    tmp = players[j];
                    players[j] = players[j + 1];
                    players[j + 1] = tmp;
                }
            }
        }

        if (players[0].getPiratePoints() > players[1].getPiratePoints()) {//whoever have more piratepoints at the end of game is the winner

            System.out.println(players[0].getName() + " is the winner!!!");
            return;
        }
        
        int count = 0;
        for (int i = 0; i < players.length && players[0].getPiratePoints() == players[i].getPiratePoints(); i++) {
            count++;
        }
       //another bubble sort to sort whoever have more digpoints
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count - 1; j++) {

                if (players[j].getDigPoints() < players[j + 1].getDigPoints()) {

                    tmp = players[j];
                    players[j] = players[j + 1];
                    players[j + 1] = tmp;
                }
            }
        }

        System.out.println(players[0].getName() + " is the winner!!!");

    }

    public void printHeader() {

        System.out.println("\t" + "AAArghh...WELCOME TO THE TREASURE HUNT GAME AAARRrrrrgh...\n");
    }

    public void printRules() { //just to print the rules for players acknowledgment

        System.out.println(" Game Rules: \n");
        System.out.println("1) The minimum age to play the game is 12.");
        System.out.println("2) A square can only be dug once.");
        System.out.println("3) If any treasure is found, 20 Pirate Points are going to be rewarded to the pirate.");
        System.out.println("4) Whenever you make a move, 1 Dig Point is subtracted.");
        System.out.println("5) If you have no Dig Points left you miss your turn.");
        System.out.println("6) The game ends when no one have any Dig Points left or when all the treasures are found.\n");
    }

    public void validationRules() {

        //main purpose of the validation rules is to make sure the user is eligible to play the game
        //and see if all the information given by user are accepted by the rules
        Scanner kb = new Scanner(System.in);
        //all the booleans were set to false because they dont get a pass till the input is validated;
        boolean playersCheck = false;
        boolean numberCheck = false;
        boolean nameCheck = false;
        int playerAge = 0;
        int playersNum = 0;
        String name;
        //firstName[]     
        //loop will keep going until user enter a number between 2 and 4
        //loop will keep going until the user is > 12

        do {

            System.out.print("Please enter the number of players: ");
            try {
                playersNum = Integer.parseInt(sc.nextLine());

            } catch (NumberFormatException ex) {

                System.out.println("Only NUMBERS allowed!");

            }
            if (playersNum < 2 || playersNum > 4) {

                System.out.println("Invalid amount of players!!! Min. 2 Max. 4");
            }
        } while ((playersNum < 2 || playersNum > 4));

        players = new Player[playersNum];

        for (int i = 0; i < players.length; i++) {

            do {

                System.out.println("Please enter the first and last name of Pirate " + (i + 1) + ":");

                name = sc.nextLine();

                nameCheck = false;

                //split the spaces. [a-z A-Z]+ with the space in between to allow the scanner to read it.
                if (!name.matches("[a-z A-Z]+") || name.split(" ").length != 2) { //simple if statement to make sure user only input letters
                    nameCheck = false;
                } else {
                    nameCheck = true;
                }
            } while (nameCheck == false);

            do {
                System.out.println("Please enter the age of Pirate " + (i + 1) + ":");

                try { //used a try and catch so it can catch any string exception and notify the user and allow
                    //and allow them to enter input again

                    playerAge = Integer.parseInt(kb.next());

                    numberCheck = true;

                    if (playerAge < 12) {
                        System.out.println("AArrgghh YOURE NOT STRONG ENOUGH TO BE A PIRATE AARgghhghh..."
                                + "The minimum age to play the game is 12!!!");
                        numberCheck = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Only NUMBERS allowed!!!");
                    numberCheck = false;
                }
            } while (numberCheck == false);
            players[i] = new Player(name.split(" ")[0], name.split(" ")[1], playerAge);
            //split space characters to take the first name of the user.            
        }
    }

    public void makeBoard() {// this method will initialize grid and fill with empty spaces

        int i;
        int j;

        board = new int[10][10];// standard 10 x 10 board - rem: array index starts at 0

        for (i = 0; i < board.length; i++) {
            //rows
            for (j = 0; j < board[i].length; j++) {
                //  columns
                board[i][j] = 0;
            }
        }
    }

    public void printBoard() {
    //*this method will actually print the board and fill it with the rows and columns labels 
        //and will be filled with the users' input: D if dug already or X if treasure been found

        // board = new int[10][10];
        String[] nums = {"  1", "  2", "  3", "  4", "  5", "  6", "  7", "  8", "  9", " 10"};
        // char letters = 65;
        String[] letters = {"    A", "  B", "  C", "  D", "  E", "  F", "  G", "  H", "  I", "  J"};
        for (int i = 0; i < board.length; i++) {

            System.out.print(letters[i]);
            //   letters++;
        }
        System.out.println("");

        for (int i = 0; i < board.length; i++) {
            System.out.print(nums[i]);

            for (int j = 0; j < board.length; j++) {

                if (board[i][j] == 0) { //empty index 
                    System.out.print("__|");
                } else if (board[i][j] == 1) { //when user input wont be an empty index so print d|
                    System.out.print("_X|");
                } else if (board[i][j] == -1) { //-1 already filled position with the treasure location so print x|

                    System.out.print("_d|");

                }
            }
            System.out.println();//jump one line
        }
    }

    public boolean getUserInput() {

        String rowCheck;
        String columnCheck;
        int col = 0;
        char row = 0;

        //variables for pirate information 
        do {
            System.out.println("Please enter a letter: ");
            try {
                rowCheck = sc.next();
                row = rowCheck.toUpperCase().charAt(0);//convert letters to uppercase

                if (rowCheck.length() > 0 || rowCheck.length() < 65) {
                    continue;
                }

            } catch (Exception e) {
                System.out.println("Letters must be between A-J!!!");
            }
        } while (row < 65 || row > 74);//do the loop while the user hasnt input a letter between A-J
                 //65 = A & 74 = J as in ASCII code

        do {
            System.out.println("Please enter a NUMBER: ");

            try {

                columnCheck = sc.next();
                col = Integer.parseInt(columnCheck);

            } catch (NumberFormatException e) {
                System.out.println("NUMBERS must be between 1-10!!!");
            }
        } while (col < 1 || col > 10);
        int cell = col;
        char rowCell = row;

       //-1 = treasure location, 1 = user already input, 0 = empty index
        if (board[cell - 1][rowCell - 65] == 0) {
            if (treasureBoard[cell - 1][rowCell - 65] == -1) {
           
                System.out.println("Treasure found!!!");
                board[cell - 1][rowCell - 65] = -1;
                treasureCount--;
                return true;
            } else {
                System.out.println("Empty square!!");
                board[cell - 1][rowCell - 65] = 1;
            }
        } else {
            System.out.println("Square already used!!!");
        }
        return false;
    }

    public void settingTreasure() throws FileNotFoundException {
        //this method will read the treasure location from the text file stored in the netbeans project files
        treasureCount = 0;
        File file = new File("treasure.txt");
        Scanner fl = new Scanner(file);
        int row;
        char col;
        treasureBoard = new int[10][10];
        while (fl.hasNextLine()) { //keep reading as long as it has a line to be read

            col = fl.nextLine().charAt(0);
            row = Integer.parseInt(fl.nextLine());

            treasureBoard[row - 1][col - 65] = -1;// -1 to convert to array location
            treasureCount++;//keep track of the amount of treasures in the map
        }
    }
}
