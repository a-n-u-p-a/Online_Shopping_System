public class Electronics extends Product {


    //Initializing variables
    private String brand;
    private int warranty_Period;

    //Initializing constructors
    public Electronics(){
    }
    public Electronics(String brand, int warranty_Period){
        this.brand = brand;
        this.warranty_Period = warranty_Period;
    }

    public Electronics(String product_ID, String product_Name, int no_of_items, double price, String brand, int warranty_Period) {
        super(product_ID, product_Name, no_of_items, price);
        this.brand = brand;
        this.warranty_Period = warranty_Period;
    }

    //Initializing getters and setters
    public String getBrand(){
        return brand;
    }

    public int getWarranty_period(){
        return warranty_Period;
    }

    @Override
    public String getType() {
        return "Electronics";
    }

    public void setBrand(String brand){

    }

    public void setWarranty_Period(int warranty_Period){

    }


}
