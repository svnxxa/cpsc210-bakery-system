package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;


// Represents a receipt that shows placed orders so far
public class Receipt implements Writable {
    private ArrayList<String> orders;
    private ArrayList<String> notes;

    //EFFECTS: generates an empty list of placed orders
    public Receipt() {
        orders = new ArrayList<>();
        notes = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add items and notes to the placedOrder and notes lists
    public void addOrder(List<Item> items, String n) {

        String orderDetails = "";
        for (int i = 0; i < items.size(); i++) {
            orderDetails += items.get(i).getName();
            if (i < items.size() - 1) {
                orderDetails += ", ";
            }
        }

        orders.add(orderDetails.toString());

        if (n == null) {
            notes.add("");
        } else {
            notes.add(n);
        }
    }


    // getters
    public ArrayList<String> getOrders() {
        return orders;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("orders", orderToJson());
        json.put("notes", notesToJson());
        return json;
    }

    // returns orders in this receipt as a JSON array
    private JSONArray orderToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String order : orders) {
            jsonArray.put(order);
        }
        
        return jsonArray;
    }

    // returns notes in this receipt as a JSON array
    private JSONArray notesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String note : notes) {
            jsonArray.put(note);
        }
        
        return jsonArray;
    }

}
