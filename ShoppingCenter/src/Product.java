public abstract class Product {

    //Initializing variables
    private String product_ID;
    private String product_Name;
    private int no_of_items;
    private double price;
    private int cart_no_of_items;


    //Initializing constructors
     public Product() {
     }

     public Product(String product_ID, String product_Name, int no_of_items, double price) {
         this.product_ID = product_ID;
         this.product_Name = product_Name;
         this.no_of_items = no_of_items;
         this.price = price;
     }

    //Initializing getters and setters
     public abstract String getType();

     public String getProduct_ID() {
         return product_ID;
     }

     public String getProduct_name() {
         return product_Name;
     }

     public int getNo_of_items() {
         return no_of_items;
     }

     public double getPrice() {
         return price;
     }

     public int getCart_no_of_items(){
         return cart_no_of_items;
     }

     public void setProduct_ID(String product_ID){

     }

     public void setProduct_name(String product_Name){

     }

    public void setNo_of_items(int no_of_items){
        this.no_of_items += no_of_items;
    }

    public void setCart_no_of_items(int cart_no_of_items){
        this.cart_no_of_items += cart_no_of_items;
    }

 }
