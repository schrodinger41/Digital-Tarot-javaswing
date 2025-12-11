import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class DigitalTarotApp {
    private TarotDeck deck;
    private FortuneQueue queue;
    private DecisionTree decisionTree;

    // GUI components
    private JFrame frame;
    private JTextArea displayArea; // keeps text log and questions
    private JPanel imagePanel; // center panel to display card image
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
    frame.setSize(900, 750);
    frame.setLayout(new BorderLayout());

    // background colors
    Color bg = new Color(175, 103, 101);
    Color panelBg = new Color(255, 207, 203);
    Color buttonColor = new Color(255, 207, 203);

    frame.getContentPane().setBackground(bg);

    // ===== TOP TITLE PANEL =====
    JPanel titlePanel = new JPanel();
    titlePanel.setBackground(bg);
    titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

    JLabel title = new JLabel("🔮  Welcome to Digital Tarot  🔮");
    title.setForeground(new Color(255, 207, 202));
    title.setFont(new Font("Serif", Font.BOLD, 38));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel subtitle = new JLabel("(Personality Readings)");
    subtitle.setForeground(new Color(250, 250, 250));
    subtitle.setFont(new Font("Serif", Font.PLAIN, 22));
    subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

    titlePanel.add(Box.createVerticalStrut(10));
    titlePanel.add(title);
    titlePanel.add(Box.createVerticalStrut(5));
    titlePanel.add(subtitle);
    titlePanel.add(Box.createVerticalStrut(15));

    frame.add(titlePanel, BorderLayout.NORTH);

    // ===== CENTER DISPLAY AREA =====
    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.setBackground(bg);

    // Image panel
    imagePanel = new JPanel();
    imagePanel.setBackground(bg);
    imagePanel.setPreferredSize(new Dimension(323, 570));
    imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel placeholder = new JLabel("Click 'Draw Card' to reveal what fate has chosen for you.");
    placeholder.setForeground(new Color(80, 45, 50));
    placeholder.setHorizontalAlignment(SwingConstants.CENTER);
    placeholder.setFont(new Font("Serif", Font.ITALIC, 20));
    imagePanel.setLayout(new GridBagLayout());
    imagePanel.add(placeholder);

    centerPanel.add(imagePanel, BorderLayout.NORTH);

    // Text area with scroll
    displayArea = new JTextArea();
    displayArea.setEditable(false);
    displayArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
    displayArea.setLineWrap(true);
    displayArea.setWrapStyleWord(true);
    displayArea.setForeground(new Color(80, 45, 50));
    displayArea.setBackground(panelBg);
    displayArea.setMargin(new Insets(10, 10, 10, 10));

    JScrollPane scroll = new JScrollPane(displayArea);
    scroll.setBorder(BorderFactory.createLineBorder(new Color(106, 52, 49), 3));
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.setPreferredSize(new Dimension(550, 200));

    Color thumbColor = new Color(226, 149, 144); // main thumb color
    Color thumbHoverColor = thumbColor.brighter();
    Color trackColor = panelBg;

    scroll.getVerticalScrollBar().setUI(new CustomScrollBarUI(thumbColor, thumbHoverColor, trackColor));
    scroll.getHorizontalScrollBar().setUI(new CustomScrollBarUI(thumbColor, thumbHoverColor, trackColor));

    // wrap scroll in a panel to ensure proper layout
    JPanel textPanel = new JPanel(new BorderLayout());
    textPanel.setBackground(panelBg);
    textPanel.add(scroll, BorderLayout.CENTER);

    centerPanel.add(textPanel, BorderLayout.CENTER);

    frame.add(centerPanel, BorderLayout.CENTER);

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
            "Click Draw Card to pick a tarot card.\n" +
            "Follow the Yes/No path to shape your reading.\n" +
            "Use Reveal Fortune to reveal queued fortunes.\n\n");

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}

    /** Fancy styled buttons with hover effect */
    private JButton styledButton(String text, Color color) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setForeground(new Color(148, 84, 84));
        b.setBackground(color);
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setPreferredSize(new Dimension(150, 45));
        b.setBorder(BorderFactory.createLineBorder(new Color(106, 52, 49), 2));

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(color.brighter());
                b.setBorder(BorderFactory.createLineBorder(new Color(140, 69, 65), 3));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(color);
                b.setBorder(BorderFactory.createLineBorder(new Color(106, 52, 49), 2));
            }
        });

        return b;
    }

    private void onDrawCard() {
        String card = deck.drawCard();
        if (card == null) {
            displayArea.append("[Deck empty] Use Reset Deck to reinitialize.\n\n");
            lastCard = null;
            yesButton.setEnabled(false);
            noButton.setEnabled(false);
            clearImage();
            return;
        }

        lastCard = card;

        // Load and display image using TarotCardArt
        updateCardImage(card);

        displayArea.append("\nYou drew: " + card + "\n\n");

        // start the decision flow (personality-style)
        decisionTree.startCard(card);
        String first = decisionTree.getCurrentQuestion();
        displayArea.append("Question: " + first + "  (Answer Yes/No)\n\n");

        // enable yes/no
        yesButton.setEnabled(true);
        noButton.setEnabled(true);
    }

    private void updateCardImage(String cardName) {
        imagePanel.removeAll();
        JLabel imgLabel = TarotCardArt.getCardImageLabel(cardName, 323, 549);
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(imgLabel, BorderLayout.CENTER);
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private void clearImage() {
        imagePanel.removeAll();
        imagePanel.revalidate();
        imagePanel.repaint();
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
        clearImage();
        yesButton.setEnabled(false);
        noButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DigitalTarotApp::new);
    }

    class CustomScrollBarUI extends BasicScrollBarUI {
        private final Color thumbColor;
        private final Color thumbHoverColor;
        private final Color trackColor;
        private boolean hover = false;

        public CustomScrollBarUI(Color thumbColor, Color thumbHoverColor, Color trackColor) {
            this.thumbColor = thumbColor;
            this.thumbHoverColor = thumbHoverColor;
            this.trackColor = trackColor;
        }

        @Override
        protected void configureScrollBarColors() { }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) return;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(hover ? thumbHoverColor : thumbColor);
            g2.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            g.setColor(trackColor);
            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }

        @Override
        protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void setThumbBounds(int x, int y, int width, int height) {
            super.setThumbBounds(x, y, width, height);
            scrollbar.repaint();
        }

        @Override
        protected void installListeners() {
            super.installListeners();
            scrollbar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                public void mouseMoved(java.awt.event.MouseEvent e) {
                    hover = true;
                    scrollbar.repaint();
                }
            });
            scrollbar.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseExited(java.awt.event.MouseEvent e) {
                    hover = false;
                    scrollbar.repaint();
                }
            });
        }
    }
}
