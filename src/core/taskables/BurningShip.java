package core.taskables;

import java.awt.Point;
import java.awt.image.BufferedImage;

import core.FractalData;
import main.Debuglog;

public class BurningShip implements TileTaskable {

    int cutoff = FractalData.cutoff;

    @Override
    public BufferedImage doTile(Point id, double pixelwidth) {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                int temp = algo((id.x * 100 + x) * pixelwidth, (id.y * 100 + y) * pixelwidth);
                if (temp == -1) {
                    img.setRGB(x, y, 0);
                } else {
                    img.setRGB(x, y, (int) (temp * 5) + 100);
                }
            }
        }

        Debuglog.log("Job Done: " + id, 2);
        return img;
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
