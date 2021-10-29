package khansapos;

import java.awt.*; 
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.crypto.Cipher;  
import javax.swing.ImageIcon;

        

public class LoginForm extends javax.swing.JFrame {  
   static Cipher cipher;  
   private int count;
   
    public LoginForm() {
        initComponents(); 
        setIcon();
        cekDatabase();
    }
    
    private void setIcon(){
        ImageIcon icon = new ImageIcon("image/keys.png");
        setIconImage(icon.getImage());         
    }
    
    private void cekDatabase(){
        try{
            java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rsCount=st.executeQuery("SELECT COUNT(*) AS rowcount FROM users");
                rsCount.next();
                 count = rsCount.getInt("rowcount");
                    if (count==0){
                            JOptionPane.showMessageDialog(null,"<html>Data user masih kosong, gunakan:<br> "
                                    + "User Name = admin <br> password = admin <br>untuk LOGIN<html>",
                                    "Khansa POS",JOptionPane.INFORMATION_MESSAGE); 
                    }
        }
            catch(HeadlessException | SQLException b){
                JOptionPane.showMessageDialog(null, b.getMessage());
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

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PasswordField = new javax.swing.JPasswordField();
        txtUserName = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lbSetting = new javax.swing.JLabel();
        btnLogin = new javax.swing.JLabel();
        btnCancel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Khansa POS");
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("User Name");
        jLabel1.setToolTipText(null);
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 130, -1));

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Password");
        jLabel2.setToolTipText(null);
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 130, -1));

        PasswordField.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        PasswordField.setToolTipText(null);
        PasswordField.setBorder(null);
        PasswordField.setOpaque(false);
        PasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PasswordFieldKeyPressed(evt);
            }
        });
        jPanel2.add(PasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 320, 20));

        txtUserName.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtUserName.setToolTipText(null);
        txtUserName.setBorder(null);
        txtUserName.setOpaque(false);
        txtUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserNameKeyPressed(evt);
            }
        });
        jPanel2.add(txtUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 320, 20));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 320, 10));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 320, -1));

        lbSetting.setDisplayedMnemonic('t');
        lbSetting.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        lbSetting.setForeground(new java.awt.Color(102, 102, 102));
        lbSetting.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\iconSettings16.png")); // NOI18N
        lbSetting.setLabelFor(this);
        lbSetting.setText("Database Setting ");
        lbSetting.setToolTipText(null);
        lbSetting.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbSetting.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                lbSettingFocusLost(evt);
            }
        });
        lbSetting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbSettingMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbSettingMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbSettingMouseExited(evt);
            }
        });
        jPanel2.add(lbSetting, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, 30));

        btnLogin.setBackground(new java.awt.Color(0, 123, 255));
        btnLogin.setDisplayedMnemonic('i');
        btnLogin.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLogin.setLabelFor(this);
        btnLogin.setText("Login");
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin.setOpaque(true);
        btnLogin.setPreferredSize(new java.awt.Dimension(75, 25));
        btnLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnLoginFocusLost(evt);
            }
        });
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLoginMouseExited(evt);
            }
        });
        jPanel2.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, 90, 30));

        btnCancel.setBackground(new java.awt.Color(235, 154, 35));
        btnCancel.setDisplayedMnemonic('c');
        btnCancel.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCancel.setLabelFor(this);
        btnCancel.setText("Cancel");
        btnCancel.setToolTipText(null);
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
        jPanel2.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 61, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 380, 220));

        jPanel1.setBackground(new java.awt.Color(0, 123, 255));
        jPanel1.setBorder(null);
        jPanel1.setToolTipText(null);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(255, 0, 0));
        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("hansa");
        jLabel3.setToolTipText(null);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 160, 50));

        jLabel4.setBackground(new java.awt.Color(255, 0, 0));
        jLabel4.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 56)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("K");
        jLabel4.setToolTipText(null);
        jLabel4.setAlignmentX(0.5F);
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel4.setOpaque(true);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 61, 55));

        jLabel5.setBackground(new java.awt.Color(187, 220, 220));
        jLabel5.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 24)); // NOI18N
        jLabel5.setLabelFor(this);
        jLabel5.setText("Welcome");
        jLabel5.setToolTipText(null);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, -1, -1));

        jLabel6.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Point Of Sale");
        jLabel6.setToolTipText(null);
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 220));

        getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
       
    private void lbSettingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbSettingMouseEntered
        lbSetting.setForeground(new Color(217,0,0));
    }//GEN-LAST:event_lbSettingMouseEntered

    private void lbSettingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbSettingMouseExited
       lbSetting.setForeground(new Color(102,102,102));
    }//GEN-LAST:event_lbSettingMouseExited

    private void lbSettingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbSettingMouseClicked
            Settings();   
    }//GEN-LAST:event_lbSettingMouseClicked

    private void txtUserNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserNameKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            PasswordField.setText(null);
            PasswordField.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
          txtUserName.setText("");
         }
    }//GEN-LAST:event_txtUserNameKeyPressed

    private void PasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordFieldKeyPressed
           if(evt.getKeyCode() == KeyEvent.VK_ENTER){
               Login();
           } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
          PasswordField.setText("");
         }
    }//GEN-LAST:event_PasswordFieldKeyPressed

    private void lbSettingFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lbSettingFocusLost
        Settings();
    }//GEN-LAST:event_lbSettingFocusLost

    private void btnCancelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCancelFocusLost
        Keluar();
    }//GEN-LAST:event_btnCancelFocusLost

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        Keluar();
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseEntered
        btnCancel.setBackground(new Color(255,180,61));
        btnCancel.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_btnCancelMouseEntered

    private void btnCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseExited
        btnCancel.setBackground(new Color(235,154,35));
        btnCancel.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnCancelMouseExited

    private void btnLoginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnLoginFocusLost
        Login();
    }//GEN-LAST:event_btnLoginFocusLost

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked
        Login();
    }//GEN-LAST:event_btnLoginMouseClicked

    private void btnLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseEntered
        btnLogin.setForeground(new Color(0,0,0));
        btnLogin.setBackground(new Color(26,149,255));
    }//GEN-LAST:event_btnLoginMouseEntered

    private void btnLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseExited
        btnLogin.setForeground(new Color(255,255,255));
        btnLogin.setBackground(new Color(0,123,255));
    }//GEN-LAST:event_btnLoginMouseExited

    private void Keluar(){
        //UIManager.put("OptionPane.noButtonText", "Tidak");
        //UIManager.put("OptionPane.yesButtonText", "Ya");
        //if (JOptionPane.showConfirmDialog(null, "Transaksi sudah disimpan, Apakah Anda Yakin Ingin Keluar?", "Khansa POS", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
       // }
    }
    private void Settings(){
          new KoneksiSetting().setVisible(true);
         dispose();  
    }
    
    private void Login() {
        final String secretKey = "khansaPOS";
     
        String userName= String.valueOf(txtUserName.getText());
        String password = String.valueOf(PasswordField.getPassword());
        String encryptedString = Utility_AES.encrypt(password, secretKey) ;      
             
        if (txtUserName.getText() == null || txtUserName.getText().trim().isEmpty()||PasswordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "User Name atau Password Tidak Boleh Kosong!!", "Khansa POS", 
                    JOptionPane.WARNING_MESSAGE);
        }else {           
            if (count==0  && "admin".equals(password) && "admin".equals(userName)){               
                Utility_Session.setUserId("0");
                Utility_Session.setUserName("admin");
                Utility_Session.setUserLevel("Admin");
                new Beranda().setVisible(true);               
                dispose();                        
            } else if (count > 0){
                try{                   
                    java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
                    java.sql.Statement st = con.createStatement();
                    java.sql.ResultSet rsLogin = st.executeQuery("SELECT * FROM users WHERE user_name='"+txtUserName.getText()
                                +"' AND user_password='"+encryptedString+"'");
        
                            if(rsLogin.next()){                        
                                Utility_Session.setUserId(rsLogin.getString("user_id"));
                                Utility_Session.setUserName(rsLogin.getString("user_name"));
                                Utility_Session.setUserLevel(rsLogin.getString("user_level"));
                                new Beranda().setVisible(true);               
                                dispose(); 
                            } else{
                                JOptionPane.showMessageDialog(null,"Username Atau Password Salah!");
                                Bersih();
                            }
                } 
                    catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
               
            } else {
                JOptionPane.showMessageDialog(null,"Gunakan User Name = admin, Password = admin");
                Bersih();
            }
        }
    }   

private void Bersih(){
    txtUserName.setText("");
    PasswordField.setText("");
    txtUserName.requestFocus();
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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {  
            Frame f=new LoginForm();                        
           // f.setIconImage(Toolkit.getDefaultToolkit().getImage("image\\keys.png"));             
            f.setVisible(true);
        });
    }



 
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField PasswordField;
    private static javax.swing.JLabel btnCancel;
    private static javax.swing.JLabel btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private static javax.swing.JLabel lbSetting;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables

   

    
}
