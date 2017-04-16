package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JPanel;

import core.taskables.Mandelbrot;
import window.Content;

public class Intro extends Content {
    static final long serialVersionUID = 0;
    private JPanel blackbar = new JPanel();
    private Fractal fractalPane = new Fractal(Mandelbrot.class, new Point2D.Double(0, 0), new Point2D.Double(4, 4));
    private JButton skipBtn = new JButton("Skip");

    @Override
    protected void create() {
        windowFrame.setMinimumSize(new Dimension(960, 560));
        setLayout(null);
        addComponentListener(new resizeListener());

        blackbar.setBackground(Color.black);
        blackbar.setLayout(null);

        skipBtn.setSize(60, 25);
        skipBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                done();
            }
        });

        add(fractalPane);
        add(blackbar);

        blackbar.add(skipBtn);
        setComponentZOrder(blackbar, 0);

    }

    public void done() {
        windowFrame.setMinimumSize(null);
        windowFrame.setContent(new Home());

        try {
            this.finalize();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    class resizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            fractalPane.setSize(getWidth(), getHeight() - 35);
            skipBtn.setLocation(getWidth() - 65, 5);
            blackbar.setLocation(0, getHeight() - 35);
            blackbar.setSize(getWidth(), 35);
            windowFrame.redraw();
        }
    }

}
