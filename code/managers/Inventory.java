package code.managers;

import femto.mode.HiRes16Color;
import code.Globals;

import sprites.Hoe;
import sprites.Water;
import sprites.Planter;
import sprites.Basket;

class Inventory {
    // 0:hoe, 1:water, 2:planter, 3:other? 
    int equipped = 0;
    
    // start with 1 (even if we don't have seeds)
    int equippedSeed = 1;
    
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
    Basket basket;
    
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
        
        basket = new Basket();
        basket.hud();
    }
    
    /**
     * Manages increase in Monies when a crop
     * is harvested.     
     */ 
    void harvest(int id){
        switch(id){
            case 0: break;
            case 1: monies += 10;break;
            case 2: monies += 10;break;
            case 3: monies += 10;break;
            case 4: monies += 10;break;
            case 5: monies += 10;break;
            case 6: monies += 10;break;
            case 7: monies += 10;break;
            case 8: monies += 10;break;
            case 9: monies += 10;break;
        }
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
                basket.draw(screen, screen.width()-20, screen.height()-16);
                break;
        }
    }
    
}