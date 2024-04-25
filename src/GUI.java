import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame {
    private ShoppingCartFrame shoppingCartFrame = new ShoppingCartFrame();
    JTable table;

    public GUI(ArrayList<Product> products) {
        // Create a frame for the GUI
        JFrame frame = new JFrame("Westminster Shopping Centre");
        frame.setLayout(null);
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        panel1.setBackground(Color.lightGray);


        // Create label for select the category
        JLabel label = new JLabel("Select Product Category ");
        //create button for filtering and shopping cart actions
        JComboBox<String> dropdown = new JComboBox<>(new String[]{"ALL", "Electronics", "Clothing"});
        dropdown.setBackground(Color.CYAN);


        // Create label for select the shopping cart
        JButton shoppingCartButton = new JButton("SHOPPING CART");
        shoppingCartButton.setBackground(Color.cyan);
        // Action listener for shopping cart button
        shoppingCartButton.addActionListener(e -> {
            shoppingCartFrame.setVisible(true);
            shoppingCartFrame.pack();
        });
        // Adding components to panel1
        panel1.add(label);
        panel1.add(dropdown);
        panel1.add(shoppingCartButton);




        // Define column names for the table
        String[] columnNames = {"Product ID", "Name", "Category", "Price($)", "Info"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return !(column >= 0 && column <= 4);
            }
        };

        // Populate the table model with product data
        for (Product product : products) {
            String[] rowData;
            if (product instanceof Electronics) {
                rowData = new String[]{product.getProductID(), product.getProductName(), "Electronics",
                        String.valueOf(product.getPrice()), ((Electronics) product).getBrand() + ", " +
                        ((Electronics) product).getWarrantyPeriod() + " weeks warranty"};
            } else if (product instanceof Clothing) {
                rowData = new String[]{product.getProductID(), product.getProductName(), "Clothing",
                        String.valueOf(product.getPrice()), ((Clothing) product).getSize() + ", " +
                        ((Clothing) product).getColor()};
            } else {
                rowData = new String[]{};
            }
            model.addRow(rowData);
        }

        // Create JTable to display product information
        JTable table = new JTable(model);

        // Column width adjustment based on content
        for (int column = 0; column < table.getColumnCount(); column++) {
            // Adjust column width based on header and cell content
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int maxWidth = 0;
            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(table, tableColumn.getHeaderValue(),
                    false, false, -1, column);
            maxWidth = Math.max(maxWidth, headerComp.getPreferredSize().width);

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component cellComp = table.prepareRenderer(cellRenderer, row, column);
                maxWidth = Math.max(maxWidth, cellComp.getPreferredSize().width);
            }
            tableColumn.setPreferredWidth(maxWidth + 10); // Add padding
        }

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Configure table appearance and add to scroll pane
        table.setPreferredScrollableViewportSize(new Dimension(450, 80));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setGridColor(Color.BLACK);
        panel1.add(scrollPane, BorderLayout.CENTER);



        // Action listener for filtering products based on category
        dropdown.addActionListener(e -> {
            String selectedCategory = dropdown.getSelectedItem().toString();


            TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
            if (!selectedCategory.equals("ALL")) {
                sorter.setRowFilter(RowFilter.regexFilter(selectedCategory, 2));
            } else {
                sorter.setRowFilter(null);
            }
            table.setRowSorter(sorter);
        });



        // Create panel2 for displaying selected product details and add-to-cart functionality
        JLabel labelPanel2 = new JLabel("Selected Product Details");
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setLayout(new BorderLayout());
        panel2.add(labelPanel2, BorderLayout.NORTH);

        JTextArea productDetailsTextArea = new JTextArea(7, 40);
        productDetailsTextArea.setEditable(false);
        panel2.add(new JScrollPane(productDetailsTextArea), BorderLayout.CENTER);


        // Listener for updating product details in panel2 based on table selection
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!selectionModel.isSelectionEmpty()) {
                int row = selectionModel.getMinSelectionIndex();
                String details = "";
                for (int i = 0; i < columnNames.length; i++) {
                    details += columnNames[i] + ": " + model.getValueAt(row, i) + "\n";
                }
                productDetailsTextArea.setText(details);
            } else {
                productDetailsTextArea.setText("");
            }
        });



        // create Button to add selected product to shopping cart
        JButton button2 = new JButton("ADD TO SHOPPING CART");
        button2.setBackground(Color.CYAN);
        button2.addActionListener(e -> {
            String selectedProductDetails = productDetailsTextArea.getText();
            if (!selectedProductDetails.isEmpty()) {
                String[] details = selectedProductDetails.split("\n");
                String productName = details[1].split(": ")[1];
                int quantity = 1;
                String price = details[3].split(": ")[1];

                shoppingCartFrame.addToCart(productName, quantity, price, details[2].split(": ")[1]);
                System.out.println("Added to Shopping Cart:\n" + selectedProductDetails);
            } else {
                JOptionPane.showMessageDialog(GUI.this, "Please select a product to " +
                        "add to the " + "" + "shopping cart.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Adjust layout of panel2
        panel2.setLayout(new BorderLayout());
        // add component for the panel2
        panel2.add(labelPanel2, BorderLayout.NORTH);
        panel2.add(new JScrollPane(productDetailsTextArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(button2);
        panel2.add(bottomPanel, BorderLayout.SOUTH);

        // Add panel1 and panel2 to the frame
        frame.setLayout(new BorderLayout());
        frame.add(panel1, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.SOUTH);

        // Set frame properties and make it visible
        panel1.setPreferredSize(new Dimension(100, 10));
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

