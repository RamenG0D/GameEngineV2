package Menu;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import helper.App;
import helper.Button;

public class MenuEditor extends App {
    private List<Button> btns = new ArrayList<>();
    private Button tmp;
    //
    public MenuEditor(String title, int width, int height, int desiredFps, Integer frameBuffer) {
        super(title, width, height, desiredFps, frameBuffer);
        //
        b = new Button("Button", 10, 30, 100, 30);
        b.addActionListener((e) -> {
            tmp.setVisible(true);
            b.setVisible(false);
        });
        tmp = new Button("TEMP", 0, 0, 100, 30);
        tmp.setVisible(false);
        //
        this.add(tmp);
        this.add(b);
        //
        addCustomKey("UP", KeyEvent.VK_UP);
        addCustomKey("DOWN", KeyEvent.VK_DOWN);
        addCustomKey("LEFT", KeyEvent.VK_LEFT);
        addCustomKey("RIGHT", KeyEvent.VK_RIGHT);
        //
        tmp.addActionListener((e) -> {
            btns.add(new Button("Temp", tmp.x, tmp.y, 100, 30));
            tmp = new Button("TEMP", 0, 0, 100, 30);
            tmp.x = 0;
            tmp.y = 0;
            tmp.setVisible(false);
            b.setVisible(true);
        });
        //
    }
    private Button b;

    @Override
    public void render() {}
    @Override
    public void update(float delta) {
        b.setSize(b.width, b.height);
        b.setLocation(b.x, b.y);
        tmp.setLocation(tmp.x, tmp.y);
        tmp.setSize(100, 100);
        //
        for(int i = 0; i < btns.size(); i++) {
            btns.get(i).setLocation(btns.get(i).x, btns.get(i).x);
            btns.get(i).setSize(btns.get(i).width, btns.get(i).height);
            this.add(btns.get(i));
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
        //
    }
}
