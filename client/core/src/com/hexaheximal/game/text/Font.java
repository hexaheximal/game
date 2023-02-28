package com.hexaheximal.game.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Align;

public class Font {
	public BitmapFont font;
	public int size;
	
	public Font(String filename, int size) {
		this.size = size;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(filename));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = this.size;
		parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|/?-+=()*&.:;,{}\"´`'<>";
		this.font = generator.generateFont(parameter);
		generator.dispose();
	}
	
	public void render(SpriteBatch batch, float x, float y, String text, boolean centered) {
		GlyphLayout glyphLayout = new GlyphLayout();
		glyphLayout.setText(this.font, text);
		float w = glyphLayout.width;
		float h = glyphLayout.height;
		if (centered) {
			this.font.draw(batch, glyphLayout, x - (w / 2), y - (h / 2));
		}
		else {
			this.font.draw(batch, glyphLayout, x, y);
		}
	}
}
