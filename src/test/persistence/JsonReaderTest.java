package persistence;

import model.Bakery;
import model.Item;
import model.Receipt;
import model.Worker;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {
    private List<Item> testItems; 
    private List<Worker> testWorkers; 
    private Receipt testReceipt;

    @BeforeEach
    void setUp() {
        testItems = new ArrayList<>();
        testWorkers = new ArrayList<>();
        testReceipt = new Receipt();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.readReceipt();
            reader.readItems();
            reader.readWorkers();
            fail("Expected IOException due to invalid file path");
        } catch (IOException e) {
            // Expected exception
        }
    }

    @Test
    void testReaderEmptyReceipt() {
        try {
            JsonWriter writer = new JsonWriter("./data/testReaderEmptyBakery.json");
            writer.open();
            writer.write(testReceipt, testWorkers, testItems);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderEmptyBakery.json");
            Receipt readReceipt = reader.readReceipt();
            assertTrue(readReceipt.getOrders().isEmpty());
            assertTrue(readReceipt.getNotes().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderValidFile() {
        try {
            testWorkers.add(new Worker("Worker A", 1234));
            testItems.add(new Item("Bread"));

            JsonWriter writer = new JsonWriter("./data/testReaderGeneralBakery.json");
            writer.open();
            writer.write(testReceipt, testWorkers, testItems);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderGeneralBakery.json");
            Receipt readReceipt = reader.readReceipt();
            List<Worker> readWorkers = reader.readWorkers();
            List<Item> readItems = reader.readItems();

            assertNotNull(readReceipt);
            assertTrue(readReceipt.getOrders().isEmpty());
            assertTrue(readReceipt.getNotes().isEmpty());
            assertEquals(1, readWorkers.size());
            assertEquals("Worker A", readWorkers.get(0).getName());
            assertEquals(1234, readWorkers.get(0).getPin());
            assertEquals(1, readItems.size());
            assertEquals("Bread", readItems.get(0).getName());
        } catch (IOException e) {
            fail();
        }
    }
}
