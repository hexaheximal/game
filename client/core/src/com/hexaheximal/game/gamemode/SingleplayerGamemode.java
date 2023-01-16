package com.hexaheximal.game.gamemode;

import com.hexaheximal.game.text.Font;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.InputProcessor;
import com.hexaheximal.game.gui.HudScreen;
import com.hexaheximal.game.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import java.util.*;

public class SingleplayerGamemode extends Gamemode {
	public Texture playerTexture;
	public Texture starTexture;
	
	float x;
	float y;
	
	float rotation;
	
	float xvelocity;
	float yvelocity;
	
	public Font font;
	
	public Random random;

	public float acceleration;

	public OrthographicCamera camera;
	
	public List<Vector2> starPositions = new ArrayList<>();

	public int worldSize;
	
	public SingleplayerGamemode(Game game) {
		super(game);

		this.playerTexture = new Texture("spaceship.png");
		this.starTexture = new Texture("star.png");
		
		this.x = 0;
		this.y = 0;

		this.worldSize = 10000;
		
		this.font = new Font("Roboto-Regular.ttf", 64);
		
		this.random = new Random();
		
		for (int i = 0; i < 10000; i++) {
			int x = this.random.nextInt(this.worldSize * 2) - this.worldSize;
			int y = this.random.nextInt(this.worldSize * 2) - this.worldSize;
			
			this.starPositions.add(new Vector2(x, y));
		}

		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.game.width, this.game.height);
	}

	public void reset() {
		this.x = 0;
		this.y = 0;
			
		this.xvelocity = 0;
		this.yvelocity = 0;
		this.rotation = 0;
	}

	public void update() {
		if (acceleration == 0.0f) {
			this.xvelocity *= 0.99;
			this.yvelocity *= 0.99;
		}

		boolean available = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
		
		if (available) {
			float yRot = Gdx.input.getAccelerometerY();
			this.rotation += yRot;
		}
		
		if (!this.game.isMobile) {
			if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
				acceleration = 1.0f;
			} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
				acceleration = -1.0f;
			} else {
				acceleration = 0.0f;
			}
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			this.rotation -= 1.0f;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			this.rotation += 1.0f;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.R)) {
			this.reset();
		}
		
		
		double radians = Math.toRadians(rotation);
			
		this.xvelocity += Math.sin(radians) * this.acceleration;
		this.yvelocity += Math.cos(radians) * this.acceleration;
		
		this.x += this.xvelocity;
		this.y += this.yvelocity;
		
		if (-this.worldSize > this.x) {
			this.x = -this.worldSize;
			this.xvelocity = 8.0f;
		}
		
		if (this.x > this.worldSize - this.playerTexture.getWidth()) {
			this.x = this.worldSize - this.playerTexture.getWidth();
			this.xvelocity = -8.0f;
		}
		
		if (-this.worldSize > this.y) {
			this.y = -this.worldSize;
			this.yvelocity = 8.0f;
		}
		
		if (this.y > this.worldSize - this.playerTexture.getHeight()) {
			this.y = this.worldSize - this.playerTexture.getHeight();
			this.yvelocity = -8.0f;
		}
		
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
	}

	public void render(SpriteBatch batch) {
		this.camera.position.set(this.x, this.y, 0);
		this.camera.update();

		batch.setProjectionMatrix(this.camera.combined);
		
		for (Vector2 starPosition:starPositions) {
			batch.draw(this.starTexture, starPosition.x, starPosition.y);
		}
		
		batch.draw(new TextureRegion(this.playerTexture, 0, 0, this.playerTexture.getWidth(), this.playerTexture.getHeight()), this.x - (this.playerTexture.getWidth() / 2), this.y - (this.playerTexture.getHeight() / 2), (this.playerTexture.getWidth() / 2), (this.playerTexture.getHeight() / 2), this.playerTexture.getWidth(), this.playerTexture.getHeight(), 1f, 1f, -rotation);

		//System.out.println("x: " + Float.toString(x) + ", y: " + Float.toString(y));
	}

	public void dispose() {
		this.playerTexture.dispose();
		this.starTexture.dispose();
	}
}