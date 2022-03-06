import java.io.IOException;
import java.util.ArrayList;

public class Statistics {
    private static int playerWins = 0;
    public static ArrayList<Integer> results = new ArrayList<>();
    public static int threadsProgress = 0;

    static void blackjackPercentage(int rounds) {
        Deck deck = new Deck();
        int oldProgress = 0;
        for (int i = 0; i < rounds; i++) {
            final int progress = (int) ((100L * (rounds - i)) / rounds);
            deck.newDeck();
            deck.shuffle();
            Card.Value firstCard = deck.deck.get(0).getValue();
            Card.Value secondCard = deck.deck.get(2).getValue(); // Third index since the dealer gets second card
            if (firstCard == Card.Value.ACE && (secondCard == Card.Value.ACE || secondCard == Card.Value.TEN
                    || secondCard == Card.Value.JACK || secondCard == Card.Value.QUEEN
                    || secondCard == Card.Value.KING)) {
                playerWins++;
            }
            if ((firstCard == Card.Value.TEN || firstCard == Card.Value.JACK
                    || firstCard == Card.Value.QUEEN
                    || firstCard == Card.Value.KING) && secondCard == Card.Value.ACE) {
                playerWins++;
            }
            results.add(playerWins);
            playerWins = 0;
            if (progress % 10 == 0) {
                if (oldProgress != progress) {
                    progress(progress);
                    oldProgress = progress;
                }
            }
        }
    }

    static void blackjackPercentageThreaded(int rounds, int threadCount) {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            CalcThread calcThread = new CalcThread(rounds, threadCount);
            threads.add(calcThread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        while (CalcThread.activeCount() != 2) {
            for (Thread thread : threads) {
                System.out.print(thread.getName() + ": ");
                progressPerThread(CalcThread.progressPercentage.getPlain());
            }
            clearConsole();
        }
        threads.clear();
    }

    static int calcAverageBlackjacks() {
        int total = getAllScores();
        return total / results.size();
    }

    static float calcAverageBlackjacksPercentage() {
        int total = getAllScores();
        return (((float) total) / Main.ROUNDS) * 100;
    }

    static int getAllScores() {
        int total = 0;
        for (Integer score : results) {
            total += score;
        }
        return total;
    }

    private static void progress(int percentage) {
        clearConsole();
        System.out.print("[");
        for (int i = 0; i < 10 - (percentage / 10); i++) {
            System.out.print("=");
        }
        for (int i = 0; i < percentage / 10; i++) {
            System.out.print(".");
        }
        System.out.println("] " + ((10 - (percentage / 10)) * 10) + "%");
    }

    private static void progressPerThread(int percentage) {
        System.out.print("[");
        for (int i = 0; i < 10 - (percentage / 10); i++) {
            System.out.print("=");
        }
        for (int i = 0; i < percentage / 10; i++) {
            System.out.print(".");
        }
        System.out.println("] " + ((10 - (percentage / 10)) * 10) + "%");
    }

    private static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
