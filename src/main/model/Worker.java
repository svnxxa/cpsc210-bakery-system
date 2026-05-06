package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents a worker working in this bakery
public class Worker implements Writable {
    private String name;
    private int pin;

    // EFFECTS: generates a worker with their name and PIN
    public Worker(String name, int pin) {
        this.name = name;
        this.pin = pin;
    }

    // MODIFIES: this
    // EFFECTS: add an item to the available items list if the item is not in the list
    public static void addItem(String itemName) {
        List<Item> items = Item.getAvailableItems();

        boolean itemExists = false;
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                itemExists = true;
            }
        }

        if (!itemExists) {
            Item item = new Item(itemName);
            Item.addItemToList(item);
        }
    }

    // MODIFIES: this
    // EFFECTS: remove an item from the available items list
    public static void removeItem(String itemName) {
        List<Item> items = Item.getAvailableItems();
        for (Item i : items) {
            if (i.getName().equals(itemName)) {
                items.remove(i);
                break;
            }
        }
    }


    // getters
    public String getName() {
        return name;
    }

    public int getPin() {
        return pin;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("pin", pin);
        return json;
    }


    
}
