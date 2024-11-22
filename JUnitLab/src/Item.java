class Item {
    double price;
    int stock;
    String description;
    int purchaseCount;
    boolean bestseller;

    Item(double price, int numPieces, String description) {
        this.price = price;
        this.stock = numPieces;
        this.description = description;
        this.purchaseCount = 0;
        this.bestseller = false;
    }

    Item(double price, int numPieces) {
        this(price, numPieces, "No description available");
    }

    void restock(int amount) {
        this.stock += amount;
    }

    void purchase(int amount) {
        if (amount <= this.stock) {
            this.stock -= amount;
            this.purchaseCount += amount;
        } else {
            throw new IllegalArgumentException("Not enough stock available.");
        }
    }

    void applyDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100.");
        }
        this.price -= this.price * (percentage / 100);
    }

    void markAsBestseller() {
        this.bestseller = true;
    }

    void unmarkAsBestseller() {
        this.bestseller = false;
    }

    String getDetails() {
        return "Price: $" + String.format("%.2f", this.price)
                + ", Stock: " + this.stock
                + ", Description: " + (description.isEmpty() ? "No description available" : description)
                + ", Bestseller: " + (bestseller ? "Yes" : "No");
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
