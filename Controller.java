import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class Controller {
    private static Model model;
    private static View view;

    public Controller(Model m, View v) {
        model = m;
        view = v;
    }

    public void createFrame(){
        JFrame f = new JFrame("Конструктор молекулы");
        f.setSize(805,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        f.setJMenuBar(menuBar);
        model.setFrame(f);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        Action loadAction = new AbstractAction("Загрузить")
        {
            public void actionPerformed(ActionEvent event)
            {
                JFileChooser jf = new JFileChooser();
                int  result = jf.showOpenDialog(null);
                if(result==JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        model.setFileName(jf.getSelectedFile().getAbsolutePath());
                        File iF= new File(model.getFileName());
                        jf.addChoosableFileFilter(new TextFileFilter(".png"));
                        jf.addChoosableFileFilter(new TextFileFilter(".jpg"));
                        model.setImage(ImageIO.read(iF));
                        BufferedImage imag = model.getImage();
                        model.setLoading(true);
                        model.getFrame().setSize(imag.getWidth()+40, imag.getWidth()+80);
                        model.getJpanel().setSize(imag.getWidth(), imag.getWidth());
                        model.getJpanel().repaint();
                    } catch (FileNotFoundException ex) {
                        view.showMessageDialog(model.getFrame(), "Такого файла не существует");
                    }
                    catch (IOException ex) {
                        view.showMessageDialog(model.getFrame(), "Исключение ввода-вывода");
                    }
                    catch (Exception ex) {
                        view.println("Ошибка!");
                    }
                }
            }
        };
        JMenuItem loadMenu = new JMenuItem(loadAction);
        fileMenu.add(loadMenu);
        fileMenu.addSeparator();

        Action saveAction = new AbstractAction("Сохранить")
        {
            public void actionPerformed(ActionEvent event)
            {
                try
                {
                    JFileChooser jf = new JFileChooser();
                    TextFileFilter pngFilter = new TextFileFilter(".png");
                    TextFileFilter jpgFilter = new TextFileFilter(".jpg");
                    if(model.getFileName()==null)
                    {
                        jf.addChoosableFileFilter(pngFilter);
                        jf.addChoosableFileFilter(jpgFilter);
                        int  result = jf.showSaveDialog(null);
                        if(result==JFileChooser.APPROVE_OPTION)
                        {
                            model.setFileName(jf.getSelectedFile().getAbsolutePath());
                        }
                    }
                    if(jf.getFileFilter() == pngFilter)
                    {
                        view.writeImage(model.getImage(), "png", new File(model.getFileName()+".png"));
                    }
                    else
                    {
                        view.writeImage(model.getImage(), "jpeg", new File(model.getFileName()+".jpg"));
                    }
                }
                catch(IOException ex)
                {
                    view.showMessageDialog(f, "Ошибка ввода-вывода");
                }
            }
        };
        JMenuItem saveMenu = new JMenuItem(saveAction);
        fileMenu.add(saveMenu);

        Action saveasAction = new AbstractAction("Сохранить как...")
        {
            public void actionPerformed(ActionEvent event)
            {
                try
                {
                    JFileChooser jf= new JFileChooser();
                    TextFileFilter pngFilter = new TextFileFilter(".png");
                    TextFileFilter jpgFilter = new TextFileFilter(".jpg");
                    jf.addChoosableFileFilter(pngFilter);
                    jf.addChoosableFileFilter(jpgFilter);
                    int  result = jf.showSaveDialog(null);
                    if(result==JFileChooser.APPROVE_OPTION)
                    {
                        model.setFileName(jf.getSelectedFile().getAbsolutePath());
                    }
                    if(jf.getFileFilter()==pngFilter)
                    {
                        view.writeImage(model.getImage(), "png", new File(model.getFileName()+".png"));
                    }
                    else
                    {
                        view.writeImage(model.getImage(), "jpeg", new File(model.getFileName()+".jpg"));
                    }
                }
                catch(IOException ex)
                {
                    view.showMessageDialog(f, "Ошибка ввода-вывода");
                }
            }
        };
        JMenuItem saveasMenu = new JMenuItem(saveasAction);
        fileMenu.add(saveasMenu);

        Model.MyPanel jpanel = new Model.MyPanel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jpanel.setBounds(100,0,f.getWidth()-95,f.getHeight());
        jpanel.setPreferredSize(new Dimension(f.getWidth()-100, f.getHeight()));
        jpanel.setBackground(Color.white);
        jpanel.setOpaque(true);
        f.add(jpanel);
        model.setJpanel(jpanel);

        JToolBar toolbar = new JToolBar("Toolbar", JToolBar.VERTICAL);

        JButton hydrogen = makeButton(Model.Types.H, "/images/H.png");
        hydrogen.setToolTipText("hydrogen");
        toolbar.add(hydrogen);

        JButton nitrogen = makeButton(Model.Types.N, "/images/N.png");
        nitrogen.setToolTipText("nitrogen");
        toolbar.add(nitrogen);

        JButton carbon = makeButton(Model.Types.C, "/images/C.png");
        carbon.setToolTipText("carbon");
        toolbar.add(carbon);

        JButton oxygen = makeButton(Model.Types.O, "/images/O.png");
        oxygen.setToolTipText("oxygen");
        toolbar.add(oxygen);

        JButton radical = makeButton(Model.Types.R, "/images/R.png");
        radical.setToolTipText("radical");
        toolbar.add(radical);

        JButton eraser = new JButton();
        eraser.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/images/erase.png"))));
        eraser.addActionListener(new  ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                model.setType(Model.Types.eraser);
            }
        });
        toolbar.add(eraser);


        JButton lineButton = new JButton();
        lineButton.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/images/line.png"))));
        lineButton.addActionListener(new  ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                model.setType(Model.Types.line);
            }
        });
        toolbar.add(lineButton);

        toolbar.setBounds(0, 0, 100, f.getHeight());

        f.add(toolbar);

        jpanel.addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                if (model.isPressed())
                {
                    Graphics g = model.getImage().getGraphics();
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setColor(Color.BLACK);
                    if (model.getType() == Model.Types.eraser) {
                        g2.setStroke(new BasicStroke(10));
                        g2.setColor(Color.WHITE);
                        g2.drawLine(model.getxPrev(), model.getyPrev(), e.getX(), e.getY());
                    }
                    model.setxPrev(e.getX());
                    model.setyPrev(e.getY());
                }
                jpanel.repaint();
            }
        });

        jpanel.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e) {
                model.setX0(e.getX());
                model.setY0(e.getY());
                model.setxPrev(e.getX());
                model.setyPrev(e.getY());
                model.setPressed(true);
            }
            public void mouseReleased(MouseEvent e) {
                Graphics g = model.getImage().getGraphics();
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(Color.BLACK);
                switch (model.getType()) {
                    case eraser -> {
                        g2.setStroke(new BasicStroke(10));
                        g2.setColor(Color.WHITE);
                        g2.drawLine(model.getX0(), model.getY0(), e.getX(), e.getY());
                    }
                    case line -> {
                        g2.setStroke(new BasicStroke(10));
                        g2.drawLine(model.getX0(), model.getY0(), e.getX(), e.getY());
                    }
                }
                model.setX0(0);
                model.setY0(0);
                model.setPressed(false);
                jpanel.repaint();
            }
            public void mouseClicked(MouseEvent e){
                Graphics g = model.getImage().getGraphics();
                Graphics2D g2 = (Graphics2D)g;
                switch(model.getType()){
                    case H -> {
                        g2.setColor(Color.CYAN);
                        view.fillOval(g, e.getX()-17, e.getY()-17, 35, 35);
                    }
                    case N -> {
                        g2.setColor(Color.PINK);
                        view.fillOval(g, e.getX()-25, e.getY()-25, 50, 50);
                    }
                    case C -> {
                        g2.setColor(Color.ORANGE);
                        view.fillOval(g, e.getX()-25, e.getY()-25, 50, 50);
                    }
                    case O -> {
                        g2.setColor(Color.BLUE);
                        view.fillOval(g, e.getX()-37, e.getY()-37, 75, 75);
                    }
                    case R -> {
                        try {
                            BufferedImage myPicture = model.readImage(new File("D:\\Java\\IndividualTask\\src\\images\\R2.png"));
                            view.drawImage(g, myPicture, e.getX()-25, e.getY(), model.getJpanel());
                            model.getJpanel().repaint();
                        } catch (IOException ex) {
                            view.println("Не загржена картинка R");
                        }
                    }
                }
                model.getJpanel().repaint();
            }
        });

        f.addComponentListener(new ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                if(!model.isLoading())
                {
                    jpanel.setSize(f.getWidth()-40, f.getHeight()-80);
                    BufferedImage tempImage = new BufferedImage(jpanel.getWidth(), jpanel.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D d2 = (Graphics2D) tempImage.createGraphics();
                    d2.setColor(Color.white);
                    view.fillRect(d2, 0, 0, jpanel.getWidth(), jpanel.getHeight());
                    tempImage.setData(model.getImage().getRaster());
                    model.setImage(tempImage);
                    jpanel.repaint();
                }
                model.setLoading(false);
            }
        });
        f.setLayout(null);
        f.setVisible(true);
    }
    private JButton makeButton(Model.Types t, String path){
        JButton button = new JButton(path);
        button.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource(path))));
        button.addActionListener(new  ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                model.setType(t);
            }
        });
        return button;
    }
}
