package com.rba.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rba.Main.MainGame;

/**
 * @author linguokun
 * @packageName com.rba.sprite
 * @description
 * @date 16/7/21
 */
public class Route {
    Texture mRouteTexture;
    Texture mRouteTexture2;
    public Route(){
        mRouteTexture = MainGame.manager.get("paoku/route1.png");
        mRouteTexture2 = MainGame.manager.get("paoku/route2.png");
    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(mRouteTexture, 0, -765);
        batch.draw(mRouteTexture2, 6020, -765);
        batch.end();
    }
}
