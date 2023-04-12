import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import Renders.Camera;
import Renders.Camera2D;
import Test.Player;
import helper.App;
import helper.Button;

public class Main extends App {
    private MainMenu menu;
    private Camera cam;
    private Player p;
    //
    public Main() {
        //
        p = new Player(100, 100);
        cam = new Camera2D(0 ,0, this.getWidth(), this.getHeight());
        menu = new MainMenu(this.getHeight(), this.getWidth());
        //
    }
    @Override
    public void render(double FAS) { // FAS (Frames Ahead Of Schedue)
        if(this.gameState == GameState.Menu) menu.DrawMenu(this.getPanel().getGraphics());
    }
    @Override
    public void update(float delta) { // delta is the time between now and the last frame or the FPS
        // this method is called as the main logic loop for the game
        input();
    }
    //
    public GameState gameState = GameState.Menu;
    //
    public enum GameState {
        Game,
        Menu
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
        System.out.println("X:" + p.x + " Y: " + p.y);
    }
    //
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) w = false;
        if(e.getKeyCode() == KeyEvent.VK_S) s = false;
        if(e.getKeyCode() == KeyEvent.VK_A) a = false;
        if(e.getKeyCode() == KeyEvent.VK_D) d = false;
    }
    //
    class MainMenu {
        private int width, height;
        private Button btn1;
        //
        public MainMenu(int width, int height) {
            //
            btn1 = new Button(200, 300, 100, 80);
            this.height = height;
            this.width = width;
            //
        }
        //
        public void DrawMenu(Graphics g) {
            //
            g.setColor(new Color(0,0,0,120));
            g.fillRect(0,0, width, height);
            //
        }
    }
    //
}
