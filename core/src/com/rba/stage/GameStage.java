package com.rba.stage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * @author linguokun
 * @packageName com.rba.stage
 * @description
 * @date 16/7/21
 */
public class GameStage extends Stage {
    Texture mRouteTexture;
    Image mRouteImage;
    public GameStage(){
        super();

    }

    public GameStage(ScreenViewport screenViewport, SpriteBatch batch) {
        super(screenViewport, batch);
        init();
    }

    private void init() {
//        mRouteTexture = MainGame.manager.get("paoku/background.png");
//        mRouteImage = new Image(mRouteTexture);
//        mRouteImage.setPosition(0, 500);
//        this.addActor(mRouteImage);
    }
}
