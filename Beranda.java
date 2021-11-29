package khansapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Beranda extends javax.swing.JFrame {
    public static Integer PW,PH;
    User UserForm ;
    Member MemberForm ;
    Suplier SuplierForm ;
    Item ItemForm ;
    ItemUnit ItemUnit ;
    ItemCategory ItemCategory ;
    Purchase Purchase ;
    
    public Beranda() {
        initComponents();
        Icon();
        setUser();   
        setWaktu();
        setJam(); 
        ShortcutMenu();
        
        SwingUtilities.invokeLater(() -> {
           PW=panelUtama.getWidth();
           PH=panelUtama.getHeight();
           
           UserForm = new User();
           MemberForm = new Member();
           SuplierForm = new Suplier();
           ItemForm = new Item();
           ItemUnit = new ItemUnit();
           ItemCategory = new ItemCategory();
           Purchase = new Purchase();
        });
        
   }
  
    private void ShortcutMenu() {
        KeyboardFocusManager keyManager;
        keyManager=KeyboardFocusManager.getCurrentKeyboardFocusManager();
        keyManager.addKeyEventDispatcher((KeyEvent e) -> {
            if(e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==27){
                JOptionPane.showMessageDialog(null, "escape"); 
                return true;
            }
            else if (e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==112) {
                
                return true;
            }
            else if (e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==113) {
                
                return true;
            }
            
            else if (e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==114) {
                PurchaseShow();
                return true;
            }
            else if (e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==115) {
                
                return true;
            }
            else if (e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==116) {
                
                return true;
            }
            
            else if (e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==117) {
                
                return true;
            }
            else if (e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==118) {
                
                return true;
            }
            else if (e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==119) {
                
                return true;
            }
            
            else if (e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==123) {
                Logout();
                return true;
            }            
            return false;
        });
}
    private void Icon(){       
        ImageIcon icon = new ImageIcon("image/khansa_pos64.png");
        setIconImage(icon.getImage());        
    }
    
    private void setUser(){      
            String userId = LoginForm.sessionId;
            String userName=LoginForm.sessionName;
            String userLevel=LoginForm.sessionLevel;          

            lblUser.setText(userLevel + ":  " +userName);
                 
    }
       
   private void setWaktu() {
            java.util.Date tglsekarang = new java.util.Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
            String tanggal = sdf.format(tglsekarang);
            SimpleDateFormat fTahun = new SimpleDateFormat(" yyyy", Locale.getDefault());
            int copyrightSymbolCodePoint = 169 ;
            String s = Character.toString( copyrightSymbolCodePoint ) ;
            String copyRight=s +" Copy right " + fTahun.format(tglsekarang) + "  Rohmadi Cahyono";
             lblTanggal.setText(tanggal);
             lblCopyright.setText(copyRight);
    }
  
    private void setJam(){
        ActionListener taskPerformer = (ActionEvent evt) -> {            
                String nol_jam = "", nol_menit = "";
                Calendar calendar = Calendar.getInstance();            
                int nilai_jam = calendar.get(Calendar.HOUR_OF_DAY);
                int nilai_menit = calendar.get(Calendar.MINUTE);
                // int nilai_detik = calendar.get(Calendar.SECOND);
                if(nilai_jam <= 9) nol_jam= "0";
                if(nilai_menit <= 9) nol_menit= "0";
                // if(nilai_detik <= 9) nol_detik= "0";
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                //String detik = nol_detik + Integer.toString(nilai_detik);
                lblJam.setText(jam+":"+menit);
                // lblJam.setText(jam+":"+menit+":"+detik+"");
        };
        new Timer(1000, taskPerformer).start();
    }  




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popUp = new javax.swing.JPopupMenu();
        Penjualan = new javax.swing.JMenuItem();
        pjBayar = new javax.swing.JMenuItem();
        pjRetur = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        Pembelian = new javax.swing.JMenuItem();
        pbBayar = new javax.swing.JMenuItem();
        pbRetur = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        lapPembelian = new javax.swing.JMenuItem();
        lapPenjualan = new javax.swing.JMenuItem();
        lapLaba = new javax.swing.JMenuItem();
        lapGrafik = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        StokBarang = new javax.swing.JMenuItem();
        StokOpnam = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        Barang = new javax.swing.JMenu();
        mBarang = new javax.swing.JMenuItem();
        mBarangSatuan = new javax.swing.JMenuItem();
        mBarangKategori = new javax.swing.JMenuItem();
        mSuplier = new javax.swing.JMenuItem();
        mMember = new javax.swing.JMenuItem();
        mUser = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        logOut = new javax.swing.JMenuItem();
        panelSidebar = new javax.swing.JPanel();
        btnSC1 = new khansapos.Utility_ButtonMetro();
        btnSC2 = new khansapos.Utility_ButtonMetro();
        btnSC3 = new khansapos.Utility_ButtonMetro();
        btnSC4 = new khansapos.Utility_ButtonMetro();
        btnSC5 = new khansapos.Utility_ButtonMetro();
        btnSC6 = new khansapos.Utility_ButtonMetro();
        btnSC7 = new khansapos.Utility_ButtonMetro();
        btnSC8 = new khansapos.Utility_ButtonMetro();
        btnSC9 = new khansapos.Utility_ButtonMetro();
        panelHeader = new javax.swing.JPanel();
        lblTanggal = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblJam = new javax.swing.JLabel();
        btnExit = new khansapos.Utility_ButtonMetro();
        jLabel3 = new javax.swing.JLabel();
        btnMenu = new khansapos.Utility_ButtonMetro();
        panelUtama =  new javax.swing.JDesktopPane() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(248,251,251));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        lblCopyright = new javax.swing.JLabel();

        popUp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Penjualan.setBackground(new java.awt.Color(0, 0, 0));
        Penjualan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Penjualan.setText("Penjualan");
        popUp.add(Penjualan);

        pjBayar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pjBayar.setText("Penjualan Bayar");
        popUp.add(pjBayar);

        pjRetur.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pjRetur.setText("Penjualan Retur");
        popUp.add(pjRetur);
        popUp.add(jSeparator1);

        Pembelian.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Pembelian.setText("Pembelian");
        Pembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PembelianActionPerformed(evt);
            }
        });
        popUp.add(Pembelian);

        pbBayar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pbBayar.setText("Pembelian Bayar");
        popUp.add(pbBayar);

        pbRetur.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pbRetur.setText("Pembelian Retur");
        popUp.add(pbRetur);
        popUp.add(jSeparator2);

        lapPembelian.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lapPembelian.setText("Laporan Pembelian");
        popUp.add(lapPembelian);

        lapPenjualan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lapPenjualan.setText("Laporan Penjualan");
        popUp.add(lapPenjualan);

        lapLaba.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lapLaba.setText("Laporan Laba");
        popUp.add(lapLaba);

        lapGrafik.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lapGrafik.setText("Laporan Grafik");
        popUp.add(lapGrafik);
        popUp.add(jSeparator3);

        StokBarang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        StokBarang.setText("Stok Barang");
        popUp.add(StokBarang);

        StokOpnam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        StokOpnam.setText("Stok Opname");
        popUp.add(StokOpnam);
        popUp.add(jSeparator5);

        Barang.setText("Barang");
        Barang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        mBarang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mBarang.setText("Master Barang");
        mBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBarangActionPerformed(evt);
            }
        });
        Barang.add(mBarang);

        mBarangSatuan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mBarangSatuan.setText("Satuan Barang");
        mBarangSatuan.setToolTipText("");
        mBarangSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBarangSatuanActionPerformed(evt);
            }
        });
        Barang.add(mBarangSatuan);

        mBarangKategori.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mBarangKategori.setText("Kategori Barang");
        mBarangKategori.setToolTipText("");
        mBarangKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBarangKategoriActionPerformed(evt);
            }
        });
        Barang.add(mBarangKategori);

        popUp.add(Barang);

        mSuplier.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mSuplier.setText("Master Suplier");
        mSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSuplierActionPerformed(evt);
            }
        });
        popUp.add(mSuplier);

        mMember.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mMember.setText("Master Member");
        mMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mMemberActionPerformed(evt);
            }
        });
        popUp.add(mMember);

        mUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mUser.setText("Master User");
        mUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mUserActionPerformed(evt);
            }
        });
        popUp.add(mUser);
        popUp.add(jSeparator4);

        logOut.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        logOut.setText("Log Out");
        logOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutActionPerformed(evt);
            }
        });
        popUp.add(logOut);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(1366, 768));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelSidebar.setBackground(new java.awt.Color(0, 123, 255));
        panelSidebar.setPreferredSize(new java.awt.Dimension(120, 718));

        btnSC1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(26, 149, 255)));
        btnSC1.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\006-checkout.png")); // NOI18N
        btnSC1.setMnemonic('1');
        btnSC1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnSC1.setMouseHover(new java.awt.Color(77, 200, 255));
        btnSC1.setMousePress(new java.awt.Color(26, 149, 255));
        btnSC1.setPreferredSize(new java.awt.Dimension(80, 60));
        btnSC1.setPress(true);
        btnSC1.setWarnaBackground(new java.awt.Color(0, 123, 255));
        btnSC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSC1ActionPerformed(evt);
            }
        });

        btnSC2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(26, 149, 255)));
        btnSC2.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\009-delivery.png")); // NOI18N
        btnSC2.setMnemonic('1');
        btnSC2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnSC2.setMouseHover(new java.awt.Color(77, 200, 255));
        btnSC2.setMousePress(new java.awt.Color(26, 149, 255));
        btnSC2.setPreferredSize(new java.awt.Dimension(80, 60));
        btnSC2.setPress(true);
        btnSC2.setWarnaBackground(new java.awt.Color(0, 123, 255));
        btnSC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSC2ActionPerformed(evt);
            }
        });

        btnSC3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(26, 149, 255)));
        btnSC3.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\019-loan.png")); // NOI18N
        btnSC3.setMnemonic('1');
        btnSC3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnSC3.setMouseHover(new java.awt.Color(77, 200, 255));
        btnSC3.setMousePress(new java.awt.Color(26, 149, 255));
        btnSC3.setPreferredSize(new java.awt.Dimension(80, 60));
        btnSC3.setPress(true);
        btnSC3.setWarnaBackground(new java.awt.Color(0, 123, 255));
        btnSC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSC3ActionPerformed(evt);
            }
        });

        btnSC4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(26, 149, 255)));
        btnSC4.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\027-list.png")); // NOI18N
        btnSC4.setMnemonic('1');
        btnSC4.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnSC4.setMouseHover(new java.awt.Color(77, 200, 255));
        btnSC4.setMousePress(new java.awt.Color(26, 149, 255));
        btnSC4.setPreferredSize(new java.awt.Dimension(80, 60));
        btnSC4.setPress(true);
        btnSC4.setWarnaBackground(new java.awt.Color(0, 123, 255));
        btnSC4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSC4ActionPerformed(evt);
            }
        });

        btnSC5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(26, 149, 255)));
        btnSC5.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\026-file.png")); // NOI18N
        btnSC5.setMnemonic('1');
        btnSC5.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnSC5.setMouseHover(new java.awt.Color(77, 200, 255));
        btnSC5.setMousePress(new java.awt.Color(26, 149, 255));
        btnSC5.setPreferredSize(new java.awt.Dimension(80, 60));
        btnSC5.setPress(true);
        btnSC5.setWarnaBackground(new java.awt.Color(0, 123, 255));
        btnSC5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSC5ActionPerformed(evt);
            }
        });

        btnSC6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(26, 149, 255)));
        btnSC6.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\009-time-is-money.png")); // NOI18N
        btnSC6.setMnemonic('1');
        btnSC6.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnSC6.setMouseHover(new java.awt.Color(77, 200, 255));
        btnSC6.setMousePress(new java.awt.Color(26, 149, 255));
        btnSC6.setPreferredSize(new java.awt.Dimension(80, 60));
        btnSC6.setPress(true);
        btnSC6.setWarnaBackground(new java.awt.Color(0, 123, 255));
        btnSC6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSC6ActionPerformed(evt);
            }
        });

        btnSC7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(26, 149, 255)));
        btnSC7.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\011-graph.png")); // NOI18N
        btnSC7.setMnemonic('1');
        btnSC7.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnSC7.setMouseHover(new java.awt.Color(77, 200, 255));
        btnSC7.setMousePress(new java.awt.Color(26, 149, 255));
        btnSC7.setPreferredSize(new java.awt.Dimension(80, 60));
        btnSC7.setPress(true);
        btnSC7.setWarnaBackground(new java.awt.Color(0, 123, 255));
        btnSC7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSC7ActionPerformed(evt);
            }
        });

        btnSC8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(26, 149, 255)));
        btnSC8.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\lock-landscape-48.png")); // NOI18N
        btnSC8.setMnemonic('1');
        btnSC8.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnSC8.setMouseHover(new java.awt.Color(77, 200, 255));
        btnSC8.setMousePress(new java.awt.Color(26, 149, 255));
        btnSC8.setPreferredSize(new java.awt.Dimension(80, 60));
        btnSC8.setPress(true);
        btnSC8.setWarnaBackground(new java.awt.Color(0, 123, 255));
        btnSC8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSC8ActionPerformed(evt);
            }
        });

        btnSC9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(26, 149, 255)));
        btnSC9.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\006-notes.png")); // NOI18N
        btnSC9.setMnemonic('1');
        btnSC9.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnSC9.setMouseHover(new java.awt.Color(77, 200, 255));
        btnSC9.setMousePress(new java.awt.Color(26, 149, 255));
        btnSC9.setPreferredSize(new java.awt.Dimension(80, 60));
        btnSC9.setPress(true);
        btnSC9.setWarnaBackground(new java.awt.Color(0, 123, 255));
        btnSC9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSC9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSidebarLayout = new javax.swing.GroupLayout(panelSidebar);
        panelSidebar.setLayout(panelSidebarLayout);
        panelSidebarLayout.setHorizontalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSidebarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSC8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSC3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSC2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSC1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSC9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSC4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSC5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSC6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSC7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelSidebarLayout.setVerticalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSidebarLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btnSC1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSC9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSC2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSC3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSC4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSC5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSC6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSC7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(btnSC8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        getContentPane().add(panelSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 120, -1));

        panelHeader.setBackground(new java.awt.Color(0, 0, 0));
        panelHeader.setPreferredSize(new java.awt.Dimension(1366, 50));
        panelHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTanggal.setBackground(new java.awt.Color(0, 0, 0));
        lblTanggal.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        lblTanggal.setForeground(new java.awt.Color(255, 255, 255));
        lblTanggal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTanggal.setText("23 September 2021");
        lblTanggal.setOpaque(true);
        panelHeader.add(lblTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 198, 50));

        lblUser.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblUser.setForeground(new java.awt.Color(204, 204, 204));
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUser.setText("Kasir: Cahyono");
        panelHeader.add(lblUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, 214, 50));

        lblJam.setBackground(new java.awt.Color(0, 0, 0));
        lblJam.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        lblJam.setForeground(new java.awt.Color(204, 204, 0));
        lblJam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblJam.setText("12:34");
        lblJam.setOpaque(true);
        panelHeader.add(lblJam, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 0, 67, 50));

        btnExit.setMnemonic('x');
        btnExit.setText("Exit");
        btnExit.setToolTipText("");
        btnExit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnExit.setWarnaBackground(new java.awt.Color(9, 42, 42));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        panelHeader.add(btnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1287, 0, 80, 50));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\logoKhansa.png")); // NOI18N
        jLabel3.setOpaque(true);
        jLabel3.setPreferredSize(new java.awt.Dimension(150, 50));
        panelHeader.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 50));

        btnMenu.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\MenuIcon32.png")); // NOI18N
        btnMenu.setMnemonic('m');
        btnMenu.setText("Menu");
        btnMenu.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnMenu.setMouseHover(new java.awt.Color(85, 118, 118));
        btnMenu.setMousePress(new java.awt.Color(34, 67, 67));
        btnMenu.setWarnaBackground(new java.awt.Color(9, 42, 42));
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });
        panelHeader.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, -1, 50));

        getContentPane().add(panelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        panelUtama.setBackground(new java.awt.Color(248, 251, 251));
        panelUtama.setMinimumSize(new java.awt.Dimension(1246, 698));

        javax.swing.GroupLayout panelUtamaLayout = new javax.swing.GroupLayout(panelUtama);
        panelUtama.setLayout(panelUtamaLayout);
        panelUtamaLayout.setHorizontalGroup(
            panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1246, Short.MAX_VALUE)
        );
        panelUtamaLayout.setVerticalGroup(
            panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 698, Short.MAX_VALUE)
        );

        getContentPane().add(panelUtama, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 1246, 698));

        lblCopyright.setBackground(new java.awt.Color(153, 153, 153));
        lblCopyright.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lblCopyright.setForeground(new java.awt.Color(255, 255, 255));
        lblCopyright.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCopyright.setText(" Copy Right  2021  Rohmadi Cahyono");
        lblCopyright.setMaximumSize(new java.awt.Dimension(1246, 20));
        lblCopyright.setMinimumSize(new java.awt.Dimension(1246, 20));
        lblCopyright.setOpaque(true);
        lblCopyright.setPreferredSize(new java.awt.Dimension(1246, 20));
        getContentPane().add(lblCopyright, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 750, -1, -1));

        setBounds(0, 0, 1367, 764);
    }// </editor-fold>//GEN-END:initComponents
   
    private void btnMenuMouseClicked(java.awt.event.MouseEvent evt) {                                     
        popUp.show(this, 155, 55);
    }  
    
    private void logOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutActionPerformed
        Logout();
    }//GEN-LAST:event_logOutActionPerformed

    private void mUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mUserActionPerformed
        UserFormShow();
    }//GEN-LAST:event_mUserActionPerformed

    private void mMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mMemberActionPerformed
        MemberFormShow();
    }//GEN-LAST:event_mMemberActionPerformed

    private void mSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSuplierActionPerformed
        SuplierFormShow();
    }//GEN-LAST:event_mSuplierActionPerformed

    private void mBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBarangActionPerformed
        ItemFormShow();
    }//GEN-LAST:event_mBarangActionPerformed

    private void mBarangSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBarangSatuanActionPerformed
        ItemUnitShow();
    }//GEN-LAST:event_mBarangSatuanActionPerformed

    private void mBarangKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBarangKategoriActionPerformed
        ItemCategoryShow();
    }//GEN-LAST:event_mBarangKategoriActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        Keluar();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        popUp.show(this, 155, 55); 
        //getSizeJDesktopPane();
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnSc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSc1ActionPerformed

    }//GEN-LAST:event_btnSc1ActionPerformed

    private void btnSc5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSc5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSc5ActionPerformed

    private void btnSc4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSc4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSc4ActionPerformed

    private void btnSc3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSc3ActionPerformed
        PurchaseShow();
    }//GEN-LAST:event_btnSc3ActionPerformed

    private void btnSc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSc2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSc2ActionPerformed

    private void btnSc8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSc8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSc8ActionPerformed

    private void btnScLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScLogOutActionPerformed
        Logout();
    }//GEN-LAST:event_btnScLogOutActionPerformed

    private void btnSc6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSc6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSc6ActionPerformed

    private void btnSc7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSc7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSc7ActionPerformed

    private void PembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PembelianActionPerformed
        PurchaseShow();
    }//GEN-LAST:event_PembelianActionPerformed

    private void btnSC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSC1ActionPerformed
       
    }//GEN-LAST:event_btnSC1ActionPerformed

    private void btnSC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSC2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSC2ActionPerformed

    private void btnSC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSC3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSC3ActionPerformed

    private void btnSC4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSC4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSC4ActionPerformed

    private void btnSC5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSC5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSC5ActionPerformed

    private void btnSC6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSC6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSC6ActionPerformed

    private void btnSC7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSC7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSC7ActionPerformed

    private void btnSC8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSC8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSC8ActionPerformed

    private void btnSC9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSC9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSC9ActionPerformed
  
      
    private void UserFormShow(){
        RemovePanel();  
        panelUtama.add(UserForm);
        UserForm.setVisible(true);  
        UserForm.Focus();
    }
    
    private void MemberFormShow(){
        RemovePanel();           
        panelUtama.add(MemberForm);
        MemberForm.setVisible(true);
        MemberForm.Focus();
    }
    
    private void SuplierFormShow(){
        RemovePanel(); 
        panelUtama.add(SuplierForm);
        SuplierForm.setVisible(true);
        SuplierForm.Focus();
    }
    
    private void ItemFormShow(){
        RemovePanel();   
        panelUtama.add(ItemForm);
        ItemForm.setVisible(true);
        ItemForm.Focus();
    }
    
    private void ItemUnitShow(){
        RemovePanel(); 
        panelUtama.add(ItemUnit);
        ItemUnit.setVisible(true);
        ItemUnit.Focus();
    }
      
    private void ItemCategoryShow(){
        RemovePanel();
        panelUtama.add(ItemCategory);
        ItemCategory.setVisible(true); 
        ItemCategory.Focus();
    }
    
    private void PurchaseShow(){
        RemovePanel();          
        panelUtama.add(Purchase);
        Purchase.setVisible(true);
        Purchase.Focus();
    }
    
    private void popUpMenu(){
           
    }
    
    public void RemovePanel(){
        
        panelUtama.removeAll();
        panelUtama.updateUI();      
    }
    
    private void Logout(){
          new LoginForm().setVisible(true);
         dispose();  
    }
    private void Keluar(){
        System.exit(0);
    }
    
    
    /**
     * @param args the command line arguments
     * @throws javax.swing.UnsupportedLookAndFeelException
     */
    
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        /* Create and display the form */

        java.awt.EventQueue.invokeLater(() -> {            
            new Beranda().setVisible(true);
        });
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Barang;
    private javax.swing.JMenuItem Pembelian;
    private javax.swing.JMenuItem Penjualan;
    private javax.swing.JMenuItem StokBarang;
    private javax.swing.JMenuItem StokOpnam;
    private khansapos.Utility_ButtonMetro btnExit;
    private khansapos.Utility_ButtonMetro btnMenu;
    private khansapos.Utility_ButtonMetro btnSC1;
    private khansapos.Utility_ButtonMetro btnSC2;
    private khansapos.Utility_ButtonMetro btnSC3;
    private khansapos.Utility_ButtonMetro btnSC4;
    private khansapos.Utility_ButtonMetro btnSC5;
    private khansapos.Utility_ButtonMetro btnSC6;
    private khansapos.Utility_ButtonMetro btnSC7;
    private khansapos.Utility_ButtonMetro btnSC8;
    private khansapos.Utility_ButtonMetro btnSC9;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JMenuItem lapGrafik;
    private javax.swing.JMenuItem lapLaba;
    private javax.swing.JMenuItem lapPembelian;
    private javax.swing.JMenuItem lapPenjualan;
    private javax.swing.JLabel lblCopyright;
    private javax.swing.JLabel lblJam;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JLabel lblUser;
    private javax.swing.JMenuItem logOut;
    private javax.swing.JMenuItem mBarang;
    private javax.swing.JMenuItem mBarangKategori;
    private javax.swing.JMenuItem mBarangSatuan;
    private javax.swing.JMenuItem mMember;
    private javax.swing.JMenuItem mSuplier;
    private javax.swing.JMenuItem mUser;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JDesktopPane panelUtama;
    private javax.swing.JMenuItem pbBayar;
    private javax.swing.JMenuItem pbRetur;
    private javax.swing.JMenuItem pjBayar;
    private javax.swing.JMenuItem pjRetur;
    private javax.swing.JPopupMenu popUp;
    // End of variables declaration//GEN-END:variables
}
