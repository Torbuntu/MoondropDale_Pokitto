import code.Globals;

class Inventory {
    // 0:empty, 1:hoe, 2:water, 3:seed 
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
    int yam;
    int blueberry;
    int magicFruit;
    
    int monies;
    
    Inventory(){
        turnip = Globals.saveManager.turnip;
        radish = Globals.saveManager.radish;
        beet = Globals.saveManager.beet;
        daisy = Globals.saveManager.daisy;
        coffee = Globals.saveManager.coffee;
        tea = Globals.saveManager.tea;
        greenBean = Globals.saveManager.greenBean;
        tomato = Globals.saveManager.tomato;
        cranberry = Globals.saveManager.cranberry;
        yam = Globals.saveManager.yam;
        blueberry = Globals.saveManager.blueberry;
        magicFruit = Globals.saveManager.magicFruit;
        
        monies = Globals.saveManager.monies;
    }
    
    int getEquipped(){
        return equipped;
    }
    
    void setSeed(int type){
        equippedSeed = type;
    }
}