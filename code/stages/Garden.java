package code.stages;

import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.palette.Psygnosia;
import femto.font.TIC80;

import images.Grass;

import code.Globals;
import code.Main;

import code.entities.Player;
import code.entities.Crop;

import code.managers.Inventory;

import code.stages.Title;

import sprites.Hoe;
import sprites.Planter;
import sprites.Water;
import sprites.Pond;
import sprites.Basket;

class Garden extends State {
    HiRes16Color screen;
    Player player;
    Inventory inventory;
    Grass grass;
    Crop[] crops;
    Hoe hoe;
    int swingHoe = 0;
    
    Planter planter;
    int swingPlanter = 0;
    
    Water water;
    int swingWater = 0;
    
    Basket basket;
    int swingBasket = 0;
    
    Pond pond;
    
    int hour, dayProgress, cursor, day;
    
    boolean pause = false, move = true, shop = false;
    
    final private int w = 10, h = 8;
    
    void init(){
        screen = Globals.screen;
        day = Globals.saveManager.day;
        int playerId = Globals.character == 0 ? 2 : Globals.character;
        player = new Player(playerId);
        inventory = new Inventory();
       
        grass = new Grass();
        dayProgress = 0;
        hour=0;
        cursor = 0;
        // TODO: Fix this nonsense.
        byte[] field = Globals.load("field");
        crops = new Crop[80];
        int y = 0;
        int x = 0;
        int id = 0;
        for(int i =0; i < 160; i+=2){
            System.out.println(id);
            if(id > 80)return;
            crops[id] = new Crop( field[i], field[i+1], x, y );
            x++;
            if(x > 5){
                x=0;
                y++;
            }
            if(y > 5)y=0;
            id++;
        }

        pond = new Pond();
        pond.wave();
        
        hoe = new Hoe();
        planter = new Planter();
        water = new Water();
        basket = new Basket();
    }
    
    void shutdown(){
        screen = null;
        player.dispose();
        player = null;
        inventory = null;
        crops = null;
        grass = null;
        
        pond = null;
        hoe = null;
        planter = null;
        water = null;
        basket = null;
    }
    
    void update(){
        if(pause){
            handheldMenu();
            // We don't want to progress with any other details while in the handheld menu.
            return;
        }
        hour++;
        if(hour > 10){
            dayProgress++;
            hour = 0;
        }
        
        if(dayProgress > 220){
            dayProgress = 0;
            for(Crop c: crops){
                c.update();
            }
        }
        
        // Shift equipped item
        if(Button.B.justPressed()){
            inventory.equipped++;
            if(inventory.equipped > 3){
                inventory.equipped = 0;
            }
        }
        

        
        if( Button.C.justPressed() ){
            pause = !pause;
        }
        
        // handle movement
        if( move ){
            if( Button.A.justPressed() ){
                // 0:hoe, 1:water, 2:planter, 3:other? 
                switch(inventory.equipped){
                    case 0:
                        useHoe();
                        break;
                    case 1:
                        useWater();
                        break;
                    case 2:
                        usePlanter();
                        break;
                    case 3:
                        useBasket();
                        break;
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
        
        // -- Render --
        screen.clear(7);
        
        renderScene();
        
        // crops
        for(Crop c:crops){
            if(c.renderY < player.getRenderY() || c.type == 0)c.render(screen);
        }
        
        // Render player in between plants that are over or under.
        player.render(screen);

        
        for(Crop c:crops){
            if(c.renderY > player.getRenderY() && c.type > 0) c.render(screen);
        }
        
        // Swinging tools and render if active
        checkToolUse();
        
        // Always render the cursor on top of all the crops
        player.renderCursor(screen);
        
        // Render the HUD (equipped item, seed amount, Monies);
        inventory.drawHud(screen);
        
        // Render day progression
        screen.drawHLine(0, 175, dayProgress, 12);
        
        screen.flush();
    }
    
    void seedShop(){
        screen.setTextColor(10);
        screen.setTextPosition(43, 43);
        screen.print("-- Seed Shop --");
        
        screen.setTextPosition(45, 56);
        screen.print("$$: " + inventory.monies);
        
        // List available seeds.
        screen.setTextPosition(45, 78);
        screen.print("> Seed to Buy: ");
        inventory.drawSeed(screen, cursor);
        
        screen.setTextPosition(45, 126);
        screen.print("[B - Back]");
        
        if(Button.Right.justPressed()){
            if(cursor < 8)cursor++;
            else cursor = 0;
        }
        if(Button.Left.justPressed()){
            if(cursor > 0)cursor--;
            else cursor = 8;
        }
        
        if(Button.A.justPressed()){
            if(inventory.buySuccess(cursor)){
                // Play cash chaching
            }else{
                // Play donk sound for no monies
            }
        }
        
        if(Button.B.justPressed()){
            shop = false;
            cursor = 2;
        }
        screen.flush();
    }
    
    void handheldMenu(){
        // TODO: Replace manual drawing with nice device images.
        screen.fillRect(30, 30, 160, 116, 9);
        
        screen.fillRect(15, 60, 190, 20, 9);
        
        screen.fillRect(40, 40, 140, 96, 5);
        screen.fillRect(42, 42, 136, 92, 1);
        if(shop){
            seedShop();
            return;
        }
        
        screen.setTextColor(10);
        screen.setTextPosition(43, 43);
        screen.print("-- Menu --");
        
        // Tool Select (cursor == 0)
        screen.setTextPosition(45, 56);
        if(cursor == 0)screen.print("> ");
        screen.print("Tool Equipped: ");
        inventory.drawTool(screen);
        
        // Seed Select (cursor == 1)
        screen.setTextPosition(45, 78);
        if(cursor == 1)screen.print("> ");
        screen.print("Seed to Plant: ");
        inventory.drawSeed(screen);
        
        // Seed Shop (cursor == 2)
        screen.setTextPosition(45, 100);
        if(cursor == 2)screen.print("> ");
        screen.print("Enter Seed Shop");
        
        // Save and quit (cursor == 3)
        screen.setTextPosition(45, 126);
        if(cursor == 3)screen.print("> ");
        screen.print("Save & Quit");
        
        if(Button.Up.justPressed())cursor--;
        if(Button.Down.justPressed())cursor++;
        if(cursor < 0)cursor = 0;
        if(cursor > 4)cursor = 4;
        
        
        if(Button.Left.justPressed()){
            if(cursor==0){
                if(inventory.equipped > 0){
                    inventory.equipped--;
                }
            }
            if(cursor == 1){
                inventory.equippedSeed--;
                if(inventory.equippedSeed < 0)inventory.equippedSeed = 8;
            }
        }
        if(Button.Right.justPressed()){
            if(cursor==0){
                if(inventory.equipped < 3){
                    inventory.equipped++;
                }
            }
            if(cursor == 1){
                inventory.equippedSeed++;
                if(inventory.equippedSeed > 8)inventory.equippedSeed = 0;
            }
        }
        
        if(Button.A.justPressed()){
            if(cursor == 2){
                cursor = 0;
                shop = true;
            }
            if(cursor == 3){
                saveAndQuit();
            }
        }
        
        if(Button.B.justPressed()){
            pause = false;
        }
        
        if(Button.C.justPressed())pause=false;
        
        screen.flush();
    }
    
    void useHoe(){
        if(inField()){
            // this will remove any growing crop and un-water
            crops[getFieldId()].till();
            swingHoe = 20;
            move = false;
            if(player.face == 2)hoe.setMirrored(true);
            else hoe.setMirrored(false);
        }
    }
    
    void useWater(){
        // If at the pond, fill the water
        if(player.x < 50 && player.y < 36)inventory.fill = 8;
        
        else if(inField() && inventory.fill > 0){
            int id = getFieldId();
            // only water tilled soil and crops
            if(crops[id].growth > 0){
                inventory.fill--;
                crops[id].setWater();
                if(player.face == 2)water.setMirrored(true);
                else water.setMirrored(false);
                swingWater = 20;
                move = false;
            }
        }
    }
    
    void usePlanter(){
        if(inField() && inventory.hasQuantity()){
            int id = getFieldId();
            // only plant a crop if the soil is tilled and no plant exists
            if(crops[id].type == 0 && crops[id].growth == 1){
                if(player.face == 2)planter.setMirrored(true);
                else planter.setMirrored(false);
                crops[id].plant(inventory.equippedSeed+1);
                inventory.planted();
                swingPlanter = 20;
                move = false;
            }
        }
    }
    
    void useBasket(){
        // Basket for harvesting
        if(inField()){
            // this will remove any growing crop and un-water
            inventory.harvest(crops[getFieldId()].harvest());

            if(player.face == 2)basket.setMirrored(true);
            else basket.setMirrored(false);
            swingBasket = 20;
            move = false;
        }
    }
    
    /**
     * Check if the player is inside the field area
     */ 
    boolean inField(){
        return (player.x >= 20 && player.x < 140 
        && player.y >= 64 && player.y < 160);
    }
    
    /**
     * Get the Crop ID based on player's position 
     */ 
    int getFieldId(){
        int x = (player.x - w)/w;
        int y = (player.y - 64)/h;
        
        return  x+y*6;
    }
    
    void checkToolUse(){
        if(swingHoe > 0){
            swingHoe--;
            hoe.swing();
            hoe.draw(screen, player.x, player.y);
            if(swingHoe == 0) move = true;
        }
        if(swingPlanter > 0){
            swingPlanter--;
            planter.swing();
            planter.draw(screen, player.x, player.y);
            if(swingPlanter == 0) move = true;
        }
        if(swingWater > 0){
            swingWater--;
            water.swing();
            water.draw(screen, player.x, player.y);
            if(swingWater == 0) move = true;
        }
        if(swingBasket > 0){
            swingBasket--;
            basket.swing();
            basket.draw(screen, player.x, player.y);
            if(swingBasket == 0)move = true;
        }
    }
    
    void renderScene(){
        // Grass
        //Vertical columns
        for(int j = 0; j < 22; j++){
            // Horizontal rows
            for(int i = 0; i < 22; i++){
                grass.draw(screen, i*w, j*h);
            }
        }
        
        // Pond
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                pond.draw(screen, x*w, y*h);
            }
        }
        
        // house
        screen.fillRect(140, 0, 80, 32, 4);
        screen.fillRect(130, 0, 90, 8, 12);
        
        // field zone
        screen.drawRect(19, 63, 121, 97, 5);
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
        byte[] field = new byte[crops.length * 2];
        int id = 0;
        for(int i = 0; i < 72; i+=2){
            field[i] = (byte)crops[id].type;
            field[i+1] = (byte)crops[id].growth;
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
        
        Globals.save(field, items, inventory.monies, day);
        
        Game.changeState( new Title() );
    }


}