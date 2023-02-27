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
import com.hexaheximal.game.entity.Spaceship;
import com.hexaheximal.game.entity.Laser;
import com.hexaheximal.game.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import java.util.*;

public class SingleplayerGamemode extends Gamemode {
	public Texture starTexture;
	public Texture laserTexture;
	public Font font;
	public Random random;
	public OrthographicCamera camera;
	public List<Vector2> starPositions = new ArrayList<>();
	public List<Laser> lasers = new ArrayList<>();
	public int worldSize;
	public Sound laserSound;
	public int laserCooldown;
	public boolean fireLaser;
	public Spaceship spaceship;
	
	public SingleplayerGamemode(Game game) {
		super(game);

		this.spaceship = new Spaceship(this.game, 0, 0, 0, "Player");

		this.starTexture = new Texture("star.png");
		this.laserTexture = new Texture("laser.png");

		this.laserSound = Gdx.audio.newSound(Gdx.files.internal("laser.wav"));

		this.worldSize = 10000;
		
		this.font = new Font("Roboto-Regular.ttf", 50);
		
		this.random = new Random();
		
		for (int i = 0; i < 10000; i++) {
			int x = this.random.nextInt(this.worldSize * 2) - this.worldSize;
			int y = this.random.nextInt(this.worldSize * 2) - this.worldSize;

			if (800 > Math.sqrt((y - 0) * (y - 0) + (x - 0) * (x - 0))) {
				continue;
			}
			
			this.starPositions.add(new Vector2(x, y));
		}

		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.game.width, this.game.height);
	}

	public void reset() {
		this.spaceship.x = 0;
		this.spaceship.y = 0;
			
		this.spaceship.xvelocity = 0;
		this.spaceship.yvelocity = 0;
		this.spaceship.rotation = 0;
	}

	public void update() {
		boolean available = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
		
		if (available) {
			float yRot = Gdx.input.getAccelerometerY();
			this.spaceship.rotation += yRot;
		}
		
		if (!this.game.isMobile) {
			if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
				this.spaceship.acceleration = 1.0f;
			} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
				this.spaceship.acceleration = -1.0f;
			} else {
				this.spaceship.acceleration = 0.0f;
			}
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			this.spaceship.rotation -= 1.0f;
		}
		
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
				this.spaceship.rotation += 1.0f;
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.R)) {
				this.reset();
			}

			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				fireLaser = true;
			} else {
				fireLaser = false;
			}
		}

		this.spaceship.update();

		if (fireLaser) {
			if (this.laserCooldown != 0) {
				this.laserCooldown--;
			} else {
				this.laserCooldown = 4;
				this.laserSound.play(0.5f);

				double radians = Math.toRadians(this.spaceship.rotation);
			
				float laserX = this.spaceship.x;
				float laserY = this.spaceship.y;

				laserX += Math.sin(radians) * 128.0f;
				laserY += Math.cos(radians) * 128.0f;

				this.lasers.add(new Laser(this.game, laserX, laserY, this.spaceship.rotation, this.laserTexture));
			}
		}
		
		if (-this.worldSize > this.spaceship.x) {
			this.spaceship.x = -this.worldSize;
			this.spaceship.xvelocity = 8.0f;
		}
		
		if (this.spaceship.x > this.worldSize - this.spaceship.playerTexture.getWidth()) {
			this.spaceship.x = this.worldSize - this.spaceship.playerTexture.getWidth();
			this.spaceship.xvelocity = -8.0f;
		}
		
		if (-this.worldSize > this.spaceship.y) {
			this.spaceship.y = -this.worldSize;
			this.spaceship.yvelocity = 8.0f;
		}
		
		if (this.spaceship.y > this.worldSize - this.spaceship.playerTexture.getHeight()) {
			this.spaceship.y = this.worldSize - this.spaceship.playerTexture.getHeight();
			this.spaceship.yvelocity = -8.0f;
		}

		List<Laser> laserDeleteQueue = new ArrayList<>();

		for (Laser laser:lasers) {
			laser.update();

			if (laser.counter > 100) {
				laserDeleteQueue.add(laser);
			}
		}

		for (Laser laser:laserDeleteQueue) {
			lasers.remove(laser);
		}
	}

	public void render(SpriteBatch batch) {
		this.camera.position.set(this.spaceship.x, this.spaceship.y, 0);
		this.camera.update();

		batch.setProjectionMatrix(this.camera.combined);
		
		for (Vector2 starPosition:starPositions) {
			batch.draw(this.starTexture, starPosition.x, starPosition.y);
		}
		
		this.font.render(batch, 0, 512, "Welcome!", true);

		if (this.game.isMobile) {
			this.font.render(batch, 0, 512 - 60, "Tilt your device to rotate your spaceship,", true);
			this.font.render(batch, 0, 512 - 60 - 60, "And use the buttons at the bottom to accelerate!", true);
			this.font.render(batch, 0, 512 - 60 - 60 - 60, "You also have a laser - Tap the screen to fire it!", true);
			this.font.render(batch, 0, 512 - 60 - 60 - 60 - 60, "If you get lost, press the RESET button to respawn!", true);
		} else {
			this.font.render(batch, 0, 512 - 60, "Use A and D to rotate your spaceship,", true);
			this.font.render(batch, 0, 512 - 60 - 60, "And use W and S to accelerate!", true);
			this.font.render(batch, 0, 512 - 60 - 60 - 60, "You also have a laser - Press SPACE to fire it!", true);
			this.font.render(batch, 0, 512 - 60 - 60 - 60 - 60, "If you get lost, press R to respawn!", true);
		}

		for (Laser laser:lasers) {
			laser.render(batch);
		}

		this.spaceship.render(batch);
	}

	public void dispose() {
		this.starTexture.dispose();
		this.laserTexture.dispose();
		this.laserSound.dispose();
	}
}