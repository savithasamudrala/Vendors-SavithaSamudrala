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
        vendor.select("Chips");
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


    private int getStockQuantity(String name) {
        return Vending.Stock.get(name).stock;
    }



}
