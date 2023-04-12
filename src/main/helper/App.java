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
    private double fps;
    /* 
     * camera clsses are kinda just a funnel where we assemble the graphics data 
     * to what wthe camera wants(3d vs 2d), then the camera hands the result back
     * to the app to be drawn to the panel
     */
    private Camera camera;
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
        panel.setSize(getWidth(), getWidth());
        this.addKeyListener(this);
        this.add(panel);
        this.setVisible(true);
        repaint();
        //
        double last = System.nanoTime();
        double MS_PER_UPDATE = 30_000_000.0;
        double lag = 0.0f;
        //
        while(true) {
            double current = System.nanoTime();
            double elapsed = current - last;
            last = current;
            lag += elapsed;
            //
            while(lag >= MS_PER_UPDATE) {
                update((float)elapsed);
                lag -= MS_PER_UPDATE;
            }
            //
            render(lag / MS_PER_UPDATE);
            repaint();
        }
    }
    /**
     * <h3>A class to define window and application behavior</h3>
     * creates a default window and graphics context
     */
    public App(String title, int width, int height, Camera camera) {
        //
        this.setSize(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addKeyListener(this);
        this.setTitle(title);
        //
        panel.setSize(getWidth(), getHeight());
        this.add(panel);
        //
        this.camera = camera;
        this.setVisible(true);
        repaint();
        //
        double last = System.nanoTime();
        double MS_PER_UPDATE = 30_000_000.0;
        double lag = 0.0f;
        //
        while(true) {
            double current = System.nanoTime();
            double elapsed = current - last;
            last = current;
            lag += elapsed;
            //
            while(lag >= MS_PER_UPDATE) {
                update((float)elapsed);
                lag -= MS_PER_UPDATE;
            }
            //
            render(lag / MS_PER_UPDATE);
            repaint();
        }
    }
    //
    public void CloseApp() {
        this.dispatchEvent(
            new WindowEvent(this, WindowEvent.WINDOW_CLOSING)
        );
    }
    //
    public abstract void render(double FAS); // render loop that must be implemented
    public abstract void update(float delta); // logic loop that must be implemented
    //
    public class Panel extends JPanel {
        //
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            //camera.render(g);
            draw(g);
        }
        //
        public void draw(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawString("FPS: " + fps, 100, 100);
        }
    }
    /** sets the cam to be used for this window (the engine only supports one cam TOTAL, at the moment...) */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    //
    public Panel getPanel() {
        return panel;
    }
    // blank because its almost never used but can still be implemented
    @Override
    public void keyTyped(KeyEvent e) {}
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
