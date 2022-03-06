import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Card> player = new ArrayList<>();
    private static ArrayList<Card> dealer = new ArrayList<>();
    private static Deck deck;

    public static final int ROUNDS = 1000000, THREADS = 10;

    private static HashMap<Card.Value, Integer> cardValue = new HashMap<>();


    public static void main(String[] args) {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("  _     _            _     _            _              _     \n" +
                " | |__ | | __ _  __ | |__ (_) __ _  __ | |__ __  __ _ | | __ \n" +
                " | '_ \\| |/ _` |/ _|| / / | |/ _` |/ _|| / // _|/ _` || |/ _|\n" +
                " |_.__/|_|\\__,_|\\__||_\\_\\_/ |\\__,_|\\__||_\\_\\\\__|\\__,_||_|\\__|\n" +
                "                        |__/                                 ");
        setCardValuesInInt();
        deck = new Deck();
        deck.newDeck();
        deck.shuffle();
        startGame();
        gameMenu();
    }

    private static void gameMenu() {
        while (true) {
            System.out.println("1) Draw");
            System.out.println("2) Calc % chance of Blackjack");
            System.out.print("> ");
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    draw();
                    break;
                case 2:
                    System.out.println("Do you want to run multithreaded: ");
                    System.out.println("1) Yes");
                    System.out.println("2) No");
                    input = scanner.nextInt();
                    boolean multithreaded = false;
                    long startTime = System.nanoTime();
                    switch (input) {
                        case 1:
                            Statistics.blackjackPercentageThreaded(ROUNDS, THREADS);
                            multithreaded = true;
                            break;
                        case 2:
                            Statistics.blackjackPercentage(ROUNDS);
                            break;
                    }
                    long stopTime = System.nanoTime();
                    printCalculations(startTime, stopTime, multithreaded);
            }
        }
    }

    static void printCalculations(long startTime, long stopTime, boolean multithreaded) {
        System.out.println("=======================");
        if (multithreaded)
            System.out.println("Multithreaded Mode");
        else
            System.out.println("Single threaded Mode");
        System.out.println("Rounds: " + ROUNDS);
        System.out.println("Calculation took: " + TimeUnit.SECONDS.convert(stopTime - startTime, TimeUnit.NANOSECONDS) + " seconds");
        System.out.println("Average Blackjack's: " + Statistics.calcAverageBlackjacks());
        System.out.println("Wins in percentage: " + Statistics.calcAverageBlackjacksPercentage() + "%");
        System.out.println("=======================");
    }

    private static void startGame() {
        player.add(deck.deck.get(0));
        deck.deck.remove(0);
        dealer.add(deck.deck.get(0));
        deck.deck.remove(0);
        player.add(deck.deck.get(0));
        deck.deck.remove(0);
        dealer.add(deck.deck.get(0));
        deck.deck.remove(0);
    }

    private static void draw() {
        player.add(deck.deck.get(0));
        deck.deck.remove(0);
    }

    private static void stand() {

    }

    private static void showCards() {
        int playerScore = 0, dealerScore = 0;
        for (Card card : player) {
            System.out.print(card.getValue() + " ");
            playerScore += cardValue.get(card.getValue());
        }
        System.out.println("- " + playerScore);
        for (Card card : dealer) {
            System.out.print(card.getValue() + " ");
            dealerScore += cardValue.get(card.getValue());
        }
        System.out.println("- " + dealerScore);
        System.out.println();
    }

    private static void setCardValuesInInt() {
        Card card = new Card();
        for (int i = 0; i < 13; i++) {
            cardValue.put(card.getValues()[i], i + 1);
        }
    }

}
