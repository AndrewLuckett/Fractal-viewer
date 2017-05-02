package core;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import core.taskables.TileTaskable;
import main.Debuglog;
import ui.Fractal;

public class Tiles {
    Point centre = new Point();
    Double pixelwidth = 0d;
    JobTasker taskInterface;

    Map<Point, Tile> tiledata = new HashMap<Point, Tile>();
    private Point tilecount;
    private Fractal drawsurface;

    Boolean sized = false;
    private Point2D startBounds;
    private Point2D startCentre;

    public Tiles(Fractal drawsurface, Class<? extends TileTaskable> task, Point2D startCentre, Point2D startBounds) {
        this.startBounds = startBounds;
        this.startCentre = startCentre;
        taskInterface = new JobTasker(task);
        this.drawsurface = drawsurface;

    }

    public void gotoloc(Point2D startCentre, Point2D startBounds) {
        this.startBounds = startBounds;
        this.startCentre = startCentre;
        sized = false;

        tiledata.forEach((id, tile) -> {
            removeTile(tile);
        });
        tiledata.clear();
        windowResized(new Point(drawsurface.getWidth(), drawsurface.getHeight()));
    }

    public void fixedupdate() {
        List<Point> remove = new ArrayList<Point>();
        taskInterface.tasks.forEach((id, img) -> {
            if (tiledata.containsKey(id)) {
                if (img.isDone()) {
                    Debuglog.log("Tile done", 1);
                    remove.add(id);
                    try {
                        tiledata.get(id).fill(img.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }
            } else {
                remove.add(id);
            }
        });
        remove.forEach(id -> {
            taskInterface.tasks.remove(id);
        });
    }

    public void checkAll() {
        Point roundedcentre = new Point(centre.x / 100, centre.y / 100);

        List<Point> safe = new ArrayList<Point>();

        for (int x = -tilecount.x / 2 - 1; x <= tilecount.x / 2 + 1; x++) {
            for (int y = -tilecount.y / 2 - 1; y <= tilecount.y / 2 + 1; y++) {
                Point id = new Point(x, y);
                id.translate(-roundedcentre.x, -roundedcentre.y);
                safe.add(id);
                if (!tiledata.containsKey(id)) {
                    newTile(id);
                }
            }
        }

        // TODO remove tiles that aren't safe
        // or better
    }

    public void windowResized(Point size) {
        tilecount = new Point(size.x / 100, size.y / 100);
        if (!sized) {
            Debuglog.log("SCALED", 5);
            pixelwidth = Math.max(startBounds.getX() / size.getX(), startBounds.getY() / size.getY());
            centre = new Point((int) (startCentre.getX() / pixelwidth), (int) (startCentre.getY() / pixelwidth));
            sized = true;
        }
        checkAll();
        move(new Point());
    }

    public void zoom(Boolean dir) {
        Point2D.Double realcentre = new Point2D.Double(centre.getX() * pixelwidth, centre.getY() * pixelwidth);
        if (dir) {
            pixelwidth *= 1.25;
        } else {
            pixelwidth /= 1.25;
        }
        centre.setLocation(realcentre.getX() / pixelwidth, realcentre.getY() / pixelwidth);
        tiledata.forEach((id, tile) -> {
            removeTile(tile);
        });
        tiledata.clear();
        checkAll();
        move(new Point());

    }

    private void newTile(Point id) {
        Debuglog.log("New tile: " + id, 4);
        Tile newtile = new Tile();
        tiledata.put(id, newtile);
        drawsurface.add(newtile);
        newtile.setSize(100, 100);
        taskInterface.addJob(id, pixelwidth);
    }

    private void removeTile(Tile tile) {
        drawsurface.remove(tile);
    }

    public void move(Point dist) {
        centre.translate(dist.x, dist.y);
        tiledata.forEach((id, tile) -> {
            tile.setLocation(new Point(
                id.x * 100 + centre.x + tilecount.x * 50,
                id.y * 100 + centre.y + tilecount.y * 50));
        });
        checkAll();
    }
}
