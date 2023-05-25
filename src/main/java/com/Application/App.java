package com.Application;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;
import com.ScriptSupport.Script;
import com.ScriptSupport.ScriptHandler;

public abstract class App implements KeyListener, MouseInputListener {
    private HashMap<String, Key> keys = new HashMap<>(); // used to find the new keys keycode
    //protected DepResourceManager resourceManager;
    protected volatile Mouse mouse = new Mouse();
    private JFrame frame = new JFrame();
    private Color BackgroundColor = Color.BLACK;
    private int desiredFps = 30; // 30 fps defualt
    private boolean threaded = false, debug = false;
    private int buffer = 3; private Robot rob;
    private boolean capture = false;
    protected int fps;

    /**
     * the currently initialized Application's State for general use and modification/checking
     */
    public ApplicationState state = ApplicationState.Running;

    /**
     * <h3>A class to define window and application behavior</h3>
     * creates a default window and graphics context
     */
    public App(String title) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setTitle(title);
        frame.setLayout(null);
        frame.addMouseMotionListener(this);
        frame.addMouseListener(this);
        frame.addKeyListener(this);
        frame.setVisible(true);
        try {this.rob = new Robot();}
        catch(AWTException e)
        {e.printStackTrace();}
        this.setupKeys();
    }

    public static void splash(Graphics g, Image splash, int width, int height, int i) {
        g.drawImage(splash, 0, 0, width, height, null);
        g.setColor(new Color(0, 0, 0, Math.min(255, i*10)));
        g.fillRect(0, 0, width, height);

        try{Thread.sleep(50);}
        catch(InterruptedException e)
        {e.printStackTrace();}
    }

    /**
     * <h3>A class to define window and application behavior</h3>
     * creates a default window and graphics context
     */
    public App(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Demo"); // default
        frame.setSize(600, 600); // default
        frame.addMouseMotionListener(this);
        frame.addMouseListener(this);
        frame.addKeyListener(this);
        frame.setLayout(null);
        frame.setVisible(true);
        try{this.rob = new Robot();}
        catch(AWTException e)
        {e.printStackTrace();}
        this.setupKeys();
    }

    public int getWidth() {
        return frame.getWidth();
    }

    public int getHeight() {
        return frame.getHeight();
    }

    public App setDimensions(int width, int height) {
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        return this;
    }

    public App setAppTitle(String name) {
        frame.setTitle(name);
        return this;
    }

    public App setFrameBuff(int buff) {
        this.buffer = buff;
        return this;
    }

    public App setDesiredFPS(int fps) {
        this.desiredFps = fps;
        return this;
    }

    public App setThreaded(boolean t) {
        this.threaded = t;
        return this;
    }

    public final void init(
        String title, 
        int width, 
        int height, 
        Integer buffer, 
        int desiredFps, 
        Color backgroundColor,
        boolean threaded
    ) {
        if(buffer != null) this.buffer = buffer;
        this.desiredFps = desiredFps;
        this.threaded = threaded;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.addMouseMotionListener(this);
        frame.addMouseListener(this);
        frame.addKeyListener(this);
        frame.setTitle(title);
        frame.setFocusable(true);
        frame.setLayout(null);
        this.setColor(backgroundColor);
    }

    public synchronized void start() {
        this.run();
    } 

    public App setColor(Color c) {
        this.BackgroundColor = c;
        return this;
    }

    public App addSystemScript(Script s) {
        ScriptHandler.getInstance().addScript(s);
        return this;
    }

    protected volatile Graphics g;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public final void run() {
        final double nsPerTick = 1000000000D / desiredFps;
        long lastMs = System.currentTimeMillis();
        long last = System.nanoTime();
        double delta = 0.0, elapsed = 0.0;
        int fpsCounter=0;

        Image img = null;
        try{
            img = ImageIO.read(new File("assets/rainbow.png"));
        }catch(IOException e){}
        for (int i = 0; i < 60; i++)
        {
            if(frame.getBufferStrategy() == null) frame.createBufferStrategy(buffer);
            g = frame.getBufferStrategy().getDrawGraphics();
            splash(g, img, frame.getWidth(), frame.getHeight(), i);
            frame.getBufferStrategy().show();
        }

        while(state == App.ApplicationState.Running) {
            long now = System.nanoTime();
            elapsed = now - last;
            last = now;

            delta += elapsed / nsPerTick;

            if(delta >= 1) {
                update((float)delta);
                ScriptHandler.getInstance().runScripts();
                delta--;

                if(frame.getBufferStrategy() == null) frame.createBufferStrategy(buffer);
                final Graphics g = frame.getBufferStrategy().getDrawGraphics();
                g.setColor(BackgroundColor);
                g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
                if(threaded) {
                    try{executor.execute(()->{render(g); if(debug){drawFps(g);} g.dispose();});}
                    catch(RejectedExecutionException e){};
                } else {
                    render(g);
                    if(debug)
                    {drawFps(g);}
                    g.dispose();
                }
                frame.getBufferStrategy().show();
                if(debug){fpsCounter++;}

                input();
            }

            if(System.currentTimeMillis() - lastMs >= 1000) 
            {
                if(debug){fps = fpsCounter; fpsCounter = 0;}
                lastMs += 1000;
            }
        }
    }

    /** its not nessesary to set this to false as that is the default */
    public App setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public void drawFps(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibre", Font.PLAIN, 14));
        g.drawString("FPS: "+fps, 20, 54);
    }

    private void setupKeys() {
        keys.put("ESC", new Key(KeyEvent.VK_ESCAPE, false));
        keys.put("SPC", new Key(KeyEvent.VK_SPACE, false));
        keys.put("W", new Key(KeyEvent.VK_W, false));
        keys.put("A", new Key(KeyEvent.VK_A, false));
        keys.put("S", new Key(KeyEvent.VK_S, false));
        keys.put("D", new Key(KeyEvent.VK_D, false));
    }

    public void captureMouse() {
    }

    public void releaseMouse() {
        //
    }

    public abstract void update(float delta); // delta is the time between now andd the last frame
    public abstract void render(Graphics g); // used for all things rendering
    public abstract void input(); // called continuosly but this function will control the flow of (keyboard / mouse) input events

    public void CloseApp() {
        frame.dispatchEvent(
            new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)
        );
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public final void toggle(int keycode, boolean isPressed) {
        keys.forEach(
            (keyName, key) -> {
                if(key.keycode == keycode) key.toggle(isPressed);
            }
        );
    }

    /** used to debug whether or not keys are pressed */
    public final void DebugKeys() {
        keys.forEach(
            (string, key) -> {
                if(key.pressed) System.out.println("Key: " + string);
            }
        );
    }

    /** used to debug the mouse position */
    public final void DebugMouse() {
        System.out.println("mouse X: "+mouse.x+" mouse Y: "+mouse.y+" mouse click X: "+mouse.cX+" mouse click Y: "+mouse.cY);
    }

    public class Mouse {
        private int x, y, cX, cY;
        private boolean captured;

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

        public void setCaptured(boolean captured) {
            this.captured = captured;
        }

        public boolean isCaptured() {
            return captured;
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e.getKeyCode(), true);
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e.getKeyCode(), false);
    }

    public boolean keypressed(String key) {
        if(keys.get(key.toUpperCase()) != null) return keys.get(key.toUpperCase()).isPressed();
        else try {throw new Exception("The key {"+key+"} Was not found in key map/n you can add new ones by calling the addCustomKey(String KeyName, int keycode) function!");}
        catch(Exception e) {e.printStackTrace();}
        return false;
    }
 
    public class Key {
        private int numTimesPressed;
        private boolean pressed;
        private int keycode;

        Key(int keycode, boolean pressed) {
            this.keycode = keycode;
            this.pressed = pressed;
        }

        public int getKeyCode() {
            return keycode;
        }

        public boolean isPressed() {
            return pressed;
        }

        public int getNumTimesPressed() {
            return numTimesPressed;
        }

        public void toggle(boolean isPressed) {
            pressed = isPressed;
            if(pressed)
            numTimesPressed++;
        }
    }

    public void addCustomKey(String KeyName, int keycode) {
        keys.putIfAbsent(KeyName, new Key(keycode, false));
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(capture) captureMouse();
        else {
            mouse.cX = e.getX();
            mouse.cY = e.getY();
        }
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
        mouse.x = e.getX();
        mouse.y = e.getY();
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
        Idle
    }
}
