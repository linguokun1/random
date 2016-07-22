package com.rba.sprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rba.handle.Constant;
/**覆写每个精灵都要调用的方法,例如渲染,流程都是一样的,都是获取当前时间的关键帧,用batch类根据帧图片的宽度和高度进行绘制*/
public class GameSprite extends Actor {

	protected Body body;
	protected Animation animation;
	float width, height;

	public GameSprite(Body body) {
		this.body = body;
	}

	public void setAnimation(float delay, TextureRegion[] reg) {
		animation = new Animation(delay, reg); // delay frameDuration֡
												// reg keyframe

		width = reg[0].getRegionWidth(); //
		height = reg[0].getRegionHeight(); //

	}

	public void render(SpriteBatch batch, float dt) {
		//每次渲染会有一个时间差,也就是每次刷新画面时间隔的时间,把这个时间差累加,就知道当前累计时间所对应的关键帧.
		TextureRegion currentFrame = animation.getKeyFrame(dt, true);
		//每次移动到什么位置: 当前位置乘以现实世界比率 - 纹理区域第一张图片的宽度的一半
		//这是因为刚体的坐标在刚体的正中间
		float x = body.getPosition().x * Constant.RATE - width / 2;
		float y = body.getPosition().y * Constant.RATE - height / 2;
		batch.begin();
		batch.draw(currentFrame, x, y);

		batch.end();
	}

	public void update(float dt) {
	}
	
	public Body getBody() {
		return body;
	}

	public Vector2 getPosition() {
		return body.getPosition();
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}


}
