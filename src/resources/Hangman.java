package resources;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is used to hold all the values for the Hangman game
 *  The class uses a record 'SecretWorld' to store the secret word
 *     - The record makes it easier to compare and get information
 *     from the secretWord string.
 *  In order to play this game a driver must be used.
 */


public class Hangman {
// ==========================================================================================
//                          Global variables
// -----------------------------------------------------------------------------
    private final ArrayList<String> guessedLettersList = new ArrayList<>(); // Stores the letters that have been guessed
    private final SecretWord secretWord;                               // Stores the secret word
    private String guessedWord;                                       // Stores the guessed word so far
    private int guessNumber = 0;                                         // keeps track of how many guesses the user has made
    private final static String[] secretWords = {              // These are all the available secret words:
            "GAME",                                            //   Game
            "LETTUCE",                                         //   Lettuce
            "BLOCK",                                           //   Block
            "SPOON",                                           //   Spoon
            "CONTROLLER",                                      //   Controller
            "SWORD",                                           //   Sword
            "WATER",                                           //   Water
            "BOTTLE"                                           //   Bottle
    };                                                         //  I've made these words all upperCase to simplify comparing
// ==========================================================================================
// ------------------------------------- Constructor ----------------------------------------
    public Hangman(){
        // generates random number within the range of the array
        Random random = new Random();
        int wordNumber = random.nextInt(0,secretWords.length);
        //The random number is used to select a random word from the array and
        //          initialize the secretWord object
        this.secretWord = new SecretWord(secretWords[wordNumber]);
        //This method creates the guessed word based on the secretWord
        guessedWord = generateGuessedWord(secretWord.toString());
    }
//===========================================================================================
//===========================================================================================
//----------------------------------- Getters -----------------------------------------------
    public String getGuessedWord(){
        return guessedWord;
    }
    public String getSecretWord(){
        return secretWord.toString();
    }
    public int getGuessNumber(){
        return guessNumber;
    }
    public ArrayList<String> getGuessedLettersList(){
        return guessedLettersList;
    }
//===========================================================================================
//===========================================================================================
//-------------------------------------------------------------------------------------------
//----------------------------------- Methods -----------------------------------------------
//-------------------------------------------------------------------------------------------
    // This method is only used when an object is first created
    // To ensure that the guessed word is the same length as the
    //                  Secret word
    public String generateGuessedWord(String secretWord){
        return "_".repeat(secretWord.length());
    }
    // This method checks if the letter guessed has already been guessed.
    //  If not will add the letter to the list then update all values.
    public void checkGuess(String letter){
        if(!guessedLettersList.contains(letter)){   // is letter within the list?
            guessedLettersList.add(letter.toUpperCase());         // if not will add to list
            guessNumber++;                          // adds to count
            updateGuessedWord(letter);              // then updates guessedWord accordingly
        }else{
            System.out.println("=============================");        // this part is if there is a repeated letter
            System.out.println("Word has already been guessed");
        }
    }
    //This method will update the guessed word with the letter guessed
    // if guess word did not change that means that the letter was not
    //              within the secret word.
    public void updateGuessedWord(String letter) {
        final int firstIndex = secretWord.letterLocation(letter);
        if (secretWord.containsIgnoreCase(letter)) {
            guessNumber--;              // if letter is in word do not count as guess
            if (secretWord.hasMoreThanOne(letter)) {
                final int secondIndex = secretWord.letterLocation(letter, firstIndex + 1);
                String subStr1 = guessedWord.substring(0, firstIndex);
                String subStr2 = guessedWord.substring(firstIndex + 1, secondIndex);
                String subStr3 = guessedWord.substring(secondIndex + 1);
                guessedWord = subStr1 + letter.toUpperCase() + subStr2 + letter.toUpperCase() + subStr3;
            } else {
                String subStr1 = guessedWord.substring(0, firstIndex);        // creates first substring
                String subStr2 = guessedWord.substring(firstIndex + 1);        // creates second
                guessedWord = subStr1 + letter.toUpperCase() + subStr2;                                           // adds the letter in between each string
            }
        }
    }
    // Checks if guessed word is secretWord
    public boolean isWordCorrect(){
        return guessedWord.equalsIgnoreCase(secretWord.toString());
    }
}
