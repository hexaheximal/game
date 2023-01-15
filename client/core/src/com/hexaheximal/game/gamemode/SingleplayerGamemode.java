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
	
	public SingleplayerGamemode(Game game) {
		super(game);

		this.playerTexture = new Texture("test.png");
		this.starTexture = new Texture("star.png");
		
		this.x = (this.game.width / 2.0f) - 64;
		this.y = (this.game.height / 2.0f) - 64;
		
		this.font = new Font("Roboto-Regular.ttf", 64);
		
		this.random = new Random();
		
		for (int i = 0; i < 1000; i++) {
			int x = this.random.nextInt(this.game.width * 4) - this.game.width;
			int y = this.random.nextInt(this.game.height * 4) - this.game.width;
			
			this.starPositions.add(new Vector2(x, y));
		}

		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.game.width, this.game.height);
	}

	public void update() {
		boolean available = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
		
		if (available) {
			float yRot = Gdx.input.getAccelerometerY() * 1.5f;
			this.rotation += yRot;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			acceleration = 1.0f;
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
			acceleration = -1.0f;
		} else if (this.game.deviceType != "Android") {
			acceleration = 0.0f;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			this.rotation -= 1.0f;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			this.rotation += 1.0f;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.R)) {
			this.x = (this.game.width / 2.0f) - 64;
			this.y = (this.game.height / 2.0f) - 64;
			
			this.xvelocity = 0;
			this.yvelocity = 0;
			this.rotation = 0;
		}
		
		
		double radians = Math.toRadians(rotation);
			
		this.xvelocity += Math.sin(radians) * this.acceleration;
		this.yvelocity += Math.cos(radians) * this.acceleration;
		
		this.x += this.xvelocity;
		this.y += this.yvelocity;
		
		if (0 > this.x) {
			this.x = 0;
			this.xvelocity = 8.0f;
		}
		
		if (this.x > this.game.width - 128) {
			this.x = this.game.width - 128;
			this.xvelocity = -8.0f;
		}
		
		if (0 > this.y) {
			this.y = 0;
			this.yvelocity = 8.0f;
		}
		
		if (this.y > this.game.height - 128) {
			this.y = this.game.height - 128;
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
		
		batch.draw(new TextureRegion(this.playerTexture, 0, 0, 128, 128), this.x - 64, this.y - 64, 64, 64, 128, 128, 1f, 1f, -rotation);
		
		batch.setProjectionMatrix(this.game.guiCamera.combined);
		
		this.font.render(batch, 32, this.game.height - 32, this.game.deviceType + " (" + this.game.deviceName + ")", false);
	}

	public void dispose() {
		this.playerTexture.dispose();
		this.starTexture.dispose();
	}
}