package helper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import Renders.Camera;

public abstract class App extends JFrame implements KeyListener {
    private Panel panel = new Panel();
    //private Camera camera; // will re-implement later
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
        //
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setTitle("Application"); // default Title
        this.setKeyListener(this);
        this.setFocusable(true);
        //
        panel.setSize(getWidth(), getWidth());
        this.add(panel);
        //
        this.setVisible(true);
        this.repaint();
        //
    }
    /**
     * <h3>A class to define window and application behavior</h3>
     * creates a default window and graphics context
     */
    public App(String title, int width, int height/*, Camera camera*/) {
        //
        this.setSize(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle(title);
        this.setKeyListener(this);
        this.setFocusable(true);
        //
        panel.setSize(getWidth(), getHeight());
        this.add(panel);
        //
        //this.camera = camera;
        this.setVisible(true);
        this.repaint();
        //
    }
    //
    public void run() {
        double last = System.nanoTime();
        double MS_PER_UPDATE = 25_000_000.0;
        double lag = 0.0f;
        //
        while(state == App.ApplicationState.Running) {
            double current = System.nanoTime();
            double elapsed = current - last;
            last = current;
            lag += elapsed;
            //
            while(lag >= MS_PER_UPDATE) {
                update((float)elapsed);
                lag -= MS_PER_UPDATE;
            }
            //render(lag / MS_PER_UPDATE); // old render version where you WOULD HAVE TO input the frame delay (would be nice to hive but maybe ill figure it out later)
            repaint();
        }
    }
    //public abstract void render(double FAS); // FAS (stands for, Frames. Ahead. of. Schedule) // old implementation
    public abstract void render(Graphics g);
    public abstract void update(float delta); // delta is the time between now andd the last frame
    //
    public void CloseApp() {
        this.dispatchEvent(
            new WindowEvent(this, WindowEvent.WINDOW_CLOSING)
        );
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    //
    public class Panel extends JPanel {
        //
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            //
            render(g);
            draw(g);
        }
        //
        public void draw(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawString("FPS: " + fps, 10, 10);
        }
    }
    /** sets the cam to be used for this window (the engine only supports one cam TOTAL, at the moment...) */
    /*public void setCamera(Camera camera) {
        this.camera = camera;
    }*/
    //
    public void setKeyListener(KeyListener kl) {
        this.addKeyListener(kl);
    }
    //
    public Panel getPanel() {
        return panel;
    }
    /*public void setMouseListener(MouseListener ml) {
        this.addMouseListener(ml);
    }*/
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //
        panel.repaint();
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
        Closing
    }
}
