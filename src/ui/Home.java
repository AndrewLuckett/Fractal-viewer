package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;

import javax.swing.JButton;
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

    @Override
    protected void create() {
        setLayout(null);
        windowFrame.setMinimumSize(new Dimension(600, 600));

        fractalPane.setLocation(0, 0);

        blackbar.setBackground(Color.black);

        mandelbrot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialogPane != null) {
                    dialogPane.dispose();
                }
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
                if (dialogPane != null) {
                    dialogPane.dispose();
                }
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
                if (dialogPane != null) {
                    dialogPane.dispose();
                }
                dialogPane = new WindowFrame(new GoToDialog(fractalPane), new Dimension(400, 200), "Specified look at");
                dialogPane.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dialogPane.setVisible(true);
            }
        });

        save.addActionListener(new ActionListener() { // TODO
            @Override
            public void actionPerformed(ActionEvent e) {

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

    public void startjulia() {
        if (dialogPane != null) {
            dialogPane.dispose();
        }
        dialogPane = new WindowFrame(new JuliaDialog(this), new Dimension(400, 200), "Set Julia coords");
        dialogPane.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialogPane.setVisible(true);

        enableallbuttons();
        julia.setEnabled(false);
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
