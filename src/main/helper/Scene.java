package com.app.helper;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

import com.app.Objects.Box;
import com.app.Objects.Ground;
import com.app.Player.Player;

public class Scene extends World {
    private List<GameObject> objects = new ArrayList<>();
    private Box box;
    private Ground ground;
    private int DELAY = 100;
    private Player player;
    
    public Scene() throws IOException {
        player = new Player(20, 0, 10, new ImageLoader("assets/Red.png", 65, 100).get_image());
        box = new Box(190, 320, 100, 100);
        ground = new Ground(0, 420, 700, 180);

        addObjectToWorld(box);
        this.add(player);

        new Timer(DELAY, (e -> {
            if (player.getVelocity().x > 0) {
                player.getVelocity().subtract(-1, 0);
            } else if (player.getVelocity().x < 0) {
                player.getVelocity().add(1, 0);
            }

            if (player.isColliding(ground.getRect()) || player.isColliding(box.getRect())) {
                if (player.getCollider().x == (int) box.getRect().getCenterX()) {
                    player.getVelocity().x = -player.getVelocity().x;
                }
                player.getVelocity().y = 0;
            } else {
                player.gravity();
            }
            player.setCollider(new Rectangle(player.getX() - 2, player.getY(), 68, 104));
        })).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ground.paint(g);
        for (GameObject object : objects) {
            object.paint(g);
        }
        player.paint(g);
    }

    public Player getPlayer() {
        return player;
    }

    public void addObjectToWorld(GameObject object) {
        objects.add(object);
    }
}