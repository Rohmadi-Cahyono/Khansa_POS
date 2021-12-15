package khansapos;

public class PurchaseItemObject {
    //pendeklarasian atribut
    private final String code;
    private final String name;
    private final Integer bprice;
    private final Integer discount;    
    private final Integer qty;
    private final String unit;
    private final Integer subtotal;
    private final Integer sprice;
    
    //konstruktor berparameter untuk set atribut
    public PurchaseItemObject(String code, String name, Integer bprice, 
            Integer discount, Integer qty, String unit, Integer subtotal, Integer sprice) {
        this.code=code;
        this.name=name;
        this.bprice=bprice;
        this.discount=discount;
        this.sprice=sprice;
        this.qty=qty;
        this.unit=unit;
        this.subtotal=subtotal;  
    }   
    
    //method get
    public String Code(){
        return code;
    }
    
    public String Name(){
        return name;
    }    

    public Integer Bprice(){
        return bprice;
    }
    
    public Integer Discount(){
        return discount;
    }
    
    public Integer Qty(){
        return qty;
    }
    
    public String Unit(){
        return unit;
    }
    
    public Integer Subtotal(){
        return subtotal;
    }    
    
    public Integer Sprice(){
        return sprice;
    }
}
