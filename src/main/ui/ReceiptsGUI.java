package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.*;

// represents the receipts GUI for the bakery application
public class ReceiptsGUI extends JFrame {
    private Bakery bakery;

    // EFFECTS: initializes the ReceiptGUI with the specified bakery
    public ReceiptsGUI(Bakery bakery) {
        this.bakery = bakery;
        setTitle("Receipts");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panel();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up the panel displaying the receipts
    public void panel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Receipts", SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();

        List<String> orders = bakery.getCart().getReceipt().getOrders();
        List<String> notes = bakery.getCart().getReceipt().getNotes();

        for (int i = 0; i < orders.size(); i++) {
            String receiptEntry = "Order: " + orders.get(i) + " | Notes: " + notes.get(i);
            listModel.addElement(receiptEntry);
        }

        JList<String> receiptList = new JList<>(listModel);
        panel.add(new JScrollPane(receiptList), BorderLayout.CENTER);

        add(panel);
    }
}
