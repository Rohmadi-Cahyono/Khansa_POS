package khansapos;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import net.proteanit.sql.DbUtils;

public class ItemForm extends javax.swing.JInternalFrame {
    private int X,Y;
    private static String  itemId;
    
    public ItemForm() {
        initComponents();
        IframeBorderLess(); 
        SPtableTampil.getViewport().setBackground(new Color(255,255,255));
        TampilBarang(); 
    }
   
    private void IframeBorderLess() {
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null); 
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
   
    private void TampilBarang() {      
        try {
            java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery("SELECT item_id,item_code,item_name, "
                    + "item_stock, item_unit,item_sprice,item_bprice, item_discount,item_created, item_update FROM items ");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs));            
            TampilkanDiTabel();         
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void SearchFocus(){
        txtSearch.setText(null);
        txtSearch.requestFocus();
    }

    private void TambahBarang(){
        ItemFormAdd ifa = new ItemFormAdd();
        this.getParent().add(ifa);
        ifa.setVisible(true);
        this.setVisible(false);        
    }
    
    public void EditBarang(){
 /*       BarangFormEdit ufe = new BarangFormEdit();
        this.getParent().add(ufe);
        ufe.setVisible(true);       
        this.setVisible(false);      */
    }
   
    private void HapusBarang(){       
        if (JOptionPane.showConfirmDialog(null, "Yakin data Barang akan dihapus?", "Khansa POS",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {            
            try {
                    java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
                    java.sql.Statement st = con.createStatement();           
                    st.executeUpdate("DELETE FROM items WHERE item_id="+itemId);            
                    JOptionPane.showMessageDialog(null, "Data Barang berhasil dihapus!");
                    TampilBarang();
            } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
            }  
        }        
    }
    
     private void Cari(){
        try {
            java.sql.Connection con =  new Utility_KoneksiDB().koneksi();
            java.sql.Statement st = con.createStatement();           
            java.sql.ResultSet rs = st.executeQuery("SELECT item_id,item_code,item_name, "
                    + "item_stock, item_unit,item_sprice,item_bprice, item_discount,item_created, item_update FROM items"
                    + " WHERE item_name LIKE '%"+txtSearch.getText()+"%'");
            tableTampil.setModel(DbUtils.resultSetToTableModel(rs)); 
            TampilkanDiTabel();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
     
     private void TampilkanDiTabel() {                     
            Utility_Table ut = new Utility_Table();         
          
            ut.Header(tableTampil,0,"",-10);
            ut.Header(tableTampil,1,"Kode",120);
            ut.Header(tableTampil,2,"Nama Barang",400);
            ut.Header(tableTampil,3,"Stok",50); 
            ut.Header(tableTampil,4,"Satuan",50);
            ut.Header(tableTampil,5,"Harga Jual",100);
            ut.Header(tableTampil,6,"Harga Beli",100);
            ut.Header(tableTampil,7,"Discount",50);
            ut.Header(tableTampil,8,"Date Created",100);
            ut.Header(tableTampil,9,"Date Update",100);
             
            tableTampil.getColumnModel().getColumn(3).setCellRenderer(ut.formatAngka);
            tableTampil.getColumnModel().getColumn(5).setCellRenderer(ut.formatAngka);
            tableTampil.getColumnModel().getColumn(6).setCellRenderer(ut.formatAngka);
            tableTampil.getColumnModel().getColumn(7).setCellRenderer(ut.formatAngka);
            tableTampil.getColumnModel().getColumn(8).setCellRenderer(ut.formatTanggal);
            tableTampil.getColumnModel().getColumn(9).setCellRenderer(ut.formatTanggal);
            //tableBarang.getColumnModel().getColumn(2).setCellRenderer(new Utility_RupiahCellRenderer());
            tableTampil.removeColumn(tableTampil.getColumnModel().getColumn(0)); //tidak menampilkan kolom (index:0)
    }

    public static void setId(String Id){
        itemId=Id;        
    }    
    public static String getId(){
        return itemId;
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
        panelEH = new javax.swing.JPanel();
        btnEdit = new javax.swing.JLabel();
        btnHapus = new javax.swing.JLabel();
        SPtableTampil = new javax.swing.JScrollPane();
        tableTampil = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txtSearch = new javax.swing.JTextField();
        btnTambah = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1246, 714));

        jPanel1.setBackground(new java.awt.Color(248, 251, 251));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelEH.setOpaque(false);
        panelEH.setPreferredSize(new java.awt.Dimension(130, 30));

        btnEdit.setBackground(new java.awt.Color(0, 123, 255));
        btnEdit.setDisplayedMnemonic('h');
        btnEdit.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEdit.setText("Edit");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEdit.setOpaque(true);
        btnEdit.setPreferredSize(new java.awt.Dimension(75, 25));
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditMouseExited(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(220, 53, 69));
        btnHapus.setDisplayedMnemonic('h');
        btnHapus.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnHapus.setText("Hapus");
        btnHapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHapus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHapus.setOpaque(true);
        btnHapus.setPreferredSize(new java.awt.Dimension(75, 25));
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHapusMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHapusMouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelEHLayout = new javax.swing.GroupLayout(panelEH);
        panelEH.setLayout(panelEHLayout);
        panelEHLayout.setHorizontalGroup(
            panelEHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEHLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelEHLayout.setVerticalGroup(
            panelEHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEHLayout.createSequentialGroup()
                .addGroup(panelEHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(panelEH, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 130, 0));

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

        btnTambah.setBackground(new java.awt.Color(87, 176, 86));
        btnTambah.setDisplayedMnemonic('a');
        btnTambah.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnTambah.setText("Tambah Barang");
        btnTambah.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambah.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTambah.setOpaque(true);
        btnTambah.setPreferredSize(new java.awt.Dimension(75, 25));
        btnTambah.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnTambahFocusLost(evt);
            }
        });
        btnTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTambahMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTambahMouseExited(evt);
            }
        });
        jPanel1.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 130, 40));

        jSeparator1.setForeground(new java.awt.Color(78, 115, 223));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 270, 10));

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(78, 115, 223));
        jLabel1.setText("Master Barang");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 300, 40));

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

        setBounds(0, 0, 1246, 714);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        panelEH.setSize(130, 0);
        EditBarang();
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseEntered
        btnEdit.setBackground(new Color(26,149,255));
        btnEdit.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_btnEditMouseEntered

    private void btnEditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseExited
        btnEdit.setBackground(new Color(0,123,255));
        btnEdit.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnEditMouseExited

    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked
        panelEH.setSize(130, 0);
        HapusBarang();
    }//GEN-LAST:event_btnHapusMouseClicked

    private void btnHapusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseEntered
        btnHapus.setBackground(new Color(246,79,95));
        btnHapus.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_btnHapusMouseEntered

    private void btnHapusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseExited
        btnHapus.setBackground(new Color(220,53,69));
        btnHapus.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_btnHapusMouseExited

    private void tableTampilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTampilMouseClicked
        String  Id= tableTampil.getModel().getValueAt(tableTampil.getSelectedRow(), 0).toString(); //Ambil nilai kolom (0) dan masukkan ke variabel Id
        setId(Id); // Kirim Id ke session setId()

        X= evt.getXOnScreen()-70;
        Y= evt.getYOnScreen()-60;
        panelEH.setLocation(X, Y);
        panelEH.setSize(130, 30);
    }//GEN-LAST:event_tableTampilMouseClicked

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){

        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtSearch.setText("");
            panelEH.setSize(130, 0);
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        Cari();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnTambahFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnTambahFocusLost
        TambahBarang();
    }//GEN-LAST:event_btnTambahFocusLost

    private void btnTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseClicked
        TambahBarang();
    }//GEN-LAST:event_btnTambahMouseClicked

    private void btnTambahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseEntered
        btnTambah.setForeground(new Color(0,0,0));
        btnTambah.setBackground(new Color(113,202,112));
    }//GEN-LAST:event_btnTambahMouseEntered

    private void btnTambahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseExited
        btnTambah.setForeground(new Color(255,255,255));
        btnTambah.setBackground(new Color(87,176,86));
    }//GEN-LAST:event_btnTambahMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane SPtableTampil;
    private static javax.swing.JLabel btnEdit;
    private static javax.swing.JLabel btnHapus;
    private static javax.swing.JLabel btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel panelEH;
    private javax.swing.JTable tableTampil;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
