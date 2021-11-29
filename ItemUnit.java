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

public class ItemUnit extends javax.swing.JInternalFrame {
    java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
    private static String  Id;
        
    public ItemUnit() {
        initComponents();
        IframeBorderLess(); 
        SPtableTampil.getViewport().setBackground(new Color(255,255,255));
        SListAdd.setVisible(false);
        SListAdd.getViewport().setBackground(new Color(255,255,255));   
        
        SListEdit.setVisible(false);
        SListEdit.getViewport().setBackground(new Color(255,255,255));
        TampilSatuan(); 
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
        
    private void TampilSatuan() {      
        try {            
            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery("SELECT unit_id,unit_name FROM iunits ORDER BY unit_name ");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs));            
            TampilkanDiTabel();         
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void Cari(){
        try {           
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  unit_id,unit_name FROM iunits WHERE unit_name LIKE '%"+txtSearch.getText()+"%' ORDER BY unit_name");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs)); 
            TampilkanDiTabel();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }         
    }
    

    private void Tambah(){
        this.setVisible(false);
        txtSatuanAdd.setText("");
        SwingUtilities.invokeLater(() -> {txtSatuanAdd.requestFocusInWindow(); });
        UnitAdd.setSize(421, 156);                      
        UnitAdd.setLocation(((Beranda.PW+120)-421 )/2,((Beranda.PH+50)-156 )/2);
        UnitAdd.setVisible(true);
    }     
    
        
    private void Edit(){
        this.setVisible(false);
        txtSatuanEdit.setText("");
        TampilEdit();
        SwingUtilities.invokeLater(() -> {txtSatuanEdit.requestFocusInWindow(); });
        UnitEdit.setSize(421, 156);                      
        UnitEdit.setLocation(((Beranda.PW+120)-421 )/2,((Beranda.PH+50)-156 )/2);
        UnitEdit.setVisible(true);
    }
    
    private void Hapus(){       
        if (JOptionPane.showConfirmDialog(null, "Yakin data Satuan akan dihapus?", "Khansa POS",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {            
            try {
                    java.sql.Statement st = con.createStatement();           
                    st.executeUpdate("DELETE FROM iunits WHERE unit_id="+Id);            
                    JOptionPane.showMessageDialog(null, "Data Satuan berhasil dihapus!");
                    TampilSatuan();
            } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
            }  
        }        
    }
    
     private void TampilkanDiTabel() {                     
            Utility_Table ut = new Utility_Table();           
            ut.Header(tableTampil,0,"",-10);
            ut.Header(tableTampil,1,"Satuan Barang",100);
            tableTampil.removeColumn(tableTampil.getColumnModel().getColumn(0)); //tidak menampilkan kolom (index:0)
    }
     
    private void Keluar(){
        this.dispose();
    }     
    
    //-----------------------------------UnitAdd-------------------------------------------------------------------------
    
       private void PopUpSatuan(){
        try {           
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT unit_id,unit_name FROM iunits WHERE unit_name LIKE '"+txtSatuanAdd.getText()+"%'");
            ListAdd.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                ListAdd.setBackground(new Color(255,255,255));
                ListAdd.setShowGrid(false);
                ListAdd.removeColumn(ListAdd.getColumnModel().getColumn(0));
                
                if (rs.getRow() <= 3) {
                    SListAdd.setSize(200, (rs.getRow()*17)+2);
                } else{
                    SListAdd.setSize(200, (3*17)+2);                    
                }
                    SListAdd.setVisible(true); 
            } else {
                    SListAdd.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
    
    private void SimpanSatuan(){             
        if (txtSatuanAdd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Satuan Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
                try{   
                       String sql ="INSERT INTO iunits(unit_name) "
                                + "VALUES ('"+txtSatuanAdd.getText()+"')";
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                    
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data satuan Berhasil");
                        KeluarAdd();                  
                    }                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
        }         
    }
            
    private void KeluarAdd(){
       UnitAdd.dispose();
       this.setVisible(true);
       Cari();
       txtSearch.requestFocus();
   }

//-------------------------------------------------------UnitEdit-------------------------------------------------------------------------
    private void TampilEdit(){
        try {           
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT * FROM iunits WHERE unit_id =' "+Id+" ' ");
                if(rs.next()){                    
                    txtSatuanEdit.setText(rs.getString("unit_name"));
                }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }  
    }
    
    private void PopUpKategori(){
        try {          
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT unit_id,unit_name FROM iunits WHERE unit_name LIKE '"+txtSatuanEdit.getText()+"%'");
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
    
    private void Update(){             
        if (txtSatuanEdit.getText() == null || txtSatuanEdit.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Satuan Barang Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
                try{   
                     //   Id = ItemUnit.Id;                        
                        String sql ="UPDATE iunits SET unit_name='"+txtSatuanEdit.getText()+"'  WHERE unit_id='"+Id+"' ";                      
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();

                        JOptionPane.showMessageDialog(null, "Update Data Satuan Barang Berhasil");               
                        KeluarEdit();                  
                    }                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
        }         
    }
    
    
    private void KeluarEdit(){
       UnitEdit.dispose();
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

        UnitAdd = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnCloseAdd = new khansapos.Utility_ButtonMetro();
        jLabel5 = new javax.swing.JLabel();
        txtSatuanAdd = new javax.swing.JTextField();
        SListAdd = new javax.swing.JScrollPane();
        ListAdd = new javax.swing.JTable();
        jSeparator5 = new javax.swing.JSeparator();
        btnSimpanAdd = new khansapos.Utility_ButtonFlat();
        UnitEdit = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        txtSatuanEdit = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
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
        btnUpdateAdd = new khansapos.Utility_ButtonFlat();
        jPanel1 = new javax.swing.JPanel();
        panelEH = new javax.swing.JPanel();
        btnEdit = new khansapos.Utility_ButtonFlat();
        btnHapus = new khansapos.Utility_ButtonFlat();
        panelSatuan = new javax.swing.JPanel();
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

        UnitAdd.setModal(true);
        UnitAdd.setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(87, 176, 86)));
        jPanel2.setPreferredSize(new java.awt.Dimension(421, 156));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(87, 176, 86));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tambah Satuan");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 2, -1, 34));

        btnCloseAdd.setMnemonic('c');
        btnCloseAdd.setText("Close");
        btnCloseAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnCloseAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddActionPerformed(evt);
            }
        });
        jPanel4.add(btnCloseAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 80, 40));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, -1));

        jLabel5.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Satuan");
        jLabel5.setToolTipText(null);
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 76, 20));

        txtSatuanAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtSatuanAdd.setToolTipText(null);
        txtSatuanAdd.setBorder(null);
        txtSatuanAdd.setOpaque(false);
        txtSatuanAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSatuanAddKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSatuanAddKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSatuanAddKeyTyped(evt);
            }
        });
        jPanel2.add(txtSatuanAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 190, -1));

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

        jPanel2.add(SListAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 90, -1, 0));
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 220, 10));

        btnSimpanAdd.setMnemonic('s');
        btnSimpanAdd.setText("Simpan");
        btnSimpanAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnSimpanAdd.setMousePress(new java.awt.Color(204, 204, 204));
        btnSimpanAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanAddActionPerformed(evt);
            }
        });
        jPanel2.add(btnSimpanAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 90, 30));

        javax.swing.GroupLayout UnitAddLayout = new javax.swing.GroupLayout(UnitAdd.getContentPane());
        UnitAdd.getContentPane().setLayout(UnitAddLayout);
        UnitAddLayout.setHorizontalGroup(
            UnitAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        UnitAddLayout.setVerticalGroup(
            UnitAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        UnitEdit.setModal(true);
        UnitEdit.setUndecorated(true);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(235, 154, 35)));
        jPanel5.setPreferredSize(new java.awt.Dimension(421, 156));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSatuanEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtSatuanEdit.setToolTipText(null);
        txtSatuanEdit.setBorder(null);
        txtSatuanEdit.setOpaque(false);
        txtSatuanEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSatuanEditKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSatuanEditKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSatuanEditKeyTyped(evt);
            }
        });
        jPanel5.add(txtSatuanEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 270, -1));

        jPanel6.setBackground(new java.awt.Color(235, 154, 35));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tambah Satuan");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 2, -1, 34));

        btnCloseEdit.setMnemonic('c');
        btnCloseEdit.setText("Close");
        btnCloseEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnCloseEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseEditActionPerformed(evt);
            }
        });
        jPanel6.add(btnCloseEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 0, 80, 40));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 420, -1));

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
        ListEdit.setTableHeader(null
        );
        SListEdit.setViewportView(ListEdit);

        jPanel5.add(SListEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 90, 260, 0));
        jPanel5.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 270, 10));

        jLabel6.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Satuan");
        jLabel6.setToolTipText(null);
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 70, 20));

        btnUpdateAdd.setMnemonic('u');
        btnUpdateAdd.setText("Update");
        btnUpdateAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnUpdateAdd.setMouseHover(new java.awt.Color(255, 180, 61));
        btnUpdateAdd.setMousePress(new java.awt.Color(255, 231, 112));
        btnUpdateAdd.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnUpdateAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateAddActionPerformed(evt);
            }
        });
        jPanel5.add(btnUpdateAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 90, 30));

        javax.swing.GroupLayout UnitEditLayout = new javax.swing.GroupLayout(UnitEdit.getContentPane());
        UnitEdit.getContentPane().setLayout(UnitEditLayout);
        UnitEditLayout.setHorizontalGroup(
            UnitEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        UnitEditLayout.setVerticalGroup(
            UnitEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setClosable(true);
        setMaximumSize(new java.awt.Dimension(306, 510));
        setMinimumSize(new java.awt.Dimension(306, 510));
        setPreferredSize(new java.awt.Dimension(306, 510));

        jPanel1.setBackground(new java.awt.Color(248, 251, 251));
        jPanel1.setMaximumSize(new java.awt.Dimension(290, 480));
        jPanel1.setMinimumSize(new java.awt.Dimension(290, 480));
        jPanel1.setPreferredSize(new java.awt.Dimension(290, 480));
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

        panelSatuan.setBackground(new java.awt.Color(255, 255, 255));
        panelSatuan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelSatuan.setMaximumSize(new java.awt.Dimension(290, 480));
        panelSatuan.setMinimumSize(new java.awt.Dimension(290, 480));
        panelSatuan.setPreferredSize(new java.awt.Dimension(290, 480));
        panelSatuan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        tableTampil.setTableHeader(null);
        tableTampil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTampilMouseClicked(evt);
            }
        });
        SPtableTampil.setViewportView(tableTampil);
        if (tableTampil.getColumnModel().getColumnCount() > 0) {
            tableTampil.getColumnModel().getColumn(0).setResizable(false);
            tableTampil.getColumnModel().getColumn(1).setResizable(false);
        }

        panelSatuan.add(SPtableTampil, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 118, 270, 350));

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
        panelSatuan.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 110, 20));

        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator1.setToolTipText("");
        panelSatuan.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 110, 10));

        jPanel3.setBackground(new java.awt.Color(0, 123, 255));

        jLabel1.setBackground(new java.awt.Color(235, 154, 35));
        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText(" Satuan Barang");
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

        panelSatuan.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\Search-icon.png")); // NOI18N
        jLabel2.setToolTipText(null);
        panelSatuan.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, -1, -1));

        btnTambah.setMnemonic('t');
        btnTambah.setText("Tambah");
        btnTambah.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnTambah.setMousePress(new java.awt.Color(204, 204, 204));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        panelSatuan.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 90, 30));

        jPanel1.add(panelSatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setBounds(0, 0, 306, 510);
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

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        Keluar();
    }//GEN-LAST:event_btnCloseActionPerformed

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

    private void txtSatuanAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            SimpanSatuan();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSatuanAdd.setText("");
        }
    }//GEN-LAST:event_txtSatuanAddKeyPressed

    private void txtSatuanAddKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanAddKeyReleased
        if (txtSatuanAdd.getText().trim().isEmpty()) {
            SListAdd.setVisible(false);
        } else{
            PopUpSatuan();
        }
    }//GEN-LAST:event_txtSatuanAddKeyReleased

    private void txtSatuanAddKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanAddKeyTyped
        if (txtSatuanAdd.getText().length() >= 15 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSatuanAddKeyTyped

    private void btnSimpanAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanAddActionPerformed
        SimpanSatuan();
    }//GEN-LAST:event_btnSimpanAddActionPerformed

    private void txtSatuanEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Update();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSatuanEdit.setText("");
        }
    }//GEN-LAST:event_txtSatuanEditKeyPressed

    private void txtSatuanEditKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanEditKeyReleased
        if (txtSatuanEdit.getText().trim().isEmpty()) {
            SListEdit.setVisible(false);
        } else{
            PopUpKategori();
        }
    }//GEN-LAST:event_txtSatuanEditKeyReleased

    private void txtSatuanEditKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanEditKeyTyped
        if (txtSatuanEdit.getText().length() >= 15 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSatuanEditKeyTyped

    private void btnCloseEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseEditActionPerformed
        KeluarEdit();
    }//GEN-LAST:event_btnCloseEditActionPerformed

    private void btnUpdateAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateAddActionPerformed
        Update();
    }//GEN-LAST:event_btnUpdateAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ListAdd;
    private javax.swing.JTable ListEdit;
    private javax.swing.JScrollPane SListAdd;
    private javax.swing.JScrollPane SListEdit;
    private javax.swing.JScrollPane SPtableTampil;
    private javax.swing.JDialog UnitAdd;
    private javax.swing.JDialog UnitEdit;
    private khansapos.Utility_ButtonMetro btnClose;
    private khansapos.Utility_ButtonMetro btnCloseAdd;
    private khansapos.Utility_ButtonMetro btnCloseEdit;
    private khansapos.Utility_ButtonFlat btnEdit;
    private khansapos.Utility_ButtonFlat btnHapus;
    private khansapos.Utility_ButtonFlat btnSimpanAdd;
    private khansapos.Utility_ButtonFlat btnTambah;
    private khansapos.Utility_ButtonFlat btnUpdateAdd;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JPanel panelEH;
    private javax.swing.JPanel panelSatuan;
    private javax.swing.JTable tableTampil;
    private javax.swing.JTextField txtSatuanAdd;
    private javax.swing.JTextField txtSatuanEdit;
    public javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
