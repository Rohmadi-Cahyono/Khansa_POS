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
    java.sql.Connection con =  new UDbConnection().koneksi();
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
            UTable ut = new UTable();         
          
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
    private void Tambah(){
        this.setVisible(false); 
        Bersih(); 
        SwingUtilities.invokeLater(() -> {txtNameAdd.requestFocusInWindow(); });
        SuplierAdd.setSize(1010,325);                     
        SuplierAdd.setLocation(((Beranda.SW+120)-1010 )/2,((Beranda.SH+50)-325 )/2);
        SuplierAdd.setBackground(new Color(0, 0, 0, 0)); 
        SuplierAdd.setVisible(true);
    }     
     
        private void PopUp(){
        try {
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT suplier_id,suplier_name FROM supliers WHERE suplier_name LIKE '"+txtNameAdd.getText()+"%'");
            ListAdd.setModel(DbUtils.resultSetToTableModel(rs));           
            
            if(rs.last()){   
                UTable uts = new UTable();
                uts.Header(ListAdd,0,"",-10);
                uts.Header(ListAdd,1,"",200);
                ListAdd.setBackground(new Color(255,255,255));
                ListAdd.setShowGrid(false);
                ListAdd.removeColumn(ListAdd.getColumnModel().getColumn(0));
                SListAdd.setLocation(txtNameAdd.getX(), txtNameAdd.getY()+20);
                
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
    
    public void Edit(){
        this.setVisible(false);
        TampilEdit();
        SwingUtilities.invokeLater(() -> {txtNameEdit.requestFocusInWindow(); });
        SuplierEdit.setSize(1010, 325);                     
        SuplierEdit.setLocation(((Beranda.SW+120)-1010 )/2,((Beranda.SH+50)-325 )/2);
        SuplierEdit.setBackground(new Color(0, 0, 0, 0)); 
        SuplierEdit.setVisible(true);         
    }
    
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
                UTable uts = new UTable();
                uts.Header(ListEdit,0,"",-10);
                uts.Header(ListEdit,1,"",200);
                ListEdit.setBackground(new Color(255,255,255));
                ListEdit.setShowGrid(false);
                ListEdit.removeColumn(ListEdit.getColumnModel().getColumn(0));
                SListEdit.setLocation(txtNameEdit.getX(), txtNameEdit.getY()+20);
                
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
        uPanelRoundrect2 = new Utility.UPanelRoundrect();
        SListAdd = new javax.swing.JScrollPane();
        ListAdd = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtNameAdd = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txtAddressAdd = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        txtPhoneAdd = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        btnBersih = new Utility.UButton();
        btnSimpan = new Utility.UButton();
        uPanelRoundrect1 = new Utility.UPanelRoundrect();
        jLabel3 = new javax.swing.JLabel();
        btnCloseAdd = new Utility.UButton();
        SuplierEdit = new javax.swing.JDialog();
        uPanelRoundrect3 = new Utility.UPanelRoundrect();
        jLabel7 = new javax.swing.JLabel();
        btnCloseEdit = new Utility.UButton();
        uPanelRoundrect4 = new Utility.UPanelRoundrect();
        SListEdit = new javax.swing.JScrollPane();
        ListEdit = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtNameEdit = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        txtAdressEdit = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        txtPhoneEdit = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        btnUpdate = new Utility.UButton();
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

        SuplierAdd.setUndecorated(true);
        SuplierAdd.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect2.setKetebalanBorder(2.5F);
        uPanelRoundrect2.setPreferredSize(new java.awt.Dimension(1000, 260));
        uPanelRoundrect2.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        uPanelRoundrect2.add(SListAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 65, 330, 0));

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Nama Suplier");
        jLabel4.setToolTipText(null);
        uPanelRoundrect2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, 20));

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
        uPanelRoundrect2.add(txtNameAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 335, -1));

        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 335, 10));

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Alamat ");
        jLabel5.setToolTipText(null);
        uPanelRoundrect2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, 20));

        txtAddressAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtAddressAdd.setToolTipText(null);
        txtAddressAdd.setBorder(null);
        txtAddressAdd.setOpaque(false);
        txtAddressAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAddressAddKeyPressed(evt);
            }
        });
        uPanelRoundrect2.add(txtAddressAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 815, -1));

        jSeparator4.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 815, 10));

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Phone ");
        jLabel6.setToolTipText(null);
        uPanelRoundrect2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, 20));

        txtPhoneAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtPhoneAdd.setToolTipText(null);
        txtPhoneAdd.setBorder(null);
        txtPhoneAdd.setOpaque(false);
        txtPhoneAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneAddKeyPressed(evt);
            }
        });
        uPanelRoundrect2.add(txtPhoneAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 289, -1));

        jSeparator5.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 289, 10));

        btnBersih.setMnemonic('h');
        btnBersih.setText("Bersih");
        btnBersih.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnBersih.setKetebalanBorder(2.0F);
        btnBersih.setKetumpulanSudut(35);
        btnBersih.setPreferredSize(new java.awt.Dimension(120, 38));
        btnBersih.setWarnaBackground(new java.awt.Color(235, 154, 35));
        btnBersih.setWarnaBackgroundHover(new java.awt.Color(255, 231, 112));
        btnBersih.setWarnaBackgroundPress(new java.awt.Color(235, 154, 35));
        btnBersih.setWarnaBorder(new java.awt.Color(255, 231, 112));
        btnBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBersihActionPerformed(evt);
            }
        });
        uPanelRoundrect2.add(btnBersih, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 210, -1, -1));

        btnSimpan.setText("Simpan");
        btnSimpan.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnSimpan.setKetebalanBorder(2.0F);
        btnSimpan.setKetumpulanSudut(35);
        btnSimpan.setPreferredSize(new java.awt.Dimension(180, 38));
        btnSimpan.setWarnaBorder(new java.awt.Color(164, 253, 163));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        uPanelRoundrect2.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 210, -1, -1));

        SuplierAdd.getContentPane().add(uPanelRoundrect2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 60, -1, -1));

        uPanelRoundrect1.setKetebalanBorder(2.5F);
        uPanelRoundrect1.setKetumpulanSudut(10);
        uPanelRoundrect1.setMinimumSize(new java.awt.Dimension(1000, 50));
        uPanelRoundrect1.setPreferredSize(new java.awt.Dimension(1010, 325));
        uPanelRoundrect1.setWarnaBackground(new java.awt.Color(87, 176, 86));
        uPanelRoundrect1.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tambah Member");
        uPanelRoundrect1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 30));

        btnCloseAdd.setMnemonic('c');
        btnCloseAdd.setText("Close");
        btnCloseAdd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnCloseAdd.setKetebalanBorder(0.0F);
        btnCloseAdd.setKetumpulanSudut(35);
        btnCloseAdd.setPreferredSize(new java.awt.Dimension(150, 38));
        btnCloseAdd.setWarnaBackgroundHover(new java.awt.Color(87, 176, 86));
        btnCloseAdd.setWarnaBorder(new java.awt.Color(87, 176, 86));
        btnCloseAdd.setWarnaForegroundHover(new java.awt.Color(255, 0, 0));
        btnCloseAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseAddActionPerformed(evt);
            }
        });
        uPanelRoundrect1.add(btnCloseAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, 70, 30));

        SuplierAdd.getContentPane().add(uPanelRoundrect1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        SuplierEdit.setUndecorated(true);
        SuplierEdit.setPreferredSize(new java.awt.Dimension(1010, 325));
        SuplierEdit.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect3.setKetebalanBorder(2.5F);
        uPanelRoundrect3.setKetumpulanSudut(10);
        uPanelRoundrect3.setMinimumSize(new java.awt.Dimension(1010, 325));
        uPanelRoundrect3.setPreferredSize(new java.awt.Dimension(1010, 325));
        uPanelRoundrect3.setWarnaBackground(new java.awt.Color(235, 154, 35));
        uPanelRoundrect3.setWarnaBorder(new java.awt.Color(255, 231, 112));
        uPanelRoundrect3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Edit Member");
        uPanelRoundrect3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

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
        uPanelRoundrect3.add(btnCloseEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, 70, 20));

        uPanelRoundrect4.setKetebalanBorder(2.5F);
        uPanelRoundrect4.setMinimumSize(new java.awt.Dimension(1000, 260));
        uPanelRoundrect4.setPreferredSize(new java.awt.Dimension(1000, 260));
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

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Nama Suplier");
        jLabel8.setToolTipText(null);
        uPanelRoundrect4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, 20));

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
        uPanelRoundrect4.add(txtNameEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 335, -1));

        jSeparator6.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 335, 10));

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Alamat ");
        jLabel9.setToolTipText(null);
        uPanelRoundrect4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, 20));

        txtAdressEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtAdressEdit.setToolTipText(null);
        txtAdressEdit.setBorder(null);
        txtAdressEdit.setOpaque(false);
        txtAdressEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAdressEditKeyPressed(evt);
            }
        });
        uPanelRoundrect4.add(txtAdressEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 815, -1));

        jSeparator7.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 815, 10));

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Phone ");
        jLabel10.setToolTipText(null);
        uPanelRoundrect4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, 20));

        txtPhoneEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        txtPhoneEdit.setToolTipText(null);
        txtPhoneEdit.setBorder(null);
        txtPhoneEdit.setOpaque(false);
        txtPhoneEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneEditKeyPressed(evt);
            }
        });
        uPanelRoundrect4.add(txtPhoneEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 289, -1));

        jSeparator8.setForeground(new java.awt.Color(204, 204, 204));
        uPanelRoundrect4.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 289, 10));

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
        uPanelRoundrect4.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 210, -1, -1));

        uPanelRoundrect3.add(uPanelRoundrect4, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 60, -1, -1));

        SuplierEdit.getContentPane().add(uPanelRoundrect3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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

        SPtableTampil.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SPtableTampil.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

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

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
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

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 250, 10));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Master Suplier");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 270, 40));

        btnTambah.setMnemonic('t');
        btnTambah.setText("Tambah Suplier");
        btnTambah.setToolTipText("");
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
        panelEH.setSize(172, 30);
    }//GEN-LAST:event_tableTampilMouseClicked

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

    private void btnCloseAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseAddActionPerformed
        KeluarAdd();
    }//GEN-LAST:event_btnCloseAddActionPerformed

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
        Bersih();
    }//GEN-LAST:event_btnBersihActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        Simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

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
    private javax.swing.JTable ListAdd;
    private javax.swing.JTable ListEdit;
    private javax.swing.JScrollPane SListAdd;
    private javax.swing.JScrollPane SListEdit;
    private javax.swing.JScrollPane SPtableTampil;
    private javax.swing.JDialog SuplierAdd;
    private javax.swing.JDialog SuplierEdit;
    private Utility.UButton btnBersih;
    private Utility.UButton btnCloseAdd;
    private Utility.UButton btnCloseEdit;
    private Utility.UButton btnEdit;
    private Utility.UButton btnHapus;
    private Utility.UButton btnSimpan;
    private Utility.UButton btnTambah;
    private Utility.UButton btnUpdate;
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
    private Utility.UPanelRoundrect uPanelRoundrect1;
    private Utility.UPanelRoundrect uPanelRoundrect2;
    private Utility.UPanelRoundrect uPanelRoundrect3;
    private Utility.UPanelRoundrect uPanelRoundrect4;
    // End of variables declaration//GEN-END:variables
}
