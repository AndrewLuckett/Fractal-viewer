package core.taskables;

import java.awt.Point;
import java.awt.image.BufferedImage;

import core.ImageGenerator;
import main.Debuglog;

public class BurningShip extends TileTaskable {

    private int cutoff = 2000;

    @Override
    public BufferedImage doTile(Point id, double pixelwidth) {
        ImageGenerator img = new ImageGenerator(100, 100);
        for (double x = (id.x * 100) * pixelwidth; x < ((id.x + 1) * 100) * pixelwidth; x += pixelwidth) {
            for (double y = (id.y * 100) * pixelwidth; y < ((id.y + 1) * 100) * pixelwidth; y += pixelwidth) {
                int temp = algo(x, y);
                if (temp == -1) {
                    img.add_pixel_int(0);
                } else {
                    img.add_pixel_int((int) (temp * 5) + 100);
                }
            }
        }
        Debuglog.log("Job Done: " + id, 2);
        return img.getImage();
    }

    public int algo(double x, double y) {
        double zreal = 0;
        double zimaginary = 0;
        for (int i = 0; i < cutoff; i++) {
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            double zimaginaryUpdated = 2 * Math.abs(zreal) * Math.abs(zimaginary) + y;

            zreal = zrealUpdated;
            zimaginary = zimaginaryUpdated;

            if (zreal * zreal + zimaginary * zimaginary > 4) {
                return i;
            }
        }
        return -1;
    }

}
