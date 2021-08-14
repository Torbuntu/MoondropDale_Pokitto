package code.managers;

import femto.mode.HiRes16Color;
import code.Globals;

// For the hud
import sprites.SeedIcons;

import images.HoeIcon;
import images.WaterIcon;
import images.PlanterIcon;
import images.BasketIcon;
import images.FishingRodIcon;

class Inventory {
    // 0:Fishing rod, 1:Hoe, 2:water, 3:basket, 4:Planter -> Seed 
    byte equipped = 1;
    
    // start with 0 (even if we don't have seeds)
    byte equippedSeed = 0;
    
    // Seed quantity
    /*
    int turnipQ;    0
    int radishQ;    1
    int daisyQ;     2
    int coffeeQ;    3
    int teaQ;       4
    int greenBeanQ; 5
    int tomatoQ;    6
    int blueberryQ; 7
    int magicFruitQ;8
    */

    int monies;
    
    HoeIcon hoeIcon;
    WaterIcon waterIcon;
    PlanterIcon planterIcon;
    BasketIcon basketIcon;
    FishingRodIcon fishingIcon;
    
    // Start with a full bucket to not irritate users.
    byte fill = 8;
    
    SeedIcons seedIcons;
    
    short[] quantities;
    boolean[] locks;
    
    Inventory(){
        quantities = new short[9];
        locks = new boolean[9];
        byte[] items = Globals.load("items");
        byte id = 0;
        for(int i = 0; i < 16; i+=2){
            quantities[id] = items[i+1];
            locks[id] = items[i] == 1;
            id++;
        }

        monies = Globals.saveManager.monies;
        
        seedIcons = new SeedIcons();
        seedIcons.turnip();
        
        hoeIcon = new HoeIcon();
        waterIcon = new WaterIcon();
        planterIcon = new PlanterIcon();
        basketIcon = new BasketIcon();
        fishingIcon = new FishingRodIcon();
    }
    
    /**
     * Manages increase in Monies when a crop
     * is harvested.    
     * 
     * TODO: update the prices to be accurate
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
    
    public boolean hasQuantity(){
        return quantities[equippedSeed] > 0;
    }
    
    public void planted(){
        quantities[equippedSeed]--;
    }
    
    public boolean buySuccess(byte id){
        if(monies > 10 && locks[id]){
            monies-=10;
            quantities[id]++;
        }else{
            return false;
        }
        return true;
    }
    
    // 0:hoe, 1:water, 2:planter, 3:fishing rod 
    void drawHud(HiRes16Color screen, boolean fish){
        
        screen.setTextPosition(0, 158);
        screen.print("$"+monies);
                
        // Unlock the fishing rod?
        if(fish)fishingIcon.draw(screen, 30, 160);
        
        hoeIcon.draw(screen, 50, 160);
        
        waterIcon.draw(screen, 70, 160);
        screen.drawHLine(73, 173,(int)(fill*14/8),14);
        
        basketIcon.draw(screen, 90, 160);

        planterIcon.draw(screen, 110, 160);
        switch(equippedSeed){
            case 0:seedIcons.turnip();break;
            case 1:seedIcons.radish();break;
            case 2:seedIcons.daisy();break;
            case 3:seedIcons.coffee();break;
            case 4:seedIcons.tea();break;
            case 5:seedIcons.greenBean();break;
            case 6:seedIcons.tomato();break;
            case 7:seedIcons.blueberry();break;
            case 8:seedIcons.magicFruit();break;
        }
        seedIcons.draw(screen, 130, 160);

        // Show selected
        screen.drawRect(29+equipped*20, 159, 20,16, 14);
    }
    
    public void drawTool(HiRes16Color screen){
        switch(equipped){
            case 0:
                fishingIcon.draw(screen, 146, 50);
                break;
            case 1:
                hoeIcon.draw(screen, 146 , 50);
                break;
            case 2:
                waterIcon.draw(screen, 146 , 50);
                break;
            case 3:
                basketIcon.draw(screen, 146 , 50);
                break;
            case 4:
                planterIcon.draw(screen, 146 , 50);
                break;
        }
    }
    
    public void drawSeed(HiRes16Color screen, byte id){
        screen.setTextPosition(150, 80);
        screen.print("x"+(int)quantities[id]);
        
        screen.setTextPosition(45, 72);
        switch(id){
            case 0:seedIcons.turnip();screen.print("Turnip");break;
            case 1:if(locks[1]){seedIcons.radish();screen.print("Radish");}else{ seedIcons.lock();screen.print("Radish");}break;
            case 2:if(locks[2]){seedIcons.daisy();screen.print("Daisy");}else{ seedIcons.lock();screen.print("Daisy");}break;
            case 3:if(locks[3]){seedIcons.coffee();screen.print("Coffee");}else{ seedIcons.lock();screen.print("Coffee");}break;
            case 4:if(locks[4]){seedIcons.tea();screen.print("Tea");}else{ seedIcons.lock();screen.print("Tea");}break;
            case 5:if(locks[5]){seedIcons.greenBean();screen.print("Green Beans");}else{ seedIcons.lock();screen.print("Green Beans");}break;
            case 6:if(locks[6]){seedIcons.tomato();screen.print("Tomato");}else{ seedIcons.lock();screen.print("Tomato");}break;
            case 7:if(locks[7]){seedIcons.blueberry();screen.print("Blueberry");}else{ seedIcons.lock();screen.print("Blueberry");}break;
            case 8:if(locks[8]){seedIcons.magicFruit();screen.print("Magic Fruit");}else{ seedIcons.lock();screen.print("Magic Fruit");}break;
        }
        seedIcons.draw(screen, 145, 64);
    }
    
    public void drawSeed(HiRes16Color screen){
        drawSeed(screen, equippedSeed);
    }
    
}