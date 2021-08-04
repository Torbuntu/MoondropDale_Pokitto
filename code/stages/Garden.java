package code.stages;

import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.palette.Psygnosia;
import femto.font.TIC80;
import femto.File;

import code.Globals;
import code.Main;

import code.entities.Player;
import code.entities.Crop;

import code.stages.Title;


class Garden extends State {
    HiRes16Color screen;
    Player player;
    
    Crop[] crops;
    
    void init(){
        screen = Globals.screen;
        player = new Player();
        byte[] field = readFile("field");

        // TODO: Fix this nonsense.
        crops = new Crop[36];
        int y = 0;
        int x = 0;
        int id = 0;
        for(int i =0; i < 72; i+=2){
            if(id > 35)return;
            System.out.print(field[i]); 
            crops[id] = new Crop(
                field[i], 
                field[i+1], 
                x, y);
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
    }
    
    void update(){
        if(Button.A.justPressed()){
            Game.changeState( new Title() );
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
        screen.clear(3);
        
        // crops
        for(Crop c:crops){
            screen.drawRect(20+c.getX()*20, 64+c.getY()*16, 20, 16, c.getType()+5);
        }
        
        // pond
        screen.fillRect(0,0, 50,36, 14);
        screen.fillRect(36, 18, 30, 16, 7);
        
        // house
        screen.fillRect(140, 0, 80, 32, 4);
        screen.fillRect(130, 0, 90, 8, 12);
        
        player.render(screen);
        
        
        screen.flush();
    }

    public byte[] readFile(String path){
        File file = new File();
        byte[] result;
        if(file.openRO(path)){
            result = file.toArray();
        }
        file.close();
        return result;
    }
}