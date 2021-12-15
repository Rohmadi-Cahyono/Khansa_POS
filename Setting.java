package khansapos;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class Setting extends javax.swing.JInternalFrame {
    java.sql.Connection con =  new UDbConnection().koneksi();
    private static String  cbForm;
    private static Object level;
    int checkBox;
    
    public Setting() {
        initComponents();
        IframeBorderLess();
        Tengah();
        Tampil();
   }

    public void Focus(){
       cbAkses.requestFocus();
   }
    
    private void IframeBorderLess() {
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null); 
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setSize(700,400);
    }
       
    private void Tengah(){
        Dimension formIni = this.getSize();
        this.setLocation(( Beranda.PW-formIni.width )/2,(Beranda.PH-formIni.height )/2);
    }
     
    private void Tampil() {   
        
        try {
            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery("SELECT id,user_level,form,access FROM user_access "
                    + "WHERE user_level LIKE '"+cbAkses.getSelectedItem()+"' ORDER BY form");                      

            while (rs.next()) {
                Boolean ac;
                ac = rs.getInt("access")==1;
                
                if (null != rs.getString("form"))switch (rs.getString("form")) {
                    case "cb01" -> cb01.setSelected(ac);
                    case "cb02" -> cb02.setSelected(ac);
                    case "cb03" -> cb03.setSelected(ac);
                    case "cb04" -> cb04.setSelected(ac);
                    case "cb05" -> cb05.setSelected(ac);
                    case "cb06" -> cb06.setSelected(ac);
                    case "cb07" -> cb07.setSelected(ac);
                    case "cb08" -> cb08.setSelected(ac);
                    case "cb09" -> cb09.setSelected(ac);
                    case "cb10" -> cb10.setSelected(ac);
                    case "cb11" -> cb11.setSelected(ac);
                    case "cb12" -> cb12.setSelected(ac);
                    case "cb13" -> cb13.setSelected(ac);
                    case "cb14" -> cb14.setSelected(ac);
                    case "cb15" -> cb15.setSelected(ac);
                    case "cb16" -> cb16.setSelected(ac);
                    case "cb17" -> cb17.setSelected(ac);
                    default -> {
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void Simpan(){     
        level=cbAkses.getSelectedItem();
        
        for (int i = 0; i <17; i++) {
            if (i==0){
                cbForm="cb01";
                checkBox = cb01.isSelected() ? 1:0;
            }else if (i==1) {
                cbForm="cb02";
                checkBox = cb02.isSelected() ? 1:0;
            }else if (i==2) {
                cbForm="cb03";
                checkBox = cb03.isSelected() ? 1:0;
            }else if (i==3) {
                cbForm="cb04";
                checkBox = cb04.isSelected() ? 1:0;
            }else if (i==4) {
                cbForm="cb05";
                checkBox = cb05.isSelected() ? 1:0;
            }else if (i==5) {
                cbForm="cb06";
                checkBox = cb06.isSelected() ? 1:0;
            }else if (i==6) {
                cbForm="cb07";
                checkBox = cb07.isSelected() ? 1:0;
            }else if (i==7) {
                cbForm="cb08";
                checkBox = cb08.isSelected() ? 1:0;
            }else if (i==8) {
                cbForm="cb09";
                checkBox = cb09.isSelected() ? 1:0;
            }else if (i==9) {
                cbForm="cb10";
                checkBox = cb10.isSelected() ? 1:0;
            }else if (i==10) {
                cbForm="cb11";
                checkBox = cb11.isSelected() ? 1:0;
            }else if (i==11) {
                cbForm="cb12";
                checkBox = cb12.isSelected() ? 1:0;
            }else if (i==12) {
                cbForm="cb13";
                checkBox = cb13.isSelected() ? 1:0;
            }else if (i==13) {
                cbForm="cb14";                
                checkBox = cb14.isSelected() ? 1:0;
            }else if (i==14) {
                cbForm="cb15";
                checkBox = cb15.isSelected() ? 1:0;
            }else if (i==15) {
                cbForm="cb16";
                checkBox = cb16.isSelected() ? 1:0;
            }else if (i==16) {
                cbForm="cb17";
                checkBox = cb17.isSelected() ? 1:0;
            }      
            
              try{   
                        String sql ="UPDATE user_access SET access = '"+checkBox+"' "
                                + "WHERE user_level = '"+level+"' && form ='"+cbForm+"'  "; 
                        java.sql.PreparedStatement pst=con.prepareStatement(sql);
                        pst.execute();
                    
                
                    } catch(HeadlessException | SQLException b){
                        JOptionPane.showMessageDialog(null, b.getMessage());
                    }
        }
        JOptionPane.showMessageDialog(null, "Penyimpanan Data Setting Form Berhasil");
        dispose();          
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        uPanelRoundrect1 = new Utility.UPanelRoundrect();
        uPanelRoundrect2 = new Utility.UPanelRoundrect();
        cb01 = new javax.swing.JCheckBox();
        cb02 = new javax.swing.JCheckBox();
        cb03 = new javax.swing.JCheckBox();
        cb04 = new javax.swing.JCheckBox();
        cb05 = new javax.swing.JCheckBox();
        cb06 = new javax.swing.JCheckBox();
        cb08 = new javax.swing.JCheckBox();
        cb07 = new javax.swing.JCheckBox();
        cb09 = new javax.swing.JCheckBox();
        cb10 = new javax.swing.JCheckBox();
        cb11 = new javax.swing.JCheckBox();
        cb12 = new javax.swing.JCheckBox();
        cb13 = new javax.swing.JCheckBox();
        cb14 = new javax.swing.JCheckBox();
        cb15 = new javax.swing.JCheckBox();
        cb16 = new javax.swing.JCheckBox();
        cb17 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbAkses = new javax.swing.JComboBox<>();
        btnClose = new Utility.UButton();
        btnSimpan = new Utility.UButton();
        jPanel1 = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(700, 400));
        setMinimumSize(new java.awt.Dimension(700, 400));
        setPreferredSize(new java.awt.Dimension(700, 400));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uPanelRoundrect1.setKetebalanBorder(2.5F);
        uPanelRoundrect1.setPreferredSize(new java.awt.Dimension(700, 400));
        uPanelRoundrect1.setWarnaBackground(new java.awt.Color(87, 176, 86));
        uPanelRoundrect1.setWarnaBorder(new java.awt.Color(164, 253, 163));
        uPanelRoundrect1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cb01.setBackground(new java.awt.Color(255, 255, 255));
        cb01.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        cb01.setText("Penjualan");

        cb02.setBackground(new java.awt.Color(255, 255, 255));
        cb02.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cb02.setText("Bayar Penjualan");

        cb03.setBackground(new java.awt.Color(255, 255, 255));
        cb03.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        cb03.setText("Retur Penjualan");

        cb04.setBackground(new java.awt.Color(255, 255, 255));
        cb04.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        cb04.setText("Pembelian");

        cb05.setBackground(new java.awt.Color(255, 255, 255));
        cb05.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cb05.setText("Bayar Pembelian");

        cb06.setBackground(new java.awt.Color(255, 255, 255));
        cb06.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cb06.setText("Retur Pembelian");

        cb08.setBackground(new java.awt.Color(255, 255, 255));
        cb08.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        cb08.setText("Laporan Penjualan");

        cb07.setBackground(new java.awt.Color(255, 255, 255));
        cb07.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cb07.setText("Laporan Pembelian");

        cb09.setBackground(new java.awt.Color(255, 255, 255));
        cb09.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cb09.setText("Laporan Laba Kotor");

        cb10.setBackground(new java.awt.Color(255, 255, 255));
        cb10.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cb10.setText("Grafik Penjualan");

        cb11.setBackground(new java.awt.Color(255, 255, 255));
        cb11.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        cb11.setText("Stok Barang");

        cb12.setBackground(new java.awt.Color(255, 255, 255));
        cb12.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cb12.setText("Stok Opnam");

        cb13.setBackground(new java.awt.Color(255, 255, 255));
        cb13.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        cb13.setText("Master Barang");

        cb14.setBackground(new java.awt.Color(255, 255, 255));
        cb14.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cb14.setText("Master Suplier");

        cb15.setBackground(new java.awt.Color(255, 255, 255));
        cb15.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cb15.setText("Master Member");

        cb16.setBackground(new java.awt.Color(255, 255, 255));
        cb16.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cb16.setText("Master User");

        cb17.setBackground(new java.awt.Color(255, 255, 255));
        cb17.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        cb17.setText("Master Setting");
        cb17.setEnabled(false);

        javax.swing.GroupLayout uPanelRoundrect2Layout = new javax.swing.GroupLayout(uPanelRoundrect2);
        uPanelRoundrect2.setLayout(uPanelRoundrect2Layout);
        uPanelRoundrect2Layout.setHorizontalGroup(
            uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uPanelRoundrect2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(uPanelRoundrect2Layout.createSequentialGroup()
                        .addGroup(uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb02)
                            .addComponent(cb01))
                        .addGap(93, 93, 93)
                        .addGroup(uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb08)
                            .addComponent(cb07))
                        .addGap(77, 77, 77)
                        .addGroup(uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb14)
                            .addComponent(cb13)))
                    .addGroup(uPanelRoundrect2Layout.createSequentialGroup()
                        .addComponent(cb03)
                        .addGap(93, 93, 93)
                        .addComponent(cb09)
                        .addGap(105, 105, 105)
                        .addComponent(cb15))
                    .addGroup(uPanelRoundrect2Layout.createSequentialGroup()
                        .addGroup(uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb05)
                            .addComponent(cb04)
                            .addComponent(cb06))
                        .addGap(91, 91, 91)
                        .addGroup(uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb10)
                            .addComponent(cb11)
                            .addComponent(cb12))
                        .addGap(131, 131, 131)
                        .addGroup(uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb16)
                            .addComponent(cb17))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        uPanelRoundrect2Layout.setVerticalGroup(
            uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uPanelRoundrect2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb01)
                    .addComponent(cb08)
                    .addComponent(cb13)
                    .addGroup(uPanelRoundrect2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb02)
                            .addComponent(cb07)
                            .addComponent(cb14))))
                .addGap(3, 3, 3)
                .addGroup(uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb03)
                    .addComponent(cb09)
                    .addComponent(cb15))
                .addGap(3, 3, 3)
                .addGroup(uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(uPanelRoundrect2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(uPanelRoundrect2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(cb05))
                            .addComponent(cb04))
                        .addGap(3, 3, 3)
                        .addComponent(cb06))
                    .addGroup(uPanelRoundrect2Layout.createSequentialGroup()
                        .addComponent(cb10)
                        .addGap(23, 23, 23)
                        .addGroup(uPanelRoundrect2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb11)
                            .addGroup(uPanelRoundrect2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(cb12))))
                    .addGroup(uPanelRoundrect2Layout.createSequentialGroup()
                        .addComponent(cb16)
                        .addGap(23, 23, 23)
                        .addComponent(cb17)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        uPanelRoundrect1.add(uPanelRoundrect2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 680, 240));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Setting Akses Form");
        uPanelRoundrect1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(138, 227, 137));
        jLabel2.setText("User Level  :");
        uPanelRoundrect1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 20));

        cbAkses.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        cbAkses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Manager", "Kasir" }));
        cbAkses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAksesActionPerformed(evt);
            }
        });
        uPanelRoundrect1.add(cbAkses, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, -1, -1));

        btnClose.setText("Close");
        btnClose.setWarnaBackgroundHover(new java.awt.Color(255, 0, 51));
        btnClose.setWarnaBorder(new java.awt.Color(138, 227, 137));
        btnClose.setWarnaForeground(new java.awt.Color(138, 227, 137));
        btnClose.setWarnaForegroundHover(new java.awt.Color(255, 255, 255));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        uPanelRoundrect1.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, -1));

        btnSimpan.setMnemonic('s');
        btnSimpan.setText("Simpan Perubahan");
        btnSimpan.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnSimpan.setKetebalanBorder(2.5F);
        btnSimpan.setKetumpulanSudut(35);
        btnSimpan.setPreferredSize(new java.awt.Dimension(170, 38));
        btnSimpan.setWarnaBackgroundHover(new java.awt.Color(138, 227, 137));
        btnSimpan.setWarnaBorder(new java.awt.Color(138, 227, 137));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        uPanelRoundrect1.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 340, -1, -1));

        getContentPane().add(uPanelRoundrect1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(248, 251, 251));
        jPanel1.setBorder(null);
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 400));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setBounds(0, 0, 716, 430);
    }// </editor-fold>//GEN-END:initComponents

    private void cbAksesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAksesActionPerformed
        Tampil();
    }//GEN-LAST:event_cbAksesActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        Simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Utility.UButton btnClose;
    private Utility.UButton btnSimpan;
    private javax.swing.JCheckBox cb01;
    private javax.swing.JCheckBox cb02;
    private javax.swing.JCheckBox cb03;
    private javax.swing.JCheckBox cb04;
    private javax.swing.JCheckBox cb05;
    private javax.swing.JCheckBox cb06;
    private javax.swing.JCheckBox cb07;
    private javax.swing.JCheckBox cb08;
    private javax.swing.JCheckBox cb09;
    private javax.swing.JCheckBox cb10;
    private javax.swing.JCheckBox cb11;
    private javax.swing.JCheckBox cb12;
    private javax.swing.JCheckBox cb13;
    private javax.swing.JCheckBox cb14;
    private javax.swing.JCheckBox cb15;
    private javax.swing.JCheckBox cb16;
    private javax.swing.JCheckBox cb17;
    private javax.swing.JComboBox<String> cbAkses;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private Utility.UPanelRoundrect uPanelRoundrect1;
    private Utility.UPanelRoundrect uPanelRoundrect2;
    // End of variables declaration//GEN-END:variables
}
