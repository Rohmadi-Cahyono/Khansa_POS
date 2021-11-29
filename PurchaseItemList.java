package khansapos;

import java.util.ArrayList;

public class PurchaseItemList {
       //pendeklarasian PurchaseList sebagai ArrayList
    ArrayList<PurchaseItemObject> PurchaseList;
    
    //Konstruktor
    @SuppressWarnings("unchecked")
    public PurchaseItemList(){
        //Inisialisasi dari PurchaseList
        PurchaseList = new ArrayList();
    }
    
    //method isi data
    public void tambahItem(String code, String name, String category, Integer bprice, 
            Integer discount, Integer sprice, Integer qty, String unit, Integer subtotal){
        
        PurchaseItemObject PIO = new PurchaseItemObject(code,name,category,bprice,discount,sprice,qty,unit,subtotal);
        PurchaseList.add(PIO);
    }
    
    void hapusItem(String Code){
        this.PurchaseList.removeIf(item -> (item.Code() == null ? Code == null : item.Code().equals(Code)));
    }
    
    public boolean cariItem(String Code){
        boolean ditemukan = false;
        for(int i=0; i<this.PurchaseList.size(); i++){
            if (this.PurchaseList.get(i).Code() == null ? Code == null : this.PurchaseList.get(i).Code().equals(Code)){
                ditemukan = true;
            }
        }
       return ditemukan;  
    }
     
        
    //method getData untuk mengambil seluruh data yang ada pada PurchaseList atau ArrayList
    public ArrayList<PurchaseItemObject> getItem(){
        return PurchaseList;
    }
    
     
}
