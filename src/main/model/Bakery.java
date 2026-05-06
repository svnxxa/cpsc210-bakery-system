package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import persistence.JsonReader;
import persistence.JsonWriter;

// Represents a backery that shows backery features
public class Bakery {
    private Cart cart;
    private List<Worker> workers;

    // EFFECTS: creates a bakery with an empty cart and empty list of workers
    public Bakery() {
        cart = new Cart();
        workers = new ArrayList<>();
    }

    // EFFECTS: returns the cart of the bakery
    public Cart getCart() {
        return cart;
    }

    // EFFECTS: returns the list of workers in the bakery
    public List<Worker> getWorkers() {
        return workers;
    }


    // REQUIRES: worker must not be null
    // MODIFIES: this
    // EFFECTS: adds the given worker to the list of workers
    public void addWorker(Worker worker) {
        workers.add(worker);

        EventLog.getInstance().logEvent(new Event("A worker, " + worker.getName().toString() + ", has been added!"));   
    }

    // MODIFIES: this
    // EFFECTS: loads data from the JsonReader, updates the cart's receipt, list of workers,
    //          available items
    public void loadData(JsonReader jsonReader) throws IOException {
        Receipt receipt = jsonReader.readReceipt();
        cart.setReceipt(receipt);
        workers = jsonReader.readWorkers();
        List<Item> items = jsonReader.readItems();
        Item.setItems(items);
    }

    // MODIFIES: jsonWriter
    // EFFECTS: writes the current cart's receipt, list of workers, and available items to the JsonWriter
    public void saveData(JsonWriter jsonWriter) throws FileNotFoundException {
        jsonWriter.open();
        jsonWriter.write(cart.getReceipt(), workers, Item.getAvailableItems());
        jsonWriter.close();
    }
}
