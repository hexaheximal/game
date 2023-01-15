package com.hexaheximal.game;

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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import java.util.*;

public class Game extends ApplicationAdapter implements InputProcessor {
	public OrthographicCamera camera;
	public OrthographicCamera guiCamera;
	public SpriteBatch batch;
	public Texture playerTexture;
	public Texture starTexture;
	public Music music;
	
	public int width;
	public int height;
	
	float x;
	float y;
	
	float rotation;
	
	float xvelocity;
	float yvelocity;
	
	public Font font;
	
	public String deviceType;
	public String deviceName;
	
	public Random random;
	
	public List<Vector2> starPositions = new ArrayList<>();
	
	public Game(String deviceType, String deviceName) {
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}
	
	public float acceleration;
	
	@Override
	public void create() {
		// This won't work if the screen gets resized, HOWEVER that should never happen in our case.
		
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
		
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.width, this.height);
		
		this.guiCamera = new OrthographicCamera();
		this.guiCamera.setToOrtho(false, this.width, this.height);

		this.batch = new SpriteBatch();
		this.playerTexture = new Texture("test.png");
		this.starTexture = new Texture("star.png");
		//this.music = Gdx.audio.newMusic(Gdx.files.internal("test.ogg"));
		
		//this.music.setLooping(true);
		//this.music.play();
		
		this.x = (width / 2.0f) - 64;
		this.y = (height / 2.0f) - 64;
		
		this.font = new Font("Roboto-Regular.ttf", 64);
		
		this.random = new Random();
		
		for (int i = 0; i < 1000; i++) {
			int x = this.random.nextInt(width * 4) - width;
			int y = this.random.nextInt(height * 4) - width;
			
			this.starPositions.add(new Vector2(x, y));
		}
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		// TODO: how would this work on the Steam Deck?
		
		boolean available = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
		
		if (available) {
			float yRot = Gdx.input.getAccelerometerY() * 1.5f;
			this.rotation += yRot;
		}
		
		/*if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			this.xvelocity -= 0.5f;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			this.xvelocity += 0.5f;
		}*/
		
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			acceleration = 1.0f;
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
			acceleration = -1.0f;
		} else if (this.deviceType != "Android") {
			acceleration = 0.0f;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			this.rotation -= 1.0f;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			this.rotation += 1.0f;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.R)) {
			this.x = (width / 2.0f) - 64;
			this.y = (height / 2.0f) - 64;
			
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
			//this.rotation = 90f;
		}
		
		if (this.x > this.width - 128) {
			this.x = this.width - 128;
			this.xvelocity = -8.0f;
			//this.rotation = -90f;
		}
		
		if (0 > this.y) {
			this.y = 0;
			this.yvelocity = 8.0f;
			//this.rotation = 0f;
		}
		
		if (this.y > this.height - 128) {
			this.y = this.height - 128;
			this.yvelocity = -8.0f;
			//this.rotation = 180f;
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
		
		this.camera.position.set(x, y, 0);
		this.camera.update();
		
		ScreenUtils.clear(0, 0, 0, 1);
		
		this.batch.begin();
		
		this.batch.setProjectionMatrix(this.camera.combined);
		
		for (Vector2 starPosition:starPositions) {
			this.batch.draw(this.starTexture, starPosition.x, starPosition.y);
		}
		
		//this.batch.draw(this.playerTexture, x - 64, y - 64);
		this.batch.draw(new TextureRegion(this.playerTexture, 0, 0, 128, 128), x - 64, y - 64, 64, 64, 128, 128, 1f, 1f, -rotation);
		
		this.batch.setProjectionMatrix(this.guiCamera.combined);
		
		this.font.render(batch, 32, height - 32, this.deviceType + " (" + this.deviceName + ")", false);
		
		//this.font.render(batch, 32, height - 150, Float.toString(this.x), false);
		//this.font.render(batch, 32, height - 230, Float.toString(this.y), false);
		
		this.batch.end();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (screenY > this.height / 2) {
			this.acceleration = -1.0f;
		} else {
			this.acceleration = 1.0f;
		}
		
		return true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		this.acceleration = 0.0f;
		return true;
	}
	
	@Override
	public void dispose() {
		this.batch.dispose();
		this.playerTexture.dispose();
	}
	
	@Override public boolean mouseMoved (int screenX, int screenY) {
		return false;
	}

	@Override public boolean touchDragged (int screenX, int screenY, int pointer) {
		if (screenY > this.height / 2) {
			this.acceleration = -1.0f;
		} else {
			this.acceleration = 1.0f;
		}
		
		return true;
	}

	@Override public void resize (int width, int height) {}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	
	@Override
	public boolean scrolled(float x, float y) {
		return false;
	}
}
