package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import core.taskables.Mandelbrot;
import window.Content;

public class Intro extends Content {
    static final long serialVersionUID = 0;

    private Fractal fractalPane = new Fractal(Mandelbrot.class, new Point2D.Double(0, 0), new Point2D.Double(4, 4));

    private JPanel blackbar = new JPanel();
    private JButton skipBtn = new JButton("Skip");

    private JPanel tooltip = new JPanel();
    private JButton nextBtn = new JButton("Next");
    private JLabel tiptext = new JLabel();
    String[] tips = { "one", "two" }; // update
    int currenttip = 0;

    @Override
    protected void create() {

        windowFrame.setMinimumSize(new Dimension(960, 560));
        setLayout(null);
        addComponentListener(new resizeListener());

        tooltip.setLayout(new BorderLayout());
        tooltip.setBackground(Color.black);
        tooltip.setBorder(new EmptyBorder(10, 10, 10, 10));
        tooltip.setSize(100, 200);
        tooltip.setLocation(10, 10);

        tiptext.setForeground(Color.white);
        tiptext.setHorizontalAlignment(SwingConstants.CENTER);
        tooltip.add(tiptext, BorderLayout.CENTER);
        tooltip.add(nextBtn, BorderLayout.SOUTH);

        settiptext();
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                settiptext();
            }
        });

        blackbar.setBackground(Color.black);
        blackbar.setLayout(null);
        blackbar.add(skipBtn);

        skipBtn.setSize(60, 25);
        skipBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                done();
            }
        });

        add(tooltip);
        add(fractalPane);
        add(blackbar);

    }

    public void settiptext() {
        if (currenttip < tips.length) {
            if (currenttip == tips.length - 1) {
                nextBtn.setText("Done");
            }
            tiptext.setText(tips[currenttip]);
            currenttip++;
        } else {
            done();
        }
    }

    public void done() {
        windowFrame.setMinimumSize(null);
        windowFrame.setContent(new Home(fractalPane));
    }

    class resizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            fractalPane.setSize(getWidth(), getHeight() - 35);
            skipBtn.setLocation(getWidth() - 65, 5);
            blackbar.setLocation(0, getHeight() - 35);
            blackbar.setSize(getWidth(), 35);
            // windowFrame.redraw();
        }
    }

}
