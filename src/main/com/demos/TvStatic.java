package com.demos;

import java.awt.Color;
import java.util.Random;

import com.utils.App;

public class TvStatic extends App {

    public TvStatic() {super();}

    public static void main(String[] args) {
        new TvStatic().run();
    }

    @Override
    public void update(float delta) {}

    @Override
    public void render() {
        Random rand = new Random();
        //
        for (int r = 0; r < getScreen().getWidth(); r++) {
            for (int c = 0; c < getScreen().getHeight(); c++) {
                getScreen().setRGB(r, c, new Color(rand.nextInt(0, 255), rand.nextInt(0, 255), rand.nextInt(0, 255)).getRGB());
            }
        }
    }

    @Override
    public void input() {}
}
