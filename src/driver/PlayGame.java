package driver;

import driver.gui.MainFrame;
import resources.Hangman;

import java.util.Scanner;

public class PlayGame {
    public static int gamesWon = 0;
    public static int gamesLost = 0;

    public static void main(String[] args) {

        Hangman game = new Hangman();
        MainFrame gameFrame = new MainFrame(game);

        /* boolean playAgain;
        do{
            Hangman game = new Hangman();
            processGuesses(game);
            determineWinner(game);

            playAgain = playAgain();
        }while(playAgain);

        */
    }

    public static void processGuesses(Hangman game){
        Scanner scan = new Scanner(System.in);                    // Scanner used to get user input
        int numberOfGuesses = 3 * game.getSecretWord().length();  // the user has (3 * the number of letter) to guess

        System.out.println("=======================================");
        System.out.println("--------- Let's play Hangman! ---------");  // Title
        System.out.println("=======================================");

        // Game starts here
            boolean gameHasFinished = false;
             do{
                if (game.getGuessNumber() < numberOfGuesses && !(game.wordCorrect())) {     // checks if the guess number is less than the number of guesses allowed
                    System.out.println("===========================================");
                    System.out.println("-- You have " + (numberOfGuesses - game.getGuessNumber()) + " guesses --");    // displays how many guesses left
                    System.out.println("Guess this word: " + game.getGuessedWord());     // displays the guessedWord so far
                    System.out.println("type in your guess below:\n(Must be a letter)");
                    String answer = scan.nextLine();                                     // Reads answer
                    if (answer.length() > 1 || answer.trim().equals("")) {                                           // ensures the answer was only one letter
                        System.out.println("=================================");
                        System.out.println("You typed more than one letter...");
                        System.out.println("-------- Let's try again -------");
                        System.out.println("=================================");
                    } else {
                        game.checkGuess(answer);                                        // checks if the guess has already been made
                    }
                } else {
                    gameHasFinished = true;                                             // if exceeded number of guesses or word is correct will end loop
                }
            }while(!gameHasFinished);
    }

    public static void determineWinner(Hangman game){
        if(game.getGuessedWord().equalsIgnoreCase(game.getSecretWord())){
            System.out.println("You've Won!!!");
            gamesWon++;
        }else{
            System.out.println("Oh no you've lost :(");
            gamesLost++;

        }
    }

    //This method asks the user if they would like to play again
    //      Returns true if they
    public static boolean playAgain(){
        boolean result = false;
        Scanner scan = new Scanner(System.in);

        System.out.println("=============================");
        System.out.println("Would you like to play again? Y/N"); // The question
        String answer = scan.nextLine();    // The answer
        // This line ensures that they answer with a 'y' or 'n'
        while(!(answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("n"))){
            System.out.println("Please type Y or N"); // asks them again
            answer = scan.nextLine();
        }
        if(answer.equalsIgnoreCase("y")){
            result = true;
        }
        return result;
    }
}
