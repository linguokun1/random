package com.rba.Main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rba.Screen.MainScreen;
import com.rba.Screen.PlayScreen;


public class MainGame extends Game {

	public static final String TAG = "GameTest";

	public static final String Title = "GameTest";

	public static int Viewport_Width = 1920; //640

	public static int Viewport_Height = 1080; //480

	int Scale = 2; //

	private SpriteBatch batch;//用于打包纹理
	private OrthographicCamera camera; //
	private OrthographicCamera camera_UI;  //

//	public static final float STEP = 1 / 60f; //

	public static AssetManager manager; //

	public MainScreen mainScreen;
	public PlayScreen playScreen;

	@Override
	public void create() {

		manager = new AssetManager();
		load();
		
		batch = new SpriteBatch();//精灵画笔

		camera = new OrthographicCamera();//创建正交照相机

		//指定镜头的方向和宽高 位置居于正中 y轴指向上   宽的像素       高的像素
		camera.setToOrtho(false, Viewport_Width, Viewport_Height);

		camera_UI = new OrthographicCamera();//为什么又要创建多一个镜头
		camera_UI.setToOrtho(false, Viewport_Width, Viewport_Height);
		
		mainScreen = new MainScreen(this);//创建主屏幕类对象
		
		playScreen = new PlayScreen(this);//创建玩家屏幕类对象
		
		setScreen(mainScreen);//设置当前屏幕playScreen
		
	}
	/**加载各种图片*/
	public void load() {

		manager.load("mainScreen_img/main_bg.png", Texture.class);
		manager.load("mainScreen_img/btn_play.png", Texture.class);
		//
//		manager.load("maps/select.pack", TextureAtlas.class);
		manager.load("maps/level_select_panel.png", Texture.class);
		manager.load("maps/level_select_bg.png", Texture.class);
		//
		manager.load("bg/bg_map01.png", Texture.class);
		//
		manager.load("sprite/catline1.png", Texture.class);
		manager.load("sprite/catline2.png", Texture.class);
		manager.load("sprite/catline3.png", Texture.class);
		//
		manager.load("sprite/star.png", Texture.class);
		//
		manager.load("monster/monster_box.png", Texture.class);
		//
		manager.load("music/BG_music.mp3", Music.class);
		manager.load("sound/jump.mp3", Sound.class);
		manager.load("sound/contact.wav", Sound.class);
		manager.load("sound/diamond.ogg", Sound.class);
		manager.load("sound/select.wav", Sound.class);
		manager.load("sound/switch.wav", Sound.class);

		manager.load("paoku/background1.png", Texture.class);
		manager.load("paoku/background2.png", Texture.class);
		manager.load("paoku/route1.png", Texture.class);
		manager.load("paoku/route2.png", Texture.class);

		manager.load("paoku/cloud1.png", Texture.class);

		manager.load("paoku/farscene1.png", Texture.class);
		manager.load("paoku/farscene2.png", Texture.class);
//		manager.load("sound/", Sound.class);
		
		//
		manager.finishLoading();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public OrthographicCamera getCameraUI() {
		return camera_UI;
	}

}
