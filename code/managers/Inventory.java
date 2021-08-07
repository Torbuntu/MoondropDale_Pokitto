package code.managers;

import femto.mode.HiRes16Color;
import code.Globals;

import sprites.Hoe;

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
    }
    
    void drawHud(HiRes16Color screen){
        switch(equipped){
            case 0:
                hoe.draw(screen, screen.width()-40, screen.height()-16);
                break;
        }
        
    }

    
}