package com.hexaheximal.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.*;

public class Laser {
	public Game game;
	public float x;
	public float y;
	public int counter;
	public float direction;
	public Texture laserTexture;
	
	public Laser(Game game, float x, float y, float direction, Texture laserTexture) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.counter = 0;
		this.laserTexture = laserTexture;
	}

	public void update() {
		double radians = Math.toRadians(this.direction);
			
		this.x += Math.sin(radians) * 16.0f;
		this.y += Math.cos(radians) * 16.0f;

		this.counter++;
	}

	public void render(SpriteBatch batch) {
		TextureRegion region = new TextureRegion(this.laserTexture, 0, 0, this.laserTexture.getWidth(), this.laserTexture.getHeight());
		batch.draw(region, this.x - (this.laserTexture.getWidth() / 2), this.y - (this.laserTexture.getHeight() / 2), (this.laserTexture.getWidth() / 2), (this.laserTexture.getHeight() / 2), this.laserTexture.getWidth(), this.laserTexture.getHeight(), 1f, 1f, -this.direction);
	}
}
