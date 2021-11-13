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

public class ItemCategoryEdit extends javax.swing.JInternalFrame {
     java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
     String Id;
     
    public ItemCategoryEdit() {
        initComponents();
        IframeBorderLess(); 
        Tengah();
        TampilEdit();
        SwingUtilities.invokeLater(() -> {txtKategori.requestFocusInWindow(); });
    }
    
    private void IframeBorderLess(){
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setSize(421, 156);
    }
 
    private void Tengah(){
        Dimension formIni = this.getSize();
        this.setLocation(( Utility_Session.getPanelW()-formIni.width )/2,(Utility_Session.getPanelH()-formIni.height )/2);
    }
     
    private void TampilEdit(){
        Id = ItemCategory.Id; 
        
        try {           
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT * FROM icategory WHERE category_id =' "+Id+" ' ");
                if(rs.next()){                    
                    txtKategori.setText(rs.getString("category_name"));
                }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }  
    }
        
    private void Update(){             
        if (txtKategori.getText() == null || txtKategori.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Kategori Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
                try{   
                        Id = ItemCategory.Id;                        
                        String sql ="UPDATE icategory SET category_name='"+txtKategori.getText()+"'  WHERE category_id='"+Id+"' ";                      
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();

                        JOptionPane.showMessageDialog(null, "Update Data Kategori Barang Berhasil");               
                        Keluar();                  
                    }                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
        }         
    }
    
        
   private void PopUpKategori(){
        try {          
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT category_id,category_name FROM icategory WHERE category_name LIKE '"+txtKategori.getText()+"%'");
            List.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){ 
                List.setBackground(new Color(255,255,255));
                List.setShowGrid(false);
                List.removeColumn(List.getColumnModel().getColumn(0));
                
                if (rs.getRow() <= 3) {
                    SList.setSize(270, (rs.getRow()*17)+2);
                } else{
                    SList.setSize(270, (3*17)+2);                    
                }
                    SList.setVisible(true); 
            } else {
                    SList.setVisible(false);                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
   
    private void Keluar(){
        ItemCategory ic = new ItemCategory();
        this.getParent().add(ic);  
        ic.setVisible(true);
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

        jFrame1 = new javax.swing.JFrame();
        jPanel3 = new javax.swing.JPanel();
        utility_ButtonMetro2 = new khansapos.Utility_ButtonMetro();
        jPanel1 = new javax.swing.JPanel();
        txtKategori = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnClose = new khansapos.Utility_ButtonMetro();
        SList = new javax.swing.JScrollPane();
        List = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jSeparator5 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        btnUpdate = new khansapos.Utility_ButtonFlat();

        utility_ButtonMetro2.setText("utility_ButtonMetro2");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(206, 206, 206)
                .addComponent(utility_ButtonMetro2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(370, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(utility_ButtonMetro2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(281, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(235, 154, 35)));
        jPanel1.setPreferredSize(new java.awt.Dimension(421, 156));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtKategori.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtKategori.setToolTipText(null);
        txtKategori.setBorder(null);
        txtKategori.setOpaque(false);
        txtKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKategoriKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKategoriKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKategoriKeyTyped(evt);
            }
        });
        jPanel1.add(txtKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 270, -1));

        jPanel2.setBackground(new java.awt.Color(235, 154, 35));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tambah Kategori");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 2, -1, 34));

        btnClose.setMnemonic('c');
        btnClose.setText("Close");
        btnClose.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        jPanel2.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 0, 80, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 420, -1));

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

        jPanel1.add(SList, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 90, 260, 0));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 270, 10));

        jLabel5.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Kategori");
        jLabel5.setToolTipText(null);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 70, 20));

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
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 90, 30));

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

    private void txtKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Update();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtKategori.setText("");
        }
    }//GEN-LAST:event_txtKategoriKeyPressed

    private void txtKategoriKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriKeyReleased
        if (txtKategori.getText().trim().isEmpty()) {
            SList.setVisible(false);
        } else{
            PopUpKategori();
        }
    }//GEN-LAST:event_txtKategoriKeyReleased

    private void txtKategoriKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriKeyTyped
        if (txtKategori.getText().length() >= 15 ) {
            evt.consume();
        }
    }//GEN-LAST:event_txtKategoriKeyTyped

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        Keluar();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Update();
    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable List;
    private javax.swing.JScrollPane SList;
    private khansapos.Utility_ButtonMetro btnClose;
    private khansapos.Utility_ButtonFlat btnUpdate;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextField txtKategori;
    private khansapos.Utility_ButtonMetro utility_ButtonMetro2;
    // End of variables declaration//GEN-END:variables
}
