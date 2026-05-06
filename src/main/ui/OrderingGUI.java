package ui;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;

// represents the ordering GUI for the bakery application
public class OrderingGUI extends JFrame implements ActionListener {
    private Bakery bakery;
    private DefaultListModel<String> listModel;
    private JList<String> itemJList;
    private JTextArea notesTextArea;
    private JPanel panel;

    // EFFECTS: initializes the OrderingGUI w specified bakery
    public OrderingGUI(Bakery bakery) {
        this.bakery = bakery;
        setTitle("Ordering Menu");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);    

        initializePanel();

        setVisible(true);
    }

    // EFFECTS: initializes the GUI panel with header, item list, notes area, and buttons
    private void initializePanel() {
        setLayout(new BorderLayout());
        add(createHeader(), BorderLayout.NORTH);
        add(createItemPanel(), BorderLayout.CENTER);
        add(createNotesPanel(), BorderLayout.WEST);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }


    // EFFECTS: creates and returns the header panel
    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        JLabel label = new JLabel("Ordering Menu", SwingConstants.CENTER);
        headerPanel.add(label);
        return headerPanel;
    }

    // EFFECTS: creates and returns the item selection panel
    private JPanel createItemPanel() {
        JPanel itemPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();

        for (Item item : Item.getAvailableItems()) {
            listModel.addElement(item.getName());
        }

        itemJList = new JList<>(listModel);
        itemPanel.add(new JScrollPane(itemJList), BorderLayout.CENTER);

        return itemPanel;
    }

    // EFFECTS: creates and returns the notes panel
    private JPanel createNotesPanel() {
        JPanel notesPanel = new JPanel(new BorderLayout());
        JLabel notesLabel = new JLabel("Notes:");
        notesTextArea = new JTextArea(5, 20);

        JScrollPane notesScrollPane = new JScrollPane(notesTextArea);
        notesPanel.add(notesLabel, BorderLayout.NORTH);
        notesPanel.add(notesScrollPane, BorderLayout.CENTER);

        return notesPanel;
    }

    // EFFECTS: creates and returns the buttons
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton addItemsButton = new JButton("Add Item");
        addItemsButton.setActionCommand("addItems");
        addItemsButton.addActionListener(this);
        addItemsButton.setFocusable(false);
        buttonPanel.add(addItemsButton);

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.setActionCommand("placeOrder");
        placeOrderButton.addActionListener(this);
        addItemsButton.setFocusable(false);
        buttonPanel.add(placeOrderButton);

        JButton reorderButton = new JButton("Alphabetical Order");
        reorderButton.setActionCommand("reorder");
        reorderButton.addActionListener(this);
        reorderButton.setFocusable(false);
        buttonPanel.add(reorderButton);
        return buttonPanel;
    }


    // EFFECTS: performs actions based on the action command of the event
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addItems")) {
            addItems();
        } else if (e.getActionCommand().equals("placeOrder")) {
            placeOrder();
        } else if (e.getActionCommand().equals("reorder")) {
            reorderItems();
        }
    }

    // MODIFIES: bakery.getCart()
    // EFFECTS: adds selected items to the cart
    private void addItems() {
        List<String> selectedItems = itemJList.getSelectedValuesList();
        if (selectedItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items selected for order.");
            return;
        }

        for (String itemName : selectedItems) {
            Item item = findItemByName(itemName);
            if (item != null) {
                bakery.getCart().addItemToCart(item);
            }
        }

        JOptionPane.showMessageDialog(this, "Items added to cart successfully!");
    }

    // MODIFIES: bakery.getCart()
    // EFFECTS: places the order with notes
    private void placeOrder() {
        String notes = notesTextArea.getText();
        bakery.getCart().addNotes(notes);
        bakery.getCart().placeOrder();
        JOptionPane.showMessageDialog(this, "Order placed successfully!");

        dispose();
    }

    // EFFECTS: reorders the available items in alphabetical order
    private void reorderItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            items.add(listModel.get(i));
        }

        Collections.sort(items, String.CASE_INSENSITIVE_ORDER);

        listModel.clear();
        for (String item : items) {
            listModel.addElement(item);
        }
    }

    // EFFFECTS: finds an item by name
    private Item findItemByName(String itemName) {
        // List<Item> items = Item.getAvailableItems();
        for (Item item : Item.getAvailableItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;            
            }
        }
        return null;
    }

}
