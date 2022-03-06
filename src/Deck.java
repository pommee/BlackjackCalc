import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    ArrayList<Card> deck = new ArrayList<>();


    public void newDeck() {
        deck.clear();
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                Card card = new Card();
                card.setValue(card.getValues()[i]);
                card.setType(card.getTypes()[j]);
                deck.add(card);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }
}
