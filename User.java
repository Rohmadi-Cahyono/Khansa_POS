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

public class User extends javax.swing.JInternalFrame {
    java.sql.Connection con =  new UDbConnection().koneksi();
    private static String  Id;
    
    public User() {        
        initComponents();      
        IframeBorderLess(); 
        SPtableTampil.getViewport().setBackground(new Color(255,255,255));
        
        SListAdd.setVisible(false);
        SListAdd.getViewport().setBackground(new Color(255,255,255));
        SLevelAdd.setVisible(false);
        
        SListEdit.setVisible(false);
        SListEdit.getViewport().setBackground(new Color(255,255,255));
        SLevelEdit.setVisible(false);
        TampilUser(); 
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
       
    private void Tengah(){
        Dimension formIni = this.getSize();
        this.setLocation(( Beranda.PW-formIni.width )/2,(Beranda.PH-formIni.height )/2);
    }
    
    private void TampilUser() {      
        try {
            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery("SELECT user_id,user_name, user_address, user_phone, user_level,date_created, date_update FROM users ");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs));            
            TampilkanDiTabel();         
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
 

    

    
    private void Hapus(){       
        if (JOptionPane.showConfirmDialog(null, "Yakin data User akan dihapus?", "Khansa POS",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {            
            try {
                    java.sql.Statement st = con.createStatement();           
                    st.executeUpdate("DELETE FROM users WHERE user_id="+Id);            
                    JOptionPane.showMessageDialog(null, "Data User berhasil dihapus!");
                    TampilUser();
            } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
            }  
        }        
    }
    
     private void Cari(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT  user_id,user_name, user_address, user_phone, "
                    + "user_level,date_created, date_update  FROM users WHERE user_name LIKE '%"+txtSearch.getText()+"%'");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs)); 
            TampilkanDiTabel();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
     
     private void TampilkanDiTabel() {                     
            UTable ut = new UTable();         
          
            ut.Header(tableTampil,0,"",-10);
            ut.Header(tableTampil,1,"User Name",200);
            ut.Header(tableTampil,2,"Address",400);
            ut.Header(tableTampil,3,"Phone",150);
            ut.Header(tableTampil,4,"User Level",50);
            ut.Header(tableTampil,5,"Date Created",100);
            ut.Header(tableTampil,6,"Date Update",100);
                   
            tableTampil.getColumnModel().getColumn(5).setCellRenderer(ut.formatTanggal);
            tableTampil.getColumnModel().getColumn(6).setCellRenderer(ut.formatTanggal);
            //tableUser.getColumnModel().getColumn(2).setCellRenderer(new Utility_RupiahCellRenderer());
            tableTampil.removeColumn(tableTampil.getColumnModel().getColumn(0)); //tidak menampilkan kolom (index:0)
    }
     
     //-----------------------------------------------UserAdd-----------------------------------
    private void Tambah(){
        this.setVisible(false);
        Bersih();          
        SwingUtilities.invokeLater(() -> {txtNameAdd.requestFocusInWindow(); });
        UserAdd.setSize(1010, 440);                     
        UserAdd.setLocation(((Beranda.SW+120)-1010 )/2,((Beranda.SH+50)-440 )/2);
        UserAdd.setBackground(new Color(0, 0, 0, 0)); 
        UserAdd.setVisible(true);                     
        
    }
    
    private void PopUpAdd(){
        try {
           
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT user_id,user_name FROM users WHERE user_name LIKE '"+txtNameAdd.getText()+"%'");
            ListAdd.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                UTable uts = new UTable();
                uts.Header(ListAdd,0,"",-10);
                uts.Header(ListAdd,1,"",200);
                ListAdd.setBackground(new Color(255,255,255));
                ListAdd.setShowGrid(false);
                ListAdd.removeColumn(ListAdd.getColumnModel().getColumn(0));
                SListAdd.setLocation(txtNameAdd.getX(),txtNameAdd.getY()+20);
                
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
        RePasswordFieldAdd.setText("");
        PasswordFieldAdd.setText("");
        txtUserLevelAdd.setText("");
        txtPhoneAdd.setText("");
        txtAlamatAdd.setText("");
        txtNameAdd.setText("");
        txtNameAdd.requestFocus();
    }
   
    private void Simpan(){
        final String secretKey = "khansaPOS";     
        String password = String.valueOf(PasswordFieldAdd.getPassword());
        String rePassword = String.valueOf(RePasswordFieldAdd.getPassword());
        String encryptedString = UAES.encrypt(password, secretKey) ;      
             
        if (txtNameAdd.getText() == null || txtNameAdd.getText().trim().isEmpty()||PasswordFieldAdd.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "Data User Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
            if (password .equals ( rePassword)){
                try{   
                        java.util.Date dt = new java.util.Date();
                        java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(dt);
                    
                        String sql ="INSERT INTO users(user_name,user_address,user_phone,user_level,user_password,date_created) VALUES ('"+txtNameAdd.getText()+"','"+txtAlamatAdd.getText()+"','"+txtPhoneAdd.getText()+"','"+txtUserLevelAdd.getText()+"','"+encryptedString+"','"+currentTime+"')";
                        
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                    
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data User Berhasil");
                        KeluarAdd();
                  
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
        
    private void PilihLevelAdd(){
        txtUserLevelAdd.setText(LevelAdd.getSelectedValue());
        SLevelAdd.setVisible(false);
        PasswordFieldAdd.requestFocus();
    }  
    
    private void KeluarAdd(){
       UserAdd.dispose();
       this.setVisible(true);
       Cari();
       txtSearch.requestFocus();
   }
    
    
    //--------------------------------------------------------------UserEdit-------------------------------------------------
    public void Edit(){
        this.setVisible(false);       
        TampilEdit(); 
        SwingUtilities.invokeLater(() -> {txtNameEdit.requestFocusInWindow(); });
        UserEdit.setSize(1010,440);                      
        UserEdit.setLocation(((Beranda.SW+120)-1010 )/2,((Beranda.SH+50)-440 )/2);
        UserEdit.setBackground(new Color(0, 0, 0, 0));
        UserEdit.setVisible(true);
    }
    
    private void TampilEdit(){
         final String secretKey = "khansaPOS";
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT * FROM users WHERE user_id =' "+Id+" ' ");
                if(rs.next()){                    
                    txtNameEdit.setText(rs.getString("user_name"));
                    txtAlamatEdit.setText(rs.getString("user_address"));
                    txtPhoneEdit.setText(rs.getString("user_phone"));
                    txtLevelEdit.setText(rs.getString("user_level"));
                    String encryptedString = (rs.getString("user_password")) ;
                    String password = UAES.decrypt(encryptedString, secretKey) ;
                    PasswordFieldEdit.setText(password);
                    RePasswordFieldEdit.setText(password);
                }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }  
    }
   
    
    private void PopUpEdit(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT user_id,user_name FROM users "
                    + "WHERE user_name LIKE '"+txtNameEdit.getText()+"%'");
            ListEdit.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                UTable uts = new UTable();
                uts.Header(ListEdit,0,"",-10);
                uts.Header(ListEdit,1,"",200);
                ListEdit.setBackground(new Color(255,255,255));
                ListEdit.setShowGrid(false);
                ListEdit.removeColumn(ListEdit.getColumnModel().getColumn(0));
                SListEdit.setLocation(txtNameEdit.getX(),txtNameEdit.getY()+20);
                
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
        final String secretKey = "khansaPOS";     
        String password = String.valueOf(PasswordFieldEdit.getPassword());
        String rePassword = String.valueOf(RePasswordFieldEdit.getPassword());
        String encryptedString = UAES.encrypt(password, secretKey) ;      
             
        if (txtNameEdit.getText() == null || txtNameEdit.getText().trim().isEmpty()||PasswordFieldEdit.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "Data User Tidak Boleh Kosong!!", "Khansa POS", JOptionPane.WARNING_MESSAGE);
        }else {
            if (password .equals ( rePassword)){
                    try{   
                        /*
                        java.util.Date dt = new java.util.Date();
                        java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(dt);
                        
                        String sql ="INSERT INTO users(user_name,user_address,user_phone,user_level,user_password,date_created) VALUES ('"+txtUserName.getText()+"','"+txtAlamat.getText()+"','"+txtPhone.getText()+"','"+txtUserLevel.getText()+"','"+encryptedString+"','"+currentTime+"')";
                        java.sql.Connection con=new Utility_KoneksiDB().koneksi();
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                        */
                       // Id = UserForm.Id;
                        String sql ="UPDATE users SET user_name='"+txtNameEdit.getText()+"', user_address='"+txtAlamatEdit.getText()+"', "
                                + "user_phone='"+txtPhoneEdit.getText()+"', user_level='"+txtLevelEdit.getText()+"', "
                                + "user_password='"+encryptedString+"' WHERE user_id='"+Id+"' ";
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();

                        JOptionPane.showMessageDialog(null, "Update Data User Berhasil");
                        KeluarEdit();                  
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
        
    private void PilihLevelEdit(){
        txtLevelEdit.setText(LevelEdit.getSelectedValue());
        SLevelEdit.setVisible(false);
        PasswordFieldEdit.requestFocus();
    }
    
    private void KeluarEdit(){
       UserEdit.dispose();
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

        UserAdd = new javax.swing.JDialog();
        uPanelRoundrect2 = new Utility.UPanelRoundrect();
        jLabel3 = new javax.swing.JLabel();
        btnClose = new Utility.UButton();
        uPanelRoundrect1 = new Utility.UPanelRoundrect();
        SListAdd = new javax.swing.JScrollPane();
        ListAdd = new javax.swing.JTable();
        SLevelAdd = new javax.swing.JScrollPane();
        LevelAdd = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        txtNameAdd = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txtAlamatAdd = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        txtPhoneAdd = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        txtUserLevelAdd = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        PasswordFieldAdd = new javax.swing.JPasswordField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        RePasswordFieldAdd = new javax.swing.JPasswordField();
        jSeparator8 = new javax.swing.JSeparator();
        btnBersih = new Utility.UButton();
        btnSimpan = new Utility.UButton();
        UserEdit = new javax.swing.JDialog();
        uPanelRoundrect4 = new Utility.UPanelRoundrect();
        SListEdit = new javax.swing.JScrollPane();
        ListEdit = new javax.swing.JTable();
        SLevelEdit = new javax.swing.JScrollPane();
        LevelEdit = new javax.swing.JList<>();
        jLabel11 = new javax.swing.JLabel();
        txtNameEdit = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        txtAlamatEdit = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        txtPhoneEdit = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        txtLevelEdit = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        PasswordFieldEdit = new javax.swing.JPasswordField();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        RePasswordFieldEdit = new javax.swing.JPasswordField();
        jSeparator14 = new javax.swing.JSeparator();
        btnUpdate = new Utility.UButton();
        uPanelRoundrect3 = new Utility.UPanelRoundrect();
        jLabel10 = new javax.swing.JLabel();
        btnCloseEdit = new Utility.UButton();
        jPanel1 = new javax.swing.JPanel();
        panelEH = new javax.swing.JPanel();
        btnHapus = new Utility.UButton();
        btnEdit = new Utility.UButton();
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
        btnTambah = new Utility.UButton();

        UserAdd.setUndecorated(true);
        UserAdd.setResizable(false);
        UserAdd.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect2.setKetebalanBorder(2.5F);
        uPanelRoundrect2.setPreferredSize(new java.awt.Dimension(1000, 50));
        uPanelRoundrect2.setWarnaBackground(new java.awt.Color(88, 176, 86));
        uPanelRoundrect2.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tambah User");
        uPanelRoundrect2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 30));

        btnClose.setMnemonic('c');
        btnClose.setText("Close");
        btnClose.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnClose.setKetebalanBorder(0.0F);
        btnClose.setKetumpulanSudut(35);
        btnClose.setPreferredSize(new java.awt.Dimension(70, 20));
        btnClose.setWarnaBackgroundHover(new java.awt.Color(87, 176, 86));
        btnClose.setWarnaBorder(new java.awt.Color(87, 176, 86));
        btnClose.setWarnaForegroundHover(new java.awt.Color(255, 0, 0));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        uPanelRoundrect2.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(925, 15, -1, -1));

        uPanelRoundrect1.setKetebalanBorder(2.5F);
        uPanelRoundrect1.setMinimumSize(new java.awt.Dimension(960, 398));
        uPanelRoundrect1.setPreferredSize(new java.awt.Dimension(1000, 370));
        uPanelRoundrect1.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        uPanelRoundrect1.add(SListAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 330, 0));

        SLevelAdd.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SLevelAdd.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SLevelAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        SLevelAdd.setHorizontalScrollBar(null);
        SLevelAdd.setPreferredSize(new java.awt.Dimension(180, 70));

        LevelAdd.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        LevelAdd.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Admin", "Manager", "Kasir" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        LevelAdd.setMaximumSize(new java.awt.Dimension(180, 70));
        LevelAdd.setMinimumSize(new java.awt.Dimension(180, 70));
        LevelAdd.setPreferredSize(new java.awt.Dimension(180, 70));
        LevelAdd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                LevelAddFocusLost(evt);
            }
        });
        LevelAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LevelAddMouseClicked(evt);
            }
        });
        LevelAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LevelAddKeyPressed(evt);
            }
        });
        SLevelAdd.setViewportView(LevelAdd);

        uPanelRoundrect1.add(SLevelAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 180, 70));

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Nama User ");
        jLabel4.setToolTipText(null);
        uPanelRoundrect1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, 20));

        txtNameAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
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
        uPanelRoundrect1.add(txtNameAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 335, -1));

        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 67, 335, 10));

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Alamat ");
        jLabel5.setToolTipText(null);
        uPanelRoundrect1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, 20));

        txtAlamatAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtAlamatAdd.setToolTipText(null);
        txtAlamatAdd.setBorder(null);
        txtAlamatAdd.setOpaque(false);
        txtAlamatAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAlamatAddKeyPressed(evt);
            }
        });
        uPanelRoundrect1.add(txtAlamatAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 815, -1));

        jSeparator4.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 117, 815, 10));

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Phone ");
        jLabel6.setToolTipText(null);
        uPanelRoundrect1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, 20));

        txtPhoneAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtPhoneAdd.setToolTipText(null);
        txtPhoneAdd.setBorder(null);
        txtPhoneAdd.setOpaque(false);
        txtPhoneAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneAddKeyPressed(evt);
            }
        });
        uPanelRoundrect1.add(txtPhoneAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 289, -1));

        jSeparator5.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 167, 289, 10));

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Level User ");
        jLabel7.setToolTipText(null);
        uPanelRoundrect1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 76, 20));

        txtUserLevelAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtUserLevelAdd.setToolTipText(null);
        txtUserLevelAdd.setBorder(null);
        txtUserLevelAdd.setOpaque(false);
        txtUserLevelAdd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUserLevelAddFocusGained(evt);
            }
        });
        uPanelRoundrect1.add(txtUserLevelAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 174, -1));

        jSeparator6.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 217, 174, 10));

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Password ");
        jLabel8.setToolTipText(null);
        uPanelRoundrect1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 76, 20));

        PasswordFieldAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        PasswordFieldAdd.setToolTipText(null);
        PasswordFieldAdd.setBorder(null);
        PasswordFieldAdd.setOpaque(false);
        PasswordFieldAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PasswordFieldAddKeyPressed(evt);
            }
        });
        uPanelRoundrect1.add(PasswordFieldAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 312, -1));

        jSeparator7.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 267, 317, 10));

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Re-Password ");
        jLabel9.setToolTipText(null);
        uPanelRoundrect1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, -1, 20));

        RePasswordFieldAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RePasswordFieldAdd.setToolTipText(null);
        RePasswordFieldAdd.setBorder(null);
        RePasswordFieldAdd.setOpaque(false);
        RePasswordFieldAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RePasswordFieldAddKeyPressed(evt);
            }
        });
        uPanelRoundrect1.add(RePasswordFieldAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 318, -1));

        jSeparator8.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 317, 318, 10));

        btnBersih.setMnemonic('h');
        btnBersih.setText("Bersih");
        btnBersih.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnBersih.setKetebalanBorder(2.0F);
        btnBersih.setKetumpulanSudut(35);
        btnBersih.setPreferredSize(new java.awt.Dimension(150, 38));
        btnBersih.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnBersih.setWarnaBackgroundHover(new java.awt.Color(255, 231, 112));
        btnBersih.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnBersih.setWarnaBorder(new java.awt.Color(255, 231, 112));
        btnBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBersihActionPerformed(evt);
            }
        });
        uPanelRoundrect1.add(btnBersih, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 320, 120, -1));

        btnSimpan.setMnemonic('s');
        btnSimpan.setText("Simpan");
        btnSimpan.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnSimpan.setKetebalanBorder(2.0F);
        btnSimpan.setKetumpulanSudut(35);
        btnSimpan.setPreferredSize(new java.awt.Dimension(150, 38));
        btnSimpan.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        uPanelRoundrect1.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 320, 160, -1));

        uPanelRoundrect2.add(uPanelRoundrect1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 65, -1, -1));

        UserAdd.getContentPane().add(uPanelRoundrect2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 440));

        UserEdit.setUndecorated(true);
        UserEdit.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect4.setKetebalanBorder(2.5F);
        uPanelRoundrect4.setPreferredSize(new java.awt.Dimension(1000, 370));
        uPanelRoundrect4.setWarnaBorder(new java.awt.Color(255, 231, 112));
        uPanelRoundrect4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        uPanelRoundrect4.add(SListEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 330, 0));

        SLevelEdit.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SLevelEdit.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SLevelEdit.setPreferredSize(new java.awt.Dimension(180, 70));

        LevelEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        LevelEdit.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Admin", "Manager", "Kasir" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        LevelEdit.setToolTipText("");
        LevelEdit.setMaximumSize(new java.awt.Dimension(180, 70));
        LevelEdit.setMinimumSize(new java.awt.Dimension(180, 70));
        LevelEdit.setPreferredSize(new java.awt.Dimension(180, 70));
        LevelEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                LevelEditFocusLost(evt);
            }
        });
        LevelEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LevelEditMouseClicked(evt);
            }
        });
        LevelEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LevelEditKeyPressed(evt);
            }
        });
        SLevelEdit.setViewportView(LevelEdit);

        uPanelRoundrect4.add(SLevelEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 180, 70));

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Nama User ");
        jLabel11.setToolTipText(null);
        uPanelRoundrect4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, 20));

        txtNameEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
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
        uPanelRoundrect4.add(txtNameEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 335, -1));

        jSeparator9.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 57, 335, 10));

        jLabel12.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Alamat ");
        jLabel12.setToolTipText(null);
        uPanelRoundrect4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, 20));

        txtAlamatEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtAlamatEdit.setToolTipText(null);
        txtAlamatEdit.setBorder(null);
        txtAlamatEdit.setOpaque(false);
        txtAlamatEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAlamatEditKeyPressed(evt);
            }
        });
        uPanelRoundrect4.add(txtAlamatEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 815, -1));

        jSeparator10.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 107, 815, 10));

        jLabel13.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Phone ");
        jLabel13.setToolTipText(null);
        uPanelRoundrect4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, 20));

        txtPhoneEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtPhoneEdit.setToolTipText(null);
        txtPhoneEdit.setBorder(null);
        txtPhoneEdit.setOpaque(false);
        txtPhoneEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneEditKeyPressed(evt);
            }
        });
        uPanelRoundrect4.add(txtPhoneEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 289, -1));

        jSeparator11.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 157, 289, 10));

        jLabel14.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("Level User ");
        jLabel14.setToolTipText(null);
        uPanelRoundrect4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 76, 20));

        txtLevelEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtLevelEdit.setToolTipText(null);
        txtLevelEdit.setBorder(null);
        txtLevelEdit.setOpaque(false);
        txtLevelEdit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLevelEditFocusGained(evt);
            }
        });
        uPanelRoundrect4.add(txtLevelEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 174, -1));

        jSeparator12.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 207, 174, 10));

        jLabel15.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setText("Password ");
        jLabel15.setToolTipText(null);
        uPanelRoundrect4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 76, 20));

        PasswordFieldEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        PasswordFieldEdit.setToolTipText(null);
        PasswordFieldEdit.setBorder(null);
        PasswordFieldEdit.setOpaque(false);
        PasswordFieldEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PasswordFieldEditKeyPressed(evt);
            }
        });
        uPanelRoundrect4.add(PasswordFieldEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 312, -1));

        jSeparator13.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 257, 317, 10));

        jLabel16.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Re-Password ");
        jLabel16.setToolTipText(null);
        uPanelRoundrect4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, -1, 20));

        RePasswordFieldEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RePasswordFieldEdit.setToolTipText(null);
        RePasswordFieldEdit.setBorder(null);
        RePasswordFieldEdit.setOpaque(false);
        RePasswordFieldEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RePasswordFieldEditKeyPressed(evt);
            }
        });
        uPanelRoundrect4.add(RePasswordFieldEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, 318, -1));

        jSeparator14.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 307, 318, 10));

        btnUpdate.setText("Update");
        btnUpdate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnUpdate.setKetebalanBorder(2.0F);
        btnUpdate.setKetumpulanSudut(35);
        btnUpdate.setPreferredSize(new java.awt.Dimension(150, 38));
        btnUpdate.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnUpdate.setWarnaBackgroundHover(new java.awt.Color(255, 231, 112));
        btnUpdate.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnUpdate.setWarnaBorder(new java.awt.Color(255, 231, 112));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        uPanelRoundrect4.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 300, -1, -1));

        UserEdit.getContentPane().add(uPanelRoundrect4, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 65, 1000, -1));

        uPanelRoundrect3.setBackground(new java.awt.Color(255, 180, 61));
        uPanelRoundrect3.setKetebalanBorder(2.5F);
        uPanelRoundrect3.setPreferredSize(new java.awt.Dimension(1010, 440));
        uPanelRoundrect3.setWarnaBackground(new java.awt.Color(235, 154, 35));
        uPanelRoundrect3.setWarnaBorder(new java.awt.Color(255, 231, 112));
        uPanelRoundrect3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Edit User");
        uPanelRoundrect3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 28));

        btnCloseEdit.setMnemonic('c');
        btnCloseEdit.setText("Close");
        btnCloseEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnCloseEdit.setKetebalanBorder(2.0F);
        btnCloseEdit.setKetumpulanSudut(35);
        btnCloseEdit.setPreferredSize(new java.awt.Dimension(150, 38));
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
        uPanelRoundrect3.add(btnCloseEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, 70, 30));

        UserEdit.getContentPane().add(uPanelRoundrect3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setOpaque(true);
        setPreferredSize(new java.awt.Dimension(1246, 714));

        jPanel1.setBackground(new java.awt.Color(248, 251, 251));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelEH.setOpaque(false);
        panelEH.setPreferredSize(new java.awt.Dimension(130, 30));
        panelEH.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnHapus.setText("Hapus");
        btnHapus.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnHapus.setKetebalanBorder(2.0F);
        btnHapus.setKetumpulanSudut(35);
        btnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        btnHapus.setWarnaBackground(new java.awt.Color(255, 0, 0));
        btnHapus.setWarnaBackgroundHover(new java.awt.Color(255, 204, 204));
        btnHapus.setWarnaBackgroundPress(new java.awt.Color(255, 0, 0));
        btnHapus.setWarnaBorder(new java.awt.Color(255, 204, 204));
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        panelEH.add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 100, -1));

        btnEdit.setText("Edit");
        btnEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnEdit.setKetebalanBorder(2.0F);
        btnEdit.setKetumpulanSudut(35);
        btnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        btnEdit.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnEdit.setWarnaBackgroundHover(new java.awt.Color(255, 231, 112));
        btnEdit.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnEdit.setWarnaBorder(new java.awt.Color(255, 231, 112));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        panelEH.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.add(panelEH, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, 0));

        tableTampil.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
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
        tableTampil.setRowHeight(20);
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
        jLabel2.setText("Cari Nama User :");
        jLabel2.setToolTipText(null);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 110, -1, -1));
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

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 210, 10));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Master User");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 230, 40));

        btnTambah.setMnemonic('t');
        btnTambah.setText("Tambah User");
        btnTambah.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnTambah.setKetebalanBorder(2.5F);
        btnTambah.setKetumpulanSudut(35);
        btnTambah.setPreferredSize(new java.awt.Dimension(150, 38));
        btnTambah.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        jPanel1.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 1246, 714);
    }// </editor-fold>//GEN-END:initComponents


    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSearch.setText("");
            panelEH.setSize(172, 0);
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        Cari();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void tableTampilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTampilMouseClicked
        Id= tableTampil.getModel().getValueAt(tableTampil.getSelectedRow(), 0).toString(); //Ambil nilai kolom (0) dan masukkan ke variabel Id
       
        panelEH.setLocation( evt.getX() + SPtableTampil.getX(),  evt.getY() + SPtableTampil.getY());
        panelEH.setSize(172, 30);
    }//GEN-LAST:event_tableTampilMouseClicked

    private void LevelAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_LevelAddFocusLost
        SLevelAdd.setVisible(false);
    }//GEN-LAST:event_LevelAddFocusLost

    private void LevelAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LevelAddMouseClicked
        PilihLevelAdd();
    }//GEN-LAST:event_LevelAddMouseClicked

    private void LevelAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LevelAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            PilihLevelAdd();
        }
    }//GEN-LAST:event_LevelAddKeyPressed

    private void txtNameAddFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameAddFocusLost
        SListAdd.setVisible(false);
    }//GEN-LAST:event_txtNameAddFocusLost

    private void txtNameAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtAlamatAdd.setText(null);
            txtAlamatAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtNameAdd.setText("");
        }
    }//GEN-LAST:event_txtNameAddKeyPressed

    private void txtNameAddKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameAddKeyReleased
        if (txtNameAdd.getText().trim().isEmpty()) {
            SListAdd.setVisible(false);
        } else{
            PopUpAdd();
        }
    }//GEN-LAST:event_txtNameAddKeyReleased

    private void txtAlamatAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlamatAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtPhoneAdd.setText(null);
            txtPhoneAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtAlamatAdd.setText("");
        }
    }//GEN-LAST:event_txtAlamatAddKeyPressed

    private void txtPhoneAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtUserLevelAdd.setText(null);
            txtUserLevelAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtPhoneAdd.setText("");
        }
    }//GEN-LAST:event_txtPhoneAddKeyPressed

    private void txtUserLevelAddFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserLevelAddFocusGained
        SLevelAdd.setVisible(true);
        LevelAdd.setSelectedIndex(0);
        LevelAdd.requestFocus();
    }//GEN-LAST:event_txtUserLevelAddFocusGained

    private void PasswordFieldAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordFieldAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            RePasswordFieldAdd.setText(null);
            RePasswordFieldAdd.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            PasswordFieldAdd.setText("");
        }
    }//GEN-LAST:event_PasswordFieldAddKeyPressed

    private void RePasswordFieldAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RePasswordFieldAddKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Simpan();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            RePasswordFieldAdd.setText(null);
        }
    }//GEN-LAST:event_RePasswordFieldAddKeyPressed

    private void LevelEditFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_LevelEditFocusLost
        SLevelEdit.setVisible(false);
    }//GEN-LAST:event_LevelEditFocusLost

    private void LevelEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LevelEditMouseClicked
        PilihLevelEdit();
    }//GEN-LAST:event_LevelEditMouseClicked

    private void LevelEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LevelEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            PilihLevelEdit();
        }
    }//GEN-LAST:event_LevelEditKeyPressed

    private void txtNameEditFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameEditFocusLost
        SListEdit.setVisible(false);
    }//GEN-LAST:event_txtNameEditFocusLost

    private void txtNameEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            //txtAlamat.setText(null);
            txtAlamatEdit.requestFocus();
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

    private void txtAlamatEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlamatEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            //txtPhone.setText(null);
            txtPhoneEdit.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtAlamatEdit.setText("");
        }
    }//GEN-LAST:event_txtAlamatEditKeyPressed

    private void txtPhoneEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            //txtUserLevel.setText(null);
            txtLevelEdit.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtPhoneEdit.setText("");
        }
    }//GEN-LAST:event_txtPhoneEditKeyPressed

    private void txtLevelEditFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLevelEditFocusGained
        SLevelEdit.setVisible(true);
        LevelEdit.setSelectedIndex(0);
        LevelEdit.requestFocus();
    }//GEN-LAST:event_txtLevelEditFocusGained

    private void PasswordFieldEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordFieldEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            //RePasswordField.setText(null);
            RePasswordFieldEdit.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            PasswordFieldEdit.setText("");
        }
    }//GEN-LAST:event_PasswordFieldEditKeyPressed

    private void RePasswordFieldEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RePasswordFieldEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Update();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            RePasswordFieldEdit.setText(null);
        }
    }//GEN-LAST:event_RePasswordFieldEditKeyPressed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        Tambah();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        KeluarAdd();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        Simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
        Bersih();
    }//GEN-LAST:event_btnBersihActionPerformed

    private void btnCloseEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseEditActionPerformed
        KeluarEdit();
    }//GEN-LAST:event_btnCloseEditActionPerformed

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




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> LevelAdd;
    private javax.swing.JList<String> LevelEdit;
    private javax.swing.JTable ListAdd;
    private javax.swing.JTable ListEdit;
    private javax.swing.JPasswordField PasswordFieldAdd;
    private javax.swing.JPasswordField PasswordFieldEdit;
    private javax.swing.JPasswordField RePasswordFieldAdd;
    private javax.swing.JPasswordField RePasswordFieldEdit;
    private javax.swing.JScrollPane SLevelAdd;
    private javax.swing.JScrollPane SLevelEdit;
    private javax.swing.JScrollPane SListAdd;
    private javax.swing.JScrollPane SListEdit;
    private javax.swing.JScrollPane SPtableTampil;
    private javax.swing.JDialog UserAdd;
    private javax.swing.JDialog UserEdit;
    private Utility.UButton btnBersih;
    private Utility.UButton btnClose;
    private Utility.UButton btnCloseEdit;
    private Utility.UButton btnEdit;
    private Utility.UButton btnHapus;
    private Utility.UButton btnSimpan;
    private Utility.UButton btnTambah;
    private Utility.UButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPanel panelEH;
    private javax.swing.JTable tableTampil;
    private javax.swing.JTextField txtAlamatAdd;
    private javax.swing.JTextField txtAlamatEdit;
    private javax.swing.JTextField txtLevelEdit;
    private javax.swing.JTextField txtNameAdd;
    private javax.swing.JTextField txtNameEdit;
    private javax.swing.JTextField txtPhoneAdd;
    private javax.swing.JTextField txtPhoneEdit;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUserLevelAdd;
    private Utility.UPanelRoundrect uPanelRoundrect1;
    private Utility.UPanelRoundrect uPanelRoundrect2;
    private Utility.UPanelRoundrect uPanelRoundrect3;
    private Utility.UPanelRoundrect uPanelRoundrect4;
    // End of variables declaration//GEN-END:variables


}
