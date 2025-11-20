import java.util.*;

class TarotDeck {
    private Stack<String> deck;

    public TarotDeck() {
        deck = new Stack<>();
        resetDeck();
    }

    public void resetDeck() {
        deck.clear();
        // All 22 Major Arcana
        String[] cards = {
                "The Fool", "The Magician", "The High Priestess", "The Empress", "The Emperor",
                "The Hierophant", "The Lovers", "The Chariot", "Strength", "The Hermit",
                "Wheel of Fortune", "Justice", "The Hanged Man", "Death", "Temperance",
                "The Devil", "The Tower", "The Star", "The Moon", "The Sun",
                "Judgement", "The World"
        };
        List<String> list = new ArrayList<>(Arrays.asList(cards));
        Collections.shuffle(list); // shuffle
        deck.addAll(list);
    }

    public String drawCard() {
        return deck.isEmpty() ? null : deck.pop();
    }

    public int remaining() {
        return deck.size();
    }
}
