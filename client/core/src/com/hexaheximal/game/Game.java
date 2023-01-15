package com.hexaheximal.game;

import com.hexaheximal.game.gui.HudScreen;
import com.hexaheximal.game.gui.GuiScreen;
import com.hexaheximal.game.gamemode.Gamemode;
import com.hexaheximal.game.gamemode.SingleplayerGamemode;
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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import java.util.*;

public class Game extends ApplicationAdapter implements InputProcessor {
	public OrthographicCamera guiCamera;
	public SpriteBatch batch;
	
	public int width;
	public int height;
	
	public String deviceType;
	public String deviceName;

	public HudScreen hud;

	public Music music;

	public boolean isMobile;
	
	public Game(String deviceType, String deviceName, boolean isMobile) {
		this.deviceType = deviceType;
		this.deviceName = deviceName;
		this.isMobile = isMobile;
	}
	
	public float acceleration;

	public SingleplayerGamemode gamemode;

	public boolean showSmallerImages;
	
	@Override
	public void create() {
		// This won't work if the screen gets resized, HOWEVER that should never happen in our case.
		
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();

		if (1920 > this.width && 1080 > this.height) {
			this.showSmallerImages = true;
		} else {
			this.showSmallerImages = false;
		}
		
		this.guiCamera = new OrthographicCamera();
		this.guiCamera.setToOrtho(false, this.width, this.height);

		this.batch = new SpriteBatch();

		this.gamemode = new SingleplayerGamemode(this);
		this.hud = new HudScreen(this);

		this.music = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
		this.music.setLooping(true);
		this.music.play();

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		this.guiCamera.update();
		this.gamemode.update();

		ScreenUtils.clear(0, 0, 0, 1);
		
		this.batch.begin();
		
		this.gamemode.render(this.batch);
		
		if (this.hud != null && isMobile) {
			this.hud.render(this.batch);
		}
		
		this.batch.end();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (this.hud != null) {
			return this.hud.handleTouchDown(screenX, screenY);
		}
		
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (this.hud != null) {
			return this.hud.handleTouchUp(screenX, screenY);
		}

		return false;
	}
	
	@Override
	public void dispose() {
		this.batch.dispose();
		this.gamemode.dispose();
	}
	
	@Override public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override public void resize(int width, int height) {}

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
