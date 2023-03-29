package com.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.awt.Graphics;
import javax.swing.JFrame;

import com.app.Menus.Menu;
import com.app.Menus.MainMenu;
import com.app.helper.Scene;
import com.app.helper.State;

public class App extends JFrame implements KeyListener {
    private State stateMgr;
    private Scene scene;
    private Menu menu;
    //
    public static void main(String[] args) throws IOException {
        new App();
    }
    /**
     * creates a new App which will have a window and also creates other game related things such as the 
     * {@code KeyListener} and Objects like the {@code Player} and {@code StateManager}
     * @throws IOException
     */
    public App() throws IOException {
        startupConfig();
        //
        this.add(menu);
        this.add(scene);

        this.setVisible(true);
    }
    /**
     * sets up most of the windows configurations such as position, width, height, etc
     * @throws IOException
     */
    public void startupConfig() throws IOException {
        this.setTitle("App");
        this.setSize(720, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(this);
        //
        stateMgr = new State();
        stateMgr.state = State.gameState.Menu;
        menu = new MainMenu();
        scene = new Scene();
        //
    }
    // does key inputs for the window / same as an inputHandler
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (stateMgr.state != State.gameState.Game) {
            return;
        }
        if (keyCode == KeyEvent.VK_D) {
            scene.getPlayer().x += scene.getPlayer().getVelocity().x + 1;
            scene.getPlayer().getVelocity().add(1, 0);
        } else if (keyCode == KeyEvent.VK_A) {
            scene.getPlayer().x -= scene.getPlayer().getVelocity().x + 1;
            scene.getPlayer().getVelocity().subtract(1, 0);
        }
    }
    /**
     * paints to the window and also conditionally switchs to whatever 
     * Menu, or World/Scene, it should draw based on the current state
     */
    public void paint(Graphics g) {
        super.paint(g);
        //
        this.repaint();
        if(stateMgr.state == State.gameState.Menu) scene.setVisible(false);
        //
        if(stateMgr.state == State.gameState.Game) return;
        
        //
    }
    //
    @Override
    public void keyReleased(KeyEvent e) {
    }
    //
    @Override
    public void keyTyped(KeyEvent e) {
    }
}