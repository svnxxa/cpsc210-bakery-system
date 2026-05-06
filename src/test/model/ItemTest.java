package model;

// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemTest {
    private Item i1;
    private Item i2;
    // private static ArrayList<Item> items;
    
    @BeforeEach
    void runBefore() {
        i1 = new Item("Bread");
        i2 = new Item("Cookie");
        // items = new ArrayList<Item>();
        Item.getAvailableItems().clear();
    }

    @Test
    void testConstructor() {
        assertEquals("Bread", i1.getName());
    }

    @Test
    void testAddItemToList() {
        Item.addItemToList(i1);
        Item.addItemToList(i2);

        List<Item> availableItems = Item.getAvailableItems();
        assertEquals(i1, availableItems.get(0));
        assertEquals(i2, availableItems.get(1));
        assertEquals(2, availableItems.size());
    }

    @Test
    void testRemoveItem() {
        Item.addItemToList(i1);
        Item.addItemToList(i2);

        List<Item> availableItems = Item.getAvailableItems();
        assertEquals(2, availableItems.size());

        Item.removeItem(i1);
        assertEquals(i2, availableItems.get(0));
        assertEquals(1, availableItems.size());

    }

    @Test
    void testGetAvailableItems() {
        Item.addItemToList(i1);
        Item.addItemToList(i2);
        
        List<Item> availableItems = Item.getAvailableItems();
        assertTrue(availableItems.contains(i1));
        assertTrue(availableItems.contains(i2));

    }

    @Test
    void testSetItems() {
        List<Item> items = List.of(i1, i2);
        Item.setItems(items);

        List<Item> availableItems = Item.getAvailableItems();
        assertEquals(2, availableItems.size());
        assertTrue(availableItems.contains(i1));
        assertTrue(availableItems.contains(i2));
    }
}


