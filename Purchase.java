package khansapos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import net.proteanit.sql.DbUtils;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

public class Purchase extends javax.swing.JInternalFrame {
    java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
    public static String  Id;
    private static String noFaktur,idSuplier,namaSuplier,alamatSuplier;
    PurchaseItemList DaftarBeli = new PurchaseItemList();
    DefaultTableModel Tabel;
    
    
    public Purchase() {
        initComponents();
        IframeBorderLess(); 
        SPtableTampil.getViewport().setBackground(new Color(255,255,255));
        StabelPilih.getViewport().setBackground(new Color(255,255,255));
        
        Tengah();      
         if (noFaktur != null){
             lblInput.setText("Barang ");
             TampilPilih();
         }else{
             lblInput.setText("No Faktur ");
         }
        
        //Merubah Huruf Besar
        DocumentFilter filter = new Utility_Text();
        AbstractDocument isiText = (AbstractDocument) txtInput.getDocument();
        isiText.setDocumentFilter(filter);
        
        TampilBeli();        
        //SwingUtilities.invokeLater(() -> { txtInput.requestFocusInWindow(); });

    }
    
   public void Focus(){
       txtInput.requestFocus();
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
    


    private void CariBarang(){        
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rsCari = st.executeQuery("SELECT  item_code,item_name, item_unit, item_category, item_bprice, item_discount,"
                    + "item_sprice, item_unit FROM items WHERE item_code LIKE '"+txtInput.getText()+"'");
            
            if(rsCari.next()){  
                if (DaftarBeli.cariItem(txtInput.getText())){
                    JOptionPane.showMessageDialog(null, "Data Sudah Ada..");
                }else{                          
                    Integer Qty = 1;
                    Integer subTotal =  (rsCari.getInt("item_bprice") - rsCari.getInt("item_discount")) * Qty;
                    //(code,name,category,bprice,discount,sprice,qty,unit,subtotal)
                    DaftarBeli.tambahItem(rsCari.getString("item_code"), rsCari.getString("item_name"), rsCari.getString("item_category"), 
                            rsCari.getInt("item_bprice"), rsCari.getInt("item_discount"), rsCari.getInt("item_sprice"),Qty, 
                            rsCari.getString("item_unit"), subTotal  );

                            TampilBeli();
                }

            }else{
                
            }
                
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }

    private void TampilBeli(){
        //Set nama kolom pada jtable
        String[] NamaKolom = {"Kode barang","Nama Barang", "Kategori", "Harga Jual ","Satuan","Harga Beli","Discount","Qty","Sub Total"};
        //menyimpan data yang didapat dari method getData dg jumlah 3kolom ke aray object
        Object[][] daftarBeli = new Object[DaftarBeli.getItem().size()] [9];
        
        int i = 0;
                for (PurchaseItemObject p : DaftarBeli.getItem()){
                    Object arrayBeli[] = {p.Code(), p.Name(), p.Category(), p.Sprice(), p.Unit(), p.Bprice(), p.Discount(), p.Qty(), p.Subtotal() };
                     daftarBeli[i] = arrayBeli;
                     i++;
                }
                
                //memasukkan data yang ada di objectBuku ke jTable
                Tabel = new DefaultTableModel(daftarBeli, NamaKolom);
                tableTampil.setModel(Tabel);
    }
    
    private void SuplierInput(){
        dialogSuplier.setSize(828, 416);
        dialogSuplier.setVisible(true);              
        dialogSuplier.setLocation(150,80);
        txtFakturInput.setText("");
        txtFakturInput.requestFocus();

    }
    
    public void Edit(){
    
    }
    
    private void Hapus(){       
        if (JOptionPane.showConfirmDialog(null, "Yakin data Suplier akan dihapus?", "Khansa POS",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {            
            try {
                    java.sql.Statement st = con.createStatement();           
                    st.executeUpdate("DELETE FROM supliers WHERE suplier_id="+Id);            
                    JOptionPane.showMessageDialog(null, "Data Suplier berhasil dihapus!");
                    //TampilSuplier();
            } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
            }  
        }        
    }
    
     private void CariSuplier(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  suplier_id,suplier_name, suplier_address "
                    + "FROM supliers WHERE suplier_name LIKE '%"+txtSuplierInput.getText()+"%'");
            tabelPilih.setModel(DbUtils.resultSetToTableModel(rs)); 
            Utility_Table ut = new Utility_Table();             
            ut.Header(tabelPilih,0,"",-10);
            ut.Header(tabelPilih,1,"Suplier Name",120);
            ut.Header(tabelPilih,2,"Address",450);
            tabelPilih.removeColumn(tabelPilih.getColumnModel().getColumn(0)); 

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
     
     private void CariPilih() { 
        try { 
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  suplier_id,suplier_name, suplier_address "
                    + "FROM supliers WHERE suplier_id LIKE '"+idSuplier+"'");
           
            if(rs.next()){  
                    namaSuplier=rs.getString("suplier_name");
                    alamatSuplier=rs.getString("suplier_address");
                    noFaktur=txtFakturInput.getText();
                    TampilPilih();
                    dialogSuplier.dispose();
                    lblInput.setText("Barang ");
                    txtInput.requestFocus();
            } else{
                txtSuplierInput.requestFocus();
            } 
                txtSuplierInput.setText("");
                txtFakturInput.setText("");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }            
    }
     
     private void TampilPilih(){
        lblNoFakturSuplier.setText("No Faktur : "+noFaktur);  
        lblNamaSuplier.setText(namaSuplier);
        lblAlamatSuplier.setText("<html><p style=\"width:300px\">"+alamatSuplier+"</p></html>");       
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
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panelEH = new javax.swing.JPanel();
        SPtableTampil = new javax.swing.JScrollPane();
        tableTampil = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        lblInput = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txtInput = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        lblNamaSuplier = new javax.swing.JLabel();
        lblAlamatSuplier = new javax.swing.JLabel();
        lblNoFakturSuplier = new javax.swing.JLabel();

        dialogSuplier.setIconImage(null);
        dialogSuplier.setMinimumSize(new java.awt.Dimension(828, 416));
        dialogSuplier.setUndecorated(true);
        dialogSuplier.setResizable(false);

        jPanel2.setBackground(new java.awt.Color(247, 247, 247));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(87, 176, 86)));
        jPanel2.setMaximumSize(new java.awt.Dimension(828, 416));
        jPanel2.setMinimumSize(new java.awt.Dimension(828, 416));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInput1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblInput1.setText("Nama Suplier  ");
        lblInput1.setToolTipText(null);
        jPanel2.add(lblInput1, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 60, -1, -1));

        txtSuplierInput.setBackground(new java.awt.Color(247, 247, 247));
        txtSuplierInput.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
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
        jPanel2.add(txtSuplierInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 60, 220, -1));

        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 220, 10));

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

        jPanel2.add(StabelPilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 124, 806, 238));

        lblInput2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblInput2.setText("No Faktur       ");
        lblInput2.setToolTipText(null);
        jPanel2.add(lblInput2, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 20, -1, -1));

        txtFakturInput.setBackground(new java.awt.Color(247, 247, 247));
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
        jPanel2.add(txtFakturInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 20, 220, -1));

        jSeparator4.setForeground(new java.awt.Color(204, 204, 204));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 40, 220, 10));

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(87, 176, 86));
        jLabel2.setText("Input Nomer Faktur dan Nama Suplier");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, -1, -1));

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
        tableTampil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTampilMouseClicked(evt);
            }
        });
        SPtableTampil.setViewportView(tableTampil);

        jPanel1.add(SPtableTampil, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 1190, 500));

        lblInput.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblInput.setForeground(new java.awt.Color(87, 176, 86));
        lblInput.setText("Barang");
        lblInput.setToolTipText(null);
        jPanel1.add(lblInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 90, -1));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 240, 10));

        txtInput.setBackground(new java.awt.Color(248, 251, 251));
        txtInput.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        txtInput.setToolTipText(null);
        txtInput.setBorder(null);
        txtInput.setFocusCycleRoot(true);
        txtInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtInputKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInputKeyReleased(evt);
            }
        });
        jPanel1.add(txtInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 240, 20));

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
        lblNamaSuplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNamaSuplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNamaSuplierMouseClicked(evt);
            }
        });
        jPanel1.add(lblNamaSuplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 310, 30));

        lblAlamatSuplier.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblAlamatSuplier.setForeground(new java.awt.Color(102, 102, 102));
        lblAlamatSuplier.setText("Alamat Suplier");
        lblAlamatSuplier.setToolTipText(null);
        lblAlamatSuplier.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblAlamatSuplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAlamatSuplier.setPreferredSize(new java.awt.Dimension(500, 40));
        lblAlamatSuplier.setRequestFocusEnabled(false);
        lblAlamatSuplier.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblAlamatSuplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAlamatSuplierMouseClicked(evt);
            }
        });
        jPanel1.add(lblAlamatSuplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        lblNoFakturSuplier.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblNoFakturSuplier.setForeground(new java.awt.Color(102, 102, 102));
        lblNoFakturSuplier.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNoFakturSuplier.setText("Nomer Faktur");
        lblNoFakturSuplier.setToolTipText(null);
        lblNoFakturSuplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNoFakturSuplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNoFakturSuplierMouseClicked(evt);
            }
        });
        jPanel1.add(lblNoFakturSuplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 70, 260, 20));

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
        panelEH.setSize(130, 0);
        Edit();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        panelEH.setSize(130, 0);
        Hapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tableTampilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTampilMouseClicked
        Id= tableTampil.getModel().getValueAt(tableTampil.getSelectedRow(), 0).toString(); //Ambil nilai kolom (0) dan masukkan ke variabel Id

        panelEH.setLocation( evt.getX() + SPtableTampil.getX(),  evt.getY() + SPtableTampil.getY());
        panelEH.setSize(130, 30);
    }//GEN-LAST:event_tableTampilMouseClicked

    private void btnCloseSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseSuplierActionPerformed
        dialogSuplier.dispose();
    }//GEN-LAST:event_btnCloseSuplierActionPerformed

    private void txtInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInputKeyReleased

    }//GEN-LAST:event_txtInputKeyReleased

    private void txtInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInputKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if (noFaktur== null){
                SuplierInput();
                txtFakturInput.setText(txtInput.getText());
                txtSuplierInput.requestFocus();
                txtInput.setText("");
            }else{
                CariBarang();

            }
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtInput.setText("");
            panelEH.setSize(130, 0);
        }
    }//GEN-LAST:event_txtInputKeyPressed

    private void txtSuplierInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSuplierInputKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN){  
            tabelPilih.setRowSelectionInterval(0, 0);
            tabelPilih.requestFocus();
        }
    }//GEN-LAST:event_txtSuplierInputKeyPressed

    private void txtSuplierInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSuplierInputKeyReleased
        CariSuplier();
    }//GEN-LAST:event_txtSuplierInputKeyReleased

    private void tabelPilihMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPilihMouseClicked
         idSuplier= tabelPilih.getModel().getValueAt(tabelPilih.getSelectedRow(), 0).toString();
        CariPilih();
    }//GEN-LAST:event_tabelPilihMouseClicked

    private void tabelPilihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelPilihKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             idSuplier= tabelPilih.getModel().getValueAt(tabelPilih.getSelectedRow(), 0).toString();
            CariPilih();
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

    private void lblNamaSuplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNamaSuplierMouseClicked
        SuplierInput();
    }//GEN-LAST:event_lblNamaSuplierMouseClicked

    private void lblAlamatSuplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAlamatSuplierMouseClicked
        SuplierInput();
    }//GEN-LAST:event_lblAlamatSuplierMouseClicked

    private void lblNoFakturSuplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNoFakturSuplierMouseClicked
        SuplierInput();
    }//GEN-LAST:event_lblNoFakturSuplierMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane SPtableTampil;
    private javax.swing.JScrollPane StabelPilih;
    private javax.swing.JDialog dialogSuplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblAlamatSuplier;
    private javax.swing.JLabel lblInput;
    private javax.swing.JLabel lblInput1;
    private javax.swing.JLabel lblInput2;
    private javax.swing.JLabel lblNamaSuplier;
    private javax.swing.JLabel lblNoFakturSuplier;
    private javax.swing.JPanel panelEH;
    private javax.swing.JTable tabelPilih;
    private javax.swing.JTable tableTampil;
    private javax.swing.JTextField txtFakturInput;
    private javax.swing.JTextField txtInput;
    private javax.swing.JTextField txtSuplierInput;
    // End of variables declaration//GEN-END:variables
}
