package window;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

public class WindowFrame extends JFrame { // Designed for reuse. Uses Content
    static final long serialVersionUID = 0;

    public WindowFrame(Content content, Dimension size, Point location, String name) {
        setTitle(name);
        create(content, size.width, size.height);
        setLocation(location.x, location.y);
    }

    public WindowFrame(Content content, Dimension size, String name) { // For putting in middle
        setTitle(name);
        create(content, size.width, size.height);
    }

    public WindowFrame(Content content, ContainerData data, String name) {
        setTitle(name);
        create(content, data.getSize().width, data.getSize().height);
        setLocation(data.getLocation().x, data.getLocation().y);
    }

    public ContainerData getWindowData() {
        return new ContainerData(this);
    }

    public void setContent(Content content) {
        content.setOpaque(true);
        content.setParent(this);
        setContentPane(content);
        redraw();
        content.create(); // comes last
    }

    public void create(Content content, int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContent(content);
    }

    public void redraw() {
        revalidate();
        repaint();
    }
}