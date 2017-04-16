package core;

import java.awt.image.BufferedImage;

public class ImageGenerator { // Designed to allow images to be built pixel by pixel
    private int width = 1024; // default
    private int height = 1024;// default
    private BufferedImage bufferedImage;

    private int x = 0;
    private int y = 0;

    public ImageGenerator(int w, int h) {
        width = w;
        height = h;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void reset() {
        x = 0;
        y = 0;
    }

    public void add_pixel_RGB(int R, int G, int B) {
        int r = R;
        int g = G;
        int b = B;
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }

        if (x >= width) { // newline
            y++;
            x = 0;
        }
        if (y < height) { // Stops over filling
            try {
                bufferedImage.setRGB(x, y, (r << 16) | (g << 8) | (b));
            } catch (Exception e) {
                System.out.print(x);
                System.out.print("\n");
                System.out.print(y);
                System.out.print("\n");
                e.printStackTrace();
            }
        }
        x++;
    }

    public void add_pixel_int(int value) {
        add_pixel_RGB(value, value, value);
    }

    public void add_pixel_hex(String R, String G, String B) {
        add_pixel_RGB(Integer.parseInt(R, 16),
            Integer.parseInt(G, 16),
            Integer.parseInt(B, 16));
    }

    public BufferedImage getImage() {
        return bufferedImage;
    }
}
