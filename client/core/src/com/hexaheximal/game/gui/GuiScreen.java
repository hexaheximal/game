package com.hexaheximal.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexaheximal.game.Game;
import com.hexaheximal.game.text.Font;

public class GuiScreen {
	public Game game;
	public Font font;

	public GuiScreen(Game game) {
		this.game = game;
		this.font = new Font("Roboto-Regular.ttf", 64);
	}

	public void render(SpriteBatch batch) {
		// Switch to the GUI camera

		batch.setProjectionMatrix(this.game.guiCamera.combined);

		// Render the example text

		//this.font.render(batch, this.game.width / 2, this.game.height / 2, "Hello, World!", true);
	}

	public void dispose() {}
}