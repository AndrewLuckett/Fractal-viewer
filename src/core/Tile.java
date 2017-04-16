package core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Tile extends JLabel {
    private static final long serialVersionUID = 1L;

    public Tile() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("notes.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(Color.GRAY);
        setIcon(new ImageIcon(img));
        // System.out.println("tile drawn");
        setVisible(true);
    }

    public void fill(BufferedImage content) {
        setIcon(new ImageIcon(content));
    }

}
