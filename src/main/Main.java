import java.awt.Graphics;
import java.awt.event.KeyEvent;
import Test.Player;
import helper.App;
import helper.Button;

public class Main extends App {
    private MainMenu menu = new MainMenu(this);
    private Player p;
    //
    public Main(String title, int width, int height, Player p/*, Camera cam*/) {
        super(title, width, height/*, cam*/);
        this.p = p;
        //
        run();
    }
    @Override
    public void render(Graphics g) { // FAS (Frames Ahead Of Schedue)
        if(p != null) p.draw(g);
    }
    @Override
    public void update(float delta) { // delta is the time between now and the last frame or the FPS
        // things update here/things in world update here, this also handles application updates
        if(gameState == GameState.Menu) menu.show(this.getPanel());
        this.fps = delta;
        input();
    }
    //
    public static void main(String[] args) {
        //
        Player p = new Player(100, 100);
        //Camera cam = new Camera2D(p, 0 ,0, 800, 600);
        //
        new Main("Test Application", 800, 600, p/*, cam*/);
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
            gameState = GameState.Menu;
        }
        //
    }
    //
    public GameState gameState = GameState.Menu;
    //
    public enum GameState {
        Game,
        Menu
    }
    //
    public void input() { // ill add a more elagent solution later such as if( keypressed( {CONSTANT}.{KEY} ) )
        if(w) {
            p.x += p.dx*5;
            p.y += p.dy*5;
        }
        if(a) {
            p.angle += 2;
            if(p.angle > 359) p.angle -= 360;
        }
        if(s) {
            p.x -= p.dx*5;
            p.y -= p.dy*5;
        }
        if(d) {
            p.angle -= 2;
            if(p.angle < 0) p.angle += 360;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) w = false;
        if(e.getKeyCode() == KeyEvent.VK_S) s = false;
        if(e.getKeyCode() == KeyEvent.VK_A) a = false;
        if(e.getKeyCode() == KeyEvent.VK_D) d = false;
    }
    //
    class MainMenu {
        public Button[] btns = new Button[] {
            new Button("exit",40, 100, 100, 80)
        };
        //
        public MainMenu(App app) {
            for(Button btn : btns) {
                btn.setLocation(btn.x, btn.y);
                btn.setSize(btn.width, btn.height);
                app.getPanel().add(btn);
                btn.setVisible(false);
            }
            //
            btns[0].addActionListener((e) -> {
                CloseApp();
            });
        }
        //
        public void hide(Panel p) {
            for (Button button : btns) {
                button.setVisible(false);
            }
        }
        //
        public void show(Panel p) {
            for (Button button : btns) {
                button.setVisible(true);
            }
        }
    }
    //
}
