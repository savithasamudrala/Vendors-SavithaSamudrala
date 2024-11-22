import java.util.HashMap;


/**
 * Class for a Vending Machine.  Contains a hashtable mapping item names to item data, as
 * well as the current balance of money that has been deposited into the machine.
 */
class Vending {
    static HashMap<String, Item> Stock = new HashMap<String,Item>();
    private double balance;

    Vending(int numCandy, int numGum) {
        Stock.put("Candy", new Item(1.25, numCandy));
        Stock.put("Gum", new Item(.5, numGum));
        this.balance = 0;
    }

    /** resets the Balance to 0 */
    void resetBalance () {
        this.balance = 0;
    }

    /** returns the current balance */
    double getBalance () {
        return this.balance;
    }

    /** adds money to the machine's balance
     * @param amt how much money to add
     * */
    void addMoney (double amt) {
        if (amt >= 0) {
            this.balance = this.balance + amt;
        }
        if(this.balance > Integer.MAX_VALUE){
            this.balance = Integer.MAX_VALUE;
        }
    }

    /** attempt to purchase named item.  Message returned if
     * the balance isn't sufficient to cover the item cost.
     *
     * @param name The name of the item to purchase ("Candy" or "Gum")
     */
    void select (String name) {
        if (Stock.containsKey(name)) {
            Item item = Stock.get(name);
            if(item.stock == 0){
                System.out.println("Item out of stock");
            }else if (balance >= item.price) {
                item.purchase(1);
                this.balance = this.balance - item.price;
            }
            else
                System.out.println("Gimme more money");
        }
        else System.out.println("Sorry, don't know that item");
    }

    void restockItem(String name, int amount){
        if(amount <=0){
            System.out.println("Invalid restock amount.");
        }

        else if(Stock.containsKey(name)){
            Item item = Stock.get(name);
            if ((long)item.stock + amount > Integer.MAX_VALUE) {
                item.stock = Integer.MAX_VALUE;
                System.out.println("Stock of " + name + " has reached its maximum value.");
            } else {
                item.restock(amount);
                System.out.println("Restocked " + amount + " units of " + name + ".");
            }

        } else {
            System.out.println("Item not found. Adding " + name + " with " + amount + " units.");
            Stock.put(name, new Item(1.00, Math.min(amount, Integer.MAX_VALUE)));
        }
    }

    void setPrice(String name, double price) {
        if (price < 0) {
            System.out.println("Invalid price.");
            return;
        }

        if (Stock.containsKey(name)) {
            Stock.get(name).setPrice(price);
            System.out.println("Set price of " + name + " to " + price + ".");
        } else {
            System.out.println("Item " + name + " not found.");
        }
    }


}

class Examples {
}

