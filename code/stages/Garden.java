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
import code.entities.Crop;

import code.stages.Title;

class Garden extends State {
    HiRes16Color screen;
    Player player;
    
    Crop[] crops;
    
    int hour,dayProgress, cursor;
    
    boolean pause = false;
    
    void init(){
        screen = Globals.screen;
        player = new Player();
        byte[] field = Globals.load("field");
        dayProgress = 0;
        hour=0;
        cursor = 0;
        
        // TODO: Fix this nonsense.
        crops = new Crop[36];
        int y = 0;
        int x = 0;
        int id = 0;
        for(int i =0; i < 72; i+=2){
            if(id > 35)return;
            crops[id] = new Crop( field[i], field[i+1], x, y );
            x++;
            if(x > 5){
                x=0;
                y++;
            }
            if(y > 5)y=0;
            id++;
        }
        
    }
    
    void shutdown(){
        screen = null;
        player.dispose();
        player = null;
        crops = null;
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
            player.inventory.equipped++;
            if(player.inventory.equipped > 3){
                player.inventory.equipped = 0;
            }

        }
        
        if( Button.A.justPressed() ){
            // 0:hoe, 1:water, 2:planter, 3:other? 
            switch(player.inventory.equipped){
                case 0:
                    if(player.x >= 20 && player.x < 140 
                    && player.y >= 64 && player.y < 160){
                        int x = (player.x - 20)/20;
                        int y = (player.y - 64)/16;
                        // this will remove any growing crop and un-water
                        crops[x+y*6].type=0;
                        crops[x+y*6].growth = 1;
                        crops[x+y*6].water = false;
                    }
                    break;
                case 1:
                    if(player.x >= 20 && player.x < 140 
                    && player.y >= 64 && player.y < 160){
                        int x = (player.x - 20)/20;
                        int y = (player.y - 64)/16;
                        // only water tilled soil and crops
                        if(crops[x+y*6].growth > 0){
                            crops[x+y*6].water = true;    
                        }
                    }
                    break;
                case 2:
                    if(player.x >= 20 && player.x < 140 
                    && player.y >= 64 && player.y < 160){
                        int x = (player.x - 20)/20;
                        int y = (player.y - 64)/16;
                        // only plant a crop if the soil is tilled and no plant exists
                        if(crops[x+y*6].type == 0 && crops[x+y*6].growth > 0){
                            crops[x+y*6].plant(player.inventory.equippedSeed);
                        }
                    }
                    break;
                case 3:
                    // Hand for harvesting
                    
                    break;
            }

        }
        
        if( Button.C.justPressed() ){
            pause = !pause;
        }
        
        // handle movement
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
        
        // Render
        screen.clear(7);
        
        // pond
        screen.fillRect(0,0, 50,36, 14);
        screen.fillRect(36, 18, 30, 16, 8);
        
        // house
        screen.fillRect(140, 0, 80, 32, 4);
        screen.fillRect(130, 0, 90, 8, 12);
        
        // field zone
        screen.drawRect(19, 63, 121, 97, 5);
        
        // crops
        for(Crop c:crops){
            screen.drawRect(20+c.getX()*20, 64+c.getY()*16, 20, 16, c.getType()+5);
            if(c.renderY < player.getRenderY() || c.type == 0)c.render(screen);
        }
        
        // Render player in between plants that are over or under.
        player.render(screen);
        
        for(Crop c:crops){
            if(c.renderY > player.getRenderY() && c.type > 0) c.render(screen);
        }
        
        // Render the HUD (equipped item, seed amount, Monies);
        screen.drawRect(screen.width()-40, screen.height()-16, 20, 16, 3);
        player.inventory.drawHud(screen);
        
        
        screen.drawRect(screen.width()-20, screen.height()-16, 20, 16, 3);
        screen.setTextColor(1);
        screen.setTextPosition(screen.width()-38, screen.height()-16);
        switch(player.inventory.equipped){
            case 0:
                screen.print("H");
                break;
            case 1:
                screen.print("W");
                break;
            case 2:
                screen.print("P");
                break;
            case 3:
                screen.print("?");
                break;
        }
        
        screen.setTextPosition(screen.width()-18, screen.height()-16);
        screen.print(player.inventory.equippedSeed);
        
        // Render day progression
        screen.drawHLine(0, screen.height()-1, dayProgress, 12);
        
        screen.flush();
    }
    
    void handheldMenu(){
        screen.fillRect(30, 30, screen.width()-60, screen.height()-60, 9);
        
        screen.fillRect(15, 60, screen.width()-30, 20, 9);
        
        screen.fillRect(40, 40, screen.width()-80, screen.height()-80, 5);
        screen.fillRect(42, 42, screen.width()-84, screen.height()-84, 1);
        screen.setTextColor(10);
        screen.setTextPosition(43, 43);
        screen.print("-- Menu --");
        
        screen.setTextPosition(45, 43+8);
        if(cursor == 0)screen.print("> ");
        screen.print("Seed to plant: " + player.inventory.equippedSeed);
        
        screen.setTextPosition(45, 43+16);
        if(cursor == 1)screen.print("> ");
        screen.print("Save & Quit");
        
        if(Button.Up.justPressed())cursor--;
        if(Button.Down.justPressed())cursor++;
        if(cursor < 0)cursor = 0;
        if(cursor > 4)cursor = 4;
        
        
        if(Button.Left.justPressed()){
            
        }
        if(Button.Right.justPressed()){
            
        }
        
        if(Button.A.justPressed()){
            if(cursor == 1){
                saveAndQuit();
            }
        }
        
        if(Button.B.justPressed()){
            player.inventory.equippedSeed++;
            if(player.inventory.equippedSeed > 9)player.inventory.equippedSeed = 0;
        }
        
        if(Button.C.justPressed())pause=false;
        
        screen.flush();
    }
    
    void saveAndQuit(){
        byte[] save = new byte[crops.length * 2];
        int id = 0;
        for(int i = 0; i < 72; i+=2){
            save[i] = (byte)crops[id].getType();
            save[i+1] = (byte)crops[id].getGrowth();
            id++;
        }
        Globals.save("field", save);
        
        Game.changeState( new Title() );
    }


}