package com.hexaheximal.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hexaheximal.game.Game;

public class Spaceship extends Entity {
	public Game game;
	public float x;
	public float y;
	public float xvelocity;
	public float yvelocity;
	public float rotation;
	public float acceleration;
	public String name;

	public Texture playerTexture;
	public TextureRegion region;

	public float health;
	public float maxHealth;

	public Spaceship(Game game, float x, float y, float rotation, float health, float maxHealth, String name) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.health = health;
		this.maxHealth = maxHealth;
		this.name = name;

		this.playerTexture = new Texture("spaceship.png");
		this.region = new TextureRegion(this.playerTexture, 0, 0, this.playerTexture.getWidth(), this.playerTexture.getHeight());
	}

	public void update() {
		if (acceleration == 0.0f) {
			this.xvelocity *= 0.99;
			this.yvelocity *= 0.99;
		}

		double radians = Math.toRadians(this.rotation);
			
		this.xvelocity += Math.sin(radians) * this.acceleration;
		this.yvelocity += Math.cos(radians) * this.acceleration;

		if (this.xvelocity > 32) {
			this.xvelocity = 32;
		}
		
		if (-32 > this.xvelocity) {
			this.xvelocity = -32;
		}
		
		if (this.yvelocity > 32) {
			this.yvelocity = 32;
		}
		
		if (-32 > this.yvelocity) {
			this.yvelocity = -32;
		}
		
		this.x += this.xvelocity;
		this.y += this.yvelocity;
	}

	public void render(SpriteBatch batch) {
		batch.draw(this.region, this.x - (this.playerTexture.getWidth() / 2), this.y - (this.playerTexture.getHeight() / 2), (this.playerTexture.getWidth() / 2), (this.playerTexture.getHeight() / 2), this.playerTexture.getWidth(), this.playerTexture.getHeight(), 1f, 1f, -this.rotation);
	}

	public void dispose() {
		this.playerTexture.dispose();
	}
}
