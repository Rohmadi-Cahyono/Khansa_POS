package khansapos;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public final class KoneksiSetting extends javax.swing.JFrame {
Properties prop;

    public KoneksiSetting() {
        initComponents();
        setIcon();
    }
    
    private void setIcon(){
         ImageIcon icon = new ImageIcon("image/keys.png");
        setIconImage(icon.getImage());   
}
    
    private void CreatePropertiesFile () {
        if (txtHost.getText().trim().isEmpty() || txtPort.getText().trim().isEmpty() || txtUser.getText().trim().isEmpty() || 
                txtDatabase.getText().trim().isEmpty() ) {
            JOptionPane.showMessageDialog(null, "<html>Host Name <br> Port Number <br> User <br> Database Name <br>"
                    + "TIDAK BOLEH KOSONG !!</html>", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
            prop = new Properties();
            prop.setProperty("HOST", txtHost.getText());
            prop.setProperty("PORT", txtPort.getText());
            prop.setProperty("USER", txtUser.getText());
            prop.setProperty("PASS", String.copyValueOf(txtPassword.getPassword(), 0, txtPassword.getPassword().length));
            prop.setProperty("DB", txtDatabase.getText());
        
            FileOutputStream fos;
                try {
                        fos = new FileOutputStream("SettingDB.xml");
                        prop.storeToXML(fos, "Settingan", "UTF-8");
                        JOptionPane.showMessageDialog(this, "<html>KONFIGURASI DATABASE BERHASIL <br>"
                                + "Untuk Menyimpan pengaturan, Aplikasi Akan ditutup <br>Silahkan jalankan ulang aplikasi</html>");
                        Tutup();
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
         }
    }

    private void ReadPropertiesFile() {  

            prop = new Properties();  
                try {  
                    prop.loadFromXML(new FileInputStream("SettingDB.xml"));  
                    JOptionPane.showMessageDialog(this, "Host : " + prop.getProperty("HOST")  
                        + "\nPort : " + prop.getProperty("PORT")  
                        + "\nUser : " + prop.getProperty("USER")  
                        + "\nPassword : " + prop.getProperty("PASS")  
                        + "\nDatabase   : " + prop.getProperty("DB"));  
                } catch (IOException ex) {  
                    JOptionPane.showMessageDialog(this, ex.getMessage());        
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

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtHost = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        txtPort = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        txtDatabase = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        txtPassword = new javax.swing.JPasswordField();
        btnSave = new javax.swing.JLabel();
        btnCancel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lbSetting = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnView = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImages(null);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));
        jPanel1.setToolTipText(null);

        jLabel3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Ip Address / Server Name");
        jLabel3.setToolTipText(null);

        txtHost.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtHost.setToolTipText(null);
        txtHost.setBorder(null);
        txtHost.setOpaque(false);
        txtHost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHostKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Port");
        jLabel4.setToolTipText(null);

        txtPort.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtPort.setToolTipText(null);
        txtPort.setBorder(null);
        txtPort.setOpaque(false);
        txtPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPortKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("User Name");
        jLabel5.setToolTipText(null);

        txtUser.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtUser.setToolTipText(null);
        txtUser.setBorder(null);
        txtUser.setOpaque(false);
        txtUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Password");
        jLabel6.setToolTipText(null);

        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Database Name");
        jLabel7.setToolTipText(null);

        txtDatabase.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtDatabase.setToolTipText(null);
        txtDatabase.setBorder(null);
        txtDatabase.setOpaque(false);
        txtDatabase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDatabaseKeyPressed(evt);
            }
        });

        txtPassword.setBorder(null);
        txtPassword.setOpaque(false);
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(0, 123, 255));
        btnSave.setDisplayedMnemonic('s');
        btnSave.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSave.setLabelFor(this);
        btnSave.setText("Save");
        btnSave.setAlignmentX(0.5F);
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.setOpaque(true);
        btnSave.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnSaveFocusLost(evt);
            }
        });
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSaveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSaveMouseExited(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(235, 154, 35));
        btnCancel.setDisplayedMnemonic('c');
        btnCancel.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCancel.setLabelFor(this);
        btnCancel.setText("Cancel");
        btnCancel.setBorder(null);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancel.setOpaque(true);
        btnCancel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnCancelFocusLost(evt);
            }
        });
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator2)
                            .addComponent(txtPort)
                            .addComponent(jLabel4)
                            .addComponent(jSeparator1)
                            .addComponent(txtHost)
                            .addComponent(jLabel5)
                            .addComponent(txtUser)
                            .addComponent(jSeparator3)
                            .addComponent(jLabel6)
                            .addComponent(jSeparator4)
                            .addComponent(jLabel7)
                            .addComponent(txtDatabase)
                            .addComponent(jSeparator5)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHost, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(4, 4, 4)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addGap(4, 4, 4)
                .addComponent(txtDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, -1, 420));

        jPanel2.setBackground(new java.awt.Color(0, 123, 255));

        lbSetting.setBackground(new java.awt.Color(255, 255, 255));
        lbSetting.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        lbSetting.setForeground(new java.awt.Color(102, 102, 102));
        lbSetting.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\database-settings-icon.png")); // NOI18N
        lbSetting.setToolTipText(null);
        lbSetting.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DataBase Settings");
        jLabel1.setToolTipText(null);

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("<html>Untuk menghubungkan aplikasi ke database<br> isikan pengaturan berikut<html>");
        jLabel2.setToolTipText(null);

        btnView.setBackground(new java.awt.Color(0, 123, 255));
        btnView.setDisplayedMnemonic('v');
        btnView.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnView.setForeground(new java.awt.Color(255, 255, 255));
        btnView.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnView.setLabelFor(this);
        btnView.setText("View Setting");
        btnView.setAlignmentX(0.5F);
        btnView.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnView.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnView.setOpaque(true);
        btnView.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnViewFocusLost(evt);
            }
        });
        btnView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnViewMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnViewMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnViewMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lbSetting)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbSetting)
                        .addGap(18, 18, 18)))
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 246, Short.MAX_VALUE)
                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 420));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtHostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHostKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtPort.setText(null);
            txtPort.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtHost.setText("");
         }
    }//GEN-LAST:event_txtHostKeyPressed

    private void txtPortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPortKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtUser.setText(null);
            txtUser.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtPort.setText("");
         }
    }//GEN-LAST:event_txtPortKeyPressed

    private void txtUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtPassword.setText(null);
            txtPassword.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtPort.setText("");
         }
    }//GEN-LAST:event_txtUserKeyPressed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtDatabase.setText(null);
            txtDatabase.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtPassword.setText("");
         }
    }//GEN-LAST:event_txtPasswordKeyPressed

    private void txtDatabaseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDatabaseKeyPressed
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             CreatePropertiesFile ();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtDatabase.setText("");
         }
    }//GEN-LAST:event_txtDatabaseKeyPressed

    private void btnCancelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCancelFocusLost
        Tutup();
    }//GEN-LAST:event_btnCancelFocusLost

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        Tutup();
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseEntered
        btnCancel.setBackground(new Color(255,180,61));
        btnCancel.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_btnCancelMouseEntered

    private void btnCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseExited
        btnCancel.setBackground(new Color(235,154,35));
        btnCancel.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnCancelMouseExited

    private void btnSaveFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSaveFocusLost
        CreatePropertiesFile();
    }//GEN-LAST:event_btnSaveFocusLost

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        CreatePropertiesFile();
    }//GEN-LAST:event_btnSaveMouseClicked

    private void btnSaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseEntered
        btnSave.setForeground(new Color(0,0,0));
        btnSave.setBackground(new Color(26,149,255));
    }//GEN-LAST:event_btnSaveMouseEntered

    private void btnSaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseExited
        btnSave.setForeground(new Color(255,255,255));
        btnSave.setBackground(new Color(0,123,255));
    }//GEN-LAST:event_btnSaveMouseExited

    private void btnViewFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnViewFocusLost
        ReadPropertiesFile();
    }//GEN-LAST:event_btnViewFocusLost

    private void btnViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewMouseClicked
        ReadPropertiesFile();
    }//GEN-LAST:event_btnViewMouseClicked

    private void btnViewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewMouseEntered
        btnView.setForeground(new Color(235,154,35));
       btnView.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(235,154,35)));
        //btnView.setBackground(new Color(0,0,0));
    }//GEN-LAST:event_btnViewMouseEntered

    private void btnViewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewMouseExited
       btnView.setForeground(new Color(255,255,255));
       btnView.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(255,255,255)));
    }//GEN-LAST:event_btnViewMouseExited

    private void Tutup(){
            new LoginForm().setVisible(true);
            dispose();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KoneksiSetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KoneksiSetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KoneksiSetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KoneksiSetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            Frame fr=new KoneksiSetting();                        
           //fr.setIconImage(Toolkit.getDefaultToolkit().getImage("\\image\\iconSetting32.PNG"));    
            
            fr.setVisible(true);
            
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLabel btnCancel;
    private javax.swing.JLabel btnSave;
    private javax.swing.JLabel btnView;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lbSetting;
    private javax.swing.JTextField txtDatabase;
    private javax.swing.JTextField txtHost;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables


}
