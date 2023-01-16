package com.hexaheximal.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.hexaheximal.game.Game;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument

public class DesktopLauncher {
	public static void main(String[] args) {
		boolean fullscreen = false;
		boolean mobile = false;

		for (String arg: args) {
			if (arg.equals("--fullscreen")) {
				fullscreen = true;
				continue;
			}

			if (arg.equals("--mobile")) {
				mobile = true;
				continue;
			}
		}

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Game");
		config.useVsync(true);

		if (fullscreen) {
			config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		} else {
			config.setWindowedMode(1920, 1080);
		}

		new Lwjgl3Application(new Game(System.getProperty("os.name"), "Unknown", mobile), config);
	}
}
