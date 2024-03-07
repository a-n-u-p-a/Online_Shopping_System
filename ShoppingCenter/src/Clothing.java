public class Clothing extends Product {

    //initializing variables
    private String size;
    private String colour;

    //constructors
    public Clothing(){
    }

    public Clothing(String size,String colour) {
        this.size = size;
        this.colour = colour;
    }

    public Clothing(String product_ID, String product_Name, int no_of_items, double price, String size, String colour) {
        super(product_ID, product_Name, no_of_items, price);
        this.size = size;
        this.colour = colour;
    }

    //Initializing getters and setters
    public String getSize() {
        return size;
    }

    public String getColour() {
        return colour;
    }

    @Override
    public String getType() {
        return "Clothing";
    }
    public void setSize() {
    }

    public void setColour() {
    }
}
