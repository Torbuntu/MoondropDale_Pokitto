package code.entities;

import femto.mode.HiRes16Color;

import sprites.Turnip;
import sprites.Radish;
import sprites.Daisy;
import sprites.Coffee;
import sprites.Tea;
import sprites.GreenBean;
import sprites.Tomato;
import sprites.Blueberry;
import sprites.MagicFruit;

import sprites.Sapling;

public class Crop {
    int type, growth, x, y, wet;
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
    
    Sapling sapling;
    
    // TODO: Figure out how long each crop will take in days to reach Harvest
    
    public Crop(int type, int growth, int x, int y){
        this.type = type;
        this.growth = growth;
        this.x = x;
        this.y = y;
        this.renderX = 20+x*20;
        this.renderY = 48+y*16;
        
        wet = 0;
        
        water = false;
        initCrop();
    }
    
    private void initCrop(){
        sapling = new Sapling();
        switch(type){
            case 1:
                turnip = new Turnip();
                turnip.harvest();
                break;
            case 2:
                radish = new Radish();
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
     * Crops begin growing at 2 because growth 1 = seed
     */ 
    public void plant(int type){
        this.type = type;
        growth = 2;
        initCrop();
    }
    
    public void till(){
        type = 0;
        growth = 1;
        water = false;
    }
    
    public void setWater(){
        water = true;
        wet = 40;
    }
    
    /**
     * Returns the Crop ID of what has been harvested.
     * If the spot is empty, returns 0.
     * 
     * If the Crop isn't harvestable, return 0;
     */ 
    int harvest(){
        if(type == 0)return 0;
        if(growth < 7)return 0;
        int id = type;
        type = 0;
        growth = 0;
        water = false;
        return id;
    }

    /**
     * At the end of a day, we need to progress the crops.
     * Crops will only grow if watered that day. 
     * 
     * If a crop hasn't been watered in over [num] days, 
     * then the crop will need to die :( 
     */
    void update(){
        if(type == 0){
            if(water)water = false;
            else growth = 0;
            return;
        }
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
    
    void updateTurnip(){
        if(growth > 3){
            switch(growth){
                case 4:
                    turnip.four();
                    break;
                case 5:
                    turnip.five();
                    break;
                case 6:
                    turnip.six();
                    break;
                default:
                    turnip.harvest();
                    break;
            }
        }
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
    
    void updateDaisy(){
        if(growth > 3){
            switch(growth){
                case 4:
                    daisy.four();
                    break;
                case 5:
                    daisy.five();
                    break;
                case 6:
                    daisy.six();
                    break;
                default:
                    daisy.harvest();
                    break;
            }
        }
    }
    
    void updateGreenBean(){
        if(growth > 3){
            switch(growth){
                case 4:
                    greenBean.four();
                    break;
                case 5:
                    greenBean.five();
                    break;
                case 6:
                    greenBean.six();
                    break;
                default:
                    greenBean.harvest();
                    break;
            }
        }
    }
    
    void updateCoffee(){
        if(growth > 3){
            switch(growth){
                case 4:
                    coffee.four();
                    break;
                case 5:
                    coffee.five();
                    break;
                case 6:
                    coffee.six();
                    break;
                default:
                    coffee.harvest();
                    break;
            }
        }
    }
    void updateTea(){
        if(growth > 3){
            switch(growth){
                case 4:
                    tea.four();
                    break;
                case 5:
                    tea.five();
                    break;
                case 6:
                    tea.six();
                    break;
                default:
                    tea.harvest();
                    break;
            }
        }
    }
    void updateTomato(){
        if(growth > 3){
            switch(growth){
                case 4:
                    tomato.four();
                    break;
                case 5:
                    tomato.five();
                    break;
                case 6:
                    tomato.six();
                    break;
                default:
                    tomato.harvest();
                    break;
            }
        }
    }
    void updateBlueberry(){
        if(growth > 3){
            switch(growth){
                case 4:
                    blueberry.four();
                    break;
                case 5:
                    blueberry.five();
                    break;
                case 6:
                    blueberry.six();
                    break;
                default:
                    blueberry.harvest();
                    break;
            }
        }
    }
    void updateMagicFruit(){
        if(growth > 3){
            switch(growth){
                case 4:
                    magicFruit.four();
                    break;
                case 5:
                    magicFruit.five();
                    break;
                case 6:
                    magicFruit.six();
                    break;
                default:
                    magicFruit.harvest();
                    break;
            }
        }
    }
    
    void render(HiRes16Color screen){
        // If watered, we draw a dark plot
        if(water){
            screen.fillRect(renderX, renderY+16, 20, 16, 8);
        }else{
            // For dry plots, if it is NOT tilled (growth < 1) draw a light plot.
            // We return from here because no point checking anything else if empty.
            if(growth < 1) {
                screen.fillRect(renderX, renderY+16, 20, 16, 6);
                return;
            }else{
                // Tilled plots get medium brown.
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
                    sapling.seed();
                    sapling.draw(screen, renderX, renderY+16);
                    break;
                case 3:
                    sapling.saplingSmall();
                    sapling.draw(screen, renderX, renderY+16);
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
        
        if(wet > 0){
            wet--;
            screen.drawCircle(renderX+10, renderY+24, (int)(8/wet), 14);
        }
        
    }
}