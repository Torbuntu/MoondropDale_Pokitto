package code.stages;

import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.font.TIC80;
import femto.sound.Stream;
import femto.sound.Mixer;

import code.Globals;

import code.stages.Garden;

import sprites.Tor;
import sprites.Lol;
import sprites.TitleBanner;

class Title extends State {
    Tor tor;
    Lol lol;
    TitleBanner title;
    HiRes16Color screen;
    
    boolean playing = false;
    
    void init(){
        screen = Globals.screen;

        lol = new Lol();
        lol.idleDown();
        
        tor = new Tor();
        tor.idleDown();
        
        title = new TitleBanner();
        title.idle();
        
        Mixer.init(8000);
        playing = Stream.play("music/mddal.raw");
    }
    
    void shutdown(){
        screen = null;
        title = null;
        tor = null;
        lol = null;
    }
    
    void update(){
        Stream.update();
        
        screen.clear(10);
        screen.setTextColor(2);
        // Change to a new state when A is pressed
        if( Button.A.justPressed() )
            Game.changeState( new Garden() );
            
        if(Button.Left.justPressed()){
            Globals.character = 1;
        }
        if(Button.Right.justPressed()){
            Globals.character = 0;
        }
        
        title.draw(screen,0,0);

        // Print some text
        screen.setTextPosition( 0, 84 );
        screen.println("Welcome to Moondrop Dale!");
        screen.println("Select a player: ");
        
        if(Globals.character == 1){
            screen.drawRect(38, 98, 24, 26, 14);
            screen.setTextPosition(0, 100);
            screen.print("Tor!");
        }else{
            screen.drawRect(98, 98, 24, 26, 14);
            screen.setTextPosition(0, 100);
            screen.print("Lol!");
        }
        
        tor.draw(screen, 40, 100);
        lol.draw(screen, 100, 100);
        
        // Update the screen with everything that was drawn
        screen.flush();
    }

}