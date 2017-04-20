package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import javax.swing.JPanel;
import javax.swing.Timer;

import core.Tiles;
import core.taskables.TileTaskable;

public class Fractal extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    static final long serialVersionUID = 0;
    private Point initial;

    protected Tiles tiles;
    private Timer timer;

    public Fractal(Class<? extends TileTaskable> task, Point2D startCentre, Point2D startBounds) {
        setBackground(Color.GRAY); // debug?
        setLayout(null);
        this.addComponentListener(new resizeListener());
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        tiles = new Tiles(this, task, startCentre, startBounds);
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

        timer = new Timer(200, updater);
        timer.start();
    }

    ActionListener updater = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            tiles.fixedupdate();
        }
    };

    public void gotoloc(Point2D startCentre, Point2D startBounds) {
        tiles.gotoloc(startCentre, startBounds);
    }

    // * Listeners
    class resizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            tiles.windowResized(new Point(getWidth(), getHeight()));
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Boolean t = (Math.abs(e.getPreciseWheelRotation()) == e.getPreciseWheelRotation());
        tiles.zoom(t);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point release = e.getPoint();
        release.translate(-initial.x, -initial.y);
        tiles.move(release);
        repaint();
        revalidate();
        initial = e.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        initial = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
