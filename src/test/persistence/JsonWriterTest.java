package persistence;

import model.Item;
import model.Receipt;
import model.Worker;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Receipt receipt = new Receipt();
            List<Worker> workers = List.of(new Worker("Worker A", 1234));
            List<Item> items = List.of(new Item("Bread"));
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("Expected IOException due to invalid file path");
        } catch (IOException e) {
            // Expected exception
        }
    }

    @Test
    void testWriterEmptyReceipt() {
        try {
            Receipt receipt = new Receipt();
            List<Worker> workers = List.of();
            List<Item> items = List.of();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBakery.json");
            writer.open();
            writer.write(receipt, workers, items);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBakery.json");
            Receipt readReceipt = reader.readReceipt();
            List<Worker> readWorkers = reader.readWorkers();
            List<Item> readItems = reader.readItems();

            assertNotNull(readReceipt);
            assertTrue(readReceipt.getOrders().isEmpty());
            assertTrue(readReceipt.getNotes().isEmpty());
            assertTrue(readWorkers.isEmpty());
            assertTrue(readItems.isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testWriterGeneralBakery() {
        try {
            Receipt receipt = new Receipt();
            receipt.addOrder(List.of(new Item("Bread")), "No notes");
            List<Worker> workers = List.of(new Worker("Worker A", 1234));
            List<Item> items = List.of(new Item("Bread"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBakery.json");
            writer.open();
            writer.write(receipt, workers, items);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBakery.json");
            Receipt readReceipt = reader.readReceipt();
            List<Worker> readWorkers = reader.readWorkers();
            List<Item> readItems = reader.readItems();

            assertNotNull(readReceipt);
            assertEquals(1, readReceipt.getOrders().size());
            assertEquals("Bread", readReceipt.getOrders().get(0));
            assertEquals("No notes", readReceipt.getNotes().get(0));
            assertEquals(1, readWorkers.size());
            assertEquals("Worker A", readWorkers.get(0).getName());
            assertEquals(1234, readWorkers.get(0).getPin());
            assertEquals(1, readItems.size());
            assertEquals("Bread", readItems.get(0).getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
