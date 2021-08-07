package code.managers;

import femto.mode.HiRes16Color;
import code.Globals;

import sprites.Hoe;
import sprites.Water;

class Inventory {
    // 0:hoe, 1:water, 2:planter, 3:other? 
    int equipped = 0;
    
    // start with none
    int equippedSeed = 0;
    
    // Seed quantity
    int turnip;
    int radish;
    int beet;
    int daisy;
    int coffee;
    int tea;
    int greenBean;
    int tomato;
    int cranberry;
    int blueberry;
    int magicFruit;
    
    int monies;
    
    
    Hoe hoe;
    Water water;
    int fill = 0;
    
    Inventory(){
        turnip = Globals.saveManager.turnip;
        radish = Globals.saveManager.radish;
        daisy = Globals.saveManager.daisy;
        coffee = Globals.saveManager.coffee;
        tea = Globals.saveManager.tea;
        greenBean = Globals.saveManager.greenBean;
        tomato = Globals.saveManager.tomato;
        blueberry = Globals.saveManager.blueberry;
        magicFruit = Globals.saveManager.magicFruit;
        
        monies = Globals.saveManager.monies;
        
        hoe = new Hoe();
        hoe.hud();
        
        water = new Water();
        water.hud();
    }
    
    // 0:hoe, 1:water, 2:planter, 3:other? 
    void drawHud(HiRes16Color screen){
        switch(equipped){
            case 0:
                hoe.draw(screen, screen.width()-40, screen.height()-16);
                break;
            case 1:
                water.draw(screen, screen.width()-40, screen.height()-16);
                screen.drawVLine(screen.width()-22, screen.height()-fill-2, fill, 14);
                break;
        }
        
    }

    
}