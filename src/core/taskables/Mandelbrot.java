package core.taskables;

import java.awt.Point;
import java.awt.image.BufferedImage;

import main.Debuglog;

public class Mandelbrot extends TileTaskable {
    private int cutoff = 2000;

    @Override
    public BufferedImage doTile(Point id, double pixelwidth) {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                int temp = algo((id.x * 100 + x) * pixelwidth, (id.y * 100 + y) * pixelwidth, 0, 0);
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
