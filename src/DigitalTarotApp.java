import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class DigitalTarotApp {
    private TarotDeck deck;
    private FortuneQueue queue;
    private DecisionTree decisionTree;

    private JFrame frame;
    private JTextArea displayArea;
    private JPanel imagePanel;
    private JButton drawButton, revealButton, yesButton, noButton, resetDeckButton;

    private String lastCard = null;

    public DigitalTarotApp() {
        deck = new TarotDeck();
        queue = new FortuneQueue();
        decisionTree = new DecisionTree();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("🔮 Digital Tarot — Personality Readings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 750);
        frame.setLayout(new BorderLayout());

        Color bg = new Color(175, 103, 101);
        Color panelBg = new Color(255, 207, 203);
        Color buttonColor = new Color(255, 207, 203);

        frame.getContentPane().setBackground(bg);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(bg);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("🔮  Welcome to Digital Tarot  🔮");
        title.setForeground(new Color(255, 207, 202));
        title.setFont(new Font("Serif", Font.BOLD, 38));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("(Personality Readings)");
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Serif", Font.PLAIN, 22));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(title);
        titlePanel.add(subtitle);
        titlePanel.add(Box.createVerticalStrut(15));

        frame.add(titlePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(bg);

        imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(bg);
        imagePanel.setPreferredSize(new Dimension(323, 570));
        centerPanel.add(imagePanel, BorderLayout.NORTH);

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

        Color thumbColor = new Color(226, 149, 144);
        Color thumbHoverColor = thumbColor.brighter();
        Color trackColor = panelBg;

        scroll.getVerticalScrollBar().setUI(
                new CustomScrollBarUI(thumbColor, thumbHoverColor, trackColor)
        );
        scroll.getHorizontalScrollBar().setUI(
                new CustomScrollBarUI(thumbColor, thumbHoverColor, trackColor)
        );


        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(panelBg);
        textPanel.add(scroll, BorderLayout.CENTER);

        centerPanel.add(textPanel, BorderLayout.CENTER);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(1, 5, 15, 15));
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

        yesButton.setEnabled(false);
        noButton.setEnabled(false);

        drawButton.addActionListener(e -> onDrawCard());
        revealButton.addActionListener(e -> onRevealFortune());
        yesButton.addActionListener(e -> onDecision(true));
        noButton.addActionListener(e -> onDecision(false));
        resetDeckButton.addActionListener(e -> onResetDeck());

        displayArea.append(
                "Click Draw Card to pick a tarot card.\n" +
                        "Follow the Yes/No path to shape your reading.\n" +
                        "Use Reveal Fortune to reveal queued fortunes.\n\n");

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

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


    // 🔮 UPDATED DRAW LOGIC
    private void onDrawCard() {
        String card = deck.drawCard();
        if (card == null) {
            displayArea.append("[Deck empty]\n\n");
            return;
        }

        lastCard = card;

        showBackImage();
        playFlipAnimation(card);

        displayArea.append("\nYou drew: " + card + "\n\n");

        decisionTree.startCard(card);
        displayArea.append("Question: " +
                decisionTree.getCurrentQuestion() + "  (Answer Yes/No)\n\n");

        yesButton.setEnabled(true);
        noButton.setEnabled(true);
    }

    private void showBackImage() {
        imagePanel.removeAll();
        imagePanel.add(TarotCardArt.getBackImageLabel(323, 549), BorderLayout.CENTER);
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private void playFlipAnimation(String cardName) {
        final int fullWidth = 323;
        final int height = 549;
        final int steps = 20;

        Timer timer = new Timer(20, null);
        final int[] step = { 0 };
        final boolean[] showingBack = { true };

        timer.addActionListener(e -> {
            step[0]++;

            // Calculate scale (1 → 0 → 1)
            double progress = step[0] / (double) steps;
            double scaleX = progress <= 0.5
                    ? 1 - (progress * 2)
                    : (progress - 0.5) * 2;

            int currentWidth = Math.max(1, (int) (fullWidth * scaleX));

            imagePanel.removeAll();

            // Swap image at midpoint (when "edge-on")
            JLabel imgLabel;
            if (progress >= 0.5 && showingBack[0]) {
                showingBack[0] = false;
            }

            if (showingBack[0]) {
                imgLabel = TarotCardArt.getBackImageLabel(currentWidth, height);
            } else {
                imgLabel = TarotCardArt.getCardImageLabel(cardName, currentWidth, height);
            }

            imagePanel.add(imgLabel, BorderLayout.CENTER);
            imagePanel.revalidate();
            imagePanel.repaint();

            if (step[0] >= steps) {
                timer.stop();

                // Ensure final card is fully visible
                imagePanel.removeAll();
                imagePanel.add(
                        TarotCardArt.getCardImageLabel(cardName, fullWidth, height),
                        BorderLayout.CENTER);
                imagePanel.revalidate();
                imagePanel.repaint();
            }
        });

        timer.start();
    }

    private void onDecision(boolean yes) {
        DecisionNode node = decisionTree.advance(yes);
        if (node.isLeaf()) {
            displayArea.append("Outcome: " + node.getValue() + "\n\n");
            queue.enqueue("Fortune for " + lastCard + ": " + node.getValue());
            yesButton.setEnabled(false);
            noButton.setEnabled(false);
        } else {
            displayArea.append("Question: " + node.getValue() + "\n\n");
        }
    }

    private void onRevealFortune() {
        String msg = queue.dequeue();
        displayArea.append(msg == null ? "[No fortunes]\n\n" : "[Revealed] " + msg + "\n\n");
    }

    private void onResetDeck() {
        deck.resetDeck();
        displayArea.append("[Deck reset]\n\n");
        imagePanel.removeAll();
        imagePanel.repaint();
        yesButton.setEnabled(false);
        noButton.setEnabled(false);
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
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(DigitalTarotApp::new);
    }
}
