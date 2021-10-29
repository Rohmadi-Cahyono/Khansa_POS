package khansapos;
/*----------------------------------------------------------------------------------------------------------------------------------------
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class koneksi {
    public static Connection dataBase;    
    public static Connection dBase()throws SQLException{
        try {
            String url="jdbc:mysql://192.168.100.100:3306/khansa_pos";
            String user="clien";
            String pass=""; 
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            dataBase=DriverManager.getConnection(url, user, pass);            
        } catch (SQLException e) {
             //JOptionPane.showMessageDialog(null,"Tidak Connect ke Database");
             JOptionPane.showMessageDialog(null,"Tidak Connect ke Database","Connection Error",
             JOptionPane.INFORMATION_MESSAGE);
        }
        return dataBase;
    }    
}
----------------------------------------------------------------------------------------------------------------------------------------------*/
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Utility_KoneksiDB {

    public String port;
    public String namaserver;
    public String namadb;
    public String user;
    public String pswd;
    public java.sql.Connection conn = null;
    Properties prop;

    @SuppressWarnings("FinallyDiscardsException")
    public Connection koneksi() {
        prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("SettingDB.xml"));
            namaserver = prop.getProperty("HOST");
            port = prop.getProperty("PORT");
            user = prop.getProperty("USER");
            pswd = prop.getProperty("PASS");
            namadb = prop.getProperty("DB");

            String DriverDB = "jdbc:mysql://";
            String server = namaserver + ":" + port + "/";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DriverDB + server + namadb, user, pswd);
            if (conn == null) {
                throw new SQLException();
            }
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(null,"Tidak Connect ke Database","Connection Error",JOptionPane.INFORMATION_MESSAGE);
        } catch (ClassNotFoundException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Kesalahan berikut terjadi: " + e.getMessage());
        } finally {
            return conn;
        }
    }
}
