package com.app.Menus;

import java.awt.Graphics;
import java.io.IOException;

import com.app.helper.Button;
import com.app.helper.ImageLoader;

public class MainMenu extends Menu {
    private Button btn;
    //
    public MainMenu() throws IOException {
        btn = new Button(100, 100, 100, 100, new ImageLoader("assets/Red.png", 100).get_image());
        //
        this.setSize(720, 600);
        this.setDoubleBuffered(true);
        //
        this.add(btn);
    }
    //
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //
        btn.repaint();
        //
    }
    //
}
