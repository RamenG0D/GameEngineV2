package helper;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Renders.Camera;

public class App extends JFrame {
    private JPanel panel = new JPanel();
    private MouseListener ml;
    private KeyListener kl;
    private World world;
    /* 
     * camera clsses are kinda just a funnel where we assemble the graphics data 
     * to what wthe cmaera wants(3d vs 2d), then the camera hands the result back
     * to the app to be drawn to the panel
     */
    private Camera camera;
    /**
     * the initialized gameState enum for use and modification/checking
     */
    public ApplicationState state = ApplicationState.Running;
    /**
     * <h3>A class to define window and application behavior</h3>
     * creates a default window and graphics context
     */
    public App() {
        world = new World();
        //
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setTitle("Application"); // default Title
        panel.setSize(400, 400);
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
    public App(String title, int width, int height, KeyListener kl, MouseListener ml) {
        //
        this.setSize(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle(title);
        //
        this.addKeyListener(kl);
        this.addMouseListener(ml);
        panel.setSize(width, height);
        this.add(panel);
        //
        this.setVisible(true);
        this.repaint();
        //
    }
    //
    @Override
    public void setResizable(boolean visible) {
        this.setResizable(visible);
    }
    /** sets the cam to be used for this window (the angine only supports one cam TOTAL, at the moment) */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    //
    public void setKeyListener(KeyListener kl) {
        this.kl = kl;
    }
    //
    public void setMouseListener(MouseListener ml) {
        this.ml = ml;
    }
    //
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //
        panel.repaint();
    }
    /** The Apps Graphics Context. It also has double buffering by default. */
    public Graphics getPanelGraphics() {
        return panel.getGraphics();
    }
    /**
     *  a general purpose state Manager to facilitate changes in the game such
     *  as menu to 1st level to pause menu, yep its pretty simple
     */
    public static enum ApplicationState {
        // app states
        Running,
        Closing
    }
}
