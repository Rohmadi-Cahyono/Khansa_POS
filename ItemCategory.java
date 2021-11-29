package khansapos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import net.proteanit.sql.DbUtils;

public class ItemCategory extends javax.swing.JInternalFrame {
    java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
    private static String Id;
    
    public ItemCategory() {
        initComponents();
        IframeBorderLess(); 
        SPtableTampil.getViewport().setBackground(new Color(255,255,255));
        SListAdd.setVisible(false);
        SListAdd.getViewport().setBackground(new Color(255,255,255));   
        
        SListEdit.setVisible(false);
        SListEdit.getViewport().setBackground(new Color(255,255,255));
        Tampil(); 
        Tengah(); 
    }
    
   public void Focus(){
       txtSearch.requestFocus();
   }
    
    private void IframeBorderLess() {
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null); 
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setSize(290,480);
    }
   
    private void Tengah(){
        Dimension formIni = this.getSize();
        this.setLocation(( Beranda.PW-formIni.width )/2,(Beranda.PH-formIni.height )/2);
    }
    private void Tampil() {      
        try {            
            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery("SELECT category_id,category_name FROM icategory ORDER BY category_name ");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs));            
            TampilkanDiTabel();         
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void Cari(){
        try {            
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  category_id,category_name FROM icategory WHERE category_name LIKE '%"+txtSearch.getText()+"%' ORDER BY category_name");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs)); 
            TampilkanDiTabel();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }         
    }
    
     private void TampilkanDiTabel() {                     
            Utility_Table ut = new Utility_Table();           
            ut.Header(tableTampil,0,"",-10);
            ut.Header(tableTampil,1,"Kategori Barang",100);
            tableTampil.removeColumn(tableTampil.getColumnModel().getColumn(0)); //tidak menampilkan kolom (index:0)
    }
     
    private void Tambah(){
        this.setVisible(false); 
        txtKategoriAdd.setText("");
        SwingUtilities.invokeLater(() -> {txtKategoriAdd.requestFocusInWindow(); });
        categoryAdd.setSize(421, 156);
        categoryAdd.setLocation(((Beranda.PW+120)-421 )/2,((Beranda.PH+50)-156 )/2);
        categoryAdd.setVisible(true);
    }     
    
        
    private void Edit(){
        this.setVisible(false);
        TampilEdit();
        SwingUtilities.invokeLater(() -> {txtKategoriEdit.requestFocusInWindow(); });
        categoryEdit.setSize(421, 156);
        categoryEdit.setLocation(((Beranda.PW+120)-421 )/2,((Beranda.PH+50)-156 )/2);
        categoryEdit.setVisible(true);
    }
        
    private void Hapus(){       
        if (JOptionPane.showConfirmDialog(null, "Yakin data Kategori akan dihapus?", "Khansa POS",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {            
            try {
                    java.sql.Statement st = con.createStatement();           
                    st.executeUpdate("DELETE FROM icategory WHERE category_id="+Id);            
                    JOptionPane.showMessageDialog(null, "Data Kategori berhasil dihapus!");
                    Tampil();
            } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
            }  
        }        
    }
    
    private void Keluar(){
        this.dispose(); 
    } 
    
    //--------------------------categoryAdd--------------------------------------
        private void Simpan(){             
        if (txtKategoriAdd.getText() == null || txtKategoriAdd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Kategori Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
                try{   
                       String sql ="INSERT INTO icategory(category_name) "
                                + "VALUES ('"+txtKategoriAdd.getText()+"')";                        
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                    
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data Kategori Berhasil");
                        KeluarAdd();                         
                    }                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
        }         
    }
    
        
   private void PopUpKategoriAdd(){
        try {            
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT category_id,category_name FROM icategory "
                    + "WHERE category_name LIKE '"+txtKategoriAdd.getText()+"%'");
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
   
   private void KeluarAdd(){
       categoryAdd.dispose();
       this.setVisible(true);
       Cari();
       txtSearch.requestFocus();
   }
   
   //------------------------------------------------KatergoryEdit---------------------------------------------
   
       private void TampilEdit(){     
        
        try {           
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT * FROM icategory WHERE category_id =' "+Id+" ' ");
                if(rs.next()){                    
                    txtKategoriEdit.setText(rs.getString("category_name"));
                }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }  
    }
        
    private void Update(){             
        if (txtKategoriEdit.getText() == null || txtKategoriEdit.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Kategori Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
                try{                        
                        String sql ="UPDATE icategory SET category_name='"+txtKategoriEdit.getText()+"'  WHERE category_id='"+Id+"' ";                      
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();

                        JOptionPane.showMessageDialog(null, "Update Data Kategori Barang Berhasil");    
                        KeluarEdit();                  
                    }                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
        }         
    }
    
        
   private void PopUpKategoriEdit(){
        try {          
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT category_id,category_name FROM icategory "
                    + "WHERE category_name LIKE '"+txtKategoriEdit.getText()+"%'");
            ListEdit.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){ 
                ListEdit.setBackground(new Color(255,255,255));
                ListEdit.setShowGrid(false);
                ListEdit.removeColumn(ListEdit.getColumnModel().getColumn(0));
                
                if (rs.getRow() <= 3) {
                    SListEdit.setSize(270, (rs.getRow()*17)+2);
                } else{
                    SListEdit.setSize(270, (3*17)+2);                    
                }
                    SListEdit.setVisible(true); 
            } else {
                    SListEdit.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
   
    private void KeluarEdit(){
       categoryEdit.dispose();
       this.setVisible(true);
       Cari();
       txtSearch.requestFocus();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        categoryAdd = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        txtKategoriAdd = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnCloseAdd = new khansapos.Utility_ButtonMetro();
        SListAdd = new javax.swing.JScrollPane();
        ListAdd = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jSeparator5 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        btnSimpan = new khansapos.Utility_ButtonFlat();
        categoryEdit = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        txtKategoriEdit = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnCloseEdit = new khansapos.Utility_ButtonMetro();
        SListEdit = new javax.swing.JScrollPane();
        ListEdit = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jSeparator6 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        btnUpdate = new khansapos.Utility_ButtonFlat();
        panelKategori = new javax.swing.JPanel();
        panelEH = new javax.swing.JPanel();
        btnEdit = new khansapos.Utility_ButtonFlat();
        btnHapus = new khansapos.Utility_ButtonFlat();
        SPtableTampil = new javax.swing.JScrollPane();
        tableTampil = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        txtSearch = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnClose = new khansapos.Utility_ButtonMetro();
        jLabel2 = new javax.swing.JLabel();
        btnTambah = new khansapos.Utility_ButtonFlat();

        categoryAdd.setMaximumSize(new java.awt.Dimension(421, 156));
        categoryAdd.setMinimumSize(new java.awt.Dimension(421, 156));
        categoryAdd.setModal(true);
        categoryAdd.setUndecorated(true);
        categoryAdd.setPreferredSize(new java.awt.Dimension(421, 156));
        categoryAdd.setResizable(false);
        categoryAdd.setSize(new java.awt.Dimension(421, 156));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(87, 176, 86)));
        jPanel1.setPreferredSize(new java.awt.Dimension(421, 156));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtKategoriAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKategoriAdd.setToolTipText(null);
        txtKategoriAdd.setBorder(null);
        txtKategoriAdd.setOpaque(false);
        txtKategoriAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKategoriAddKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKategoriAddKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKategoriAddKeyTyped(evt);
            }
        });
        jPanel1.add(txtKategoriAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 270, -1));

        jPanel2.setBackground(new java.awt.Color(87, 176, 86));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tambah Kategori");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 2, -1, 34));

        btnCloseAdd.setMnemonic('c');
        btnCloseAdd.setText("Close");
        btnCloseAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnCloseAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddActionPerformed(evt);
            }
        });
        jPanel2.add(btnCloseAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 0, 80, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 420, -1));

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

        jPanel1.add(SListAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 90, 260, 0));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 270, 10));

        jLabel5.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Kategori");
        jLabel5.setToolTipText(null);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 70, 20));

        btnSimpan.setMnemonic('s');
        btnSimpan.setText("Simpan");
        btnSimpan.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnSimpan.setMousePress(new java.awt.Color(204, 204, 204));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel1.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 90, 30));

        javax.swing.GroupLayout categoryAddLayout = new javax.swing.GroupLayout(categoryAdd.getContentPane());
        categoryAdd.getContentPane().setLayout(categoryAddLayout);
        categoryAddLayout.setHorizontalGroup(
            categoryAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        categoryAddLayout.setVerticalGroup(
            categoryAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        categoryEdit.setMinimumSize(new java.awt.Dimension(421, 156));
        categoryEdit.setModal(true);
        categoryEdit.setUndecorated(true);
        categoryEdit.setResizable(false);
        categoryEdit.setSize(new java.awt.Dimension(421, 156));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(235, 154, 35)));
        jPanel4.setPreferredSize(new java.awt.Dimension(421, 156));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtKategoriEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKategoriEdit.setToolTipText(null);
        txtKategoriEdit.setBorder(null);
        txtKategoriEdit.setOpaque(false);
        txtKategoriEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKategoriEditKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKategoriEditKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKategoriEditKeyTyped(evt);
            }
        });
        jPanel4.add(txtKategoriEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 270, -1));

        jPanel5.setBackground(new java.awt.Color(235, 154, 35));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Edit Kategori");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 2, -1, 34));

        btnCloseEdit.setMnemonic('c');
        btnCloseEdit.setText("Close");
        btnCloseEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnCloseEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseEditActionPerformed(evt);
            }
        });
        jPanel5.add(btnCloseEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 0, 80, 40));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 420, -1));

        SListEdit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        SListEdit.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SListEdit.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SListEdit.setFocusable(false);
        SListEdit.setPreferredSize(new java.awt.Dimension(200, 200));

        ListEdit.setForeground(new java.awt.Color(153, 153, 153));
        ListEdit.setModel(new javax.swing.table.DefaultTableModel(
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
        ListEdit.setGridColor(new java.awt.Color(255, 255, 255));
        ListEdit.setShowGrid(false);
        ListEdit.setSurrendersFocusOnKeystroke(true);
        ListEdit.setTableHeader(null);
        SListEdit.setViewportView(ListEdit);

        jPanel4.add(SListEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 90, 260, 0));
        jPanel4.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 270, 10));

        jLabel6.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Kategori");
        jLabel6.setToolTipText(null);
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 70, 20));

        btnUpdate.setMnemonic('u');
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
        jPanel4.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 90, 30));

        javax.swing.GroupLayout categoryEditLayout = new javax.swing.GroupLayout(categoryEdit.getContentPane());
        categoryEdit.getContentPane().setLayout(categoryEditLayout);
        categoryEditLayout.setHorizontalGroup(
            categoryEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        categoryEditLayout.setVerticalGroup(
            categoryEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setClosable(true);
        setMaximumSize(new java.awt.Dimension(306, 510));
        setMinimumSize(new java.awt.Dimension(306, 510));
        setPreferredSize(new java.awt.Dimension(306, 510));

        panelKategori.setBackground(new java.awt.Color(255, 255, 255));
        panelKategori.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 123, 255)));
        panelKategori.setMaximumSize(new java.awt.Dimension(290, 480));
        panelKategori.setMinimumSize(new java.awt.Dimension(290, 480));
        panelKategori.setPreferredSize(new java.awt.Dimension(290, 480));
        panelKategori.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        panelKategori.add(panelEH, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 130, 0));

        SPtableTampil.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SPtableTampil.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tableTampil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableTampil.setFocusable(false);
        tableTampil.setMaximumSize(new java.awt.Dimension(150, 80));
        tableTampil.setMinimumSize(new java.awt.Dimension(150, 80));
        tableTampil.setTableHeader(null);
        tableTampil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTampilMouseClicked(evt);
            }
        });
        SPtableTampil.setViewportView(tableTampil);

        panelKategori.add(SPtableTampil, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 270, 350));

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
        panelKategori.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 110, 20));

        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator1.setToolTipText("");
        panelKategori.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 110, 10));

        jPanel3.setBackground(new java.awt.Color(0, 123, 255));

        jLabel1.setBackground(new java.awt.Color(235, 154, 35));
        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText(" Kategori Barang");
        jLabel1.setToolTipText("");

        btnClose.setMnemonic('c');
        btnClose.setText("Close");
        btnClose.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClose, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelKategori.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\Search-icon.png")); // NOI18N
        jLabel2.setToolTipText(null);
        panelKategori.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, -1, -1));

        btnTambah.setMnemonic('t');
        btnTambah.setText("Tambah");
        btnTambah.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        panelKategori.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelKategori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setBounds(0, 0, 306, 510);
    }// </editor-fold>//GEN-END:initComponents

    private void tableTampilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTampilMouseClicked
         Id= tableTampil.getModel().getValueAt(tableTampil.getSelectedRow(), 0).toString(); //Ambil nilai kolom (0) dan masukkan ke variabel Id
        //setId(Id);
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

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        Keluar();
    }//GEN-LAST:event_btnCloseActionPerformed

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

    private void txtKategoriAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Simpan();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKategoriAdd.setText("");
        }
    }//GEN-LAST:event_txtKategoriAddKeyPressed

    private void txtKategoriAddKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriAddKeyReleased
        if (txtKategoriAdd.getText().trim().isEmpty()) {
            SListAdd.setVisible(false);
        } else{
            PopUpKategoriAdd();
        }
    }//GEN-LAST:event_txtKategoriAddKeyReleased

    private void txtKategoriAddKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriAddKeyTyped
        if (txtKategoriAdd.getText().length() >= 15 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtKategoriAddKeyTyped

    private void btnCloseAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseAddActionPerformed
        KeluarAdd();
    }//GEN-LAST:event_btnCloseAddActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        Simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtKategoriEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Update();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKategoriEdit.setText("");
        }
    }//GEN-LAST:event_txtKategoriEditKeyPressed

    private void txtKategoriEditKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriEditKeyReleased
        if (txtKategoriEdit.getText().trim().isEmpty()) {
            SListEdit.setVisible(false);
        } else{
            PopUpKategoriEdit();
        }
    }//GEN-LAST:event_txtKategoriEditKeyReleased

    private void txtKategoriEditKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriEditKeyTyped
        if (txtKategoriEdit.getText().length() >= 15 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtKategoriEditKeyTyped

    private void btnCloseEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseEditActionPerformed
        KeluarEdit();
    }//GEN-LAST:event_btnCloseEditActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Update();
    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ListAdd;
    private javax.swing.JTable ListEdit;
    private javax.swing.JScrollPane SListAdd;
    private javax.swing.JScrollPane SListEdit;
    private javax.swing.JScrollPane SPtableTampil;
    private khansapos.Utility_ButtonMetro btnClose;
    private khansapos.Utility_ButtonMetro btnCloseAdd;
    private khansapos.Utility_ButtonMetro btnCloseEdit;
    private khansapos.Utility_ButtonFlat btnEdit;
    private khansapos.Utility_ButtonFlat btnHapus;
    private khansapos.Utility_ButtonFlat btnSimpan;
    private khansapos.Utility_ButtonFlat btnTambah;
    private khansapos.Utility_ButtonFlat btnUpdate;
    private javax.swing.JDialog categoryAdd;
    private javax.swing.JDialog categoryEdit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JPanel panelEH;
    private javax.swing.JPanel panelKategori;
    private javax.swing.JTable tableTampil;
    private javax.swing.JTextField txtKategoriAdd;
    private javax.swing.JTextField txtKategoriEdit;
    public javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
