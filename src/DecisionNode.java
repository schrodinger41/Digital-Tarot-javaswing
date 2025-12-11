class DecisionNode {
    private final String value;
    private DecisionNode left; // yes branch
    private DecisionNode right; // no branch

    public DecisionNode(String value) {
        this.value = value;
    }

    public DecisionNode(String value, DecisionNode left, DecisionNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public String getValue() {
        return value;
    }

    public DecisionNode getLeft() {
        return left;
    }

    public DecisionNode getRight() {
        return right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public void setLeft(DecisionNode left) {
        this.left = left;
    }

    public void setRight(DecisionNode right) {
        this.right = right;
    }
}
