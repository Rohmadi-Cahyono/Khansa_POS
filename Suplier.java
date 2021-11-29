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


public class Suplier extends javax.swing.JInternalFrame {
    java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
    private static String  Id;
    
    public Suplier() {
        initComponents();
        IframeBorderLess(); 
        SPtableTampil.getViewport().setBackground(new Color(255,255,255));
        SListAdd.setVisible(false);
        SListAdd.getViewport().setBackground(new Color(255,255,255));   
        
        SListEdit.setVisible(false);
        SListEdit.getViewport().setBackground(new Color(255,255,255));
        TampilSuplier(); 
        Tengah();
    }
 
   public void Focus(){
       txtSearch.requestFocus();
   }
   
    private void IframeBorderLess() {
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null); 
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
   
    private void TampilSuplier() {      
        try {
            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery("SELECT suplier_id,suplier_name, suplier_address, suplier_phone, suplier_created, suplier_update FROM supliers ");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs));            
            TampilkanDiTabel();         
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void Tengah(){
        Dimension formIni = this.getSize();
        this.setLocation(( Beranda.PW-formIni.width )/2,(Beranda.PH-formIni.height )/2);
    }
    
    private void Tambah(){
        this.setVisible(false); 
        Bersih(); 
        SwingUtilities.invokeLater(() -> {txtNameAdd.requestFocusInWindow(); });
        SuplierAdd.setSize(970, 330);                     
        SuplierAdd.setLocation(((Beranda.PW+120)-970 )/2,((Beranda.PH+50)-330 )/2);
        SuplierAdd.setVisible(true);
    }
    
    public void Edit(){
        this.setVisible(false);
        TampilEdit();
        SwingUtilities.invokeLater(() -> {txtNameEdit.requestFocusInWindow(); });
        SuplierEdit.setSize(970, 330);                     
        SuplierEdit.setLocation(((Beranda.PW+120)-970 )/2,((Beranda.PH+50)-330 )/2);
        SuplierEdit.setVisible(true);         
    }
    
    private void Hapus(){       
        if (JOptionPane.showConfirmDialog(null, "Yakin data Suplier akan dihapus?", "Khansa POS",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {            
            try {
                    java.sql.Statement st = con.createStatement();           
                    st.executeUpdate("DELETE FROM supliers WHERE suplier_id="+Id);            
                    JOptionPane.showMessageDialog(null, "Data Suplier berhasil dihapus!");
                    TampilSuplier();
            } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
            }  
        }        
    }
    
     private void Cari(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  suplier_id,suplier_name, suplier_address, suplier_phone, "
                    + "suplier_created, suplier_update  FROM supliers WHERE suplier_name LIKE '%"+txtSearch.getText()+"%'");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs)); 
            TampilkanDiTabel();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
     
     private void TampilkanDiTabel() {                     
            Utility_Table ut = new Utility_Table();         
          
            ut.Header(tableTampil,0,"",-10);
            ut.Header(tableTampil,1,"Suplier Name",200);
            ut.Header(tableTampil,2,"Address",400);
            ut.Header(tableTampil,3,"Phone",150);           
            ut.Header(tableTampil,4,"Date Created",100);
            ut.Header(tableTampil,5,"Date Update",100);
                   
            tableTampil.getColumnModel().getColumn(4).setCellRenderer(ut.formatTanggal);
            tableTampil.getColumnModel().getColumn(5).setCellRenderer(ut.formatTanggal);
            //tableSuplier.getColumnModel().getColumn(2).setCellRenderer(new Utility_RupiahCellRenderer());
            tableTampil.removeColumn(tableTampil.getColumnModel().getColumn(0)); //tidak menampilkan kolom (index:0)
    }
     
     //-----------------------------------------------------SuplierAdd--------------------------------------------------------------------
        private void PopUp(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT suplier_id,suplier_name FROM supliers WHERE suplier_name LIKE '"+txtNameAdd.getText()+"%'");
            ListAdd.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                Utility_Table uts = new Utility_Table();
                uts.Header(ListAdd,0,"",-10);
                uts.Header(ListAdd,1,"",200);
                ListAdd.setBackground(new Color(255,255,255));
                ListAdd.setShowGrid(false);
                ListAdd.removeColumn(ListAdd.getColumnModel().getColumn(0));
                
                if (rs.getRow() <= 15) {
                    SListAdd.setSize(340, (rs.getRow()*17)+2);
                } else{
                    SListAdd.setSize(340, (15*17)+2);                    
                }
                    SListAdd.setVisible(true); 
            } else {
                    SListAdd.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
  
   
    private void Bersih(){
        txtPhoneAdd.setText("");
        txtAddressAdd.setText("");
        txtNameAdd.setText("");
        txtNameAdd.requestFocus();
    }
   
    private void Simpan(){
             
        if (txtNameAdd.getText() == null || txtNameAdd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Suplier Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {

                try{   
                        java.util.Date dt = new java.util.Date();
                        java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(dt);
                    
                        String sql ="INSERT INTO supliers(suplier_name,suplier_address,suplier_phone,suplier_created) VALUES ('"+txtNameAdd.getText()+"','"+txtAddressAdd.getText()+"','"+txtPhoneAdd.getText()+"','"+currentTime+"')";
                       
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                    
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data Suplier Berhasil");
                        KeluarAdd();
                  
                    }
                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }

        }         
    }
     
    private void KeluarAdd(){
       SuplierAdd.dispose();
       this.setVisible(true);
       Cari();
       txtSearch.requestFocus();
   }
    
    //-------------------------------------------------------------------SuplierEdit--------------------------------------------------------------
    private void TampilEdit(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT * FROM supliers WHERE suplier_id =' "+Id+" ' ");
                if(rs.next()){                    
                    txtNameEdit.setText(rs.getString("suplier_name"));
                    txtAdressEdit.setText(rs.getString("suplier_address"));
                    txtPhoneEdit.setText(rs.getString("suplier_phone"));
                }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }  
    }
   

    private void PopUpEdit(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT suplier_id,suplier_name FROM supliers "
                    + "WHERE suplier_name LIKE '"+txtNameEdit.getText()+"%'");
            ListEdit.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                Utility_Table uts = new Utility_Table();
                uts.Header(ListEdit,0,"",-10);
                uts.Header(ListEdit,1,"",200);
                ListEdit.setBackground(new Color(255,255,255));
                ListEdit.setShowGrid(false);
                ListEdit.removeColumn(ListEdit.getColumnModel().getColumn(0));
                
                if (rs.getRow() <= 15) {
                    SListEdit.setSize(340, (rs.getRow()*17)+2);
                } else{
                    SListEdit.setSize(340, (15*17)+2);                    
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
                  
        if (txtNameEdit.getText() == null || txtNameEdit.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Suplier Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {  
                try{ 
                        String sql ="UPDATE supliers SET suplier_name='"+txtNameEdit.getText()+"', "
                                + "suplier_address='"+txtAdressEdit.getText()+"', suplier_phone='"+txtPhoneEdit.getText()+"'  WHERE suplier_id='"+Id+"' ";
                        
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();

                        JOptionPane.showMessageDialog(null, "Update Data Suplier Berhasil");
                        KeluarEdit();
                }                
                catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                }
        }
    }
    
    
    private void KeluarEdit(){
       SuplierEdit.dispose();
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

        SuplierAdd = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnCloseSuplierAdd = new khansapos.Utility_ButtonMetro();
        SListAdd = new javax.swing.JScrollPane();
        ListAdd = new javax.swing.JTable();
        txtNameAdd = new javax.swing.JTextField();
        txtAddressAdd = new javax.swing.JTextField();
        txtPhoneAdd = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        btnBersih = new khansapos.Utility_ButtonFlat();
        btnSimpanSuplier = new khansapos.Utility_ButtonFlat();
        SuplierEdit = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        btnCloseSuplierEdit = new khansapos.Utility_ButtonMetro();
        SListEdit = new javax.swing.JScrollPane();
        ListEdit = new javax.swing.JTable();
        txtNameEdit = new javax.swing.JTextField();
        txtAdressEdit = new javax.swing.JTextField();
        txtPhoneEdit = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        btnUpdate = new khansapos.Utility_ButtonFlat();
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

        SuplierAdd.setModal(true);
        SuplierAdd.setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(87, 176, 86)));
        jPanel2.setMaximumSize(new java.awt.Dimension(970, 330));
        jPanel2.setMinimumSize(new java.awt.Dimension(970, 330));
        jPanel2.setPreferredSize(new java.awt.Dimension(970, 330));
        jPanel2.setVerifyInputWhenFocusTarget(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(87, 176, 86));
        jPanel3.setPreferredSize(new java.awt.Dimension(315, 55));

        jLabel3.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tambah Suplier");

        btnCloseSuplierAdd.setMnemonic('c');
        btnCloseSuplierAdd.setText("Close");
        btnCloseSuplierAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnCloseSuplierAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseSuplierAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 673, Short.MAX_VALUE)
                .addComponent(btnCloseSuplierAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(14, Short.MAX_VALUE))
            .addComponent(btnCloseSuplierAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, -1));

        SListAdd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        SListAdd.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SListAdd.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SListAdd.setFocusable(false);

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

        jPanel2.add(SListAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 330, 0));

        txtNameAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtNameAdd.setToolTipText(null);
        txtNameAdd.setBorder(null);
        txtNameAdd.setOpaque(false);
        txtNameAdd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameAddFocusLost(evt);
            }
        });
        txtNameAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameAddKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNameAddKeyReleased(evt);
            }
        });
        jPanel2.add(txtNameAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 335, -1));

        txtAddressAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtAddressAdd.setToolTipText(null);
        txtAddressAdd.setBorder(null);
        txtAddressAdd.setOpaque(false);
        txtAddressAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAddressAddKeyPressed(evt);
            }
        });
        jPanel2.add(txtAddressAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 815, -1));

        txtPhoneAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtPhoneAdd.setToolTipText(null);
        txtPhoneAdd.setBorder(null);
        txtPhoneAdd.setOpaque(false);
        txtPhoneAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneAddKeyPressed(evt);
            }
        });
        jPanel2.add(txtPhoneAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 289, -1));

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Nama Suplier");
        jLabel4.setToolTipText(null);
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 101, -1, 20));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 335, 10));

        jLabel5.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Alamat ");
        jLabel5.setToolTipText(null);
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 149, -1, 20));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 815, 10));

        jLabel6.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Phone ");
        jLabel6.setToolTipText(null);
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 197, -1, 20));
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 289, 10));

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
        jPanel2.add(btnBersih, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 270, 90, 30));

        btnSimpanSuplier.setMnemonic('s');
        btnSimpanSuplier.setText("Simpan");
        btnSimpanSuplier.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnSimpanSuplier.setMousePress(new java.awt.Color(204, 204, 204));
        btnSimpanSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanSuplierActionPerformed(evt);
            }
        });
        jPanel2.add(btnSimpanSuplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 270, 210, 30));

        javax.swing.GroupLayout SuplierAddLayout = new javax.swing.GroupLayout(SuplierAdd.getContentPane());
        SuplierAdd.getContentPane().setLayout(SuplierAddLayout);
        SuplierAddLayout.setHorizontalGroup(
            SuplierAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        SuplierAddLayout.setVerticalGroup(
            SuplierAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        SuplierEdit.setModal(true);
        SuplierEdit.setUndecorated(true);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(235, 154, 35)));
        jPanel4.setMaximumSize(new java.awt.Dimension(970, 339));
        jPanel4.setMinimumSize(new java.awt.Dimension(970, 339));
        jPanel4.setPreferredSize(new java.awt.Dimension(970, 339));
        jPanel4.setRequestFocusEnabled(false);
        jPanel4.setVerifyInputWhenFocusTarget(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(235, 154, 35));

        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Edit Suplier");

        btnCloseSuplierEdit.setMnemonic('c');
        btnCloseSuplierEdit.setText("Close");
        btnCloseSuplierEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnCloseSuplierEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseSuplierEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 726, Short.MAX_VALUE)
                .addComponent(btnCloseSuplierEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(14, Short.MAX_VALUE))
            .addComponent(btnCloseSuplierEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, -1));

        SListEdit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        SListEdit.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SListEdit.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SListEdit.setFocusable(false);

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

        jPanel4.add(SListEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 330, 0));

        txtNameEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtNameEdit.setToolTipText(null);
        txtNameEdit.setBorder(null);
        txtNameEdit.setOpaque(false);
        txtNameEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameEditFocusLost(evt);
            }
        });
        txtNameEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameEditKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNameEditKeyReleased(evt);
            }
        });
        jPanel4.add(txtNameEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 335, -1));

        txtAdressEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtAdressEdit.setToolTipText(null);
        txtAdressEdit.setBorder(null);
        txtAdressEdit.setOpaque(false);
        txtAdressEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAdressEditKeyPressed(evt);
            }
        });
        jPanel4.add(txtAdressEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 815, -1));

        txtPhoneEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtPhoneEdit.setToolTipText(null);
        txtPhoneEdit.setBorder(null);
        txtPhoneEdit.setOpaque(false);
        txtPhoneEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneEditKeyPressed(evt);
            }
        });
        jPanel4.add(txtPhoneEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 289, -1));

        jLabel8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Nama Suplier");
        jLabel8.setToolTipText(null);
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 101, -1, 20));
        jPanel4.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 335, 10));

        jLabel9.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Alamat ");
        jLabel9.setToolTipText(null);
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 149, -1, 20));
        jPanel4.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 815, 10));

        jLabel10.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Phone ");
        jLabel10.setToolTipText(null);
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 197, -1, 20));
        jPanel4.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 289, 10));

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
        jPanel4.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 280, 210, 30));

        javax.swing.GroupLayout SuplierEditLayout = new javax.swing.GroupLayout(SuplierEdit.getContentPane());
        SuplierEdit.getContentPane().setLayout(SuplierEditLayout);
        SuplierEditLayout.setHorizontalGroup(
            SuplierEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        SuplierEditLayout.setVerticalGroup(
            SuplierEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

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

        jPanel1.add(SPtableTampil, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 1190, 510));

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
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 290, 10));

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(78, 115, 223));
        jLabel1.setText("Master Suplier");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 300, 40));

        btnTambah.setMnemonic('t');
        btnTambah.setText("Tambah Suplier");
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

        pack();
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

    private void txtNameAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameAddFocusLost
        SListAdd.setVisible(false);
    }//GEN-LAST:event_txtNameAddFocusLost

    private void txtNameAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtAddressAdd.setText(null);
            txtAddressAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtNameAdd.setText("");
        }
    }//GEN-LAST:event_txtNameAddKeyPressed

    private void txtNameAddKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameAddKeyReleased
        if (txtNameAdd.getText().trim().isEmpty()) {
            SListAdd.setVisible(false);
        } else{
            PopUp();
        }
    }//GEN-LAST:event_txtNameAddKeyReleased

    private void txtAddressAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddressAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtPhoneAdd.setText(null);
            txtPhoneAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtAddressAdd.setText("");
        }
    }//GEN-LAST:event_txtAddressAddKeyPressed

    private void txtPhoneAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Simpan();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtPhoneAdd.setText("");
        }
    }//GEN-LAST:event_txtPhoneAddKeyPressed

    private void txtNameEditFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameEditFocusLost
        SListEdit.setVisible(false);
    }//GEN-LAST:event_txtNameEditFocusLost

    private void txtNameEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtAdressEdit.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtNameEdit.setText("");
        }
    }//GEN-LAST:event_txtNameEditKeyPressed

    private void txtNameEditKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameEditKeyReleased
        if (txtNameEdit.getText().trim().isEmpty()) {
            SListEdit.setVisible(false);
        } else{
            PopUpEdit();
        }
    }//GEN-LAST:event_txtNameEditKeyReleased

    private void txtAdressEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdressEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtPhoneEdit.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtAdressEdit.setText("");
        }
    }//GEN-LAST:event_txtAdressEditKeyPressed

    private void txtPhoneEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Update();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtPhoneEdit.setText("");
        }
    }//GEN-LAST:event_txtPhoneEditKeyPressed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        Tambah();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnCloseSuplierAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseSuplierAddActionPerformed
        KeluarAdd();
    }//GEN-LAST:event_btnCloseSuplierAddActionPerformed

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
        Bersih();
    }//GEN-LAST:event_btnBersihActionPerformed

    private void btnSimpanSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanSuplierActionPerformed
        Simpan();
    }//GEN-LAST:event_btnSimpanSuplierActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        panelEH.setSize(130, 0);
        Edit();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        panelEH.setSize(130, 0);
        Hapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnCloseSuplierEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseSuplierEditActionPerformed
        KeluarEdit();
    }//GEN-LAST:event_btnCloseSuplierEditActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ListAdd;
    private javax.swing.JTable ListEdit;
    private javax.swing.JScrollPane SListAdd;
    private javax.swing.JScrollPane SListEdit;
    private javax.swing.JScrollPane SPtableTampil;
    private javax.swing.JDialog SuplierAdd;
    private javax.swing.JDialog SuplierEdit;
    private khansapos.Utility_ButtonFlat btnBersih;
    private khansapos.Utility_ButtonMetro btnCloseSuplierAdd;
    private khansapos.Utility_ButtonMetro btnCloseSuplierEdit;
    private khansapos.Utility_ButtonFlat btnEdit;
    private khansapos.Utility_ButtonFlat btnHapus;
    private khansapos.Utility_ButtonFlat btnSimpanSuplier;
    private khansapos.Utility_ButtonFlat btnTambah;
    private khansapos.Utility_ButtonFlat btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JPanel panelEH;
    private javax.swing.JTable tableTampil;
    private javax.swing.JTextField txtAddressAdd;
    private javax.swing.JTextField txtAdressEdit;
    private javax.swing.JTextField txtNameAdd;
    private javax.swing.JTextField txtNameEdit;
    private javax.swing.JTextField txtPhoneAdd;
    private javax.swing.JTextField txtPhoneEdit;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
