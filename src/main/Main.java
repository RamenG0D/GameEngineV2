import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import Test.Player;
import helper.App;
import helper.Button;

public class Main extends App {
    private MainMenu menu = new MainMenu();
    private Player p = new Player(100, 100);
    private BufferedImage image = new BufferedImage(800, 580, BufferedImage.TYPE_INT_RGB);
    //
    public Main(String title, int width, int height/*, Camera cam*/) {
        super(title, width, height/*, cam*/);
        //
        addCustomKey("Q", KeyEvent.VK_Q); // Example of how to add a new key other than the defaults -> [w,a,s,d,esc,spc(space)]
        run();
    }
    @Override
    public void render() {
        //if(p!=null&&gameState==GameState.Game) p.render(g);
        /*try {
            image = ImageLoader.getImage("assets/Ball.png");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*if(image == null) return;
        //
        int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        int[] colorBuff = image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        //
        for(int p : pixels) {
            p = 255;
        }
        //
        image.setRGB(0, 0, image.getWidth(), image.getHeight(), colorBuff, 0, image.getWidth());

        g.drawImage(image, 0, 0, null);*/
    }
    @Override
    public void update(float delta) { // delta is the time between now and the last frame or the FPS
        if(gameState == GameState.Menu) { menu.show(); }
        else { menu.hide(); }
    }
    //
    public static void main(String[] args) {
        new Main("Test Application", 800, 600/*, cam*/);
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
        if(keypressed("esc")) {
            gameState = GameState.Menu;
        }
    }
    //
    private void FixAng(double a) {
        if(a < 0) a += 360;
        if(a > 359) a -= 360;
    }
    //
    @SuppressWarnings("unused")
    private double DegToRad(double a) {
        return a * Math.PI/180.0;
    }
    //
    public class MainMenu {
        private Button btn;
        //
        public MainMenu() {
            btn = new Button("Play",40, 100, 100, 100);
            //
            btn.addActionListener((e) -> {//CloseApp();
                gameState = GameState.Game;
            });
            //
            getPanel().add(btn);
            btn.setVisible(false);
        }
        //
        public void show() {
            btn.setVisible(true);
            btn.setSize(btn.width, btn.height);
            btn.setLocation(btn.x, btn.y);
            //
            getPanel().setBackground(Color.lightGray);
        }
        //
        public void hide() {
            btn.setVisible(false);
            getPanel().setBackground(Color.GRAY);
        }
    }
    //
    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }
}
