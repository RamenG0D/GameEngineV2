import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import Renders.Camera;
import Renders.Camera2D;
import Test.Player;
import helper.App;
import helper.Button;

public class Main implements KeyListener {
    private Camera cam;
    private Player p;
    private App app;
    private double fps;
    //
    public Main() {
        //
        p = new Player(100, 100);
        cam = new Camera2D(p, 0 ,0, 800, 600);
        app = new App("App", 800, 600, cam);
        app.setKeyListener(this);
        //
        double last = System.nanoTime();
        double MS_PER_UPDATE = 20_000_000.0;
        double lag = 0.0f;
        //
        while(app.state == App.ApplicationState.Running) {
            double current = System.nanoTime();
            double elapsed = current - last;
            last = current;
            lag += elapsed;
            //
            while(lag >= MS_PER_UPDATE) {
                update();
                lag -= MS_PER_UPDATE;
            }
            //
            render((float)(lag / MS_PER_UPDATE));
        }
        //
    }
    //
    public void render(float delta) {
        app.setFPS(delta);
        app.repaint();
    }
    //
    public void update() {
        // things update here/things in world update here
        input();
    }
    //
    public static void main(String[] args) {
        //
        new Main();
        //
    }
    //
    boolean w = false;
    boolean a = false;
    boolean s = false;
    boolean d = false;
    //
    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        //
        if(keycode == KeyEvent.VK_W) {
            w = true;
        }
        if(keycode == KeyEvent.VK_A) {
            a = true;
        }
        if(keycode == KeyEvent.VK_S) {
            s = true;
        }
        if(keycode == KeyEvent.VK_D) {
            d = true;
        }
        if(keycode == KeyEvent.VK_ESCAPE) {
            
        }
        //
        app.repaint();
    }
    //
    public void CloseApp() {
        app.dispatchEvent(
            new WindowEvent(app, WindowEvent.WINDOW_CLOSING)
        );
    }
    //
    public void input() {
        if(w) {
            p.x += p.dx*5;
            p.y += p.dy*5;
        }
        if(a) {
            p.angle += 5;
            if(p.angle > 359) p.angle -= 360;
        }
        if(s) {
            p.x -= p.dx*5;
            p.y -= p.dy*5;
        }
        if(d) {
            p.angle -= 5;
            if(p.angle < 0) p.angle += 360;
        }
    }
    //
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) w = false;
        if(e.getKeyCode() == KeyEvent.VK_S) s = false;
        if(e.getKeyCode() == KeyEvent.VK_A) a = false;
        if(e.getKeyCode() == KeyEvent.VK_D) d = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    //
    public void DrawMenu(Graphics g) {
        Button btn1 = new Button(200, 300, 100, 80);
        //
        g.setColor(new Color(0,0,0,120));
        g.fillRect(0,0,app.getWidth(),app.getHeight());
        btn1.repaint();
    }
    //
}
