package ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;

// represents the worker GUI for the bakery application
public class WorkerGUI extends JFrame implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private Bakery bakery;
    private Worker currentWorker;
    private JPanel panel;

    // EFFECTS: initializes the workerGUI with the specified bakery
    public WorkerGUI(Bakery bakery) {
        this.bakery = bakery;
        setTitle("Worker Menu");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        loginDialog();

        setVisible(true);
    }

    // MODIFIES this
    // EFFECTS: displays a login dialog for the worker
    public void loginDialog() {
        panel = new JPanel();
        JLabel label = new JLabel("Enter your PIN or click 'New Worker' to create a new account");
        panel.add(label);

        buttons();

        add(panel, BorderLayout.CENTER);
    }

    // EFFECTS: creates and adds login and new worker buttons to the panel
    public void buttons() {
        JButton loginButton = new JButton("Login");
        loginButton.setActionCommand("login");
        loginButton.addActionListener(this);
        loginButton.setFocusable(false);
        panel.add(loginButton);

        JButton newWorkerButton = new JButton("New Worker");
        newWorkerButton.setActionCommand("new");
        newWorkerButton.addActionListener(this);
        newWorkerButton.setFocusable(false);
        panel.add(newWorkerButton);

    }

    // EFFECTS: performs actions based on the action command
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("login")) {
            handleLogin();
        } else if (e.getActionCommand().equals("new")) {
            createNewWorker();           
        } else if (currentWorker != null) {
            if (e.getActionCommand().equals("add")) {
                addItem();
            } else if (e.getActionCommand().equals("remove")) {
                removeItem();
            } else if (e.getActionCommand().equals("quit")) {
                dispose();
            }
        }
    }

    // MODIFIES: bakery
    // EFFECTS: creates a new worker and adds them to the bakery
    private void createNewWorker() {
        String workerName = JOptionPane.showInputDialog(this, "Enter your name: ");
        String workerPin = JOptionPane.showInputDialog(this, "Enter your PIN: ");
        int workerPinInt = Integer.parseInt(workerPin);

        if (workerName != null && workerPin != null) {
            Worker newWorker = new Worker(workerName, workerPinInt);
            bakery.addWorker(newWorker);
            currentWorker = newWorker;
            JOptionPane.showMessageDialog(this, "New worker added and logged in successfully!");
            showWorkerMenu();
        }
    }

    // MODIFIES: currentWorker
    // EFFECTS: handles the login process for existing workers
    private void handleLogin() {
        String workerPin = JOptionPane.showInputDialog(this, "Enter your PIN:");
        if (workerPin != null) {
            int pinInt = Integer.parseInt(workerPin);
            currentWorker = findWorkerByPin(pinInt);
            if (currentWorker != null) {
                JOptionPane.showMessageDialog(this, "Logged in successfully!");
                showWorkerMenu();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid PIN!");
            }
        }
    }

    // EFFECTS: finds a worker by their PIN
    private Worker findWorkerByPin(int pin) {
        for (Worker worker : bakery.getWorkers()) {
            if (worker.getPin() == pin) {
                return worker;
            }
        }
        return null;
    }

    // EFFECTS: displays the worker menu with options to add/remove items
    private void showWorkerMenu() {
        getContentPane().removeAll();
        // JPanel workerPanel = new JPanel();
        panel = new JPanel();
        JLabel label = new JLabel("Worker Menu");
        panel.add(label);

        JButton addButton = new JButton("Add Item");
        addButton.setActionCommand("add");
        addButton.addActionListener(this);
        addButton.setFocusable(false);
        panel.add(addButton);

        JButton removeButton = new JButton("Remove Item");
        removeButton.setActionCommand("remove");
        removeButton.addActionListener(this);
        removeButton.setFocusable(false);
        panel.add(removeButton);

        JButton quitButton = new JButton("Quit");
        quitButton.setActionCommand("quit");
        quitButton.addActionListener(this);
        quitButton.setFocusable(false);
        panel.add(quitButton);

        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    // MODIFIES: Item
    // EFFECTS: adds a new item to the bakery's item list
    private void addItem() {
        String itemName = JOptionPane.showInputDialog(this, "Enter item name to add:");
        if (itemName != null) {
            Item newItem = new Item(itemName.trim());
            Item.addItemToList(newItem);
            JOptionPane.showMessageDialog(this, "Item added successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid item name");
        }
    }


    // MODIFIES: Item
    // EFFECTS: removes a item from the bakery's item list
    private void removeItem() {
        String itemName = JOptionPane.showInputDialog(this, "Enter item name to remove:");
        if (itemName != null) {
            Item itemToRemove = null;
            for (Item item : Item.getAvailableItems()) {
                if (item.getName().equals(itemName)) {
                    itemToRemove = item;
                    break;
                }
            }
    
            if (itemToRemove != null) {
                Item.removeItem(itemToRemove);
                JOptionPane.showMessageDialog(this, "Item removed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Item not found!");
            }
        }
    }
    



}
