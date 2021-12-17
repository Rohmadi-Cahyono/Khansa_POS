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
    java.sql.Connection con =  new UDbConnection().koneksi();
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
    
/*  private void Cari(){
        try {            
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  category_id,category_name FROM icategory WHERE category_name LIKE '%"+txtSearch.getText()+"%' ORDER BY category_name");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs)); 
            TampilkanDiTabel();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }         
    }*/
    
     private void TampilkanDiTabel() {                     
            UTable ut = new UTable();           
            ut.Header(tableTampil,0,"",-10);
            ut.Header(tableTampil,1,"Kategori Barang",100);
            tableTampil.removeColumn(tableTampil.getColumnModel().getColumn(0)); //tidak menampilkan kolom (index:0)
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
    
    private void Tambah(){
        this.setVisible(false); 
        txtKategoriAdd.setText("");
        SwingUtilities.invokeLater(() -> {txtKategoriAdd.requestFocusInWindow(); });
        categoryAdd.setSize(421, 171);
        categoryAdd.setLocation(((Beranda.SW+120)-421 )/2,((Beranda.SH+50)-171 )/2);
        categoryAdd.setBackground(new Color(0, 0, 0, 0)); 
        categoryAdd.setVisible(true);
    }     
    
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
                SListAdd.setLocation(txtKategoriAdd.getX(),txtKategoriAdd.getY()+20);
                
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
       Tampil();
   }
   
   //------------------------------------------------KatergoryEdit---------------------------------------------
       
    private void Edit(){
        this.setVisible(false);
        TampilEdit();
        SwingUtilities.invokeLater(() -> {txtKategoriEdit.requestFocusInWindow(); });
        categoryEdit.setSize(421, 171);
        categoryEdit.setLocation(((Beranda.SW+120)-421 )/2,((Beranda.SH+50)-171 )/2);
        categoryAdd.setBackground(new Color(0, 0, 0, 0)); 
        categoryEdit.setVisible(true);
    }   
   
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
                SListEdit.setLocation(txtKategoriEdit.getX(),txtKategoriEdit.getY()+20);
                
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
       Tampil();
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
        uPanelRoundrect7 = new Utility.UPanelRoundrect();
        uPanelRoundrect8 = new Utility.UPanelRoundrect();
        btnSimpan = new Utility.UButton();
        SListAdd = new javax.swing.JScrollPane();
        ListAdd = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtKategoriAdd = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        btnCloseAdd = new Utility.UButton();
        categoryEdit = new javax.swing.JDialog();
        uPanelRoundrect1 = new Utility.UPanelRoundrect();
        uPanelRoundrect2 = new Utility.UPanelRoundrect();
        btnUpdate = new Utility.UButton();
        SListEdit = new javax.swing.JScrollPane();
        ListEdit = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtKategoriEdit = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        btnCloseEdit = new Utility.UButton();
        jPanel3 = new javax.swing.JPanel();
        uPanelRoundrect6 = new Utility.UPanelRoundrect();
        uPanelRoundrect9 = new Utility.UPanelRoundrect();
        panelEH = new javax.swing.JPanel();
        btnEdit = new Utility.UButton();
        btnHapus = new Utility.UButton();
        SPtableTampil = new javax.swing.JScrollPane();
        tableTampil = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        btnTambah = new Utility.UButton();
        jLabel1 = new javax.swing.JLabel();
        btnClose = new Utility.UButton();

        categoryAdd.setMinimumSize(new java.awt.Dimension(421, 156));
        categoryAdd.setUndecorated(true);
        categoryAdd.setPreferredSize(new java.awt.Dimension(421, 171));
        categoryAdd.setResizable(false);
        categoryAdd.setSize(new java.awt.Dimension(421, 171));
        categoryAdd.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect7.setKetebalanBorder(2.5F);
        uPanelRoundrect7.setPreferredSize(new java.awt.Dimension(421, 171));
        uPanelRoundrect7.setWarnaBackground(new java.awt.Color(87, 176, 86));
        uPanelRoundrect7.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect8.setKetebalanBorder(2.5F);
        uPanelRoundrect8.setPreferredSize(new java.awt.Dimension(411, 115));
        uPanelRoundrect8.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSimpan.setText("Simpan");
        btnSimpan.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnSimpan.setKetebalanBorder(2.0F);
        btnSimpan.setKetumpulanSudut(35);
        btnSimpan.setPreferredSize(new java.awt.Dimension(100, 32));
        btnSimpan.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        uPanelRoundrect8.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 75, -1, -1));

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

        uPanelRoundrect8.add(SListAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 62, -1, 0));

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Kategori");
        jLabel5.setToolTipText(null);
        uPanelRoundrect8.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 38, 70, 20));

        txtKategoriAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtKategoriAdd.setToolTipText(null);
        txtKategoriAdd.setBorder(null);
        txtKategoriAdd.setOpaque(false);
        txtKategoriAdd.setPreferredSize(new java.awt.Dimension(1, 20));
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
        uPanelRoundrect8.add(txtKategoriAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 38, 270, -1));

        jSeparator5.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect8.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(102, 58, 270, 10));

        uPanelRoundrect7.add(uPanelRoundrect8, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 50, -1, -1));

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tambah Kategori");
        uPanelRoundrect7.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 34));

        btnCloseAdd.setMnemonic('c');
        btnCloseAdd.setText("Close");
        btnCloseAdd.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnCloseAdd.setWarnaBackgroundHover(new java.awt.Color(87, 176, 86));
        btnCloseAdd.setWarnaBorder(new java.awt.Color(87, 176, 86));
        btnCloseAdd.setWarnaForegroundHover(new java.awt.Color(255, 0, 51));
        btnCloseAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddActionPerformed(evt);
            }
        });
        uPanelRoundrect7.add(btnCloseAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 10, -1, -1));

        categoryAdd.getContentPane().add(uPanelRoundrect7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        categoryEdit.setMinimumSize(new java.awt.Dimension(421, 156));
        categoryEdit.setUndecorated(true);
        categoryEdit.setPreferredSize(new java.awt.Dimension(421, 171));
        categoryEdit.setResizable(false);
        categoryEdit.setSize(new java.awt.Dimension(421, 156));
        categoryEdit.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect1.setKetebalanBorder(2.5F);
        uPanelRoundrect1.setPreferredSize(new java.awt.Dimension(421, 171));
        uPanelRoundrect1.setWarnaBackground(new java.awt.Color(235, 154, 35));
        uPanelRoundrect1.setWarnaBorder(new java.awt.Color(255, 231, 112));
        uPanelRoundrect1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect2.setKetebalanBorder(2.5F);
        uPanelRoundrect2.setPreferredSize(new java.awt.Dimension(411, 115));
        uPanelRoundrect2.setWarnaBorder(new java.awt.Color(255, 231, 112));
        uPanelRoundrect2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnUpdate.setText("Simpan");
        btnUpdate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnUpdate.setKetebalanBorder(2.0F);
        btnUpdate.setKetumpulanSudut(35);
        btnUpdate.setPreferredSize(new java.awt.Dimension(100, 32));
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
        uPanelRoundrect2.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, -1, -1));

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

        uPanelRoundrect2.add(SListEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, -1, 0));

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Kategori");
        jLabel8.setToolTipText(null);
        uPanelRoundrect2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 70, 20));

        txtKategoriEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtKategoriEdit.setToolTipText(null);
        txtKategoriEdit.setBorder(null);
        txtKategoriEdit.setOpaque(false);
        txtKategoriEdit.setPreferredSize(new java.awt.Dimension(1, 20));
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
        uPanelRoundrect2.add(txtKategoriEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 270, -1));

        jSeparator7.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 270, 10));

        uPanelRoundrect1.add(uPanelRoundrect2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 50, -1, -1));

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Edit Kategori");
        uPanelRoundrect1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 34));

        btnCloseEdit.setMnemonic('c');
        btnCloseEdit.setText("Close");
        btnCloseEdit.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnCloseEdit.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnCloseEdit.setWarnaBackgroundHover(new java.awt.Color(235, 154, 35));
        btnCloseEdit.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnCloseEdit.setWarnaBorder(new java.awt.Color(235, 154, 35));
        btnCloseEdit.setWarnaForegroundHover(new java.awt.Color(255, 51, 0));
        btnCloseEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseEditActionPerformed(evt);
            }
        });
        uPanelRoundrect1.add(btnCloseEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 10, -1, -1));

        categoryEdit.getContentPane().add(uPanelRoundrect1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setMaximumSize(new java.awt.Dimension(306, 510));
        setMinimumSize(new java.awt.Dimension(306, 510));
        setPreferredSize(new java.awt.Dimension(306, 510));

        jPanel3.setBackground(new java.awt.Color(248, 251, 251));
        jPanel3.setPreferredSize(new java.awt.Dimension(310, 510));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect6.setKetebalanBorder(2.5F);
        uPanelRoundrect6.setPreferredSize(new java.awt.Dimension(310, 510));
        uPanelRoundrect6.setWarnaBackground(new java.awt.Color(60, 93, 93));
        uPanelRoundrect6.setWarnaBorder(new java.awt.Color(85, 118, 118));
        uPanelRoundrect6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect9.setKetebalanBorder(2.5F);
        uPanelRoundrect9.setPreferredSize(new java.awt.Dimension(300, 440));
        uPanelRoundrect9.setWarnaBorder(new java.awt.Color(85, 118, 118));
        uPanelRoundrect9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelEH.setOpaque(false);
        panelEH.setPreferredSize(new java.awt.Dimension(135, 0));
        panelEH.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEdit.setText("Edit");
        btnEdit.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnEdit.setKetebalanBorder(2.5F);
        btnEdit.setKetumpulanSudut(25);
        btnEdit.setPreferredSize(new java.awt.Dimension(75, 28));
        btnEdit.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnEdit.setWarnaBackgroundHover(new java.awt.Color(255, 205, 86));
        btnEdit.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnEdit.setWarnaBorder(new java.awt.Color(255, 231, 112));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        panelEH.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        btnHapus.setText("Hapus");
        btnHapus.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnHapus.setKetebalanBorder(2.5F);
        btnHapus.setKetumpulanSudut(25);
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
        panelEH.add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 0, -1, -1));

        uPanelRoundrect9.add(panelEH, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, -1));

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

        uPanelRoundrect9.add(SPtableTampil, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 280, 380));

        btnTambah.setMnemonic('t');
        btnTambah.setText("Tambah Kategori");
        btnTambah.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnTambah.setKetebalanBorder(2.0F);
        btnTambah.setKetumpulanSudut(25);
        btnTambah.setPreferredSize(new java.awt.Dimension(120, 28));
        btnTambah.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        uPanelRoundrect9.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 140, -1));

        uPanelRoundrect6.add(uPanelRoundrect9, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 65, -1, -1));

        jLabel1.setBackground(new java.awt.Color(235, 154, 35));
        jLabel1.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Kategori Barang");
        jLabel1.setToolTipText("");
        uPanelRoundrect6.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 30));

        btnClose.setText("Close");
        btnClose.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnClose.setPreferredSize(new java.awt.Dimension(64, 20));
        btnClose.setWarnaBackground(new java.awt.Color(60, 93, 93));
        btnClose.setWarnaBackgroundHover(new java.awt.Color(60, 93, 93));
        btnClose.setWarnaBackgroundPress(new java.awt.Color(60, 93, 93));
        btnClose.setWarnaBorder(new java.awt.Color(60, 93, 93));
        btnClose.setWarnaForegroundHover(new java.awt.Color(255, 0, 0));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        uPanelRoundrect6.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 10, -1, -1));

        jPanel3.add(uPanelRoundrect6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 326, 540);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseEditActionPerformed
        KeluarEdit();
    }//GEN-LAST:event_btnCloseEditActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        Tambah();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        Keluar();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        Simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnCloseAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseAddActionPerformed
        KeluarAdd();
    }//GEN-LAST:event_btnCloseAddActionPerformed

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

    private void txtKategoriAddKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriAddKeyTyped
        if (txtKategoriAdd.getText().length() >= 15 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtKategoriAddKeyTyped

    private void txtKategoriAddKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriAddKeyReleased
        if (txtKategoriAdd.getText().trim().isEmpty()) {
            SListAdd.setVisible(false);
        } else{
            PopUpKategoriAdd();
        }
    }//GEN-LAST:event_txtKategoriAddKeyReleased

    private void txtKategoriAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Simpan();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKategoriAdd.setText("");
        }
    }//GEN-LAST:event_txtKategoriAddKeyPressed

    private void txtKategoriEditKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriEditKeyTyped
        if (txtKategoriEdit.getText().length() >= 15 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtKategoriEditKeyTyped

    private void txtKategoriEditKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriEditKeyReleased
        if (txtKategoriEdit.getText().trim().isEmpty()) {
            SListEdit.setVisible(false);
        } else{
            PopUpKategoriEdit();
        }
    }//GEN-LAST:event_txtKategoriEditKeyReleased

    private void txtKategoriEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Update();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKategoriEdit.setText("");
        }
    }//GEN-LAST:event_txtKategoriEditKeyPressed

    private void tableTampilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTampilMouseClicked
        Id= tableTampil.getModel().getValueAt(tableTampil.getSelectedRow(), 0).toString(); //Ambil nilai kolom (0) dan masukkan ke variabel Id
        //setId(Id);
        panelEH.setLocation( evt.getX() + SPtableTampil.getX(),  evt.getY() + SPtableTampil.getY());
        panelEH.setSize(172,30);
    }//GEN-LAST:event_tableTampilMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ListAdd;
    private javax.swing.JTable ListEdit;
    private javax.swing.JScrollPane SListAdd;
    private javax.swing.JScrollPane SListEdit;
    private javax.swing.JScrollPane SPtableTampil;
    private Utility.UButton btnClose;
    private Utility.UButton btnCloseAdd;
    private Utility.UButton btnCloseEdit;
    private Utility.UButton btnEdit;
    private Utility.UButton btnHapus;
    private Utility.UButton btnSimpan;
    private Utility.UButton btnTambah;
    private Utility.UButton btnUpdate;
    private javax.swing.JDialog categoryAdd;
    private javax.swing.JDialog categoryEdit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JPanel panelEH;
    private javax.swing.JTable tableTampil;
    private javax.swing.JTextField txtKategoriAdd;
    private javax.swing.JTextField txtKategoriEdit;
    private Utility.UPanelRoundrect uPanelRoundrect1;
    private Utility.UPanelRoundrect uPanelRoundrect2;
    private Utility.UPanelRoundrect uPanelRoundrect6;
    private Utility.UPanelRoundrect uPanelRoundrect7;
    private Utility.UPanelRoundrect uPanelRoundrect8;
    private Utility.UPanelRoundrect uPanelRoundrect9;
    // End of variables declaration//GEN-END:variables
}
