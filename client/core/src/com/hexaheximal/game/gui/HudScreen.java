package com.hexaheximal.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexaheximal.game.Game;
import com.hexaheximal.game.text.Font;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class HudScreen {
	public Game game;
	public Font font;

	public Texture pauseTexture;
	public Texture resetTexture;
	public Texture forwardTexture;
	public Texture backwardTexture;

	public HudScreen(Game game) {
		this.game = game;
		//this.font = new Font("Roboto-Regular.ttf", 64);

		this.pauseTexture = new Texture("pause.png");
		this.resetTexture = new Texture("reset.png");
		this.forwardTexture = new Texture("forward.png");
		this.backwardTexture = new Texture("backward.png");
	}

	public void render(SpriteBatch batch) {
		// Switch to the GUI camera

		batch.setProjectionMatrix(this.game.guiCamera.combined);

		// Render the HUD icons

		//this.font.render(batch, this.game.width / 2, this.game.height / 2, "Hello, World!", true);

		batch.draw(this.pauseTexture, 16, this.game.height - (this.pauseTexture.getHeight() + 16));
		batch.draw(this.resetTexture, 16 + this.pauseTexture.getWidth() + 16, this.game.height - (this.resetTexture.getHeight() + 16));
		batch.draw(this.forwardTexture, 16, 16);
		batch.draw(this.backwardTexture, this.game.width - (this.backwardTexture.getHeight() + 16), 16);
	}

	public void dispose() {
		this.pauseTexture.dispose();
		this.resetTexture.dispose();
		this.forwardTexture.dispose();
		this.backwardTexture.dispose();
	}

	public boolean handleTouchUp(int x, int y) {
		Rectangle pauseRect = new Rectangle(16, 16, this.resetTexture.getWidth(), this.resetTexture.getHeight());
		Rectangle resetRect = new Rectangle(16 + this.pauseTexture.getWidth() + 16, 16, this.resetTexture.getWidth(), this.resetTexture.getHeight());
		Rectangle forwardRect = new Rectangle(16, this.game.height - (this.forwardTexture.getHeight() + 16), this.forwardTexture.getWidth(), this.forwardTexture.getHeight());
		Rectangle backwardRect = new Rectangle(this.game.width - (this.backwardTexture.getWidth() + 16), this.game.height - (this.backwardTexture.getHeight() + 16), this.backwardTexture.getWidth(), this.backwardTexture.getHeight());

		if (forwardRect.contains(x, y) || backwardRect.contains(x, y)) {
			this.game.gamemode.acceleration = 0.0f;
			return true;
		}

		this.game.gamemode.fireLaser = false;

		return true;
	}

	public boolean handleTouchDown(int x, int y) {
		Rectangle pauseRect = new Rectangle(16, 16, this.resetTexture.getWidth(), this.resetTexture.getHeight());
		Rectangle resetRect = new Rectangle(16 + this.pauseTexture.getWidth() + 16, 16, this.resetTexture.getWidth(), this.resetTexture.getHeight());
		Rectangle forwardRect = new Rectangle(16, this.game.height - (this.forwardTexture.getHeight() + 16), this.forwardTexture.getWidth(), this.forwardTexture.getHeight());
		Rectangle backwardRect = new Rectangle(this.game.width - (this.backwardTexture.getWidth() + 16), this.game.height - (this.backwardTexture.getHeight() + 16), this.backwardTexture.getWidth(), this.backwardTexture.getHeight());

		if (pauseRect.contains(x, y)) {
			return true;
		}

		if (resetRect.contains(x, y)) {
			this.game.gamemode.reset();
			return true;
		}

		if (forwardRect.contains(x, y)) {
			this.game.gamemode.acceleration = 1.0f;
			return true;
		}

		if (backwardRect.contains(x, y)) {
			this.game.gamemode.acceleration = -1.0f;
			return true;
		}

		this.game.gamemode.fireLaser = true;

		return true;
	}
}