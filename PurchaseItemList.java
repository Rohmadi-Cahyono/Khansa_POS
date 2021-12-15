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
    public void tambahItem(String code, String name,  Integer bprice, 
            Integer discount, Integer qty, String unit, Integer subtotal, Integer sprice){
        
        PurchaseItemObject PIO = new PurchaseItemObject(code,name,bprice,discount,qty,unit,subtotal,sprice);
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
     
    public Integer  hitungTotal(){
        int sum = 0;
        for(int i=0; i<this.PurchaseList.size(); i++){
            sum += this.PurchaseList.get(i).Subtotal();
       }       
        return sum;
    }
    
    public Integer jumlahItem(){
        return this.PurchaseList.size();
    }
        
    //method getData untuk mengambil seluruh data yang ada pada PurchaseList atau ArrayList
    public ArrayList<PurchaseItemObject> getItems(){
        return PurchaseList;
    }
        
    public ArrayList<PurchaseItemObject> getItem(String Code){
        //boolean ditemukan = false;
        for(int i=0; i<this.PurchaseList.size(); i++){
            if (this.PurchaseList.get(i).Code() == null ? Code == null : this.PurchaseList.get(i).Code().equals(Code)){
               // ditemukan = true;
               return PurchaseList;
            }
        }
       return null;
        
    }
    public void TampilItem(String Code){
       
        for(int i=0; i<this.PurchaseList.size(); i++){
            if (this.PurchaseList.get(i).Code() == null ? Code == null : this.PurchaseList.get(i).Code().equals(Code)){
                PurchaseItemObject PIO = new PurchaseItemObject( this.PurchaseList.get(i).Code(), this.PurchaseList.get(i).Name(),
                         this.PurchaseList.get(i).Bprice(), this.PurchaseList.get(i).Discount(), this.PurchaseList.get(i).Qty(),
                        this.PurchaseList.get(i).Unit(),this.PurchaseList.get(i).Subtotal(),this.PurchaseList.get(i).Sprice());                
            }
        }
    }
     
}
