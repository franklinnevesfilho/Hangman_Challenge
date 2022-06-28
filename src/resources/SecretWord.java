package resources;

import java.util.Locale;

/**
 * This record is used to de-clutter the other classes
 * mainly used to acquire information from the secretWord
 */

public record SecretWord(String word) {

    //This method will return true if there is a repeated letter within the word
    //first replace all instances of the letter with an empty String
    //if the modified string is 2 places less than the normal String it has more than one instance
    public boolean hasMoreThanOne(String letter) {
        boolean result = false;
        final int index = word.toLowerCase().indexOf(letter.toLowerCase());
        if (word.toLowerCase().indexOf(letter.toLowerCase(),index + 1) > - 1) {
            result = true;
        }
        return result;

    }

    //These methods will return the index of the letter
    public int letterLocation(String letter) {
        return word.toLowerCase().indexOf(letter.toLowerCase());
    }

    //  The second parameter will be to get the index of a repeated letter
    //  In this method the second parameter will allow to get the location
    //          of the first and second instance of that letter
    public int letterLocation(String letter, int instance) {
        int location = -1;
        if (this.hasMoreThanOne(letter)) {
            final int indexOf = word.toLowerCase().indexOf(letter.toLowerCase());
            switch (instance) {
                case 1 -> location = indexOf;
                case 2 -> location = word.indexOf(letter.toLowerCase(), indexOf + 1);

            }
        }
        return location;
    }

    //  This is a modification of the contains method for Strings
    // this method will check if the String contains the parameter
    //                 regardless of the case
    public boolean containsIgnoreCase(String letter) {
        return this.word.toLowerCase().contains(letter.toLowerCase());
    }

    public String toString() {
        return word;
    }
}
