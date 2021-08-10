package code.managers;

import femto.mode.HiRes16Color;
import code.Globals;

import sprites.Hoe;
import sprites.Water;
import sprites.Planter;
import sprites.Basket;

// For the hud
import sprites.Turnip;
import sprites.Radish;
import sprites.Daisy;
import sprites.Coffee;
import sprites.Tea;
import sprites.GreenBean;
import sprites.Tomato;
import sprites.Blueberry;
import sprites.MagicFruit;

class Inventory {
    // 0:hoe, 1:water, 2:planter, 3:other? 
    int equipped = 0;
    
    // start with 0 (even if we don't have seeds)
    int equippedSeed = 0;
    
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
    
    // Seed available progress
    boolean turnipL;
    boolean radishL;
    boolean daisyL;
    boolean coffeeL;
    boolean teaL;
    boolean greenBeanL;
    boolean tomatoL;
    boolean blueberryL;
    boolean magicFruitL;
    
    int monies;
    
    Hoe hoe;
    Water water;
    Planter planter;
    Basket basket;
    
    // Start with a full bucket to not irritate users.
    int fill = 8;
    
    Turnip turnip;
    Radish radish;
    Daisy daisy;
    Coffee coffee;
    Tea tea;
    GreenBean greenBean;
    Tomato tomato;
    Blueberry blueberry;
    MagicFruit magicFruit;
    
    int[] quantities;
    boolean[] locks;
    
    Inventory(){
        quantities = new int[9];
        locks = new boolean[9];
        byte[] items = Globals.load("items");
        int id = 0;
        for(int i = 0; i < 16; i+=2){
            quantities[id] = items[i+1];
            locks[id] = items[i] == 1;
            id++;
        }

        monies = Globals.saveManager.monies;
        
        turnip = new Turnip();
        radish = new Radish();
        daisy = new Daisy();
        coffee = new Coffee();
        tea = new Tea();
        greenBean = new GreenBean();
        tomato = new Tomato();
        blueberry = new Blueberry();
        magicFruit = new MagicFruit();
        
        turnip.hud();
        radish.hud();
        daisy.hud();
        coffee.hud();
        tea.hud();
        greenBean.hud();
        tomato.hud();
        blueberry.hud();
        magicFruit.hud();
        
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
    
    public boolean hasQuantity(){
        return quantities[equippedSeed] > 0;
    }
    
    public void planted(){
        quantities[equippedSeed]--;
    }
    
    // 0:hoe, 1:water, 2:planter, 3:other? 
    void drawHud(HiRes16Color screen){
        switch(equipped){
            case 0:
                hoe.draw(screen, 200, 160);
                break;
            case 1:
                water.draw(screen, 180, 160);
                screen.fillRect(201, 176-(int)(fill*16/8),18,(int)(fill*16/8),14);
                screen.drawRect(200, 160, 19, 15, 5);
                break;
            case 2:
                planter.draw(screen, 180, 160);
                switch(equippedSeed){
                    case 0:turnip.draw(screen, 200, 160);break;
                    case 1:radish.draw(screen, 200, 160);break;
                    case 2:daisy.draw(screen, 200, 160);break;
                    case 3:coffee.draw(screen, 200, 160);break;
                    case 4:tea.draw(screen, 200, 160);break;
                    case 5:greenBean.draw(screen, 200, 160);break;
                    case 6:tomato.draw(screen, 200, 160);break;
                    case 7:blueberry.draw(screen, 200, 160);break;
                    case 8:magicFruit.draw(screen, 200, 160);break;
                }
                break;
            case 3:
                basket.draw(screen, 200, 160);
                break;
        }
    }
    
    public void drawTool(HiRes16Color screen){
        switch(equipped){
            case 0:
                hoe.hud();
                hoe.draw(screen, 146 , 50);
                break;
            case 1:
                water.hud();
                water.draw(screen, 146 , 50);
                break;
            case 2:
                planter.hud();
                planter.draw(screen, 146 , 50);
                break;
            case 3:
                basket.hud();
                basket.draw(screen, 146 , 50);
                break;
        }
    }
    
    public void drawSeed(HiRes16Color screen){
        screen.setTextPosition(140, 90);
        screen.print("x"+quantities[equippedSeed]);
        switch(equippedSeed){
            case 0:
                if(locks[0])turnip.draw(screen, 146, 72);
                else screen.fillRect(145, 72, 20, 16, 12);
                break;
            case 1:if(locks[1])radish.draw(screen, 146, 72);else screen.fillRect(145, 72, 20, 16, 12);break;
            case 2:if(locks[2])daisy.draw(screen, 146, 72);else screen.fillRect(145, 72, 20, 16, 12);break;
            case 3:if(locks[3])coffee.draw(screen, 146, 72);else screen.fillRect(145, 72, 20, 16, 12);break;
            case 4:if(locks[4])tea.draw(screen, 146, 72);else screen.fillRect(145, 72, 20, 16, 12);break;
            case 5:if(locks[5])greenBean.draw(screen, 146, 72);else screen.fillRect(145, 72, 20, 16, 12);break;
            case 6:if(locks[6])tomato.draw(screen, 146, 72);else screen.fillRect(145, 72, 20, 16, 12);break;
            case 7:if(locks[7])blueberry.draw(screen, 146, 72);else screen.fillRect(145, 72, 20, 16, 12);break;
            case 8:if(locks[8])magicFruit.draw(screen, 146, 72);else screen.fillRect(145, 72, 20, 16, 12);break;
        }
    }
    
}