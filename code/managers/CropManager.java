package code.managers;

import femto.mode.HiRes16Color;

import images.Seeds;
import images.Sprouts;

import images.TurnipYoung;
import images.TurnipHarvest;

/**
 * CropManager is used for managing the Crop sprite lifecycles. 
 * 
 * Since we don't really animate the sprites for Crops, we use
 * regular static images, which is much easier for saving some space.
 * 
 * TODO: Finish out the artwork here
 */ 
public class CropManager {
    /*
    turnip;     0
    radish;     1
    daisy;      2
    coffee;     3
    tea;        4
    greenBean;  5
    tomato;     6
    blueberry;  7
    magicFruit; 8
    */
    
    Seeds seeds;
    Sprouts sprouts;
    
    // Turnip
    TurnipYoung turnipYoung;
    TurnipHarvest turnipHarvest;
    
    /**
     * Initializes the images for drawing
     */ 
    public CropManager(){
        seeds = new Seeds();
        sprouts = new Sprouts();
        
        turnipYoung = new TurnipYoung();
        turnipHarvest = new TurnipHarvest();
    }
    
    
    public void draw(HiRes16Color screen, byte type, byte growth, short x, short y){
        switch(type){
            case 1: drawTurnip(screen, growth, x, y);break;
            case 2: drawRadish(screen, growth, x, y);break;
            case 3: drawDaisy(screen, growth, x, y);break;
            case 4: drawCoffee(screen, growth, x, y);break;
            case 5: drawTea(screen, growth, x, y);break;
            case 6: drawGreenBean(screen, growth, x, y);break;
            case 7: drawTomato(screen, growth, x, y);break;
            case 8: drawBlueberry(screen, growth, x, y);break;
            case 9: drawMagicFruit(screen, growth, x, y);break;
        }
    }
    
    private void drawTurnip(HiRes16Color screen, byte growth, short x, short y){
        switch(growth){
            case 2:seeds.draw(screen, x, y);break;
            case 3:sprouts.draw(screen, x, y);break;
            case 4:turnipYoung.draw(screen, x, y);break;
            case 5:
            case 6:
            case 7: 
            case 8: 
                turnipHarvest.draw(screen, x, y);
                break;
            
        }
    }
    private void drawRadish(HiRes16Color screen, byte growth, short x, short y){
        switch(growth){
            case 2:seeds.draw(screen, x, y);break;
            case 3:sprouts.draw(screen, x, y);break;
            case 4:sprouts.draw(screen, x, y);break;
            case 5:break;
        }
    }
    private void drawDaisy(HiRes16Color screen, byte growth, short x, short y){
        switch(growth){
            case 2:seeds.draw(screen, x, y);break;
            case 3:break;
            case 4:break;
            case 5:break;
        }
    }
    private void drawCoffee(HiRes16Color screen, byte growth, short x, short y){
        switch(growth){
            case 2:seeds.draw(screen, x, y);break;
            case 3:break;
            case 4:break;
            case 5:break;
        }
    }
    private void drawTea(HiRes16Color screen, byte growth, short x, short y){
        switch(growth){
            case 2:seeds.draw(screen, x, y);break;
            case 3:break;
            case 4:break;
            case 5:break;
        }
    }
    private void drawGreenBean(HiRes16Color screen, byte growth, short x, short y){}
    private void drawTomato(HiRes16Color screen, byte growth, short x, short y){}
    private void drawBlueberry(HiRes16Color screen, byte growth, short x, short y){}
    private void drawMagicFruit(HiRes16Color screen, byte growth, short x, short y){}
    
}