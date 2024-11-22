class Item {
    double price;
    int stock;
    String description;
    int purchaseCount;

    Item(double price, int numPieces, String description) {
        this.price = price;
        this.stock = numPieces;
        this.description = description;
        this.purchaseCount = 0;
    }

    Item(double price, int numPieces) {
        this(price, numPieces, "No description available");
    }

    void restock(int amount) {
        this.stock = this.stock + amount;
    }

    void purchase(int amount) {
        if (amount <= this.stock) {
            this.stock -= amount;
            this.purchaseCount += amount;
        } else {
            throw new IllegalArgumentException("Not enough stock available.");
        }
    }
    public void setPrice(double price) {
        this.price = price;
    }
    String getDetails() {
        return "Price: $" + this.price + ", Stock: " + this.stock + ", Description: " + (description.isEmpty() ? "No description available" : description);
    }

}