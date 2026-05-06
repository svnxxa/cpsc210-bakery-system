package model;

// import static org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ReceiptTest {
    private Receipt testReceipt;
    private Item i1;
    private Item i2;
    private List<Item> items;

    @BeforeEach
    void runBefore() {
        testReceipt = new Receipt();
        i1 = new Item("Bread");
        i2 = new Item("Cookie");
        items = new ArrayList<Item>();
        items.add(i1);
        items.add(i2);

    }

    @Test
    void testConstructor() {
        assertTrue(testReceipt.getOrders().isEmpty());
        assertTrue(testReceipt.getNotes().isEmpty());
    }

    @Test
    void testAddOrderWithNotes() {
        testReceipt.addOrder(items, "Notesss");
        
        assertEquals(1, testReceipt.getOrders().size());
        assertEquals(1, testReceipt.getNotes().size());
        assertEquals("Bread, Cookie", testReceipt.getOrders().get(0));
        assertEquals("Notesss", testReceipt.getNotes().get(0));
    }

    @Test
    void testAddOrderWithoutNotes() {
        testReceipt.addOrder(items, null);
        
        assertEquals(1, testReceipt.getOrders().size());
        assertEquals(1, testReceipt.getNotes().size());
        assertEquals("Bread, Cookie", testReceipt.getOrders().get(0));
        assertEquals("", testReceipt.getNotes().get(0));
    }

}
