package com.hexaheximal.game;

import com.hexaheximal.game.gui.HudScreen;
import com.hexaheximal.game.gui.GuiScreen;
import com.hexaheximal.game.gui.MainMenuScreen;
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

	public GuiScreen gui;
	public HudScreen hud;
	
	public boolean hideGUI;

	public Music music;

	public boolean isMobile;
	
	public Game(String deviceType, String deviceName, boolean isMobile) {
		this.deviceType = deviceType;
		this.deviceName = deviceName;
		this.isMobile = isMobile;
	}
	
	public float acceleration;

	public SingleplayerGamemode gamemode;
	
	@Override
	public void create() {
		// This won't work if the screen gets resized, HOWEVER that should never happen in our case.
		
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
		
		this.guiCamera = new OrthographicCamera();
		this.guiCamera.setToOrtho(false, this.width, this.height);

		this.batch = new SpriteBatch();

		this.gamemode = new SingleplayerGamemode(this);
		this.hud = new HudScreen(this);
		
		this.gui = null;//new MainMenuScreen(this);

		this.music = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
		this.music.setLooping(true);
		this.music.play();

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		
		this.batch.begin();
		
		this.guiCamera.update();
		
		if (this.hideGUI) {
			this.gui = null;
		}
		
		if (this.gui != null) {
			this.gui.render(this.batch);
			this.batch.end();
			return;
		}
		
		this.gamemode.update();
		
		this.gamemode.render(this.batch);
		
		if (this.hud != null) {
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
		this.music.dispose();
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
