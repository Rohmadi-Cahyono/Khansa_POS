package khansapos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import net.proteanit.sql.DbUtils;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

public class Purchase extends javax.swing.JInternalFrame {
    java.sql.Connection con =  new UDbConnection().koneksi();
    public static String  Id;
    private static String noFaktur,idSuplier,namaSuplier,alamatSuplier,HBeli,HJual,DiscBeli,Sejumlah,SubTotal;
    PurchaseItemList DaftarBeli = new PurchaseItemList();
    DefaultTableModel Tabel;
    UText ut = new UText();
    UTable utb = new UTable(); 
    
    public Purchase() {
        initComponents();
        IframeBorderLess(); 
        SPtableTampil.getViewport().setBackground(new Color(255,255,255));
        StabelPilih.getViewport().setBackground(new Color(255,255,255));
        StabelBarangCari.getViewport().setBackground(new Color(255,255,255));
        
        Tengah();              
        //Merubah Huruf Besar
        DocumentFilter filter = new UText();
        AbstractDocument isiText = (AbstractDocument) txtKode.getDocument();
        isiText.setDocumentFilter(filter);
        
        TampilBeli();        
       

    }
    
   public void Focus(){
       //btnSuplier.requestFocus();
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

    private void TampilBeli(){
        //Set nama kolom pada jtable
        String[] NamaKolom = {"Kode barang","Nama Barang", "Harga Jual ","Harga Beli","Discount","Qty","Satuan","Sub Total"};
        //menyimpan data yang didapat dari method getData dg jumlah 3kolom ke aray object
        Object[][] daftarBeli = new Object[DaftarBeli.getItems().size()] [8];
        
                int i = 0;
                for (PurchaseItemObject p : DaftarBeli.getItems()){
                    Object arrayBeli[] = {p.Code(), p.Name(),  p.Sprice(),  p.Bprice(), p.Discount(), p.Qty(), p.Unit(), p.Subtotal() };
                     daftarBeli[i] = arrayBeli;
                     i++;
                }
                
                //memasukkan data yang ada di objectBuku ke jTable
                Tabel = new DefaultTableModel(daftarBeli, NamaKolom);
                tableTampil.setModel(Tabel);
                ColSize(tableTampil,0,200);
                ColSize(tableTampil,1,300);
                ColSize(tableTampil,2,100);
                ColSize(tableTampil,3,100);
                ColSize(tableTampil,4,50);
                ColSize(tableTampil,5,50);
                ColSize(tableTampil,6,50);
                ColSize(tableTampil,7,100);
                tableTampil.getColumnModel().getColumn(2).setCellRenderer(utb.formatAngka);
                tableTampil.getColumnModel().getColumn(3).setCellRenderer(utb.formatAngka);
                tableTampil.getColumnModel().getColumn(4).setCellRenderer(utb.formatAngka);
                tableTampil.getColumnModel().getColumn(5).setCellRenderer(utb.formatAngka);
                tableTampil.getColumnModel().getColumn(7).setCellRenderer(utb.formatAngka);
                tableTampil.getTableHeader().setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 14));
                String TotalBeli=String.valueOf(DaftarBeli.hitungTotal());
                lblTotal.setText("Total Rp  "+ ut.LabelToRp(TotalBeli));
    }
    
    private void ColSize(JTable table, int col_index,int col_size){     
        table.getColumnModel().getColumn(col_index).setPreferredWidth(col_size);   
    }
    



//-----------------------------------------------------------------SuplierInput-------------------------------------------------------------
    
    private void SuplierInput_Tampil(){
        dialogSuplier.setSize(828, 416);
        dialogSuplier.setVisible(true);              
        dialogSuplier.setLocation(150,80);
        txtFakturInput.setText("");
        txtFakturInput.requestFocus();
    }
     
    private void SuplierInput_Cari(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  suplier_id,suplier_name, suplier_address "
                    + "FROM supliers WHERE suplier_name LIKE '%"+txtSuplierInput.getText()+"%'");
            tabelPilih.setModel(DbUtils.resultSetToTableModel(rs)); 
            //Utility_Table ut = new Utility_Table();             
            utb.Header(tabelPilih,0,"",-10);
            utb.Header(tabelPilih,1,"Suplier Name",120);
            utb.Header(tabelPilih,2,"Address",450);
            tabelPilih.removeColumn(tabelPilih.getColumnModel().getColumn(0)); 

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
     
     private void SuplierInput_CariPilih() { 
        try { 
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  suplier_id,suplier_name, suplier_address "
                    + "FROM supliers WHERE suplier_id LIKE '"+idSuplier+"'");
           
            if(rs.next()){  
                if (!txtFakturInput.getText().trim().isEmpty() ){
                    namaSuplier=rs.getString("suplier_name");
                    alamatSuplier=rs.getString("suplier_address");
                    noFaktur=txtFakturInput.getText();
                    SuplierInput_TampilPilih();
                    dialogSuplier.dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"Nomer Faktur tidak boleh Kosong.","Pembelian Barang",JOptionPane.WARNING_MESSAGE); 
                    txtSuplierInput.setText("");
                    txtFakturInput.requestFocus();
                }
            } else{
                txtSuplierInput.requestFocus();
            } 
                txtSuplierInput.setText("");
                txtFakturInput.setText("");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }            
    }
     
     private void SuplierInput_TampilPilih(){
        lblNoFakturSuplier.setText("No Faktur : "+noFaktur);  
        lblNamaSuplier.setText(namaSuplier);
        lblAlamatSuplier.setText("<html><p style=\"width:300px\">"+alamatSuplier+"</p></html>");       
     }
     
     
//-----------------------------------------HapusBarang-------------------------------------------------------------------

    private void HapusBarang(){       
        if (JOptionPane.showConfirmDialog(null, "Yakin data Pembelian Barang akan dihapus?", "Khansa POS",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {            
                DaftarBeli.hapusItem(Id);
                TampilBeli();
        }        
    }     
     
//-----------------------------------------EditBarang-----------------------------------------------------------------------

     private void EditBarang_Tampil(){
        dialogBarang.setSize(700,375);
        dialogBarang.setVisible(true);              
        dialogBarang.setLocation(150,80); 
        labelHeaderBarang.setText("EDIT PEMBELIAN");
        //btnSimpanBarang.setText("Update");
        //btnSimpanBarang.setMnemonic('u');

        DaftarBeli.getItems().stream().filter(p -> (p.Code().equals(Id))).map(p -> {
            txtKode.setText(p.Code());
            return p;
        }).map(p -> {
            txtNama.setText(p.Name());
            return p;
        }).map(p -> {
            ut.AngkaToRp(txtHBeli,String.valueOf(p.Bprice()));
            return p;
        }).map(p -> {
            ut.AngkaToRp(txtDiscBeli, String.valueOf(p.Discount()));
            return p;
        }).map(p -> {
            ut.AngkaToRp(txtSejumlah,String. valueOf(p.Qty()));
            return p;
        }).map(p -> {
            ut.AngkaToRp(txtHJual, String.valueOf(p.Sprice()));
            return p;
        }).map(p -> {
            ut.AngkaToRp(txtSubtotal, String.valueOf(p.Subtotal()));
            return p;
        }).forEachOrdered(p -> {
            lblSatuan.setText(p.Unit());
        });    
        txtKode.requestFocus();
     }     
     
     private void EditBarang_Simpan(){
         DaftarBeli.hapusItem(Id);         
         TambahBarang_Simpan();
     }
     
//-----------------------------------------TambahBarang------------------------------------------------------------------
     
     private void TambahBarang_Tampil(){
        dialogBarang.setSize(700,375);
        dialogBarang.setVisible(true);              
        dialogBarang.setLocation(150,80);
        labelHeaderBarang.setText("TAMBAH PEMBELIAN");
        //btnSimpanBarang.setText("Tambah");
        //btnSimpanBarang.setMnemonic('t');
        Bersih();
        
     }
     
     private void Bersih(){
         HBeli = "";
         HJual = "";
         DiscBeli = "";
         Sejumlah = "";
         SubTotal = "";
         txtKode.setText("");
         txtNama.setText("");
         txtHBeli.setText("");
         txtDiscBeli.setText("");
         txtSejumlah.setText("");
         txtHJual.setText("");
         txtSubtotal.setText("");
         lblSatuan.setText("Satuan");
         txtKode.requestFocus();
     }
     
    private void TambahBarang_Cari(){        
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rsCari = st.executeQuery("SELECT  item_code,item_name, item_unit, item_category, item_bprice, item_sdiscount,"
                    + "item_sprice, item_unit FROM items WHERE item_code LIKE '"+txtKode.getText()+"'");
            
            if(rsCari.next()){  
                txtNama.setText(rsCari.getString("item_name"));
                lblSatuan.setText(rsCari.getString("item_unit"));
                HBeli=rsCari.getString("item_bprice");
                HJual=rsCari.getString("item_sprice");                    
                    
                ut.AngkaToRp(txtHBeli,HBeli);
                ut.AngkaToRp(txtHJual,HJual);
                txtDiscBeli.setText("");
                txtSejumlah.setText("");        
                txtSubtotal.setText("");
                DiscBeli = "";
                Sejumlah = "";
                SubTotal = "";
                txtHBeli.requestFocus();
               
            }else{   
                  //Tampil Pencarian barang   
                SwingUtilities.invokeLater(() -> {                                                 
                    dialogBarangCari.setLocation(165,90);  
                    dialogBarangCari.setSize(838, 422);
                    txtBarangCari.setText(txtKode.getText());
                    txtBarangCari.requestFocus();
                });
                 
                dialogBarangCari.setVisible(true); 
            }
                
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }

    private void TambahBarang_Subtotal(){
        try{
            if(!txtHBeli.getText().trim().isEmpty() && !txtDiscBeli.getText().trim().isEmpty() && !txtSejumlah.getText().trim().isEmpty()){
                Integer STotal=(Integer.parseInt(HBeli)-Integer.parseInt(DiscBeli))*Integer.parseInt(Sejumlah);
                SubTotal= Integer.toString(STotal);
                ut.AngkaToRp(txtSubtotal,SubTotal);
            }
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null, ex);
            }    
    }      
    
    private void TambahBarang_Simpan(){
        if (DaftarBeli.cariItem(txtKode.getText())){
            JOptionPane.showMessageDialog(null, "Barang sudah ada ditransaksi!","Pembelian Barang",
            JOptionPane.INFORMATION_MESSAGE);
            Bersih();
        }else{  
            DaftarBeli.tambahItem(txtKode.getText(), txtNama.getText(), Integer.parseInt(HBeli), Integer.parseInt(DiscBeli),
                Integer.parseInt(Sejumlah),lblSatuan.getText(),Integer.parseInt(SubTotal),Integer.parseInt(HJual));
            TampilBeli();
            dialogBarang.dispose();
           // btnBarang.requestFocus();
        }
    }
    
    
    //-----------------------------------------------------BarangCari------------------------------------------
    
    private void DialogBarang_Cari(){
        try {
            java.sql.Statement st = con.createStatement();  
            java.sql.ResultSet rsBarangCari = st.executeQuery("SELECT  item_code,item_name, item_category, item_bprice,"
                    + "item_stock, item_unit, item_delete FROM items WHERE item_name LIKE '%"+txtBarangCari.getText()+"%' AND item_delete=0");
            tabelBarangCari.setModel(DbUtils.resultSetToTableModel(rsBarangCari)); 
            //Utility_Table ut = new Utility_Table();             
            utb.Header(tabelBarangCari,0,"Kode Barang",250);
            utb.Header(tabelBarangCari,1,"Nama Barang",400);
            utb.Header(tabelBarangCari,2,"Kategori",150);
            utb.Header(tabelBarangCari,3,"H Beli",90);
            utb.Header(tabelBarangCari,4,"Stok",65);
            utb.Header(tabelBarangCari,5,"Satuan",100);

            tabelBarangCari.removeColumn(tabelBarangCari.getColumnModel().getColumn(6)); 
            tabelBarangCari.getColumnModel().getColumn(3).setCellRenderer(utb.formatAngka);
            tabelBarangCari.getColumnModel().getColumn(4).setCellRenderer(utb.formatAngka);
            tabelBarangCari.getTableHeader().setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 12));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }      
    }
    
    //-------------------------------------------------------Simpan Pembelian--------------------------------------------------------------
    
    private void SimpanPembelian(){
        if (idSuplier == null){
            JOptionPane.showMessageDialog(null,"Data Suplier belum diisi.","Pembelian Barang",JOptionPane.WARNING_MESSAGE); 
            //btnSuplier.requestFocus();           
        }else if(DaftarBeli.jumlahItem() == 0){
            JOptionPane.showMessageDialog(null,"Daftar pembelian masih kosong.","Pembelian Barang",JOptionPane.WARNING_MESSAGE); 
            //btnBarang.requestFocus();
        }else{
            //Proses Simpan
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

        dialogSuplier = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        lblInput1 = new javax.swing.JLabel();
        txtSuplierInput = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        StabelPilih = new javax.swing.JScrollPane();
        tabelPilih = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        lblInput2 = new javax.swing.JLabel();
        txtFakturInput = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        dialogBarang = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        lblInput = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        lblInput3 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        lblInput4 = new javax.swing.JLabel();
        txtHBeli = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        lblInput5 = new javax.swing.JLabel();
        txtDiscBeli = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        lblInput6 = new javax.swing.JLabel();
        txtSejumlah = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        lblInput7 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        lblInput8 = new javax.swing.JLabel();
        txtHJual = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        lblSatuan = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        labelHeaderBarang = new javax.swing.JLabel();
        dialogBarangCari = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        lblInput9 = new javax.swing.JLabel();
        txtBarangCari = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        StabelBarangCari = new javax.swing.JScrollPane();
        tabelBarangCari = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panelEH = new javax.swing.JPanel();
        SPtableTampil = new javax.swing.JScrollPane();
        tableTampil = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        lblNamaSuplier = new javax.swing.JLabel();
        lblAlamatSuplier = new javax.swing.JLabel();
        lblNoFakturSuplier = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();

        dialogSuplier.setIconImage(null);
        dialogSuplier.setMinimumSize(new java.awt.Dimension(828, 416));
        dialogSuplier.setUndecorated(true);
        dialogSuplier.setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(235, 154, 35), 3, true));
        jPanel2.setMaximumSize(new java.awt.Dimension(828, 416));
        jPanel2.setMinimumSize(new java.awt.Dimension(828, 416));
        jPanel2.setPreferredSize(new java.awt.Dimension(828, 416));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInput1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblInput1.setForeground(new java.awt.Color(85, 118, 118));
        lblInput1.setText("Nama Suplier  ");
        lblInput1.setToolTipText(null);
        jPanel2.add(lblInput1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        txtSuplierInput.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        txtSuplierInput.setForeground(new java.awt.Color(85, 118, 118));
        txtSuplierInput.setToolTipText(null);
        txtSuplierInput.setBorder(null);
        txtSuplierInput.setFocusCycleRoot(true);
        txtSuplierInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSuplierInputKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSuplierInputKeyReleased(evt);
            }
        });
        jPanel2.add(txtSuplierInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 220, -1));

        jSeparator3.setBackground(new java.awt.Color(230, 230, 230));
        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 220, 10));

        StabelPilih.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(87, 176, 86), 1, true));
        StabelPilih.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        StabelPilih.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        StabelPilih.setFocusable(false);

        tabelPilih.setForeground(new java.awt.Color(51, 51, 51));
        tabelPilih.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelPilih.setGridColor(new java.awt.Color(255, 255, 255));
        tabelPilih.setShowGrid(false);
        tabelPilih.setSurrendersFocusOnKeystroke(true);
        tabelPilih.setTableHeader(null
        );
        tabelPilih.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPilihMouseClicked(evt);
            }
        });
        tabelPilih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelPilihKeyPressed(evt);
            }
        });
        StabelPilih.setViewportView(tabelPilih);

        jPanel2.add(StabelPilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 141, 810, 260));

        lblInput2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblInput2.setForeground(new java.awt.Color(85, 118, 118));
        lblInput2.setText("No Faktur       ");
        lblInput2.setToolTipText(null);
        jPanel2.add(lblInput2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txtFakturInput.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        txtFakturInput.setToolTipText(null);
        txtFakturInput.setBorder(null);
        txtFakturInput.setFocusCycleRoot(true);
        txtFakturInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFakturInputKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFakturInputKeyReleased(evt);
            }
        });
        jPanel2.add(txtFakturInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 220, -1));

        jSeparator4.setBackground(new java.awt.Color(230, 230, 230));
        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 220, 10));

        jPanel6.setBackground(new java.awt.Color(235, 154, 35));
        jPanel6.setPreferredSize(new java.awt.Dimension(828, 40));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Input Nomer Faktur dan Nama Suplier");
        jPanel6.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 828, 40));

        javax.swing.GroupLayout dialogSuplierLayout = new javax.swing.GroupLayout(dialogSuplier.getContentPane());
        dialogSuplier.getContentPane().setLayout(dialogSuplierLayout);
        dialogSuplierLayout.setHorizontalGroup(
            dialogSuplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dialogSuplierLayout.setVerticalGroup(
            dialogSuplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dialogBarang.setUndecorated(true);
        dialogBarang.setResizable(false);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(87, 176, 86), 3, true));
        jPanel3.setMaximumSize(new java.awt.Dimension(693, 375));
        jPanel3.setPreferredSize(new java.awt.Dimension(700, 375));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInput.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblInput.setForeground(new java.awt.Color(85, 118, 118));
        lblInput.setText("Kode Barang");
        lblInput.setToolTipText(null);
        jPanel3.add(lblInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 57, -1, -1));

        txtKode.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        txtKode.setToolTipText(null);
        txtKode.setBorder(null);
        txtKode.setFocusCycleRoot(true);
        txtKode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeKeyPressed(evt);
            }
        });
        jPanel3.add(txtKode, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 57, 233, -1));

        jSeparator2.setForeground(new java.awt.Color(230, 230, 230));
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 76, 233, -1));

        lblInput3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblInput3.setForeground(new java.awt.Color(85, 118, 118));
        lblInput3.setText("Nama Barang");
        lblInput3.setToolTipText(null);
        jPanel3.add(lblInput3, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 94, -1, -1));

        txtNama.setEditable(false);
        txtNama.setBackground(new java.awt.Color(255, 255, 255));
        txtNama.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        txtNama.setToolTipText(null);
        txtNama.setBorder(null);
        txtNama.setFocusCycleRoot(true);
        jPanel3.add(txtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 94, 394, -1));

        jSeparator5.setForeground(new java.awt.Color(230, 230, 230));
        jPanel3.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 113, 394, -1));

        lblInput4.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblInput4.setForeground(new java.awt.Color(85, 118, 118));
        lblInput4.setText("Harga Beli");
        lblInput4.setToolTipText(null);
        jPanel3.add(lblInput4, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 131, -1, -1));

        txtHBeli.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        txtHBeli.setToolTipText(null);
        txtHBeli.setBorder(null);
        txtHBeli.setFocusCycleRoot(true);
        txtHBeli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHBeliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHBeliFocusLost(evt);
            }
        });
        txtHBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHBeliKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHBeliKeyTyped(evt);
            }
        });
        jPanel3.add(txtHBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 131, 108, -1));

        jSeparator6.setForeground(new java.awt.Color(230, 230, 230));
        jPanel3.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 150, 108, -1));

        lblInput5.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblInput5.setForeground(new java.awt.Color(85, 118, 118));
        lblInput5.setText("Discount Beli");
        lblInput5.setToolTipText(null);
        jPanel3.add(lblInput5, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 168, -1, -1));

        txtDiscBeli.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        txtDiscBeli.setToolTipText(null);
        txtDiscBeli.setBorder(null);
        txtDiscBeli.setFocusCycleRoot(true);
        txtDiscBeli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiscBeliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiscBeliFocusLost(evt);
            }
        });
        txtDiscBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDiscBeliKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiscBeliKeyTyped(evt);
            }
        });
        jPanel3.add(txtDiscBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 168, 108, -1));

        jSeparator7.setForeground(new java.awt.Color(230, 230, 230));
        jPanel3.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 187, 108, -1));

        lblInput6.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblInput6.setForeground(new java.awt.Color(85, 118, 118));
        lblInput6.setText("Sejumlah");
        lblInput6.setToolTipText(null);
        jPanel3.add(lblInput6, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 205, -1, -1));

        txtSejumlah.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        txtSejumlah.setToolTipText(null);
        txtSejumlah.setBorder(null);
        txtSejumlah.setFocusCycleRoot(true);
        txtSejumlah.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSejumlahFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSejumlahFocusLost(evt);
            }
        });
        txtSejumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSejumlahKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSejumlahKeyTyped(evt);
            }
        });
        jPanel3.add(txtSejumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 205, 88, -1));

        jSeparator8.setForeground(new java.awt.Color(230, 230, 230));
        jPanel3.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 224, 88, -1));

        lblInput7.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblInput7.setForeground(new java.awt.Color(85, 118, 118));
        lblInput7.setText("Subtotal");
        lblInput7.setToolTipText(null);
        jPanel3.add(lblInput7, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 242, -1, -1));

        txtSubtotal.setEditable(false);
        txtSubtotal.setBackground(new java.awt.Color(255, 255, 255));
        txtSubtotal.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        txtSubtotal.setToolTipText(null);
        txtSubtotal.setBorder(null);
        txtSubtotal.setFocusCycleRoot(true);
        jPanel3.add(txtSubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 242, 144, -1));

        jSeparator9.setForeground(new java.awt.Color(230, 230, 230));
        jPanel3.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 261, 144, -1));

        lblInput8.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblInput8.setForeground(new java.awt.Color(85, 118, 118));
        lblInput8.setText("Harga Jual");
        lblInput8.setToolTipText(null);
        jPanel3.add(lblInput8, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 301, -1, -1));

        txtHJual.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        txtHJual.setToolTipText(null);
        txtHJual.setBorder(null);
        txtHJual.setFocusCycleRoot(true);
        txtHJual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHJualFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHJualFocusLost(evt);
            }
        });
        txtHJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHJualKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHJualKeyTyped(evt);
            }
        });
        jPanel3.add(txtHJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 301, 104, -1));

        jSeparator10.setForeground(new java.awt.Color(230, 230, 230));
        jPanel3.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(147, 320, 104, -1));

        lblSatuan.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblSatuan.setForeground(new java.awt.Color(85, 118, 118));
        lblSatuan.setText("Satuan");
        lblSatuan.setToolTipText(null);
        jPanel3.add(lblSatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 205, -1, -1));

        jPanel5.setBackground(new java.awt.Color(87, 176, 86));
        jPanel5.setPreferredSize(new java.awt.Dimension(700, 40));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelHeaderBarang.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        labelHeaderBarang.setForeground(new java.awt.Color(255, 255, 255));
        labelHeaderBarang.setText("TAMBAH BARANG");
        jPanel5.add(labelHeaderBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout dialogBarangLayout = new javax.swing.GroupLayout(dialogBarang.getContentPane());
        dialogBarang.getContentPane().setLayout(dialogBarangLayout);
        dialogBarangLayout.setHorizontalGroup(
            dialogBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        dialogBarangLayout.setVerticalGroup(
            dialogBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        dialogBarangCari.setModal(true);
        dialogBarangCari.setUndecorated(true);
        dialogBarangCari.setResizable(false);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        jPanel4.setMaximumSize(new java.awt.Dimension(838, 422));
        jPanel4.setMinimumSize(new java.awt.Dimension(838, 422));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInput9.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblInput9.setForeground(new java.awt.Color(85, 118, 118));
        lblInput9.setText("Nama Barang");
        lblInput9.setToolTipText(null);
        jPanel4.add(lblInput9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txtBarangCari.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        txtBarangCari.setForeground(new java.awt.Color(85, 118, 118));
        txtBarangCari.setToolTipText(null);
        txtBarangCari.setBorder(null);
        txtBarangCari.setFocusCycleRoot(true);
        txtBarangCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBarangCariKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBarangCariKeyReleased(evt);
            }
        });
        jPanel4.add(txtBarangCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 220, -1));

        jSeparator11.setBackground(new java.awt.Color(230, 230, 230));
        jSeparator11.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 220, 10));

        StabelBarangCari.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(87, 176, 86), 1, true));
        StabelBarangCari.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        StabelBarangCari.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        StabelBarangCari.setFocusable(false);

        tabelBarangCari.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        tabelBarangCari.setForeground(new java.awt.Color(51, 51, 51));
        tabelBarangCari.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelBarangCari.setGridColor(new java.awt.Color(255, 255, 255));
        tabelBarangCari.setShowGrid(false);
        tabelBarangCari.setSurrendersFocusOnKeystroke(true);
        tabelBarangCari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBarangCariMouseClicked(evt);
            }
        });
        tabelBarangCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelBarangCariKeyPressed(evt);
            }
        });
        StabelBarangCari.setViewportView(tabelBarangCari);

        jPanel4.add(StabelBarangCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 820, 310));

        jPanel7.setBackground(new java.awt.Color(34, 67, 67));
        jPanel7.setMinimumSize(new java.awt.Dimension(838, 40));
        jPanel7.setPreferredSize(new java.awt.Dimension(838, 40));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("CARI BARANG");
        jPanel7.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout dialogBarangCariLayout = new javax.swing.GroupLayout(dialogBarangCari.getContentPane());
        dialogBarangCari.getContentPane().setLayout(dialogBarangCariLayout);
        dialogBarangCariLayout.setHorizontalGroup(
            dialogBarangCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        dialogBarangCariLayout.setVerticalGroup(
            dialogBarangCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1246, 714));
        setMinimumSize(new java.awt.Dimension(1246, 714));
        setPreferredSize(new java.awt.Dimension(1246, 714));
        setRequestFocusEnabled(false);

        jPanel1.setBackground(new java.awt.Color(248, 251, 251));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelEH.setOpaque(false);
        panelEH.setPreferredSize(new java.awt.Dimension(130, 30));
        panelEH.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(panelEH, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 130, 0));

        tableTampil.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        tableTampil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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

        jPanel1.add(SPtableTampil, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 1190, 490));

        jSeparator1.setForeground(new java.awt.Color(78, 115, 223));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 60, 260, 10));

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(78, 115, 223));
        jLabel1.setText("Transaksi Pembelian");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 20, 260, 40));

        lblNamaSuplier.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        lblNamaSuplier.setForeground(new java.awt.Color(102, 102, 102));
        lblNamaSuplier.setText("Nama Suplier");
        lblNamaSuplier.setToolTipText(null);
        lblNamaSuplier.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(lblNamaSuplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 310, 30));

        lblAlamatSuplier.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblAlamatSuplier.setForeground(new java.awt.Color(102, 102, 102));
        lblAlamatSuplier.setText("Alamat Suplier");
        lblAlamatSuplier.setToolTipText(null);
        lblAlamatSuplier.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblAlamatSuplier.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblAlamatSuplier.setPreferredSize(new java.awt.Dimension(500, 40));
        lblAlamatSuplier.setRequestFocusEnabled(false);
        lblAlamatSuplier.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel1.add(lblAlamatSuplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        lblNoFakturSuplier.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblNoFakturSuplier.setForeground(new java.awt.Color(102, 102, 102));
        lblNoFakturSuplier.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNoFakturSuplier.setText("Nomer Faktur");
        lblNoFakturSuplier.setToolTipText(null);
        lblNoFakturSuplier.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(lblNoFakturSuplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 70, 260, 20));

        lblTotal.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(102, 102, 102));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("0");
        lblTotal.setToolTipText(null);
        lblTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 640, 300, 30));

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

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        panelEH.setSize(172, 0);
        EditBarang_Tampil();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        panelEH.setSize(172, 0);
        HapusBarang();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tableTampilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTampilMouseClicked
        Id= tableTampil.getModel().getValueAt(tableTampil.getSelectedRow(), 0).toString(); //Ambil nilai kolom (0) dan masukkan ke variabel Id

        panelEH.setLocation( evt.getX() + SPtableTampil.getX(),  evt.getY() + SPtableTampil.getY());
        panelEH.setSize(172,30);
    }//GEN-LAST:event_tableTampilMouseClicked

    private void btnCloseSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseSuplierActionPerformed
        dialogSuplier.dispose();
    }//GEN-LAST:event_btnCloseSuplierActionPerformed

    private void txtKodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                TambahBarang_Cari();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKode.setText("");
            panelEH.setSize(172, 0);
        }
    }//GEN-LAST:event_txtKodeKeyPressed

    private void txtSuplierInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSuplierInputKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN){  
            tabelPilih.setRowSelectionInterval(0, 0);
            tabelPilih.requestFocus();
        }
    }//GEN-LAST:event_txtSuplierInputKeyPressed

    private void txtSuplierInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSuplierInputKeyReleased
        SuplierInput_Cari();
    }//GEN-LAST:event_txtSuplierInputKeyReleased

    private void tabelPilihMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPilihMouseClicked
         idSuplier= tabelPilih.getModel().getValueAt(tabelPilih.getSelectedRow(), 0).toString();
        SuplierInput_CariPilih();
    }//GEN-LAST:event_tabelPilihMouseClicked

    private void tabelPilihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelPilihKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             idSuplier= tabelPilih.getModel().getValueAt(tabelPilih.getSelectedRow(), 0).toString();
            SuplierInput_CariPilih();
        }
    }//GEN-LAST:event_tabelPilihKeyPressed

    private void txtFakturInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFakturInputKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtSuplierInput.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtFakturInput.setText("");
        }
    }//GEN-LAST:event_txtFakturInputKeyPressed

    private void txtFakturInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFakturInputKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFakturInputKeyReleased

    private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        SuplierInput_Tampil();
    }//GEN-LAST:event_btnSuplierActionPerformed

    private void btnCloseBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseBarangActionPerformed
        dialogBarang.dispose();
    }//GEN-LAST:event_btnCloseBarangActionPerformed

    private void txtHBeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHBeliKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            //txtDiscBeli.setText(null);
            txtDiscBeli.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtHBeli.setText("");
        }
    }//GEN-LAST:event_txtHBeliKeyPressed

    private void txtHBeliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHBeliFocusLost
        HBeli =txtHBeli.getText();          //Menyimpan di Variable HBeli
        if (!txtHBeli.getText().trim().isEmpty()){
            TambahBarang_Subtotal();
            ut.AngkaToRp(txtHBeli,HBeli);
        }
    }//GEN-LAST:event_txtHBeliFocusLost

    private void txtHBeliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHBeliFocusGained
        txtHBeli.setText(HBeli);
    }//GEN-LAST:event_txtHBeliFocusGained

    private void txtHBeliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHBeliKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtHBeliKeyTyped

    private void txtHJualFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHJualFocusGained
        txtHJual.setText(HJual);
    }//GEN-LAST:event_txtHJualFocusGained

    private void txtHJualFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHJualFocusLost
        HJual =txtHJual.getText();         
        if (!txtHJual.getText().trim().isEmpty()){
            ut.AngkaToRp(txtHJual,HJual);
        }        
    }//GEN-LAST:event_txtHJualFocusLost

    private void txtHJualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHJualKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtHJualKeyTyped

    private void txtHJualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHJualKeyPressed
    /*    if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if ("Tambah".equals(btnSimpanBarang.getText())){
                TambahBarang_Simpan();
            }else{
                EditBarang_Simpan();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtHJual.setText("");
        }*/
    }//GEN-LAST:event_txtHJualKeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        TambahBarang_Tampil();
    }//GEN-LAST:event_btnBarangActionPerformed

    private void txtDiscBeliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscBeliFocusGained
        txtDiscBeli.setText(DiscBeli);
    }//GEN-LAST:event_txtDiscBeliFocusGained

    private void txtDiscBeliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscBeliFocusLost
        DiscBeli=txtDiscBeli.getText();         
        if (txtDiscBeli.getText().trim().isEmpty()){
            txtDiscBeli.setText("0");
            DiscBeli="0";
        }  
            TambahBarang_Subtotal();
            ut.AngkaToRp(txtDiscBeli,DiscBeli);
    }//GEN-LAST:event_txtDiscBeliFocusLost

    private void txtDiscBeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscBeliKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            //txtSejumlah.setText("");
            txtSejumlah.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtDiscBeli.setText("");
        }
    }//GEN-LAST:event_txtDiscBeliKeyPressed

    private void txtDiscBeliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscBeliKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtDiscBeliKeyTyped

    private void txtSejumlahFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSejumlahFocusGained
        txtSejumlah.setText(Sejumlah);
    }//GEN-LAST:event_txtSejumlahFocusGained

    private void txtSejumlahFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSejumlahFocusLost
        Sejumlah=txtSejumlah.getText();         
        if (!txtSejumlah.getText().trim().isEmpty()){
            TambahBarang_Subtotal();
            ut.AngkaToRp(txtSejumlah,Sejumlah);
        }
    }//GEN-LAST:event_txtSejumlahFocusLost

    private void txtSejumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSejumlahKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtHJual.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSejumlah.setText("");
        }
    }//GEN-LAST:event_txtSejumlahKeyPressed

    private void txtSejumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSejumlahKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtSejumlahKeyTyped

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
        Bersih();
    }//GEN-LAST:event_btnBersihActionPerformed

    private void btnSimpanBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanBarangActionPerformed
  /*      if ("Tambah".equals(btnSimpanBarang.getText())){
            TambahBarang_Simpan();
        }else{
            EditBarang_Simpan();
        }*/
    }//GEN-LAST:event_btnSimpanBarangActionPerformed

    private void txtBarangCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBarangCariKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN){  
            tabelBarangCari.setRowSelectionInterval(0, 0);
            tabelBarangCari.requestFocus();
        }
    }//GEN-LAST:event_txtBarangCariKeyPressed

    private void txtBarangCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBarangCariKeyReleased
        DialogBarang_Cari();
    }//GEN-LAST:event_txtBarangCariKeyReleased

    private void tabelBarangCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelBarangCariMouseClicked
        txtKode.setText(tabelBarangCari.getModel().getValueAt(tabelBarangCari.getSelectedRow(), 0).toString());
        dialogBarangCari.dispose();
        TambahBarang_Cari();
    }//GEN-LAST:event_tabelBarangCariMouseClicked

    private void tabelBarangCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelBarangCariKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER ){ 
            txtKode.setText(tabelBarangCari.getModel().getValueAt(tabelBarangCari.getSelectedRow(), 0).toString());
            dialogBarangCari.dispose();
            TambahBarang_Cari();
        }
    }//GEN-LAST:event_tabelBarangCariKeyPressed

    private void btnCloseBarangCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseBarangCariActionPerformed
        dialogBarangCari.dispose();
        txtKode.requestFocus();
    }//GEN-LAST:event_btnCloseBarangCariActionPerformed

    private void btnSimpanBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanBeliActionPerformed
        SimpanPembelian();
    }//GEN-LAST:event_btnSimpanBeliActionPerformed

    private void btnCloseSuplierMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseSuplierMouseEntered
      //  btnCloseSuplier.setForeground(new Color(255,0,0));
    }//GEN-LAST:event_btnCloseSuplierMouseEntered

    private void btnCloseSuplierMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseSuplierMouseExited
        //btnCloseSuplier.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnCloseSuplierMouseExited

    private void btnCloseBarangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseBarangMouseEntered
       // btnCloseBarang.setForeground(new Color(255,0,0));
    }//GEN-LAST:event_btnCloseBarangMouseEntered

    private void btnCloseBarangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseBarangMouseExited
       // btnCloseBarang.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnCloseBarangMouseExited

    private void btnCloseBarangCariMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseBarangCariMouseEntered
       // btnCloseBarangCari.setForeground(new Color(255,0,0));
    }//GEN-LAST:event_btnCloseBarangCariMouseEntered

    private void btnCloseBarangCariMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseBarangCariMouseExited
        //btnCloseBarangCari.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnCloseBarangCariMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane SPtableTampil;
    private javax.swing.JScrollPane StabelBarangCari;
    private javax.swing.JScrollPane StabelPilih;
    private javax.swing.JDialog dialogBarang;
    private javax.swing.JDialog dialogBarangCari;
    private javax.swing.JDialog dialogSuplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel labelHeaderBarang;
    private javax.swing.JLabel lblAlamatSuplier;
    private javax.swing.JLabel lblInput;
    private javax.swing.JLabel lblInput1;
    private javax.swing.JLabel lblInput2;
    private javax.swing.JLabel lblInput3;
    private javax.swing.JLabel lblInput4;
    private javax.swing.JLabel lblInput5;
    private javax.swing.JLabel lblInput6;
    private javax.swing.JLabel lblInput7;
    private javax.swing.JLabel lblInput8;
    private javax.swing.JLabel lblInput9;
    private javax.swing.JLabel lblNamaSuplier;
    private javax.swing.JLabel lblNoFakturSuplier;
    private javax.swing.JLabel lblSatuan;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panelEH;
    private javax.swing.JTable tabelBarangCari;
    private javax.swing.JTable tabelPilih;
    private javax.swing.JTable tableTampil;
    private javax.swing.JTextField txtBarangCari;
    private javax.swing.JTextField txtDiscBeli;
    private javax.swing.JTextField txtFakturInput;
    private javax.swing.JTextField txtHBeli;
    private javax.swing.JTextField txtHJual;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtSejumlah;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtSuplierInput;
    // End of variables declaration//GEN-END:variables
}
