package com.hexaheximal.game.gamemode;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexaheximal.game.Game;

public class Gamemode {
	public Game game;

	public Gamemode(Game game) {
		this.game = game;
	}

	public void update() {}
	public void render(SpriteBatch batch) {}
	public void dispose() {}
}