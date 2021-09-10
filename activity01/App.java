import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;  // Import the Scanner class
import java.util.concurrent.TimeUnit;

public class App {
    /*Below are App Fields**/
    static String ascii = "0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]{}^_`abcdefghijklmnopqrstuvwxyz";

    //Below are helper methods
    //Copy Pasta for Reading Input @ Java Course during College
    public BufferedReader getReader() {
        BufferedReader reader= new BufferedReader (new InputStreamReader(System.in));
        return reader;
    }

    public String readString(String message) {
        String input= null;
        try {
            System.out.print(message + ": ");
            input= getReader().readLine();
        }
        catch(Exception e) {
        }
        return input;
    }

    public int readInt(String message) {
        int input = 0;
		try {
			System.out.print(message + ": ");
			input= Integer.parseInt(getReader().readLine());
            return input;
		}
        catch(NumberFormatException  e){
            System.out.println("Must be an integer ");
            return readInt(message);
        }
		catch(Exception e) {
            System.out.println("Must be an integer ");
            return readInt(message);
		}
        
	}
    public int countChar(String str, String searchString){
        int count = 0;

        //Split the string
        String a[] = str.split("");

        for (int i = 0; i < a.length; i++) {
            if(searchString.equals(a[i])){
                count++;
            }
        }
        return count;
    }

    public String generateAsciiChar(String asciiChar){
        //Source @https://stackoverflow.com/questions/20536566/creating-a-random-string-with-a-z-and-0-9-in-java
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 3) { // length of the random string.
            int index = (int) (rnd.nextFloat() * asciiChar.length());
            salt.append(asciiChar.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public void randomizeMatrix(String arr[][]){
        //Generate random ASCII chars
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = generateAsciiChar(ascii); 
            }
        }
    }

    /**Below are the functionality methods*/
    public static void viewCommands(){
        System.out.println("COMMANDS LIST: ");
        System.out.println("[V]: view the commands ");
        System.out.println("[S]: users input search character/characters, identify number of occurance in the table, and identify index of search string ");
        System.out.println("[E]: specify index to update ");
        System.out.println("[P]: print table ");
        System.out.println("[R]: specify new dimensions, new random chars ");
        System.out.println("[X]: exit the programm ");
    }

    public void searchCommand(String[][] arr){
        //SEARCH 
        String searchString = readString("Enter search value ");
        int totalFound = 0;

        long startTime = System.nanoTime();
        for (int i = 0; i < arr.length; i++) { //Outer Loop
            for (int j = 0; j < arr[i].length; j++) { //Inner Loop
                if(arr[i][j].contains(searchString)){ //Compare search string to current search value
                    int count = countChar(arr[i][j], searchString);
                    System.out.println("Found " + searchString + " on ("+i+","+j+") with "+ count + " instances");
                    ++totalFound;
                }
            }
        }
        long stopTime = System.nanoTime();
        long duration =  (stopTime - startTime) / 1000000; 
        System.out.println("========================");
        System.out.println("Total substrings found: " + totalFound);
        System.out.println("Search took "+ duration +" ms");
    }

    public void updateCommand(String[][] arr){
        //Edit String
        try{
            String search = readString("Enter search index FORMAT (x,y) ");
        
            String[] indexArr = search.split(",");
    
            int specifyRow = Integer.parseInt(indexArr[0]);
            int specifyColumn = Integer.parseInt(indexArr[1]);

            System.out.println("FOUND Index ( "+specifyRow+","+specifyColumn+" ) with value " + arr[specifyRow][specifyColumn]);

            String newValue = readString("Enter new value ");

            arr[specifyRow][specifyColumn] = newValue;

        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Index not found");
        }
    }

    public void printTable(String arr[][]){
        
        int totalRows = 0;

        long startTime = System.nanoTime();
        //PRINT MATRIX 
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                ++totalRows;
                System.out.printf(arr[i][j] + " " );
            }
            System.out.println();
        }
        long stopTime = System.nanoTime();
        long duration = (stopTime - startTime) / 1000000;
        System.out.println("Found " + totalRows + " rows with execution speed of " + duration + " ms");
    }

    //Main App Method
    public static void main(String[] args) throws Exception {
        //Initialize some helper variables
        boolean isEnd = false;

        App app = new App();
        
        System.out.println("==========Exercise 01: Basic Java==========");

        //Row Input
        int row = app.readInt("Enter row ");
        //Col Input
        int column = appreadInt("Enter column ");

        String[][] twoDimensionalArr = new String[row][column];
        app.randomizeMatrix(twoDimensionalArr);

        do{
            String command = app.readString("Enter command ");

            switch(command.toUpperCase()){
                case "S":
                    app.searchCommand(twoDimensionalArr);
                    break;
                case "E":
                    app.updateCommand(twoDimensionalArr);
                    break;
                case "P":
                    app.printTable(twoDimensionalArr);
                    // printTable(twoDimensionalArr);
                    break;
                case "R":
                    row = app.readInt("Enter row ");
                    column = app.readInt("Enter column ");
                    twoDimensionalArr = new String[row][column];
                    app.randomizeMatrix(twoDimensionalArr);
                    break;
                case "V":
                    viewCommands();
                    break;
                case "X":
                    isEnd = true;
                    System.out.println("Thank you for using the programm! ");
                    break;
                default:
                    System.out.println("Wrong command, please input only as follows");
                    viewCommands();
                    break;
            }
        }while(isEnd == false);
    }

}
