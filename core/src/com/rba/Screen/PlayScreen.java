package com.rba.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.rba.Main.MainGame;
import com.rba.handle.Box2DContactListener;
import com.rba.sprite.BackGroundGame;
import com.rba.sprite.FarScene1;
import com.rba.sprite.FarScene2;
import com.rba.sprite.MonsterBox;
import com.rba.sprite.Protagonist;
import com.rba.sprite.Route;
import com.rba.sprite.Sky;
import com.rba.sprite.Star;
import com.rba.stage.GameStage;

import static com.rba.Main.MainGame.Viewport_Height;
import static com.rba.Main.MainGame.Viewport_Width;
import static com.rba.handle.Constant.GREEN;
import static com.rba.handle.Constant.MONSTER;
import static com.rba.handle.Constant.PLAYER;
import static com.rba.handle.Constant.RATE;
import static com.rba.handle.Constant.STAR;

//import static com.rba.handle.Constant.BLUE;
//import static com.rba.handle.Constant.RED;
/**真正开始游戏时的界面*/
public class PlayScreen extends RunerScreen{
	
	
	World world;
	Box2DDebugRenderer b2dRender;
	OrthographicCamera box2dCamera;
	//
	private boolean Box2DDebug = true;

	BodyDef bodyDef;
	Body body;

	FixtureDef fixDef;
	Box2DContactListener bcl; //

	TiledMap tileMap;
	OrthogonalTiledMapRenderer TileMapRender; //
	public static int level; //
	float tileSize; //
	float mapWidth;
	float mapHeight;
	public static int[][] barriers;
	float x, y;

	Protagonist protagonist;
	float statetime;

	Array<Star> stars;

	Array<MonsterBox> monsterBox;
	
	private Sky sky;
	private Route route;
	private FarScene1 farScene1;
	private FarScene2 farScene2;
	private Route route1;
	private BackGroundGame backGroundGame;

	public PlayScreen(MainGame mgame) {
		// TODO Auto-generated constructor stub
		super(mgame);
		/**
		 * World可以管理所有物理实体,动态模拟,异步查询。还包含高效的内存管理机制。
		 * 物理世界，管理所有body.创建世界对象,传入重力加速度
		 * 第一个参数是一个2维向量，0说明水平方向的重力为0, 9.8f说明垂直方向的重力为9.8f
		 */

		world = new World(new Vector2(0, -9.8f), true);
		// 为world添加碰撞检测监听器
		bcl = new Box2DContactListener();
		world.setContactListener(bcl);

		b2dRender = new Box2DDebugRenderer();//测试用的绘制渲染器

		// 镜头
		box2dCamera = new OrthographicCamera();
		//因为world以m作单位  舞台以px做单位 1m=100px
		box2dCamera.setToOrtho(false, Viewport_Width / RATE, Viewport_Height / RATE);

		this.init();
	}
	
	public void init() {
		//创建演员 主角
		creatActor();
		createMap();
		createStar();
		createMonsterBox();
//		sky = new Sky();
		route = new Route();
		farScene1 = new FarScene1();
		farScene2 = new FarScene2();
		route1 = new Route();
		backGroundGame = new BackGroundGame();
	}

	GameStage mGameStage;

	private void createStage() {
		ScreenViewport screenViewport = new ScreenViewport();
		screenViewport.setCamera(camera);
		mGameStage = new GameStage(screenViewport, batch);
		mGameStage.getViewport().update(Viewport_Width, Viewport_Height);
		mGameStage.addActor(protagonist);
	}

	private void creatActor() {
		//定义Body所需要的所有数据都由它负责，我们可以重复使用。Shape与Body的绑定必须在它之后完成。
		bodyDef = new BodyDef();

		//创建固定物对象
//		fixDef = new FixtureDef();
//		body = world.createBody(bodyDef);
		//设置刚体类型
		bodyDef.type = BodyType.DynamicBody;//动态物体
		bodyDef.position.set(310 / RATE, 220 / RATE);//所处这个屏幕的位置  220 / RATE
//		bodyDef.linearVelocity.set(0.5f, 0);//这里设置了刚体线性运动的速率

		body = world.createBody(bodyDef);//创建刚体

		//创建多边形对象
		PolygonShape polyShape = new PolygonShape();

		polyShape.setAsBox(20 / RATE, 40 / RATE);//多边形设置为一个盒子形状 以及其宽和高一半的值
		//创建夹具属性对象
		fixDef = new FixtureDef();

		fixDef.shape = polyShape;//为固定物设置形状
		/**
		 * 做过滤检测 categoryBits 类别 以2的指数值作为value 1,2,4,8……
		 * 最大多少 我也不清楚 有兴趣可以看Box2d源码
		 * maskBits 掩码 只有当 oneBody.maskBit & otherBody.scategoryBit > 0 时方碰撞
		 * 否则忽略两个rigid body之间的碰撞检测
		 *
		 * 这个 categoryBits 标志可以认为 fixture 在说“我是 ………. ”，
		 * maskBits 就想是在说“我将会和 … 发生碰撞”。
		 * 重要的一点是：这些情况下两个 fixtures 都得满足，这是为了碰撞被许可。
		 * */
		fixDef.filter.categoryBits = PLAYER;//固定物的碰撞类别位数
		fixDef.filter.maskBits = GREEN | STAR | MONSTER;//固定物 与草 星星 怪物都会产生碰撞
		fixDef.friction = 0f;//摩擦力
		fixDef.isSensor = false;
		body.createFixture(fixDef).setUserData("box");//用夹具把刚体与形状夹在一起(相当于固定刚体与形状)

		//复用polyShape  以盒子的形状作为shape 并且把这个形状设置到刚体的y轴的-35的位置
		polyShape.setAsBox(20 / RATE, 5 / RATE, new Vector2(0, -35 / RATE), 0);
		fixDef.shape = polyShape;
		fixDef.filter.categoryBits = PLAYER;
		fixDef.filter.maskBits = GREEN;
		fixDef.friction = 0f;//摩擦力
		fixDef.isSensor = true;//收集碰撞信息,但不产生碰撞效果
		body.createFixture(fixDef).setUserData("foot");//这是固定物 脚

		protagonist = new Protagonist(body);//自定义的主角色类  继承GameSprite精灵类
	}

	/**创建地图*/
	public void createMap() {
		try {
			//创建星星和怪物的瓦片地图类 这个文件包含了坐标
			 tileMap = new TmxMapLoader().load("mapLevel/"+"level"+level+".tmx");//"mapLevel/"+"level"+level+".tmx"
//			tileMap = new TmxMapLoader().load("maps/mapGrass.tmx");
		} catch (Exception e) {
			Gdx.app.exit();
		}

		TileMapRender = new OrthogonalTiledMapRenderer(tileMap);//地图渲染类 根据地图信息生成一个正交投射图块地图绘制对象
		System.out.println("这里会走几次");
		tileSize = tileMap.getProperties().get("tilewidth", Integer.class); // 瓦片的宽
		mapWidth = tileMap.getProperties().get("width", Integer.class); // 草地地图的宽
		mapHeight = tileMap.getProperties().get("height", Integer.class); // 草地地图的高

		System.out.println("ok" + "\n tilewidth:" + tileSize + "\n width:" + mapWidth + "\n Height:" + mapHeight);

		TiledMapTileLayer layer;

//		layer = (TiledMapTileLayer) tileMap.getLayers().get("red"); //
//		createMapLayer(layer, RED); //
		//green图层
		layer = (TiledMapTileLayer) tileMap.getLayers().get("green");
		createMapLayer(layer, GREEN);

//		layer = (TiledMapTileLayer) tileMap.getLayers().get("blue"); //
//		createMapLayer(layer, BLUE); //

	}
	/**创建地图的刚体 夹具 也就是碰撞区域*/
	public void createMapLayer(TiledMapTileLayer layer, short bits) {

		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();

		//

//		System.out.println(layer.getHeight());
		//遍历green图层 遍历每一行每一列的各个格子
		for (int row = 0; row < layer.getHeight(); row++) {
			for (int column = 0; column < layer.getWidth(); column++) {
				Cell cell = layer.getCell(column, row);
				if (cell == null)//如果某行某列没有格子,就进行下一次循环
					continue;
				if (cell.getTile() == null)//如果某个格子没有瓦片,就进行下一次循环
					continue;
				//如果格子上有瓦片的话, 就设置一个刚体并绑定形状 做一个碰撞检测
				//静止物
				bodyDef.type = BodyType.StaticBody;
				bodyDef.position.set((column + 0.5f) * tileSize / RATE, (row + 0.5f) * tileSize / RATE);//(row + 0.5f) * tileSize / RATE

				ChainShape chainShape = new ChainShape();//链形 给每个有瓦片个格子画一个链形

				Vector2[] v = new Vector2[3];
				v[0] = new Vector2(-tileSize / 2 / RATE, -tileSize / 2 / RATE);
				v[1] = new Vector2(-tileSize / 2 / RATE, tileSize / 2 / RATE);
				v[2] = new Vector2(tileSize / 2 / RATE, tileSize / 2 / RATE);

				chainShape.createChain(v);

				fixtureDef.shape = chainShape;

				fixtureDef.friction = 0f;//无摩擦力
				fixtureDef.filter.categoryBits = bits;//其实就是GREEN
				fixtureDef.filter.maskBits = PLAYER;//可与玩家碰撞
				fixtureDef.isSensor = false;//不产生碰撞信息,产生碰撞效果

				world.createBody(bodyDef).createFixture(fixtureDef);//创建地图固定物 一个草地图片由8x4个瓦片组成

			}
		}
	}
	/**创建星星*/
	private void createStar() {
		// TODO Auto-generated method stub
		MapLayer maplayer = tileMap.getLayers().get("star");
		stars = new Array<Star>();

		if (maplayer == null)
			return;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;

		CircleShape cirShape = new CircleShape();
		cirShape.setRadius(10 / RATE);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = cirShape;
		fixtureDef.filter.categoryBits = STAR;
		fixtureDef.filter.maskBits = PLAYER;
		fixtureDef.isSensor = true;//收集碰撞信息,但不产生碰撞效果
		//创建一个个星星
		for (MapObject mapObject : maplayer.getObjects()) {
			float x = 0;
			float y = 0;

			if (mapObject instanceof EllipseMapObject) {
				EllipseMapObject eMapObject = (EllipseMapObject) mapObject;
				x = eMapObject.getEllipse().x / RATE;
				y = eMapObject.getEllipse().y / RATE;
			}

			bodyDef.position.set(x, y);

			Body body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData("star");

			Star s = new Star(body);
			stars.add(s);
			body.setUserData(s);
		}
	}
	/**创建怪物*/
	private void createMonsterBox() {
		// TODO Auto-generated method stub
		MapLayer mapLayer = tileMap.getLayers().get("monster");
		monsterBox = new Array<MonsterBox>();

		if (mapLayer == null)
			return;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;

		CircleShape cirShape = new CircleShape();
		cirShape.setRadius(20 / RATE);

		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = cirShape;
		fixtureDef.restitution = 1.0f;
		fixtureDef.filter.categoryBits = MONSTER;
		fixtureDef.filter.maskBits = PLAYER|GREEN;
		fixtureDef.isSensor = true;

		for (MapObject mapObject : mapLayer.getObjects()) {
			float x = 0;
			float y = 0;

			if (mapObject instanceof EllipseMapObject) {
				EllipseMapObject eMapObject = (EllipseMapObject) mapObject;
				x = eMapObject.getEllipse().x / RATE;
				y = eMapObject.getEllipse().y / RATE;
			}
			
			bodyDef.position.set(x, y);
			
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData("monster");
			
			MonsterBox monbox = new MonsterBox(body);
			body.setUserData(monbox);
			monsterBox.add(monbox);
		}
		cirShape.dispose();
	}
	/**处理按键输入事件*/
	public void handleInput() {
		// TODO Auto-generated method stub
		if (Gdx.input.isKeyJustPressed(Keys.Z)
				|| (Gdx.input.justTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() / 2 && Gdx.input.getY() < Gdx.graphics.getHeight() / 2)) {
			if (bcl.isOnPlatform()) {
				protagonist.getBody().applyForceToCenter(0, 260, true);//260
			}
		}else if(Gdx.input.isKeyJustPressed(Keys.B) || (Gdx.input.justTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() / 2 && Gdx.input.getY() > Gdx.graphics.getHeight() / 2)){
			if (bcl.isOnPlatform() && protagonist.getBody().getLinearVelocity().x < 10.0f) {
//				protagonist.getBody().applyLinearImpulse(new Vector2(0.5f,0),protagonist.getBody().getWorldCenter(), true);
				protagonist.getBody().applyForceToCenter(10,0,true);
			}

		}else if(Gdx.input.isKeyJustPressed(Keys.B) || (Gdx.input.justTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 2)){

			if (bcl.isOnPlatform() && protagonist.getBody().getLinearVelocity().x < 10.0f) {
//				protagonist.getBody().applyLinearImpulse(new Vector2(0.5f,0),protagonist.getBody().getWorldCenter(), true);
				protagonist.getBody().applyForceToCenter(-10,0,true);
			}

		}

		if (Gdx.input.isKeyJustPressed(Keys.A)
				|| (Gdx.input.justTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 2)) {
			System.out.println("Switch");
		}
	}
	/**调整镜头位置*/
	public void adjustCamera() {
		//镜头移动到最左边不小于640/2 = 320
		if (camera.position.x < camera.viewportWidth / 2) {
			camera.position.x = camera.viewportWidth / 2;
		}
		//镜头移动到最右边不能超过3776
		//256(地图的宽度=等于256块瓦片组成 这里的width不是像素,而是瓦片的块数) x 16(瓦片的宽度)=地图全长4096 难道作者一开始就把地图宽度定义为地图x16的长度?
		//256(地图的宽度) x 16(瓦片的宽度)-屏幕的一半=4096-320=3776
		if (camera.position.x > (tileMap.getProperties().get("width", Integer.class) * tileSize)
				- camera.viewportWidth / 2) {
			camera.position.x = (tileMap.getProperties().get("width", Integer.class) * tileSize)
					- camera.viewportWidth / 2;
		}
	}
	/**调整Box2D镜头位置*/
	public void adjustBox2DCamera() {
		//当镜头位置小于屏幕一半,就把屏幕一半设置为镜头的位置
		if (box2dCamera.position.x < box2dCamera.viewportWidth / 2) {
			box2dCamera.position.x = box2dCamera.viewportWidth / 2;
		}
		// 当镜头位置大于瓦片数乘以地图总宽-屏幕一半
		if (box2dCamera.position.x > (tileMap.getProperties().get("width", Integer.class) * tileSize) / RATE
				- box2dCamera.viewportWidth / 2) {
			box2dCamera.position.x = (tileMap.getProperties().get("width", Integer.class) * tileSize) / RATE
					- box2dCamera.viewportWidth / 2;
		}
	}
	/**渲染*/
	@Override
	public void render(float delta) {
		createStage();
		// TODO Auto-generated method stub
		update(delta);
		//镜头移动            主角刚体坐标*现实世界比率+镜头的1/4宽度, 而镜头一直停留在屏幕的1/2高度
		camera.position.set(protagonist.getPosition().x * RATE + Viewport_Width
				/ 4, Viewport_Height / 2, 0);

		adjustCamera();//调整镜头
		camera.update();//更新镜头位置

		//难道cameraUI专门用于渲染天空?
//		batch.setProjectionMatrix(camera_UI.combined);//在当前的Batch使用投影矩阵。
//		sky.render(batch);//渲染天空背景图片
		backGroundGame.render(batch);

		farScene1.render(batch, protagonist);
		farScene2.render(batch, protagonist);
		route1.render(batch);

//		route.render(batch);
		statetime+=delta;//这个累计时间差 用于获取当前累计时间对应的关键帧
		//camera则专门用于渲染主角 星星 怪物

//		protagonist.render(batch, statetime);
		mGameStage.act();
		mGameStage.draw();
		batch.setProjectionMatrix(camera.combined);//把当前镜头的投影矩阵设置给精灵画笔
		//渲染主角图片
//		protagonist.render(batch, statetime);//由精灵画笔对主角的每一帧图片进行渲染

//		// 渲染星星
//		for (int i = 0; i < stars.size; i++) {
//			stars.get(i).render(batch, statetime);
//		}
//
//		//渲染怪物
//		for(int i = 0; i<monsterBox.size; i++){
//			monsterBox.get(i).render(batch, statetime);
//		}

		//绘制渲染地图之前, 设置镜头, 也就是绘制渲染当前镜头所处位置的瓦片地图
		TileMapRender.setView(camera);		//
		TileMapRender.render();				//开始渲染地图

		//更新Box2D镜头位置  用于测试  刚体形状的位置是否与刚体对应的图片一样
		if(Box2DDebug){//Box2DDebug
			//设定镜头位置 Viewport_Height / 2/RATE
			box2dCamera.position.set(protagonist.getPosition().x + Viewport_Width
					/ 4 /RATE, Viewport_Height/2/ RATE, 0);//更新Box2D镜头的x坐标和y坐标  x坐标=主角所处x坐标+屏幕1/4 y坐标=屏幕高度/2
			adjustBox2DCamera();//调整位置
			box2dCamera.update();//按设定的位置更新							//
			b2dRender.render(world, box2dCamera.combined);		//测试用的渲染器进行渲染 用于绘制box2d的格子 box2dCamera
		}
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		handleInput();//处理屏幕事件
		
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);//清屏



		/**
		 * 第一个参数是时间步，也就是我们想让世界模拟的时间。
		 在大部分情况下， 这个时间步都应该是固定的， Libgdx推荐在移动手机1/45f或者1/300f.
		 另外两个参数是velocityIterations，positionIterations 速度的约束求解量 和 位置的约束求解量.
		 约束求解器用于解决模拟中的所有
		 约束，一次一个。单个的约束会被完美的求解，然而当我们求解一个约束的时候，我们就会稍微耽误另
		 一个。要得到良好的解，我们需要迭代所有约束多次。建议的  Box2D 迭代次数是 10 次。你可以按自己
		 的喜好去调整这个数，但要记得它是速度与质量之间的平衡。更少的迭代会增加性能并降低精度，同样
		 地，更多的迭代会减少性能但提高模拟质量。
		 * */
		world.step(dt, 6, 2); //时间步长
		//获取被移除的刚体集合
		Array<Body> bodies = bcl.getRemoveBodies();
//		System.out.println("bodies.size = "+bodies.size);
		for(int i= 0; i<bodies.size; i++){
			Body b = bodies.get(i);					//获取每个刚体
//			System.out.println("b.getUserData() = "+b.getUserData());
			if(b.getUserData() != null){
				if(b.getUserData().equals("star")){
					for(int x=0; x<stars.size;x++){
						if(stars.get(x).getBody() == b){
							stars.removeValue(stars.get(x), true);		//从星星集合中移除被主角取得的星星刚体
							world.destroyBody(b);					//在世界中移除这个刚体
							protagonist.collectStars();				//主角取得星星数++
						}
					}
				}
			}
		}
		bodies.clear();
		if(bcl.isContactMonster()){
//			System.out.println("isContactMonster == true");
		}
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
		
	}

	

}
