package com.rba.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rba.Main.MainGame;
import com.rba.handle.Constant;

/**
 * @author linguokun
 * @packageName com.rba.sprite
 * @description
 * @date 16/7/21
 */
public class FarScene2 {
    Texture mRouteTexture;
    public FarScene2(){
        mRouteTexture = MainGame.manager.get("paoku/farscene2.png");

    }

    public void render(SpriteBatch batch, Protagonist protagonist){
        batch.begin();
        batch.draw(mRouteTexture, 0 - protagonist.getBody().getPosition().x * Constant.RATE*5/10, 0);
        batch.end();
    }


}
