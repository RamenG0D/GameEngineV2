package Menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
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
    private BufferedImage img;
    private int[] pixels;
    @Override
    public void render(Graphics g) {
        //
        if(g == null) return;
        //
        img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        //
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        //
        for(int i = 1400; i < pixels.length; i++) {
            pixels[100] = -231213144;
        }
        //g.drawImage(img, 0, 0, null);
    }
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
            //
        }
        //
        tmp.repaint();
    }
    @Override
    public void render() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }
}
