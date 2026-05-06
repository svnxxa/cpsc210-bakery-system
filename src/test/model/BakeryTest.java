package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BakeryTest {

    private Bakery bakery;
    private Worker worker1;
    private Worker worker2;
    private List<Item> testItems;

    @BeforeEach
    void setUp() {
        bakery = new Bakery();
        worker1 = new Worker("Worker A", 1234);
        worker2 = new Worker("Worker B", 5678);
        testItems = new ArrayList<>();
    }

    @Test
    void testAddWorker() {
        bakery.addWorker(worker1);
        bakery.addWorker(worker2);

        List<Worker> workers = bakery.getWorkers();
        assertEquals(2, workers.size());
        assertTrue(workers.contains(worker1));
        assertTrue(workers.contains(worker2));
    }

    @Test
    void testLoadData() {
        try {
            JsonWriter writer = new JsonWriter("./data/testLoadBakery.json");
            writer.open();
            Receipt receipt = new Receipt();
            List<Worker> workers = List.of(worker1, worker2);
            writer.write(receipt, workers, testItems); // Write the test items list
            writer.close();

            bakery.loadData(new JsonReader("./data/testLoadBakery.json"));

            assertEquals(new Receipt().getOrders(), bakery.getCart().getReceipt().getOrders());
            assertEquals(new Receipt().getNotes(), bakery.getCart().getReceipt().getNotes());

            List<Worker> expectedWorkers = List.of(worker1, worker2);
            assertEquals(expectedWorkers.size(), bakery.getWorkers().size());

            assertEquals(testItems.size(), Item.getAvailableItems().size());
            for (Item item : testItems) {
                assertTrue(Item.getAvailableItems().contains(item));
            }

        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testSaveData() {
        bakery.addWorker(worker1);
        bakery.addWorker(worker2);

        Item item1 = new Item("Bread");
        Item item2 = new Item("Cookie");
        testItems.add(item1);
        testItems.add(item2);
        Item.addItemToList(item1);
        Item.addItemToList(item2);

        try {
            JsonWriter writer = new JsonWriter("./data/testSaveBakery.json");
            writer.open();
            bakery.saveData(writer);
            writer.close();

            JsonReader reader = new JsonReader("./data/testSaveBakery.json");
            Bakery loadedBakery = reader.readBakery();

            List<Worker> loadedWorkers = loadedBakery.getWorkers();
            assertEquals(2, loadedWorkers.size());
            assertTrue(containsWorker(loadedWorkers, worker1));
            assertTrue(containsWorker(loadedWorkers, worker2));

        } catch (IOException e) {
            fail();
        }
    }

    private boolean containsWorker(List<Worker> workers, Worker worker) {
        for (Worker w : workers) {
            if (w.getName().equals(worker.getName()) && w.getPin() == worker.getPin()) {
                return true;
            }
        }
        return false;
    }
}
