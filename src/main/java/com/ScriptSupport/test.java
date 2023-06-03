package com.ScriptSupport;

import java.awt.Graphics;

import com.Application.App;

public class test extends App {
    
    public test() {}

    public static void main(String[] args) {
        new test()
        .setAppTitle("Yeet")
        .setDimensions(800, 600)
        .setDesiredFPS(60)
        .setFrameBuff(2)
        .addSystemScript(()->{System.out.println("asdas");})
        .run();
    }

    @Override
    public void update(float delta) {}

    @Override
    public void render(Graphics g) {}

    @Override
    public void input() {}

}
