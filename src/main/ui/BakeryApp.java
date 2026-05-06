package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// This class references code from the TellerApp for the methods, run, processcommand, and displayMenu
// https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
// Bakery application
public class BakeryApp {

    private static final String JSON_STORE = "./data/bakery.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Scanner input;
    private Cart cart;
    // private Receipt receipt;
    private List<Worker> workers;

    // EFFECTS: runs the backery application
    public BakeryApp() throws FileNotFoundException {
        run();
    }

    
    // MODIFIES: this
    // EFFECTS: processes user input
    public void run() {
        boolean keepGoing = true;
        String command = null;

        init();

        System.out.println("Do you want to load previous data? (yes/no): ");
        String load = input.next().toLowerCase();
        if (load.equals("yes")) {
            loadData();
        }

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                handleQuit();
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("w")) {
            login();
        } else if (command.equals("s")) {
            shopping();
        } else if (command.equals("r")) {
            receipts();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        input = new Scanner(System.in);
        cart = new Cart();
        // receipt = new Receipt();
        workers = new ArrayList<Worker>();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tw -> login as a worker");
        System.out.println("\ts -> shopping");
        System.out.println("\tr -> receipts");
        System.out.println("\tq -> quit");
    }


    // MODIFIES: this
    // EFFECTS: loads receipt, workers, and available items from file
    private void loadData() {
        try {
            Receipt receipt = jsonReader.readReceipt();
            cart.setReceipt(receipt);
            workers = jsonReader.readWorkers();
            List<Item> items = jsonReader.readItems();
            for (Item item : items) {
                Item.addItemToList(item);
            }
            System.out.println("Data loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: handles the quit option, offering to save data
    private void handleQuit() {
        System.out.println("Would you like to save your data before quitting? (yes/no): ");
        String save = input.next().toLowerCase();
        if (save.equals("yes")) {
            saveData();
        }
    }

    // EFFECTS: saves the data to file
    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(cart.getReceipt(), workers, Item.getAvailableItems());
            jsonWriter.close();
            System.out.println("Data saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }


    // EFFECTS: handles worker login
    private void login() {
        System.out.println("Enter your PIN. If you are a new worker, enter 'new': ");
        String response = input.next();

        if (response.equalsIgnoreCase("new")) {
            createNewWorker();
        } else {
            handleExistingWorker(response);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new worker
    private void createNewWorker() {
        System.out.println("Enter your name: ");
        String name = input.next();

        

        while (true) {
            System.out.println("Enter your PIN");
            String pinInput = input.next().trim(); 
    
            if (pinInput.isEmpty()) {
                System.out.println("PIN cannot be empty. Please enter a valid PIN:");
                continue;
            }
    
            try {
                int pin = Integer.parseInt(pinInput);
                Worker w = new Worker(name, pin);
                workers.add(w);
                System.out.println("Worker added.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer PIN:");
            }
        }
        
        // while (!input.hasNextInt()) {
        //     System.out.println("Invalid input. Please enter a valid integer PIN:");
        //     input.next();
        // }
    
        // int pin = input.nextInt();
        // Worker w = new Worker(name, pin);
        // workers.add(w);
        // System.out.println("Worker added.");
    }

    // EEFECTS: handle existing worker login
    private void handleExistingWorker(String response) {
        int pin = Integer.parseInt(response);
        Worker worker = null;

        for (Worker w : workers) {
            if (w.getPin() == pin) { 
                worker = w;
                System.out.println("Welcome!");
                break;
            }
        }

        if (worker == null) {
            System.out.println("The PIN is incorrect.");
            login();
        } else {
            workerMenu(worker);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays worker menu
    private void workerMenu(Worker worker) {
        boolean workerMenu = true;
        String command = null;

        while (workerMenu) {
            displayWorkerMenu();

            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                workerMenu = false;
            } else {
                workerMenuCommand(worker, command);
            }
        }
    }


    // EFFECTS: displays menu of options for workers
    private void displayWorkerMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta-> add an item");
        System.out.println("\tr -> remove an item");
        System.out.println("\tq -> quit worker menu");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void workerMenuCommand(Worker worker, String command) {
        if (command.equals("a")) {
            System.out.println("Enter item name to add: ");
            String item = input.next();
            Worker.addItem(item);
        } else if (command.equals("r")) {
            System.out.println("Enter item name to remove: ");
            String item = input.next();
            Worker.removeItem(item);
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: handles shopping process
    private void shopping() {
        List<Item> items = Item.getAvailableItems();
        System.out.println("Available items: ");
        
        for (Item i : items) {
            System.out.println("- " + i.getName());
        }

        // get what items the user want
        boolean shopping = true;
        while (shopping) {
            System.out.println("\nEnter item name to add to cart (or 'done' to finish)");
            String itemName = input.next();
            if (itemName.equalsIgnoreCase("done")) {
                shopping = false;
            } else {
                for (Item item : items) {
                    if (item.getName().equalsIgnoreCase(itemName)) {
                        cart.addItemToCart(item);
                        System.out.println("Item added to cart.");
                    }
                }
            }
        }

        placeOrder();

    }


    // MODIFIES: this
    // EFFECTS: place the order
    private void placeOrder() {
        System.out.println("Enter any notes for the order: ");
        input.nextLine();
        String notes = input.nextLine();
        cart.addNotes(notes);

        System.out.println("Place order? (yes/no): ");
        String placeOrder = input.next().toLowerCase();
        if (placeOrder.equals("yes")) {
            cart.placeOrder();
            System.out.println("Order placed.");
            
        }
    }



    // EFFECTS: displays receipts
    private void receipts() {
        // List<String> orders = receipt.getOrders();
        // List<String> notes = receipt.getNotes();
        List<String> orders = cart.getReceipt().getOrders();
        List<String> notes = cart.getReceipt().getNotes();
        
        if (orders.isEmpty()) {
            System.out.println("No orders placed yet.");
        } else {
            for (int i = 0; i < orders.size(); i++) {
                System.out.println("Order " + (i + 1) + ": " + orders.get(i));
                System.out.println("Notes: " + notes.get(i));
            }
        }
    }



}
