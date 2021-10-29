package khansapos;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import net.proteanit.sql.DbUtils;

public class ItemFormAdd extends javax.swing.JInternalFrame {
    private int X,Y;
    
    public ItemFormAdd() {
        initComponents();
        IframeBorderLess();
         
        SList.setVisible(false);
        SList.getViewport().setBackground(Color.WHITE);
        SPilih.setVisible(false);

        //SetFocus pertama form aktif
        SwingUtilities.invokeLater(() -> {
            txtKode.requestFocusInWindow();
        });
    }
        
    private void IframeBorderLess(){
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
    
   
    private void Keluar(){
        ItemForm itf = new ItemForm();
        this.getParent().add(itf);
        itf.setVisible(true);
        this.setVisible(false);     
    }
    
    private void PopUpCode(){
        try {
            java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT item_id,item_code FROM items WHERE item_code LIKE '"+txtKode.getText()+"%'");
            List.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                Utility_Table uts = new Utility_Table();
                uts.Header(List,0,"",-10);
                uts.Header(List,1,"",200);
                List.setBackground(new Color(255,255,255));
                List.setShowGrid(false);
                List.removeColumn(List.getColumnModel().getColumn(0));
                SList.setLocation(130, 130);
                
                if (rs.getRow() <= 15) {
                    SList.setSize(340, (rs.getRow()*17)+2);
                } else{
                    SList.setSize(340, (15*17)+2);                    
                }
                    SList.setVisible(true); 
            } else {
                    SList.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
  
        private void PopUpName(){
        try {
            java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT item_id,item_name FROM items WHERE item_name LIKE '"+txtNamaBarang.getText()+"%'");
            List.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                Utility_Table uts = new Utility_Table();
                uts.Header(List,0,"",-10);
                uts.Header(List,1,"",200);
                List.setBackground(new Color(255,255,255));
                List.setShowGrid(false);
                List.removeColumn(List.getColumnModel().getColumn(0));
                SList.setLocation(130, 180);
                
                if (rs.getRow() <= 15) {
                    SList.setSize(340, (rs.getRow()*17)+2);
                } else{
                    SList.setSize(340, (15*17)+2);                    
                }
                    SList.setVisible(true); 
            } else {
                    SList.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
   
    private void Bersih(){
        txtDiscount.setText("");
        txtHargaBeli.setText("");
        txtHargaJual.setText("");       
        txtSatuan.setText("");
        txtKategori.setText("");
        txtNamaBarang.setText("");
        txtKode.setText("");
    }
   
    private void Simpan(){
             
        if (txtNamaBarang.getText() == null || txtNamaBarang.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Barang Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
                try{   
                        java.util.Date dt = new java.util.Date();
                        java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(dt);
                    
                        String sql ="INSERT INTO items(item_code,item_name,item_category,item_unit,item_sprice,itembprice,item_created) "
                                + "VALUES ('"+txtKode.getText()+"','"+txtNamaBarang.getText()+"','"+txtKategori.getText()+"',"
                                + "'"+txtSatuan.getText()+"','"+txtHargaBeli+"','"+txtHargaJual+"','"+txtDiscount+"','"+currentTime+"')";
                        java.sql.Connection con=new Utility_KoneksiDB().koneksi();
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
    
    private void TampilSatuan(){
        try {
            DefaultListModel model = new DefaultListModel();
            java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
            java.sql.Statement st = con.createStatement();           
            try (java.sql.ResultSet rs = st.executeQuery("SELECT  unit_id,unit_name FROM iunits  ORDER BY unit_name")) {
                while (rs.next()) {
                    String itemCode = rs.getString("unit_name"); 
                    model.addElement(itemCode);
                }
                Pilih.setModel(model);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }      
   
   

    
        SPilih.setVisible(true);
        SPilih.setLocation(130,250);
        SPilih.setSize(220,200);
        Pilih.setSelectedIndex(0);
        Pilih.requestFocus();
    }
        
    private void PilihSatuan(){
        txtSatuan.setText(Pilih.getSelectedValue());
        SPilih.setVisible(false);
        txtHargaBeli.requestFocus();
    }  
    
    private void TambahSatuan(){
        ItemUnitAdd iud = new ItemUnitAdd();
         this.getParent().add(iud);
         iud.setLocation(X, Y);
        iud.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbExit = new javax.swing.JLabel();
        SList = new javax.swing.JScrollPane();
        List = new javax.swing.JTable();
        SPilih = new javax.swing.JScrollPane();
        Pilih = new javax.swing.JList<>();
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
        btnSimpan = new javax.swing.JLabel();
        btnBersih = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtHargaBeli = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        txtHargaJual = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        txtDiscount = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        btnTambahSatuan = new javax.swing.JLabel();
        btnTambahKategori = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1246, 714));

        jPanel3.setBackground(new java.awt.Color(248, 251, 251));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(87, 176, 86));

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tambah Barang");

        lbExit.setBackground(new java.awt.Color(85, 118, 118));
        lbExit.setDisplayedMnemonic('c');
        lbExit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        lbExit.setForeground(new java.awt.Color(255, 0, 0));
        lbExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbExit.setText("Close");
        lbExit.setToolTipText(null);
        lbExit.setBorder(null);
        lbExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbExit.setOpaque(true);
        lbExit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                lbExitFocusLost(evt);
            }
        });
        lbExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbExitMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbExit, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lbExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, -1));

        SList.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        SList.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SList.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SList.setFocusable(false);

        List.setForeground(new java.awt.Color(153, 153, 153));
        List.setModel(new javax.swing.table.DefaultTableModel(
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
        List.setGridColor(new java.awt.Color(255, 255, 255));
        List.setShowGrid(false);
        List.setSurrendersFocusOnKeystroke(true);
        List.setTableHeader(null
        );
        SList.setViewportView(List);

        jPanel1.add(SList, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 500, 0));

        SPilih.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SPilih.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        Pilih.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        Pilih.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Botol", "Box", "Cup", "Dus", "Galon", "Kaleng", "Meter", "Pak", "Pasang", "Pcs", "Roll", "Sachet", "Set", "Strip", "Tube" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        Pilih.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                PilihFocusLost(evt);
            }
        });
        Pilih.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PilihMouseClicked(evt);
            }
        });
        Pilih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PilihKeyPressed(evt);
            }
        });
        SPilih.setViewportView(Pilih);

        jPanel1.add(SPilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 370, 220, 0));

        txtKode.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKode.setToolTipText(null);
        txtKode.setBorder(null);
        txtKode.setOpaque(false);
        txtKode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtKodeFocusLost(evt);
            }
        });
        txtKode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKodeKeyReleased(evt);
            }
        });
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
        txtKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKategoriKeyPressed(evt);
            }
        });
        jPanel1.add(txtKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 190, -1));

        txtSatuan.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtSatuan.setToolTipText(null);
        txtSatuan.setBorder(null);
        txtSatuan.setOpaque(false);
        txtSatuan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSatuanFocusGained(evt);
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

        btnSimpan.setBackground(new java.awt.Color(87, 176, 86));
        btnSimpan.setDisplayedMnemonic('s');
        btnSimpan.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSimpan.setText("Simpan");
        btnSimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSimpan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimpan.setOpaque(true);
        btnSimpan.setPreferredSize(new java.awt.Dimension(75, 25));
        btnSimpan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnSimpanFocusLost(evt);
            }
        });
        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSimpanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSimpanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSimpanMouseExited(evt);
            }
        });
        jPanel1.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 213, 36));

        btnBersih.setBackground(new java.awt.Color(235, 154, 35));
        btnBersih.setDisplayedMnemonic('h');
        btnBersih.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnBersih.setForeground(new java.awt.Color(255, 255, 255));
        btnBersih.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnBersih.setText("Bersih");
        btnBersih.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBersih.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBersih.setOpaque(true);
        btnBersih.setPreferredSize(new java.awt.Dimension(75, 25));
        btnBersih.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnBersihFocusLost(evt);
            }
        });
        btnBersih.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBersihMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBersihMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBersihMouseExited(evt);
            }
        });
        jPanel1.add(btnBersih, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 420, 86, 35));

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
        txtHargaBeli.setToolTipText(null);
        txtHargaBeli.setBorder(null);
        txtHargaBeli.setOpaque(false);
        txtHargaBeli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHargaBeliFocusGained(evt);
            }
        });
        jPanel1.add(txtHargaBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 174, -1));
        jPanel1.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 174, 10));

        txtHargaJual.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtHargaJual.setToolTipText(null);
        txtHargaJual.setBorder(null);
        txtHargaJual.setOpaque(false);
        txtHargaJual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHargaJualFocusGained(evt);
            }
        });
        jPanel1.add(txtHargaJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, 174, -1));
        jPanel1.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 174, 10));

        txtDiscount.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtDiscount.setToolTipText(null);
        txtDiscount.setBorder(null);
        txtDiscount.setOpaque(false);
        txtDiscount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiscountFocusGained(evt);
            }
        });
        jPanel1.add(txtDiscount, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 400, 174, -1));
        jPanel1.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 174, 10));

        btnTambahSatuan.setBackground(new java.awt.Color(87, 176, 86));
        btnTambahSatuan.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnTambahSatuan.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahSatuan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnTambahSatuan.setText("+");
        btnTambahSatuan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambahSatuan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTambahSatuan.setOpaque(true);
        btnTambahSatuan.setPreferredSize(new java.awt.Dimension(75, 25));
        btnTambahSatuan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahSatuanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTambahSatuanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTambahSatuanMouseExited(evt);
            }
        });
        jPanel1.add(btnTambahSatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 30, 30));

        btnTambahKategori.setBackground(new java.awt.Color(87, 176, 86));
        btnTambahKategori.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnTambahKategori.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahKategori.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnTambahKategori.setText("+");
        btnTambahKategori.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambahKategori.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTambahKategori.setOpaque(true);
        btnTambahKategori.setPreferredSize(new java.awt.Dimension(75, 25));
        btnTambahKategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahKategoriMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTambahKategoriMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTambahKategoriMouseExited(evt);
            }
        });
        jPanel1.add(btnTambahKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 190, 30, 30));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(183, 183, 183)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(256, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 1246, 714);
    }// </editor-fold>//GEN-END:initComponents

    private void lbExitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lbExitFocusLost
        Keluar();
    }//GEN-LAST:event_lbExitFocusLost

    private void lbExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbExitMouseClicked
        Keluar();
    }//GEN-LAST:event_lbExitMouseClicked

    private void lbExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbExitMouseEntered
        lbExit.setForeground(new Color(255,255,255));
        lbExit.setBackground(new Color(217,0,0));
    }//GEN-LAST:event_lbExitMouseEntered

    private void lbExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbExitMouseExited
        lbExit.setForeground(new Color(255,0,0));
        lbExit.setBackground(new Color(85,118,118));
    }//GEN-LAST:event_lbExitMouseExited

    private void PilihFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PilihFocusLost
        SPilih.setVisible(false);
    }//GEN-LAST:event_PilihFocusLost

    private void PilihMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PilihMouseClicked
        PilihSatuan();
    }//GEN-LAST:event_PilihMouseClicked

    private void PilihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PilihKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            PilihSatuan();
        }
    }//GEN-LAST:event_PilihKeyPressed

    private void txtKodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKodeFocusLost
        SList.setVisible(false);
    }//GEN-LAST:event_txtKodeFocusLost

    private void txtKodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtNamaBarang.setText(null);
            txtNamaBarang.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKode.setText("");
        }
    }//GEN-LAST:event_txtKodeKeyPressed

    private void txtKodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeKeyReleased
        if (txtKode.getText().trim().isEmpty()) {
            SList.setVisible(false);
        } else{
            PopUpCode();
        }
    }//GEN-LAST:event_txtKodeKeyReleased

    private void txtNamaBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaBarangKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtKategori.setText(null);
            txtKategori.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtNamaBarang.setText("");
        }
    }//GEN-LAST:event_txtNamaBarangKeyPressed

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

    private void btnSimpanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSimpanFocusLost
        Simpan();
    }//GEN-LAST:event_btnSimpanFocusLost

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
        Simpan();
    }//GEN-LAST:event_btnSimpanMouseClicked

    private void btnSimpanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseEntered
        btnSimpan.setForeground(new Color(0,0,0));
        btnSimpan.setBackground(new Color(113,202,112));
    }//GEN-LAST:event_btnSimpanMouseEntered

    private void btnSimpanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseExited
        btnSimpan.setForeground(new Color(255,255,255));
        btnSimpan.setBackground(new Color(87,176,86));
    }//GEN-LAST:event_btnSimpanMouseExited

    private void btnBersihFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnBersihFocusLost
        Bersih();
    }//GEN-LAST:event_btnBersihFocusLost

    private void btnBersihMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBersihMouseClicked
        Bersih();
    }//GEN-LAST:event_btnBersihMouseClicked

    private void btnBersihMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBersihMouseEntered
        btnBersih.setBackground(new Color(255,180,61));
        btnBersih.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_btnBersihMouseEntered

    private void btnBersihMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBersihMouseExited
        btnBersih.setBackground(new Color(235,154,35));
        btnBersih.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnBersihMouseExited

    private void txtHargaBeliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaBeliFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaBeliFocusGained

    private void txtHargaJualFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHargaJualFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaJualFocusGained

    private void txtDiscountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscountFocusGained

    private void txtNamaBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaBarangKeyReleased
        if (txtNamaBarang.getText().trim().isEmpty()) {
            SList.setVisible(false);
        } else{
            PopUpName();
        }
    }//GEN-LAST:event_txtNamaBarangKeyReleased

    private void txtNamaBarangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNamaBarangFocusLost
        SList.setVisible(false);
    }//GEN-LAST:event_txtNamaBarangFocusLost

    private void btnTambahSatuanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahSatuanMouseClicked
        X= evt.getXOnScreen()-70;
        Y= evt.getYOnScreen()-60;
        
        TambahSatuan();
    }//GEN-LAST:event_btnTambahSatuanMouseClicked

    private void btnTambahSatuanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahSatuanMouseEntered
        btnTambahSatuan.setForeground(new Color(0,0,0));
        btnTambahSatuan.setBackground(new Color(113,202,112));
    }//GEN-LAST:event_btnTambahSatuanMouseEntered

    private void btnTambahSatuanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahSatuanMouseExited
        btnTambahSatuan.setForeground(new Color(255,255,255));
        btnTambahSatuan.setBackground(new Color(87,176,86));
    }//GEN-LAST:event_btnTambahSatuanMouseExited

    private void btnTambahKategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahKategoriMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTambahKategoriMouseClicked

    private void btnTambahKategoriMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahKategoriMouseEntered
        btnTambahKategori.setForeground(new Color(0,0,0));
        btnTambahKategori.setBackground(new Color(113,202,112));
    }//GEN-LAST:event_btnTambahKategoriMouseEntered

    private void btnTambahKategoriMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahKategoriMouseExited
        btnTambahKategori.setForeground(new Color(255,255,255));
        btnTambahKategori.setBackground(new Color(87,176,86));
    }//GEN-LAST:event_btnTambahKategoriMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable List;
    private javax.swing.JList<String> Pilih;
    private javax.swing.JScrollPane SList;
    private javax.swing.JScrollPane SPilih;
    private static javax.swing.JLabel btnBersih;
    private static javax.swing.JLabel btnSimpan;
    private static javax.swing.JLabel btnTambahKategori;
    private static javax.swing.JLabel btnTambahSatuan;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator9;
    private static javax.swing.JLabel lbExit;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtHargaBeli;
    private javax.swing.JTextField txtHargaJual;
    private javax.swing.JTextField txtKategori;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtSatuan;
    // End of variables declaration//GEN-END:variables
}
