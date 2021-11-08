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

public class SuplierFormEdit extends javax.swing.JInternalFrame {

    public SuplierFormEdit() {
        initComponents();
        IframeBorderLess();
         
        SPtableAutoComplete.setVisible(false);
        SPtableAutoComplete.getViewport().setBackground(Color.WHITE);

        TampilEdit();
        Tengah();
        SwingUtilities.invokeLater(() -> { txtSuplierName.requestFocusInWindow(); });
    }
       
    private void IframeBorderLess(){
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setSize(970, 339);
    }
    
    private void Tengah(){
        Dimension formIni = this.getSize();
        this.setLocation(( Utility_Session.getPanelW()-formIni.width )/2,(Utility_Session.getPanelH()-formIni.height )/2);
    }
    
    private void TampilEdit(){
        String Id = SuplierForm.getId();  //Ambil variabel suplierId dari form SuplierForm
        
        try {            
            java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT * FROM supliers WHERE suplier_id =' "+Id+" ' ");
                if(rs.next()){                    
                    txtSuplierName.setText(rs.getString("suplier_name"));
                    txtAdress.setText(rs.getString("suplier_address"));
                    txtPhone.setText(rs.getString("suplier_phone"));
                }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }  
    }
   
    private void Keluar(){
        SuplierForm mf = new SuplierForm();
        this.getParent().add(mf);
        mf.setVisible(true);
        this.setVisible(false);     
    }
    
    private void PopUp(){
        try {
            java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT suplier_id,suplier_name FROM supliers WHERE suplier_name LIKE '"+txtSuplierName.getText()+"%'");
            tableAutoComplete.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                Utility_Table uts = new Utility_Table();
                uts.Header(tableAutoComplete,0,"",-10);
                uts.Header(tableAutoComplete,1,"",200);
                tableAutoComplete.setBackground(new Color(255,255,255));
                tableAutoComplete.setShowGrid(false);
                tableAutoComplete.removeColumn(tableAutoComplete.getColumnModel().getColumn(0));
                
                if (rs.getRow() <= 15) {
                    SPtableAutoComplete.setSize(340, (rs.getRow()*17)+2);
                } else{
                    SPtableAutoComplete.setSize(340, (15*17)+2);                    
                }
                    SPtableAutoComplete.setVisible(true); 
            } else {
                    SPtableAutoComplete.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
  
   
    private void Bersih(){
        txtPhone.setText("");
        txtAdress.setText("");
        txtSuplierName.setText("");
        txtSuplierName.requestFocus();
    }
   
    private void Simpan(){
                   
        if (txtSuplierName.getText() == null || txtSuplierName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Suplier Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {  
                try{ 
                        String Id = SuplierForm.getId();
                        String sql ="UPDATE supliers SET suplier_name='"+txtSuplierName.getText()+"', suplier_address='"+txtAdress.getText()+"', suplier_phone='"+txtPhone.getText()+"'  WHERE suplier_id='"+Id+"' ";
                        java.sql.Connection con=new Utility_KoneksiDB().koneksi();
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();

                        JOptionPane.showMessageDialog(null, "Update Data Suplier Berhasil");
                        Keluar();
                }                
                catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                }
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnExit = new javax.swing.JLabel();
        SPtableAutoComplete = new javax.swing.JScrollPane();
        tableAutoComplete = new javax.swing.JTable();
        txtSuplierName = new javax.swing.JTextField();
        txtAdress = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        btnBersih = new khansapos.Utility_ButtonFlat();
        btnUpdate = new khansapos.Utility_ButtonFlat();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1246, 714));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));
        jPanel1.setMaximumSize(new java.awt.Dimension(970, 339));
        jPanel1.setMinimumSize(new java.awt.Dimension(970, 339));
        jPanel1.setPreferredSize(new java.awt.Dimension(970, 339));
        jPanel1.setRequestFocusEnabled(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 123, 255));

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Edit Suplier");

        btnExit.setBackground(new java.awt.Color(85, 118, 118));
        btnExit.setDisplayedMnemonic('c');
        btnExit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 0, 0));
        btnExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnExit.setLabelFor(this);
        btnExit.setText("Close");
        btnExit.setToolTipText(null);
        btnExit.setBorder(null);
        btnExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExit.setOpaque(true);
        btnExit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnExitFocusLost(evt);
            }
        });
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExitMouseExited(evt);
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
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(14, Short.MAX_VALUE))
            .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, -1));

        SPtableAutoComplete.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        SPtableAutoComplete.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SPtableAutoComplete.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SPtableAutoComplete.setFocusable(false);

        tableAutoComplete.setForeground(new java.awt.Color(153, 153, 153));
        tableAutoComplete.setModel(new javax.swing.table.DefaultTableModel(
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
        tableAutoComplete.setGridColor(new java.awt.Color(255, 255, 255));
        tableAutoComplete.setShowGrid(false);
        tableAutoComplete.setSurrendersFocusOnKeystroke(true);
        tableAutoComplete.setTableHeader(null
        );
        SPtableAutoComplete.setViewportView(tableAutoComplete);

        jPanel1.add(SPtableAutoComplete, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 330, 0));

        txtSuplierName.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtSuplierName.setToolTipText(null);
        txtSuplierName.setBorder(null);
        txtSuplierName.setOpaque(false);
        txtSuplierName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSuplierNameFocusLost(evt);
            }
        });
        txtSuplierName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSuplierNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSuplierNameKeyReleased(evt);
            }
        });
        jPanel1.add(txtSuplierName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 335, -1));

        txtAdress.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtAdress.setToolTipText(null);
        txtAdress.setBorder(null);
        txtAdress.setOpaque(false);
        txtAdress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAdressKeyPressed(evt);
            }
        });
        jPanel1.add(txtAdress, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 815, -1));

        txtPhone.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtPhone.setToolTipText(null);
        txtPhone.setBorder(null);
        txtPhone.setOpaque(false);
        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneKeyPressed(evt);
            }
        });
        jPanel1.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 289, -1));

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Nama Suplier");
        jLabel1.setToolTipText(null);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 101, -1, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 335, 10));

        jLabel3.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Alamat ");
        jLabel3.setToolTipText(null);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 149, -1, 20));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 815, 10));

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Phone ");
        jLabel4.setToolTipText(null);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 197, -1, 20));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 289, 10));

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
        jPanel1.add(btnBersih, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 290, 90, 30));

        btnUpdate.setMnemonic('u');
        btnUpdate.setText("Update");
        btnUpdate.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnUpdate.setMouseHover(new java.awt.Color(26, 149, 255));
        btnUpdate.setMousePress(new java.awt.Color(204, 204, 204));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 290, 220, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setBounds(0, 0, 986, 369);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExitFocusLost
        Keluar();
    }//GEN-LAST:event_btnExitFocusLost

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        Keluar();
    }//GEN-LAST:event_btnExitMouseClicked

    private void btnExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseEntered
        btnExit.setForeground(new Color(255,255,255));
        btnExit.setBackground(new Color(217,0,0));
    }//GEN-LAST:event_btnExitMouseEntered

    private void btnExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseExited
        btnExit.setForeground(new Color(255,0,0));
        btnExit.setBackground(new Color(85,118,118));
    }//GEN-LAST:event_btnExitMouseExited

    private void txtSuplierNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSuplierNameFocusLost
        SPtableAutoComplete.setVisible(false);
    }//GEN-LAST:event_txtSuplierNameFocusLost

    private void txtSuplierNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSuplierNameKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            //txtAlamat.setText(null);
            txtAdress.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSuplierName.setText("");
        }
    }//GEN-LAST:event_txtSuplierNameKeyPressed

    private void txtSuplierNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSuplierNameKeyReleased
        if (txtSuplierName.getText().trim().isEmpty()) {
            SPtableAutoComplete.setVisible(false);
        } else{
            PopUp();
        }
    }//GEN-LAST:event_txtSuplierNameKeyReleased

    private void txtAdressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdressKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            //txtPhone.setText(null);
            txtPhone.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtAdress.setText("");
        }
    }//GEN-LAST:event_txtAdressKeyPressed

    private void txtPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            //Simpan();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtPhone.setText("");
        }
    }//GEN-LAST:event_txtPhoneKeyPressed

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
        Bersih();
    }//GEN-LAST:event_btnBersihActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Simpan();
    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane SPtableAutoComplete;
    private khansapos.Utility_ButtonFlat btnBersih;
    private static javax.swing.JLabel btnExit;
    private khansapos.Utility_ButtonFlat btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable tableAutoComplete;
    private javax.swing.JTextField txtAdress;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSuplierName;
    // End of variables declaration//GEN-END:variables
}
