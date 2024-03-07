import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.SwingUtilities;


public class WestminsterShoppingManager implements ShoppingManager {

    //Initializing variables
    public static ArrayList <Product> product_list = new ArrayList<>();
    Scanner input = new Scanner(System.in);
    public static int stock = 0;

    //Method to display menu
    public void displayMenu() {

        boolean exit = false;
        String option;
        Scanner menu_Input = new Scanner(System.in);
        loadProductsFromFile();
        try {
            do {
                System.out.println("""
                        __________________________________________________________________________
                        <                                                                        >
                        <              ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~                   >
                        <            >>Welcome to Westminster Shopping Manager<<                 >
                        <              ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~                   >
                        <                                                                        >
                        <      Please select the type of product that you want to enter,         >
                        <                                                                        >
                        <              'OP1' or 1 : Add New Products                             >
                        <              'OP2' or 2 : Delete A Product                             >
                        <              'OP3' or 3 : List The Products                            >
                        <              'OP4' or 4 : Save Product Information To A File           >
                        <              'OP5' or 5 : View Shopping Center Interface               >
                        <              'OP6' or 6 : Exit Shopping Manager                        >
                        <                                                                        >
                        <                                                                        >
                        --------------------------------------------------------------------------
                        """);
                System.out.print("         Enter your Option: ----------->   ");
                option = menu_Input.nextLine();

                //Relevant method is initiated according to he selected option
                switch (option) {
                    case "OP1", "1" -> addProduct();
                    case "OP2", "2" -> deleteProduct();
                    case "OP3", "3" -> listProducts();
                    case "OP4", "4" -> saveProducts();
                    case "OP5", "5" -> SwingUtilities.invokeLater(() -> new WestminsterShoppingManagerGUI().setVisible(true));
                    case "OP6", "6" -> {
                        exit = true;
                        closeApplication();
                    }
                    default -> System.out.println("Wrong menu input!!");

                }

            } while (!exit);
        }catch(InputMismatchException e) {
            System.out.println("Error occurred");
        }
    }

    //Application closing method
    private void closeApplication() {
        System.out.println("Exiting Westminster Shopping Manager. Goodbye!");
        System.exit(0);
    }

    @Override
    public void addProduct(){
        String product_type;

        if(stock<50) {
            System.out.println("""
                    Please select the type of product that you want to enter:
                                                   
                               1. electronics
                               2. clothes
                           
                    """);

            System.out.print("Enter your Product Type: ");
            product_type = input.nextLine();

            //According to the entered answer, respective methods are called
            if ("OP1".equals(product_type) || "1".equals(product_type)) {
                addElectronicProduct();
            } else if ("OP2".equals(product_type) || "2".equals(product_type)) {
                addClothingProduct();
            } else {
                System.out.println("Wrong product type!!");
            }


        }else{
            System.out.println("The Stocks are full!");
        }
    }

    void addElectronicProduct(){

        try {
            String temp_ID;
            int number_of_items;
            String temp_Name;
            String temp_Brand;
            int temp_Warranty;
            double temp_Price;


            System.out.print("Enter the product ID: ");
            temp_ID = input.nextLine();

            System.out.print("Enter the product name: ");
            temp_Name = input.nextLine();


            System.out.print("Enter the number of products you want add for the above type: ");
            number_of_items = input.nextInt();

            //Checks if the number entered of less thant the number of products that can be entered
            if(number_of_items > (50-stock)){
                System.out.println("Number of products entered might exceed the 50 product limit, Please enter a number =< " + (50-stock));
                return;
            }
            stock += number_of_items;

            // Consume the newline left
            input.nextLine();

            System.out.print("Enter the product brand: ");
            temp_Brand = input.nextLine();

            System.out.print("Enter the warranty period(months): ");
            temp_Warranty = input.nextInt();

            System.out.print("Enter the product price: ");
            temp_Price = input.nextDouble();

            input.nextLine();

            Electronics newElectronicProduct = new Electronics(temp_ID, temp_Name, number_of_items, temp_Price, temp_Brand, temp_Warranty);
            product_list.add(newElectronicProduct);
        }catch (Exception e){
            System.out.println("\nPlease enter numbers in the respective inputs.\n");
        }
    }

    void addClothingProduct(){
        try {
            String temp_ID;
            String temp_Name;
            int number_of_items;
            double temp_Price;
            String temp_Size;
            String temp_Colour;

            System.out.print("Enter the product ID: ");
            temp_ID = input.nextLine();

            System.out.print("Enter the product name: ");
            temp_Name = input.nextLine();

            System.out.print("Enter the number of products you want to for above type: ");
            number_of_items = input.nextInt();

            //Checks if the number entered of less thant the number of products that can be entered
            if(number_of_items > (50-stock)) {
                System.out.println("Number of products entered might exceed the 50 product limit, Please enter a number =< " + (50 - stock));
                return;
            }
            stock += number_of_items;

            input.nextLine();

            System.out.print("Enter the size of the cloth: ");
            temp_Size = input.nextLine();

            System.out.print("Enter the color of the cloth: ");
            temp_Colour = input.nextLine();

            System.out.print("Enter the product price: ");
            temp_Price = input.nextDouble();

            input.nextLine();

            Clothing newClothingProduct = new Clothing(temp_ID, temp_Name, number_of_items, temp_Price, temp_Size, temp_Colour);
            product_list.add(newClothingProduct);
        }catch (Exception e){
            System.out.println("\nPlease enter numbers in the respective inputs.\n");
        }
    }

    @Override
    public void deleteProduct(){
        String product_ID;
        int no_of_products;

        try {
            if (product_list.size() == 0){
                System.out.println("There are no items to delete, inventory is empty.");
            }else {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter the ID of the product you want to remove: ");
                product_ID = input.nextLine();
                System.out.print("Enter the number of products you want to remove: ");
                no_of_products = input.nextInt();
                stock -= no_of_products;

                //Different conditions are evaluated for products in product_list, checks with the id
                for (Product product : product_list) {
                    if (product.getProduct_ID().equals(product_ID) && (product.getNo_of_items() > no_of_products)) {
                        product.setNo_of_items(-(no_of_products));
                        System.out.println("Successfully removed " + no_of_products + " products from " + product.getProduct_name());
                        return;
                    } else if (product.getProduct_ID().equals(product_ID) && (product.getNo_of_items() == no_of_products)) {
                        product_list.remove(product);
                        System.out.println("Successfully removed " + no_of_products + " from " + product.getProduct_name());
                        System.out.println("\nSince the removed no of items = " + no_of_products + " , whole " + product.getProduct_name() + "has been removed.");
                    } else if (product.getProduct_ID().equals(product_ID) && (product.getNo_of_items() < no_of_products)) {
                        System.out.println("The number of products cannot exceed the available no if items: " + product.getNo_of_items());
                        return;
                    }else{
                        System.out.println("Product with ID " + product_ID + " was not found.");
                    }
                }
            }
        }catch(Exception e){
            System.out.println("Number of items should be a number.");
        }
    }


    @Override
    public void saveProducts() {
        try (FileWriter writer = new FileWriter("products.txt")) {
            if(product_list.size() != 0) {
                for (Product product : product_list) {
                    // Write product information to the file
                    writer.write("Product ID: " + product.getProduct_ID() + "\n");
                    writer.write("Product Name: " + product.getProduct_name() + "\n");
                    writer.write("Available Items: " + product.getNo_of_items() + "\n");
                    writer.write("Price: " + product.getPrice() + "\n");

                    if (product instanceof Electronics electronicsProduct) {
                        writer.write("Type: Electronics\n");
                        writer.write("Brand: " + electronicsProduct.getBrand() + "\n");
                        writer.write("Warranty Period: " + electronicsProduct.getWarranty_period() + " months\n");
                    } else if (product instanceof Clothing clothingProduct) {
                        writer.write("Type: Clothing\n");
                        writer.write("Size: " + clothingProduct.getSize() + "\n");
                        writer.write("Color: " + clothingProduct.getColour() + "\n");
                    }

                    writer.write("--------------------------------------\n");
                }
                System.out.println("\nProducts saved to file successfully.\n");
            }else{
                System.out.println("\nNo product to save, inventory is empty!!\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listProducts() {
        System.out.println("""
                            
                            List of the Products available in Westminster Shopping Manager
                            ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                                                                    
                    """);
        if (product_list.isEmpty()) {
            System.out.println("Currently, there are no products");
        } else {
            product_list.sort((Product product1, Product product2) -> {
                // Compare based on type
                boolean isElectronic1 = product1 instanceof Electronics;
                boolean isElectronic2 = product2 instanceof Electronics;

                if (isElectronic1 && !isElectronic2) {
                    return -1; // Electronics comes before Clothing
                } else if (!isElectronic1 && isElectronic2) {
                    return 1; // Clothing comes after Electronics
                } else {
                    // If both are of the same type or neither is Electronics, use string comparison for product ID
                    return product1.getProduct_ID().compareTo(product2.getProduct_ID());
                }
            });

            //Listing electronic products
            System.out.println("""      
                                            _____________________________
                                            Available Electronic Products
                                            ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                                            
                                                  """);
            for (Product product : product_list) {
                if (product instanceof Electronics) {
                    listProductDetails(product);
                }
            }
            //Listing clothing products
            System.out.println("""      
                                            ___________________________
                                            Available Clothing Products
                                            ^^^^^^^^^^^^^^^^^^^^^^^^^^^
                                                  """);
            for (Product product : product_list) {
                if (product instanceof Clothing) {
                    listProductDetails(product);
                }
            }
            System.out.println("\n");
        }
    }

    private void listProductDetails(Product product) {

        //Checks if the selected product is an electronic product
        if (product instanceof Electronics electronicsProduct) {
            System.out.print("{ ID: " + electronicsProduct.getProduct_ID());
            System.out.print(" , Name: " + electronicsProduct.getProduct_name());
            System.out.print(" , Available number of products: " + electronicsProduct.getNo_of_items());
            System.out.print(" , Brand: " + electronicsProduct.getBrand());
            System.out.print(" , Warranty Period: " + electronicsProduct.getWarranty_period() + " months }\n");
            System.out.print("-----------------------------------------------------------------------------------\n");
            //Checks if the selected product is an clothing product
        } else if (product instanceof Clothing clothingProduct) {
            System.out.print("{ ID: " + clothingProduct.getProduct_ID());
            System.out.print(" , Name: " + clothingProduct.getProduct_name());
            System.out.print(" , Available number of products: " + clothingProduct.getNo_of_items());
            System.out.print(" , Size: " + clothingProduct.getSize());
            System.out.print(" , Color: " + clothingProduct.getColour()+" \n");
            System.out.print("-----------------------------------------------------------------------------------\n");
        }
    }

    //Loads the data from the saved file
    private void loadProductsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Product ID: ")) {
                    String productId = line.substring("Product ID: ".length()).trim();
                    String productName = reader.readLine().substring("Product Name: ".length()).trim();
                    int availableItems = Integer.parseInt(reader.readLine().substring("Available Items: ".length()).trim());
                    double price = Double.parseDouble(reader.readLine().substring("Price: ".length()).trim());

                    // Determine the type of product
                    Product product;
                    line = reader.readLine(); // Read the line indicating the type
                    if (line.equals("Type: Electronics")) {
                        String brand = reader.readLine().substring("Brand: ".length()).trim();
                        String warrantyPeriodString = reader.readLine().substring("Warranty Period: ".length()).trim();
                        int warrantyPeriod = Integer.parseInt(warrantyPeriodString.split(" ")[0]);
                        product = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
                    } else if (line.equals("Type: Clothing")) {
                        String size = reader.readLine().substring("Size: ".length()).trim();
                        String color = reader.readLine().substring("Color: ".length()).trim();
                        product = new Clothing(productId, productName, availableItems, price, size, color);
                    } else {
                        // Handle unrecognized product type
                        System.out.println("Error: Unrecognized product type.");
                        return;
                    }

                    // Add the product to the loaded products list
                    product_list.add(product);

                    // Skip the line separator
                    reader.readLine();
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
