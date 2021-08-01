import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.palette.Psygnosia;
import femto.font.TIC80;

import code.Globals;
import code.Main;

import code.entities.Player;

import code.stages.Title;

class Garden extends State {
    HiRes16Color screen;
    Player player;
    
    void init(){
        screen = Globals.screen;
        player = new Player();
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
        
        // pond
        screen.fillRect(0,0, 50,36, 14);
        screen.fillRect(36, 18, 30, 16, 7);
        
        // house
        screen.fillRect(140, 0, 80, 32, 4);
        screen.fillRect(130, 0, 90, 8, 12);
        
        player.render(screen);
        
        
        screen.flush();
    }
}