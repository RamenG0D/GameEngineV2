package helper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import Renders.Screen;

public abstract class App extends JFrame implements KeyListener, MouseInputListener {
    private HashMap<String, Key> keys = new HashMap<>(); // used to find the new keys keycode
    protected Panel panel = new Panel();
    public Mouse mouse = new Mouse();
    protected double fps;
    /**
     * the currently initialized Application's State for general use and modification/checking
     */
    public ApplicationState state = ApplicationState.Running;
    /**
     * <h3>A class to define window and application behavior</h3>
     * creates a default window and graphics context
     */
    public App() {
        init(); setupKeys();
        this.setVisible(true);
        this.repaint();
    }
    /**
     * <h3>A class to define window and application behavior</h3>
     * creates a default window and graphics context
     */
    public App(String title, int width, int height/*, Camera camera*/) {
        init(title, width, height); setupKeys();
        this.setVisible(true);
        this.repaint();
    }
    //
    private void init(String title, int width, int height) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.setTitle(title);
        this.setFocusable(true);
        this.setLayout(null);
        //
        panel.setSize(getWidth(), getHeight());
        this.add(panel);
        this.panel.setFocusable(false);
    }
    //
    private void init() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setTitle("Application"); // default Title
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setLayout(null);
        //
        panel.setSize(getWidth(), getWidth());
        this.add(panel);
        this.panel.setFocusable(false);
    }
    //
    public void run() {
        long lastMs = System.currentTimeMillis();
        double nsPerTick = 1000000000D / 60D;
        long last = System.nanoTime();
        double delta = 0.0;
        //
        while(state == App.ApplicationState.Running) {
            long now = System.nanoTime();
            delta += (now - last) / nsPerTick;
            last = now;
            //
            if(delta >= 1) {
                input(); update((float)delta);
                delta -= 1;
            }
            //
            render((float)delta);
            panel.paintImmediately(panel.getBounds());
            fps++;
            //
            if(System.currentTimeMillis() - lastMs >= 1000) {
                lastMs += 1000;
                fps = 0;
            }
            //
        }
    }
    //
    private void setupKeys() {
        keys.put("ESC", new Key(KeyEvent.VK_ESCAPE, false));
        keys.put("SPC", new Key(KeyEvent.VK_SPACE, false));
        keys.put("W", new Key(KeyEvent.VK_W, false));
        keys.put("A", new Key(KeyEvent.VK_A, false));
        keys.put("S", new Key(KeyEvent.VK_S, false));
        keys.put("D", new Key(KeyEvent.VK_D, false));
    }
    //
    public abstract void update(float delta); // delta is the time between now andd the last frame
    public abstract void render(float delta); // used for all things rendering
    public abstract void input(); // called continuosly but this function will control the flow of (keyboard / mouse) input events
    //
    public void CloseApp() {
        this.dispatchEvent(
            new WindowEvent(this, WindowEvent.WINDOW_CLOSING)
        );
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    //
    public void toggle(int keycode, boolean isPressed) {
        keys.forEach(
            (keyName, key) -> {
                if(key.keycode == keycode) key.toggle(isPressed);
            }
        );
    }
    //
    public class Panel extends JPanel {
        //
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            //
            draw(g);
        }
        //
        public void draw(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawString("FPS: " + fps, 10, 10); // not the actual FPS... for now at least, not sure how to compute the FPS of the app / gameloop currently
        }
    }
    /** used to debug whether or not keys are pressed */
    public void DebugKeys() {
        keys.forEach(
            (string, key) -> {
                if(key.pressed) System.out.println("Key: " + string);
            }
        );
    }
    /** used to debug the mouse position */
    public void DebugMouse() {
        System.out.println("mouse X: "+ mouse.x +" mouse Y: "+mouse.y+" mouse click X: "+mouse.cX+" mouse click Y: "+mouse.cY);
    }
    /** sets the cam to be used for this window (the engine only supports one cam TOTAL, at the moment...) */
    /*public void setCamera(Camera camera) {
        this.camera = camera;
    }*/
    public class Mouse {
        private int x, y, cX, cY;
        //
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public int getCX() {
            return cX;
        }
        public int getCY() {
            return cY;
        }
    }
    //
    public Panel getPanel() {
        return panel;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e.getKeyCode(), true);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e.getKeyCode(), false);
    }
    //
    public boolean keypressed(String key) {
        if(keys.get(key.toUpperCase()) != null) return keys.get(key.toUpperCase()).isPressed();
        else try {throw new Exception("The key {"+key+"} Was not found in key map you can add new ones by calling the addCustomKey(String KeyName, int keycode) function!");}
        catch(Exception e) {e.printStackTrace();}
        return false;
    }
    //
    public class Key {
        private int numTimesPressed;
        private boolean pressed;
        private int keycode;
        //
        Key(int keycode, boolean pressed) {
            this.keycode = keycode;
            this.pressed = pressed;
        }
        //
        public int getKeyCode() {
            return keycode;
        }
        //
        public boolean isPressed() {
            return pressed;
        }
        //
        public void toggle(boolean isPressed) {
            pressed = isPressed;
            if(pressed)
            numTimesPressed++;
        }
    }
    //
    public void addCustomKey(String KeyName, int keycode) {
        keys.putIfAbsent(KeyName, new Key(keycode, false));
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        //
        mouse.cX = e.getX();
        mouse.cY = e.getY();
        //
    }
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {
        //
        mouse.x = e.getX();
        mouse.y = e.getY();
        //
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //
        if(panel.getWidth() != getWidth() || panel.getHeight() != getHeight()) {
            panel.setSize(getWidth(), getHeight());
        }
        panel.paintImmediately(getBounds());
    }
    /**
     *  <h3>a general purpose state Manager to facilitate changes in the app such
     *  app closing, app minimized</h3> yep its pretty simple, its ment to serve as more of a wrapper
     *  where ill later add methods to this class to manage the app such as App.exit() -> closes the app
     *  or App app = new App(); app.state = ApplicationState.Closing; which ever i choose, both close the app
     */
    public static enum ApplicationState {
        // app states
        Running,
        Stopped
    }
}
