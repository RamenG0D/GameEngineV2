import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Renders.Camera;
import Renders.Camera2D;
import Test.Player;
import helper.App;

public class Main implements KeyListener {
    private Camera cam;
    private Player p;
    private App app;
    //
    public Main() {
        //
        p = new Player(0, 0);
        cam = new Camera2D(p, 0 ,0, 800, 600);
        app = new App("App", 800, 600, cam);
        app.setKeyListener(this);
        p.getVelocity().clamp(-1, 1);
        //
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
        //
        if(w) {
            p.x += (int)(Math.cos(p.angle) * 5);
            p.y += (int)(Math.sin(p.angle) * 5);
        }
        if(a) p.angle -= 1; if(p.angle < 0) p.angle += 360;
        if(s) {
            p.x -= (int)(Math.cos(p.angle) * 5);
            p.y -= (int)(Math.sin(p.angle) * 5);
        }
        if(d) p.angle += 1; if(p.angle > 359) p.angle -= 360;
        //
        app.repaint();
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
}
