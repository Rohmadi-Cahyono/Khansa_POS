package khansapos;

public class PurchaseItemObject {
    //pendeklarasian atribut
    private final String code;
    private final String name;
    private final String category;
    private final Integer bprice;
    private final Integer discount;
    private final Integer sprice;
    private final Integer qty;
    private final String unit;
    private final Integer subtotal;
    
    //konstruktor berparameter untuk set atribut
    public PurchaseItemObject(String code, String name, String category, Integer bprice, 
            Integer discount, Integer sprice, Integer qty, String unit, Integer subtotal) {
        this.code=code;
        this.name=name;
        this.category=category;
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
    
    public String Category(){
        return category;
    }
    
    public Integer Bprice(){
        return bprice;
    }
    
    public Integer Discount(){
        return discount;
    }
    
    public Integer Sprice(){
        return sprice;
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
}
