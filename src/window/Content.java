package window;

import javax.swing.JPanel;

public abstract class Content extends JPanel { // Designed for reuse. Used by WindowFrame
    static final long serialVersionUID = 0;

    protected WindowFrame windowFrame = null;

    public void setParent(WindowFrame parent) {
        this.windowFrame = parent;
    }

    protected abstract void create();
}