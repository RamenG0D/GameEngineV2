package com.raycaster.utils;

import com.raycaster.Renderer.Screen;

public interface IGame {

	public void play();

	public void quit();

	public Screen getCurrentScreen();

	public void showScreen(Screen screen);

}
