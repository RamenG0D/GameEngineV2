package Menu;

import java.awt.Color;
import java.awt.Graphics;
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
        //
        b = new Button("Button", 0, 0);
        b.addActionListener((e) -> {
            tmp = new Button("TEMP", 0, 0);
            tmp.setVisible(true);
        });
        tmp = new Button("", 0, 0, 0, 0);
        tmp.setVisible(false);
        //
        this.panel.add(b);
    }
    Button b;
    //
    @Override
    public void render(Graphics g) {
        b.setLocation(10, 80);
    }
    @Override
    public void update(float delta) {
        //
        if(tmp != null) {
            this.panel.add(tmp);
            //tmp.setLocation(m.getMouseX() - 50, m.getMouseY() - 50);
            tmp.setSelected(false);
            tmp.setSize(100, 100);
        }
        System.out.println("x: " + m.getMouseX() + " y: " + m.getMouseY());
        for(int i = 0; i < btns.size(); i++) {
            btns.get(i).setLocation(btns.get(i).x, btns.get(i).x);
            btns.get(i).setSize(btns.get(i).width, btns.get(i).height);
        }
    }
    @Override
    public void input() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'input'");
    }
}
