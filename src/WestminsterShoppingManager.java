import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.*;
public class WestminsterShoppingManager implements ShoppingManager {
    private ArrayList<Product> productList;
	Scanner sc = new Scanner(System.in);

    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
		readFromFile();
    }

	public int NumValidate(String message){
		String red= "\u001B[31m";
		String defaultColor = "\u001B[0m";
		int input=0;
		while (true){

			try {
				System.out.println(message);
				input=sc.nextInt();
				return input;
			}catch (Exception e){
				System.out.println(red+"Invalid input"+defaultColor);
				sc.next();
			}

		}
	}
	public double NumValidate2(String message){
		String red= "\u001B[31m";
		String defaultColor = "\u001B[0m";
		double input=0;
		while (true){
			try {
				System.out.println(message);
				input=sc.nextDouble();
				return input;
			}catch (Exception e){
				System.out.println(red+"Invalid input"+defaultColor);
				sc.next();
			}
		}
	}

    @Override
    public void addProduct() {
		String red= "\u001B[31m";
		String green= "\u001B[32m";
		String defaultColor = "\u001B[0m";

		System.out.println(green+ "\n<<------------------------------------->>" +defaultColor);
    	System.out.println(green+ " Select Product Type " +defaultColor);
    	System.out.println(green+ "\t1 for Electronics " +defaultColor);
    	System.out.println(green+ "\t2 for Clothing " +defaultColor);
		System.out.println(green+ "<<------------------------------------->>\n" +defaultColor);

    	int type=NumValidate("Enter the product Number: ");

    	if (productList.size()<50) {
    		
    		if (type==1) {
    		
    			System.out.println("Enter Product Id : ");
    			String productId = sc.next();
    			System.out.println("Enter Product Name : ");
    			String productName = sc.next();
    			int availableItems = NumValidate("Enter Product Available Items : ");
				double productPrice = NumValidate2("Enter Product Price : ");
    			System.out.println("Enter Product Brand : ");
    			String productBrand = sc.next();
    			int warrantyPeriod = NumValidate("Enter Product Warranty Period: ");
    			Product electronicProduct = new Electronics(productId,productName,availableItems,productPrice,productBrand,warrantyPeriod);
    			productList.add(electronicProduct);
    			productList.get(0);
    		
    		}
    		else if(type==2) {
    		
    			System.out.println("Enter Product Id : ");
    			String productId = sc.next();
    			System.out.println("Enter Product Name : ");
    			String productName = sc.next();
				int availableItems = NumValidate("Enter Product Available Items : ");
    			double productPrice = NumValidate2("Enter Product Price : ");
    			System.out.println("Enter Product Size : ");
    			String productSize = sc.next();
    			System.out.println("Enter Product Color : ");
    			String productColor = sc.next();
    			Product clothingProduct = new Clothing(productId,productName,availableItems,productPrice,productSize,productColor);
    			productList.add(clothingProduct);
    		
    		}
    		else {
    			System.out.println(red+ "Invalid Product Type! Enter Again " +defaultColor);
    			addProduct();
    		}
    		
    		}
    	else {
    		System.out.println(red+ "Product List is Full " +defaultColor);
    	}
    }

    @Override
    public void deleteProduct() {
		String red= "\u001B[31m";
		String defaultColor = "\u001B[0m";

		System.out.println("Enter Product ID : ");
        String productId = sc.next();
		 Iterator<Product> iterator=productList.iterator();
		 boolean valid=false;

		 while (iterator.hasNext()){
			 Product p= iterator.next();

			 if(p.getProductID().equals(productId)){
				 System.out.println("Product Category: "+p.getCategory()+"\nAvailable Products : "+ productList.size());
				 iterator.remove();
				 System.out.println("Product removed successfully");
				 System.out.println("Available Products: "+productList.size());
				 valid=true;
				 break;
			 }
		 }
		 if(!valid){
			 System.out.println(red+ "Invalid Product ID" +defaultColor);
			 deleteProduct();
		 }
    }

    @Override
	public void printProducts() {

		Collections.sort(productList, Comparator.comparing(Product::getProductID));

		for (Product p : productList) {
			System.out.println("\nProduct Id : " + p.getProductID());
			System.out.println("Product Catogory : " + p.getCategory());
			System.out.println("Product Name : " + p.getProductName());
			System.out.println("Available Items : " + p.getAvailableItems());
			System.out.println("Product Price : " + p.getPrice());

			if(p.getCategory().equalsIgnoreCase("Electronics")) {
				System.out.println("Product Brand : " + ((Electronics)p).getBrand());
				System.out.println("Product Warranty Period : " + ((Electronics)p).getWarrantyPeriod());
				System.out.println();
			}
			else {
				System.out.println("Product Size : " + ((Clothing)p).getSize());
				System.out.println("Product Color : " + ((Clothing)p).getColor());
				System.out.println();
			}
		}
	}

    @Override
    public void saveToFile() {

		try (PrintWriter writer = new PrintWriter(new FileWriter("Product.txt",true))) {
            for (Product product : productList) {
                writer.print("Product ID: " + product.getProductID() + ", Catogory : " + product.getCategory()+", Name: " + product.getProductName()+", Availability Items: " + product.getAvailableItems()+", Price : " + product.getPrice());
                if(product.getCategory().equalsIgnoreCase("Electronics")) {
                	 writer.println(",Product Brand : " + ((Electronics)product).getBrand() + ",Product Warranty Period : " + ((Electronics)product).getWarrantyPeriod());

            	}
            	else {
            		writer.println(",Product Size : " + ((Clothing)product).getSize()+",Product Color : " + ((Clothing)product).getColor());
            	}
            }
            System.out.println("Products saved to file: " + "Product.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readFromFile() {
    	try (BufferedReader reader = new BufferedReader(new FileReader("Product.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
			System.out.println("Products loaded from file: " + "Product.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public  void runConsoleMenu() {
		String cyan = "\u001B[36m"; // for colors in text
		String red= "\u001B[31m";
		String green= "\u001B[32m";
		String defaultColor = "\u001B[0m";


		System.out.println(cyan+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t- - - - - - - - - - - - - - - - - - - - -"+defaultColor);
		System.out.println(cyan + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t| Welcome to Westminster Shopping Center |"+ defaultColor);
		System.out.println(cyan+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t- - - - - - - - - - - - - - - - - - - - -"+ defaultColor);




        
        boolean condition= true;
        while (condition){
            // Display console menu options and get user choice
            System.out.println(green+ "\n<<------------------------------------->>" +defaultColor);
            System.out.println(green+ "\t\t1. Add a new product" +defaultColor);
            System.out.println(green+ "\t\t2. Delete a product" +defaultColor);
            System.out.println(green+ "\t\t3. Print the list of products" +defaultColor);
            System.out.println(green+ "\t\t4. Save to file" +defaultColor);
            System.out.println(green+ "\t\t5. Read from file" +defaultColor);
			System.out.println(green+ "\t\t6. Open GUI" +defaultColor);
            System.out.println(green+ "\t\t0. Exit" +defaultColor);
            System.out.println(green+ "<<------------------------------------->>\n" +defaultColor);

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your choice: ");
            String option = sc.next();

            
            switch (option) {
                case "1":
                	addProduct();
                    
                    break;
                case "2":
                	deleteProduct();
                    
                    break;
                case "3":
                	printProducts();
                    
                    
                    break;
                case "4":
                	saveToFile();
                    
                    break;
                case "5":
                	readFromFile();
                    
                    break;
				case "6":
					GUI gui= new GUI(productList);

					break;
				case "0":
					condition =false;
					System.out.println("Thank you for using Westminster Online Shopping Center ");

					break;
                default:
                    System.out.println(red+"Invalid option"+defaultColor);
                    break;
            }

        }

    }
}
