package Menu;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import helper.App;
import helper.Button;

public class MenuEditor extends App {
    private List<Button> btns = new ArrayList<>();
    private Button tmp;
    //
    public MenuEditor(String title, int width, int height) {
        super(title, width, height);
        this.setBackground(Color.black);
        this.addMouseListener(this);
        //
        b = new Button("Button", 0, 0);
        b.width = 100;
        b.height = 30;
        b.addActionListener((e) -> {
            tmp.setText("TEMP");
            tmp.setVisible(true);
        });
        tmp = new Button("", 0, 0, 100, 30);
        tmp.setVisible(false);
        //
        this.panel.add(b);
        this.panel.add(tmp);
        //
        addCustomKey("UP", KeyEvent.VK_UP);
        addCustomKey("DOWN", KeyEvent.VK_DOWN);
        addCustomKey("LEFT", KeyEvent.VK_LEFT);
        addCustomKey("RIGHT", KeyEvent.VK_RIGHT);
        //
        tmp.addActionListener((e) -> {
            btns.add(tmp);
            tmp.x = 0;
            tmp.y = 0;
            tmp.setVisible(false);
        });
    }
    Button b;
    //
    @Override
    public void render(Graphics g) {}
    @Override
    public void update(float delta) {
        b.setSize(b.width, b.height);
        b.setLocation(10, 120);
        tmp.setLocation(tmp.x, tmp.y);
        tmp.setSize(100, 100);
        //
        for(int i = 0; i < btns.size(); i++) {
            btns.get(i).setLocation(btns.get(i).x, btns.get(i).x);
            btns.get(i).setSize(btns.get(i).width, btns.get(i).height);
        }
    }
    @Override
    public void input() {
        //
        if(keypressed("up")) {
            tmp.y -= 1;
        }
        if(keypressed("down")) {
            tmp.y += 1;
        }
        if(keypressed("left")) {
            tmp.x -= 1;
        }
        if(keypressed("right")) {
            tmp.x += 1;
        }
        if(keypressed("spc")) {
            box = new JDialog(new JFrame("IDK")); // "Pressing escape will exit and save this menu/layout, Are you sure"
            box.setVisible(true);
        }
        DebugKeys();
        //
        tmp.repaint();
    }
    private static JDialog box;
}
