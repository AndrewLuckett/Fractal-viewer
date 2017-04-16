package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import core.Tiles;
import core.taskables.Mandelbrot;

public class MandelbrotFragment extends Fractal {
    private static final long serialVersionUID = 1L;

    public MandelbrotFragment() {
        setLayout(null);
        startscale = new Rectangle(new Point(0, 0), new Dimension(2, 2));
        taskable = Mandelbrot.class;
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void create() {
        tiles = new Tiles(this);
        tiles.move(new Point(0, 0));

    }

}
