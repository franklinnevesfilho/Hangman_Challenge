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
    private final ArrayList<String> guessedLetters = new ArrayList<>(); // Stores the letters that have been guessed
    private final SecretWord secretWord;                               // Stores the secret word
    private String guessedWord;                                       // Stores the guessed word so far
    private int guessNumber;                                         // keeps track of how many guesses the user has made

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
        System.out.println(secretWord);

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


//===========================================================================================
//===========================================================================================
//-------------------------------------------------------------------------------------------
//----------------------------------- Methods -----------------------------------------------
//-------------------------------------------------------------------------------------------

    // This method is only used when an object is first created
    // To ensure that the guessed word is the same length as the
    //                  Secret word
    public String generateGuessedWord(String secretWord){
        StringBuilder guessWord = new StringBuilder();
        for(int i = 0; i < secretWord.length(); i++ ){
            guessWord.append("_");
        }
        return guessWord.toString();
    }

    // This method checks if the letter guessed has already been guessed.
    //  If not will add the letter to the list then update all values.
    public void checkGuess(String letter){
        if(!guessedLetters.contains(letter)){
            guessedLetters.add(letter);
            guessNumber++;
            updateGuessedWord(letter);
        }
    }

    //This method will update the guessed word with the letter guessed
    // if guess word did not change that means that the letter was not
    //              within the secret word.
    public void updateGuessedWord(String letter){
        if(secretWord.containsIgnoreCase(letter)){
            if(secretWord.hasMoreThanOne(letter)){
                String subStr1 = guessedWord.substring(0,secretWord.letterLocation(letter));
                String subStr2 = guessedWord.substring(secretWord.letterLocation(letter) + 1, secretWord.letterLocation(letter,2));
                String subStr3 = guessedWord.substring(secretWord.letterLocation(letter,2)+1);
                guessedWord = subStr1+ letter.toUpperCase() + subStr2 + letter.toUpperCase() + subStr3;                           // Then sets guessed word with the letter in between each subString
            }else{
                String subStr1 = guessedWord.substring(0,secretWord.letterLocation(letter));        // creates first substring
                String subStr2 = guessedWord.substring(secretWord.letterLocation(letter)+1);        // creates second
                guessedWord = subStr1 + letter.toUpperCase() + subStr2;                                           // adds the letter in between each string
            }
        }
    }

    public boolean wordCorrect(){
        return guessedWord.equalsIgnoreCase(secretWord.toString());
    }



}
