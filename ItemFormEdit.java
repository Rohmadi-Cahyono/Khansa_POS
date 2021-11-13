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
import net.proteanit.sql.DbUtils;

public class ItemFormEdit extends javax.swing.JInternalFrame {
    java.sql.Connection con=new Utility_KoneksiDB().koneksi();   
    private String Pilihan,HBeli,HJual,Disc,Dipilih;
    DecimalFormat desimalFormat;    
    Utility_Text ut = new Utility_Text();
    String Id;

    public ItemFormEdit() {
        initComponents();
        IframeBorderLess();

        StabelList.setVisible(false);
        StabelList.getViewport().setBackground(new Color(255,255,255));
        
        //Option Untuk Table yang bisa dipilih
        StabelPilih.setVisible(false);
        StabelPilih.getViewport().setBackground(new Color(255,255,255)); 
        
        Tengah();
        TampilEdit();
        txtKode.setBackground(UIManager.getColor(new Color(255,255,255))); 
        
        txtKode.setEditable(false);
                
        SwingUtilities.invokeLater(() -> { txtNamaBarang.requestFocusInWindow(); });
        /* //Merubah Huruf Besar
        DocumentFilter filter = new Utility_Text();
        AbstractDocument isiText = (AbstractDocument) txtNamaBarang.getDocument();
        isiText.setDocumentFilter(filter);
        */


    }
    
    private void IframeBorderLess(){
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setSize(791,477);
    }
    
    private void Tengah(){
        Dimension formIni = this.getSize();
        this.setLocation(( Utility_Session.getPanelW()-formIni.width )/2,(Utility_Session.getPanelH()-formIni.height )/2);
    }  
     
    private void TampilEdit(){
        Id = ItemForm.Id; 
        
        try {           
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT * FROM items WHERE item_id =' "+Id+" ' ");
                if(rs.next()){                    
                    txtKode.setText(rs.getString("item_code"));
                    txtNamaBarang.setText(rs.getString("item_name"));
                    txtKategori.setText(rs.getString("item_category"));
                    txtSatuan.setText(rs.getString("item_unit"));
                    ut.AngkaToRp(txtHargaBeli,rs.getString("item_bprice"));
                    ut.AngkaToRp(txtHargaJual,rs.getString("item_sprice"));
                    ut.AngkaToRp(txtDiscount,rs.getString("item_discount"));
                    
                    HBeli=rs.getString("item_bprice");
                    HJual=rs.getString("item_sprice");
                    Disc=rs.getString("item_discount");
                    
                }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }  
    }

    
    private void PopUpName(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT item_id,item_name FROM items "
                    + "WHERE item_name LIKE '"+txtNamaBarang.getText()+"%'");
            tabelList.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                Utility_Table uts = new Utility_Table();
                uts.Header(tabelList,0,"",-10);
                uts.Header(tabelList,1,"",200);
                tabelList.setBackground(new Color(255,255,255));
                tabelList.setShowGrid(false);
                tabelList.removeColumn(tabelList.getColumnModel().getColumn(0));
                StabelList.setLocation(130, 180);
                
                if (rs.getRow() <= 15) {
                    StabelList.setSize(340, (rs.getRow()*17)+2);
                } else{
                    StabelList.setSize(340, (15*17)+2);                    
                }
                    StabelList.setVisible(true); 
            } else {
                    StabelList.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }


  
    private void TampilKategori(){
        StabelPilih.setVisible(false);
        Pilihan ="kategori";
        try { 
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  category_id,category_name FROM icategory  ORDER BY category_name");
            tabelPilih.setModel(DbUtils.resultSetToTableModel(rs));            
                if(rs.last()){   
                    Utility_Table uts = new Utility_Table();                    
                    uts.Header(tabelPilih,1,"",210);
                    tabelPilih.removeColumn(tabelPilih.getColumnModel().getColumn(0));//Tidak Menampilkan Kolom (0)
                    tabelPilih.setBackground(new Color(255,255,255));
                    tabelPilih.clearSelection();                     
                    tabelPilih.changeSelection (0,0,true, false);                                      
                        if (rs.getRow() <= 15) {
                            StabelPilih.setSize(210, (rs.getRow()*17)+2);
                        } else{
                            StabelPilih.setSize(210, (15*17)+2);                    
                        }                  
                            StabelPilih.setLocation(130, 200);
                            StabelPilih.setVisible(true); 
                            tabelPilih.requestFocus();
                } else {
                    StabelPilih.setVisible(false);                
                }    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }      
    }

    private void TampilSatuan(){
        StabelPilih.setVisible(false);
        Pilihan = "Satuan";
        try {    
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  unit_id,unit_name FROM iunits  ORDER BY unit_name");
            tabelPilih.setModel(DbUtils.resultSetToTableModel(rs));            
                if(rs.last()){   
                    Utility_Table uts = new Utility_Table();                    
                    uts.Header(tabelPilih,1,"",210);
                    tabelPilih.removeColumn(tabelPilih.getColumnModel().getColumn(0));//Tidak Menampilkan Kolom (0)
                    tabelPilih.setBackground(new Color(255,255,255));
                    tabelPilih.clearSelection();                     
                    tabelPilih.changeSelection (0,0,false, false);                                      
                        if (rs.getRow() <= 15) {
                            StabelPilih.setSize(210, (rs.getRow()*17)+2);
                        } else{
                            StabelPilih.setSize(210, (15*17)+2);                    
                        }                  
                            StabelPilih.setLocation(130, 250);
                            StabelPilih.setVisible(true); 
                            tabelPilih.requestFocus();
                } else {
                    StabelPilih.setVisible(false);                
                }    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } 
    }
   
    private void Update(){
        
        if(txtHargaJual.getText().trim().isEmpty()){
            HJual="0";
        }
        if(txtHargaBeli.getText().trim().isEmpty()){
            HBeli="0";
        }
        if(txtDiscount.getText().trim().isEmpty()){
            Disc="0"; 
        }else{
            Disc =txtDiscount.getText();            
        }

        if (txtKode.getText().trim().isEmpty() || txtNamaBarang.getText().trim().isEmpty() || txtKategori.getText().trim().isEmpty() || txtSatuan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Barang Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
                try{   
                        Id = ItemForm.Id; 
                        String sql ="UPDATE items SET item_name='"+txtNamaBarang.getText()+"',item_category='"+txtKategori.getText()+"',"
                                + "item_unit='"+txtSatuan.getText()+"',item_bprice='"+HBeli+"',item_sprice='"+HJual+"',"
                                + "item_Discount='"+Disc+"'    WHERE item_id='"+Id+"' "; 
                       // String sql ="INSERT INTO items(item_code,item_name,item_category,item_unit,item_sprice,item_bprice,item_discount,item_created) "
                       //         + "VALUES ('"+txtKode.getText()+"','"+txtNamaBarang.getText()+"','"+txtKategori.getText()+"',"
                       //         + "'"+txtSatuan.getText()+"','"+HJual+"','"+HBeli+"','"+Disc+"','"+currentTime+"')";
                       
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                    
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data Barang Berhasil");
                        Keluar();                  
                    }                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
        }         
    }
    
 
    private void TampilPilih(){
        if("kategori".equals(Pilihan)){
            Dipilih= tabelPilih.getModel().getValueAt(tabelPilih.getSelectedRow(), 1).toString();
            txtKategori.setText(Dipilih);
            StabelPilih.setVisible(false);
            txtSatuan.requestFocus();
        }else{
            Dipilih= tabelPilih.getModel().getValueAt(tabelPilih.getSelectedRow(), 1).toString();
            txtSatuan.setText(Dipilih);
            StabelPilih.setVisible(false);
            txtHargaBeli.requestFocus();
            
        }
    }
    

    
    private void TambahKategori(){
        txtKode.requestFocus();     //Kembalikan Fokus ke Text Kode
        ItemCategoryAdd.formPemanggil="ItemFormAdd";
        ItemCategoryAdd ica = new ItemCategoryAdd();
        this.getParent().add(ica);
        ica.setVisible(true);
    }
     
    private void TambahSatuan(){
        txtKode.requestFocus();     //Kembalikan Fokus ke Text Kode
        ItemUnitAdd.formPemanggil="ItemFormAdd";
        ItemUnitAdd iud = new ItemUnitAdd();
        this.getParent().add(iud);
        iud.setVisible(true);
    }   

    private void Keluar(){        
        ItemForm itf = new ItemForm();
        this.getParent().add(itf);
        itf.setVisible(true);
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnClose = new khansapos.Utility_ButtonMetro();
        StabelList = new javax.swing.JScrollPane();
        tabelList =  new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        StabelPilih = new javax.swing.JScrollPane();
        tabelPilih = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        txtKode = new javax.swing.JTextField();
        txtNamaBarang = new javax.swing.JTextField();
        txtKategori = new javax.swing.JTextField();
        txtSatuan = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtHargaBeli = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        txtHargaJual = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        txtDiscount = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        btnUpdate = new khansapos.Utility_ButtonFlat();
        btnTambahKategori = new khansapos.Utility_ButtonFlat();
        btnTambahSatuan = new khansapos.Utility_ButtonFlat();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(235, 154, 35)));
        jPanel1.setPreferredSize(new java.awt.Dimension(791, 477));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(235, 154, 35));

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Edit Barang");

        btnClose.setMnemonic('c');
        btnClose.setText("Close");
        btnClose.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 544, Short.MAX_VALUE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, -1));

        StabelList.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        StabelList.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        StabelList.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        StabelList.setFocusable(false);

        tabelList.setForeground(new java.awt.Color(153, 153, 153));
        tabelList.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelList.setGridColor(new java.awt.Color(255, 255, 255));
        tabelList.setShowGrid(false);
        tabelList.setSurrendersFocusOnKeystroke(true);
        tabelList.setTableHeader(null
        );
        StabelList.setViewportView(tabelList);

        jPanel1.add(StabelList, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 340, 0));

        StabelPilih.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
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

        jPanel1.add(StabelPilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 170, 0));

        txtKode.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKode.setForeground(new java.awt.Color(204, 204, 204));
        txtKode.setToolTipText(null);
        txtKode.setBorder(null);
        txtKode.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtKode.setOpaque(false);
        jPanel1.add(txtKode, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 335, -1));

        txtNamaBarang.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtNamaBarang.setToolTipText(null);
        txtNamaBarang.setBorder(null);
        txtNamaBarang.setOpaque(false);
        txtNamaBarang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNamaBarangFocusLost(evt);
            }
        });
        txtNamaBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaBarangKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNamaBarangKeyReleased(evt);
            }
        });
        jPanel1.add(txtNamaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 500, -1));

        txtKategori.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKategori.setToolTipText(null);
        txtKategori.setBorder(null);
        txtKategori.setOpaque(false);
        txtKategori.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtKategoriFocusGained(evt);
            }
        });
        txtKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKategoriKeyPressed(evt);
            }
        });
        jPanel1.add(txtKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 190, -1));

        txtSatuan.setEditable(false);
        txtSatuan.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtSatuan.setToolTipText(null);
        txtSatuan.setBorder(null);
        txtSatuan.setOpaque(false);
        txtSatuan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSatuanFocusGained(evt);
            }
        });
        txtSatuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSatuanKeyPressed(evt);
            }
        });
        jPanel1.add(txtSatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 190, -1));

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Kode");
        jLabel1.setToolTipText(null);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 101, -1, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 335, 10));

        jLabel3.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Nama Barang");
        jLabel3.setToolTipText(null);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 149, -1, 20));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 580, 10));

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Kategori");
        jLabel4.setToolTipText(null);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 197, -1, 20));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 220, -1));

        jLabel5.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Satuan");
        jLabel5.setToolTipText(null);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 245, 76, 20));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 220, 10));

        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Discount");
        jLabel7.setToolTipText(null);
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, 20));

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

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Harga Beli");
        jLabel8.setToolTipText(null);
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, 20));

        jLabel9.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Harga Jual");
        jLabel9.setToolTipText(null);
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, 20));

        txtHargaBeli.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtHargaBeli.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHargaBeli.setToolTipText(null);
        txtHargaBeli.setBorder(null);
        txtHargaBeli.setOpaque(false);
        txtHargaBeli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHargaBeliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHargaBeliFocusLost(evt);
            }
        });
        txtHargaBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHargaBeliKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaBeliKeyTyped(evt);
            }
        });
        jPanel1.add(txtHargaBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 130, -1));
        jPanel1.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 130, 10));

        txtHargaJual.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtHargaJual.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHargaJual.setToolTipText(null);
        txtHargaJual.setBorder(null);
        txtHargaJual.setOpaque(false);
        txtHargaJual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHargaJualFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHargaJualFocusLost(evt);
            }
        });
        txtHargaJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHargaJualKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaJualKeyTyped(evt);
            }
        });
        jPanel1.add(txtHargaJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, 130, -1));
        jPanel1.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 130, 10));

        txtDiscount.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtDiscount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscount.setToolTipText(null);
        txtDiscount.setBorder(null);
        txtDiscount.setOpaque(false);
        txtDiscount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiscountFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiscountFocusLost(evt);
            }
        });
        txtDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDiscountKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiscountKeyTyped(evt);
            }
        });
        jPanel1.add(txtDiscount, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 400, 130, -1));
        jPanel1.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 130, 10));

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
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 210, 30));

        btnTambahKategori.setMnemonic('i');
        btnTambahKategori.setText("+");
        btnTambahKategori.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnTambahKategori.setMouseHover(new java.awt.Color(113, 202, 112));
        btnTambahKategori.setMousePress(new java.awt.Color(204, 204, 204));
        btnTambahKategori.setWarnaBackground(new java.awt.Color(87, 176, 86));
        btnTambahKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKategoriActionPerformed(evt);
            }
        });
        jPanel1.add(btnTambahKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, -1, 30));

        btnTambahSatuan.setMnemonic('n');
        btnTambahSatuan.setText("+");
        btnTambahSatuan.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        btnTambahSatuan.setMouseHover(new java.awt.Color(113, 202, 112));
        btnTambahSatuan.setMousePress(new java.awt.Color(204, 204, 204));
        btnTambahSatuan.setWarnaBackground(new java.awt.Color(87, 176, 86));
        btnTambahSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSatuanActionPerformed(evt);
            }
        });
        jPanel1.add(btnTambahSatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 240, -1, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        Keluar();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void tabelPilihMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPilihMouseClicked
        TampilPilih();
    }//GEN-LAST:event_tabelPilihMouseClicked

    private void tabelPilihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelPilihKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            TampilPilih();
        }
    }//GEN-LAST:event_tabelPilihKeyPressed

    private void txtNamaBarangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNamaBarangFocusLost
        StabelList.setVisible(false);
    }//GEN-LAST:event_txtNamaBarangFocusLost

    private void txtNamaBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaBarangKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtKategori.setText(null);
            txtKategori.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtNamaBarang.setText("");
        }
    }//GEN-LAST:event_txtNamaBarangKeyPressed

    private void txtNamaBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaBarangKeyReleased
        if (txtNamaBarang.getText().trim().isEmpty()) {
            StabelList.setVisible(false);
        } else{
            PopUpName();
        }
    }//GEN-LAST:event_txtNamaBarangKeyReleased

    private void txtKategoriFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKategoriFocusGained
        TampilKategori();
    }//GEN-LAST:event_txtKategoriFocusGained

    private void txtKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtSatuan.setText(null);
            txtSatuan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKategori.setText("");
        }
    }//GEN-LAST:event_txtKategoriKeyPressed

    private void txtSatuanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSatuanFocusGained
        TampilSatuan();
    }//GEN-LAST:event_txtSatuanFocusGained

    private void txtSatuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtHargaBeli.setText(null);
            txtHargaBeli.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSatuan.setText("");
        }
    }//GEN-LAST:event_txtSatuanKeyPressed

    private void txtHargaBeliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaBeliFocusGained
        txtHargaBeli.setText(HBeli);
    }//GEN-LAST:event_txtHargaBeliFocusGained

    private void txtHargaBeliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaBeliFocusLost
        HBeli =txtHargaBeli.getText();          //Menyimpan di Variable HBeli
        if (!txtHargaBeli.getText().trim().isEmpty()){
            ut.AngkaToRp(txtHargaBeli,HBeli);
        }
    }//GEN-LAST:event_txtHargaBeliFocusLost

    private void txtHargaBeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaBeliKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtHargaJual.setText(null);
            txtHargaJual.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtHargaBeli.setText("");
        }
    }//GEN-LAST:event_txtHargaBeliKeyPressed

    private void txtHargaBeliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaBeliKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtHargaBeliKeyTyped

    private void txtHargaJualFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaJualFocusGained
        txtHargaJual.setText(HJual);
    }//GEN-LAST:event_txtHargaJualFocusGained

    private void txtHargaJualFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaJualFocusLost
        HJual =txtHargaJual.getText();          //Menyimpan di Variable HJual
        if (!txtHargaJual.getText().trim().isEmpty()){
            ut.AngkaToRp(txtHargaJual,HJual);
        }
    }//GEN-LAST:event_txtHargaJualFocusLost

    private void txtHargaJualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaJualKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtDiscount.setText(null);
            txtDiscount.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtHargaJual.setText("");
        }
    }//GEN-LAST:event_txtHargaJualKeyPressed

    private void txtHargaJualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaJualKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtHargaJualKeyTyped

    private void txtDiscountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountFocusGained
        txtDiscount.setText(Disc);
    }//GEN-LAST:event_txtDiscountFocusGained

    private void txtDiscountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountFocusLost
        Disc =txtDiscount.getText();            //Menyimpan di Variable Disc
        if (!txtDiscount.getText().trim().isEmpty()){
            ut.AngkaToRp(txtDiscount,Disc);
        }
    }//GEN-LAST:event_txtDiscountFocusLost

    private void txtDiscountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
           Update(); 
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtDiscount.setText("");
        }
    }//GEN-LAST:event_txtDiscountKeyPressed

    private void txtDiscountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtDiscountKeyTyped

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnTambahKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKategoriActionPerformed
        TambahKategori();
    }//GEN-LAST:event_btnTambahKategoriActionPerformed

    private void btnTambahSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSatuanActionPerformed
        TambahSatuan();
    }//GEN-LAST:event_btnTambahSatuanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane StabelList;
    private javax.swing.JScrollPane StabelPilih;
    private khansapos.Utility_ButtonMetro btnClose;
    private khansapos.Utility_ButtonFlat btnTambahKategori;
    private khansapos.Utility_ButtonFlat btnTambahSatuan;
    private khansapos.Utility_ButtonFlat btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable tabelList;
    private javax.swing.JTable tabelPilih;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtHargaBeli;
    private javax.swing.JTextField txtHargaJual;
    private javax.swing.JTextField txtKategori;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtSatuan;
    // End of variables declaration//GEN-END:variables
}
