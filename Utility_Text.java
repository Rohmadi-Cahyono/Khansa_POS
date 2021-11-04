package khansapos;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public  class Utility_Text extends DocumentFilter {
        //Merubah Format ke Huruf Besar------------------------------------------------------------------------------------------------------
        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr)
            throws BadLocationException {
            fb.insertString(offset, text.toUpperCase(), attr);
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
            fb.replace(offset, length, text.toUpperCase(), attrs);
        }
        
        //Merubah Format Angka menjadi Rupiah---------------------------------------------------------------------------------------------
        public void AngkaToRp(JTextField JTF,String ISI){
            Locale localeID = new Locale("in", "ID");
            String pattern = "###,###.###";
            NumberFormat nf = NumberFormat.getNumberInstance(localeID);
            DecimalFormat df = (DecimalFormat)nf;
            df.applyPattern(pattern);
            String formattedText = df.format(Double.parseDouble(ISI));        
            JTF.setText(formattedText);      
        } 
}   



