package com.rba.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.rba.Main.MainGame;

public class Star extends GameSprite {
	Texture tex;
	
	public Star(Body body) {
		super(body);
		// TODO Auto-generated constructor stub
		tex=MainGame.manager.get("sprite/star.png", Texture.class);
		
		TextureRegion[] region = TextureRegion.split(tex, tex.getWidth()/6, tex.getHeight())[0];
		
		setAnimation(1/12f, region);
	}
	
}
