import javax.swing.*;
import java.awt.*;
import java.io.File;

class TarotCardArt {

    /**
     * Return a JLabel containing a scaled ImageIcon for cardName.
     * Looks for images in the "images/" folder relative to runtime working dir.
     * Filename mapping: cardName -> lowercase, spaces -> underscore, extension png.
     */
    public static JLabel getCardImageLabel(String cardName, int width, int height) {
        String filename = cardName.toLowerCase().replace(" ", "_") + ".png";
        File imgFile = new File("images", filename);

        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        if (!imgFile.exists()) {
            label.setText("[Image not found: " + filename + "]");
            label.setForeground(Color.WHITE);
            return label;
        }

        ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        label.setIcon(new ImageIcon(scaled));
        return label;
    }

    /** 🔮 Card back image (back.png) */
    public static JLabel getBackImageLabel(int width, int height) {
        File imgFile = new File("images", "back.png");

        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        if (!imgFile.exists()) {
            label.setText("[Image not found: back.png]");
            label.setForeground(Color.WHITE);
            return label;
        }

        ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        label.setIcon(new ImageIcon(scaled));
        return label;
    }
}
