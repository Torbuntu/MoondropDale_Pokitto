package code.stages;

import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.font.TIC80;
import femto.sound.Stream;

import code.Globals;

import code.stages.Garden;

import sprites.Tor;
import sprites.Lol;

import femto.CPP;

class Title extends State {
    Tor tor;
    Lol lol;
    HiRes16Color screen;
    
    void init(){
        screen = Globals.screen;

        lol = new Lol();
        lol.idleDown();
        
        tor = new Tor();
        tor.idleDown();
        Stream.play("TitleScreen.raw");
        
    }
    void shutdown(){
        screen = null;
    }
    
    void update(){
        Stream.update();
        screen.clear(2);
        // Change to a new state when A is pressed
        if( Button.A.justPressed() )
            Game.changeState( new Garden() );
            
        if(Button.Left.justPressed()){
            Globals.character = 1;
        }
        if(Button.Right.justPressed()){
            Globals.character = 0;
        }
        
        if( Button.B.justPressed()){
             
            testFile();
            
        }

        // Print some text
        screen.setTextPosition( 0, 64 );
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
    
    @CPP(include="SDFileSystem.h")
    public void testFile(){
        __inline_cpp__("
            FILE *fp = fopen(\"test.txt\", \"rw\");
            if(fp == NULL) return;
            fprintf(fp, \"Hello, files!\");
            fclose(fp);
        ");
    }
}