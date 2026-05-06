package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkerTest {
    private Worker testWorker;
    private List<Item> testItems;

    @BeforeEach
    void setUp() {
        testWorker = new Worker("Mae", 1111);
        testItems = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals("Mae", testWorker.getName());
        assertEquals(1111, testWorker.getPin());
    }

    @Test
    void testAddItem() {
        testItems.add(new Item("Bread"));
        assertEquals(1, testItems.size());
        assertEquals("Bread", testItems.get(0).getName());
    }

    @Test
    void testAddDuplicatedItems() {
        testItems.add(new Item("Bread"));
        testItems.add(new Item("Bread"));
        assertEquals(2, testItems.size());
        assertEquals("Bread", testItems.get(0).getName());
        assertEquals("Bread", testItems.get(1).getName());
    }

    @Test
    void testRemoveItem() {
        testItems.add(new Item("Bread"));
        testItems.add(new Item("Cookie"));
        testItems.removeIf(item -> item.getName().equals("Bread"));
        assertEquals(1, testItems.size());
        assertEquals("Cookie", testItems.get(0).getName());
    }

    @Test
    void testRemoveNonExistentItem() {
        testItems.add(new Item("Bread"));
        testItems.add(new Item("Cookie"));
        testItems.removeIf(item -> item.getName().equals("Hi"));
        assertEquals(2, testItems.size());
        assertEquals("Bread", testItems.get(0).getName());
        assertEquals("Cookie", testItems.get(1).getName());
    }
}
