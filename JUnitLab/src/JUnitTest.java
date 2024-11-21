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




}
