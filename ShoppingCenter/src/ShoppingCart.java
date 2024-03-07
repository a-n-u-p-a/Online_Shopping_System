import java.util.ArrayList;
import java.util.List;


public class ShoppingCart {
    //Initializing variables
    public static List<Product> products;
    private int no_of_items;

    //Initializing constructors
    public ShoppingCart() {
        this.products = new ArrayList<>();
        this.no_of_items = no_of_items;
    }

    public void addProduct(Product product) {
        products.add(product);
        System.out.println("product added");
    }
}
