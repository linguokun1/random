package com.rba.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rba.Main.MainGame;
/**
 *
 * 这个基类做了以下这些:
 * 获取了纹理打包对象batch
 * 获取了镜头对象camera
 * 获取了镜头对象camera_UI
 * 获取了MainGame游戏对象
 * */
public abstract class RunerScreen implements Screen{
	
	protected SpriteBatch batch;
	protected OrthographicCamera camera; //
	protected OrthographicCamera camera_UI;//
	
	MainGame mgame;
	public RunerScreen(MainGame mgame) {
		this.mgame = mgame;
	}
	
	@Override
	public void render(float delta) {
	}
	
	@Override
	public void resize(int width, int height) {
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setCatchBackKey(true);			//设置手机的返回键是否有效
		batch = mgame.getBatch();
		camera = mgame.getCamera();
		camera_UI = mgame.getCameraUI();
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	
	
}
