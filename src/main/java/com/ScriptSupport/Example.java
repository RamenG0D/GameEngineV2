package com.ScriptSupport;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Example implements Script {
    // Will act as a sub routine to act like a stop watch and is run with all other added scripts
    
    public double time = 0;

    @Override
    public void run() {
        time += 0.01;
    }

    public void drawTimeAsString(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(Font.getFont("Calibre"));
        g.drawString("Timer -> "+time, 20, 20);
    }
    
}
