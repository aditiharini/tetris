package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by aditisri on 1/2/18.
 */
public class TextureFactory {

    public static Texture getTexture(TetrisColor color){
        switch(color){
            case CYAN:
                return new Texture("../assets/Squares/cyan_bevelled.png");
            case DARK_BLUE:
                return new Texture("../assets/Squares/dark_blue_bevelled.png");
            case NEON_GREEN:
                return new Texture("../assets/Squares/neon_green_bevelled.png");
            case PINK:
                return new Texture("../assets/Squares/pink_bevelled.png");
            case PURPLE:
                return new Texture("../assets/Squares/purple_bevelled.png");
            case RED:
                return new Texture("../assets/Squares/red_bevelled.png");
            case YELLOW:
                return new Texture("../assets/Squares/yellow_bevelled.png");
        }
        return null;
    }

}
