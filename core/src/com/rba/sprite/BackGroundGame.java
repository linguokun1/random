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
public class BackGroundGame {
    Texture mBackGround;
    Texture mBackGround2;
    public BackGroundGame(){
        mBackGround = MainGame.manager.get("paoku/background1.png");
        mBackGround2 = MainGame.manager.get("paoku/background2.png");
    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(mBackGround, 0, -270);
        batch.draw(mBackGround2, 2610, -270);
        batch.end();
    }


}
