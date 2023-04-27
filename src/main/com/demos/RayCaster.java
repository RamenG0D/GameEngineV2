package com.demos;

import java.awt.Graphics;
import com.utils.App;

public class RayCaster extends App {

    public RayCaster() {
        super("RayCast", 800, 600, 60, null);
        //
    }

    public static void main(String[] args) {
        new RayCaster().run();
    }

    @Override
    public void deprecatedGraphics(Graphics g) {}

    @Override
    public void update(float delta) {}

    @Override
    public void render() {}

    @Override
    public void input() {}

}
