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
        StabelListAdd.setVisible(false);
        StabelListAdd.getViewport().setBackground(new Color(255,255,255));
        
        //Option Untuk Table yang bisa dipilih
        StabelPilihAdd.setVisible(false);
        StabelPilihAdd.getViewport().setBackground(new Color(255,255,255)); 
        
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



    
    public void Edit(){
        this.setVisible(false);
        TampilEdit();
        SwingUtilities.invokeLater(() -> { txtNamaBarangAdd.requestFocusInWindow(); });
        ItemEdit.setSize(791,477);                      
        ItemEdit.setLocation((Beranda.SW-791 )/2,(Beranda.SH-477 )/2);        
        ItemEdit.setVisible(true);
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
        ItemAdd.setSize(791,462);                     
        ItemAdd.setLocation(((Beranda.SW+120)-791 )/2,((Beranda.SH+50)-462 )/2);
        ItemAdd.setBackground(new Color(0, 0, 0, 0));        
        ItemAdd.setVisible(true); 
    }
     
    private void PopUpCodeAdd(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT item_id,item_code FROM items WHERE item_code LIKE '"+txtKodeAdd.getText()+"%'");
            tabelListAdd.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                //Utility_Table uts = new Utility_Table();
                uts.Header(tabelListAdd,0,"",-10);
                uts.Header(tabelListAdd,1,"",200);
                tabelListAdd.setBackground(new Color(255,255,255));
                tabelListAdd.setShowGrid(false);
                tabelListAdd.removeColumn(tabelListAdd.getColumnModel().getColumn(0));
                StabelListAdd.setLocation(txtKodeAdd.getX(), txtKodeAdd.getY()+25);
                
                if (rs.getRow() <= 15) {
                    StabelListAdd.setSize(340, (rs.getRow()*17)+2);
                } else{
                    StabelListAdd.setSize(340, (15*17)+2);                    
                }
                    StabelListAdd.setVisible(true); 
            } else {
                    StabelListAdd.setVisible(false);                
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
            java.sql.ResultSet rs = st.executeQuery("SELECT item_id,item_name FROM items WHERE item_name LIKE '"+txtNamaBarangAdd.getText()+"%'");
            tabelListAdd.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                //Utility_Table uts = new Utility_Table();
                uts.Header(tabelListAdd,0,"",-10);
                uts.Header(tabelListAdd,1,"",200);
                tabelListAdd.setBackground(new Color(255,255,255));
                tabelListAdd.setShowGrid(false);
                tabelListAdd.removeColumn(tabelListAdd.getColumnModel().getColumn(0));
                StabelListAdd.setLocation(txtNamaBarangAdd.getX(),txtNamaBarangAdd.getY()+25);
                
                if (rs.getRow() <= 15) {
                    StabelListAdd.setSize(340, (rs.getRow()*17)+2);
                } else{
                    StabelListAdd.setSize(340, (15*17)+2);                    
                }
                    StabelListAdd.setVisible(true); 
            } else {
                    StabelListAdd.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }


  
    private void TampilKategoriAdd(){
        StabelPilihAdd.setVisible(false);
        Pilihan ="kategori";
        try { 
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  category_id,category_name FROM icategory  ORDER BY category_name");
            tabelPilihAdd.setModel(DbUtils.resultSetToTableModel(rs));            
                if(rs.last()){   
                    //Utility_Table uts = new Utility_Table();                    
                    uts.Header(tabelPilihAdd,1,"",210);
                    tabelPilihAdd.removeColumn(tabelPilihAdd.getColumnModel().getColumn(0));//Tidak Menampilkan Kolom (0)
                    tabelPilihAdd.setBackground(new Color(255,255,255));
                    tabelPilihAdd.clearSelection();                     
                    tabelPilihAdd.changeSelection (0,0,true, false);                                      
                        if (rs.getRow() <= 15) {
                            StabelPilihAdd.setSize(210, (rs.getRow()*17)+2);
                        } else{
                            StabelPilihAdd.setSize(210, (15*17)+2);                    
                        }                  
                            StabelPilihAdd.setLocation(txtKategoriAdd.getX(), txtKategoriAdd.getY());
                            StabelPilihAdd.setVisible(true); 
                            tabelPilihAdd.requestFocus();
                } else {
                    StabelPilihAdd.setVisible(false);                
                }    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }      
    }

    private void TampilSatuanAdd(){
        StabelPilihAdd.setVisible(false);
        Pilihan = "Satuan";
        try {    
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  unit_id,unit_name FROM iunits  ORDER BY unit_name");
            tabelPilihAdd.setModel(DbUtils.resultSetToTableModel(rs));            
                if(rs.last()){   
                    //Utility_Table uts = new Utility_Table();                    
                    uts.Header(tabelPilihAdd,1,"",210);
                    tabelPilihAdd.removeColumn(tabelPilihAdd.getColumnModel().getColumn(0));//Tidak Menampilkan Kolom (0)
                    tabelPilihAdd.setBackground(new Color(255,255,255));
                    tabelPilihAdd.clearSelection();                     
                    tabelPilihAdd.changeSelection (0,0,false, false);                                      
                        if (rs.getRow() <= 15) {
                            StabelPilihAdd.setSize(210, (rs.getRow()*17)+2);
                        } else{
                            StabelPilihAdd.setSize(210, (15*17)+2);                    
                        }                  
                            StabelPilihAdd.setLocation(txtSatuanAdd.getX(),txtSatuanAdd.getY());
                            StabelPilihAdd.setVisible(true); 
                            tabelPilihAdd.requestFocus();
                } else {
                    StabelPilihAdd.setVisible(false);                
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

        if (txtKodeAdd.getText().trim().isEmpty() || txtNamaBarangAdd.getText().trim().isEmpty() || txtKategoriAdd.getText().trim().isEmpty() || txtSatuanAdd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Barang Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
                try{   
                        java.util.Date dt = new java.util.Date();
                        java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(dt);
                    
                        String sql ="INSERT INTO items(item_code,item_name,item_category,item_unit,"
                                + "item_bprice,item_sprice,item_sdiscount,item_created) "
                                + "VALUES ('"+txtKodeAdd.getText()+"','"+txtNamaBarangAdd.getText()+"','"+txtKategoriAdd.getText()+"',"
                                + "'"+txtSatuanAdd.getText()+"','"+HBeli+"','"+HJual+"','"+Disc+"','"+currentTime+"')";
                       
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                    
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data Barang Berhasil");
                        KeluarAdd();                  
                    }                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
        }         
    }
    
 
    private void TampilPilihAdd(){
        if("kategori".equals(Pilihan)){
            Dipilih= tabelPilihAdd.getModel().getValueAt(tabelPilihAdd.getSelectedRow(), 1).toString();
            txtKategoriAdd.setText(Dipilih);
            StabelPilihAdd.setVisible(false);
            txtSatuanAdd.requestFocus();
        }else{
            Dipilih= tabelPilihAdd.getModel().getValueAt(tabelPilihAdd.getSelectedRow(), 1).toString();
            txtSatuanAdd.setText(Dipilih);
            StabelPilihAdd.setVisible(false);
            txtHargaBeliAdd.requestFocus();
            
        }
    }
    

    

     

   
    private void Bersih(){
        StabelPilihAdd.setVisible(false);
        txtDiscountAdd.setText("");
        txtHargaBeliAdd.setText("");
        txtHargaJualAdd.setText("");       
        txtSatuanAdd.setText("");
        txtKategoriAdd.setText("");
        txtNamaBarangAdd.setText("");
        txtKodeAdd.setText("");
        txtKodeAdd.requestFocus();
    }
    
    private void KeluarAdd(){
        ItemAdd.dispose();
        this.setVisible(true);
        txtSearch.requestFocus();
    }

//---------------------------------------------------Item Edit---------------------------------------------------------

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
    

    private void TambahKategoriEdit(){
        Pemanggil="Edit";
        ItemEdit.setVisible(false); 
        txtKategoriTambah.setText(""); 
        SwingUtilities.invokeLater(() -> { txtKategoriTambah.requestFocusInWindow(); });
        CategoryAdd.setSize(421, 156);                    
        CategoryAdd.setLocation((Beranda.SW-421 )/2,(Beranda.SH-156 )/2);
        CategoryAdd.setVisible(true); 
    }
     
    private void TambahSatuanEdit(){
        Pemanggil="Edit";
        ItemEdit.setVisible(false); 
        txtSatuanTambah.setText("");  
        SwingUtilities.invokeLater(() -> { txtSatuanTambah.requestFocusInWindow(); });
        UnitAdd.setSize(421, 156);                    
        UnitAdd.setLocation((Beranda.SW-421 )/2,(Beranda.SH-156 )/2);
        UnitAdd.setVisible(true); 
    }   

    private void KeluarEdit(){        
        ItemEdit.dispose();
        this.setVisible(true);
        txtSearch.requestFocus();
    }
    
//------------------------------------CategoryAdd-------------------------------------------------------------
    
    private void KategoriFromItemAdd(){       
        txtKategoriTambah.setText("");  
        SwingUtilities.invokeLater(() -> { txtKategoriTambah.requestFocusInWindow(); });
        CategoryAdd.setSize(430, 190);                    
        CategoryAdd.setLocation(((Beranda.SW+120)-430 )/2,((Beranda.SH+50)-190 )/2);
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
        txtKategoriAdd.requestFocus();
        
    }
    
//---------------------------------------------------UnitAdd-----------------------------------------------------------   
    private void TambahSatuanAdd(){
        Pemanggil="Add";
        ItemAdd.setVisible(false); 
        txtSatuanTambah.setText(""); 
        SwingUtilities.invokeLater(() -> { txtSatuanTambah.requestFocusInWindow(); });
        UnitAdd.setSize(421, 156);                    
        UnitAdd.setLocation((Beranda.SW-421 )/2,(Beranda.SH-156 )/2);
        UnitAdd.setVisible(true); 
                txtKategoriTambah.setText("");  
        SwingUtilities.invokeLater(() -> { txtKategoriTambah.requestFocusInWindow(); });
        CategoryAdd.setSize(430, 190);                    
        CategoryAdd.setLocation(((Beranda.SW+120)-430 )/2,((Beranda.SH+50)-190 )/2);
        CategoryAdd.setBackground(new Color(0, 0, 0, 0));
        CategoryAdd.setVisible(true); 
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
        if("Add".equals(Pemanggil)){
            SwingUtilities.invokeLater(() -> { txtKodeAdd.requestFocusInWindow(); });                                          
            ItemAdd.setSize(791,477);                    
            ItemAdd.setLocation((Beranda.SW-791 )/2,(Beranda.SH-477 )/2);
            ItemAdd.setVisible(true);
        }else{
            SwingUtilities.invokeLater(() -> { txtNamaBarangEdit.requestFocusInWindow(); });                                         
            ItemEdit.setSize(791,477);                    
            ItemEdit.setLocation((Beranda.SW-791 )/2,(Beranda.SH-477 )/2);
            ItemEdit.setVisible(true); 
        }        
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
        StabelPilihAdd = new javax.swing.JScrollPane();
        tabelPilihAdd = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        StabelListAdd = new javax.swing.JScrollPane();
        tabelListAdd =  new javax.swing.JTable(){
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
        btnBersih = new Utility.UButton();
        btnSimpan = new Utility.UButton();
        txtKodeAdd = new javax.swing.JTextField();
        txtNamaBarangAdd = new javax.swing.JTextField();
        txtKategoriAdd = new javax.swing.JTextField();
        txtSatuanAdd = new javax.swing.JTextField();
        txtHargaBeliAdd = new javax.swing.JTextField();
        txtHargaJualAdd = new javax.swing.JTextField();
        txtDiscountAdd = new javax.swing.JTextField();
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
        ItemAdd.setPreferredSize(new java.awt.Dimension(791, 462));
        ItemAdd.setSize(new java.awt.Dimension(791, 462));
        ItemAdd.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect1.setKetebalanBorder(2.5F);
        uPanelRoundrect1.setPreferredSize(new java.awt.Dimension(800, 500));
        uPanelRoundrect1.setWarnaBackground(new java.awt.Color(87, 176, 86));
        uPanelRoundrect1.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect2.setKetebalanBorder(2.5F);
        uPanelRoundrect2.setPreferredSize(new java.awt.Dimension(780, 420));
        uPanelRoundrect2.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        StabelPilihAdd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        StabelPilihAdd.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        StabelPilihAdd.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        StabelPilihAdd.setFocusable(false);

        tabelPilihAdd.setForeground(new java.awt.Color(51, 51, 51));
        tabelPilihAdd.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelPilihAdd.setGridColor(new java.awt.Color(255, 255, 255));
        tabelPilihAdd.setShowGrid(false);
        tabelPilihAdd.setSurrendersFocusOnKeystroke(true);
        tabelPilihAdd.setTableHeader(null
        );
        tabelPilihAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPilihAddMouseClicked(evt);
            }
        });
        tabelPilihAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelPilihAddKeyPressed(evt);
            }
        });
        StabelPilihAdd.setViewportView(tabelPilihAdd);

        uPanelRoundrect2.add(StabelPilihAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, 170, 0));

        StabelListAdd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        StabelListAdd.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        StabelListAdd.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        StabelListAdd.setFocusable(false);

        tabelListAdd.setForeground(new java.awt.Color(153, 153, 153));
        tabelListAdd.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelListAdd.setGridColor(new java.awt.Color(255, 255, 255));
        tabelListAdd.setShowGrid(false);
        tabelListAdd.setSurrendersFocusOnKeystroke(true);
        tabelListAdd.setTableHeader(null
        );
        StabelListAdd.setViewportView(tabelListAdd);

        uPanelRoundrect2.add(StabelListAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 340, 0));

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Kode");
        jLabel4.setToolTipText(null);
        uPanelRoundrect2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 335, 10));

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Nama Barang");
        jLabel5.setToolTipText(null);
        uPanelRoundrect2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 20));

        jSeparator4.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 580, 10));

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Kategori");
        jLabel6.setToolTipText(null);
        uPanelRoundrect2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 20));

        jSeparator5.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 220, -1));

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Satuan");
        jLabel7.setToolTipText(null);
        uPanelRoundrect2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 76, 20));

        jSeparator6.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 220, 10));

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Harga Beli");
        jLabel9.setToolTipText(null);
        uPanelRoundrect2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, 20));

        jSeparator9.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 130, 10));

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Harga Jual");
        jLabel10.setToolTipText(null);
        uPanelRoundrect2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, 20));

        jSeparator10.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 130, 10));

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Discount");
        jLabel8.setToolTipText(null);
        uPanelRoundrect2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, -1, 20));

        jSeparator11.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, 130, 10));

        btnBersih.setMnemonic('h');
        btnBersih.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnBersih.setKetebalanBorder(2.5F);
        btnBersih.setKetumpulanSudut(35);
        btnBersih.setLabel("Bersih");
        btnBersih.setPreferredSize(new java.awt.Dimension(120, 38));
        btnBersih.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnBersih.setWarnaBackgroundHover(new java.awt.Color(255, 231, 112));
        btnBersih.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnBersih.setWarnaBorder(new java.awt.Color(255, 231, 112));
        btnBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBersihActionPerformed(evt);
            }
        });
        uPanelRoundrect2.add(btnBersih, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 360, -1, -1));

        btnSimpan.setMnemonic('s');
        btnSimpan.setText("Simpan");
        btnSimpan.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnSimpan.setKetebalanBorder(2.5F);
        btnSimpan.setKetumpulanSudut(35);
        btnSimpan.setPreferredSize(new java.awt.Dimension(150, 38));
        btnSimpan.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        uPanelRoundrect2.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 360, -1, -1));

        txtKodeAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKodeAdd.setForeground(new java.awt.Color(204, 204, 204));
        txtKodeAdd.setToolTipText(null);
        txtKodeAdd.setBorder(null);
        txtKodeAdd.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtKodeAdd.setOpaque(false);
        uPanelRoundrect2.add(txtKodeAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 335, -1));

        txtNamaBarangAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtNamaBarangAdd.setToolTipText(null);
        txtNamaBarangAdd.setBorder(null);
        txtNamaBarangAdd.setOpaque(false);
        txtNamaBarangAdd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNamaBarangAddFocusLost(evt);
            }
        });
        txtNamaBarangAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaBarangAddKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNamaBarangAddKeyReleased(evt);
            }
        });
        uPanelRoundrect2.add(txtNamaBarangAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 500, -1));

        txtKategoriAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKategoriAdd.setToolTipText(null);
        txtKategoriAdd.setBorder(null);
        txtKategoriAdd.setOpaque(false);
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
        uPanelRoundrect2.add(txtKategoriAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 190, -1));

        txtSatuanAdd.setEditable(false);
        txtSatuanAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtSatuanAdd.setToolTipText(null);
        txtSatuanAdd.setBorder(null);
        txtSatuanAdd.setOpaque(false);
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
        uPanelRoundrect2.add(txtSatuanAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 190, -1));

        txtHargaBeliAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtHargaBeliAdd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHargaBeliAdd.setToolTipText(null);
        txtHargaBeliAdd.setBorder(null);
        txtHargaBeliAdd.setOpaque(false);
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
        uPanelRoundrect2.add(txtHargaBeliAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 130, -1));

        txtHargaJualAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtHargaJualAdd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHargaJualAdd.setToolTipText(null);
        txtHargaJualAdd.setBorder(null);
        txtHargaJualAdd.setOpaque(false);
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
        uPanelRoundrect2.add(txtHargaJualAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, 130, -1));

        txtDiscountAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtDiscountAdd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscountAdd.setToolTipText(null);
        txtDiscountAdd.setBorder(null);
        txtDiscountAdd.setOpaque(false);
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
        uPanelRoundrect2.add(txtDiscountAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 130, -1));

        uPanelRoundrect1.add(uPanelRoundrect2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tambah Barang");
        uPanelRoundrect1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        btnCloseAdd.setMnemonic('c');
        btnCloseAdd.setText("Close");
        btnCloseAdd.setWarnaBorder(new java.awt.Color(164, 253, 163));
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

        uPanelRoundrect3.setKetebalanBorder(2.5F);
        uPanelRoundrect3.setPreferredSize(new java.awt.Dimension(800, 500));
        uPanelRoundrect3.setWarnaBackground(new java.awt.Color(235, 154, 35));
        uPanelRoundrect3.setWarnaBorder(new java.awt.Color(255, 231, 112));
        uPanelRoundrect3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect4.setKetebalanBorder(2.5F);
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

        txtKodeEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKodeEdit.setForeground(new java.awt.Color(204, 204, 204));
        txtKodeEdit.setToolTipText(null);
        txtKodeEdit.setBorder(null);
        txtKodeEdit.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtKodeEdit.setOpaque(false);
        uPanelRoundrect4.add(txtKodeEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 335, -1));

        jSeparator7.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 335, 10));

        jLabel13.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Nama Barang");
        jLabel13.setToolTipText(null);
        uPanelRoundrect4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, 20));

        txtNamaBarangEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtNamaBarangEdit.setToolTipText(null);
        txtNamaBarangEdit.setBorder(null);
        txtNamaBarangEdit.setOpaque(false);
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

        txtKategoriEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKategoriEdit.setToolTipText(null);
        txtKategoriEdit.setBorder(null);
        txtKategoriEdit.setOpaque(false);
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
        txtSatuanEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtSatuanEdit.setToolTipText(null);
        txtSatuanEdit.setBorder(null);
        txtSatuanEdit.setOpaque(false);
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

        txtHargaBeliEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtHargaBeliEdit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHargaBeliEdit.setToolTipText(null);
        txtHargaBeliEdit.setBorder(null);
        txtHargaBeliEdit.setOpaque(false);
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

        txtHargaJualEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtHargaJualEdit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHargaJualEdit.setToolTipText(null);
        txtHargaJualEdit.setBorder(null);
        txtHargaJualEdit.setOpaque(false);
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

        txtDiscountEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtDiscountEdit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscountEdit.setToolTipText(null);
        txtDiscountEdit.setBorder(null);
        txtDiscountEdit.setOpaque(false);
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

        uPanelRoundrect3.add(uPanelRoundrect4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Edit Barang");
        uPanelRoundrect3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        btnCloseEdit.setMnemonic('c');
        btnCloseEdit.setText("Close");
        btnCloseEdit.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnCloseEdit.setWarnaBackgroundHover(new java.awt.Color(255, 255, 255));
        btnCloseEdit.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnCloseEdit.setWarnaBorder(new java.awt.Color(255, 180, 61));
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
        uPanelRoundrect5.setPreferredSize(new java.awt.Dimension(450, 190));
        uPanelRoundrect5.setWarnaBackground(new java.awt.Color(87, 176, 86));
        uPanelRoundrect5.setWarnaBorder(new java.awt.Color(164, 253, 163));

        uPanelRoundrect6.setKetebalanBorder(2.5F);
        uPanelRoundrect6.setPreferredSize(new java.awt.Dimension(430, 120));
        uPanelRoundrect6.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSimpanKategori.setMnemonic('s');
        btnSimpanKategori.setText("Simpan");
        btnSimpanKategori.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnSimpanKategori.setKetebalanBorder(2.5F);
        btnSimpanKategori.setKetumpulanSudut(35);
        btnSimpanKategori.setPreferredSize(new java.awt.Dimension(120, 32));
        btnSimpanKategori.setPress(true);
        btnSimpanKategori.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnSimpanKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanKategoriActionPerformed(evt);
            }
        });
        uPanelRoundrect6.add(btnSimpanKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(288, 77, -1, -1));

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
        txtKategoriTambah.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtKategoriTambahFocusGained(evt);
            }
        });
        txtKategoriTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKategoriTambahKeyPressed(evt);
            }
        });
        uPanelRoundrect6.add(txtKategoriTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 270, -1));

        jLabel19.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Tambah Kategori");

        btnCloseKategori.setText("Close");
        btnCloseKategori.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnCloseKategori.setWarnaBorder(new java.awt.Color(113, 202, 112));
        btnCloseKategori.setWarnaForegroundHover(new java.awt.Color(255, 0, 0));
        btnCloseKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseKategoriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout uPanelRoundrect5Layout = new javax.swing.GroupLayout(uPanelRoundrect5);
        uPanelRoundrect5.setLayout(uPanelRoundrect5Layout);
        uPanelRoundrect5Layout.setHorizontalGroup(
            uPanelRoundrect5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uPanelRoundrect5Layout.createSequentialGroup()
                .addGroup(uPanelRoundrect5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(uPanelRoundrect5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(uPanelRoundrect6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(uPanelRoundrect5Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCloseKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        uPanelRoundrect5Layout.setVerticalGroup(
            uPanelRoundrect5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uPanelRoundrect5Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(uPanelRoundrect5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCloseKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uPanelRoundrect6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        CategoryAdd.getContentPane().add(uPanelRoundrect5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        UnitAdd.setModal(true);
        UnitAdd.setUndecorated(true);
        UnitAdd.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect7.setKetebalanBorder(2.5F);
        uPanelRoundrect7.setPreferredSize(new java.awt.Dimension(450, 190));
        uPanelRoundrect7.setWarnaBackground(new java.awt.Color(87, 176, 86));
        uPanelRoundrect7.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect8.setKetebalanBorder(2.5F);
        uPanelRoundrect8.setPreferredSize(new java.awt.Dimension(430, 120));
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
        uPanelRoundrect8.add(btnSimpanSatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, -1, -1));

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

        txtSatuanTambah.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtSatuanTambah.setToolTipText(null);
        txtSatuanTambah.setBorder(null);
        txtSatuanTambah.setOpaque(false);
        txtSatuanTambah.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSatuanTambahFocusGained(evt);
            }
        });
        txtSatuanTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSatuanTambahKeyPressed(evt);
            }
        });
        uPanelRoundrect8.add(txtSatuanTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 220, -1));

        uPanelRoundrect7.add(uPanelRoundrect8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 59, -1, -1));

        jLabel21.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Tambah Satuan");
        uPanelRoundrect7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        btnCloseSatuan.setMnemonic('c');
        btnCloseSatuan.setText("Close");
        btnCloseSatuan.setWarnaBorder(new java.awt.Color(113, 202, 112));
        btnCloseSatuan.setWarnaForegroundHover(new java.awt.Color(255, 0, 0));
        btnCloseSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseSatuanActionPerformed(evt);
            }
        });
        uPanelRoundrect7.add(btnCloseSatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, -1));

        UnitAdd.getContentPane().add(uPanelRoundrect7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1246, 714));

        jPanel1.setBackground(new java.awt.Color(248, 251, 251));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelEH.setOpaque(false);
        panelEH.setPreferredSize(new java.awt.Dimension(130, 30));
        panelEH.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(panelEH, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 130, 0));

        SPtableTampil.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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
        btnTambah.setKetebalanBorder(2.5F);
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

    private void btnTambahKategoriEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKategoriEditActionPerformed
        TambahKategoriEdit();
    }//GEN-LAST:event_btnTambahKategoriEditActionPerformed

    private void btnTambahSatuanEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSatuanEditActionPerformed
        TambahSatuanEdit();
    }//GEN-LAST:event_btnTambahSatuanEditActionPerformed

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

    private void btnCategoryFIAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoryFIAddActionPerformed
        KategoriFromItemAdd();
    }//GEN-LAST:event_btnCategoryFIAddActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        Simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
        Bersih();
    }//GEN-LAST:event_btnBersihActionPerformed

    private void btnSimpanSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanSatuanActionPerformed
        SimpanSatuan();
    }//GEN-LAST:event_btnSimpanSatuanActionPerformed

    private void btnCloseSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseSatuanActionPerformed
        KeluarSatuan();
    }//GEN-LAST:event_btnCloseSatuanActionPerformed

    private void txtNamaBarangAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNamaBarangAddFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaBarangAddFocusLost

    private void txtNamaBarangAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaBarangAddKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaBarangAddKeyPressed

    private void txtNamaBarangAddKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaBarangAddKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaBarangAddKeyReleased

    private void txtKategoriAddFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKategoriAddFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKategoriAddFocusGained

    private void txtKategoriAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriAddKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKategoriAddKeyPressed

    private void txtSatuanAddFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSatuanAddFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSatuanAddFocusGained

    private void txtSatuanAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanAddKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSatuanAddKeyPressed

    private void txtHargaBeliAddFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaBeliAddFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaBeliAddFocusGained

    private void txtHargaBeliAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaBeliAddFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaBeliAddFocusLost

    private void txtHargaBeliAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaBeliAddKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaBeliAddKeyPressed

    private void txtHargaBeliAddKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaBeliAddKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaBeliAddKeyTyped

    private void txtHargaJualAddFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaJualAddFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaJualAddFocusGained

    private void txtHargaJualAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaJualAddFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaJualAddFocusLost

    private void txtHargaJualAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaJualAddKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaJualAddKeyPressed

    private void txtHargaJualAddKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaJualAddKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaJualAddKeyTyped

    private void txtDiscountAddFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountAddFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscountAddFocusGained

    private void txtDiscountAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountAddFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscountAddFocusLost

    private void txtDiscountAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountAddKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscountAddKeyPressed

    private void txtDiscountAddKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountAddKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscountAddKeyTyped

    private void txtKategoriTambahFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKategoriTambahFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKategoriTambahFocusGained

    private void txtKategoriTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriTambahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKategoriTambahKeyPressed

    private void txtSatuanTambahFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSatuanTambahFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSatuanTambahFocusGained

    private void txtSatuanTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanTambahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSatuanTambahKeyPressed

    private void tabelPilihAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPilihAddMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelPilihAddMouseClicked

    private void tabelPilihAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelPilihAddKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelPilihAddKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog CategoryAdd;
    private javax.swing.JDialog ItemAdd;
    private javax.swing.JDialog ItemEdit;
    private javax.swing.JTable ListCategoryAdd;
    private javax.swing.JTable ListUnitAdd;
    private javax.swing.JScrollPane SListCategoryAdd;
    private javax.swing.JScrollPane SListUnitAdd;
    private javax.swing.JScrollPane SPtableTampil;
    private javax.swing.JScrollPane StabelListAdd;
    private javax.swing.JScrollPane StabelListEdit;
    private javax.swing.JScrollPane StabelPilihAdd;
    private javax.swing.JScrollPane StabelPilihEdit;
    private javax.swing.JDialog UnitAdd;
    private Utility.UButton btnBersih;
    private Utility.UButton btnCloseAdd;
    private Utility.UButton btnCloseEdit;
    private Utility.UButton btnCloseKategori;
    private Utility.UButton btnCloseSatuan;
    private Utility.UButton btnSimpan;
    private Utility.UButton btnSimpanKategori;
    private Utility.UButton btnSimpanSatuan;
    private Utility.UButton btnTambah;
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
    private javax.swing.JTable tabelListAdd;
    private javax.swing.JTable tabelListEdit;
    private javax.swing.JTable tabelPilihAdd;
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
    private javax.swing.JTextField txtNamaBarangAdd;
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
