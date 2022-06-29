package resources;

/**
 * This record is used to de-clutter the other classes
 * mainly used to acquire information from the secretWord
 */
public record SecretWord(String word) {

    //This method will return true if there is a repeated letter within the word
    public boolean hasMoreThanOne(String letter) {
        boolean result = false;
        String wordToLower = word.toLowerCase();
        String letterToLower = letter.toLowerCase();
        if (wordToLower.indexOf(letterToLower,wordToLower.indexOf(letterToLower )+1) > -1) {
            result = true;
        }
        return result;

    }

    //These methods will return the index of the letter
    public int letterLocation(String letter) {
        return word.toLowerCase().indexOf(letter.toLowerCase());
    }
    public int letterLocation(String letter, int startingIndex){
        return word.toLowerCase().indexOf(letter.toLowerCase(),startingIndex);
    }

    //  This is a modification of the contains method for Strings
    // this method will check if the String contains the parameter
    //                 regardless of the case
    public boolean containsIgnoreCase(String letter) {
        return this.word.toLowerCase().contains(letter.toLowerCase());
    }

    // This method is used to return the number of a character are in the word
    public int numbersOf(String letter){
        String lowerCaseWord = word.toLowerCase();           // sets word to lower case
        final String lowerCaseLetter = letter.toLowerCase(); // sets the letter to lower case
        int latestIndex = 0;                                 // this is the latest index of the letter
        int count = 0;                                       // count variable
        while(lowerCaseWord.contains(lowerCaseLetter)){   // while the word contains the letter continue to run
            count++;                                      // add count
            lowerCaseWord = lowerCaseWord.substring(latestIndex, lowerCaseWord.indexOf(lowerCaseLetter));
                        // sets word as the substring from the latest index till the next index
            latestIndex = lowerCaseWord.indexOf(lowerCaseLetter) + 1;
                        // latest index is the index of the letter plus 1 so that the last letter is not included
        }
        return count;       // return the count
    }

    public String toString() {
        return word;
    }
}
