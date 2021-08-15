package code.managers;

import femto.mode.HiRes16Color;

import images.Bush;

import images.Seeds;
import images.Sprouts;

import images.Stick;

import images.TurnipYoung;
import images.TurnipHarvest;

import images.RadishYoung;
import images.RadishHarvest;


import images.BlueberryYoung;
import images.BlueberryHarvest;

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
    turnip;     1
    radish;     2
    daisy;      3
    coffee;     4
    tea;        5
    greenBean;  6
    tomato;     7
    blueberry;  8
    magicFruit; 9
    */
    Bush bush;
    Seeds seeds;
    Sprouts sprouts;
    Stick stick;
    
    // Turnip
    TurnipYoung turnipYoung;
    TurnipHarvest turnipHarvest;
    
    RadishYoung radishYoung;
    RadishHarvest radishHarvest;
    
    
    BlueberryYoung blueberryYoung;
    BlueberryHarvest blueberryHarvest;
    
    /**
     * Initializes the images for drawing
     */ 
    public CropManager(){
        bush = new Bush();
        seeds = new Seeds();
        sprouts = new Sprouts();
        
        stick = new Stick();
        
        turnipYoung = new TurnipYoung();
        turnipHarvest = new TurnipHarvest();
        
        radishYoung = new RadishYoung();
        radishHarvest = new RadishHarvest();
        
        
        blueberryYoung = new BlueberryYoung();
        blueberryHarvest = new BlueberryHarvest();
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
    
    /**
     * Turnip is ready to harvest at growth 4 and after.
     */ 
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
    
    /**
     * Radish is harvestable at growth 5
     */
    private void drawRadish(HiRes16Color screen, byte growth, short x, short y){
        switch(growth){
            case 2:seeds.draw(screen, x, y);break;
            case 3:sprouts.draw(screen, x, y);break;
            case 4:sprouts.draw(screen, x, y);break;
            case 5:radishYoung.draw(screen, x,y);break;
            case 6:
            case 7:
            case 8:
                radishHarvest.draw(screen,x,y);
                break;
        }
    }
    
    /**
     * Daisy is harvestable at growth 5
     */
    private void drawDaisy(HiRes16Color screen, byte growth, short x, short y){
        switch(growth){
            case 2:seeds.draw(screen, x, y);break;
            case 3:seeds.draw(screen, x, y);break;
            case 4:sprouts.draw(screen, x, y);break;
            case 5:sprouts.draw(screen, x, y);break;
        }
    }
    
    /**
     * Coffee is harvestable at growth 6
     */
    private void drawCoffee(HiRes16Color screen, byte growth, short x, short y){
        switch(growth){
            case 2:stick.draw(screen, x, y);break;
            case 3:bush.draw(screen, x, y);break;
            case 4:bush.draw(screen, x, y);break;
            case 5:bush.draw(screen, x, y);break;
        }
    }
    
    /**
     * Tea is harvestable at growth 6
     */
    private void drawTea(HiRes16Color screen, byte growth, short x, short y){
        switch(growth){
            case 2:stick.draw(screen, x, y);break;
            case 3:stick.draw(screen, x, y);break;
            case 4:bush.draw(screen, x, y);break;
            case 5:bush.draw(screen, x, y);break;
        }
    }
    
    /**
     * GreenBean is harvestable at growth 7
     */
    private void drawGreenBean(HiRes16Color screen, byte growth, short x, short y){
        switch(growth){
            case 2: seeds.draw(screen, x, y);break;
            case 3: stick.draw(screen, x, y);break;
            case 4: bush.draw(screen, x, y);break;
            case 5: bush.draw(screen, x, y);break;
        }
    }
    
    /**
     * Tomato is harvestable at growth 7
     */
    private void drawTomato(HiRes16Color screen, byte growth, short x, short y){
        switch(growth){
            case 2: seeds.draw(screen, x, y);break;
            case 3: stick.draw(screen, x, y);break;
            case 4: bush.draw(screen, x, y);break;
            case 5: bush.draw(screen, x, y);break;
        }
    }
    
    /**
     * Blueberry is harvestable at growth 8
     */
    private void drawBlueberry(HiRes16Color screen, byte growth, short x, short y){
        switch(growth){
            case 2: seeds.draw(screen, x, y);break;
            case 3: stick.draw(screen, x, y);break;
            case 4: stick.draw(screen, x, y);break;
            case 5: bush.draw(screen, x, y);break;
            case 6: 
            case 7: blueberryYoung.draw(screen, x, y);break;
            case 8: blueberryHarvest.draw(screen, x, y);break;
        }
    }
    
    /**
     * MagicFruit is harvestable at growth 8
     */
    private void drawMagicFruit(HiRes16Color screen, byte growth, short x, short y){}
    
}