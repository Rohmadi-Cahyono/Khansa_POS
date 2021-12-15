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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Beranda extends javax.swing.JFrame {
    java.sql.Connection con =  new UDbConnection().koneksi();
    public static Integer PW,PH,SW,SH; 
    Purchase PurchaseForm ;
    
    public Beranda() {
        initComponents();
        Icon();
        setUser();   
        setWaktu();
        setJam(); 
        ShortcutMenu();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        SH = screenSize.height;
        SW = screenSize.width;
        
        SwingUtilities.invokeLater(() -> {
           PW=panelUtama.getWidth();
           PH=panelUtama.getHeight();

           PurchaseForm = new Purchase();
        });
        UserAkses();
        
   }
    
    private void UserAkses(){
        String Level=Login.sessionLevel;
        try {
            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery("SELECT id,user_level,form,access FROM user_access "
                    + "WHERE user_level LIKE '"+Level+"' ORDER BY form");                      

            while (rs.next()) {
                Boolean ac;
                ac = rs.getInt("access")==1;
                
                if (null != rs.getString("form"))switch (rs.getString("form")) {
                    case "cb01" -> {
                        pop01.setEnabled(ac);
                       // sm01.setEnabled(ac);
                    }
                    case "cb02" -> {
                        pop02.setEnabled(ac);
                        //sm02.setEnabled(ac);
                    }
                    case "cb03" -> pop03.setEnabled(ac);
                    
                    case "cb04" -> {
                        pop04.setEnabled(ac);
                        //sm04.setEnabled(ac);
                    }
                    case "cb05" -> {
                        pop05.setEnabled(ac);
                        //sm05.setEnabled(ac);
                    }
                    case "cb06" -> pop06.setEnabled(ac);
                    
                    case "cb07" -> {
                        pop07.setEnabled(ac);
                       // sm07.setEnabled(ac);
                    }
                    case "cb08" -> {
                        pop08.setEnabled(ac);
                        //sm08.setEnabled(ac);
                    }
                    case "cb09" -> {
                        pop09.setEnabled(ac);
                        //sm09.setEnabled(ac);
                    }
                    case "cb10" -> {
                        pop10.setEnabled(ac);
                       // sm10.setEnabled(ac);
                    }
                    case "cb11" -> pop11.setEnabled(ac);
                    case "cb12" -> pop12.setEnabled(ac);
                    case "cb13" -> pop13.setEnabled(ac);
                    case "cb14" -> pop14.setEnabled(ac);
                    case "cb15" -> pop15.setEnabled(ac);
                    case "cb16" -> pop16.setEnabled(ac);
                    case "cb17" -> pop17.setEnabled(ac);
                    default -> {
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }        
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
            String userId = Login.sessionId;
            String userName=Login.sessionName;
            String userLevel=Login.sessionLevel;          

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
        pop01 = new javax.swing.JMenuItem();
        pop02 = new javax.swing.JMenuItem();
        pop03 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        pop04 = new javax.swing.JMenuItem();
        pop05 = new javax.swing.JMenuItem();
        pop06 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        pop07 = new javax.swing.JMenuItem();
        pop08 = new javax.swing.JMenuItem();
        pop09 = new javax.swing.JMenuItem();
        pop10 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        pop11 = new javax.swing.JMenuItem();
        pop12 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        pop13 = new javax.swing.JMenu();
        mBarang = new javax.swing.JMenuItem();
        mBarangSatuan = new javax.swing.JMenuItem();
        mBarangKategori = new javax.swing.JMenuItem();
        pop14 = new javax.swing.JMenuItem();
        pop15 = new javax.swing.JMenuItem();
        pop16 = new javax.swing.JMenuItem();
        pop17 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        logOut = new javax.swing.JMenuItem();
        panelSidebar = new javax.swing.JPanel();
        Sm09 = new Utility.UButton();
        Sm01 = new Utility.UButton();
        Sm02 = new Utility.UButton();
        Sm03 = new Utility.UButton();
        Sm04 = new Utility.UButton();
        Sm05 = new Utility.UButton();
        Sm06 = new Utility.UButton();
        Sm07 = new Utility.UButton();
        Sm08 = new Utility.UButton();
        panelHeader = new javax.swing.JPanel();
        lblTanggal = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblJam = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnMenu = new Utility.UButton();
        btnClose = new Utility.UButton();
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

        pop01.setBackground(new java.awt.Color(0, 0, 0));
        pop01.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        pop01.setText("Penjualan");
        pop01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pop01ActionPerformed(evt);
            }
        });
        popUp.add(pop01);

        pop02.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop02.setText("Bayar Penjualan");
        pop02.setToolTipText("");
        popUp.add(pop02);

        pop03.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop03.setText("Retur Penjualan");
        pop03.setToolTipText("");
        popUp.add(pop03);
        popUp.add(jSeparator1);

        pop04.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        pop04.setText("Pembelian");
        pop04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pop04ActionPerformed(evt);
            }
        });
        popUp.add(pop04);

        pop05.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop05.setText("Bayar Pembelian");
        popUp.add(pop05);

        pop06.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop06.setText("Retur Pembelian");
        popUp.add(pop06);
        popUp.add(jSeparator2);

        pop07.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop07.setText("Laporan Pembelian");
        popUp.add(pop07);

        pop08.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop08.setText("Laporan Penjualan");
        popUp.add(pop08);

        pop09.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop09.setText("Laporan Laba Kotor");
        popUp.add(pop09);

        pop10.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop10.setText("Grafik Penjualan");
        popUp.add(pop10);
        popUp.add(jSeparator3);

        pop11.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop11.setText("Stok Barang");
        popUp.add(pop11);

        pop12.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop12.setText("Stok Opname");
        popUp.add(pop12);
        popUp.add(jSeparator5);

        pop13.setText("Barang");
        pop13.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N

        mBarang.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        mBarang.setText("Master Barang");
        mBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBarangActionPerformed(evt);
            }
        });
        pop13.add(mBarang);

        mBarangSatuan.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        mBarangSatuan.setText("Satuan Barang");
        mBarangSatuan.setToolTipText("");
        mBarangSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBarangSatuanActionPerformed(evt);
            }
        });
        pop13.add(mBarangSatuan);

        mBarangKategori.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        mBarangKategori.setText("Kategori Barang");
        mBarangKategori.setToolTipText("");
        mBarangKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBarangKategoriActionPerformed(evt);
            }
        });
        pop13.add(mBarangKategori);

        popUp.add(pop13);

        pop14.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop14.setText("Master Suplier");
        pop14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pop14ActionPerformed(evt);
            }
        });
        popUp.add(pop14);

        pop15.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop15.setText("Master Member");
        pop15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pop15ActionPerformed(evt);
            }
        });
        popUp.add(pop15);

        pop16.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop16.setText("Master User");
        pop16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pop16ActionPerformed(evt);
            }
        });
        popUp.add(pop16);

        pop17.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        pop17.setText("Master Setting");
        pop17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pop17ActionPerformed(evt);
            }
        });
        popUp.add(pop17);
        popUp.add(jSeparator4);

        logOut.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
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

        panelSidebar.setBackground(new java.awt.Color(60, 93, 93));
        panelSidebar.setPreferredSize(new java.awt.Dimension(120, 718));
        panelSidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Sm09.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\sm-09.png")); // NOI18N
        Sm09.setKetebalanBorder(0.8F);
        Sm09.setKetumpulanSudut(25);
        Sm09.setPreferredSize(new java.awt.Dimension(100, 65));
        Sm09.setWarnaBackground(new java.awt.Color(85, 118, 118));
        Sm09.setWarnaBackgroundHover(new java.awt.Color(111, 144, 144));
        Sm09.setWarnaBackgroundPress(new java.awt.Color(124, 157, 157));
        Sm09.setWarnaBorder(new java.awt.Color(235, 154, 35));
        panelSidebar.add(Sm09, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 630, -1, -1));

        Sm01.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\sm-01.png")); // NOI18N
        Sm01.setKetebalanBorder(0.8F);
        Sm01.setKetumpulanSudut(25);
        Sm01.setPreferredSize(new java.awt.Dimension(100, 65));
        Sm01.setWarnaBackground(new java.awt.Color(85, 118, 118));
        Sm01.setWarnaBackgroundHover(new java.awt.Color(111, 144, 144));
        Sm01.setWarnaBackgroundPress(new java.awt.Color(124, 157, 157));
        Sm01.setWarnaBorder(new java.awt.Color(235, 154, 35));
        panelSidebar.add(Sm01, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        Sm02.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\sm-02.png")); // NOI18N
        Sm02.setKetebalanBorder(0.8F);
        Sm02.setKetumpulanSudut(25);
        Sm02.setPreferredSize(new java.awt.Dimension(100, 65));
        Sm02.setWarnaBackground(new java.awt.Color(85, 118, 118));
        Sm02.setWarnaBackgroundHover(new java.awt.Color(111, 144, 144));
        Sm02.setWarnaBackgroundPress(new java.awt.Color(124, 157, 157));
        Sm02.setWarnaBorder(new java.awt.Color(235, 154, 35));
        panelSidebar.add(Sm02, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Sm03.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\sm-03.png")); // NOI18N
        Sm03.setKetebalanBorder(0.8F);
        Sm03.setKetumpulanSudut(25);
        Sm03.setPreferredSize(new java.awt.Dimension(100, 65));
        Sm03.setWarnaBackground(new java.awt.Color(85, 118, 118));
        Sm03.setWarnaBackgroundHover(new java.awt.Color(111, 144, 144));
        Sm03.setWarnaBackgroundPress(new java.awt.Color(124, 157, 157));
        Sm03.setWarnaBorder(new java.awt.Color(235, 154, 35));
        panelSidebar.add(Sm03, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        Sm04.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\sm-04.png")); // NOI18N
        Sm04.setKetebalanBorder(0.8F);
        Sm04.setKetumpulanSudut(25);
        Sm04.setPreferredSize(new java.awt.Dimension(100, 65));
        Sm04.setWarnaBackground(new java.awt.Color(85, 118, 118));
        Sm04.setWarnaBackgroundHover(new java.awt.Color(111, 144, 144));
        Sm04.setWarnaBackgroundPress(new java.awt.Color(124, 157, 157));
        Sm04.setWarnaBorder(new java.awt.Color(235, 154, 35));
        panelSidebar.add(Sm04, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        Sm05.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\sm-05.png")); // NOI18N
        Sm05.setKetebalanBorder(0.8F);
        Sm05.setKetumpulanSudut(25);
        Sm05.setPreferredSize(new java.awt.Dimension(100, 65));
        Sm05.setWarnaBackground(new java.awt.Color(85, 118, 118));
        Sm05.setWarnaBackgroundHover(new java.awt.Color(111, 144, 144));
        Sm05.setWarnaBackgroundPress(new java.awt.Color(124, 157, 157));
        Sm05.setWarnaBorder(new java.awt.Color(235, 154, 35));
        panelSidebar.add(Sm05, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        Sm06.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\sm-06.png")); // NOI18N
        Sm06.setKetebalanBorder(0.8F);
        Sm06.setKetumpulanSudut(25);
        Sm06.setPreferredSize(new java.awt.Dimension(100, 65));
        Sm06.setWarnaBackground(new java.awt.Color(85, 118, 118));
        Sm06.setWarnaBackgroundHover(new java.awt.Color(111, 144, 144));
        Sm06.setWarnaBackgroundPress(new java.awt.Color(124, 157, 157));
        Sm06.setWarnaBorder(new java.awt.Color(235, 154, 35));
        panelSidebar.add(Sm06, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        Sm07.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\sm-07.png")); // NOI18N
        Sm07.setKetebalanBorder(0.8F);
        Sm07.setKetumpulanSudut(25);
        Sm07.setPreferredSize(new java.awt.Dimension(100, 65));
        Sm07.setWarnaBackground(new java.awt.Color(85, 118, 118));
        Sm07.setWarnaBackgroundHover(new java.awt.Color(111, 144, 144));
        Sm07.setWarnaBackgroundPress(new java.awt.Color(124, 157, 157));
        Sm07.setWarnaBorder(new java.awt.Color(235, 154, 35));
        panelSidebar.add(Sm07, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, -1, -1));

        Sm08.setIcon(new javax.swing.ImageIcon("D:\\Java\\Belajar Java\\KhansaPOS\\image\\icon48\\sm-08.png")); // NOI18N
        Sm08.setKetebalanBorder(0.8F);
        Sm08.setKetumpulanSudut(25);
        Sm08.setPreferredSize(new java.awt.Dimension(100, 65));
        Sm08.setWarnaBackground(new java.awt.Color(85, 118, 118));
        Sm08.setWarnaBackgroundHover(new java.awt.Color(111, 144, 144));
        Sm08.setWarnaBackgroundPress(new java.awt.Color(124, 157, 157));
        Sm08.setWarnaBorder(new java.awt.Color(235, 154, 35));
        panelSidebar.add(Sm08, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, -1, -1));

        getContentPane().add(panelSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, -1, -1));

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
        btnMenu.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnMenu.setKetebalanBorder(0.0F);
        btnMenu.setKetumpulanSudut(0);
        btnMenu.setPreferredSize(new java.awt.Dimension(120, 50));
        btnMenu.setWarnaBackground(new java.awt.Color(9, 42, 42));
        btnMenu.setWarnaBackgroundHover(new java.awt.Color(60, 93, 93));
        btnMenu.setWarnaBackgroundPress(new java.awt.Color(9, 42, 42));
        btnMenu.setWarnaBorder(new java.awt.Color(9, 42, 42));
        btnMenu.setWarnaForeground(new java.awt.Color(153, 153, 153));
        btnMenu.setWarnaForegroundHover(new java.awt.Color(255, 255, 255));
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });
        panelHeader.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, -1, -1));

        btnClose.setMnemonic('x');
        btnClose.setKetebalanBorder(0.0F);
        btnClose.setKetumpulanSudut(0);
        btnClose.setLabel("Exit");
        btnClose.setPreferredSize(new java.awt.Dimension(90, 50));
        btnClose.setWarnaBackground(new java.awt.Color(9, 42, 42));
        btnClose.setWarnaBackgroundHover(new java.awt.Color(255, 0, 0));
        btnClose.setWarnaBackgroundPress(new java.awt.Color(255, 51, 51));
        btnClose.setWarnaBorder(new java.awt.Color(9, 42, 42));
        btnClose.setWarnaForeground(new java.awt.Color(153, 153, 153));
        btnClose.setWarnaForegroundHover(new java.awt.Color(255, 255, 255));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        panelHeader.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 0, -1, -1));

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
   

    
    private void logOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutActionPerformed
        Logout();
    }//GEN-LAST:event_logOutActionPerformed

    private void pop16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pop16ActionPerformed
        UserFormShow();
    }//GEN-LAST:event_pop16ActionPerformed

    private void pop15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pop15ActionPerformed
        MemberFormShow();
    }//GEN-LAST:event_pop15ActionPerformed

    private void pop14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pop14ActionPerformed
        SuplierFormShow();
    }//GEN-LAST:event_pop14ActionPerformed

    private void mBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBarangActionPerformed
        ItemFormShow();
    }//GEN-LAST:event_mBarangActionPerformed

    private void mBarangSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBarangSatuanActionPerformed
        ItemUnitShow();
    }//GEN-LAST:event_mBarangSatuanActionPerformed

    private void mBarangKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBarangKategoriActionPerformed
        ItemCategoryShow();
    }//GEN-LAST:event_mBarangKategoriActionPerformed


    private void pop04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pop04ActionPerformed
        RemovePanel();
        PurchaseShow();
    }//GEN-LAST:event_pop04ActionPerformed

    private void pop01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pop01ActionPerformed
      
    }//GEN-LAST:event_pop01ActionPerformed

    private void pop17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pop17ActionPerformed
        SettingFormShow();
    }//GEN-LAST:event_pop17ActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        popUp.show(this, 150,55);
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        Keluar();
    }//GEN-LAST:event_btnCloseActionPerformed
  
    private void SettingFormShow(){
        RemovePanel();  
        Setting SettingForm = new Setting();
        panelUtama.add(SettingForm);
        SettingForm.setVisible(true);  
        SettingForm.Focus();
    }    
      
    private void UserFormShow(){
        RemovePanel();  
        User UserForm = new User();
        panelUtama.add(UserForm);
        UserForm.setVisible(true);  
        UserForm.Focus();
    }
    
    private void MemberFormShow(){
        RemovePanel();
        Member MemberForm = new Member();
        panelUtama.add(MemberForm);
        MemberForm.setVisible(true);
        MemberForm.Focus();
    }
    
    private void SuplierFormShow(){
        RemovePanel(); 
        Suplier SuplierForm = new Suplier();
        panelUtama.add(SuplierForm);
        SuplierForm.setVisible(true);
        SuplierForm.Focus();
    }
    
    private void ItemFormShow(){
        RemovePanel();   
        Item ItemForm = new Item();
        panelUtama.add(ItemForm);
        ItemForm.setVisible(true);
        ItemForm.Focus();
    }
    
    private void ItemUnitShow(){
        RemovePanel(); 
        ItemUnit ItemUnitForm = new ItemUnit();
        panelUtama.add(ItemUnitForm);
        ItemUnitForm.setVisible(true);
        ItemUnitForm.Focus();
    }
      
    private void ItemCategoryShow(){
        RemovePanel();
        ItemCategory ItemCategoryForm = new ItemCategory();
        panelUtama.add(ItemCategoryForm);
        ItemCategoryForm.setVisible(true); 
        ItemCategoryForm.Focus();
    }
    
    private void PurchaseShow(){
        RemovePanel();          
        panelUtama.add(PurchaseForm);
        PurchaseForm.setVisible(true);
        PurchaseForm.Focus();
    }
    
    private void popUpMenu(){
           
    }
    
    public void RemovePanel(){
        closeAllDialogs();
        panelUtama.removeAll();
        panelUtama.updateUI();      
        
    }
    
    private void closeAllDialogs(){
        Window[] windows = getWindows();
            for (Window window : windows){
                if (window instanceof JDialog){
                    window.dispose();
                }
            }
    }
    
    private void Logout(){
          new Login().setVisible(true);
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
    private Utility.UButton Sm01;
    private Utility.UButton Sm02;
    private Utility.UButton Sm03;
    private Utility.UButton Sm04;
    private Utility.UButton Sm05;
    private Utility.UButton Sm06;
    private Utility.UButton Sm07;
    private Utility.UButton Sm08;
    private Utility.UButton Sm09;
    private Utility.UButton btnClose;
    private Utility.UButton btnMenu;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JLabel lblCopyright;
    private javax.swing.JLabel lblJam;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JLabel lblUser;
    private javax.swing.JMenuItem logOut;
    private javax.swing.JMenuItem mBarang;
    private javax.swing.JMenuItem mBarangKategori;
    private javax.swing.JMenuItem mBarangSatuan;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JDesktopPane panelUtama;
    private javax.swing.JMenuItem pop01;
    private javax.swing.JMenuItem pop02;
    private javax.swing.JMenuItem pop03;
    private javax.swing.JMenuItem pop04;
    private javax.swing.JMenuItem pop05;
    private javax.swing.JMenuItem pop06;
    private javax.swing.JMenuItem pop07;
    private javax.swing.JMenuItem pop08;
    private javax.swing.JMenuItem pop09;
    private javax.swing.JMenuItem pop10;
    private javax.swing.JMenuItem pop11;
    private javax.swing.JMenuItem pop12;
    private javax.swing.JMenu pop13;
    private javax.swing.JMenuItem pop14;
    private javax.swing.JMenuItem pop15;
    private javax.swing.JMenuItem pop16;
    private javax.swing.JMenuItem pop17;
    private javax.swing.JPopupMenu popUp;
    // End of variables declaration//GEN-END:variables
}
