package code.managers;

import femto.mode.HiRes16Color;
import code.Globals;

import sprites.Hoe;
import sprites.Water;
import sprites.Planter;

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
    Planter planter;
    
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
        
        planter = new Planter();
        planter.hud();
    }
    
    // 0:hoe, 1:water, 2:planter, 3:other? 
    void drawHud(HiRes16Color screen){
        switch(equipped){
            case 0:
                hoe.draw(screen, screen.width()-20, screen.height()-16);
                break;
            case 1:
                water.draw(screen, screen.width()-40, screen.height()-16);
                screen.fillRect(screen.width()-19, screen.height()-(int)(fill*16/8),18,(int)(fill*16/8),14);
                screen.drawRect(screen.width()-20, screen.height()-16, 19, 15, 5);
                break;
            case 2:
                planter.draw(screen, screen.width()-40, screen.height()-16);
                screen.drawRect(screen.width()-20, screen.height()-16, 19, 16, 5);
                screen.setTextPosition(screen.width()-18, screen.height()-12);
                screen.print(equippedSeed);
                break;
            case 3:
                break;
        }
        
    }

    
}