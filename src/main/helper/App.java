package helper;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Renders.Camera;
import Renders.Camera2D;

public class App extends JFrame {
    private Panel panel = new Panel();
    private World world;
    /* 
     * camera clsses are kinda just a funnel where we assemble the graphics data 
     * to what wthe camera wants(3d vs 2d), then the camera hands the result back
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
        //
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setTitle("Application"); // default Title
        panel.setSize(getWidth(), getWidth());
        this.add(panel);
        //
        this.camera = new Camera2D(0, 0, getWidth() - 1, getHeight() - 1);
        world = new World();
        this.setVisible(true);
        repaint();
        //
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
        this.setTitle(title);
        //
        panel.setSize(getWidth(), getHeight());
        this.add(panel);
        //
        this.camera = camera;
        this.setVisible(true);
        repaint();
        //
    }
    //
    class Panel extends JPanel {
        //
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            //
            camera.render(g);
        }
        //
    }
    /** sets the cam to be used for this window (the angine only supports one cam TOTAL, at the moment) */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    //
    /*public void setKeyListener(KeyListener kl) {
        this.addKeyListener(kl);
    }
    //
    public void setMouseListener(MouseListener ml) {
        this.addMouseListener(ml);
    }*/
    //
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //
        if(!camera.ViewPortAlreadySet()) camera.setViewPortSize(getWidth(), getHeight());
        panel.repaint();
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
