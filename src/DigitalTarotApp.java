import javax.swing.*;
import java.awt.*;

/**
 * DigitalTarotApp
 * Main Swing GUI.
 *
 * - Draw Card: pops a card from the deck (stack), shows ASCII art
 * - After draw: decision flow begins (Yes/No buttons)
 * - At leaf: outcome is enqueued into FortuneQueue
 * - Reveal Fortune: dequeues next fortune and shows it in the display area
 */
public class DigitalTarotApp {
    private TarotDeck deck;
    private FortuneQueue queue;
    private DecisionTree decisionTree;

    // GUI components
    private JFrame frame;
    private JTextArea displayArea;
    private JButton drawButton, revealButton, yesButton, noButton, resetDeckButton;

    // track last drawn card name for message personalization
    private String lastCard = null;

    public DigitalTarotApp() {
        deck = new TarotDeck();
        queue = new FortuneQueue();
        decisionTree = new DecisionTree(); // personality-style tree (style 2)

        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Digital Tarot — Personality Readings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 600);
        frame.setLayout(new BorderLayout(8, 8));

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scroll = new JScrollPane(displayArea);
        frame.add(scroll, BorderLayout.CENTER);

        // Buttons
        JPanel controlPanel = new JPanel();
        drawButton = new JButton("Draw Card");
        revealButton = new JButton("Reveal Fortune");
        yesButton = new JButton("Yes");
        noButton = new JButton("No");
        resetDeckButton = new JButton("Reset Deck");

        controlPanel.add(drawButton);
        controlPanel.add(revealButton);
        controlPanel.add(yesButton);
        controlPanel.add(noButton);
        controlPanel.add(resetDeckButton);

        frame.add(controlPanel, BorderLayout.SOUTH);

        // Initially disable Yes/No (only enabled once decision flow begins)
        yesButton.setEnabled(false);
        noButton.setEnabled(false);

        // Event handlers
        drawButton.addActionListener(e -> onDrawCard());
        revealButton.addActionListener(e -> onRevealFortune());
        yesButton.addActionListener(e -> onDecision(true));
        noButton.addActionListener(e -> onDecision(false));
        resetDeckButton.addActionListener(e -> onResetDeck());

        // show initial help text
        displayArea.append("Welcome to Digital Tarot (Personality-style readings).\n");
        displayArea.append("Click 'Draw Card' to pick a card.\n");
        displayArea.append("After drawing, answer Yes/No to follow the decision path.\n");
        displayArea.append("Use 'Reveal Fortune' to view queued fortunes.\n\n");

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void onDrawCard() {
        String card = deck.drawCard();
        if (card == null) {
            displayArea.append("[Deck empty] Use Reset Deck to reinitialize.\n\n");
            lastCard = null;
            // disable decision buttons
            yesButton.setEnabled(false);
            noButton.setEnabled(false);
            return;
        }

        lastCard = card;
        // show ASCII art + name
        String art = TarotCardArt.getArt(card);
        displayArea.append(art);
        displayArea.append("\nYou drew: " + card + "\n\n");

        // start the decision flow (personality-style)
        decisionTree.startCard(card); // initialize traversal for this card
        String first = decisionTree.getCurrentQuestion();
        displayArea.append("Question: " + first + "  (Answer Yes/No)\n\n");

        // enable yes/no
        yesButton.setEnabled(true);
        noButton.setEnabled(true);
    }

    private void onDecision(boolean yes) {
        if (decisionTree == null || decisionTree.getCurrentNode() == null) {
            displayArea.append("[No decision is active. Draw a card first.]\n\n");
            yesButton.setEnabled(false);
            noButton.setEnabled(false);
            return;
        }

        DecisionNode node = decisionTree.advance(yes); // advance and return current node
        if (node == null) {
            displayArea.append("[Decision error]\n\n");
            yesButton.setEnabled(false);
            noButton.setEnabled(false);
            return;
        }

        if (node.isLeaf()) {
            // final outcome
            String outcome = node.getValue();
            displayArea.append("Outcome: " + outcome + "\n\n");

            // enqueue the personalized fortune message (includes card)
            String personalized = (lastCard != null)
                    ? "Fortune for " + lastCard + ": " + outcome
                    : "Fortune: " + outcome;
            queue.enqueue(personalized);

            // disable yes/no until next draw
            yesButton.setEnabled(false);
            noButton.setEnabled(false);
        } else {
            // intermediate question
            displayArea.append("Question: " + node.getValue() + "  (Answer Yes/No)\n\n");
        }
    }

    private void onRevealFortune() {
        String msg = queue.dequeue();
        if (msg == null) {
            displayArea.append("[No pending fortunes to reveal.]\n\n");
        } else {
            displayArea.append("[Revealed] " + msg + "\n\n");
        }
    }

    private void onResetDeck() {
        deck.resetDeck(); // reinitialize & shuffle
        displayArea.append("[Deck reset and shuffled.]\n\n");
        lastCard = null;
        yesButton.setEnabled(false);
        noButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DigitalTarotApp::new);
    }
}
