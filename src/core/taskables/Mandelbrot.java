package core.taskables;

import java.awt.Point;
import java.awt.image.BufferedImage;

import core.ImageGenerator;
import main.Debuglog;

public class Mandelbrot extends TileTaskable {
    private int cutoff = 2000;

    @Override
    public BufferedImage doTile(Point id, double pixelwidth) {
        ImageGenerator img = new ImageGenerator(100, 100);
        for (double x = (id.x * 100) * pixelwidth; x < ((id.x + 1) * 100) * pixelwidth; x += pixelwidth) {
            for (double y = (id.y * 100) * pixelwidth; y < ((id.y + 1) * 100) * pixelwidth; y += pixelwidth) {
                int temp = algo(x, y, 0, 0);
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

    public int algo(double Creal, double Cimaginary, double Zreal, double Zimaginary) { // Only one set should be
        // changing
        double r = Zreal; // hunt through Creal and Cimaginary to get Mandelbrot
        double i = Zimaginary; // hunt through Zreal and Zimaginary to get Julia sets
        int steps = 0;
        for (int x = 0; x < cutoff; x++) { // For Mandelbrot Z's should be 0

            steps++; // For Julia the C's can be any value
            double newr = r * r - i * i + Creal;
            double newi = 2 * r * i + Cimaginary; // Cutoff should scale with the zoom
            r = newr; // A high cutoff is slow and puts hard edges on the bits that slip through
            i = newi; // A low cutoff lets through more accidental values

            if ((r * r + i * i) > 4) {
                return steps; // Using this value grey boundaries can be made
            }
        }
        return -1; // Is in set
    }
}
