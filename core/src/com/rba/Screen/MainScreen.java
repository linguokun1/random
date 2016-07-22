package com.rba.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.rba.Main.MainGame;

import static com.rba.Main.MainGame.Viewport_Height;
import static com.rba.Main.MainGame.Viewport_Width;
import static com.rba.Main.MainGame.manager;
/**游戏开始界面*/
public class MainScreen extends RunerScreen {
	private Stage stage;
	private StretchViewport stretchViewport;

	private TextureAtlas atlas;
	private Image[] images;
	private Image image_start;
	private Image image_panel, image_BG;
								//开场纹理
	private Texture panel, BG, startTexture,tex;

	private Texture tex_play;
	private TextureRegion[][] reg_play;
	private TextureRegionDrawable texRegDra_play_up, texRegDra_play_down;
	private ImageButton imgBtn_play;

	public static boolean Debug = true; // 显示开始游戏还是显示关卡

	public MainScreen(MainGame mgame) {
		// TODO Auto-generated constructor stub
		super(mgame);
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		//屏幕适配类 不保持高宽比例，将世界尺寸缩放到屏幕尺寸，但是有时候游戏元素会变形 640 480
		stretchViewport = new StretchViewport(Viewport_Width, Viewport_Height);
		//包含拥有层次结构的一个二维场景，场景中有许多演员。处理视图和分配的输入事件。舞台负责操作视角，和处理分配输入事件。
		stage = new Stage(stretchViewport);

		startTexture = manager.get("mainScreen_img/main_bg.png");
		image_start = new Image(startTexture);
		// play
		tex_play = manager.get("mainScreen_img/btn_play.png");//开始按钮纹理
		//纹理区域 截取这张纹理,以高度的一般剪开,返回一个二维数组
		reg_play = TextureRegion.split(tex_play, tex_play.getWidth(), tex_play.getHeight() / 2);
		//上半边纹理
		texRegDra_play_up = new TextureRegionDrawable(reg_play[1][0]);
		//下半边纹理
		texRegDra_play_down = new TextureRegionDrawable(reg_play[0][0]);
		//把纹理设置到按钮里
		imgBtn_play = new ImageButton(texRegDra_play_up, texRegDra_play_down);
		//play按钮所在的位置 这里其实就是放在屏幕正中间
		float btn_play_x = (Viewport_Width / 2) - (imgBtn_play.getWidth() / 2);
		float btn_play_y = (Viewport_Height / 2) - (imgBtn_play.getHeight() / 2)-60;//多减60是为了下移一些 不遮住背景logo
		imgBtn_play.setPosition(btn_play_x, btn_play_y);

//		atlas = manager.get("maps/select.pack");
		//获取图册
		atlas = new TextureAtlas(Gdx.files.internal("maps/select.pack"));
		 
		//开场界面的选择背景板
		panel = manager.get("maps/level_select_panel.png");
		BG = manager.get("maps/level_select_bg.png");

		image_panel = new Image(panel);
		image_BG = new Image(BG);
		image_start.setSize(Viewport_Width, Viewport_Height);
		
//		images[0]=new Image(atlas.findRegion("lv1"));
		
		
		images = new Image[9];
		for (int i = 0; i < images.length; i++) {
			//通过位置切割出图片 返回的是一个个AtlasRegion
			images[i] = new Image(atlas.findRegion("lv"+(i+1)));

			images[i].setScale(0.5f);
		}
		
		
		// 把maps/select.pack中的图片存放到集合并且设置好坐标 选择级数图片 把一个个级数放在屏幕各个位置
		images[0].setPosition(100, Viewport_Height/2);
		images[1].setPosition(200, Viewport_Height/2);
		images[2].setPosition(300, Viewport_Height/2);
		images[3].setPosition(400, Viewport_Height/2);
		images[4].setPosition(500, Viewport_Height/2);
		//
		images[5].setPosition(100, 100);
		images[6].setPosition(200, 100);
		images[7].setPosition(300, 100);
		images[8].setPosition(400, 100);

		initListener();
		//告诉gpu 设置输入事件处理器  Stage类的父类实现了InputProcessor 设置整个舞台的监听注册  这样舞台中的按钮才会起效 否则即使注册了按钮监听也不会起效
		Gdx.input.setInputProcessor(stage);

	}

	private void initListener() {
		// TODO Auto-generated method stub
		//点击play按钮
		imgBtn_play.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = manager.get("sound/diamond.ogg");
				sound.play();
				Debug = false;
				return true;
			}
		});

		//
		images[0].addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = manager.get("sound/select.wav");
				sound.play();
				// 播放第一级对应的音乐
				PlayScreen.level = 0;
				// 玩家屏幕
				PlayScreen playScr = new PlayScreen(mgame);
				// 设置玩家屏幕
				mgame.setScreen(playScr);
				return true;
			}

		});

		images[1].addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = manager.get("sound/select.wav");
				sound.play();
				PlayScreen.level = 1;
				PlayScreen playScr = new PlayScreen(mgame);
				mgame.setScreen(playScr);
				return true;
			}

		});

		images[2].addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = manager.get("sound/select.wav");
				sound.play();
				PlayScreen.level = 2;
				PlayScreen playScr = new PlayScreen(mgame);
				mgame.setScreen(playScr);
				return true;
			}

		});

		images[3].addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = manager.get("sound/select.wav");
				sound.play();
				PlayScreen.level = 3;
				PlayScreen playScr = new PlayScreen(mgame);
				mgame.setScreen(playScr);
				return true;
			}

		});

		images[4].addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = manager.get("sound/select.wav");
				sound.play();
				PlayScreen.level = 4;
				PlayScreen playScr = new PlayScreen(mgame);
				mgame.setScreen(playScr);
				return true;
			}

		});

		images[5].addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = manager.get("sound/select.wav");
				sound.play();
				PlayScreen.level = 5;
				PlayScreen playScr = new PlayScreen(mgame);
				mgame.setScreen(playScr);
				return true;
			}

		});

		images[6].addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = manager.get("sound/select.wav");
				sound.play();
				PlayScreen.level = 6;
				PlayScreen playScr = new PlayScreen(mgame);
				mgame.setScreen(playScr);
				return true;
			}

		});

		images[7].addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = manager.get("sound/select.wav");
				sound.play();
				PlayScreen.level = 7;
				PlayScreen playScr = new PlayScreen(mgame);
				mgame.setScreen(playScr);
				return true;
			}

		});

		images[8].addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = manager.get("sound/select.wav");
				sound.play();
				PlayScreen.level = 8;
				PlayScreen playScr = new PlayScreen(mgame);
				mgame.setScreen(playScr);
				return true;
			}

		});

		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		//清屏
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(delta);//添加开始按钮 背景图 以及用于选择级别的图片到舞台
		/**
		 * 通过源码可以看出act(float delta)和draw()方法
		 * 都是通知添加到Actor中的对象来执行各自的act()和draw()方法。
		 * 另外特别指出setDebugAll方法，此方法可以使在Stage中的每一个Actor都添加线框，方便调试*/
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

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
		stage.clear();
		stage.dispose();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}
	/**为舞台添加演员 开始按钮和 背景图片*/
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		if (Debug) {
			stage.getActors().clear();
			stage.addActor(image_start);
			stage.addActor(imgBtn_play);
		} else {
			stage.getActors().clear();
			stage.addActor(image_BG);
			stage.addActor(image_panel);
			for (int i = 0; i < images.length; i++) {
				stage.addActor(images[i]);
			}
		}
	}

}
