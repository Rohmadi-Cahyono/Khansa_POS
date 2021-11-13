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


public class UserFormAdd extends javax.swing.JInternalFrame {
    java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
    
    public UserFormAdd() {
        initComponents();
        IframeBorderLess();
         
        SPtableAutoComplete.setVisible(false);
        SPtableAutoComplete.getViewport().setBackground(Color.WHITE);
        SPlistUserLevel.setVisible(false);
        Tengah();
        SwingUtilities.invokeLater(() -> { txtUserName.requestFocusInWindow(); }); 
    }
        
    private void IframeBorderLess(){
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setSize(970, 438);
    }
    
    private void Tengah(){
        Dimension formIni = this.getSize();
        this.setLocation(( Utility_Session.getPanelW()-formIni.width )/2,(Utility_Session.getPanelH()-formIni.height )/2);
    }    
   
    private void Keluar(){
        UserForm uf = new UserForm();
        this.getParent().add(uf);
        uf.setVisible(true);
        this.setVisible(false);     
    }
    
    private void PopUp(){
        try {
           
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT user_id,user_name FROM users WHERE user_name LIKE '"+txtUserName.getText()+"%'");
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
        RePasswordField.setText("");
        PasswordField.setText("");
        txtUserLevel.setText("");
        txtPhone.setText("");
        txtAlamat.setText("");
        txtUserName.setText("");
        txtUserName.requestFocus();
    }
   
    private void Simpan(){
        final String secretKey = "khansaPOS";     
        String password = String.valueOf(PasswordField.getPassword());
        String rePassword = String.valueOf(RePasswordField.getPassword());
        String encryptedString = Utility_AES.encrypt(password, secretKey) ;      
             
        if (txtUserName.getText() == null || txtUserName.getText().trim().isEmpty()||PasswordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "Data User Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
            if (password .equals ( rePassword)){
                try{   
                        java.util.Date dt = new java.util.Date();
                        java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(dt);
                    
                        String sql ="INSERT INTO users(user_name,user_address,user_phone,user_level,user_password,date_created) VALUES ('"+txtUserName.getText()+"','"+txtAlamat.getText()+"','"+txtPhone.getText()+"','"+txtUserLevel.getText()+"','"+encryptedString+"','"+currentTime+"')";
                        
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                    
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data User Berhasil");
                        Keluar();
                  
                    }
                
                        catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
            } else {
                JOptionPane.showMessageDialog(null, "Password dan Re-Password Harus Sama!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
            }
            //txtUserName.requestFocus();
        }         
    }
        
    private void PilihLevel(){
        txtUserLevel.setText(listUserLevel.getSelectedValue());
        SPlistUserLevel.setVisible(false);
        PasswordField.requestFocus();
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
        lbExit = new javax.swing.JLabel();
        SPtableAutoComplete = new javax.swing.JScrollPane();
        tableAutoComplete = new javax.swing.JTable();
        SPlistUserLevel = new javax.swing.JScrollPane();
        listUserLevel = new javax.swing.JList<>();
        txtUserName = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtUserLevel = new javax.swing.JTextField();
        PasswordField = new javax.swing.JPasswordField();
        RePasswordField = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        btnSimpan = new khansapos.Utility_ButtonFlat();
        btnBersih = new khansapos.Utility_ButtonFlat();

        setBackground(new java.awt.Color(242, 248, 248));
        setMinimumSize(new java.awt.Dimension(970, 438));
        setPreferredSize(new java.awt.Dimension(970, 438));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(87, 176, 86)));
        jPanel1.setMaximumSize(new java.awt.Dimension(970, 438));
        jPanel1.setMinimumSize(new java.awt.Dimension(970, 438));
        jPanel1.setPreferredSize(new java.awt.Dimension(970, 438));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(87, 176, 86));

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tambah User");

        lbExit.setBackground(new java.awt.Color(85, 118, 118));
        lbExit.setDisplayedMnemonic('c');
        lbExit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        lbExit.setForeground(new java.awt.Color(255, 0, 0));
        lbExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbExit.setLabelFor(this);
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

        listUserLevel.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        listUserLevel.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Admin", "Kasir" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listUserLevel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                listUserLevelFocusLost(evt);
            }
        });
        listUserLevel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listUserLevelMouseClicked(evt);
            }
        });
        listUserLevel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                listUserLevelKeyPressed(evt);
            }
        });
        SPlistUserLevel.setViewportView(listUserLevel);

        jPanel1.add(SPlistUserLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 180, 47));

        txtUserName.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtUserName.setToolTipText(null);
        txtUserName.setBorder(null);
        txtUserName.setOpaque(false);
        txtUserName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUserNameFocusLost(evt);
            }
        });
        txtUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUserNameKeyReleased(evt);
            }
        });
        jPanel1.add(txtUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 103, 335, -1));

        txtAlamat.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtAlamat.setToolTipText(null);
        txtAlamat.setBorder(null);
        txtAlamat.setOpaque(false);
        txtAlamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAlamatKeyPressed(evt);
            }
        });
        jPanel1.add(txtAlamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 151, 815, -1));

        txtPhone.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtPhone.setToolTipText(null);
        txtPhone.setBorder(null);
        txtPhone.setOpaque(false);
        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneKeyPressed(evt);
            }
        });
        jPanel1.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 199, 289, -1));

        txtUserLevel.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtUserLevel.setToolTipText(null);
        txtUserLevel.setBorder(null);
        txtUserLevel.setOpaque(false);
        txtUserLevel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUserLevelFocusGained(evt);
            }
        });
        jPanel1.add(txtUserLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 247, 174, -1));

        PasswordField.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        PasswordField.setToolTipText(null);
        PasswordField.setBorder(null);
        PasswordField.setOpaque(false);
        PasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PasswordFieldKeyPressed(evt);
            }
        });
        jPanel1.add(PasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 295, 312, -1));

        RePasswordField.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        RePasswordField.setToolTipText(null);
        RePasswordField.setBorder(null);
        RePasswordField.setOpaque(false);
        RePasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RePasswordFieldKeyPressed(evt);
            }
        });
        jPanel1.add(RePasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 343, 318, -1));

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Nama User ");
        jLabel1.setToolTipText(null);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 101, -1, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 121, 335, 10));

        jLabel3.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Alamat ");
        jLabel3.setToolTipText(null);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 149, -1, 20));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 169, 815, 10));

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Phone ");
        jLabel4.setToolTipText(null);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 197, -1, 20));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 217, 289, 10));

        jLabel5.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Level User ");
        jLabel5.setToolTipText(null);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 245, 76, 20));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 265, 174, 10));

        jLabel6.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Password ");
        jLabel6.setToolTipText(null);
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 293, 76, 20));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 313, 317, 10));

        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Re-Password ");
        jLabel7.setToolTipText(null);
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 341, -1, 20));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 361, 318, 10));

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

        btnSimpan.setMnemonic('s');
        btnSimpan.setText("Simpan");
        btnSimpan.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnSimpan.setMouseHover(new java.awt.Color(113, 202, 112));
        btnSimpan.setMousePress(new java.awt.Color(204, 204, 204));
        btnSimpan.setWarnaBackground(new java.awt.Color(87, 176, 86));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel1.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 390, 210, 30));

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
        jPanel1.add(btnBersih, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 390, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setBounds(0, 0, 986, 466);
    }// </editor-fold>//GEN-END:initComponents

    

    private void txtUserNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserNameKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtAlamat.setText(null);
            txtAlamat.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
          txtUserName.setText("");
        }
    }//GEN-LAST:event_txtUserNameKeyPressed

    private void txtPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtUserLevel.setText(null);            
            txtUserLevel.requestFocus();            
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
          txtPhone.setText("");
         }
    }//GEN-LAST:event_txtPhoneKeyPressed

    private void PasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            RePasswordField.setText(null);
            RePasswordField.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            PasswordField.setText("");
         }
    }//GEN-LAST:event_PasswordFieldKeyPressed

    private void RePasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RePasswordFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Simpan();            
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
           RePasswordField.setText(null);
         }
    }//GEN-LAST:event_RePasswordFieldKeyPressed

    private void txtAlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlamatKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtPhone.setText(null);
            txtPhone.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
          txtAlamat.setText("");
         }      
    }//GEN-LAST:event_txtAlamatKeyPressed

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

    private void txtUserNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserNameFocusLost
        SPtableAutoComplete.setVisible(false);
    }//GEN-LAST:event_txtUserNameFocusLost

    private void listUserLevelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listUserLevelMouseClicked
        PilihLevel();
    }//GEN-LAST:event_listUserLevelMouseClicked

    private void txtUserLevelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserLevelFocusGained
        SPlistUserLevel.setVisible(true);
        listUserLevel.setSelectedIndex(0);
        listUserLevel.requestFocus();
    }//GEN-LAST:event_txtUserLevelFocusGained

    private void listUserLevelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listUserLevelKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            PilihLevel();
        }
    }//GEN-LAST:event_listUserLevelKeyPressed

    private void listUserLevelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_listUserLevelFocusLost
        SPlistUserLevel.setVisible(false);
    }//GEN-LAST:event_listUserLevelFocusLost

    private void txtUserNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserNameKeyReleased
        if (txtUserName.getText().trim().isEmpty()) {
             SPtableAutoComplete.setVisible(false);       
        } else{          
            PopUp();
        }         
    }//GEN-LAST:event_txtUserNameKeyReleased

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        Simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
        Bersih();
    }//GEN-LAST:event_btnBersihActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JPasswordField RePasswordField;
    private javax.swing.JScrollPane SPlistUserLevel;
    private javax.swing.JScrollPane SPtableAutoComplete;
    private khansapos.Utility_ButtonFlat btnBersih;
    private khansapos.Utility_ButtonFlat btnSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private static javax.swing.JLabel lbExit;
    private javax.swing.JList<String> listUserLevel;
    private javax.swing.JTable tableAutoComplete;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtUserLevel;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables




}
