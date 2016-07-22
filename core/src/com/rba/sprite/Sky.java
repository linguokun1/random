package com.rba.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rba.Main.MainGame;

public class Sky {
	Texture tex1;
	TextureRegion region;
	
	public Sky(){
		tex1 = MainGame.manager.get("bg/bg_map01.png");
	
	}
	
	public void render(SpriteBatch batch){
		batch.begin();
		batch.draw(tex1, 0, 0);
		batch.end();
	}
}
