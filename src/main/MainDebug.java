package main;

import java.awt.Dimension;

import ui.Home;
import window.WindowFrame;

public class MainDebug {

    public static void main(String[] args) {

        WindowFrame inst = new WindowFrame(new Home(), new Dimension(1280, 720), "Fractal Viewer Debug - Andrew Luckett 2017");
        inst.setVisible(true);

    }

}
