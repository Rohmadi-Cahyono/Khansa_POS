package khansapos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import net.proteanit.sql.DbUtils;

public class Item extends javax.swing.JInternalFrame {
    java.sql.Connection con =  new UDbConnection().koneksi();
    private static String  Id;
    private String Pilihan,HBeli,HJual,Disc,Dipilih, Pemanggil;
    DecimalFormat desimalFormat;    
    UText ut = new UText();
    UTable uts = new UTable();
    
    public Item() {
        initComponents();
        IframeBorderLess(); 
        SPtableTampil.getViewport().setBackground(new Color(255,255,255));
        TampilBarang(); 
        Tengah();
        //------------------------------------------Add------------------------------------------
        SListAdd.setVisible(false);
        SListAdd.getViewport().setBackground(new Color(255,255,255));
        
        //Option Untuk Table yang bisa dipilih
        SPilihAdd.setVisible(false);
        SPilihAdd.getViewport().setBackground(new Color(255,255,255)); 
        
        //Merubah Huruf Besar
        DocumentFilter filter = new UText();
        AbstractDocument isiText = (AbstractDocument) txtKodeAdd.getDocument();
        isiText.setDocumentFilter(filter);
        
        //----------------------------------------Edit--------------------------------------------------
        StabelListEdit.setVisible(false);
        StabelListEdit.getViewport().setBackground(new Color(255,255,255));
        
        //Option Untuk Table yang bisa dipilih
        StabelPilihEdit.setVisible(false);
        StabelPilihEdit.getViewport().setBackground(new Color(255,255,255)); 
        txtKodeEdit.setBackground(UIManager.getColor(new Color(255,255,255)));         
        txtKodeEdit.setEditable(false);
    }
    

   
 
   public void Focus(){
       txtSearch.requestFocus();
   }
   
    private void IframeBorderLess() {
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null); 
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
   
    private void Tengah(){
        Dimension formIni = this.getSize();
        this.setLocation(( Beranda.PW-formIni.width )/2,(Beranda.PH-formIni.height )/2);
    }
   
    private void Bersih(){
        SPilihAdd.setVisible(false);
        txtDiscountAdd.setText("");
        txtHargaBeliAdd.setText("");
        txtHargaJualAdd.setText("");       
        txtSatuanAdd.setText("");
        txtKategoriAdd.setText("");
        txtNamaAdd.setText("");
        txtKodeAdd.setText("");
        txtKodeAdd.requestFocus();
    }
    
   
    private void Hapus(){   
        
        Object[] options = {"Hapus Sementara","Hapus Permanent", "Batal"};
        int value = JOptionPane.showOptionDialog(null,    "Yakin data Barang akan dihapus?", "Khansa POS",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,  options[2]);
            if (value == JOptionPane.YES_OPTION) {
                try{                          
                    String sql ="UPDATE items SET item_delete='"+1+"' WHERE item_id='"+Id+"' ";                              
                    java.sql.PreparedStatement pst=con.prepareStatement(sql);
                    pst.execute();
                    TampilBarang();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }else if (value == JOptionPane.NO_OPTION) {
                try {                    
                    java.sql.Statement st = con.createStatement();           
                    st.executeUpdate("DELETE FROM items WHERE item_id="+Id);            
                    JOptionPane.showMessageDialog(null, "Data Barang berhasil dihapus!");
                    TampilBarang();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }            
            }                
    }
  
    
     private void Cari(){
        try {
            //java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT item_id,item_code,item_name, "
                    + "item_stock, item_unit,item_bprice,item_sprice, item_sdiscount,item_created, item_update FROM items"
                    + " WHERE item_name LIKE '%"+txtSearch.getText()+"%'");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs)); 
            TampilkanDiTabel();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
         
    private void TampilBarang() {      
        try {
            //java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery("SELECT item_id,item_code,item_name, "
                    + "item_stock, item_unit,item_bprice,item_sprice, item_sdiscount,item_created, item_update FROM items WHERE item_delete = 0 ");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs));            
            TampilkanDiTabel();         
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     
     private void TampilkanDiTabel() {                     
            //Utility_Table ut = new Utility_Table();         
          
            uts.Header(tableTampil,0,"",-10);
            uts.Header(tableTampil,1,"Kode",120);
            uts.Header(tableTampil,2,"Nama Barang",400);
            uts.Header(tableTampil,3,"Stok",50); 
            uts.Header(tableTampil,4,"Satuan",50);
            uts.Header(tableTampil,5,"Harga Beli",100);
            uts.Header(tableTampil,6,"Harga Jual",100);            
            uts.Header(tableTampil,7,"Disc",50);
            uts.Header(tableTampil,8,"Date Created",125);
            uts.Header(tableTampil,9,"Date Update",125);
             
            tableTampil.getColumnModel().getColumn(3).setCellRenderer(uts.formatAngka);
            tableTampil.getColumnModel().getColumn(5).setCellRenderer(uts.formatAngka);
            tableTampil.getColumnModel().getColumn(6).setCellRenderer(uts.formatAngka);
            tableTampil.getColumnModel().getColumn(7).setCellRenderer(uts.formatAngka);
            tableTampil.getColumnModel().getColumn(8).setCellRenderer(uts.formatTanggal);
            tableTampil.getColumnModel().getColumn(9).setCellRenderer(uts.formatTanggal);
            
            tableTampil.removeColumn(tableTampil.getColumnModel().getColumn(0)); //tidak menampilkan kolom (index:0)
    }
     
     //-------------------------------------Item Add--------------------------------------------------------------------------------------
     
    private void Tambah(){
        this.setVisible(false);
        txtKodeAdd.setText("");
        Bersih();        
        SwingUtilities.invokeLater(() -> { txtKodeAdd.requestFocusInWindow(); });
        ItemAdd.setSize(800,500);                     
        ItemAdd.setLocation(((Beranda.SW+120)-800 )/2,((Beranda.SH+50)-500 )/2);
        ItemAdd.setBackground(new Color(0, 0, 0, 0));        
        ItemAdd.setVisible(true); 
    }
     
    private void PopUpCodeAdd(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT item_id,item_code FROM items WHERE item_code LIKE '"+txtKodeAdd.getText()+"%'");
            ListAdd.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                //Utility_Table uts = new Utility_Table();
                uts.Header(ListAdd,0,"",-10);
                uts.Header(ListAdd,1,"",200);
                ListAdd.setBackground(new Color(255,255,255));
                ListAdd.setShowGrid(false);
                ListAdd.removeColumn(ListAdd.getColumnModel().getColumn(0));
                SListAdd.setLocation(txtKodeAdd.getX(), txtKodeAdd.getY()+20);
                
                if (rs.getRow() <= 15) {
                    SListAdd.setSize(340, (rs.getRow()*17)+2);
                } else{
                    SListAdd.setSize(340, (15*17)+2);                    
                }
                    SListAdd.setVisible(true); 
            } else {
                    SListAdd.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }

        private void CekKodeAdd(){
         try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT item_id,item_code,item_delete FROM items WHERE item_code LIKE '"+txtKodeAdd.getText()+"'");
                 if(rs.next()){            
                     if(!"0".equals(rs.getString("item_delete"))){
                        if (JOptionPane.showConfirmDialog(null, "Data Barang sudah ada, UnDelete data?", "Khansa POS",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {            
                                String ItemId = rs.getString("item_id");
                                String sql ="UPDATE items SET item_delete='"+0+"' WHERE item_id='"+ItemId+"' ";                              
                                java.sql.PreparedStatement pst=con.prepareStatement(sql);
                                pst.execute();
                                 KeluarAdd();  
                            } else {
                                txtKodeAdd.setText("");
                                txtKodeAdd.requestFocus();                            
                        }  
                     } else {
                        JOptionPane.showMessageDialog(null, "Kode Barang sudah digunakan!", "Khansa POS", 
                            JOptionPane.WARNING_MESSAGE); 
                            txtKodeAdd.setText("");
                            txtKodeAdd.requestFocus();
                     }
                 }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }   
    }
    
    private void PopUpNameAdd(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT item_id,item_name FROM items WHERE item_name LIKE '"+txtNamaAdd.getText()+"%'");
            ListAdd.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                //Utility_Table uts = new Utility_Table();
                uts.Header(ListAdd,0,"",-10);
                uts.Header(ListAdd,1,"",200);
                ListAdd.setBackground(new Color(255,255,255));
                ListAdd.setShowGrid(false);
                ListAdd.removeColumn(ListAdd.getColumnModel().getColumn(0));
                SListAdd.setLocation(txtNamaAdd.getX(),txtNamaAdd.getY()+20);
                
                if (rs.getRow() <= 15) {
                    SListAdd.setSize(340, (rs.getRow()*17)+2);
                } else{
                    SListAdd.setSize(340, (15*17)+2);                    
                }
                    SListAdd.setVisible(true); 
            } else {
                    SListAdd.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }


  
    private void TampilKategoriAdd(){
        SPilihAdd.setVisible(false);
        Pilihan ="kategori";
        try { 
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  category_id,category_name FROM icategory  ORDER BY category_name");
            PilihAdd.setModel(DbUtils.resultSetToTableModel(rs));            
                if(rs.last()){   
                    //Utility_Table uts = new Utility_Table();                    
                    uts.Header(PilihAdd,1,"",210);
                    PilihAdd.removeColumn(PilihAdd.getColumnModel().getColumn(0));//Tidak Menampilkan Kolom (0)
                    PilihAdd.setBackground(new Color(255,255,255));
                    PilihAdd.clearSelection();                     
                    PilihAdd.changeSelection (0,0,true, false);   
                    SPilihAdd.setLocation(txtKategoriAdd.getX(),txtKategoriAdd.getY());
                        if (rs.getRow() <= 15) {
                            SPilihAdd.setSize(210, (rs.getRow()*17)+2);
                        } else{
                            SPilihAdd.setSize(210, (15*17)+2);                    
                        }  
                            SPilihAdd.setVisible(true); 
                            PilihAdd.requestFocus();
                } else {
                    SPilihAdd.setVisible(false);                
                }    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }      
    }

    private void TampilSatuanAdd(){
        SPilihAdd.setVisible(false);
        Pilihan = "Satuan";
        try {    
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  unit_id,unit_name FROM iunits  ORDER BY unit_name");
            PilihAdd.setModel(DbUtils.resultSetToTableModel(rs));            
                if(rs.last()){   
                    //Utility_Table uts = new Utility_Table();                    
                    uts.Header(PilihAdd,1,"",210);
                    PilihAdd.removeColumn(PilihAdd.getColumnModel().getColumn(0));//Tidak Menampilkan Kolom (0)
                    PilihAdd.setBackground(new Color(255,255,255));
                    PilihAdd.clearSelection();                     
                    PilihAdd.changeSelection (0,0,false, false);                                      
                        if (rs.getRow() <= 15) {
                            SPilihAdd.setSize(210, (rs.getRow()*17)+2);
                        } else{
                            SPilihAdd.setSize(210, (15*17)+2);                    
                        }                  
                            SPilihAdd.setLocation(txtSatuanAdd.getX(),txtSatuanAdd.getY());
                            SPilihAdd.setVisible(true); 
                            PilihAdd.requestFocus();
                } else {
                    SPilihAdd.setVisible(false);                
                }    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } 
    }
   
    private void Simpan(){
        
        if(txtHargaJualAdd.getText().trim().isEmpty()){
            HJual="0";
        }
        if(txtHargaBeliAdd.getText().trim().isEmpty()){
            HBeli="0";
        }
        if(txtDiscountAdd.getText().trim().isEmpty()){
            Disc="0"; 
        }else{
            Disc =txtDiscountAdd.getText(); 
        }

        if (txtKodeAdd.getText().trim().isEmpty() || txtNamaAdd.getText().trim().isEmpty() || txtKategoriAdd.getText().trim().isEmpty() || txtSatuanAdd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Barang Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
                try{   
                        java.util.Date dt = new java.util.Date();
                        java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(dt);
                    
                        String sql ="INSERT INTO items(item_code,item_name,item_category,item_unit,"
                                + "item_bprice,item_sprice,item_sdiscount,item_created) "
                                + "VALUES ('"+txtKodeAdd.getText()+"','"+txtNamaAdd.getText()+"','"+txtKategoriAdd.getText()+"',"
                                + "'"+txtSatuanAdd.getText()+"','"+HBeli+"','"+HJual+"','"+Disc+"','"+currentTime+"')";
                       
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                    
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data Barang Berhasil");
                        KeluarAdd();  
                        TampilBarang(); 
                    }                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
        }         
    }
    
 
    private void TampilPilihAdd(){
        if("kategori".equals(Pilihan)){
            Dipilih= PilihAdd.getModel().getValueAt(PilihAdd.getSelectedRow(), 1).toString();
            txtKategoriAdd.setText(Dipilih);
            SPilihAdd.setVisible(false);
            txtSatuanAdd.requestFocus();
        }else{
            Dipilih= PilihAdd.getModel().getValueAt(PilihAdd.getSelectedRow(), 1).toString();
            txtSatuanAdd.setText(Dipilih);
            SPilihAdd.setVisible(false);
            txtHargaBeliAdd.requestFocus();
            
        }
    }
    

    private void KeluarAdd(){
        ItemAdd.dispose();
        this.setVisible(true);
        txtSearch.requestFocus();
    }

//---------------------------------------------------Item Edit---------------------------------------------------------
    
    
    public void Edit(){
        this.setVisible(false);
        TampilEdit();
        SwingUtilities.invokeLater(() -> { txtNamaAdd.requestFocusInWindow(); });
        ItemEdit.setSize(800,500);                     
        ItemEdit.setLocation(((Beranda.SW+120)-800 )/2,((Beranda.SH+50)-500 )/2);
        ItemEdit.setBackground(new Color(0, 0, 0, 0));      
        ItemEdit.setVisible(true);
    }    

    private void TampilEdit(){
        //Id = ItemForm.Id; 
        
        try {           
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT * FROM items WHERE item_id =' "+Id+" ' ");
                if(rs.next()){                    
                    txtKodeEdit.setText(rs.getString("item_code"));
                    txtNamaBarangEdit.setText(rs.getString("item_name"));
                    txtKategoriEdit.setText(rs.getString("item_category"));
                    txtSatuanEdit.setText(rs.getString("item_unit"));
                    ut.AngkaToRp(txtHargaBeliEdit,rs.getString("item_bprice"));
                    ut.AngkaToRp(txtHargaJualEdit,rs.getString("item_sprice"));
                    ut.AngkaToRp(txtDiscountEdit,rs.getString("item_sdiscount"));
                    
                    HBeli=rs.getString("item_bprice");
                    HJual=rs.getString("item_sprice");
                    Disc=rs.getString("item_sdiscount");
                    
                }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }  
    }

    
    private void PopUpNameEdit(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT item_id,item_name FROM items "
                    + "WHERE item_name LIKE '"+txtNamaBarangEdit.getText()+"%'");
            tabelListEdit.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                //Utility_Table uts = new Utility_Table();
                uts.Header(tabelListEdit,0,"",-10);
                uts.Header(tabelListEdit,1,"",200);
                tabelListEdit.setBackground(new Color(255,255,255));
                tabelListEdit.setShowGrid(false);
                tabelListEdit.removeColumn(tabelListEdit.getColumnModel().getColumn(0));
                StabelListEdit.setLocation(130, 180);
                
                if (rs.getRow() <= 15) {
                    StabelListEdit.setSize(340, (rs.getRow()*17)+2);
                } else{
                    StabelListEdit.setSize(340, (15*17)+2);                    
                }
                    StabelListEdit.setVisible(true); 
            } else {
                    StabelListEdit.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }


  
    private void TampilKategoriEdit(){
        StabelPilihEdit.setVisible(false);
        Pilihan ="kategori";
        try { 
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  category_id,category_name FROM icategory  ORDER BY category_name");
            tabelPilihEdit.setModel(DbUtils.resultSetToTableModel(rs));            
                if(rs.last()){   
                    //Utility_Table uts = new Utility_Table();                    
                    uts.Header(tabelPilihEdit,1,"",210);
                    tabelPilihEdit.removeColumn(tabelPilihEdit.getColumnModel().getColumn(0));//Tidak Menampilkan Kolom (0)
                    tabelPilihEdit.setBackground(new Color(255,255,255));
                    tabelPilihEdit.clearSelection();                     
                    tabelPilihEdit.changeSelection (0,0,true, false);                                      
                        if (rs.getRow() <= 15) {
                            StabelPilihEdit.setSize(210, (rs.getRow()*17)+2);
                        } else{
                            StabelPilihEdit.setSize(210, (15*17)+2);                    
                        }                  
                            StabelPilihEdit.setLocation(130, 200);
                            StabelPilihEdit.setVisible(true); 
                            tabelPilihEdit.requestFocus();
                } else {
                    StabelPilihEdit.setVisible(false);                
                }    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }      
    }

    private void TampilSatuanEdit(){
        StabelPilihEdit.setVisible(false);
        Pilihan = "Satuan";
        try {    
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  unit_id,unit_name FROM iunits  ORDER BY unit_name");
            tabelPilihEdit.setModel(DbUtils.resultSetToTableModel(rs));            
                if(rs.last()){   
                    //Utility_Table uts = new Utility_Table();                    
                    uts.Header(tabelPilihEdit,1,"",210);
                    tabelPilihEdit.removeColumn(tabelPilihEdit.getColumnModel().getColumn(0));//Tidak Menampilkan Kolom (0)
                    tabelPilihEdit.setBackground(new Color(255,255,255));
                    tabelPilihEdit.clearSelection();                     
                    tabelPilihEdit.changeSelection (0,0,false, false);                                      
                        if (rs.getRow() <= 15) {
                            StabelPilihEdit.setSize(210, (rs.getRow()*17)+2);
                        } else{
                            StabelPilihEdit.setSize(210, (15*17)+2);                    
                        }                  
                            StabelPilihEdit.setLocation(130, 250);
                            StabelPilihEdit.setVisible(true); 
                            tabelPilihEdit.requestFocus();
                } else {
                    StabelPilihEdit.setVisible(false);                
                }    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } 
    }
   
    private void Update(){
        
        if(txtHargaJualEdit.getText().trim().isEmpty()){
            HJual="0";
        }
        if(txtHargaBeliEdit.getText().trim().isEmpty()){
            HBeli="0";
        }
        if(txtDiscountEdit.getText().trim().isEmpty()){
            Disc="0"; 
        }else{
            Disc =txtDiscountEdit.getText();            
        }

        if (txtKodeEdit.getText().trim().isEmpty() || txtNamaBarangEdit.getText().trim().isEmpty() || txtKategoriEdit.getText().trim().isEmpty() || txtSatuanEdit.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Barang Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
                try{   
                       // Id = ItemForm.Id; 
                        String sql ="UPDATE items SET item_name='"+txtNamaBarangEdit.getText()+"',item_category='"+txtKategoriEdit.getText()+"',"
                                + "item_unit='"+txtSatuanEdit.getText()+"',item_bprice='"+HBeli+"',item_sprice='"+HJual+"',"
                                + "item_sdiscount='"+Disc+"'    WHERE item_id='"+Id+"' "; 
                       // String sql ="INSERT INTO items(item_code,item_name,item_category,item_unit,item_sprice,item_bprice,item_sdiscount,item_created) "
                       //         + "VALUES ('"+txtKode.getText()+"','"+txtNamaBarang.getText()+"','"+txtKategori.getText()+"',"
                       //         + "'"+txtSatuan.getText()+"','"+HJual+"','"+HBeli+"','"+Disc+"','"+currentTime+"')";
                       
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                    
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data Barang Berhasil");
                        KeluarEdit();  
                        TampilBarang(); 
                    }                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
        }         
    }
    
 
    private void TampilPilihEdit(){
        if("kategori".equals(Pilihan)){
            Dipilih= tabelPilihEdit.getModel().getValueAt(tabelPilihEdit.getSelectedRow(), 1).toString();
            txtKategoriEdit.setText(Dipilih);
            StabelPilihEdit.setVisible(false);
            txtSatuanEdit.requestFocus();
        }else{
            Dipilih= tabelPilihEdit.getModel().getValueAt(tabelPilihEdit.getSelectedRow(), 1).toString();
            txtSatuanEdit.setText(Dipilih);
            StabelPilihEdit.setVisible(false);
            txtHargaBeliEdit.requestFocus();
            
        }
    }
    
    private void KeluarEdit(){        
        ItemEdit.dispose();
        this.setVisible(true);
        txtSearch.requestFocus();
    }
    
//------------------------------------CategoryAdd-------------------------------------------------------------
    
    private void TambahKategori(){
        txtKategoriTambah.setText(""); 
        SwingUtilities.invokeLater(() -> { txtKategoriTambah.requestFocusInWindow(); });
        CategoryAdd.setSize(421, 171);      
        CategoryAdd.setLocation(((Beranda.SW+120)-421)/2,((Beranda.SH+50)-171 )/2);
        CategoryAdd.setBackground(new Color(0, 0, 0, 0));
        CategoryAdd.setVisible(true); 
    }    

    private void PopUpKategoriAdd(){
        try {            
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT category_id,category_name FROM icategory "
                    + "WHERE category_name LIKE '"+txtKategoriTambah.getText()+"%'");
            ListCategoryAdd.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                ListCategoryAdd.setBackground(new Color(255,255,255));
                ListCategoryAdd.setShowGrid(false);
                ListCategoryAdd.removeColumn(ListCategoryAdd.getColumnModel().getColumn(0));
                SListCategoryAdd.setLocation(txtKategoriTambah.getX(),txtKategoriTambah.getY()+20);
                
                if (rs.getRow() <= 3) {
                    SListCategoryAdd.setSize(270, (rs.getRow()*17)+2);
                } else{
                    SListCategoryAdd.setSize(270, (3*17)+2);                    
                }
                    SListCategoryAdd.setVisible(true); 
            } else {
                    SListCategoryAdd.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
   
    private void SimpanKategori(){             
        if (txtKategoriTambah.getText() == null || txtKategoriTambah.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Kategori Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
                try{   
                       String sql ="INSERT INTO icategory(category_name) "
                                + "VALUES ('"+txtKategoriTambah.getText()+"')";                        
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                    
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data Kategori Berhasil");
                        Cari();
                        KeluarKategori();                         
                    }                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
        }         
    }
    
    private void KeluarKategori(){
        CategoryAdd.setVisible(false);
    }
    
//---------------------------------------------------UnitAdd-----------------------------------------------------------   
    
     
    private void TambahSatuan(){
        txtSatuanTambah.setText("");  
        SwingUtilities.invokeLater(() -> { txtSatuanTambah.requestFocusInWindow(); });
        UnitAdd.setSize(421, 171);                    
        UnitAdd.setLocation(((Beranda.SW+120)-421)/2,((Beranda.SH+50)-171 )/2);
        UnitAdd.setBackground(new Color(0, 0, 0, 0));
        UnitAdd.setVisible(true); 
    }   
  

       private void PopUpSatuan(){
        try {           
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT unit_id,unit_name FROM iunits WHERE unit_name LIKE '"+txtSatuanTambah.getText()+"%'");
            ListUnitAdd.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                ListUnitAdd.setBackground(new Color(255,255,255));
                ListUnitAdd.setShowGrid(false);
                ListUnitAdd.removeColumn(ListUnitAdd.getColumnModel().getColumn(0));
                SListUnitAdd.setLocation(txtSatuanTambah.getX(),txtSatuanTambah.getY()+20);
                
                if (rs.getRow() <= 3) {
                    SListUnitAdd.setSize(200, (rs.getRow()*17)+2);
                } else{
                    SListUnitAdd.setSize(200, (3*17)+2);                    
                }
                    SListUnitAdd.setVisible(true); 
            } else {
                    SListUnitAdd.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
       
    
    private void SimpanSatuan(){             
        if (txtSatuanTambah.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Satuan Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
                try{   
                       String sql ="INSERT INTO iunits(unit_name) "
                                + "VALUES ('"+txtSatuanTambah.getText()+"')";
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                    
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data satuan Berhasil");
                        KeluarSatuan();                  
                    }                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
        }         
    }
    
    private void KeluarSatuan(){
        UnitAdd.setVisible(false);  
    }
           

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ItemAdd = new javax.swing.JDialog();
        uPanelRoundrect1 = new Utility.UPanelRoundrect();
        uPanelRoundrect2 = new Utility.UPanelRoundrect();
        SPilihAdd = new javax.swing.JScrollPane();
        PilihAdd = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        SListAdd = new javax.swing.JScrollPane();
        ListAdd =  new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        btnSimpan = new Utility.UButton();
        txtKodeAdd = new javax.swing.JTextField();
        txtNamaAdd = new javax.swing.JTextField();
        txtKategoriAdd = new javax.swing.JTextField();
        txtSatuanAdd = new javax.swing.JTextField();
        txtHargaBeliAdd = new javax.swing.JTextField();
        txtHargaJualAdd = new javax.swing.JTextField();
        txtDiscountAdd = new javax.swing.JTextField();
        btnBersih = new Utility.UButton();
        btnTambahKategoriAdd = new Utility.UButton();
        btnTambahSatuanAdd = new Utility.UButton();
        jLabel3 = new javax.swing.JLabel();
        btnCloseAdd = new Utility.UButton();
        ItemEdit = new javax.swing.JDialog();
        uPanelRoundrect3 = new Utility.UPanelRoundrect();
        uPanelRoundrect4 = new Utility.UPanelRoundrect();
        StabelListEdit = new javax.swing.JScrollPane();
        tabelListEdit =  new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        StabelPilihEdit = new javax.swing.JScrollPane();
        tabelPilihEdit = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jLabel12 = new javax.swing.JLabel();
        txtKodeEdit = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        txtNamaBarangEdit = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        txtKategoriEdit = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        txtSatuanEdit = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        txtHargaBeliEdit = new javax.swing.JTextField();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        txtHargaJualEdit = new javax.swing.JTextField();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        txtDiscountEdit = new javax.swing.JTextField();
        jSeparator16 = new javax.swing.JSeparator();
        btnUpdate = new Utility.UButton();
        jLabel11 = new javax.swing.JLabel();
        btnCloseEdit = new Utility.UButton();
        CategoryAdd = new javax.swing.JDialog();
        uPanelRoundrect5 = new Utility.UPanelRoundrect();
        uPanelRoundrect6 = new Utility.UPanelRoundrect();
        btnSimpanKategori = new Utility.UButton();
        SListCategoryAdd = new javax.swing.JScrollPane();
        ListCategoryAdd = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jLabel20 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        txtKategoriTambah = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        btnCloseKategori = new Utility.UButton();
        UnitAdd = new javax.swing.JDialog();
        uPanelRoundrect7 = new Utility.UPanelRoundrect();
        uPanelRoundrect8 = new Utility.UPanelRoundrect();
        btnSimpanSatuan = new Utility.UButton();
        SListUnitAdd = new javax.swing.JScrollPane();
        ListUnitAdd = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JSeparator();
        txtSatuanTambah = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        btnCloseSatuan = new Utility.UButton();
        jPanel1 = new javax.swing.JPanel();
        panelEH = new javax.swing.JPanel();
        btnHapus = new Utility.UButton();
        btnEdit = new Utility.UButton();
        SPtableTampil = new javax.swing.JScrollPane();
        tableTampil = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txtSearch = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        btnTambah = new Utility.UButton();

        ItemAdd.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ItemAdd.setMinimumSize(new java.awt.Dimension(791, 477));
        ItemAdd.setUndecorated(true);
        ItemAdd.setSize(new java.awt.Dimension(791, 462));
        ItemAdd.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect1.setKetebalanBorder(2.0F);
        uPanelRoundrect1.setPreferredSize(new java.awt.Dimension(800, 500));
        uPanelRoundrect1.setWarnaBackground(new java.awt.Color(87, 176, 86));
        uPanelRoundrect1.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect2.setKetebalanBorder(2.0F);
        uPanelRoundrect2.setPreferredSize(new java.awt.Dimension(780, 420));
        uPanelRoundrect2.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SPilihAdd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        SPilihAdd.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SPilihAdd.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SPilihAdd.setFocusable(false);

        PilihAdd.setForeground(new java.awt.Color(51, 51, 51));
        PilihAdd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PilihAdd.setGridColor(new java.awt.Color(255, 255, 255));
        PilihAdd.setShowGrid(false);
        PilihAdd.setSurrendersFocusOnKeystroke(true);
        PilihAdd.setTableHeader(null
        );
        PilihAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PilihAddMouseClicked(evt);
            }
        });
        PilihAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PilihAddKeyPressed(evt);
            }
        });
        SPilihAdd.setViewportView(PilihAdd);

        uPanelRoundrect2.add(SPilihAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, 170, 0));

        SListAdd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        SListAdd.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SListAdd.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SListAdd.setFocusable(false);

        ListAdd.setForeground(new java.awt.Color(153, 153, 153));
        ListAdd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ListAdd.setGridColor(new java.awt.Color(255, 255, 255));
        ListAdd.setShowGrid(false);
        ListAdd.setSurrendersFocusOnKeystroke(true);
        ListAdd.setTableHeader(null
        );
        SListAdd.setViewportView(ListAdd);

        uPanelRoundrect2.add(SListAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 340, 0));

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Kode");
        jLabel4.setToolTipText(null);
        uPanelRoundrect2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, 20));

        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 335, 10));

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Nama Barang");
        jLabel5.setToolTipText(null);
        uPanelRoundrect2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, 20));

        jSeparator4.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 580, 10));

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Kategori");
        jLabel6.setToolTipText(null);
        uPanelRoundrect2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, 20));

        jSeparator5.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 220, -1));

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Satuan");
        jLabel7.setToolTipText(null);
        uPanelRoundrect2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 76, 20));

        jSeparator6.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 220, 10));

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Harga Beli");
        jLabel9.setToolTipText(null);
        uPanelRoundrect2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, 20));

        jSeparator9.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 130, 10));

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Harga Jual");
        jLabel10.setToolTipText(null);
        uPanelRoundrect2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, -1, 20));

        jSeparator10.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 130, 10));

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Discount");
        jLabel8.setToolTipText(null);
        uPanelRoundrect2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, -1, 20));

        jSeparator11.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 130, 10));

        btnSimpan.setMnemonic('s');
        btnSimpan.setText("Simpan");
        btnSimpan.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnSimpan.setKetebalanBorder(2.0F);
        btnSimpan.setKetumpulanSudut(35);
        btnSimpan.setPreferredSize(new java.awt.Dimension(150, 38));
        btnSimpan.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        uPanelRoundrect2.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 360, -1, -1));

        txtKodeAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtKodeAdd.setToolTipText(null);
        txtKodeAdd.setBorder(null);
        txtKodeAdd.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtKodeAdd.setOpaque(false);
        txtKodeAdd.setPreferredSize(new java.awt.Dimension(1, 20));
        txtKodeAdd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtKodeAddFocusLost(evt);
            }
        });
        txtKodeAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeAddKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKodeAddKeyReleased(evt);
            }
        });
        uPanelRoundrect2.add(txtKodeAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 335, -1));

        txtNamaAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtNamaAdd.setToolTipText(null);
        txtNamaAdd.setBorder(null);
        txtNamaAdd.setOpaque(false);
        txtNamaAdd.setPreferredSize(new java.awt.Dimension(1, 20));
        txtNamaAdd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNamaAddFocusLost(evt);
            }
        });
        txtNamaAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaAddKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNamaAddKeyReleased(evt);
            }
        });
        uPanelRoundrect2.add(txtNamaAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 500, -1));

        txtKategoriAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtKategoriAdd.setToolTipText(null);
        txtKategoriAdd.setBorder(null);
        txtKategoriAdd.setOpaque(false);
        txtKategoriAdd.setPreferredSize(new java.awt.Dimension(1, 20));
        txtKategoriAdd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtKategoriAddFocusGained(evt);
            }
        });
        txtKategoriAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKategoriAddKeyPressed(evt);
            }
        });
        uPanelRoundrect2.add(txtKategoriAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 190, -1));

        txtSatuanAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtSatuanAdd.setToolTipText(null);
        txtSatuanAdd.setBorder(null);
        txtSatuanAdd.setOpaque(false);
        txtSatuanAdd.setPreferredSize(new java.awt.Dimension(1, 20));
        txtSatuanAdd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSatuanAddFocusGained(evt);
            }
        });
        txtSatuanAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSatuanAddKeyPressed(evt);
            }
        });
        uPanelRoundrect2.add(txtSatuanAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 190, -1));

        txtHargaBeliAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtHargaBeliAdd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHargaBeliAdd.setToolTipText(null);
        txtHargaBeliAdd.setBorder(null);
        txtHargaBeliAdd.setOpaque(false);
        txtHargaBeliAdd.setPreferredSize(new java.awt.Dimension(1, 20));
        txtHargaBeliAdd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHargaBeliAddFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHargaBeliAddFocusLost(evt);
            }
        });
        txtHargaBeliAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHargaBeliAddKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaBeliAddKeyTyped(evt);
            }
        });
        uPanelRoundrect2.add(txtHargaBeliAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 130, -1));

        txtHargaJualAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtHargaJualAdd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHargaJualAdd.setToolTipText(null);
        txtHargaJualAdd.setBorder(null);
        txtHargaJualAdd.setOpaque(false);
        txtHargaJualAdd.setPreferredSize(new java.awt.Dimension(1, 20));
        txtHargaJualAdd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHargaJualAddFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHargaJualAddFocusLost(evt);
            }
        });
        txtHargaJualAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHargaJualAddKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaJualAddKeyTyped(evt);
            }
        });
        uPanelRoundrect2.add(txtHargaJualAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 130, -1));

        txtDiscountAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtDiscountAdd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscountAdd.setToolTipText(null);
        txtDiscountAdd.setBorder(null);
        txtDiscountAdd.setOpaque(false);
        txtDiscountAdd.setPreferredSize(new java.awt.Dimension(1, 20));
        txtDiscountAdd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiscountAddFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiscountAddFocusLost(evt);
            }
        });
        txtDiscountAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDiscountAddKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiscountAddKeyTyped(evt);
            }
        });
        uPanelRoundrect2.add(txtDiscountAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 130, -1));

        btnBersih.setMnemonic('h');
        btnBersih.setText("Bersih");
        btnBersih.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnBersih.setKetebalanBorder(2.0F);
        btnBersih.setKetumpulanSudut(35);
        btnBersih.setPreferredSize(new java.awt.Dimension(150, 38));
        btnBersih.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnBersih.setWarnaBackgroundHover(new java.awt.Color(255, 231, 112));
        btnBersih.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnBersih.setWarnaBorder(new java.awt.Color(255, 231, 112));
        btnBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBersihActionPerformed(evt);
            }
        });
        uPanelRoundrect2.add(btnBersih, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 360, -1, -1));

        btnTambahKategoriAdd.setText("+ Kategori");
        btnTambahKategoriAdd.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnTambahKategoriAdd.setKetebalanBorder(2.0F);
        btnTambahKategoriAdd.setPreferredSize(new java.awt.Dimension(95, 25));
        btnTambahKategoriAdd.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnTambahKategoriAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKategoriAddActionPerformed(evt);
            }
        });
        uPanelRoundrect2.add(btnTambahKategoriAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, -1, -1));

        btnTambahSatuanAdd.setText("+ Satuan");
        btnTambahSatuanAdd.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnTambahSatuanAdd.setKetebalanBorder(2.0F);
        btnTambahSatuanAdd.setPreferredSize(new java.awt.Dimension(95, 25));
        btnTambahSatuanAdd.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnTambahSatuanAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSatuanAddActionPerformed(evt);
            }
        });
        uPanelRoundrect2.add(btnTambahSatuanAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 190, -1, -1));

        uPanelRoundrect1.add(uPanelRoundrect2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tambah Barang");
        uPanelRoundrect1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        btnCloseAdd.setMnemonic('c');
        btnCloseAdd.setText("Close");
        btnCloseAdd.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnCloseAdd.setWarnaBackgroundHover(new java.awt.Color(87, 176, 86));
        btnCloseAdd.setWarnaBorder(new java.awt.Color(87, 176, 86));
        btnCloseAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddActionPerformed(evt);
            }
        });
        uPanelRoundrect1.add(btnCloseAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, -1, -1));

        ItemAdd.getContentPane().add(uPanelRoundrect1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        ItemEdit.setModal(true);
        ItemEdit.setUndecorated(true);
        ItemEdit.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect3.setKetebalanBorder(2.0F);
        uPanelRoundrect3.setPreferredSize(new java.awt.Dimension(800, 500));
        uPanelRoundrect3.setWarnaBackground(new java.awt.Color(235, 154, 35));
        uPanelRoundrect3.setWarnaBorder(new java.awt.Color(255, 231, 112));
        uPanelRoundrect3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect4.setKetebalanBorder(2.0F);
        uPanelRoundrect4.setPreferredSize(new java.awt.Dimension(780, 420));
        uPanelRoundrect4.setWarnaBorder(new java.awt.Color(255, 231, 112));
        uPanelRoundrect4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        StabelListEdit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        StabelListEdit.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        StabelListEdit.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        StabelListEdit.setFocusable(false);

        tabelListEdit.setForeground(new java.awt.Color(153, 153, 153));
        tabelListEdit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelListEdit.setGridColor(new java.awt.Color(255, 255, 255));
        tabelListEdit.setShowGrid(false);
        tabelListEdit.setSurrendersFocusOnKeystroke(true);
        tabelListEdit.setTableHeader(null
        );
        StabelListEdit.setViewportView(tabelListEdit);

        uPanelRoundrect4.add(StabelListEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 340, 0));

        StabelPilihEdit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        StabelPilihEdit.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        StabelPilihEdit.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        StabelPilihEdit.setFocusable(false);

        tabelPilihEdit.setForeground(new java.awt.Color(51, 51, 51));
        tabelPilihEdit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelPilihEdit.setGridColor(new java.awt.Color(255, 255, 255));
        tabelPilihEdit.setShowGrid(false);
        tabelPilihEdit.setSurrendersFocusOnKeystroke(true);
        tabelPilihEdit.setTableHeader(null
        );
        tabelPilihEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPilihEditMouseClicked(evt);
            }
        });
        tabelPilihEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelPilihEditKeyPressed(evt);
            }
        });
        StabelPilihEdit.setViewportView(tabelPilihEdit);

        uPanelRoundrect4.add(StabelPilihEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, 170, 0));

        jLabel12.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Kode");
        jLabel12.setToolTipText(null);
        uPanelRoundrect4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        txtKodeEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtKodeEdit.setForeground(new java.awt.Color(204, 204, 204));
        txtKodeEdit.setToolTipText(null);
        txtKodeEdit.setBorder(null);
        txtKodeEdit.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtKodeEdit.setOpaque(false);
        txtKodeEdit.setPreferredSize(new java.awt.Dimension(1, 20));
        uPanelRoundrect4.add(txtKodeEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 335, -1));

        jSeparator7.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 335, 10));

        jLabel13.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Nama Barang");
        jLabel13.setToolTipText(null);
        uPanelRoundrect4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, 20));

        txtNamaBarangEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtNamaBarangEdit.setToolTipText(null);
        txtNamaBarangEdit.setBorder(null);
        txtNamaBarangEdit.setOpaque(false);
        txtNamaBarangEdit.setPreferredSize(new java.awt.Dimension(1, 20));
        txtNamaBarangEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNamaBarangEditFocusLost(evt);
            }
        });
        txtNamaBarangEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaBarangEditKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNamaBarangEditKeyReleased(evt);
            }
        });
        uPanelRoundrect4.add(txtNamaBarangEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 500, -1));

        jSeparator8.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 580, 10));

        jLabel14.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("Kategori");
        jLabel14.setToolTipText(null);
        uPanelRoundrect4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, 20));

        txtKategoriEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtKategoriEdit.setToolTipText(null);
        txtKategoriEdit.setBorder(null);
        txtKategoriEdit.setOpaque(false);
        txtKategoriEdit.setPreferredSize(new java.awt.Dimension(1, 20));
        txtKategoriEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtKategoriEditFocusGained(evt);
            }
        });
        txtKategoriEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKategoriEditKeyPressed(evt);
            }
        });
        uPanelRoundrect4.add(txtKategoriEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 190, -1));

        jSeparator12.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 220, -1));

        jLabel15.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setText("Satuan");
        jLabel15.setToolTipText(null);
        uPanelRoundrect4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 76, 20));

        txtSatuanEdit.setEditable(false);
        txtSatuanEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtSatuanEdit.setToolTipText(null);
        txtSatuanEdit.setBorder(null);
        txtSatuanEdit.setOpaque(false);
        txtSatuanEdit.setPreferredSize(new java.awt.Dimension(1, 20));
        txtSatuanEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSatuanEditFocusGained(evt);
            }
        });
        txtSatuanEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSatuanEditKeyPressed(evt);
            }
        });
        uPanelRoundrect4.add(txtSatuanEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 190, -1));

        jSeparator13.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 220, 10));

        jLabel17.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("Harga Beli");
        jLabel17.setToolTipText(null);
        uPanelRoundrect4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, 20));

        txtHargaBeliEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtHargaBeliEdit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHargaBeliEdit.setToolTipText(null);
        txtHargaBeliEdit.setBorder(null);
        txtHargaBeliEdit.setOpaque(false);
        txtHargaBeliEdit.setPreferredSize(new java.awt.Dimension(1, 20));
        txtHargaBeliEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHargaBeliEditFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHargaBeliEditFocusLost(evt);
            }
        });
        txtHargaBeliEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHargaBeliEditKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaBeliEditKeyTyped(evt);
            }
        });
        uPanelRoundrect4.add(txtHargaBeliEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 130, -1));

        jSeparator14.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 130, 10));

        jLabel18.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setText("Harga Jual");
        jLabel18.setToolTipText(null);
        uPanelRoundrect4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, 20));

        txtHargaJualEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtHargaJualEdit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHargaJualEdit.setToolTipText(null);
        txtHargaJualEdit.setBorder(null);
        txtHargaJualEdit.setOpaque(false);
        txtHargaJualEdit.setPreferredSize(new java.awt.Dimension(1, 20));
        txtHargaJualEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHargaJualEditFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHargaJualEditFocusLost(evt);
            }
        });
        txtHargaJualEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHargaJualEditKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaJualEditKeyTyped(evt);
            }
        });
        uPanelRoundrect4.add(txtHargaJualEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, 130, -1));

        jSeparator15.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 130, 10));

        jLabel16.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Discount");
        jLabel16.setToolTipText(null);
        uPanelRoundrect4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, 20));

        txtDiscountEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtDiscountEdit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscountEdit.setToolTipText(null);
        txtDiscountEdit.setBorder(null);
        txtDiscountEdit.setOpaque(false);
        txtDiscountEdit.setPreferredSize(new java.awt.Dimension(1, 20));
        txtDiscountEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiscountEditFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiscountEditFocusLost(evt);
            }
        });
        txtDiscountEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDiscountEditKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiscountEditKeyTyped(evt);
            }
        });
        uPanelRoundrect4.add(txtDiscountEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 130, -1));

        jSeparator16.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, 130, 10));

        btnUpdate.setMnemonic('u');
        btnUpdate.setText("Update");
        btnUpdate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnUpdate.setKetebalanBorder(2.0F);
        btnUpdate.setKetumpulanSudut(35);
        btnUpdate.setPreferredSize(new java.awt.Dimension(150, 38));
        btnUpdate.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnUpdate.setWarnaBackgroundHover(new java.awt.Color(255, 231, 112));
        btnUpdate.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnUpdate.setWarnaBorder(new java.awt.Color(255, 231, 112));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        uPanelRoundrect4.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 360, -1, -1));

        uPanelRoundrect3.add(uPanelRoundrect4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Edit Barang");
        uPanelRoundrect3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        btnCloseEdit.setMnemonic('c');
        btnCloseEdit.setText("Close");
        btnCloseEdit.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnCloseEdit.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnCloseEdit.setWarnaBackgroundHover(new java.awt.Color(235, 154, 35));
        btnCloseEdit.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnCloseEdit.setWarnaBorder(new java.awt.Color(235, 154, 35));
        btnCloseEdit.setWarnaForegroundHover(new java.awt.Color(255, 0, 0));
        btnCloseEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseEditActionPerformed(evt);
            }
        });
        uPanelRoundrect3.add(btnCloseEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, -1, -1));

        ItemEdit.getContentPane().add(uPanelRoundrect3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        CategoryAdd.setModal(true);
        CategoryAdd.setUndecorated(true);
        CategoryAdd.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect5.setKetebalanBorder(2.5F);
        uPanelRoundrect5.setPreferredSize(new java.awt.Dimension(421, 171));
        uPanelRoundrect5.setWarnaBackground(new java.awt.Color(87, 176, 86));
        uPanelRoundrect5.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect6.setKetebalanBorder(2.5F);
        uPanelRoundrect6.setPreferredSize(new java.awt.Dimension(411, 115));
        uPanelRoundrect6.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSimpanKategori.setMnemonic('s');
        btnSimpanKategori.setText("Simpan");
        btnSimpanKategori.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnSimpanKategori.setKetebalanBorder(2.0F);
        btnSimpanKategori.setKetumpulanSudut(35);
        btnSimpanKategori.setPreferredSize(new java.awt.Dimension(120, 32));
        btnSimpanKategori.setPress(true);
        btnSimpanKategori.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnSimpanKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanKategoriActionPerformed(evt);
            }
        });
        uPanelRoundrect6.add(btnSimpanKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 75, -1, -1));

        SListCategoryAdd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        SListCategoryAdd.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SListCategoryAdd.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SListCategoryAdd.setFocusable(false);
        SListCategoryAdd.setPreferredSize(new java.awt.Dimension(200, 200));

        ListCategoryAdd.setForeground(new java.awt.Color(153, 153, 153));
        ListCategoryAdd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ListCategoryAdd.setGridColor(new java.awt.Color(255, 255, 255));
        ListCategoryAdd.setShowGrid(false);
        ListCategoryAdd.setSurrendersFocusOnKeystroke(true);
        ListCategoryAdd.setTableHeader(null
        );
        SListCategoryAdd.setViewportView(ListCategoryAdd);

        uPanelRoundrect6.add(SListCategoryAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 260, 0));

        jLabel20.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("Kategori");
        jLabel20.setToolTipText(null);
        uPanelRoundrect6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 70, 20));

        jSeparator17.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect6.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 270, 10));

        txtKategoriTambah.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKategoriTambah.setToolTipText(null);
        txtKategoriTambah.setBorder(null);
        txtKategoriTambah.setOpaque(false);
        txtKategoriTambah.setPreferredSize(new java.awt.Dimension(1, 20));
        txtKategoriTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKategoriTambahKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKategoriTambahKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKategoriTambahKeyTyped(evt);
            }
        });
        uPanelRoundrect6.add(txtKategoriTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 270, -1));

        uPanelRoundrect5.add(uPanelRoundrect6, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 50, -1, -1));

        jLabel19.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Tambah Kategori");
        uPanelRoundrect5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 32));

        btnCloseKategori.setText("Close");
        btnCloseKategori.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnCloseKategori.setPreferredSize(new java.awt.Dimension(64, 20));
        btnCloseKategori.setWarnaBackgroundHover(new java.awt.Color(87, 176, 86));
        btnCloseKategori.setWarnaBorder(new java.awt.Color(87, 176, 86));
        btnCloseKategori.setWarnaForegroundHover(new java.awt.Color(255, 0, 0));
        btnCloseKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseKategoriActionPerformed(evt);
            }
        });
        uPanelRoundrect5.add(btnCloseKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 10, -1, -1));

        CategoryAdd.getContentPane().add(uPanelRoundrect5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        UnitAdd.setModal(true);
        UnitAdd.setUndecorated(true);
        UnitAdd.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect7.setKetebalanBorder(2.5F);
        uPanelRoundrect7.setPreferredSize(new java.awt.Dimension(421, 171));
        uPanelRoundrect7.setWarnaBackground(new java.awt.Color(87, 176, 86));
        uPanelRoundrect7.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect8.setKetebalanBorder(2.5F);
        uPanelRoundrect8.setPreferredSize(new java.awt.Dimension(411, 115));
        uPanelRoundrect8.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSimpanSatuan.setMnemonic('s');
        btnSimpanSatuan.setText("Simpan");
        btnSimpanSatuan.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnSimpanSatuan.setKetebalanBorder(2.5F);
        btnSimpanSatuan.setKetumpulanSudut(35);
        btnSimpanSatuan.setPreferredSize(new java.awt.Dimension(120, 32));
        btnSimpanSatuan.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnSimpanSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanSatuanActionPerformed(evt);
            }
        });
        uPanelRoundrect8.add(btnSimpanSatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, -1, -1));

        SListUnitAdd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        SListUnitAdd.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SListUnitAdd.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SListUnitAdd.setFocusable(false);
        SListUnitAdd.setPreferredSize(new java.awt.Dimension(200, 200));

        ListUnitAdd.setForeground(new java.awt.Color(153, 153, 153));
        ListUnitAdd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ListUnitAdd.setGridColor(new java.awt.Color(255, 255, 255));
        ListUnitAdd.setShowGrid(false);
        ListUnitAdd.setSurrendersFocusOnKeystroke(true);
        ListUnitAdd.setTableHeader(null
        );
        SListUnitAdd.setViewportView(ListUnitAdd);

        uPanelRoundrect8.add(SListUnitAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, -1, 0));

        jLabel22.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setText("Satuan");
        jLabel22.setToolTipText(null);
        uPanelRoundrect8.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 76, 20));

        jSeparator18.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect8.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 220, 10));

        txtSatuanTambah.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtSatuanTambah.setToolTipText(null);
        txtSatuanTambah.setBorder(null);
        txtSatuanTambah.setOpaque(false);
        txtSatuanTambah.setPreferredSize(new java.awt.Dimension(1, 20));
        txtSatuanTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSatuanTambahKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSatuanTambahKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSatuanTambahKeyTyped(evt);
            }
        });
        uPanelRoundrect8.add(txtSatuanTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 220, -1));

        uPanelRoundrect7.add(uPanelRoundrect8, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 50, -1, -1));

        jLabel21.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Tambah Satuan");
        uPanelRoundrect7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 20));

        btnCloseSatuan.setMnemonic('c');
        btnCloseSatuan.setText("Close");
        btnCloseSatuan.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnCloseSatuan.setWarnaBackgroundHover(new java.awt.Color(87, 176, 86));
        btnCloseSatuan.setWarnaBorder(new java.awt.Color(87, 176, 86));
        btnCloseSatuan.setWarnaForegroundHover(new java.awt.Color(255, 0, 0));
        btnCloseSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseSatuanActionPerformed(evt);
            }
        });
        uPanelRoundrect7.add(btnCloseSatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 10, -1, -1));

        UnitAdd.getContentPane().add(uPanelRoundrect7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1246, 714));

        jPanel1.setBackground(new java.awt.Color(248, 251, 251));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelEH.setOpaque(false);
        panelEH.setPreferredSize(new java.awt.Dimension(130, 30));
        panelEH.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnHapus.setText("Hapus");
        btnHapus.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnHapus.setKetebalanBorder(2.0F);
        btnHapus.setKetumpulanSudut(35);
        btnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        btnHapus.setWarnaBackground(new java.awt.Color(255, 0, 0));
        btnHapus.setWarnaBackgroundHover(new java.awt.Color(255, 204, 204));
        btnHapus.setWarnaBackgroundPress(new java.awt.Color(255, 0, 0));
        btnHapus.setWarnaBorder(new java.awt.Color(255, 204, 204));
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        panelEH.add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 100, -1));

        btnEdit.setText("Edit");
        btnEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnEdit.setKetebalanBorder(2.0F);
        btnEdit.setKetumpulanSudut(35);
        btnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        btnEdit.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnEdit.setWarnaBackgroundHover(new java.awt.Color(255, 231, 112));
        btnEdit.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnEdit.setWarnaBorder(new java.awt.Color(255, 231, 112));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        panelEH.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.add(panelEH, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, 0));

        tableTampil.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tableTampil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableTampil.setFocusable(false);
        tableTampil.setRowHeight(20);
        tableTampil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTampilMouseClicked(evt);
            }
        });
        SPtableTampil.setViewportView(tableTampil);

        jPanel1.add(SPtableTampil, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 1180, 510));

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\Search-icon.png")); // NOI18N
        jLabel2.setText("Cari Nama Member :");
        jLabel2.setToolTipText(null);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 110, -1, -1));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 130, 240, 10));

        txtSearch.setBackground(new java.awt.Color(248, 251, 251));
        txtSearch.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        txtSearch.setToolTipText(null);
        txtSearch.setBorder(null);
        txtSearch.setFocusCycleRoot(true);
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 110, 240, 20));

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 240, 10));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(34, 67, 67));
        jLabel1.setText("Master Barang");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 300, 60));

        btnTambah.setText("Tambah Barang");
        btnTambah.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnTambah.setKetebalanBorder(2.0F);
        btnTambah.setKetumpulanSudut(35);
        btnTambah.setPreferredSize(new java.awt.Dimension(150, 38));
        btnTambah.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        jPanel1.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 1246, 714);
    }// </editor-fold>//GEN-END:initComponents

    private void tableTampilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTampilMouseClicked
        Id= tableTampil.getModel().getValueAt(tableTampil.getSelectedRow(), 0).toString(); //Ambil nilai kolom (0) dan masukkan ke variabel Id

        panelEH.setLocation( evt.getX() + SPtableTampil.getX(),  evt.getY() + SPtableTampil.getY());
        panelEH.setSize(172,30);
    }//GEN-LAST:event_tableTampilMouseClicked

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){

        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSearch.setText("");
            panelEH.setSize(172, 0);
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        Cari();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnCloseEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseEditActionPerformed
        KeluarEdit();
    }//GEN-LAST:event_btnCloseEditActionPerformed

    private void tabelPilihEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPilihEditMouseClicked
        TampilPilihEdit();
    }//GEN-LAST:event_tabelPilihEditMouseClicked

    private void tabelPilihEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelPilihEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            TampilPilihEdit();
        }
    }//GEN-LAST:event_tabelPilihEditKeyPressed

    private void txtNamaBarangEditFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNamaBarangEditFocusLost
        StabelListEdit.setVisible(false);
    }//GEN-LAST:event_txtNamaBarangEditFocusLost

    private void txtNamaBarangEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaBarangEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtKategoriEdit.setText(null);
            txtKategoriEdit.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtNamaBarangEdit.setText("");
        }
    }//GEN-LAST:event_txtNamaBarangEditKeyPressed

    private void txtNamaBarangEditKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaBarangEditKeyReleased
        if (txtNamaBarangEdit.getText().trim().isEmpty()) {
            StabelListEdit.setVisible(false);
        } else{
            PopUpNameEdit();
        }
    }//GEN-LAST:event_txtNamaBarangEditKeyReleased

    private void txtKategoriEditFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKategoriEditFocusGained
        TampilKategoriEdit();
    }//GEN-LAST:event_txtKategoriEditFocusGained

    private void txtKategoriEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtSatuanEdit.setText(null);
            txtSatuanEdit.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKategoriEdit.setText("");
        }
    }//GEN-LAST:event_txtKategoriEditKeyPressed

    private void txtSatuanEditFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSatuanEditFocusGained
        TampilSatuanEdit();
    }//GEN-LAST:event_txtSatuanEditFocusGained

    private void txtSatuanEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtHargaBeliEdit.setText(null);
            txtHargaBeliEdit.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSatuanEdit.setText("");
        }
    }//GEN-LAST:event_txtSatuanEditKeyPressed

    private void txtHargaBeliEditFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaBeliEditFocusGained
        txtHargaBeliEdit.setText(HBeli);
    }//GEN-LAST:event_txtHargaBeliEditFocusGained

    private void txtHargaBeliEditFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaBeliEditFocusLost
        HBeli =txtHargaBeliEdit.getText();          //Menyimpan di Variable HBeli
        if (!txtHargaBeliEdit.getText().trim().isEmpty()){
            ut.AngkaToRp(txtHargaBeliEdit,HBeli);
        }
    }//GEN-LAST:event_txtHargaBeliEditFocusLost

    private void txtHargaBeliEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaBeliEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtHargaJualEdit.setText(null);
            txtHargaJualEdit.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtHargaBeliEdit.setText("");
        }
    }//GEN-LAST:event_txtHargaBeliEditKeyPressed

    private void txtHargaBeliEditKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaBeliEditKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtHargaBeliEditKeyTyped

    private void txtHargaJualEditFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaJualEditFocusGained
        txtHargaJualEdit.setText(HJual);
    }//GEN-LAST:event_txtHargaJualEditFocusGained

    private void txtHargaJualEditFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaJualEditFocusLost
        HJual =txtHargaJualEdit.getText();          //Menyimpan di Variable HJual
        if (!txtHargaJualEdit.getText().trim().isEmpty()){
            ut.AngkaToRp(txtHargaJualEdit,HJual);
        }
    }//GEN-LAST:event_txtHargaJualEditFocusLost

    private void txtHargaJualEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaJualEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtDiscountEdit.setText(null);
            txtDiscountEdit.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtHargaJualEdit.setText("");
        }
    }//GEN-LAST:event_txtHargaJualEditKeyPressed

    private void txtHargaJualEditKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaJualEditKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtHargaJualEditKeyTyped

    private void txtDiscountEditFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountEditFocusGained
        txtDiscountEdit.setText(Disc);
    }//GEN-LAST:event_txtDiscountEditFocusGained

    private void txtDiscountEditFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountEditFocusLost
        Disc =txtDiscountEdit.getText();            //Menyimpan di Variable Disc
        if (!txtDiscountEdit.getText().trim().isEmpty()){
            ut.AngkaToRp(txtDiscountEdit,Disc);
        }
    }//GEN-LAST:event_txtDiscountEditFocusLost

    private void txtDiscountEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Update();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtDiscountEdit.setText("");
        }
    }//GEN-LAST:event_txtDiscountEditKeyPressed

    private void txtDiscountEditKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountEditKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtDiscountEditKeyTyped

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        panelEH.setSize(172, 0);
        Hapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        panelEH.setSize(172, 0);
        Edit();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        Tambah();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanKategoriActionPerformed
        SimpanKategori();
    }//GEN-LAST:event_btnSimpanKategoriActionPerformed

    private void btnCloseKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseKategoriActionPerformed
        KeluarKategori();
    }//GEN-LAST:event_btnCloseKategoriActionPerformed

    private void btnCloseAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseAddActionPerformed
        KeluarAdd();
    }//GEN-LAST:event_btnCloseAddActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        Simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnSimpanSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanSatuanActionPerformed
        SimpanSatuan();
    }//GEN-LAST:event_btnSimpanSatuanActionPerformed

    private void btnCloseSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseSatuanActionPerformed
        KeluarSatuan();
    }//GEN-LAST:event_btnCloseSatuanActionPerformed

    private void txtSatuanAddFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSatuanAddFocusGained
        TampilSatuanAdd();
    }//GEN-LAST:event_txtSatuanAddFocusGained

    private void txtSatuanAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtHargaBeliAdd.setText(null);
            txtHargaBeliAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSatuanAdd.setText("");
        }
    }//GEN-LAST:event_txtSatuanAddKeyPressed

    private void txtHargaBeliAddFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaBeliAddFocusGained
        txtHargaBeliAdd.setText(HBeli);
    }//GEN-LAST:event_txtHargaBeliAddFocusGained

    private void txtHargaBeliAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaBeliAddFocusLost
        HBeli =txtHargaBeliAdd.getText();          //Menyimpan di Variable HBeli
        if (!txtHargaBeliAdd.getText().trim().isEmpty()){
            ut.AngkaToRp(txtHargaBeliAdd,HBeli);
        }
    }//GEN-LAST:event_txtHargaBeliAddFocusLost

    private void txtHargaBeliAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaBeliAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtHargaJualAdd.setText(null);
            txtHargaJualAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtHargaBeliAdd.setText("");
        }
    }//GEN-LAST:event_txtHargaBeliAddKeyPressed

    private void txtHargaBeliAddKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaBeliAddKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtHargaBeliAddKeyTyped

    private void txtHargaJualAddFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaJualAddFocusGained
        txtHargaJualAdd.setText(HJual);
    }//GEN-LAST:event_txtHargaJualAddFocusGained

    private void txtHargaJualAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaJualAddFocusLost
        HJual =txtHargaJualAdd.getText();          //Menyimpan di Variable HBeli
        if (!txtHargaJualAdd.getText().trim().isEmpty()){
            ut.AngkaToRp(txtHargaJualAdd,HJual);
        }
    }//GEN-LAST:event_txtHargaJualAddFocusLost

    private void txtHargaJualAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaJualAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtDiscountAdd.setText(null);
            txtDiscountAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtHargaJualAdd.setText("");
        }
    }//GEN-LAST:event_txtHargaJualAddKeyPressed

    private void txtHargaJualAddKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaJualAddKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtHargaJualAddKeyTyped

    private void txtDiscountAddFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountAddFocusGained
        txtDiscountAdd.setText(Disc);
    }//GEN-LAST:event_txtDiscountAddFocusGained

    private void txtDiscountAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountAddFocusLost
        Disc =txtDiscountAdd.getText();            //Menyimpan di Variable Disc
        if (!txtDiscountAdd.getText().trim().isEmpty()){
            ut.AngkaToRp(txtDiscountAdd,Disc);
        }
    }//GEN-LAST:event_txtDiscountAddFocusLost

    private void txtDiscountAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Simpan();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtDiscountAdd.setText("");
        }
    }//GEN-LAST:event_txtDiscountAddKeyPressed

    private void txtDiscountAddKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountAddKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtDiscountAddKeyTyped

    private void PilihAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PilihAddMouseClicked
        TampilPilihAdd();
    }//GEN-LAST:event_PilihAddMouseClicked

    private void txtKodeAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtNamaAdd.setText(null);
            txtNamaAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKodeAdd.setText("");
        }
    }//GEN-LAST:event_txtKodeAddKeyPressed

    private void txtKodeAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKodeAddFocusLost
        SListAdd.setVisible(false);
        CekKodeAdd();
    }//GEN-LAST:event_txtKodeAddFocusLost

    private void txtKodeAddKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeAddKeyReleased
        if (txtKodeAdd.getText().trim().isEmpty()) {
            SListAdd.setVisible(false);
        } else{
            PopUpCodeAdd();
        }
    }//GEN-LAST:event_txtKodeAddKeyReleased

    private void txtNamaAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNamaAddFocusLost
        SListAdd.setVisible(false);
    }//GEN-LAST:event_txtNamaAddFocusLost

    private void txtNamaAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtKategoriAdd.setText(null);
            txtKategoriAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtNamaAdd.setText("");
        }
    }//GEN-LAST:event_txtNamaAddKeyPressed

    private void txtNamaAddKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaAddKeyReleased
        if (txtNamaAdd.getText().trim().isEmpty()) {
            SListAdd.setVisible(false);
        } else{
            PopUpNameAdd();
        }
        
    }//GEN-LAST:event_txtNamaAddKeyReleased

    private void txtKategoriAddFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKategoriAddFocusGained
        TampilKategoriAdd();
    }//GEN-LAST:event_txtKategoriAddFocusGained

    private void txtKategoriAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtSatuanAdd.setText(null);
            txtSatuanAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKategoriAdd.setText("");
        }
    }//GEN-LAST:event_txtKategoriAddKeyPressed

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
            Bersih();
    }//GEN-LAST:event_btnBersihActionPerformed

    private void PilihAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PilihAddKeyPressed
       if(evt.getKeyCode() == KeyEvent.VK_ENTER){
           TampilPilihAdd();
       }
    }//GEN-LAST:event_PilihAddKeyPressed

    private void btnTambahKategoriAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKategoriAddActionPerformed
        TambahKategori();
    }//GEN-LAST:event_btnTambahKategoriAddActionPerformed

    private void btnTambahSatuanAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSatuanAddActionPerformed
        TambahSatuan();
    }//GEN-LAST:event_btnTambahSatuanAddActionPerformed

    private void txtKategoriTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriTambahKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            SimpanKategori();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKategoriTambah.setText("");
        }
    }//GEN-LAST:event_txtKategoriTambahKeyPressed

    private void txtKategoriTambahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriTambahKeyReleased
        if (txtKategoriTambah.getText().trim().isEmpty()) {
            SListCategoryAdd.setVisible(false);
        } else{
            PopUpKategoriAdd();
        }
    }//GEN-LAST:event_txtKategoriTambahKeyReleased

    private void txtKategoriTambahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriTambahKeyTyped
        if (txtKategoriTambah.getText().length() >= 15 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtKategoriTambahKeyTyped

    private void txtSatuanTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanTambahKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            SimpanSatuan();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSatuanTambah.setText("");
        }
    }//GEN-LAST:event_txtSatuanTambahKeyPressed

    private void txtSatuanTambahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanTambahKeyReleased
        if (txtSatuanTambah.getText().trim().isEmpty()) {
            SListAdd.setVisible(false);
        } else{
            PopUpSatuan();
        }
    }//GEN-LAST:event_txtSatuanTambahKeyReleased

    private void txtSatuanTambahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanTambahKeyTyped
        if (txtSatuanTambah.getText().length() >= 15 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSatuanTambahKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog CategoryAdd;
    private javax.swing.JDialog ItemAdd;
    private javax.swing.JDialog ItemEdit;
    private javax.swing.JTable ListAdd;
    private javax.swing.JTable ListCategoryAdd;
    private javax.swing.JTable ListUnitAdd;
    private javax.swing.JTable PilihAdd;
    private javax.swing.JScrollPane SListAdd;
    private javax.swing.JScrollPane SListCategoryAdd;
    private javax.swing.JScrollPane SListUnitAdd;
    private javax.swing.JScrollPane SPilihAdd;
    private javax.swing.JScrollPane SPtableTampil;
    private javax.swing.JScrollPane StabelListEdit;
    private javax.swing.JScrollPane StabelPilihEdit;
    private javax.swing.JDialog UnitAdd;
    private Utility.UButton btnBersih;
    private Utility.UButton btnCloseAdd;
    private Utility.UButton btnCloseEdit;
    private Utility.UButton btnCloseKategori;
    private Utility.UButton btnCloseSatuan;
    private Utility.UButton btnEdit;
    private Utility.UButton btnHapus;
    private Utility.UButton btnSimpan;
    private Utility.UButton btnSimpanKategori;
    private Utility.UButton btnSimpanSatuan;
    private Utility.UButton btnTambah;
    private Utility.UButton btnTambahKategoriAdd;
    private Utility.UButton btnTambahSatuanAdd;
    private Utility.UButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPanel panelEH;
    private javax.swing.JTable tabelListEdit;
    private javax.swing.JTable tabelPilihEdit;
    private javax.swing.JTable tableTampil;
    private javax.swing.JTextField txtDiscountAdd;
    private javax.swing.JTextField txtDiscountEdit;
    private javax.swing.JTextField txtHargaBeliAdd;
    private javax.swing.JTextField txtHargaBeliEdit;
    private javax.swing.JTextField txtHargaJualAdd;
    private javax.swing.JTextField txtHargaJualEdit;
    private javax.swing.JTextField txtKategoriAdd;
    private javax.swing.JTextField txtKategoriEdit;
    private javax.swing.JTextField txtKategoriTambah;
    private javax.swing.JTextField txtKodeAdd;
    private javax.swing.JTextField txtKodeEdit;
    private javax.swing.JTextField txtNamaAdd;
    private javax.swing.JTextField txtNamaBarangEdit;
    private javax.swing.JTextField txtSatuanAdd;
    private javax.swing.JTextField txtSatuanEdit;
    private javax.swing.JTextField txtSatuanTambah;
    private javax.swing.JTextField txtSearch;
    private Utility.UPanelRoundrect uPanelRoundrect1;
    private Utility.UPanelRoundrect uPanelRoundrect2;
    private Utility.UPanelRoundrect uPanelRoundrect3;
    private Utility.UPanelRoundrect uPanelRoundrect4;
    private Utility.UPanelRoundrect uPanelRoundrect5;
    private Utility.UPanelRoundrect uPanelRoundrect6;
    private Utility.UPanelRoundrect uPanelRoundrect7;
    private Utility.UPanelRoundrect uPanelRoundrect8;
    // End of variables declaration//GEN-END:variables
}
