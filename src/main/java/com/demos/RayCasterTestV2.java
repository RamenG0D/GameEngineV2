package com.demos;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.net.MalformedURLException;

import com.Application.App;
import com.Renders.Screen;
import com.raycaster.Bitmap.IBitmap;
import com.raycaster.Tile.TileMap;
import com.raycaster.utils.Game;
import com.raycaster.utils.ResourceManager;

public class RayCasterTestV2 extends App {
	
	public RayCasterTestV2(String title, int width, int height, int desiredFps, Color color, Screen screen) {
		super(title, width, height, desiredFps, null, color, screen);

		IBitmap<Integer> bmp = null;
		try {
			bmp = ResourceManager.getInstance().loadBitmap(new File("AppData/assets/CheepTexture.png").toURI().toURL());
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
		if(bmp != null) ResourceManager.getInstance().createTexture(bmp);

		IBitmap<Integer> bmp1 = null;
		try {
			bmp1 = ResourceManager.getInstance().loadBitmap(new File("AppData/assets/CrappyHiresTexture.png").toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if(bmp1 != null) ResourceManager.getInstance().createTexture(bmp1);
		
		IBitmap<Integer> floorTex = null;
		try {
			floorTex = ResourceManager.getInstance().loadBitmap(new File("AppData/assets/Grass.png").toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if(floorTex != null) ResourceManager.getInstance().createTexture(floorTex);

		TileMap map = new TileMap(40, 40, true);
		map.autoGenerate();
		game = new Game(map, 800, 600);
		game.play();
	}

	private Game game;

	public static void main(String[] args){
		new RayCasterTestV2("RayCasterV2", 800, 600, 60, Color.BLACK, null).run();
	}

	@Override
	public void deprecatedGraphics(Graphics g) {}

	@Override
	public void update(float delta) {}

	@Override
	public void render() {
		getScreen().setImage(game.getCurrentScreen().getImage());
	}

	@Override
	public void input() {
		if(keypressed("w")) {
			game.setW(true);
		} else {
			game.setW(false);
		}
		if(keypressed("a")) {
			game.setA(true);
		} else {
			game.setA(false);
		}
		if(keypressed("s")) {
			game.setS(true);
		} else {
			game.setS(false);
		}
		if(keypressed("d")) {
			game.setD(true);
		} else {
			game.setD(false);
		}
	}
}
