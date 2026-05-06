package persistence;

// import model.WorkRoom;
import model.*;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.util.List;

// This class references code from the JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a writer that writes JSON representation of receipt to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of receipt, workersm items to file
    public void write(Receipt receipt, List<Worker> workers, List<Item> items) {
        JSONObject json = new JSONObject();
        json.put("receipt", receipt.toJson());
        json.put("workers", workersToJson(workers));
        json.put("items", itemsToJson(items));
        saveToFile(json.toString());
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }


    // EFFECTS: returns workers as a JSON array
    private JSONArray workersToJson(List<Worker> workers) {
        JSONArray jsonArray = new JSONArray();
        for (Worker worker : workers) {
            jsonArray.put(worker.toJson());
        }
        return jsonArray;
    } 

    // EFFECTS: returns items as a JSON array
    private JSONArray itemsToJson(List<Item> items) {
        JSONArray jsonArray = new JSONArray();
        for (Item item : items) {
            jsonArray.put(item.toJson());
        }
        return jsonArray;
    }
 
}
