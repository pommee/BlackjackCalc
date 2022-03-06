import java.util.concurrent.atomic.AtomicInteger;

public class CalcThread extends Thread {

    int rounds, threadCount, name;
    public static AtomicInteger progressPercentage = new AtomicInteger(0);

    public CalcThread(int rounds, int threadCount) {
        this.rounds = rounds;
        this.threadCount = threadCount;
    }

    @Override
    public void run() {
        rounds = Math.round(rounds / threadCount);
        Deck deck = new Deck();
        int oldProgress = 0;
        int playerWins = 0;
        for (int i = 0; i < rounds; i++) {
            final int progress = (int) ((100L * (rounds - i)) / rounds);
            deck.newDeck();
            deck.shuffle();
            Card.Value firstCard = deck.deck.get(0).getValue();
            Card.Value secondCard = deck.deck.get(1).getValue();
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
            if (progress % 10 == 0) {
                if (oldProgress != progress) {
                    Statistics.threadsProgress = progress;
                    oldProgress = progress;
                    progressPercentage.set(progress);
                }
            }
        }
        Statistics.results.add(playerWins);
        currentThread().interrupt();
    }
}
