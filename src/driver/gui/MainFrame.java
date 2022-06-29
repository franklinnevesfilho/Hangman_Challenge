package driver.gui;

import resources.Hangman;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;

/**
 * this class is equivalent to the driver class
 * It has most of the game logic and will display onto the GUI
 * The images here were created by myself
 */

public class MainFrame extends JFrame {
//==============================================
//-------------- Global variables --------------
//==============================================
    Hangman game;

    private int gamesWon = 0;
    private int gamesLost = 0;

    private JPanel mainPanel;
    private JPanel buttonsPanel;
    private JPanel parentPanel;
    private JButton playAgainBtn;
    private JButton startBtn;
    private JPanel card1Panel;
    private JPanel card2Panel;
    private JLabel welcomeText;
    private JLabel picLabel;
    private JLabel guessedLettersLbl;
    private JTextField userGuessTF;
    private JButton submitGuessBtn;
    private JLabel guessedLettersTxt;
    private JLabel guessedWordLbl;
    private JLabel gamesWonTxt;
    private JLabel gamesWonLbl;
    private JLabel gamesLostTxt;
    private JLabel gamesLostLbl;
    private JLabel Author;

    // These are all the images created by me for this project
    public static ImageIcon[] images = {
            getImage("/resources/images/logo.png")    ,
            getImage("/resources/images/first.png")   ,
            getImage("/resources/images/second.png")  ,
            getImage("/resources/images/third.png")   ,
            getImage("/resources/images/fourth.png")  ,
            getImage("/resources/images/fifth.png")   ,
            getImage("/resources/images/sixth.png")   ,
            getImage("/resources/images/seventh.png") ,
            getImage("/resources/images/eighth.png")  ,
            getImage("/resources/images/ninth.png")   ,
            getImage("/resources/images/tenth.png")   ,

    };
    public static ImageIcon getImage(String path){
        return new ImageIcon(Objects.requireNonNull(MainFrame.class.getResource(path)));
    }

    // The constructor for this class
    public MainFrame(Hangman game) {
        this.game = game; // sets up the game

        this.setIconImage(images[0].getImage());    // displays logo on GUI
        this.setContentPane(mainPanel);             // sets teh first panel to the mainPanel
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // when exit application to close

        updatePicture();            // this method will update the picture
        updateGuessedWord();        // this method will update the guessed word

        setButton(startBtn, true);
        setButton(playAgainBtn, false);

        updateScore();

        userGuessTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userGuessTF.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        startBtn.addActionListener(e -> {
            parentPanel.removeAll();
            parentPanel.add(card2Panel);
            parentPanel.repaint();
            parentPanel.revalidate();

            setButton(startBtn, false);

            playAgainBtn.setVisible(true);
            playAgainBtn.setEnabled(false);
        });

        submitGuessBtn.addActionListener(e -> {
            String letter = userGuessTF.getText();
            if(isValidGuess(letter)){ // validGuess checks if the guess is only one character and not a number
                userGuessed(letter);    // method for handling each guess
            }else{
                JOptionPane.showMessageDialog(
                        null,
                        "Your guess must be only one letter!!",     // Error message if user input something wrong
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
            userGuessTF.setText("");                                    // Clear Text Field
        });

        playAgainBtn.addActionListener(e -> playAgain());

        this.pack();                                // sizes everything proportionally
        this.setLocationRelativeTo(null);           // Ensures GUI is in the middle of screen
        this.setVisible(true);                      // makes the frame visible
    }
//========================================================================
//---------------------- Boolean method ---------------------------------
//========================================================================
    // checks if the string is a letter
    private boolean isValidGuess(String letter){
        boolean isLetter = Character.isLetter(letter.charAt(0)); // is the String a letter
        return isLetter && letter.length() == 1;    // checks if String is only one letter
    }
//========================================================================
//========================================================================
    // method for the user guess
    private void userGuessed(String letter){
        updateGuessedLetters(letter);   // updates the guessed letters
        updateGuessedWord();            // updates the guessed word
        updatePicture();                // updates the picture
        if(game.isWordCorrect() || game.getGuessNumber() == images.length){ // if guess number is equal to the number of images
            gameOver();     // disabled the game and reveals the word
        }
    }
    // This method updates the guessed Letters
    private void updateGuessedLetters(String letter){
        game.checkGuess(letter);            // checks the guess
        StringBuilder totalGuesses = new StringBuilder();           // creates a string to store total guesses
        for(int i = 0; i < game.getGuessedLettersList().size(); i++){
            totalGuesses.append(game.getGuessedLettersList().get(i)).append(" "); // adds space between every letter
        }
        guessedLettersTxt.setText(totalGuesses.toString());        // updates the label appropriately
    }
    // this method will update the picture according to the number of guesses
    private void updatePicture() {
        int currentGuess = game.getGuessNumber();
        if (currentGuess < 10) {
            // we have the logo in the first position
            picLabel.setIcon(images[currentGuess + 1 ]);
        }else{
            // this is the final position
            picLabel.setIcon(images[10]);
        }
    }
    // This method will update the guessed word based
    private void updateGuessedWord(){
        String guessedWord = game.getGuessedWord(); // creates a string of guessed word
        StringBuilder str1 = new StringBuilder();
        for(int i = 0; i < guessedWord.length();i++){
            str1.append(guessedWord.charAt(i)).append(" "); // adds a space after every character
        }
        guessedWordLbl.setText("<html>"+ str1 +"<html>");        // sets the label appropriately
    }
    // and this method will update teh score in the first panel
    private void updateScore(){
        gamesWonLbl.setText(String.valueOf(gamesWon));
        gamesLostLbl.setText(String.valueOf(gamesLost));
    }

    // this method runs once the game is Over
    private void gameOver(){
        setButton(playAgainBtn, true); // enables the playAgainBtn
        userGuessTF.setEnabled(false);      // disables the text field
        submitGuessBtn.setEnabled(false);   // disables submit button

        if(!game.isWordCorrect()){            // if guessed word is not the secret word display it
            guessedLettersLbl.setText(game.getSecretWord());
            gamesLost++;                                    // You've lost the game
        }else{
            gamesWon++;                                     // You've won the game
        }
    }

    // Method handler for playAgainBtn
    private void playAgain(){
        parentPanel.removeAll();        // removes current panel
        parentPanel.add(card1Panel);    // replaces with first panel
        parentPanel.repaint();
        parentPanel.revalidate();       // methods to display change

        setButton(playAgainBtn, false); // disables playAgainBtn
        setButton(startBtn, true);      // enables StartBtn
        updateScore();                       // updates teh score

        Hangman newGame = new Hangman();     // creates a new HangMan game
        resetGamePanel(newGame);             // Resets the gamePanel for new game
    }
    // game reset
    private void resetGamePanel(Hangman newGame){
        this.game = newGame;            // resets hangman
        updateGuessedWord();            // updates teh word
        guessedLettersTxt.setText("");  // resets the list
        updatePicture();                // resets picture
        userGuessTF.setEnabled(true);   // enables textField
        submitGuessBtn.setEnabled(true); // enables button
    }
    // controls whether a button can be clicked and seen
    private void setButton(JButton button, boolean bool){
        button.setEnabled(bool);
        button.setVisible(bool);
    }

}
