import java.util.HashMap;
import java.util.Map;

class VendorSystem {
    HashMap<String, Vending> vendors = new HashMap<>();

    void addVendor(String vendorName, Vending vending) {
        if (vendors.containsKey(vendorName)) {
            System.out.println("Vendor " + vendorName + " already exists.");
        } else {
            vendors.put(vendorName, vending);
            System.out.println("Vendor " + vendorName + " added.");
        }
    }

    void printInventory(String vendorName) {
        if (!vendors.containsKey(vendorName)) {
            System.out.println("Vendor " + vendorName + " not found.");
            return;
        }
        Vending vending = vendors.get(vendorName);
        System.out.println("Inventory for vendor " + vendorName + ":");
        for (Map.Entry<String, Item> entry : vending.getStock().entrySet()) {
            String itemName = entry.getKey();
            Item item = entry.getValue();
            System.out.println(itemName + ": " + item.stock + " units at $" + item.price);
        }
    }

    void printAllInventories() {
        for (String vendorName : vendors.keySet()) {
            printInventory(vendorName);
            System.out.println();
        }
    }
}
