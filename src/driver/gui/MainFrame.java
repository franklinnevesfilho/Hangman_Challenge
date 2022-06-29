package driver.gui;

import resources.Hangman;

import javax.swing.*;
import java.util.Objects;

public class MainFrame extends JFrame {
    Hangman game;
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

        picLabel.setIcon(images[1]);
        guessedWordLbl.setText(game.getGuessedWord());

        startBtn.setVisible(true);
        startBtn.setEnabled(true);

        playAgainBtn.setVisible(false);
        playAgainBtn.setEnabled(false);


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

        this.pack();
        this.setVisible(true);
    }

    private boolean validGuess(String letter){
        return !letter.isBlank() && letter.length() == 1 && !isNumeric(letter);
    }

    private boolean isNumeric(String letter){
        boolean result = true;
        try{
            Integer.parseInt(letter);
        }catch(Exception e){
            result = false;
        }

        return result;
    }
    private void userGuessed(String letter){
        game.checkGuess(letter);

        String totalGuesses = "";

        for(int i = 0; i < game.getGuessedLettersList().size(); i++){
            totalGuesses += game.getGuessedLettersList().get(i) + " ";
        }
        guessedLettersTxt.setText(totalGuesses);
        updateGuessedWord();

        if(game.wordCorrect()){
            playAgainBtn.setVisible(true);
            playAgainBtn.setEnabled(true);
        }
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
        String guessedWord = game.getGuessedWord();
        StringBuilder str1 = new StringBuilder();
        for(int i = 0; i < guessedWord.length();i++){
            str1.append(guessedWord.charAt(i)).append(" ");
        }
        guessedWordLbl.setText(str1.toString());
    }
    // this method will run, once the match has finished
    private void gameOver(){

    }

}
