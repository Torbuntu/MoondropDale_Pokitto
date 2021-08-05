package code.entities;

import femto.mode.HiRes16Color;

// TODO: Make these sprites
import sprites.Turnip;
import sprites.Radish;
import sprites.Daisy;
import sprites.Coffee;
import sprites.Tea;
import sprites.GreenBean;
import sprites.Tomato;
import sprites.Blueberry;
import sprites.MagicFruit;

public class Crop {
    int type, growth, water, x, y;
    int renderX, renderY;
    Turnip turnip;
    Radish radish;
    Daisy daisy;
    GreenBean greenBean;
    Coffee coffee;
    Tea tea;

    Tomato tomato;
    Blueberry blueberry;
    MagicFruit magicFruit;
    
    public Crop(int type, int growth, int x, int y){
        this.type = type;
        this.growth = growth;
        this.x = x;
        this.y = y;
        this.renderX = 20+x*20;
        this.renderY = 48+y*16;
        
        
        water = 0;
        initCrop();
    }
    
    private void initCrop(){
        switch(type){
            case 0:
                turnip = new Turnip();
                turnip.harvest();
                break;
            case 1:
                radish = new Radish();
                radish.harvest();
                break;
            case 2:
                daisy = new Daisy();
                daisy.harvest();
                break;
            case 3:
                greenBean = new GreenBean();
                greenBean.harvest();
                break;
        }
    }
    
    public void plant(int type){
        this.type = type;
        growth = 0;
        initCrop();
    }
    
    
    void update(){

    }
    
    void render(HiRes16Color screen){
        // TODO: draw numbers temporarily.
        screen.setTextColor(1);
        screen.setTextPosition(2+renderX, 6+renderY);
        screen.print(type + "," + growth);
        switch(type){
            case 0:
                turnip.draw(screen,renderX,renderY);
                break;
            case 1:
                radish.draw(screen,renderX,renderY);
                break;
            case 2:
                daisy.draw(screen,renderX,renderY);
                break;
            case 3:
                greenBean.draw(screen, renderX, renderY);
                break;
        }
    }

    
    int getGrowth(){return growth;}
    int getType(){return type;}
    int getX(){return x;}
    int getY(){return y;}
}