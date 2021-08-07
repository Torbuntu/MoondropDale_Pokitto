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
    int type, growth, x, y;
    int renderX, renderY;
    boolean water;
    
    Turnip turnip;//1
    Radish radish;//2
    Daisy daisy;//3
    GreenBean greenBean;//4
    Coffee coffee;//5
    Tea tea;//6
    Tomato tomato;//7
    Blueberry blueberry;//8
    MagicFruit magicFruit;//9
    
    public Crop(int type, int growth, int x, int y){
        this.type = type;
        this.growth = growth;
        this.x = x;
        this.y = y;
        this.renderX = 20+x*20;
        this.renderY = 48+y*16;
        
        
        water = false;
        initCrop();
    }
    
    private void initCrop(){
        switch(type){
            case 1:
                turnip = new Turnip();
                turnip.harvest();
                break;
            case 2:
                radish = new Radish();
                //radish.harvest();
                break;
            case 3:
                daisy = new Daisy();
                daisy.harvest();
                break;
            case 4:
                greenBean = new GreenBean();
                greenBean.harvest();
                break;
        }
    }
    
    /**
     * Set the type of Crop and initialize the crop.
     * Crops begin growing at 1.
     */ 
    public void plant(int type){
        this.type = type;
        growth = 2;
        initCrop();
    }

    /**
     * At the end of a day, we need to progress the crops.
     * Crops will only grow if watered that day. 
     * 
     * If a crop hasn't been watered in over [num] days, 
     * then the crop will need to die :( 
     */
    void update(){
        if(growth > 1 && water){
            water = false;
            // 9 is max growth mano
            if(growth < 9)growth++;
        }
        switch(type){
            case 0:break;
            case 1:break;
            case 2:
                updateRadish();
                break;
        }
        // TODO: Something here about growing sprites per crops 1-9
    }
    
    void updateRadish(){
        if(growth > 3){
            switch(growth){
                case 4:
                    radish.four();
                    break;
                case 5:
                    radish.five();
                    break;
                case 6:
                    radish.six();
                    break;
                default:
                    radish.harvest();
                    break;
            }
        }
    }
    
    
    void render(HiRes16Color screen){
        // TODO: draw numbers temporarily.
        //screen.setTextColor(1);
        //screen.setTextPosition(2+renderX, 6+renderY);
        //screen.print(type + "," + growth);
        
        if(water){
            screen.fillRect(renderX, renderY+16, 20, 16, 8);
        }else{
            if(growth < 1) {
                screen.fillRect(renderX, renderY+16, 20, 16, 6);
                return;
            }else{
                // tilled?
                screen.fillRect(renderX, renderY+16, 20, 16, 7);
            }
        }
        
        
        if(growth < 4) {
            // 0: regular tile, 1: tilled, 2: seed, 3: sappling 1
            switch(growth){
                case 0:
                    // TODO: render something to show it is tillable?
                    break;
                case 1:
                    if(!water)screen.fillRect(renderX, renderY+16, 20, 16, 7);
                    break;
                case 2:
                    screen.fillCircle(renderX+10, renderY+28, 3, 3);
                    break;
                case 3:
                    screen.drawVLine(renderX+8, renderY+24, 5, 9);
                    break;
            }
        }else{

            // Crop is growing!
            switch(type){
                case 1:
                    turnip.draw(screen,renderX,renderY);
                    break;
                case 2:
                    radish.draw(screen,renderX,renderY);
                    break;
                case 3:
                    daisy.draw(screen,renderX,renderY);
                    break;
                case 4:
                    greenBean.draw(screen, renderX, renderY);
                    break;
            }
        }
        
    }

    
    int getGrowth(){return growth;}
    int getType(){return type;}
    int getX(){return x;}
    int getY(){return y;}
}