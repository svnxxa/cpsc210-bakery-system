package model;

import java.util.*;

// Represents a cart that shows added food items and notes
public class Cart {

    private ArrayList<Item> items;
    private String notes;
    private Receipt receipt;

    // EFFECTS: generates an empty list of items, empty notes and receipt
    public Cart() {
        items = new ArrayList<>();
        notes = "";
        receipt = new Receipt();
    }

    // REQUIRES: item should be available
    // MODIFIES: this
    // EFFECTS: add selected items to the list
    public void addItemToCart(Item i) {
        items.add(i);
    }

    // MODIFIES: this
    // EFFECTS: add notes
    public void addNotes(String n) {
        notes = n;
    }

    // MODIFIES: this
    // EFFECTS: move items and notes to Receipt and make items and notes empty
    public void placeOrder() {
        receipt.addOrder(items, notes);
        items.clear();
        notes = "";
    }


    // getters and setters
    public ArrayList<Item> getItems() {
        return items;
    }

    public String getNotes() {
        return notes;
    }

    public Receipt getReceipt() {
        return receipt;
    }
    
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

}
