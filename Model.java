import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Model {
    private JFrame f;
    private MyPanel jpanel;
    public enum Types{
        H,
        N,
        O,
        C,
        line,
        eraser,
        R
    }
    private Types type;

    private int x0;
    private int y0;

    private int xPrev;
    private int yPrev;

    private boolean pressed;
    private static BufferedImage imag;
    // если мы загружаем картинку
    private boolean loading = false;
    private String fileName;

    public Model(){
    }

    public void setFrame(JFrame frame) {
        this.f = frame;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setImage(BufferedImage image) {
        imag = image;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }

    public void setxPrev(int xPrev) {
        this.xPrev = xPrev;
    }

    public void setyPrev(int yPrev) {
        this.yPrev = yPrev;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public Types getType() {
        return type;
    }

    public JFrame getFrame() {
        return f;
    }
    public MyPanel getJpanel(){ return jpanel; }

    public boolean isPressed() {
        return pressed;
    }

    public BufferedImage getImage() {
        return imag;
    }

    public boolean isLoading() {
        return loading;
    }

    public int getX0() {
        return x0;
    }
    public int getY0() {
        return y0;
    }

    public int getxPrev() {
        return xPrev;
    }

    public int getyPrev() {
        return yPrev;
    }

    public String getFileName() {
        return fileName;
    }

    public void setJpanel(MyPanel jpanel) {
        this.jpanel = jpanel;
    }

    public BufferedImage readImage(File file) throws IOException {
        return ImageIO.read(new File("C:\\Users\\User_23\\IdeaProjects\\Molecule Reductor\\Images\\R2.png"));
    }

    static class MyPanel extends JPanel {
        public MyPanel() {
        }

        public void paintComponent(Graphics g) {
            if (imag == null) {
                imag = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D d2 = (Graphics2D) imag.createGraphics();
                d2.setColor(Color.white);
                d2.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
            super.paintComponent(g);
            g.drawImage(imag, 0, 0, this);
        }
    }
}
