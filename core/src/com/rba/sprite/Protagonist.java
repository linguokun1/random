package com.rba.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.rba.Main.MainGame;
import com.rba.handle.Constant;

/**加载动画需要用到的图片作为纹理对象,创建纹理区域,放入setAnimation函数*/
public class Protagonist extends GameSprite {
	Texture tex1,tex2,tex3;
	int starsCoumt;
	int allStarsCoumt;

//	public static float x;
//	public static float y;
	public static float statetime;

	public Protagonist(Body body){
		// TODO Auto-generated constructor stub
		super(body);
		
		tex1 = MainGame.manager.get("sprite/catline1.png", Texture.class);
		tex2 = MainGame.manager.get("sprite/catline2.png", Texture.class);
		tex3 = MainGame.manager.get("sprite/catline3.png", Texture.class);
		
		TextureRegion[][] region1 = TextureRegion.split(tex1, tex1.getWidth()/2, 80);
		TextureRegion[][] region2 = TextureRegion.split(tex2, tex2.getWidth(), 80);
		TextureRegion[][] region3 = TextureRegion.split(tex3, tex3.getWidth()/2, 80);
		
		TextureRegion[] region = new TextureRegion[5];
		//region纹理区域数组装下5个动作
		region[0]=region3[0][1];
		region[1]=region3[0][0];
		region[2]=region2[0][0];
		region[3]=region1[0][0];
		region[4]=region1[0][1];
		//把数组放入动画
		setAnimation(1/12f, region);
	}

	public void collectStars(){
		starsCoumt++;
	}
	
	public int getStarsCoumt() {
		return starsCoumt;
	}

	public void setStarsCoumt(int starsCoumt) {
		this.starsCoumt = starsCoumt;
	}

	public int getAllStarsCoumt() {
		return allStarsCoumt;
	}

	public void setAllStarsCoumt(int allStarsCoumt) {
		this.allStarsCoumt = allStarsCoumt;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
//		super.draw(batch, parentAlpha);
		statetime += Gdx.graphics.getDeltaTime();
		TextureRegion currentFrame = animation.getKeyFrame(statetime, true);
		//每次移动到什么位置: 当前位置乘以现实世界比率 - 纹理区域第一张图片的宽度的一半
		//这是因为刚体的坐标在刚体的正中间
		float x = body.getPosition().x * Constant.RATE - width / 2;// - width / 2;
		float y = body.getPosition().y * Constant.RATE - height / 2;//
//		this.setPosition(x , y);
		batch.draw(currentFrame, x, y);
//		System.out.println("body的x = "+this.getBody().getPosition().x * Constant.RATE);
//		System.out.println("主角的x = "+this.getX());

	}
	@Override
	public void act(float delta) {
//		super.act(delta);
	}

	@Override
	public void update(float dt) {
//		super.update(dt);

	}
//	public void update(){
//		this.x += 1.5f;
//	}

}
