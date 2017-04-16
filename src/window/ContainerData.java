package window;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ContainerData { // Data type
    /*-
     * Basically rectangle
     */
    private Dimension size;// = new Dimension();
    private Point location; // on screen

    public ContainerData() {
        size = new Dimension(0, 0);
        location = new Point(0, 0);
    }

    public ContainerData(JFrame container) {
        size = container.getSize();
        location = container.getLocation();
    }

    public ContainerData(JPanel container) {
        size = container.getSize();
        location = container.getLocation();
    }

    public ContainerData(JLabel container) {
        size = container.getSize();
        location = container.getLocation();
    }

    public ContainerData(Dimension size, Point locationOnScreen) {
        this.size = size;
        location = locationOnScreen;
    }

    public ContainerData(int width, int height, int x, int y) {
        size.width = width;
        size.height = height;
        location.x = x;
        location.y = y;
    }

    public Boolean equals(ContainerData other) {
        if (!size.equals(other.getSize())) {
            return false;
        }
        if (!location.equals(other.getLocation())) {
            return false;
        }
        return true;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public void setSize(int width, int height) {
        size.width = width;
        size.height = height;
    }

    public Dimension getSize() {
        return size;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setLocation(int x, int y) {
        location.x = x;
        location.y = y;
    }

    public Point getLocation() {
        return location;
    }
}
