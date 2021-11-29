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
    java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
    private static String  Id;
    private String Pilihan,HBeli,HJual,Disc,Dipilih, Pemanggil;
    DecimalFormat desimalFormat;    
    Utility_Text ut = new Utility_Text();
    Utility_Table uts = new Utility_Table();
    
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
        DocumentFilter filter = new Utility_Text();
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


    private void Tambah(){
        this.setVisible(false);
        txtKodeAdd.setText("");
        BersihAdd();        
        SwingUtilities.invokeLater(() -> { txtKodeAdd.requestFocusInWindow(); });
        ItemAdd.setSize(791,477);                     
        ItemAdd.setLocation(((Beranda.PW+120)-791 )/2,((Beranda.PH+50)-477 )/2);        
        ItemAdd.setVisible(true); 
    }
    
    public void Edit(){
        this.setVisible(false);
        TampilEdit();
        SwingUtilities.invokeLater(() -> { txtNamaBarangAdd.requestFocusInWindow(); });
        ItemEdit.setSize(791,477);                      
        ItemEdit.setLocation(((Beranda.PW+120)-791 )/2,((Beranda.PH+50)-477 )/2);        
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
                    + "item_stock, item_unit,item_bprice,item_sprice, item_discount,item_created, item_update FROM items"
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
                    + "item_stock, item_unit,item_bprice,item_sprice, item_discount,item_created, item_update FROM items WHERE item_delete = 0 ");
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
            uts.Header(tableTampil,7,"Discount",50);
            uts.Header(tableTampil,8,"Date Created",120);
            uts.Header(tableTampil,9,"Date Update",120);
             
            tableTampil.getColumnModel().getColumn(3).setCellRenderer(uts.formatAngka);
            tableTampil.getColumnModel().getColumn(5).setCellRenderer(uts.formatAngka);
            tableTampil.getColumnModel().getColumn(6).setCellRenderer(uts.formatAngka);
            tableTampil.getColumnModel().getColumn(7).setCellRenderer(uts.formatAngka);
            tableTampil.getColumnModel().getColumn(8).setCellRenderer(uts.formatTanggal);
            tableTampil.getColumnModel().getColumn(9).setCellRenderer(uts.formatTanggal);
            
            tableTampil.removeColumn(tableTampil.getColumnModel().getColumn(0)); //tidak menampilkan kolom (index:0)
    }
     
     //-------------------------------------Item Add--------------------------------------------------------------------------------------
     
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
                StabelListAdd.setLocation(130, 130);
                
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
                StabelListAdd.setLocation(130, 180);
                
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
                            StabelPilihAdd.setLocation(130, 200);
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
                            StabelPilihAdd.setLocation(130, 250);
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
                                + "item_bprice,item_sprice,item_discount,item_created) "
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
    

    
    private void TambahKategoriAdd(){
        Pemanggil="Add";
        ItemAdd.setVisible(false); 
        txtKategoriTambah.setText("");  
        SwingUtilities.invokeLater(() -> { txtKategoriTambah.requestFocusInWindow(); });
        CategoryAdd.setSize(421, 156);                    
        CategoryAdd.setLocation(((Beranda.PW+120)-421 )/2,((Beranda.PH+50)-156 )/2);
        CategoryAdd.setVisible(true); 
    }
     
    private void TambahSatuanAdd(){
        Pemanggil="Add";
        ItemAdd.setVisible(false); 
        txtSatuanTambah.setText(""); 
        SwingUtilities.invokeLater(() -> { txtSatuanTambah.requestFocusInWindow(); });
        UnitAdd.setSize(421, 156);                    
        UnitAdd.setLocation(((Beranda.PW+120)-421 )/2,((Beranda.PH+50)-156 )/2);
        UnitAdd.setVisible(true); 
    }
   
    private void BersihAdd(){
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
                    ut.AngkaToRp(txtDiscountEdit,rs.getString("item_discount"));
                    
                    HBeli=rs.getString("item_bprice");
                    HJual=rs.getString("item_sprice");
                    Disc=rs.getString("item_discount");
                    
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
                                + "item_Discount='"+Disc+"'    WHERE item_id='"+Id+"' "; 
                       // String sql ="INSERT INTO items(item_code,item_name,item_category,item_unit,item_sprice,item_bprice,item_discount,item_created) "
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
        CategoryAdd.setLocation(((Beranda.PW+120)-421 )/2,((Beranda.PH+50)-156 )/2);
        CategoryAdd.setVisible(true); 
    }
     
    private void TambahSatuanEdit(){
        Pemanggil="Edit";
        ItemEdit.setVisible(false); 
        txtSatuanTambah.setText("");  
        SwingUtilities.invokeLater(() -> { txtSatuanTambah.requestFocusInWindow(); });
        UnitAdd.setSize(421, 156);                    
        UnitAdd.setLocation(((Beranda.PW+120)-421 )/2,((Beranda.PH+50)-156 )/2);
        UnitAdd.setVisible(true); 
    }   

    private void KeluarEdit(){        
        ItemEdit.dispose();
        this.setVisible(true);
        txtSearch.requestFocus();
    }
    
//------------------------------------CategoryAdd-------------------------------------------------------------
   private void PopUpKategoriAdd(){
        try {            
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT category_id,category_name FROM icategory "
                    + "WHERE category_name LIKE '"+txtKategoriTambah.getText()+"%'");
            ListAdd.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                ListAdd.setBackground(new Color(255,255,255));
                ListAdd.setShowGrid(false);
                ListAdd.removeColumn(ListAdd.getColumnModel().getColumn(0));
                
                if (rs.getRow() <= 3) {
                    SListAdd.setSize(270, (rs.getRow()*17)+2);
                } else{
                    SListAdd.setSize(270, (3*17)+2);                    
                }
                    SListAdd.setVisible(true); 
            } else {
                    SListAdd.setVisible(false);                
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
        if("Add".equals(Pemanggil)){
            SwingUtilities.invokeLater(() -> { txtKodeAdd.requestFocusInWindow(); });                             
            ItemAdd.setSize(791,477);                    
            ItemAdd.setLocation(((Beranda.PW+120)-791 )/2,((Beranda.PH+50)-477 )/2);
            ItemAdd.setVisible(true);    
        }else{
            SwingUtilities.invokeLater(() -> { txtNamaBarangEdit.requestFocusInWindow(); });                                         
            ItemEdit.setSize(791,477);                    
            ItemEdit.setLocation(((Beranda.PW+120)-791 )/2,((Beranda.PH+50)-477 )/2);
            ItemEdit.setVisible(true); 
        }
        
    }
    
//---------------------------------------------------UnitAdd-----------------------------------------------------------   
       private void PopUpSatuan(){
        try {           
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT unit_id,unit_name FROM iunits WHERE unit_name LIKE '"+txtSatuanTambah.getText()+"%'");
            ListUnit.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                ListUnit.setBackground(new Color(255,255,255));
                ListUnit.setShowGrid(false);
                ListUnit.removeColumn(ListUnit.getColumnModel().getColumn(0));
                
                if (rs.getRow() <= 3) {
                    SListUnit.setSize(200, (rs.getRow()*17)+2);
                } else{
                    SListUnit.setSize(200, (3*17)+2);                    
                }
                    SListUnit.setVisible(true); 
            } else {
                    SListUnit.setVisible(false);                
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
            ItemAdd.setLocation(((Beranda.PW+120)-791 )/2,((Beranda.PH+50)-477 )/2);
            ItemAdd.setVisible(true);
        }else{
            SwingUtilities.invokeLater(() -> { txtNamaBarangEdit.requestFocusInWindow(); });                                         
            ItemEdit.setSize(791,477);                    
            ItemEdit.setLocation(((Beranda.PW+120)-791 )/2,((Beranda.PH+50)-477 )/2);
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
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnCloseAdd = new khansapos.Utility_ButtonMetro();
        StabelListAdd = new javax.swing.JScrollPane();
        tabelListAdd =  new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        StabelPilihAdd = new javax.swing.JScrollPane();
        tabelPilihAdd = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        txtKodeAdd = new javax.swing.JTextField();
        txtNamaBarangAdd = new javax.swing.JTextField();
        txtKategoriAdd = new javax.swing.JTextField();
        txtSatuanAdd = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtHargaBeliAdd = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        txtHargaJualAdd = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        txtDiscountAdd = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        btnSimpan = new khansapos.Utility_ButtonFlat();
        btnBersih = new khansapos.Utility_ButtonFlat();
        btnTambahKategoriAdd = new khansapos.Utility_ButtonFlat();
        btnTambahSatuanAdd = new khansapos.Utility_ButtonFlat();
        ItemEdit = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btnCloseEdit = new khansapos.Utility_ButtonMetro();
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
        txtKodeEdit = new javax.swing.JTextField();
        txtNamaBarangEdit = new javax.swing.JTextField();
        txtKategoriEdit = new javax.swing.JTextField();
        txtSatuanEdit = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtHargaBeliEdit = new javax.swing.JTextField();
        jSeparator14 = new javax.swing.JSeparator();
        txtHargaJualEdit = new javax.swing.JTextField();
        jSeparator15 = new javax.swing.JSeparator();
        txtDiscountEdit = new javax.swing.JTextField();
        jSeparator16 = new javax.swing.JSeparator();
        btnUpdate = new khansapos.Utility_ButtonFlat();
        btnTambahKategoriEdit = new khansapos.Utility_ButtonFlat();
        btnTambahSatuanEdit = new khansapos.Utility_ButtonFlat();
        CategoryAdd = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        txtKategoriTambah = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        btnCloseKategori = new khansapos.Utility_ButtonMetro();
        SListAdd = new javax.swing.JScrollPane();
        ListAdd = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jSeparator17 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        btnSimpanKategori = new khansapos.Utility_ButtonFlat();
        UnitAdd = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        btnCloseUnit = new khansapos.Utility_ButtonMetro();
        jLabel22 = new javax.swing.JLabel();
        txtSatuanTambah = new javax.swing.JTextField();
        SListUnit = new javax.swing.JScrollPane();
        ListUnit = new javax.swing.JTable();
        jSeparator18 = new javax.swing.JSeparator();
        btnSimpanUnit = new khansapos.Utility_ButtonFlat();
        jPanel1 = new javax.swing.JPanel();
        panelEH = new javax.swing.JPanel();
        btnEdit = new khansapos.Utility_ButtonFlat();
        btnHapus = new khansapos.Utility_ButtonFlat();
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
        btnTambah = new khansapos.Utility_ButtonFlat();

        ItemAdd.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ItemAdd.setMinimumSize(new java.awt.Dimension(791, 477));
        ItemAdd.setModal(true);
        ItemAdd.setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(87, 176, 86)));
        jPanel2.setPreferredSize(new java.awt.Dimension(791, 477));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(87, 176, 86));

        jLabel3.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tambah Barang");

        btnCloseAdd.setMnemonic('c');
        btnCloseAdd.setText("Close");
        btnCloseAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnCloseAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 492, Short.MAX_VALUE)
                .addComponent(btnCloseAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnCloseAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, -1));

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

        jPanel2.add(StabelListAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 340, 0));

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

        jPanel2.add(StabelPilihAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 170, 0));

        txtKodeAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKodeAdd.setToolTipText(null);
        txtKodeAdd.setBorder(null);
        txtKodeAdd.setOpaque(false);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKodeAddKeyTyped(evt);
            }
        });
        jPanel2.add(txtKodeAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 335, -1));

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
        jPanel2.add(txtNamaBarangAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 500, -1));

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
        jPanel2.add(txtKategoriAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 190, -1));

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
        jPanel2.add(txtSatuanAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 190, -1));

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Kode");
        jLabel4.setToolTipText(null);
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 101, -1, 20));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 335, 10));

        jLabel5.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Nama Barang");
        jLabel5.setToolTipText(null);
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 149, -1, 20));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 580, 10));

        jLabel6.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Kategori");
        jLabel6.setToolTipText(null);
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 197, -1, 20));
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 220, -1));

        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Satuan");
        jLabel7.setToolTipText(null);
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 245, 76, 20));
        jPanel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 220, 10));

        jLabel8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Discount");
        jLabel8.setToolTipText(null);
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, 20));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 265, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel9.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Harga Beli");
        jLabel9.setToolTipText(null);
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, 20));

        jLabel10.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Harga Jual");
        jLabel10.setToolTipText(null);
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, 20));

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
        jPanel2.add(txtHargaBeliAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 130, -1));
        jPanel2.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 130, 10));

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
        jPanel2.add(txtHargaJualAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, 130, -1));
        jPanel2.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 130, 10));

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
        jPanel2.add(txtDiscountAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 400, 130, -1));
        jPanel2.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 130, 10));

        btnSimpan.setMnemonic('s');
        btnSimpan.setText("Simpan");
        btnSimpan.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnSimpan.setMousePress(new java.awt.Color(204, 204, 204));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel2.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 210, 30));

        btnBersih.setMnemonic('b');
        btnBersih.setText("Bersih");
        btnBersih.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnBersih.setMouseHover(new java.awt.Color(255, 180, 61));
        btnBersih.setMousePress(new java.awt.Color(204, 204, 204));
        btnBersih.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBersihActionPerformed(evt);
            }
        });
        jPanel2.add(btnBersih, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 420, 90, 30));

        btnTambahKategoriAdd.setMnemonic('i');
        btnTambahKategoriAdd.setText("+");
        btnTambahKategoriAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnTambahKategoriAdd.setMousePress(new java.awt.Color(204, 204, 204));
        btnTambahKategoriAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKategoriAddActionPerformed(evt);
            }
        });
        jPanel2.add(btnTambahKategoriAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, -1, 30));

        btnTambahSatuanAdd.setMnemonic('n');
        btnTambahSatuanAdd.setText("+");
        btnTambahSatuanAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnTambahSatuanAdd.setMousePress(new java.awt.Color(204, 204, 204));
        btnTambahSatuanAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSatuanAddActionPerformed(evt);
            }
        });
        jPanel2.add(btnTambahSatuanAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 240, -1, 30));

        javax.swing.GroupLayout ItemAddLayout = new javax.swing.GroupLayout(ItemAdd.getContentPane());
        ItemAdd.getContentPane().setLayout(ItemAddLayout);
        ItemAddLayout.setHorizontalGroup(
            ItemAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        ItemAddLayout.setVerticalGroup(
            ItemAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        ItemEdit.setModal(true);
        ItemEdit.setUndecorated(true);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(235, 154, 35)));
        jPanel4.setPreferredSize(new java.awt.Dimension(791, 477));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(235, 154, 35));

        jLabel11.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Edit Barang");

        btnCloseEdit.setMnemonic('c');
        btnCloseEdit.setText("Close");
        btnCloseEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnCloseEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 544, Short.MAX_VALUE)
                .addComponent(btnCloseEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnCloseEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, -1));

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

        jPanel4.add(StabelListEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 340, 0));

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

        jPanel4.add(StabelPilihEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 170, 0));

        txtKodeEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKodeEdit.setForeground(new java.awt.Color(204, 204, 204));
        txtKodeEdit.setToolTipText(null);
        txtKodeEdit.setBorder(null);
        txtKodeEdit.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtKodeEdit.setOpaque(false);
        jPanel4.add(txtKodeEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 335, -1));

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
        jPanel4.add(txtNamaBarangEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 500, -1));

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
        jPanel4.add(txtKategoriEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 190, -1));

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
        jPanel4.add(txtSatuanEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 190, -1));

        jLabel12.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Kode");
        jLabel12.setToolTipText(null);
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 101, -1, 20));
        jPanel4.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 335, 10));

        jLabel13.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Nama Barang");
        jLabel13.setToolTipText(null);
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 149, -1, 20));
        jPanel4.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 580, 10));

        jLabel14.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("Kategori");
        jLabel14.setToolTipText(null);
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 197, -1, 20));
        jPanel4.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 220, -1));

        jLabel15.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setText("Satuan");
        jLabel15.setToolTipText(null);
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 245, 76, 20));
        jPanel4.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 220, 10));

        jLabel16.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Discount");
        jLabel16.setToolTipText(null);
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, 20));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 265, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel17.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("Harga Beli");
        jLabel17.setToolTipText(null);
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, 20));

        jLabel18.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setText("Harga Jual");
        jLabel18.setToolTipText(null);
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, 20));

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
        jPanel4.add(txtHargaBeliEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 130, -1));
        jPanel4.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 130, 10));

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
        jPanel4.add(txtHargaJualEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, 130, -1));
        jPanel4.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 130, 10));

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
        jPanel4.add(txtDiscountEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 400, 130, -1));
        jPanel4.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 130, 10));

        btnUpdate.setMnemonic('s');
        btnUpdate.setText("Update");
        btnUpdate.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnUpdate.setMouseHover(new java.awt.Color(255, 180, 61));
        btnUpdate.setMousePress(new java.awt.Color(255, 231, 112));
        btnUpdate.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel4.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 210, 30));

        btnTambahKategoriEdit.setMnemonic('i');
        btnTambahKategoriEdit.setText("+");
        btnTambahKategoriEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnTambahKategoriEdit.setMousePress(new java.awt.Color(204, 204, 204));
        btnTambahKategoriEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKategoriEditActionPerformed(evt);
            }
        });
        jPanel4.add(btnTambahKategoriEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, -1, 30));

        btnTambahSatuanEdit.setMnemonic('n');
        btnTambahSatuanEdit.setText("+");
        btnTambahSatuanEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnTambahSatuanEdit.setMousePress(new java.awt.Color(204, 204, 204));
        btnTambahSatuanEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSatuanEditActionPerformed(evt);
            }
        });
        jPanel4.add(btnTambahSatuanEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 240, -1, 30));

        javax.swing.GroupLayout ItemEditLayout = new javax.swing.GroupLayout(ItemEdit.getContentPane());
        ItemEdit.getContentPane().setLayout(ItemEditLayout);
        ItemEditLayout.setHorizontalGroup(
            ItemEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        ItemEditLayout.setVerticalGroup(
            ItemEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        CategoryAdd.setModal(true);
        CategoryAdd.setUndecorated(true);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(87, 176, 86)));
        jPanel8.setPreferredSize(new java.awt.Dimension(421, 156));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtKategoriTambah.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKategoriTambah.setToolTipText(null);
        txtKategoriTambah.setBorder(null);
        txtKategoriTambah.setOpaque(false);
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
        jPanel8.add(txtKategoriTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 270, -1));

        jPanel9.setBackground(new java.awt.Color(87, 176, 86));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Tambah Kategori");
        jPanel9.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 2, -1, 34));

        btnCloseKategori.setMnemonic('c');
        btnCloseKategori.setText("Close");
        btnCloseKategori.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnCloseKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseKategoriActionPerformed(evt);
            }
        });
        jPanel9.add(btnCloseKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 0, 80, 40));

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 420, -1));

        SListAdd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        SListAdd.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SListAdd.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SListAdd.setFocusable(false);
        SListAdd.setPreferredSize(new java.awt.Dimension(200, 200));

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

        jPanel8.add(SListAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 90, 260, 0));
        jPanel8.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 270, 10));

        jLabel20.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("Kategori");
        jLabel20.setToolTipText(null);
        jPanel8.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 70, 20));

        btnSimpanKategori.setMnemonic('s');
        btnSimpanKategori.setText("Simpan");
        btnSimpanKategori.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnSimpanKategori.setMousePress(new java.awt.Color(204, 204, 204));
        btnSimpanKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanKategoriActionPerformed(evt);
            }
        });
        jPanel8.add(btnSimpanKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 90, 30));

        javax.swing.GroupLayout CategoryAddLayout = new javax.swing.GroupLayout(CategoryAdd.getContentPane());
        CategoryAdd.getContentPane().setLayout(CategoryAddLayout);
        CategoryAddLayout.setHorizontalGroup(
            CategoryAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        CategoryAddLayout.setVerticalGroup(
            CategoryAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        UnitAdd.setModal(true);
        UnitAdd.setUndecorated(true);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(87, 176, 86)));
        jPanel10.setPreferredSize(new java.awt.Dimension(421, 156));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(87, 176, 86));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Tambah Satuan");
        jPanel11.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 2, -1, 34));

        btnCloseUnit.setMnemonic('c');
        btnCloseUnit.setText("Close");
        btnCloseUnit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnCloseUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseUnitActionPerformed(evt);
            }
        });
        jPanel11.add(btnCloseUnit, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 80, 40));

        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, -1));

        jLabel22.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setText("Satuan");
        jLabel22.setToolTipText(null);
        jPanel10.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 76, 20));

        txtSatuanTambah.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtSatuanTambah.setToolTipText(null);
        txtSatuanTambah.setBorder(null);
        txtSatuanTambah.setOpaque(false);
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
        jPanel10.add(txtSatuanTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 190, -1));

        SListUnit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        SListUnit.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SListUnit.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SListUnit.setFocusable(false);
        SListUnit.setPreferredSize(new java.awt.Dimension(200, 200));

        ListUnit.setForeground(new java.awt.Color(153, 153, 153));
        ListUnit.setModel(new javax.swing.table.DefaultTableModel(
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
        ListUnit.setGridColor(new java.awt.Color(255, 255, 255));
        ListUnit.setShowGrid(false);
        ListUnit.setSurrendersFocusOnKeystroke(true);
        ListUnit.setTableHeader(null
        );
        SListUnit.setViewportView(ListUnit);

        jPanel10.add(SListUnit, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 90, -1, 0));
        jPanel10.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 220, 10));

        btnSimpanUnit.setMnemonic('s');
        btnSimpanUnit.setText("Simpan");
        btnSimpanUnit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnSimpanUnit.setMousePress(new java.awt.Color(204, 204, 204));
        btnSimpanUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanUnitActionPerformed(evt);
            }
        });
        jPanel10.add(btnSimpanUnit, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 90, 30));

        javax.swing.GroupLayout UnitAddLayout = new javax.swing.GroupLayout(UnitAdd.getContentPane());
        UnitAdd.getContentPane().setLayout(UnitAddLayout);
        UnitAddLayout.setHorizontalGroup(
            UnitAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        UnitAddLayout.setVerticalGroup(
            UnitAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1246, 714));

        jPanel1.setBackground(new java.awt.Color(248, 251, 251));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelEH.setOpaque(false);
        panelEH.setPreferredSize(new java.awt.Dimension(130, 30));
        panelEH.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEdit.setMnemonic('e');
        btnEdit.setText("Edit");
        btnEdit.setMouseHover(new java.awt.Color(255, 180, 61));
        btnEdit.setMousePress(new java.awt.Color(255, 231, 112));
        btnEdit.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        panelEH.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        btnHapus.setMnemonic('h');
        btnHapus.setText("Hapus");
        btnHapus.setMouseHover(new java.awt.Color(255, 26, 26));
        btnHapus.setMousePress(new java.awt.Color(255, 77, 77));
        btnHapus.setWarnaBackground(new java.awt.Color(255, 0, 0));
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        panelEH.add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, -1, -1));

        jPanel1.add(panelEH, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 130, 0));

        SPtableTampil.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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

        jSeparator1.setForeground(new java.awt.Color(78, 115, 223));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 250, 10));

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(78, 115, 223));
        jLabel1.setText("Master Barang");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 300, 60));

        btnTambah.setMnemonic('t');
        btnTambah.setText("Tambah Barang");
        btnTambah.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnTambah.setMousePress(new java.awt.Color(204, 204, 204));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        jPanel1.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 30));

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
        panelEH.setSize(130, 30);
    }//GEN-LAST:event_tableTampilMouseClicked

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){

        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSearch.setText("");
            panelEH.setSize(130, 0);
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        Cari();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        Tambah();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        panelEH.setSize(130, 0);
        Edit();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        panelEH.setSize(130, 0);
        Hapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnCloseAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseAddActionPerformed
        KeluarAdd();
    }//GEN-LAST:event_btnCloseAddActionPerformed

    private void tabelPilihAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPilihAddMouseClicked
        TampilPilihAdd();
    }//GEN-LAST:event_tabelPilihAddMouseClicked

    private void tabelPilihAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelPilihAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            TampilPilihAdd();
        }
    }//GEN-LAST:event_tabelPilihAddKeyPressed

    private void txtKodeAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKodeAddFocusLost
        StabelListAdd.setVisible(false);
        CekKodeAdd();
    }//GEN-LAST:event_txtKodeAddFocusLost

    private void txtKodeAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtNamaBarangAdd.setText(null);
            txtNamaBarangAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKodeAdd.setText("");
        }
    }//GEN-LAST:event_txtKodeAddKeyPressed

    private void txtKodeAddKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeAddKeyReleased
        if (txtKodeAdd.getText().trim().isEmpty()) {
            StabelListAdd.setVisible(false);
        } else{
            PopUpCodeAdd();
        }
    }//GEN-LAST:event_txtKodeAddKeyReleased

    private void txtKodeAddKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeAddKeyTyped
        if (txtKodeAdd.getText().length() >= 25 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtKodeAddKeyTyped

    private void txtNamaBarangAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNamaBarangAddFocusLost
        StabelListAdd.setVisible(false);
    }//GEN-LAST:event_txtNamaBarangAddFocusLost

    private void txtNamaBarangAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaBarangAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtKategoriAdd.setText(null);
            txtKategoriAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtNamaBarangAdd.setText("");
        }
    }//GEN-LAST:event_txtNamaBarangAddKeyPressed

    private void txtNamaBarangAddKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaBarangAddKeyReleased
        if (txtNamaBarangAdd.getText().trim().isEmpty()) {
            StabelListAdd.setVisible(false);
        } else{
            PopUpNameAdd();
        }
    }//GEN-LAST:event_txtNamaBarangAddKeyReleased

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
        HJual =txtHargaJualAdd.getText();          //Menyimpan di Variable HJual
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

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        Simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
        BersihAdd();
    }//GEN-LAST:event_btnBersihActionPerformed

    private void btnTambahKategoriAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKategoriAddActionPerformed
        TambahKategoriAdd();
    }//GEN-LAST:event_btnTambahKategoriAddActionPerformed

    private void btnTambahSatuanAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSatuanAddActionPerformed
        TambahSatuanAdd();
    }//GEN-LAST:event_btnTambahSatuanAddActionPerformed

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

    private void txtKategoriTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriTambahKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            SimpanKategori();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKategoriTambah.setText("");
        }
    }//GEN-LAST:event_txtKategoriTambahKeyPressed

    private void txtKategoriTambahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriTambahKeyReleased
        if (txtKategoriTambah.getText().trim().isEmpty()) {
            SListAdd.setVisible(false);
        } else{
            PopUpKategoriAdd();
        }
    }//GEN-LAST:event_txtKategoriTambahKeyReleased

    private void txtKategoriTambahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriTambahKeyTyped
        if (txtKategoriTambah.getText().length() >= 15 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtKategoriTambahKeyTyped

    private void btnCloseKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseKategoriActionPerformed
        KeluarKategori();
    }//GEN-LAST:event_btnCloseKategoriActionPerformed

    private void btnSimpanKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanKategoriActionPerformed
        SimpanKategori();
    }//GEN-LAST:event_btnSimpanKategoriActionPerformed

    private void btnCloseUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseUnitActionPerformed
        KeluarSatuan();
    }//GEN-LAST:event_btnCloseUnitActionPerformed

    private void txtSatuanTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanTambahKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            SimpanSatuan();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSatuanTambah.setText("");
        }
    }//GEN-LAST:event_txtSatuanTambahKeyPressed

    private void txtSatuanTambahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanTambahKeyReleased
        if (txtSatuanTambah.getText().trim().isEmpty()) {
            SListUnit.setVisible(false);
        } else{
            PopUpSatuan();
        }
    }//GEN-LAST:event_txtSatuanTambahKeyReleased

    private void txtSatuanTambahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanTambahKeyTyped
        if (txtSatuanTambah.getText().length() >= 15 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSatuanTambahKeyTyped

    private void btnSimpanUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanUnitActionPerformed
        SimpanSatuan();
    }//GEN-LAST:event_btnSimpanUnitActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog CategoryAdd;
    private javax.swing.JDialog ItemAdd;
    private javax.swing.JDialog ItemEdit;
    private javax.swing.JTable ListAdd;
    private javax.swing.JTable ListUnit;
    private javax.swing.JScrollPane SListAdd;
    private javax.swing.JScrollPane SListUnit;
    private javax.swing.JScrollPane SPtableTampil;
    private javax.swing.JScrollPane StabelListAdd;
    private javax.swing.JScrollPane StabelListEdit;
    private javax.swing.JScrollPane StabelPilihAdd;
    private javax.swing.JScrollPane StabelPilihEdit;
    private javax.swing.JDialog UnitAdd;
    private khansapos.Utility_ButtonFlat btnBersih;
    private khansapos.Utility_ButtonMetro btnCloseAdd;
    private khansapos.Utility_ButtonMetro btnCloseEdit;
    private khansapos.Utility_ButtonMetro btnCloseKategori;
    private khansapos.Utility_ButtonMetro btnCloseUnit;
    private khansapos.Utility_ButtonFlat btnEdit;
    private khansapos.Utility_ButtonFlat btnHapus;
    private khansapos.Utility_ButtonFlat btnSimpan;
    private khansapos.Utility_ButtonFlat btnSimpanKategori;
    private khansapos.Utility_ButtonFlat btnSimpanUnit;
    private khansapos.Utility_ButtonFlat btnTambah;
    private khansapos.Utility_ButtonFlat btnTambahKategoriAdd;
    private khansapos.Utility_ButtonFlat btnTambahKategoriEdit;
    private khansapos.Utility_ButtonFlat btnTambahSatuanAdd;
    private khansapos.Utility_ButtonFlat btnTambahSatuanEdit;
    private khansapos.Utility_ButtonFlat btnUpdate;
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
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
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
    // End of variables declaration//GEN-END:variables
}
