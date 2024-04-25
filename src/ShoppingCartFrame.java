import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ShoppingCartFrame extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private double totalPrice;
    String finalTotalLabel;
    String threeDiscountLabel;
    int electricCategoryCount= 0;
    int clothingCategoryCount = 0;
    double discount1 = 0.0;
    double discount2 = 0.0;

    public ShoppingCartFrame() {
        // create panel for add the details of selected items
        setTitle("Shopping Cart");
        JPanel p1 = new JPanel();
        String[] columnName = {"Product", "Quantity", "Price"};
        model = new DefaultTableModel(columnName, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };

        totalPrice = 0.0;
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        center.setVerticalAlignment(JLabel.CENTER);
        scrollPane.setPreferredSize(new Dimension(700, 200));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        table.setGridColor(Color.BLACK);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }
        p1.add(scrollPane);
        // create a panel for show the total cost and other discount amount
        JPanel p2 = new JPanel();
        JLabel total = new JLabel("Total :"+ totalPrice);
        JLabel firstDiscount = new JLabel("First Purchase Discount (10%) ");

        JLabel threeDiscount = new JLabel("Three Items in Same Category Discount (20%) "+ threeDiscountLabel);
        JLabel finalTotal = new JLabel("Final Total " + finalTotalLabel);
        p2.setLayout(new GridLayout(4, 0));

        // add components to the panel 2
        p2.add(total);
        p2.add(firstDiscount);
        p2.add(threeDiscount);
        p2.add(finalTotal);

        // set the layout of panel1 and panel2
        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.SOUTH);

    }

    public void addToCart(String productName, int quantity, String price, String category) {

        String[] cartRow = {productName, String.valueOf(quantity), price, category};
        model.addRow(cartRow);

        double itemPrice = Double.parseDouble(price);
        totalPrice += itemPrice;

        JLabel totalLabel = (JLabel) ((JPanel) getContentPane().getComponent(1)).getComponent(0);
        totalLabel.setText("Total: $" + String.format("%.2f", totalPrice));


//        int categoryCount =  Integer.parseInt(category);
        if (category.equals("Electronics")) {
            electricCategoryCount++;
        } else if (category.equals("Clothing")){
            clothingCategoryCount++;
        }



        // Update the total label in the GUI with the new total price
        // Update the first purchase discount label in the GUI with the new total price
        // Update the three items in same category discount label in the GUI with the new total price
        JLabel threeDiscountLabel = (JLabel) ((JPanel) getContentPane().getComponent(1)).getComponent(2);
        if (electricCategoryCount >= 3 || clothingCategoryCount >= 3) {
            //20% discount
            //discountTwenPer = totalPrice * 0.2;
            threeDiscountLabel.setText("Three Items in Same Category Discount (20%): -$" + String.format("%.2f", discount2));
        } else {
            threeDiscountLabel.setText("Three Items in Same Category Discount (20%): -$0.00");
        }

        //10% discount
        //discountTenPer = totalPrice * 0.1;
        // Update the final total label in the GUI with the new total price
        JLabel finalTotalLabel = (JLabel) ((JPanel) getContentPane().getComponent(1)).getComponent(3);
        double finalTotalPrice = totalPrice - (discount1) - (discount1);
        finalTotalLabel.setText("Final Total: $" + String.format("%.2f", finalTotalPrice));

    }

}
