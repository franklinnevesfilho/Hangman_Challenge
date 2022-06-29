package driver.gui;

import resources.Hangman;

import javax.swing.*;
import java.util.Objects;

public class MainFrame extends JFrame {
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

    public MainFrame(Hangman game) {
        this.game = game;

        this.setIconImage(images[0].getImage());
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        updatePicture();
        updateGuessedWord();

        startBtn.setVisible(true);
        startBtn.setEnabled(true);

        playAgainBtn.setVisible(false);
        playAgainBtn.setEnabled(false);

        gamesWonLbl.setText(String.valueOf(gamesWon));
        gamesLostLbl.setText(String.valueOf(gamesLost));

        startBtn.addActionListener(e -> {
            parentPanel.removeAll();
            parentPanel.add(card2Panel);
            parentPanel.repaint();
            parentPanel.revalidate();

            startBtn.setEnabled(false);
            startBtn.setVisible(false);

            playAgainBtn.setVisible(true);
            playAgainBtn.setEnabled(false);
        });

        submitGuessBtn.addActionListener(e -> {
            String letter = userGuessTF.getText();
            if(validGuess(letter)){ // validGuess checks if the guess is only one character and not a number
                userGuessed(letter);    // method for handling each guess
            }else{
                JOptionPane.showMessageDialog(
                        null,
                        "Your guess must be only one letter!!",     // Error message if user input something wrong
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
            userGuessTF.setText("");                                    // Clear Text Field
        });

        playAgainBtn.addActionListener(e ->{
            playAgain();
        });

        this.pack();
        this.validate();
        this.setVisible(true);
    }

    // checks if the string is only a single letter and not a number
    private boolean validGuess(String letter){
        return !letter.isBlank() && letter.length() == 1 && !isNumeric(letter);
    }

    // returns true if the string is number
    private boolean isNumeric(String letter){
        boolean result = true;
        try{
            Integer.parseInt(letter);
        }catch(Exception e){
            result = false;
        }

        return result;
    }

    // method for the user guess
    private void userGuessed(String letter){

        updateGuessedLetters(letter);   // updates the guessed letters
        updateGuessedWord();            // updates the guessed word
        updatePicture();                // updates the picture

        if(game.isWordCorrect() || game.getGuessNumber() >= 10){
            gameOver();     // disabled the game and reveals the word
        }
    }

    private void updateGuessedLetters(String letter){
        game.checkGuess(letter);            // checks the guess
        String totalGuesses = "";           // creates a string to store total guesses
        for(int i = 0; i < game.getGuessedLettersList().size(); i++){
            totalGuesses += game.getGuessedLettersList().get(i) + " "; // adds space between every letter
        }
        guessedLettersTxt.setText(totalGuesses);        // updates the label appropriately
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
    // this method updates the guessed word and sets a space between each letter
    private void updateGuessedWord(){
        String guessedWord = game.getGuessedWord(); // creates a string of guessed word
        StringBuilder str1 = new StringBuilder();
        for(int i = 0; i < guessedWord.length();i++){
            str1.append(guessedWord.charAt(i)).append(" "); // adds a space after every character
        }
        guessedWordLbl.setText("<html>"+ str1.toString() +"<html>");        // sets the label appropriately
    }

    // this method will run, once the match has finished
    private void gameOver(){
        playAgainBtn.setVisible(true);  // play again button turned on
        playAgainBtn.setEnabled(true);

        userGuessTF.setEnabled(false);      // disables the text field
        submitGuessBtn.setEnabled(false);   // disables submit button

        if(!game.isWordCorrect()){            // if guessed word is not the secret word display it
            guessedLettersLbl.setText(game.getSecretWord());
            gamesLost++;
        }else{
            gamesWon++;
        }

    }


    private void playAgain(){

        parentPanel.removeAll();
        parentPanel.add(card1Panel);
        parentPanel.repaint();
        parentPanel.revalidate();

        playAgainBtn.setEnabled(false);
        playAgainBtn.setVisible(false);
        startBtn.setEnabled(true);
        startBtn.setVisible(true);

        gamesWonLbl.setText(String.valueOf(gamesWon));
        gamesLostLbl.setText(String.valueOf(gamesLost));

        Hangman newGame = new Hangman();
        resetGamePanel(newGame);

    }

    private void resetGamePanel(Hangman newGame){
        this.game = newGame;
        updateGuessedWord();
        guessedLettersTxt.setText("");
        updatePicture();
        userGuessTF.setEnabled(true);
        submitGuessBtn.setEnabled(true);
    }

}
