package com.hexaheximal.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexaheximal.game.Game;
import com.hexaheximal.game.text.Font;

public class MainMenuScreen extends GuiScreen {
	public int counter;
	
	public MainMenuScreen(Game game) {
		super(game);
	}

	public void render(SpriteBatch batch) {
		if (this.counter >= 60 * 10) {
			this.game.hideGUI = true;
			return;
		}
		
		// Switch to the GUI camera

		batch.setProjectionMatrix(this.game.guiCamera.combined);

		// Render the example text

		this.font.render(batch, this.game.width / 2, this.game.height / 2, "test", true);
		
		this.counter++;
		
		this.game.hideGUI = false;
	}

	public void dispose() {}
}
