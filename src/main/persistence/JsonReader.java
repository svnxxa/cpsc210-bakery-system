package persistence;

import model.Receipt;
import model.Worker;
import model.Bakery;
import model.Item;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// This class references code from the JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads receipt from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads receipt from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Receipt readReceipt() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseReceipt(jsonObject);
    }

    // EFFECTS: reads workers from file and returns them as a list;
    // throws IOException if an error occurs reading data from file
    public List<Worker> readWorkers() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkers(jsonObject);
    }

    // EFFECTS: reads items from file and returns them as a list;
    // throws IOException if an error occurs reading data from file
    public List<Item> readItems() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseItems(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses receipt from JSON object and returns it
    private Receipt parseReceipt(JSONObject jsonObject) {
        JSONObject receiptJson = jsonObject.getJSONObject("receipt");
        Receipt re = new Receipt();
        addOrdersAndNotes(re, receiptJson);
        return re;
    }

    // EFFECTS: parses workers from JSON object and returns them as a list
    private List<Worker> parseWorkers(JSONObject jsonObject) {
        List<Worker> workers = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("workers");
        for (Object json : jsonArray) {
            JSONObject nextWorker = (JSONObject) json;
            addWorker(workers, nextWorker);
        }
        return workers;
    }

    // EFFECTS: parses items from JSON object and returns them as a list
    private List<Item> parseItems(JSONObject jsonObject) {
        List<Item> items = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(items, nextItem);
        }
        return items;
    }

    // MODIFIES: re
    // EFFECTS: parses orders and notes from JSON object and adds them to receipt
    private void addOrdersAndNotes(Receipt re, JSONObject jsonObject) {
        JSONArray ordersArray = jsonObject.getJSONArray("orders");
        JSONArray notesArray = jsonObject.getJSONArray("notes");
        for (int i = 0; i < ordersArray.length(); i++) {
            re.getOrders().add(ordersArray.getString(i));
            re.getNotes().add(notesArray.getString(i));
        }
    }

    // MODIFIES: workers
    // EFFECTS: parses worker from JSON object and adds it to the list of workers
    private void addWorker(List<Worker> workers, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int pin = jsonObject.getInt("pin");
        workers.add(new Worker(name, pin));
    }

    // MODIFIES: items
    // EFFECTS: parses item from JSON object and adds it to the list of items
    private void addItem(List<Item> items, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        items.add(new Item(name));
    }

    // EFFECTS: reads bakery from file and returns it
    //          throws IOEexception if an error occurs
    public Bakery readBakery() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBakery(jsonObject);
    }

    // EFFECTS: parses bakery from JSON object and returns it
    public Bakery parseBakery(JSONObject jsonObject) {
        Bakery bakery = new Bakery();
        bakery.getCart().setReceipt(parseReceipt(jsonObject));
        bakery.getWorkers().addAll(parseWorkers(jsonObject));
        List<Item> items = parseItems(jsonObject);
        for (Item i : items) {
            Item.addItemToList(i);
        }
        return bakery;
    }
}
