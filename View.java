import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class View {
    public void showMessageDialog(JFrame frame, String text) {
        JOptionPane.showMessageDialog(frame, text);
    }

    public void println(String s) {
        System.out.println(s);
    }

    public void writeImage(BufferedImage image, String extension, File file) throws IOException {
        ImageIO.write(image, extension, file);
    }

    public void fillRect(Graphics2D d, int x, int y, int width, int height) {
        d.fillRect(x, y, width, height);
    }

    public void drawImage(Graphics g, BufferedImage myPicture, int x, int y, Model.MyPanel jpanel) {
        g.drawImage(myPicture, x, y, jpanel);
    }

    public void fillOval(Graphics g, int x, int y, int width, int height) {
        g.fillOval(x,y, width, height);
    }
}
