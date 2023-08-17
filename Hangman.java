import java.util.Arrays;
import java.util.Scanner;

public class Hangman {

    static Scanner scan = new Scanner(System.in);

    public static String[] words = { "ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
            "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
            "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
            "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
            "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon",
            "python", "rabbit", "ram", "rat", "raven", "rhino", "salmon", "seal",
            "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
            "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
            "wombat", "zebra" }; // 64 animals

    public static String[] gallows = {
            "+---+\n" +
                    "|   |\n" +
                    "    |\n" +
                    "    |\n" +
                    "    |\n" +
                    "    |\n" +
                    "=========\n",

            "+---+\n" +
                    "|   |\n" +
                    "O   |\n" +
                    "    |\n" +
                    "    |\n" +
                    "    |\n" +
                    "=========\n",

            "+---+\n" +
                    "|   |\n" +
                    "O   |\n" +
                    "|   |\n" +
                    "    |\n" +
                    "    |\n" +
                    "=========\n",

            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|   |\n" +
                    "     |\n" +
                    "     |\n" +
                    " =========\n",

            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|\\  |\n" + // if you were wondering, the only way to print '\' is with a trailing escape
                                  // character, which also happens to be '\'
                    "     |\n" +
                    "     |\n" +
                    " =========\n",

            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|\\  |\n" +
                    "/    |\n" +
                    "     |\n" +
                    " =========\n",

            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|\\  |\n" +
                    "/ \\  |\n" +
                    "     |\n" +
                    " =========\n" };

    public static void main(String[] args) {

        System.out.println("You're gonna play Hangman");
        System.out.println("The rules are simple: ");
        System.out.println("\t- Find the word by guessing the right letters");
        System.out.println("\t- If you guessed wrong, the gallow gets an extra part");
        System.out.println("\t- Guess wrong seven times and you lose.");
        System.out.println("\t- Guess the word and you win! Simple, right?");
        System.out.println("\nReady to play? (y/n)");
        String answer = scan.nextLine();
        if (answer.equals("y")) {
            playHangman();
        } else {
            System.exit(0);
        }
    }

    public static void playHangman() {
        int missedGuesses = 0;
        String misses = "";
        char[] wordChar = words[randomAnimal()].toCharArray();
        // System.out.println(Arrays.toString(wordChar)); // to see the word chosen
        char[] emptyWord = new char[wordChar.length];

        System.out.println("\n");
        for (int i = 0; i < gallows.length; i++) {
            if (i == missedGuesses) {
                System.out.println(gallows[i]);
                break;
            }
        }
        System.out.print("Word: \t");
        for (int i = 0; i < emptyWord.length; i++) {
            emptyWord[i] = '_';
            System.out.print(emptyWord[i] + " ");
        }

        System.out.println("\n\nMisses: \n");

        while (true) {
            System.out.print("Guess: ");
            char guess = askGuess();
            // System.out.print(guess + "\n"); // just to check what character comes out.
            boolean checkRight = checkGuess(wordChar, guess);

            if (checkRight == false) { // adds a count to missed guesses
                missedGuesses++;
            }

            printGallows(gallows, missedGuesses);
            updatePlaceholders(wordChar, emptyWord, guess);
            printPlaceholders(emptyWord);
            misses = printMissedGuesses(misses, guess, checkRight); // getting the missed guesses and printing them.

            if (Arrays.equals(emptyWord, wordChar)) { // win condition
                System.out.println("You win!");
                System.out.println("\n\nWant to play again? (y/n)");
                scan.nextLine();
                String playAgain = scan.nextLine();
                if (playAgain.equals("y")) {
                    playHangman();
                } else {
                    System.exit(0);
                }
            }

            if (missedGuesses == gallows.length - 1) { // lose condition
                System.out.println("You lose");
                System.out.print("The correct answer was: ");
                for (int i = 0; i < wordChar.length; i++) {
                    System.out.print(wordChar[i]);
                }
                System.out.println("\n\nWant to play again? (y/n)");
                scan.nextLine();
                String playAgain = scan.nextLine();
                if (playAgain.equals("y")) {
                    playHangman();
                } else {
                    System.exit(0);
                }
            }

        }

    }

    public static void printGallows(String gallows[], int missedGuesses) {
        for (int i = 0; i < gallows.length; i++) {
            if (i == missedGuesses) {
                System.out.println(gallows[i]);
                break;
            }
        }
    }

    public static int randomAnimal() { // returns a random word from the list of random words.
        double randomNumber = Math.random() * 64;
        return (int) randomNumber;
    }

    public static boolean checkGuess(char[] wordChar, char guess) { // returns true if the user guessed a letter from
                                                                    // the word correctly.
        // boolean guessRight = false;
        for (int i = 0; i < wordChar.length; i++) {
            if (guess == wordChar[i]) {
                return true;
            }
        }
        return false;
    }

    public static char askGuess() {
        char guess = scan.next().charAt(0);
        return guess;
    }

    public static char[] updatePlaceholders(char wordChar[], char emptyWord[], char guess) { // updates the placeholders
                                                                                             // when the user makes a
                                                                                             // correct guess.
        for (int i = 0; i < wordChar.length; i++) {
            if (guess == wordChar[i]) {
                emptyWord[i] = wordChar[i];
            }
        }
        return emptyWord;

    }

    public static void printPlaceholders(char emptyWord[]) { // prints the placeholders.
        for (int i = 0; i < emptyWord.length; i++) {
            System.out.print(emptyWord[i] + " ");
        }
        System.out.println("\n");
    }

    public static String printMissedGuesses(String misses, char guess, boolean checkRight) { // prints guesses that the
                                                                                             // user missed.
        if (checkRight == false) {
            misses += guess;
        }
        System.out.println("Misses: " + misses);
        return misses;
    }

}
