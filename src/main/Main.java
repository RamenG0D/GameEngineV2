import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;
import Test.Player;
import helper.App;
import helper.Button;

public class Main extends App {
    private MainMenu menu = new MainMenu();
    private Player p = new Player(100, 100);
    //
    public Main(String title, int width, int height/*, Camera cam*/) {
        super(title, width, height/*, cam*/);
        //addCustomKey("Q", KeyEvent.VK_Q);
        run();
    }
    @Override
    public void render(Graphics g) { // FAS (Frames Ahead Of Schedue)
        if(p!=null&&gameState==GameState.Game) p.draw(g);
    }
    @Override
    public void update(float delta) { // delta is the time between now and the last frame or the FPS
        if(gameState == GameState.Menu) { menu.show(); }
        else { menu.hide(); }
    }
    //
    public static void main(String[] args) {
        //Camera cam = new Camera2D(p, 0 ,0, 800, 600);
        new Main("Test Application", 800, 600/*, cam*/);
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
    @Override
    public void input() { // an elagent solution such as if( keypressed( "{KEY}" ) ) {KEY} being the name / letter of the key
        if(keypressed("w")) {
            p.x += p.dx*5;
            p.y += p.dy*5;
        }
        if(keypressed("a")) {
            p.angle += 2;
            FixAng(p.angle);
        }
        if(keypressed("s")) {
            p.x -= p.dx*5;
            p.y -= p.dy*5;
        }
        if(keypressed("d")) {
            p.angle -= 2;
            FixAng(p.angle);
        }
        /*if(m.isClicked()) {
            System.out.println("clicking");
        }*/
    }
    //
    private void FixAng(double a) {
        if(a < 0) a += 360;
        if(a > 359) a -= 360;
    }
    //
    private void DegToRad(double a) {
        //
    }
    //
    public class MainMenu {
        private JLabel label;
        private Button btn;
        //
        public MainMenu() {
            btn = new Button("exit",40, 100, 100, 100);
            label = new JLabel("TEST MENU");
            //
            btn.addActionListener((e) -> {
                //CloseApp();
                gameState = GameState.Game;
            });
            //
            getPanel().add(btn);
            getPanel().add(label);
            btn.setVisible(false);
        }
        //
        public void show() {
            btn.setVisible(true);
            btn.setSize(btn.width, btn.height);
            btn.setLocation(btn.x, btn.y);
            //
            getPanel().setBackground(Color.lightGray);
            //
            repaint();
        }
        //
        public void hide() {
            btn.setVisible(false);
            //
            getPanel().setBackground(Color.GRAY);
            //
            repaint();
        }
    }
    //
}
