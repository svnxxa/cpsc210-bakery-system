package model;

// import static org.junit.Assert.assertTrue;
// import static org.junit.jupiter.api.*;
// import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartTest {
    private Cart testCart;
    private Item i1;
    private Item i2;
    // private ArrayList<Item> items;
    // private Receipt r;

    @BeforeEach
    void runBefore() {
        testCart = new Cart();
        i1 = new Item("bread");
        i2 = new Item("cookie");
        // items = new ArrayList<Item>();
        // items.add(i1);
        // items.add(i2);
        // r = new Receipt();
    }

    @Test
    void testConstructor() {
        ArrayList<Item> itemList = testCart.getItems();
        String notes = testCart.getNotes();
        assertTrue(itemList.isEmpty());
        assertEquals(0, itemList.size());
        assertEquals("", notes);
    }

    @Test
    void testAddItemToCart() {
        testCart.addItemToCart(i1);
        testCart.addItemToCart(i2);

        ArrayList<Item> itemList = testCart.getItems();
        assertFalse(itemList.isEmpty());
        assertEquals(i1, itemList.get(0));
        assertEquals(i2, itemList.get(1));
        assertEquals(2, itemList.size());
    }

    @Test
    void testAddNotes() {
        testCart.addNotes("Hi");
        assertEquals("Hi", testCart.getNotes());
    }


    @Test
    void testPlaceOrder() {
        testCart.addItemToCart(i1);
        testCart.addItemToCart(i2);
        testCart.addNotes("Hi");
        testCart.placeOrder();

        assertTrue(testCart.getItems().isEmpty());
        assertEquals("", testCart.getNotes());

        Receipt receipt = testCart.getReceipt();
        assertEquals(1, receipt.getOrders().size());
        assertEquals(1, receipt.getNotes().size());
        assertEquals("bread, cookie", receipt.getOrders().get(0));
        assertEquals("Hi", receipt.getNotes().get(0));
    }

    @Test
        void testSetReceipt() {
        Cart cart = new Cart();
        Receipt newReceipt = new Receipt();
    
        List<Item> items = new ArrayList<>();
        items.add(new Item("bread"));
        newReceipt.addOrder(items, "lunch");
    
        cart.setReceipt(newReceipt);
    
        assertEquals(newReceipt, cart.getReceipt());
    }



}
