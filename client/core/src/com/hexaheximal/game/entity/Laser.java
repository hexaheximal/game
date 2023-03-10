package com.hexaheximal.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexaheximal.game.Game;
import java.util.*;

public class Laser extends Entity {
	public Game game;
	public float x;
	public float y;
	public int counter;
	public float direction;
	public Texture laserTexture;
	public TextureRegion region;
	
	public Laser(Game game, float x, float y, float direction, Texture laserTexture) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.counter = 0;
		this.laserTexture = laserTexture;
		this.region = new TextureRegion(this.laserTexture, 0, 0, this.laserTexture.getWidth(), this.laserTexture.getHeight());
	}

	public void update() {
		double radians = Math.toRadians(this.direction);
			
		this.x += Math.sin(radians) * 64.0f;
		this.y += Math.cos(radians) * 64.0f;

		this.counter++;
	}

	public void render(SpriteBatch batch) {
		batch.draw(this.region, this.x - (this.laserTexture.getWidth() / 2), this.y - (this.laserTexture.getHeight() / 2), (this.laserTexture.getWidth() / 2), (this.laserTexture.getHeight() / 2), this.laserTexture.getWidth(), this.laserTexture.getHeight(), 1f, 1f, -this.direction);
	}
}
