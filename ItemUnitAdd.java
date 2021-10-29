package khansapos;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import net.proteanit.sql.DbUtils;

public class ItemUnitAdd extends javax.swing.JInternalFrame {
    private static String  Pemanggil;
    
    public ItemUnitAdd() {
        initComponents();
        IframeBorderLess();
        this.setSize(413, 160);
    }
    private void IframeBorderLess(){
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
    
    public static void setPanggil(String Nama){
        Pemanggil=Nama;        
    }    
    public static String getPanggil(){
        return Pemanggil;
    }
    private void Keluar(){
       // ItemFormAdd itf = new ItemFormAdd();
        //this.getParent().add(itf);
       //itf.setVisible(true);
        this.setVisible(false);     
    }
    
        private void Simpan(){
             
        if (txtSatuan.getText() == null || txtSatuan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Satuan Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
                try{   
                       String sql ="INSERT INTO iunits(unit_name) "
                                + "VALUES ('"+txtSatuan.getText()+"')";
                        java.sql.Connection con=new Utility_KoneksiDB().koneksi();
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                    
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data satuan Berhasil");
                        Keluar();                  
                    }                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
        }         
    }
        
   private void PopUpSatuan(){
        try {
            java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT unit_id,unit_name FROM iunits WHERE unit_name LIKE '"+txtSatuan.getText()+"%'");
            List.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                Utility_Table uts = new Utility_Table();
                //uts.Header(List,0,"",-10);
                //uts.Header(List,1,"",1);
                List.setBackground(new Color(255,255,255));
                List.setShowGrid(false);
                List.removeColumn(List.getColumnModel().getColumn(0));
                
                if (rs.getRow() <= 3) {
                    SList.setSize(200, (rs.getRow()*17)+2);
                } else{
                    SList.setSize(200, (3*17)+2);                    
                }
                    SList.setVisible(true); 
            } else {
                    SList.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
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
        lbExit = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSatuan = new javax.swing.JTextField();
        SList = new javax.swing.JScrollPane();
        List = new javax.swing.JTable();
        jSeparator5 = new javax.swing.JSeparator();
        btnSimpan = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(87, 176, 86));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel2.add(lbExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(333, 0, 79, 36));

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tambah Satuan");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 2, -1, 34));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, -1, -1));

        jLabel5.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Satuan");
        jLabel5.setToolTipText(null);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 76, 20));

        txtSatuan.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtSatuan.setToolTipText(null);
        txtSatuan.setBorder(null);
        txtSatuan.setOpaque(false);
        txtSatuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSatuanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSatuanKeyReleased(evt);
            }
        });
        jPanel1.add(txtSatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 190, -1));

        SList.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        SList.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SList.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SList.setFocusable(false);
        SList.setPreferredSize(new java.awt.Dimension(200, 200));

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

        jPanel1.add(SList, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 90, -1, 0));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 220, 10));

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
        jPanel1.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
        );

        pack();
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

    private void txtSatuanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanKeyReleased
        if (txtSatuan.getText().trim().isEmpty()) {
            SList.setVisible(false);
        } else{
            PopUpSatuan();
        }
    }//GEN-LAST:event_txtSatuanKeyReleased

    private void txtSatuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSatuanKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Simpan();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSatuan.setText("");
        }
    }//GEN-LAST:event_txtSatuanKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable List;
    private javax.swing.JScrollPane SList;
    private static javax.swing.JLabel btnSimpan;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator5;
    private static javax.swing.JLabel lbExit;
    private javax.swing.JTextField txtSatuan;
    // End of variables declaration//GEN-END:variables
}
