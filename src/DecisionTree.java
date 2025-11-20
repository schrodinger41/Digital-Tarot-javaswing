import java.util.HashMap;

/**
 * DecisionTree - Binary tree for yes/no fortune decisions.
 *
 * Each card has a unique 3-step path leading to a final fortune.
 * Style: Personality-style branching.
 */
class DecisionTree {
    private HashMap<String, DecisionNode> cardTrees;
    private DecisionNode current;
    private String currentCard;

    public DecisionTree() {
        buildTrees();
    }

    /**
     * Build binary trees for each card
     */
    private void buildTrees() {
        cardTrees = new HashMap<>();

        // Example for The Fool
        DecisionNode foolStep3Yes = new DecisionNode("You will embark on a new exciting journey soon.");
        DecisionNode foolStep3No = new DecisionNode("Be cautious; plan before taking leaps.");
        DecisionNode foolStep2Yes = new DecisionNode("Do you feel spontaneous today?", foolStep3Yes, foolStep3No);
        DecisionNode foolStep2No = new DecisionNode("Do you prefer careful planning?", foolStep3No, foolStep3Yes);
        DecisionNode foolRoot = new DecisionNode("Are you ready to take a risk?", foolStep2Yes, foolStep2No);
        cardTrees.put("The Fool", foolRoot);

        // The Magician
        DecisionNode magicianStep3Yes = new DecisionNode("Your skills will bring success soon.");
        DecisionNode magicianStep3No = new DecisionNode("Focus on learning new skills to improve.");
        DecisionNode magicianStep2Yes = new DecisionNode("Do you have a clear goal?", magicianStep3Yes,
                magicianStep3No);
        DecisionNode magicianStep2No = new DecisionNode("Do you trust your abilities?", magicianStep3Yes,
                magicianStep3No);
        DecisionNode magicianRoot = new DecisionNode("Do you feel confident?", magicianStep2Yes, magicianStep2No);
        cardTrees.put("The Magician", magicianRoot);

        // The High Priestess
        DecisionNode priestessStep3Yes = new DecisionNode("Intuition will guide your decisions.");
        DecisionNode priestessStep3No = new DecisionNode("Take time to reflect before acting.");
        DecisionNode priestessStep2Yes = new DecisionNode("Do you trust your gut feelings?", priestessStep3Yes,
                priestessStep3No);
        DecisionNode priestessStep2No = new DecisionNode("Are you seeking hidden knowledge?", priestessStep3Yes,
                priestessStep3No);
        DecisionNode priestessRoot = new DecisionNode("Are you in tune with yourself?", priestessStep2Yes,
                priestessStep2No);
        cardTrees.put("The High Priestess", priestessRoot);

        // The Empress
        DecisionNode empressStep3Yes = new DecisionNode("Creativity and abundance flow to you.");
        DecisionNode empressStep3No = new DecisionNode("Take care of yourself to nurture your potential.");
        DecisionNode empressStep2Yes = new DecisionNode("Do you embrace your creativity?", empressStep3Yes,
                empressStep3No);
        DecisionNode empressStep2No = new DecisionNode("Do you seek comfort and care?", empressStep3No,
                empressStep3Yes);
        DecisionNode empressRoot = new DecisionNode("Are you feeling nurturing today?", empressStep2Yes,
                empressStep2No);
        cardTrees.put("The Empress", empressRoot);

        // The Emperor
        DecisionNode emperorStep3Yes = new DecisionNode("Leadership opportunities are coming.");
        DecisionNode emperorStep3No = new DecisionNode("Focus on discipline and structure.");
        DecisionNode emperorStep2Yes = new DecisionNode("Are you ready to lead?", emperorStep3Yes, emperorStep3No);
        DecisionNode emperorStep2No = new DecisionNode("Do you desire stability?", emperorStep3No, emperorStep3Yes);
        DecisionNode emperorRoot = new DecisionNode("Do you feel authoritative?", emperorStep2Yes, emperorStep2No);
        cardTrees.put("The Emperor", emperorRoot);

        // The Hierophant
        DecisionNode hierophantStep3Yes = new DecisionNode("Tradition will guide you wisely.");
        DecisionNode hierophantStep3No = new DecisionNode("Seek alternative paths for growth.");
        DecisionNode hierophantStep2Yes = new DecisionNode("Do you value conventional wisdom?", hierophantStep3Yes,
                hierophantStep3No);
        DecisionNode hierophantStep2No = new DecisionNode("Are you questioning traditions?", hierophantStep3No,
                hierophantStep3Yes);
        DecisionNode hierophantRoot = new DecisionNode("Do you follow established rules?", hierophantStep2Yes,
                hierophantStep2No);
        cardTrees.put("The Hierophant", hierophantRoot);

        // The Lovers
        DecisionNode loversStep3Yes = new DecisionNode("Love and harmony are near.");
        DecisionNode loversStep3No = new DecisionNode("Focus on balance and understanding in relationships.");
        DecisionNode loversStep2Yes = new DecisionNode("Do you feel connected to someone?", loversStep3Yes,
                loversStep3No);
        DecisionNode loversStep2No = new DecisionNode("Are you seeking companionship?", loversStep3No, loversStep3Yes);
        DecisionNode loversRoot = new DecisionNode("Are you open to love?", loversStep2Yes, loversStep2No);
        cardTrees.put("The Lovers", loversRoot);

        // The Chariot
        DecisionNode chariotStep3Yes = new DecisionNode("Victory and progress are ahead.");
        DecisionNode chariotStep3No = new DecisionNode("Stay determined and disciplined.");
        DecisionNode chariotStep2Yes = new DecisionNode("Do you have strong willpower?", chariotStep3Yes,
                chariotStep3No);
        DecisionNode chariotStep2No = new DecisionNode("Are you ready to overcome obstacles?", chariotStep3No,
                chariotStep3Yes);
        DecisionNode chariotRoot = new DecisionNode("Do you feel determined?", chariotStep2Yes, chariotStep2No);
        cardTrees.put("The Chariot", chariotRoot);

        // Strength
        DecisionNode strengthStep3Yes = new DecisionNode("Courage will help you overcome challenges.");
        DecisionNode strengthStep3No = new DecisionNode("Patience is needed; act calmly.");
        DecisionNode strengthStep2Yes = new DecisionNode("Are you facing challenges?", strengthStep3Yes,
                strengthStep3No);
        DecisionNode strengthStep2No = new DecisionNode("Do you feel inner strength?", strengthStep3Yes,
                strengthStep3No);
        DecisionNode strengthRoot = new DecisionNode("Do you have courage?", strengthStep2Yes, strengthStep2No);
        cardTrees.put("Strength", strengthRoot);

        // The Hermit
        DecisionNode hermitStep3Yes = new DecisionNode("Reflection will bring clarity.");
        DecisionNode hermitStep3No = new DecisionNode("Seek solitude to gain insight.");
        DecisionNode hermitStep2Yes = new DecisionNode("Do you seek guidance?", hermitStep3Yes, hermitStep3No);
        DecisionNode hermitStep2No = new DecisionNode("Are you introspective?", hermitStep3No, hermitStep3Yes);
        DecisionNode hermitRoot = new DecisionNode("Do you need time alone?", hermitStep2Yes, hermitStep2No);
        cardTrees.put("The Hermit", hermitRoot);

        // Wheel of Fortune
        DecisionNode wheelStep3Yes = new DecisionNode("Fortune is changing in your favor.");
        DecisionNode wheelStep3No = new DecisionNode("Be prepared for unexpected events.");
        DecisionNode wheelStep2Yes = new DecisionNode("Do you embrace change?", wheelStep3Yes, wheelStep3No);
        DecisionNode wheelStep2No = new DecisionNode("Do you feel stuck?", wheelStep3No, wheelStep3Yes);
        DecisionNode wheelRoot = new DecisionNode("Is your luck shifting?", wheelStep2Yes, wheelStep2No);
        cardTrees.put("Wheel of Fortune", wheelRoot);

        // Justice
        DecisionNode justiceStep3Yes = new DecisionNode("Fairness will prevail.");
        DecisionNode justiceStep3No = new DecisionNode("Consider all sides before deciding.");
        DecisionNode justiceStep2Yes = new DecisionNode("Do you seek fairness?", justiceStep3Yes, justiceStep3No);
        DecisionNode justiceStep2No = new DecisionNode("Do you need guidance on a decision?", justiceStep3No,
                justiceStep3Yes);
        DecisionNode justiceRoot = new DecisionNode("Do you value justice?", justiceStep2Yes, justiceStep2No);
        cardTrees.put("Justice", justiceRoot);

        // The Hanged Man
        DecisionNode hangedStep3Yes = new DecisionNode("New perspectives will help you.");
        DecisionNode hangedStep3No = new DecisionNode("Patience is necessary; wait before acting.");
        DecisionNode hangedStep2Yes = new DecisionNode("Do you feel stuck?", hangedStep3Yes, hangedStep3No);
        DecisionNode hangedStep2No = new DecisionNode("Are you willing to surrender?", hangedStep3No, hangedStep3Yes);
        DecisionNode hangedRoot = new DecisionNode("Are you in suspension?", hangedStep2Yes, hangedStep2No);
        cardTrees.put("The Hanged Man", hangedRoot);

        // Death
        DecisionNode deathStep3Yes = new DecisionNode("A transformation is near; embrace it.");
        DecisionNode deathStep3No = new DecisionNode("Endings may be gradual; prepare yourself.");
        DecisionNode deathStep2Yes = new DecisionNode("Are you ready for change?", deathStep3Yes, deathStep3No);
        DecisionNode deathStep2No = new DecisionNode("Do you resist endings?", deathStep3No, deathStep3Yes);
        DecisionNode deathRoot = new DecisionNode("Do you face transformation?", deathStep2Yes, deathStep2No);
        cardTrees.put("Death", deathRoot);

        // Temperance
        DecisionNode temperanceStep3Yes = new DecisionNode("Balance and harmony will guide you.");
        DecisionNode temperanceStep3No = new DecisionNode("Seek patience and moderation.");
        DecisionNode temperanceStep2Yes = new DecisionNode("Do you seek balance?", temperanceStep3Yes,
                temperanceStep3No);
        DecisionNode temperanceStep2No = new DecisionNode("Are you acting impulsively?", temperanceStep3No,
                temperanceStep3Yes);
        DecisionNode temperanceRoot = new DecisionNode("Are you practicing moderation?", temperanceStep2Yes,
                temperanceStep2No);
        cardTrees.put("Temperance", temperanceRoot);

        // The Devil
        DecisionNode devilStep3Yes = new DecisionNode("Beware temptations and negative influences.");
        DecisionNode devilStep3No = new DecisionNode("Stay disciplined and free from bad habits.");
        DecisionNode devilStep2Yes = new DecisionNode("Do you feel tempted?", devilStep3Yes, devilStep3No);
        DecisionNode devilStep2No = new DecisionNode("Are you breaking free from constraints?", devilStep3No,
                devilStep3Yes);
        DecisionNode devilRoot = new DecisionNode("Are you facing bondage?", devilStep2Yes, devilStep2No);
        cardTrees.put("The Devil", devilRoot);

        // The Tower
        DecisionNode towerStep3Yes = new DecisionNode("Sudden change will bring opportunity.");
        DecisionNode towerStep3No = new DecisionNode("Brace for unexpected challenges.");
        DecisionNode towerStep2Yes = new DecisionNode("Do you welcome disruption?", towerStep3Yes, towerStep3No);
        DecisionNode towerStep2No = new DecisionNode("Are you resistant to change?", towerStep3No, towerStep3Yes);
        DecisionNode towerRoot = new DecisionNode("Is upheaval coming?", towerStep2Yes, towerStep2No);
        cardTrees.put("The Tower", towerRoot);

        // The Star
        DecisionNode starStep3Yes = new DecisionNode("Hope and inspiration guide you forward.");
        DecisionNode starStep3No = new DecisionNode("Take time to reflect and dream.");
        DecisionNode starStep2Yes = new DecisionNode("Do you feel inspired?", starStep3Yes, starStep3No);
        DecisionNode starStep2No = new DecisionNode("Are you searching for guidance?", starStep3No, starStep3Yes);
        DecisionNode starRoot = new DecisionNode("Do you seek hope?", starStep2Yes, starStep2No);
        cardTrees.put("The Star", starRoot);

        // The Moon
        DecisionNode moonStep3Yes = new DecisionNode("Trust your intuition in confusing times.");
        DecisionNode moonStep3No = new DecisionNode("Look closely; all is not as it seems.");
        DecisionNode moonStep2Yes = new DecisionNode("Do you sense hidden truths?", moonStep3Yes, moonStep3No);
        DecisionNode moonStep2No = new DecisionNode("Are you guided by imagination?", moonStep3No, moonStep3Yes);
        DecisionNode moonRoot = new DecisionNode("Do illusions affect you?", moonStep2Yes, moonStep2No);
        cardTrees.put("The Moon", moonRoot);

        // The Sun
        DecisionNode sunStep3Yes = new DecisionNode("Success, joy, and vitality are with you.");
        DecisionNode sunStep3No = new DecisionNode("Focus on positivity and clarity.");
        DecisionNode sunStep2Yes = new DecisionNode("Do you embrace happiness?", sunStep3Yes, sunStep3No);
        DecisionNode sunStep2No = new DecisionNode("Are you seeking clarity?", sunStep3No, sunStep3Yes);
        DecisionNode sunRoot = new DecisionNode("Do you feel radiant?", sunStep2Yes, sunStep2No);
        cardTrees.put("The Sun", sunRoot);

        // Judgement
        DecisionNode judgementStep3Yes = new DecisionNode("Self-reflection will bring awakening.");
        DecisionNode judgementStep3No = new DecisionNode("Evaluate actions before deciding.");
        DecisionNode judgementStep2Yes = new DecisionNode("Do you accept responsibility?", judgementStep3Yes,
                judgementStep3No);
        DecisionNode judgementStep2No = new DecisionNode("Are you judging yourself?", judgementStep3No,
                judgementStep3Yes);
        DecisionNode judgementRoot = new DecisionNode("Do you seek rebirth?", judgementStep2Yes, judgementStep2No);
        cardTrees.put("Judgement", judgementRoot);

        // The World
        DecisionNode worldStep3Yes = new DecisionNode("Completion and fulfillment are coming.");
        DecisionNode worldStep3No = new DecisionNode("Seek closure and finish your projects.");
        DecisionNode worldStep2Yes = new DecisionNode("Do you feel accomplished?", worldStep3Yes, worldStep3No);
        DecisionNode worldStep2No = new DecisionNode("Are you seeking closure?", worldStep3No, worldStep3Yes);
        DecisionNode worldRoot = new DecisionNode("Do you feel whole?", worldStep2Yes, worldStep2No);
        cardTrees.put("The World", worldRoot);
    }

    /**
     * Start decision flow for a specific card
     */
    public void startCard(String cardName) {
        currentCard = cardName;
        current = cardTrees.get(cardName);
    }

    /**
     * Advance the tree according to answer (yes=true, no=false)
     */
    public DecisionNode advance(boolean yes) {
        if (current == null)
            return null;
        current = yes ? current.getLeft() : current.getRight();
        return current;
    }

    public DecisionNode getCurrentNode() {
        return current;
    }

    public String getCurrentQuestion() {
        if (current == null)
            return null;
        return current.getValue();
    }

}
