import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;

public class JUnitTest {

    static Vending vendor;
    static Item item;

    @BeforeEach
    public void setup() {
        vendor = new Vending(10,5);
    }

    @Test
    public void addMoneyTest(){
        vendor.addMoney(1.25);
        Assertions.assertEquals(1.25, vendor.getBalance());
    }

    @Test
    public void addMoneyNegative(){
        vendor.addMoney(-1.25);
        Assertions.assertEquals(0, vendor.getBalance());
    }

    @Test
    public void addMoneyMax(){
        vendor.addMoney(1.25);
        vendor.addMoney(Integer.MAX_VALUE);
        Assertions.assertEquals(Integer.MAX_VALUE, vendor.getBalance());
    }

    @Test
    public void addMoneyMin(){
        vendor.addMoney(1.25);
        vendor.addMoney(Integer.MIN_VALUE);
        Assertions.assertEquals(1.25, vendor.getBalance());
    }

    @Test
    public void addMoneyZero() {
        vendor.addMoney(0);
        Assertions.assertEquals(0, vendor.getBalance());
    }

    @Test
    public void buyItem(){
        vendor.addMoney(1.25);
        vendor.select("Candy");
        Assertions.assertEquals(0, vendor.getBalance());
    }

    @Test
    public void buyItemLow(){
        vendor.addMoney(0);
        vendor.select("Candy");
        Assertions.assertEquals(0, vendor.getBalance());
    }

    @Test
    public void buyItemUnknown(){
        vendor.addMoney(1.25);
        vendor.select("Soda");
        Assertions.assertEquals(1.25, vendor.getBalance());
    }

    @Test
    public void buyItemOutOfStock() {
        for (int i = 0; i < 10; i++) {
            vendor.addMoney(1.25);
            vendor.select("Candy");
        }
        vendor.select("Candy");
        Assertions.assertEquals(0, getStockQuantity("Candy"));
    }

    @Test
    public void buyItemStockReduction() {
        vendor.addMoney(1.25);
        vendor.select("Candy");
        Assertions.assertEquals(9, getStockQuantity("Candy"));
    }

    @Test
    public void refillBalanceToBuy() {
        vendor.addMoney(0.50);
        vendor.select("Candy");
        Assertions.assertEquals(0.50, vendor.getBalance());
        vendor.addMoney(1.00);
        vendor.select("Candy");
        Assertions.assertEquals(0.25, vendor.getBalance());
    }


    @Test
    public void emptyPartialStock() {
        vendor.addMoney(2.50);
        vendor.select("Gum");
        vendor.select("Gum");
        Assertions.assertEquals(3, getStockQuantity("Gum"));
    }

    @Test
    public void emptyMultipleItems() {
        for (int i = 0; i < 10; i++) {
            vendor.addMoney(1.25);
            vendor.select("Candy");
        }
        for (int i = 0; i < 5; i++) {
            vendor.addMoney(0.50);
            vendor.select("Gum");
        }
        Assertions.assertEquals(0, getStockQuantity("Candy"));
        Assertions.assertEquals(0, getStockQuantity("Gum"));
    }

    @Test
    public void restockItem() {
        vendor.restockItem("Candy", 5);
        Assertions.assertEquals(15, getStockQuantity("Candy"));
    }

    @Test
    public void restockNewItem() {
        vendor.restockItem("Chips", 10);
        Assertions.assertEquals(10, getStockQuantity("Chips"));
        Assertions.assertEquals(1.00, Vending.Stock.get("Chips").price);
    }

    @Test
    public void restockNegative() {
        vendor.restockItem("Candy", -5);
        Assertions.assertEquals(10, getStockQuantity("Candy"));
    }

    @Test
    public void restockZero() {
        vendor.restockItem("Candy", 0);
        Assertions.assertEquals(10, getStockQuantity("Candy"));
    }

    @Test
    public void restockMaxValue() {
        vendor.restockItem("Candy", Integer.MAX_VALUE);
        Assertions.assertEquals(Integer.MAX_VALUE, getStockQuantity("Candy"));

        vendor.restockItem("Candy", 1);
        Assertions.assertEquals(Integer.MAX_VALUE, getStockQuantity("Candy"));
    }

    @Test
    public void setPriceForExistingItem() {
        vendor.setPrice("Candy", 1.50);
        Assertions.assertEquals(1.50, Vending.Stock.get("Candy").price);
    }

    @Test
    public void setPriceForNewItem() {
        vendor.restockItem("Cookies", 10);
        vendor.setPrice("Cookies", 2.00);
        Assertions.assertEquals(2.00, Vending.Stock.get("Cookies").price);
    }

    @Test
    public void setNegativePrice() {
        vendor.setPrice("Candy", -1.00);
        Assertions.assertEquals(1.25, Vending.Stock.get("Candy").price);
    }

    @Test
    public void setPriceNonExistentItem() {
        vendor.setPrice("NonExistentItem", 3.00);
        Assertions.assertFalse(Vending.Stock.containsKey("NonExistentItem"));
    }

    @Test
    public void buyItemAfterRestocking() {
        vendor.restockItem("Candy", 5);
        vendor.addMoney(1.25);
        vendor.select("Candy");
        Assertions.assertEquals(14, getStockQuantity("Candy")); // Stock reduces after purchase
    }


    private int getStockQuantity(String name) {
        if (Vending.Stock.containsKey(name)) {
            return Vending.Stock.get(name).stock;
        } else {
            return 0;
        }
    }




}
