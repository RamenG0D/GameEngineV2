package com.app.helper;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Image;

public class Button extends JButton {
    //
    /**
     * general base button class that allows for easier creation of JButtons
     * @param x the button x
     * @param y the button y
     * @param width the buttons width
     * @param height the buttons height
     */
    public Button(int x, int y, int width, int height) {
        //
        this.setBounds(x, y, width, height);
        //
    }
    /**
     * general base button class that allows for easier creation of JButtons
     * @param x the button x
     * @param y the button y
     * @param size the buttons total size otherwise known as setting both the width and height to the same value
     */
    public Button(int x, int y, int size) {
        //
        this.setSize(new Dimension(size, size));
        //
    }
    //
    /**
     * general base button class that allows for easier creation of JButtons
     * @param x the button x
     * @param y the button y
     * @param width the buttons width
     * @param height the buttons height
     * @param buttonIcon the Image that the created button will display as the clickable button
     */
    public Button(int x, int y, int width, int height, Image buttonIcon) {
        //
        this.setBounds(x, y, width, height);
        this.setContentAreaFilled(false);
        this.setIcon(new ImageIcon(buttonIcon));
        //
    }
    //
}
