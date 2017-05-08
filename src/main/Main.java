package main;

import java.awt.Dimension;

import ui.Home;
import window.WindowFrame;

public class Main {

    public static void main(String[] args) {
        Debuglog.loglevel = 5;

        WindowFrame inst = new WindowFrame(new Home(), new Dimension(1280, 720), "Fractal Viewer - Andrew Luckett 2017");
        inst.setVisible(true);

    }

}
