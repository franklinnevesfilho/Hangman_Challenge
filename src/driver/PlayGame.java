package driver;

import resources.Hangman;

import java.util.Scanner;

public class PlayGame {
    public static int gamesWon;
    public static int gamesLost;

    public static void main(String[] args) {
        boolean playAgain;
        do{
            Hangman game = new Hangman();
            processGuesses(game);
            playAgain = playAgain();
        }while(playAgain);
    }

    public static void processGuesses(Hangman game){
        Scanner scan = new Scanner(System.in);                    // Scanner used to get user input
        int numberOfGuesses = 3 * game.getSecretWord().length();  // the user has (3 * the number of letter) to guess
        int guessesLeft = numberOfGuesses - game.getGuessNumber();
        System.out.println("=======================================");
        System.out.println("--------- Let's play Hangman! ---------");  // Title
        System.out.println("=======================================");

        // Game starts here
            boolean gameHasFinished = false;
            while(!gameHasFinished) {
                if (game.getGuessNumber() < numberOfGuesses || !(game.wordCorrect())) {     // checks if the guess number is less than the number of guesses allowed
                    System.out.println("-- You have " + guessesLeft + " guesses --");    // displays how many guesses left
                    System.out.println("Guess this word: " + game.getGuessedWord());     // displays the guessedWord so far
                    System.out.println("type in your guess below:\n(Must be a letter)");
                    String answer = scan.nextLine();                                     // Reads answer
                    if (answer.length() > 1) {                                           // ensures teh answer was only one letter
                        System.out.println("You typed more than one letter...");
                        System.out.println("-------- Let's try again -------");
                        System.out.println("=================================");
                        System.out.println("=================================");
                    } else {
                        game.checkGuess(answer);                                        // checks if the guess has already been made
                    }
                } else {
                    gameHasFinished = true;                                             // if exceeded number of guesses or word is correct will end loop
                }
            }
    }

    public static void determineWinner(){

    }

    //This method asks the user if they would like to play again
    //      Returns true if they do
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
