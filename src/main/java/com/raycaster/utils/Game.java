package com.raycaster.utils;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import com.raycaster.Entities.Player;
import com.raycaster.Renderer.Screen;
import com.raycaster.Renderer.Viewport3D;
import com.raycaster.Tile.ITileMap;
import com.raycaster.Tile.World;
/**
 * @author Squareys
 */
public class Game implements IGame, Runnable {
	private static final long serialVersionUID = -3419294756259121818L;

	private Screen screen;
	
	private boolean running;
	private Thread thread;
	
	private IWorld m_world;
	private Player m_player;
	
	public Game(ITileMap map){
		this(map, 640, 480);
	}
	
	public Game(ITileMap tileMap, int windowWidth, int windowHeight) {
		m_world = new World(tileMap);
		
		m_player = new Player(tileMap.getWidth() >> 1, tileMap.getHeight() >> 1, 10, 10, null, m_world);
		
		screen = new Viewport3D(m_world, m_player, windowWidth, windowHeight);
		running = true;
	}
	
	private void update(float timeDiff){
		if(w) {
			m_player.setLocalMovementDir(Player.DIR_FORWARD);
		} else if(s) {
			m_player.setLocalMovementDir(Player.DIR_BACKWARD);
		} else {
			m_player.setMovementDir(0.0f, 0.0f);
		}
		
		if(a) {
			m_player.setSpin(-1);
		} else if(d) {
			m_player.setSpin(1);
		} else {
			m_player.setSpin(0);
		}
		
		m_player.onUpdate(timeDiff);
	}
	
	@Override
	public void play() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void quit() {
		throw new RuntimeException("Unimplemented Method!");
	}

	@Override
	public Screen getCurrentScreen() {
		return screen;
	}

	@Override
	public void showScreen(Screen screen) {
		this.screen = screen;
	}
	
	public synchronized void start() {
		if (running) return;
		running = true;
	}

	public synchronized void stop() {
		if (!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		
		/* gamelogic updates per second */
		final int ticksPerSecond = 150;
		final double secondsPerTick = 1 / (float) ticksPerSecond;
		int tickCount = 0;
		long now, passedTime;
		boolean ticked;

		while(running) {
			now = System.nanoTime();
			passedTime = now - lastTime;
			lastTime = now;
			
			if(passedTime < 0) passedTime = 0;
			if(passedTime > 100000000) passedTime = 100000000;

			unprocessedSeconds += passedTime / 1000000000.0;

			ticked = false;
			while (unprocessedSeconds > secondsPerTick) {
				update((float)secondsPerTick);
				
				unprocessedSeconds -= secondsPerTick;
				ticked = true;

				tickCount++;
			}
			
			if(ticked) {
				screen.present();
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private boolean w = false;
	private boolean a = false;
	private boolean s = false;
	private boolean d = false;

	public boolean getW() {
		return w;
	}

	public boolean getA() {
		return w;
	}

	public boolean getS() {
		return w;
	}

	public boolean getD() {
		return w;
	}

	public void setW(boolean w) {
		this.w = w;
	}

	public void setA(boolean a) {
		this.a = a;
	}

	public void setS(boolean s) {
		this.s = s;
	}

	public void setD(boolean d) {
		this.d = d;
	}

}
