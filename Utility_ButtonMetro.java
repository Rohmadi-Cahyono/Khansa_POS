package khansapos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

public class Utility_ButtonMetro extends JButton {

    private Color MouseHover = new Color(255, 0,0);
    private Color MousePress = new Color(255,153,153);
    private Color WarnaBackground = new Color(85,118,118);
    private boolean enter, press;

    public boolean isEnter() {
        return enter;
    }

    public void setEnter(boolean enter) {
        this.enter = enter;
        repaint();
    }

    public boolean isPress() {
        return press;
    }

    public void setPress(boolean press) {
        this.press = press;
        repaint();
    }

    public Utility_ButtonMetro() {
        setOpaque(false);
        setBorderPainted(true);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.white);
        setFont(getFont().deriveFont(Font.PLAIN));
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setEnter(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setEnter(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                setPress(true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                setPress(false);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gd = (Graphics2D) g.create();
        Color warna = WarnaBackground;
        Shape shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 0, 0);
        if (isEnter()) {
            warna = MouseHover;
            if (isPress()) {
                warna = MousePress;
            }
        }
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.setColor(warna);
        gd.fill(shape);
        gd.dispose();
        super.paintComponent(g);
    }

    public Color getWarnaBackground() {
        return this.WarnaBackground;
    }

    public void setWarnaBackground(Color bgColor) {
        this.WarnaBackground = bgColor;
    }

//Warna mouse hover Button
    public Color getMouseHover() {
        return this.MouseHover;
    }

    public void setMouseHover(Color mousehover) {
        this.MouseHover = mousehover;
    }

//Warna mouse press Button
    public Color getMousePress() {
        return this.MousePress;
    }

    public void setMousePress(Color mousepress) {
        this.MousePress = mousepress;
    }
}

