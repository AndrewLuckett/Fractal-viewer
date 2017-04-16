package core.taskables;

import java.awt.Point;
import java.awt.image.BufferedImage;

public interface TileTaskable {
    public BufferedImage doTile(Point id, double pixelwidth);

}
