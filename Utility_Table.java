package khansapos;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class Utility_Table {

    DecimalFormat desimalFormat;
    
    public Utility_Table(){
    }
    
    TableCellRenderer formatRupiah = new DefaultTableCellRenderer() {        
        @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel component = (JLabel) super.getTableCellRendererComponent(table, value,isSelected, hasFocus, row, column);
            component.setHorizontalAlignment(JLabel.RIGHT);
            
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("id"));
            symbols.setCurrencySymbol("Rp. ");
            desimalFormat = new DecimalFormat("\u00A4##,###.##", symbols);
            component.setText(desimalFormat.format(value));
            return component;
        }          
    };
    
    TableCellRenderer formatAngka = new DefaultTableCellRenderer() {                 
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel component = (JLabel) super.getTableCellRendererComponent(table, value,isSelected, hasFocus, row, column);
                component.setHorizontalAlignment(JLabel.RIGHT);              
                
                Locale localeID = new Locale("in", "ID");
                String pattern = "###,###.###";
                NumberFormat nf = NumberFormat.getNumberInstance(localeID);
                DecimalFormat df = (DecimalFormat)nf;
                df.applyPattern(pattern);
                component.setText (df.format(value));               
                return component;
        }          
    };
    
           
    TableCellRenderer formatTanggal = new DefaultTableCellRenderer() {
        SimpleDateFormat f = new SimpleDateFormat("dd MMMM yyyy");
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
            JLabel component = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            component.setHorizontalAlignment(JLabel.CENTER);
            component.setText(f.format(value));   
            return  component;              
        }
    };
    

TableCellRenderer backColor = new DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (row % 2 == 1) {
                component.setBackground(new Color(255,235,255));
            } else {
                component.setBackground(new Color(255,255,255));
            }       

        return component;
    }
};

    
 
    public void Header(JTable table, int col_index, String col_name,int col_size){ 
        table.getColumnModel().getColumn(col_index).setHeaderValue(col_name);
        table.getColumnModel().getColumn(col_index).setPreferredWidth(col_size);   
    }


}
