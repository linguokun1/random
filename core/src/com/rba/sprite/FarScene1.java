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
public class FarScene1 {
    Texture mRouteTexture;
    float currentX;
//    float defaultX;
    public FarScene1(){
        mRouteTexture = MainGame.manager.get("paoku/farscene1.png");

    }

    public void render(SpriteBatch batch, Protagonist protagonist){
        batch.begin();
        currentX -= protagonist.getBody().getPosition().x;
//        System.out.println("currentX = "+currentX);
        batch.draw(mRouteTexture, 0 - protagonist.getBody().getPosition().x* Constant.RATE*3/10, 0);

        batch.end();
    }


}
