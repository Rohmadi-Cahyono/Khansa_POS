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
    java.sql.Connection con =  new UDbConnection().koneksi();
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
      
   }
   
    private void IframeBorderLess() {
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null); 
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setSize(310,510);
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
    
/*    private void Cari(){
        try {           
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  unit_id,unit_name FROM iunits WHERE unit_name LIKE '%"+txtSearch.getText()+"%' ORDER BY unit_name");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs)); 
            TampilkanDiTabel();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }         
    }*/
    

    private void Tambah(){
        this.setVisible(false);
        txtSatuanAdd.setText("");
        SwingUtilities.invokeLater(() -> {txtSatuanAdd.requestFocusInWindow(); });
        UnitAdd.setSize(420, 180);
        UnitAdd.setLocation(((Beranda.SW+120)-420 )/2,((Beranda.SH+50)-180 )/2);
        UnitAdd.setBackground(new Color(0, 0, 0, 0)); 
        UnitAdd.setVisible(true);
    }     
    
        
    private void Edit(){
        this.setVisible(false);
        txtSatuanEdit.setText("");
        TampilEdit();
        SwingUtilities.invokeLater(() -> {txtSatuanEdit.requestFocusInWindow(); });
        UnitEdit.setSize(420, 180);
        UnitEdit.setLocation(((Beranda.SW+120)-420 )/2,((Beranda.SH+50)-180 )/2);
        UnitEdit.setBackground(new Color(0, 0, 0, 0)); 
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
            UTable ut = new UTable();           
            ut.Header(tableTampil,0,"",-10);
            ut.Header(tableTampil,1,"Satuan Barang",100);
            tableTampil.removeColumn(tableTampil.getColumnModel().getColumn(0)); //tidak menampilkan kolom (index:0)
    }
     
    private void Keluar(){
        this.dispose();
    }     
    
    //-----------------------------------UnitAdd-------------------------------------------------------------------------
    
       private void PopUpSatuanAdd(){
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
    
    private void Simpan(){             
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
       TampilSatuan();
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
    
    private void PopUpSatuanEdit(){
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
       TampilSatuan();
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
        uPanelRoundrect9 = new Utility.UPanelRoundrect();
        uPanelRoundrect10 = new Utility.UPanelRoundrect();
        SListAdd = new javax.swing.JScrollPane();
        ListAdd = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtSatuanAdd = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        btnSimpan = new Utility.UButton();
        jLabel3 = new javax.swing.JLabel();
        btnCloseAdd = new Utility.UButton();
        UnitEdit = new javax.swing.JDialog();
        uPanelRoundrect1 = new Utility.UPanelRoundrect();
        uPanelRoundrect2 = new Utility.UPanelRoundrect();
        SListEdit = new javax.swing.JScrollPane();
        ListEdit = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtSatuanEdit = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        btnUpdate = new Utility.UButton();
        jLabel9 = new javax.swing.JLabel();
        btnCloseEdit = new Utility.UButton();
        jPanel1 = new javax.swing.JPanel();
        uPanelRoundrect7 = new Utility.UPanelRoundrect();
        uPanelRoundrect8 = new Utility.UPanelRoundrect();
        panelEH = new javax.swing.JPanel();
        btnHapus = new Utility.UButton();
        btnEdit = new Utility.UButton();
        SPtableTampil = new javax.swing.JScrollPane();
        tableTampil = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        btnTambah = new Utility.UButton();
        jLabel7 = new javax.swing.JLabel();
        btnClose = new Utility.UButton();

        UnitAdd.setUndecorated(true);
        UnitAdd.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect9.setKetebalanBorder(2.5F);
        uPanelRoundrect9.setPreferredSize(new java.awt.Dimension(420, 180));
        uPanelRoundrect9.setWarnaBackground(new java.awt.Color(87, 176, 86));
        uPanelRoundrect9.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect10.setKetebalanBorder(2.5F);
        uPanelRoundrect10.setPreferredSize(new java.awt.Dimension(400, 120));
        uPanelRoundrect10.setWarnaBorder(new java.awt.Color(164, 253, 163));

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

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Satuan");
        jLabel5.setToolTipText(null);

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

        btnSimpan.setText("Simpan");
        btnSimpan.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnSimpan.setKetebalanBorder(2.5F);
        btnSimpan.setKetumpulanSudut(35);
        btnSimpan.setPreferredSize(new java.awt.Dimension(120, 30));
        btnSimpan.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout uPanelRoundrect10Layout = new javax.swing.GroupLayout(uPanelRoundrect10);
        uPanelRoundrect10.setLayout(uPanelRoundrect10Layout);
        uPanelRoundrect10Layout.setHorizontalGroup(
            uPanelRoundrect10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uPanelRoundrect10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(uPanelRoundrect10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(uPanelRoundrect10Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtSatuanAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(uPanelRoundrect10Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(uPanelRoundrect10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SListAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uPanelRoundrect10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        uPanelRoundrect10Layout.setVerticalGroup(
            uPanelRoundrect10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uPanelRoundrect10Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(uPanelRoundrect10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSatuanAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(uPanelRoundrect10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(uPanelRoundrect10Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(SListAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        uPanelRoundrect9.add(uPanelRoundrect10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tambah Satuan");
        uPanelRoundrect9.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 34));

        btnCloseAdd.setMnemonic('c');
        btnCloseAdd.setText("Close");
        btnCloseAdd.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnCloseAdd.setPreferredSize(new java.awt.Dimension(64, 16));
        btnCloseAdd.setWarnaBackgroundHover(new java.awt.Color(87, 176, 86));
        btnCloseAdd.setWarnaBorder(new java.awt.Color(87, 176, 86));
        btnCloseAdd.setWarnaForegroundHover(new java.awt.Color(255, 0, 0));
        btnCloseAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddActionPerformed(evt);
            }
        });
        uPanelRoundrect9.add(btnCloseAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));

        UnitAdd.getContentPane().add(uPanelRoundrect9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        UnitEdit.setUndecorated(true);
        UnitEdit.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect1.setKetebalanBorder(2.5F);
        uPanelRoundrect1.setPreferredSize(new java.awt.Dimension(420, 180));
        uPanelRoundrect1.setWarnaBackground(new java.awt.Color(235, 154, 35));
        uPanelRoundrect1.setWarnaBorder(new java.awt.Color(255, 231, 112));
        uPanelRoundrect1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect2.setKetebalanBorder(2.5F);
        uPanelRoundrect2.setPreferredSize(new java.awt.Dimension(400, 120));
        uPanelRoundrect2.setWarnaBorder(new java.awt.Color(255, 231, 112));
        uPanelRoundrect2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        uPanelRoundrect2.add(SListEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 54, -1, 0));

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Satuan");
        jLabel8.setToolTipText(null);
        uPanelRoundrect2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 70, 20));

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
        uPanelRoundrect2.add(txtSatuanEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 270, -1));
        uPanelRoundrect2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 270, 10));

        btnUpdate.setMnemonic('u');
        btnUpdate.setText("Update");
        btnUpdate.setKetebalanBorder(2.5F);
        btnUpdate.setKetumpulanSudut(35);
        btnUpdate.setPreferredSize(new java.awt.Dimension(120, 30));
        btnUpdate.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnUpdate.setWarnaBackgroundHover(new java.awt.Color(255, 231, 112));
        btnUpdate.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnUpdate.setWarnaBorder(new java.awt.Color(255, 231, 112));
        btnUpdate.setWarnaForegroundHover(new java.awt.Color(0, 0, 0));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        uPanelRoundrect2.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, -1, -1));

        uPanelRoundrect1.add(uPanelRoundrect2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Edit Satuan");
        uPanelRoundrect1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 34));

        btnCloseEdit.setMnemonic('c');
        btnCloseEdit.setText("Close");
        btnCloseEdit.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnCloseEdit.setPreferredSize(new java.awt.Dimension(64, 16));
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
        uPanelRoundrect1.add(btnCloseEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));

        UnitEdit.getContentPane().add(uPanelRoundrect1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setClosable(true);
        setMaximumSize(new java.awt.Dimension(306, 510));
        setMinimumSize(new java.awt.Dimension(306, 510));
        setPreferredSize(new java.awt.Dimension(306, 510));

        jPanel1.setBackground(new java.awt.Color(248, 251, 251));
        jPanel1.setMaximumSize(new java.awt.Dimension(290, 480));
        jPanel1.setMinimumSize(new java.awt.Dimension(290, 480));
        jPanel1.setPreferredSize(new java.awt.Dimension(290, 480));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect7.setKetebalanBorder(2.5F);
        uPanelRoundrect7.setPreferredSize(new java.awt.Dimension(310, 510));
        uPanelRoundrect7.setWarnaBackground(new java.awt.Color(0, 123, 255));
        uPanelRoundrect7.setWarnaBorder(new java.awt.Color(77, 200, 255));
        uPanelRoundrect7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect8.setKetebalanBorder(2.5F);
        uPanelRoundrect8.setPreferredSize(new java.awt.Dimension(300, 440));
        uPanelRoundrect8.setWarnaBorder(new java.awt.Color(77, 200, 255));
        uPanelRoundrect8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelEH.setOpaque(false);
        panelEH.setPreferredSize(new java.awt.Dimension(135, 0));
        panelEH.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnHapus.setMnemonic('p');
        btnHapus.setText("Hapus");
        btnHapus.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnHapus.setKetebalanBorder(2.5F);
        btnHapus.setKetumpulanSudut(35);
        btnHapus.setPreferredSize(new java.awt.Dimension(80, 28));
        btnHapus.setWarnaBackground(new java.awt.Color(255, 0, 0));
        btnHapus.setWarnaBackgroundHover(new java.awt.Color(255, 77, 77));
        btnHapus.setWarnaBackgroundPress(new java.awt.Color(255, 0, 0));
        btnHapus.setWarnaBorder(new java.awt.Color(255, 204, 204));
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        panelEH.add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        btnEdit.setMnemonic('d');
        btnEdit.setText("Edit");
        btnEdit.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnEdit.setKetebalanBorder(2.5F);
        btnEdit.setKetumpulanSudut(35);
        btnEdit.setPreferredSize(new java.awt.Dimension(85, 28));
        btnEdit.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnEdit.setWarnaBackgroundHover(new java.awt.Color(255, 205, 86));
        btnEdit.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnEdit.setWarnaBorder(new java.awt.Color(255, 231, 112));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        panelEH.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, -1, -1));

        uPanelRoundrect8.add(panelEH, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, -1));

        SPtableTampil.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SPtableTampil.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tableTampil.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
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

        uPanelRoundrect8.add(SPtableTampil, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 280, 380));

        btnTambah.setMnemonic('t');
        btnTambah.setText("Tambah Satuan");
        btnTambah.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnTambah.setKetebalanBorder(2.5F);
        btnTambah.setKetumpulanSudut(25);
        btnTambah.setPreferredSize(new java.awt.Dimension(120, 28));
        btnTambah.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        uPanelRoundrect8.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, -1));

        uPanelRoundrect7.add(uPanelRoundrect8, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 63, -1, -1));

        jLabel7.setBackground(new java.awt.Color(235, 154, 35));
        jLabel7.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Satuan Barang");
        jLabel7.setToolTipText("");
        uPanelRoundrect7.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 30));

        btnClose.setMnemonic('c');
        btnClose.setText("Close");
        btnClose.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnClose.setPreferredSize(new java.awt.Dimension(64, 16));
        btnClose.setWarnaBackground(new java.awt.Color(0, 123, 255));
        btnClose.setWarnaBackgroundHover(new java.awt.Color(0, 123, 255));
        btnClose.setWarnaBackgroundPress(new java.awt.Color(0, 123, 255));
        btnClose.setWarnaBorder(new java.awt.Color(0, 123, 255));
        btnClose.setWarnaForeground(new java.awt.Color(77, 200, 255));
        btnClose.setWarnaForegroundHover(new java.awt.Color(255, 0, 0));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        uPanelRoundrect7.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        jPanel1.add(uPanelRoundrect7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        setBounds(0, 0, 325, 551);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        Keluar();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnHapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapus1ActionPerformed
        panelEH.setSize(172, 0);
        Hapus();
    }//GEN-LAST:event_btnHapus1ActionPerformed

    private void btnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit1ActionPerformed
        panelEH.setSize(172, 0);
        Edit();
    }//GEN-LAST:event_btnEdit1ActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        Tambah();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        Simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnCloseEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseEditActionPerformed
        KeluarEdit();
    }//GEN-LAST:event_btnCloseEditActionPerformed

    private void tableTampilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTampilMouseClicked
        Id= tableTampil.getModel().getValueAt(tableTampil.getSelectedRow(), 0).toString(); //Ambil nilai kolom (0) dan masukkan ke variabel Id
        //setId(Id);
        panelEH.setLocation( evt.getX() + SPtableTampil.getX(),  evt.getY() + SPtableTampil.getY());
        panelEH.setSize(172,30);
    }//GEN-LAST:event_tableTampilMouseClicked

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        panelEH.setSize(172, 0);
        Hapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        panelEH.setSize(172, 0);
        Edit();
    }//GEN-LAST:event_btnEditActionPerformed

    private void txtSatuanAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Simpan();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSatuanAdd.setText("");
        }
    }//GEN-LAST:event_txtSatuanAddKeyPressed

    private void txtSatuanAddKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanAddKeyReleased
        if (txtSatuanAdd.getText().trim().isEmpty()) {
            SListAdd.setVisible(false);
        } else{
            PopUpSatuanAdd();
        }
    }//GEN-LAST:event_txtSatuanAddKeyReleased

    private void txtSatuanAddKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanAddKeyTyped
        if (txtSatuanAdd.getText().length() >= 15 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSatuanAddKeyTyped



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
            PopUpSatuanEdit();
        }
    }//GEN-LAST:event_txtSatuanEditKeyReleased

    private void txtSatuanEditKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanEditKeyTyped
        if (txtSatuanEdit.getText().length() >= 15 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSatuanEditKeyTyped

    private void btnCloseAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseAddActionPerformed
        KeluarAdd();
    }//GEN-LAST:event_btnCloseAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ListAdd;
    private javax.swing.JTable ListEdit;
    private javax.swing.JScrollPane SListAdd;
    private javax.swing.JScrollPane SListEdit;
    private javax.swing.JScrollPane SPtableTampil;
    private javax.swing.JDialog UnitAdd;
    private javax.swing.JDialog UnitEdit;
    private Utility.UButton btnClose;
    private Utility.UButton btnCloseAdd;
    private Utility.UButton btnCloseEdit;
    private Utility.UButton btnEdit;
    private Utility.UButton btnHapus;
    private Utility.UButton btnSimpan;
    private Utility.UButton btnTambah;
    private Utility.UButton btnUpdate;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JPanel panelEH;
    private javax.swing.JTable tableTampil;
    private javax.swing.JTextField txtSatuanAdd;
    private javax.swing.JTextField txtSatuanEdit;
    private Utility.UPanelRoundrect uPanelRoundrect1;
    private Utility.UPanelRoundrect uPanelRoundrect10;
    private Utility.UPanelRoundrect uPanelRoundrect2;
    private Utility.UPanelRoundrect uPanelRoundrect7;
    private Utility.UPanelRoundrect uPanelRoundrect8;
    private Utility.UPanelRoundrect uPanelRoundrect9;
    // End of variables declaration//GEN-END:variables
}
