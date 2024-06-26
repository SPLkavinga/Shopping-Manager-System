public class Clothing extends Product {
    private String size;
    private String color;

    public Clothing(String productID, String productName, int availableItems, double price, String size, String color) {
        super(productID, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    @Override
    public String getCategory() {
        return "Clothing";
    }

    @Override
    public String toString() {
        return "Clothing\n" +
                super.toString() +
                "\nSize: " + size +
                "\nColour: " + color + "\n";
    }


}
