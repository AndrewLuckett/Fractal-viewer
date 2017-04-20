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

import core.FractalData;
import window.Content;

public class JuliaDialog extends Content {

    private static final long serialVersionUID = 1L;

    JTextField centrex = new JTextField(10);
    JTextField centrey = new JTextField(10);
    JButton gobtn = new JButton("Go");
    Home parent;

    public JuliaDialog(Home parent) {
        this.parent = parent;
    }

    @Override
    protected void create() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        newrow("Coord X", centrex);
        newrow("Coord Y", centrey);

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
                if (valid) {
                    FractalData.coords = new Point2D.Double(x, y);
                    parent.dojulia();
                    windowFrame.dispose();
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
