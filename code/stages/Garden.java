package code.stages;

import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.palette.Psygnosia;
import femto.font.TIC80;

import code.Globals;
import code.Main;

import code.entities.Player;

import code.managers.Inventory;
import code.managers.CropManager;

import code.stages.Title;

import sprites.Hoe;
import sprites.Planter;
import sprites.Water;
import sprites.Basket;
import sprites.FishingRod;
import sprites.Pond;
import sprites.Wave;

import images.Arm;
import images.Corner;
import images.ActionIcon;
import images.GrassV;
import images.GrassH;
import images.Stone;
import images.Door;

class Garden extends State {
    
    HiRes16Color screen;
    
    CropManager cropManager;
    Player player;
    Inventory inventory;
    GrassV grassV;
    GrassH grassH;
    Stone stone;
    Door door;
    ActionIcon actionIcon;

    Hoe hoe;
    byte swingHoe = 0;
    
    Planter planter;
    byte swingPlanter = 0;
    
    Water water;
    byte swingWater = 0;
    
    Basket basket;
    byte swingBasket = 0;
    
    FishingRod rod;
    byte swingRod = 0;
    
    Pond pond;
    Wave wave;
    Arm arm = new Arm();
    Corner corner = new Corner();
    
    byte hour, cursor, menuOffset;
    short day, dayProgress;
    
    // Used in the checkTool `switch` to avoid lots of `if` checks
    byte tool = 1;
    
    // 0: regular, 1: pause/menu, 2: summary, 3: fishing
    byte gameState = 0;
    
    boolean exit, move = true, shop = false, summary = false;
    boolean raining = false;
    boolean fishingAvailable, catchSuccess = false;
    byte caughtSize, catchTime, fishTime;
    
    final private short w = 10, h = 8;

    // Field plots arrays. 
    byte[] type;
    byte[] growth;
    byte[] watered;
    
    void init(){
        screen = Globals.screen;
        day = Globals.saveManager.day;
        fishingAvailable = Globals.saveManager.fishing;
        byte playerId = Globals.character == 0 ? 2 : Globals.character;
        player = new Player(playerId);
        inventory = new Inventory();
        cropManager = new CropManager();

        dayProgress = 0;
        hour=0;
        cursor = 0;

        byte[] field = Globals.load("field");

        type = new byte[120];
        growth = new byte[120];
        watered = new byte[120];
        byte id = 0;
        for(int i =0; i < (120*2); i+=2){
            //System.out.println(id);
            if(id > 120)return;
            watered[id] = 0;
            type[id] = field[i];
            growth[id] = field[i+1];
            id++;
        }
        
        grassV = new GrassV();
        grassH = new GrassH();
        stone = new Stone();
        door = new Door();//huehue
        actionIcon = new ActionIcon();
        pond = new Pond();
        pond.wave();
        wave = new Wave();
        wave.wave();
        
        hoe = new Hoe();
        planter = new Planter();
        water = new Water();
        basket = new Basket();
        rod = new FishingRod();
        rod.swing();
        
        // TODO: I need the crops and their level of growth in here...
        
    }
    
    void shutdown(){
        screen = null;
        player.dispose();
        player = null;
        inventory = null;
        grassH = null;
        grassV = null;
        pond = null;
        hoe = null;
        planter = null;
        water = null;
        basket = null;
        rod = null;
    }
    
    void update(){
        
        // 0: regular, 1: pause/menu, 2: summary, 3: fishing
        switch(gameState){
            case 0:render();updatePrimaryDay();break;
            case 1:render();updateHandheldMenu();break;
            case 2:render();updateFishing();break;
            case 3:updateSummary();break;
        }
        
        if(gameState != 3){
            // UI box
            screen.fillRect(0, 152, 220, 24, 5);
            // Render the HUD (equipped item, seed amount, Monies);
            inventory.drawHud(screen, fishingAvailable);
            
            // Render day progression.
            // TODO: Make this a fancy gui with a "sun" -> "moon" etc...
            screen.drawHLine(0, 175, dayProgress, 12);
        }

        screen.flush();
    }
    
    void updatePrimaryDay(){
        
        hour++;
        if(hour > 10){
            dayProgress++;
            hour = 0;
        }
        
        if(dayProgress > 200){
            dayProgress = 0;
            raining = (Math.random(0,3)==1);

            for(byte i = 0; i < 120; i++){
                if(growth[i] > 0){
                    if(type[i] > 0 && watered[i]==1){
                        if(growth[i] < 9)growth[i]++;
                        //growth[i] = growth[i] < 9 ? growth[i]++ : 9;
                        System.out.println("GROW!"+ (int)growth[i]);
                    }
                    // set water based on raining
                    watered[i] = raining ? 1:0;
                }
            }

            gameState = 3;
            return;
        }
        
        // Shift equipped item
        if(Button.B.justPressed()){
            inventory.equipped++;
            if(inventory.equipped > 4){
                if(fishingAvailable){
                    inventory.equipped = 0;
                }else{
                    inventory.equipped = 1;
                }
            }
        }
        
        if( Button.C.justPressed() ){
            gameState = 1;
            menuOffset = 120;
            exit = false;
        }
        
        // handle movement
        if( move ){
            if( Button.A.justPressed() ){
                // 0:fishing rod, 1:hoe, 2:water, 3:basket, 4:planter 
                switch(inventory.equipped){
                    case 0:useFishingRod();break;
                    case 1:useHoe();break;
                    case 2:useWater();break;
                    case 3:useBasket();break;
                    case 4:usePlanter();break;
                }
            }
            if(Button.Left.justPressed()){
                player.moveLeft();
            }
            if(Button.Right.justPressed()){
                player.moveRight();
            }
            if(Button.Up.justPressed()){
                player.moveUp();
            }
            if(Button.Down.justPressed()){
                player.moveDown();
            }
        }
        
    }
    
    void render(){
        // -- Render --
        if(raining)screen.clear(11);
        else screen.clear(10);
        //screen.clear(10);
        
        renderScene();
        
        // crops
        for(int i = 0; i < 120; i++){
            byte x = (i%12);
            byte y = (i/12);
            byte id = x+y*12;
            if(type[id] == 0 && growth[id] == 0)continue;
            if(growth[id] > 0){
                if(watered[id] == 1) screen.fillRect(50+x*10, 64+y*8, 10, 8, 8);
                else screen.fillRect(50+x*10, 64+y*8, 10, 8, 7);
            }else{
                screen.fillRect(50+x*10, 64+y*8, 10, 8, 6);
                continue;
            }
            if(type[id] > 0)renderCrop(id, 50+x*10, 64+y*8);
        }
        
        // Render player in between plants that are over or under.
        player.render(screen);

        // Swinging tools and render if active
        checkToolUse();
        
        // Always render the cursor on top of all the crops
        if(inField()){
            player.renderCursor(screen);
        }

    }
    
    void updateSummary(){
        screen.clear(1);
        if(Button.A.justPressed() || Button.B.justPressed() || Button.C.justPressed()){
            gameState = 0;
            day++;
            player.x = 160;
            player.y = 32;
        }
        
        dialog("The day is over. You rest.");
        if(raining){
            dialogLine("It rains during the night.");
        }
        
    }
    
    /**
     * 1 - turnip
     * 2 - radish
     * 3 - daisy
     * 4 - coffee
     * 5 - tea
     * 6 - greenBean
     * 7 - tomato
     * 8 - blueberry
     * 9 - magicFruit
     * 
     */ 
    void renderCrop(byte id, short x, short y){
        cropManager.draw(screen, type[id], growth[id], x, y);
    }
    
    void seedShop(){
        screen.setTextColor(10);
        screen.setTextPosition(43, 43);
        screen.print("-- Seed Shop --");
        
        screen.setTextPosition(45, 56);
        screen.print("$" + inventory.monies);
        
        // TODO: get seed cost for math
        
        screen.setTextPosition(45, 64);
        if(cursor == 10)screen.print("- $100");
        else screen.print("- $"+(int)inventory.cost[cursor]);
        
        
        // List available seeds.
        screen.setTextPosition(45, 80);
        if(cursor < 9){
            screen.print("> Seed:");
            inventory.drawSeed(screen, cursor);
        } else {
            screen.print("Seed:");
        }
        
        if(!fishingAvailable){
            screen.setTextPosition(45, 100);
            if(cursor == 10) screen.print("> ");
            screen.print("Fishing Rod: ");
            inventory.fishingIcon.draw(screen, 145, 100);
        }
        
        screen.setTextPosition(45, 126);
        screen.print("[B - Back]");
        
        if(Button.Down.justPressed()){
            cursor = 10;
        }
        if(Button.Up.justPressed()){
            cursor = 0;
        }
        
        if(Button.Right.justPressed()){
            if(cursor < 8)cursor++;
        }
        if(Button.Left.justPressed()){
            if(cursor > 0)cursor--;
        }
        
        if(Button.A.justPressed()){
            if(cursor > 9 ){
                if(inventory.monies > 100){
                    fishingAvailable = true;
                    inventory.monies -= 100;
                    // Play cach chaching
                } else {
                    // Play broke donk
                }
            } else {
                if(inventory.buySuccess(cursor)){
                    // Play cash chaching
                } else {
                    // Play donk sound for no monies
                }
            }
        }
        
        if(Button.B.justPressed()){
            shop = false;
            cursor = 1;
        }
    }
    
    void updateHandheldMenu(){
        
        // TODO: Replace manual drawing with nice device images.
        screen.drawHLine(40,20+menuOffset,140,1);
        screen.fillRect(40, 21+menuOffset, 140, 20, 9);
        corner.draw(screen, 20, 20+menuOffset, false, false, true);
        corner.draw(screen, 180, 20+menuOffset, true, false, true);
        
        // Left border
        screen.drawVLine(20, 40+menuOffset, 112, 1);
        screen.fillRect(21, 40+menuOffset, 19, 112, 9);
        
        // Right border
        screen.drawVLine(199, 40+menuOffset, 112, 1);
        screen.fillRect(180, 40+menuOffset, 19, 112, 9);
        
        // arms
        arm.draw(screen, 1, 90+menuOffset, false, false, true);
        arm.draw(screen, 199, 90+menuOffset, true, false, true);
        
        // Render handheld screen
        screen.fillRect(40, 40+menuOffset, 140, 96, 5);
        screen.fillRect(42, 42+menuOffset, 136, 92, 1);
        
        // Bottom border
        screen.fillRect(40, 136+menuOffset, 140, 16, 9, true);
        
        if(!exit && menuOffset > 0){
            menuOffset -= 10;
            return;
        }
        
        if(exit && menuOffset < 120){
            menuOffset+=10;
            if(menuOffset == 120)gameState=0;
            return;
        }
        
        if(shop){
            seedShop();
            return;
        }
        
        screen.setTextColor(10);
        screen.setTextPosition(43, 43);
        screen.print("-- Menu --");
        
        // Seed Select (cursor == 1)
        screen.setTextPosition(45, 80);
        if(cursor == 0)screen.print("> ");
        screen.print("Seed:");
        inventory.drawSeed(screen);
        
        // Seed Shop (cursor == 2)
        screen.setTextPosition(45, 116);
        if(cursor == 1)screen.print("> ");
        screen.print("Enter Shop");
        
        // Save and quit (cursor == 3)
        screen.setTextPosition(45, 126);
        if(cursor == 2)screen.print("> ");
        screen.print("Save & Quit");
        
        if(Button.Up.justPressed())cursor--;
        if(Button.Down.justPressed())cursor++;
        if(cursor < 0)cursor = 0;
        if(cursor > 2)cursor = 2;
        
        
        if(Button.Left.justPressed()){
            if(cursor == 0){
                if(inventory.equippedSeed > 0){
                    if(inventory.locks[inventory.equippedSeed-1]){
                        inventory.equippedSeed--;
                    }
                }
            }
        }
        if(Button.Right.justPressed()){
            if(cursor == 0){
                
                if(inventory.equippedSeed < 8){
                    if(inventory.locks[inventory.equippedSeed+1]){
                        inventory.equippedSeed++;
                    }
                }
            }
        }
        
        if(Button.A.justPressed()){
            if(cursor == 1){
                cursor = 0;
                shop = true;
            }
            if(cursor == 2){
                saveAndQuit();
            }
        }
        
        if(Button.B.justPressed() || Button.C.justPressed()){
            exit = true;
        }
        
    }
    
    /**
     * Set the time before fish is ON!
     * Switch to fishing game state.
     * 
     */ 
    void useFishingRod(){
        if(player.x < 30 && player.face == 0){
            //swing fishing rod
            fishTime = Math.random(30, 90);
            gameState = 2;
            tool = 0;
        }
    }
    
    void useHoe(){
        if(inField()){
            // this will remove any growing crop and un-water
            //crops[getFieldId()].till();
            short id = getFieldId();
            if(type[id] == 0 && growth[id] == 0){
                growth[id] = 1;
            }
            swingHoe = 20;
            move = false;
            if(player.face == 2)hoe.setMirrored(true);
            else hoe.setMirrored(false);
            tool = 1;
        }
    }
    
    void useWater(){
        // If at the pond, fill the water
        if(player.x < 40)inventory.fill = 8;
        
        else if(inField() && inventory.fill > 0){
            short id = getFieldId();
            if(growth[id] > 0){
                inventory.fill--;
                watered[id] = 1;
                if(player.face == 2)water.setMirrored(true);
                else water.setMirrored(false);
                swingWater = 20;
                move = false;
                tool = 2;
            }
        }
    }
    
    void useBasket(){
        // Basket for harvesting
        if(inField()){
            short id = getFieldId();
            if(type[id] == 0)return;
            // TODO: Check for type and growth to determine if ready for harvest
            switch(type[id]){
                case 0: return;break;//Nothing to do here.
                case 1: if(growth[id] < 5)return;break;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8: if(growth[id] < 7)return;
            }
            
            watered[id] = 0;
            growth[id] = 0;
            inventory.harvest(type[id]);
            type[id] = 0;

            if(player.face == 2)basket.setMirrored(true);
            else basket.setMirrored(false);
            swingBasket = 20;
            move = false;
            tool = 3;
        }
    }
    
    void usePlanter(){
        if(inField() && inventory.hasQuantity()){
            short id = getFieldId();
            // only plant a crop if the soil is tilled and no plant exists
            if(type[id] == 0 && growth[id] == 1){
                if(player.face == 2)planter.setMirrored(true);
                else planter.setMirrored(false);
                type[id]=(inventory.equippedSeed+1);
                growth[id] = 2;
                inventory.planted();
                swingPlanter = 20;
                move = false;
                tool = 4;
            }
        }
    }
    
    /**
     * Check if the player is inside the field area
     */ 
    boolean inField(){
        return (player.x >= 50 && player.x < 170 
        && player.y >= 64 && player.y < 140);
    }
    
    /**
     * Get the Crop ID based on player's position 
     */ 
    short getFieldId(){
        short x = (player.x - 50)/w;
        short y = (player.y - 64)/h;
        // 12 is the width of the field
        short id = x+y*12;
        System.out.println(id);
        return id;
    }
    
    void checkToolUse(){
        switch(tool){
            case 0:
                break;
            case 1:
                if(swingHoe > 0){
                    swingHoe--;
                    hoe.swing();
                    hoe.draw(screen, player.x, player.y);
                    if(swingHoe == 0) move = true;
                }
                break;
            case 2:
                if(swingWater > 0){
                    swingWater--;
                    water.swing();
                    water.draw(screen, player.x, player.y);
                    if(swingWater == 0) move = true;
                }
                break;
            case 3:
                if(swingBasket > 0){
                    swingBasket--;
                    basket.swing();
                    basket.draw(screen, player.x, player.y);
                    if(swingBasket == 0)move = true;
                }
                break;
            case 4:
                if(swingPlanter > 0){
                    swingPlanter--;
                    planter.swing();
                    planter.draw(screen, player.x, player.y);
                    if(swingPlanter == 0) move = true;
                }
                break;
        }
    }
    
    void renderScene(){

        // Pond
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 19; y++){
                pond.draw(screen, x*w, y*h);
            }
        }
        for(int i = 0; i < 19; i++){
            wave.draw(screen, 28, i*h);
        }
        //System.out.println(screen.fps());
        
        // house
        // TODO: Make an actual house graphic
        //screen.fillRect(140, 0, 80, 32, 4);
        for(int i = 0; i < 8; i++){
            stone.draw(screen, 140+i*10, 8);
            stone.draw(screen, 140+i*10, 16);
        }
        door.draw(screen, 160, 8);
        
        // Roof
        // TODO: Make real roof graphics
        screen.drawRect(129, 0, 92, 8, 1);
        screen.fillRect(130, 0, 90, 8, 12);
        
        // field zone
        // TODO: replace this with untilled soil sprite
        screen.fillRect(50, 64, 120, 80, 3);
        
        for(int i = 0; i < 12; i++){
            grassH.draw(screen, 50+i*10, 60);
            grassH.draw(screen, 50+i*10, 142, true, true, false);
        }
        for(int i = 0; i < 10; i++){
            grassV.draw(screen, 46, 64+i*8);
            grassV.draw(screen, 168, 64+i*8, true, false, false);
        }
    }
    
    /**
     * Before updateFishing is entered, a random int for fishTime is set.
     * when fishTime is 0, we play hook and set to -1 to not play it again.
     * Then catchTime is set and the player must press `A` before it is over.
     * 
     * If successfull, we give a random money for fish size.
     */ 
    void updateFishing(){
        
        if(catchSuccess){
            if(Button.B.justPressed()){
                catchSuccess = false;
                inventory.monies += (int)(caughtSize * 2);
                gameState = 0;
            }
            dialog("You caught a fish!\n");
            dialogLine("It is " + (int)caughtSize +"cm! You sell for $" + (int)(caughtSize * 2));
            return;
        }
        
        rod.draw(screen, player.x+4, player.y-2);
        
        if(fishTime > 0)fishTime--;
        if(fishTime == 0){
            fishTime--;
            // play hook sound!
            catchTime = 15;
        }
        
        if(catchTime > 0){
            actionIcon.draw(screen, player.x+11, player.y-14);
            catchTime--;
            if(Button.A.justPressed()){
                caughtSize = Math.random(2, 15);
                catchSuccess = true;
                catchTime = 0;
            }
        }else if (fishTime < 0){
            dialog("The fish got away...");
            if(Button.B.justPressed()){
                gameState = 0;
                catchSuccess = false;
            }
        }
    }
    
    void dialog(String text){
        screen.fillRect(0, 0, 220, 30, 1);
        screen.drawRect(0,0,219,29,5);
        screen.setTextPosition(3, 3);
        screen.setTextColor(2);
        screen.print(text);
        
        screen.setTextPosition(200, 22);
        screen.print("[B]");
    }
    void dialogLine(String text){
        screen.setTextPosition(3, 11);
        screen.print(text);
    }
    
    /**
     * Collect crops type and growth into a byte array. 
     * crops.length*2 because each crop has 2 pieces of data (type, growth).
     * 
     * Globals.save will write the data to the "field" file.
     * 
     * Quit to the title screen.
     */ 
    void saveAndQuit(){
        // Collect field to byte array
        byte[] field = new byte[120 * 2];
        byte id = 0;
        for(int i = 0; i < (120*2); i+=2){
            field[i] = (byte)type[id];
            field[i+1] = (byte)growth[id];
            id++;
        }
        
        // Collect items to byte array
        byte[] items = new byte[16];
        id = 0;
        for(int j = 0; j < 16; j+=2){
            items[j] = inventory.locks[id] ? 1 : 0;
            items[j+1] = inventory.quantities[id];
            id++;
        }
        Globals.saveManager.fishing = fishingAvailable;
        Globals.save(field, items, inventory.monies, day);
        
        Game.changeState( new Title() );
    }


}