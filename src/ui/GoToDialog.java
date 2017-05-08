package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import window.Content;

public class GoToDialog extends Content {

    private static final long serialVersionUID = 1L;
    private Fractal pane;

    JTextField centrex = new JTextField(10);
    JTextField centrey = new JTextField(10);
    JTextField width = new JTextField(10);
    JTextField height = new JTextField(10);

    public JButton gobtn = new JButton("Go");

    public GoToDialog(Fractal pane) {
        this.pane = pane;
    }

    @Override
    protected void create() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        centrex.setText("0");
        centrey.setText("0");
        width.setText("2");
        height.setText("2");

        newrow("Centre X", centrex);
        newrow("Centre Y", centrey);
        newrow("Width", width);
        newrow("Height", height);
        add(gobtn);

        gobtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean valid = true;
                Double x = null;
                try {
                    x = Double.parseDouble(centrex.getText());
                } catch (NumberFormatException err) {
                    valid = false;
                    centrex.setText("Invalid");
                }
                Double y = null;
                try {
                    y = Double.parseDouble(centrey.getText());
                } catch (NumberFormatException err) {
                    valid = false;
                    centrey.setText("Invalid");
                }
                Double widthval = null;
                try {
                    widthval = Double.parseDouble(width.getText());
                } catch (NumberFormatException err) {
                    valid = false;
                    width.setText("Invalid");
                }
                Double heightval = null;
                try {
                    heightval = Double.parseDouble(height.getText());
                } catch (NumberFormatException err) {
                    valid = false;
                    height.setText("Invalid");
                }

                if (valid) {
                    pane.gotoloc(new Point2D.Double(-x, -y), new Point2D.Double(widthval, heightval));
                    // windowFrame.dispose();
                }
            }
        });
    }

    public void newrow(String text, JTextField field) {
        JPanel row = new JPanel();

        JLabel name = new JLabel(text);
        name.setFont(new Font("Arial", Font.PLAIN, 15));
        name.setVerticalAlignment(JLabel.TOP);
        name.setHorizontalAlignment(JLabel.CENTER);
        name.setAlignmentX(CENTER_ALIGNMENT);
        row.add(name);

        row.add(field);

        add(row);
    }
}
