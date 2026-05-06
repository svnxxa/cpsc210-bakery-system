package model;

import java.util.*;

import org.json.JSONObject;

import persistence.Writable;

// Represents available food items
public class Item implements Writable {
    private static List<Item> items = new ArrayList<>();
    private String name;

    //EFFECTS: generates an item (name)
    public Item(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: add an item to the items list
    public static void addItemToList(Item item) {
        items.add(item);

        EventLog.getInstance().logEvent(new Event("An item, " + item.getName().toString() + ", has been added!"));
    }

    // MODIFIES: this
    // EFFECTS: remove an item from the items list
    public static void removeItem(Item item) {
        items.remove(item);

        EventLog.getInstance().logEvent(new Event("An item, " + item.getName().toString() + ", has been removed!"));
    }

    // EFFECTS: get the items list
    public static List<Item> getAvailableItems() {
        return items;
    }

    // getter
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: sets the items list
    public static void setItems(List<Item> itemList) {
        items = itemList;
    }

    // // MODIFIES: this
    // // EFFECTS: clear the items list
    // public static void clearItems() {
    //     items.clear();
    // }

    // EFFECTS: returns this  as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        return json;
    }





}