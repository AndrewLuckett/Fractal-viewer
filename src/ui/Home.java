package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import core.taskables.BurningShip;
import core.taskables.Julia;
import core.taskables.Mandelbrot;
import window.Content;
import window.WindowFrame;

public class Home extends Content {
    static final long serialVersionUID = 0;

    private JPanel blackbar = new JPanel();
    private Fractal fractalPane;

    WindowFrame dialogPane;

    JButton mandelbrot = new JButton("Mandelbrot");
    JButton julia = new JButton("Julia (mandelbrot)");
    JButton burningship = new JButton("Burning Ship");
    JButton locate = new JButton("Go to");
    JButton save = new JButton("Save image");

    public Home(Fractal fractal) {
        fractalPane = fractal;
        addComponentListener(new resizeListener());
    }

    public Home() {
        this(new Fractal(Mandelbrot.class, new Point2D.Double(0, 0), new Point2D.Double(4, 4)));

    }

    private void enableallbuttons() {
        julia.setEnabled(true);
        mandelbrot.setEnabled(true);
        burningship.setEnabled(true);
    }

    private void closedialog() {
        if (dialogPane != null) {
            dialogPane.dispose();
        }
    }

    @Override
    protected void create() {
        setLayout(null);
        windowFrame.setMinimumSize(new Dimension(600, 600));

        fractalPane.setLocation(0, 0);

        blackbar.setBackground(Color.black);

        mandelbrot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closedialog();
                remove(fractalPane);
                fractalPane = new Fractal(Mandelbrot.class, new Point2D.Double(0, 0), new Point2D.Double(4, 4));
                add(fractalPane);
                enableallbuttons();
                mandelbrot.setEnabled(false);
            }
        });

        julia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startjulia();
            }
        });

        burningship.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closedialog();
                remove(fractalPane);
                fractalPane = new Fractal(BurningShip.class, new Point2D.Double(0, 0), new Point2D.Double(4, 4));
                add(fractalPane);
                enableallbuttons();
                burningship.setEnabled(false);
            }
        });

        locate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closedialog();
                dialogPane = new WindowFrame(new GoToDialog(fractalPane), new Dimension(400, 200), "Specified look at");
                dialogPane.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dialogPane.setVisible(true);
            }
        });

        save.addActionListener(new ActionListener() { // TODO
            @Override
            public void actionPerformed(ActionEvent e) {
                closedialog();
                BufferedImage bi = new BufferedImage(fractalPane.getWidth(), fractalPane.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = bi.createGraphics();

                fractalPane.paint(g);

                JFileChooser fc = new JFileChooser();

                int returnVal = fc.showSaveDialog(Home.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();

                    if (getExtension(file) == null) {
                        file = new File(file.getAbsolutePath() + ".png");
                    }

                    try {

                        ImageIO.write(bi, "png", file);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }
            }
        });

        blackbar.add(mandelbrot);
        blackbar.add(julia);
        blackbar.add(burningship);
        blackbar.add(locate);
        blackbar.add(save);

        add(blackbar);
        add(fractalPane);

    }

    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public void startjulia() {
        closedialog();
        dialogPane = new WindowFrame(new JuliaDialog(this), new Dimension(400, 200), "Set Julia coords");
        dialogPane.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialogPane.setVisible(true);

        enableallbuttons();
        // julia.setEnabled(false);
    }

    public void dojulia() {
        remove(fractalPane);
        fractalPane = new Fractal(Julia.class, new Point2D.Double(0, 0), new Point2D.Double(4, 4));
        add(fractalPane);
    }

    class resizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            fractalPane.setSize(getWidth(), getHeight() - 35);
            blackbar.setLocation(0, getHeight() - 35);
            blackbar.setSize(getWidth(), 35);
        }
    }

}
