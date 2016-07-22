package com.rba.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.rba.Main.MainGame;
/**加载动画需要用到的图片作为纹理对象,创建纹理区域,放入setAnimation函数*/
public class MonsterBox extends GameSprite {
	Texture tex;
	
	public MonsterBox(Body body) {
		// TODO Auto-generated constructor stub
		super(body);
		
		tex=MainGame.manager.get("monster/monster_box.png", Texture.class);
		TextureRegion[] region= TextureRegion.split(tex, 50, 47)[0];
		setAnimation(1/8f, region);
		
		width = region[0].getRegionWidth();
		height = region[0].getRegionHeight();
	}

}
