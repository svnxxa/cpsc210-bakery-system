package ui;

import model.*;
import model.Event;
import persistence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

// represents the main GUI for the bakery application
public class BakeryGUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/bakery.json";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Bakery bakery;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: initializes the BakeryGUI
    public BakeryGUI() {
        setupFrame();
        initialize();
        setupLayout();
        setVisible(true);
    }

    // EFFECTS: sets up the JFrame
    private void setupFrame() {
        setTitle("BakeryUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
    }

    // EFFECTS: initializes the components and settings
    private void initialize() {
        bakery = new Bakery();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        loadDataOption();
    }

    // EFFECTS: sets up the layout and adds buttons
    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;

        createAndAddButtons(gbc);
    }

    // MODIFIES: this
    // EFFECTS: creates and adds buttons to the GUI
    private void createAndAddButtons(GridBagConstraints gbc) {
        addButton(createButton("Worker Menu", "work"), gbc);
        addButton(createButton("Order", "order"), gbc);
        addButton(createButton("Receipt", "receipt"), gbc);
        addButton(createButton("How to Use", "instr"), gbc);
        addButton(createButton("Exit", "exit"), gbc);
    }

    // EFFECTS: creates a button with the given label and action command
    private JButton createButton(String label, String actionCommand) {
        JButton button = new JButton(label);
        button.setActionCommand(actionCommand);
        button.setPreferredSize(new Dimension(170, 50));
        button.setFocusable(false);
        button.addActionListener(this);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: adds the given button to the GUI with the specified GridBagConstraints
    private void addButton(JButton button, GridBagConstraints gbc) {
        add(button, gbc);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("work")) {
            new WorkerGUI(bakery);
        } else if (e.getActionCommand().equals("order")) {
            new OrderingGUI(bakery);
        } else if (e.getActionCommand().equals("receipt")) {
            // showReceipt();
            new ReceiptsGUI(bakery);
        } else if (e.getActionCommand().equals("instr")) {
            showInstructions();
        } else if (e.getActionCommand().equals("exit")) {
            saveDataOption();
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    for (Event event: EventLog.getInstance()) {
                        System.out.println(event.getDescription());
                        System.out.println(event.getDate());
                        System.out.println("");
                    }
                    dispose();
                }
            });
        }
    }

    // EFFECTS: shows instructions image
    private void showInstructions() {
        JFrame imageFrame = new JFrame("How to use");
        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon image = new ImageIcon("src/instructions.png");
        JLabel imageLabel = new JLabel(image);
        // imageLabel.setIcon(image);

        imageFrame.getContentPane().add(imageLabel, BorderLayout.CENTER);
        imageFrame.setSize(600, 510);


        imageFrame.setLocationRelativeTo(null);
        imageFrame.setVisible(true);
        imageFrame.setResizable(true);
    }


    // EFFECTS: prompts user to load previous data
    private void loadDataOption() {
        int response = JOptionPane.showConfirmDialog(this, "Do you want to load previous data?", "Load Data",
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            loadData();
        }
    }

    // EFFECTS: loads data from JSON file
    private void loadData() {
        try {
            bakery.loadData(jsonReader);
            JOptionPane.showMessageDialog(this, "Data loaded successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: prompts user to save data
    private void saveDataOption() {
        int response = JOptionPane.showConfirmDialog(this, "Do you want to save the data before exiting?", "Save Data",
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            saveData();
        }
        dispose();
    }

    // EFFECTS: saves data to JSON file
    private void saveData() {
        try {
            bakery.saveData(jsonWriter);
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to save data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new BakeryGUI();
    }


}
