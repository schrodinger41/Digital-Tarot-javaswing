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
        frame = new JFrame("🔮 Digital Tarot — Personality Readings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 720);
        frame.setLayout(new BorderLayout());

        // Darker purple background
        Color bg = new Color(30, 10, 50);
        Color panelBg = new Color(55, 25, 85);
        Color buttonColor = new Color(140, 90, 200);

        frame.getContentPane().setBackground(bg);

        // ===== TOP TITLE PANEL =====
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(bg);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("🔮  Welcome to Digital Tarot  🔮");
        title.setForeground(new Color(220, 190, 255));
        title.setFont(new Font("Serif", Font.BOLD, 38));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("(Personality Readings)");
        subtitle.setForeground(new Color(200, 160, 240));
        subtitle.setFont(new Font("Serif", Font.PLAIN, 22));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);
        titlePanel.add(Box.createVerticalStrut(15));

        frame.add(titlePanel, BorderLayout.NORTH);

        // ===== CENTER DISPLAY AREA =====
        displayArea = new JTextArea();
        displayArea.setEditable(false);

        // Use monospaced font (required for ASCII art)
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 18));

        // Disable wrap (critical for ASCII alignment)
        displayArea.setLineWrap(false);
        displayArea.setWrapStyleWord(false);

        displayArea.setForeground(new Color(235, 220, 255));
        displayArea.setBackground(panelBg);
        displayArea.setMargin(new Insets(18, 20, 18, 20));

        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(170, 130, 210), 2));
        frame.add(scroll, BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 5, 15, 15));
        controlPanel.setBackground(bg);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 20, 15));

        drawButton = styledButton("Draw Card", buttonColor);
        revealButton = styledButton("Reveal Fortune", buttonColor);
        yesButton = styledButton("Yes", buttonColor);
        noButton = styledButton("No", buttonColor);
        resetDeckButton = styledButton("Reset Deck", buttonColor);

        controlPanel.add(drawButton);
        controlPanel.add(revealButton);
        controlPanel.add(yesButton);
        controlPanel.add(noButton);
        controlPanel.add(resetDeckButton);

        frame.add(controlPanel, BorderLayout.SOUTH);

        // Disable yes/no initially
        yesButton.setEnabled(false);
        noButton.setEnabled(false);

        // ===== Event handlers =====
        drawButton.addActionListener(e -> onDrawCard());
        revealButton.addActionListener(e -> onRevealFortune());
        yesButton.addActionListener(e -> onDecision(true));
        noButton.addActionListener(e -> onDecision(false));
        resetDeckButton.addActionListener(e -> onResetDeck());

        // ===== Welcome text =====
        displayArea.append(
            "Click **Draw Card** to pick a tarot card.\n" +
            "Follow the Yes/No path to shape your reading.\n" +
            "Use **Reveal Fortune** to reveal queued fortunes.\n\n"
        );

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /** Fancy styled buttons with hover effect */
    private JButton styledButton(String text, Color color) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setForeground(Color.WHITE);
        b.setBackground(color);
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setPreferredSize(new Dimension(150, 45));
        b.setBorder(BorderFactory.createLineBorder(new Color(230, 210, 255), 1));

        // Hover effect
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(color.brighter());
                b.setBorder(BorderFactory.createLineBorder(new Color(255, 245, 255), 2));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(color);
                b.setBorder(BorderFactory.createLineBorder(new Color(230, 210, 255), 1));
            }
        });

        return b;
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
