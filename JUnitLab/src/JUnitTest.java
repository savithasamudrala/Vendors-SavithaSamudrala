import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

public class JUnitTest {

    static Vending vendor;
    static Item item;

    @BeforeEach
    public void setup() {
        vendor = new Vending(10, 5);
        vendor.getStock().clear();
        vendor.getStock().put("Candy", new Item(1.25, 10));
        vendor.getStock().put("Gum", new Item(0.50, 5));
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
        Assertions.assertEquals(14, getStockQuantity("Candy"));
    }


    private int getStockQuantity(String name) {
        if (Vending.Stock.containsKey(name)) {
            return Vending.Stock.get(name).stock;
        } else {
            return 0;
        }
    }

    @Test
    public void renameExistingItem() {
        vendor.renameItem("Candy", "Sweets");
        Assertions.assertFalse(Vending.Stock.containsKey("Candy"));
        Assertions.assertTrue(Vending.Stock.containsKey("Sweets"));
        Assertions.assertEquals(10, getStockQuantity("Sweets"));
        Assertions.assertEquals(1.25, Vending.Stock.get("Sweets").price);
    }


    @Test
    public void renameNonExistentItem() {
        vendor.renameItem("NonExistent", "Existent");
        Assertions.assertFalse(Vending.Stock.containsKey("Existent"));
    }


    @Test
    public void renameToExistingItem() {
        vendor.renameItem("Candy", "Gum");
        Assertions.assertTrue(Vending.Stock.containsKey("Candy"));
        Assertions.assertTrue(Vending.Stock.containsKey("Gum"));
        Assertions.assertEquals(10, getStockQuantity("Candy"));
        Assertions.assertEquals(5, getStockQuantity("Gum"));
    }


    @Test
    public void renameToEmptyString() {
        vendor.renameItem("Candy", "");
        Assertions.assertFalse(Vending.Stock.containsKey(""));
        Assertions.assertTrue(Vending.Stock.containsKey("Candy"));
    }


    @Test
    public void addMultipleVendors() {
        VendorSystem system = new VendorSystem();
        system.addVendor("Vendor1", new Vending(10, 5));
        system.addVendor("Vendor2", new Vending(20, 10));
        Assertions.assertEquals(2, system.vendors.size());
    }

    @Test
    public void printVendorInventory() {
        VendorSystem system = new VendorSystem();
        Vending vending = new Vending(10, 5);
        system.addVendor("Vendor1", vending);
        system.printInventory("Vendor1");
    }

    @Test
    public void printAllVendorsInventories() {
        VendorSystem system = new VendorSystem();
        system.addVendor("Vendor1", new Vending(10, 5));
        system.addVendor("Vendor2", new Vending(20, 10));
        system.printAllInventories();
    }

    @Test
    public void addDuplicateVendor() {
        VendorSystem system = new VendorSystem();
        system.addVendor("Vendor1", new Vending(10, 5));
        system.addVendor("Vendor1", new Vending(20, 10));
        Assertions.assertEquals(1, system.vendors.size());
    }

    @Test
    public void printNonExistentVendor() {
        VendorSystem system = new VendorSystem();
        system.printInventory("NonExistentVendor");
    }

    @Test
    public void removeExistingItem() {
        Vending vendor = new Vending(10, 5);
        vendor.removeItem("Candy");
        Assertions.assertFalse(vendor.getStock().containsKey("Candy"));
    }

    @Test
    public void removeNonExistentItem() {
        Vending vendor = new Vending(10, 5);
        vendor.removeItem("NonExistentItem");
        Assertions.assertTrue(vendor.getStock().containsKey("Candy"));
        Assertions.assertTrue(vendor.getStock().containsKey("Gum"));
    }

    @Test
    public void removeAllItems() {
        Vending vendor = new Vending(10, 5);
        vendor.removeItem("Candy");
        vendor.removeItem("Gum");
        Assertions.assertTrue(vendor.getStock().isEmpty());
    }


    @Test
    public void trackCustomerPurchases() {
        Vending vendor = new Vending(10, 5);
        vendor.addMoney(3.25);
        vendor.select("Candy");
        vendor.select("Candy");
        vendor.select("Gum");
        HashMap<String, Integer> trends = vendor.getPurchaseTrends();
        Assertions.assertEquals(2, trends.get("Candy"));
        Assertions.assertEquals(1, trends.get("Gum"));
    }




    @Test
    public void viewItemDetailsWithDescription() {
        Vending vendor = new Vending(10, 5);
        String candyDetails = vendor.viewItemDetails("Candy");
        Assertions.assertTrue(candyDetails.contains("Price: $1.25"));
        Assertions.assertTrue(candyDetails.contains("Stock: 10"));
        Assertions.assertTrue(candyDetails.contains("Description: A sweet treat to enjoy."));
    }

    @Test
    public void viewItemDetailsWithoutDescription() {
        Vending vendor = new Vending(10, 5);
        String gumDetails = vendor.viewItemDetails("Gum");
        Assertions.assertTrue(gumDetails.contains("Price: $0.5"));
        Assertions.assertTrue(gumDetails.contains("Stock: 5"));
        Assertions.assertTrue(gumDetails.contains("Description: No description available"));
    }


    @Test
    public void viewDetailsForNonExistentItem() {
        Vending vendor = new Vending(10, 5);
        String nonExistentDetails = vendor.viewItemDetails("Soda");
        Assertions.assertEquals("Item Soda not found.", nonExistentDetails);
    }





}
