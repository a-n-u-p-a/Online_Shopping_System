import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {

    @org.junit.jupiter.api.Test
    void displayMenu() {
    }


    @org.junit.jupiter.api.Test
    void testAddClothingProduct() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.stock = 20; // Set an initial stock value

        // Mock user input
        manager.input = new Scanner("456\nClothingProduct\n8\nLarge\nBlue\n34.99\n");

        // Call the method
        manager.addClothingProduct();

        // Assert the product list is updated
        assertEquals(1, manager.product_list.size());
        assertEquals("456", manager.product_list.get(0).getProduct_ID());
        assertEquals("ClothingProduct", manager.product_list.get(0).getProduct_name());
        assertEquals(8, manager.product_list.get(0).getNo_of_items());
        assertEquals(34.99, manager.product_list.get(0).getPrice());
        assertTrue(manager.product_list.get(0) instanceof Clothing);

        // Assert the stock is updated
        assertEquals(28, manager.stock);
    }

}