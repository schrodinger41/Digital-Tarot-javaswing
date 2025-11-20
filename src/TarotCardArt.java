import java.util.HashMap;

class TarotCardArt {
    private static final HashMap<String, String> art = new HashMap<>();

    static {
        art.put("The Fool",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /    THE FOOL   \\\\\n" +
            "|     (0)  * :)  |\n" +
            "|   carefree step |\n" +
            "|________________|\n");

        art.put("The Magician",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /   THE MAGICIAN\\\\\n" +
            "|     (I)  ✦✦✦  |\n" +
            "|   skill & will |\n" +
            "|________________|\n");

        art.put("The High Priestess",
            "   _____________\n" +
            "  /             \\\\\n" +
            " / HIGH PRIESTESS\\\\\n" +
            "|    (II)  ☾ ☽   |\n" +
            "|   intuition    |\n" +
            "|________________|\n");

        art.put("The Empress",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /   THE EMPRESS \\\\\n" +
            "|     (III)  👑  |\n" +
            "|    abundance   |\n" +
            "|________________|\n");

        art.put("The Emperor",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /   THE EMPEROR \\\\\n" +
            "|     (IV)  ⚔️⚔️ |\n" +
            "|    authority   |\n" +
            "|________________|\n");

        art.put("The Hierophant",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /  THE HIEROPHANT\\\\\n" +
            "|    (V)   ✝     |\n" +
            "|   tradition    |\n" +
            "|________________|\n");

        art.put("The Lovers",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /   THE LOVERS  \\\\\n" +
            "|    (VI)  ♥ ♥   |\n" +
            "|    union       |\n" +
            "|________________|\n");

        art.put("The Chariot",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /   THE CHARIOT \\\\\n" +
            "|    (VII)  🚗   |\n" +
            "|   forward drive|\n" +
            "|________________|\n");

        art.put("Strength",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /    STRENGTH   \\\\\n" +
            "|    (VIII)  💪  |\n" +
            "|   courage      |\n" +
            "|________________|\n");

        art.put("The Hermit",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /   THE HERMIT  \\\\\n" +
            "|    (IX)   🔦   |\n" +
            "|   introspect   |\n" +
            "|________________|\n");

        art.put("Wheel of Fortune",
            "   _____________\n" +
            "  /             \\\\\n" +
            " / WHEEL OF FORTUNE\\\\\n" +
            "|    (X)   🔄    |\n" +
            "|   destiny spins|\n" +
            "|________________|\n");

        art.put("Justice",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /    JUSTICE    \\\\\n" +
            "|    (XI)  ⚖️    |\n" +
            "|   balance      |\n" +
            "|________________|\n");

        art.put("The Hanged Man",
            "   _____________\n" +
            "  /             \\\\\n" +
            " / THE HANGED MAN\\\\\n" +
            "|    (XII)  🔄   |\n" +
            "|   surrender    |\n" +
            "|________________|\n");

        art.put("Death",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /     DEATH     \\\\\n" +
            "|    (XIII) 💀   |\n" +
            "|   transformation|\n" +
            "|________________|\n");

        art.put("Temperance",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /   TEMPERANCE  \\\\\n" +
            "|    (XIV) ⚗️   |\n" +
            "|   balance      |\n" +
            "|________________|\n");

        art.put("The Devil",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /   THE DEVIL   \\\\\n" +
            "|    (XV)  😈   |\n" +
            "|   temptation   |\n" +
            "|________________|\n");

        art.put("The Tower",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /   THE TOWER   \\\\\n" +
            "|    (XVI)  ⚡️  |\n" +
            "|   sudden change|\n" +
            "|________________|\n");

        art.put("The Star",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /     THE STAR  \\\\\n" +
            "|    (XVII) ✨   |\n" +
            "|   hope & light |\n" +
            "|________________|\n");

        art.put("The Moon",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /     THE MOON  \\\\\n" +
            "|    (XVIII) 🌙  |\n" +
            "|   intuition    |\n" +
            "|________________|\n");

        art.put("The Sun",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /      THE SUN  \\\\\n" +
            "|    (XIX) ☀️   |\n" +
            "|   positivity   |\n" +
            "|________________|\n");

        art.put("Judgement",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /   JUDGEMENT   \\\\\n" +
            "|    (XX) 📯     |\n" +
            "|   awakening    |\n" +
            "|________________|\n");

        art.put("The World",
            "   _____________\n" +
            "  /             \\\\\n" +
            " /    THE WORLD  \\\\\n" +
            "|    (XXI) 🌎    |\n" +
            "|   completion   |\n" +
            "|________________|\n");
    }

    public static String getArt(String cardName) {
        return art.getOrDefault(cardName, "[No Art Available]\n");
    }
}
