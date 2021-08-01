import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.font.TIC80;

import code.Globals;

import code.stages.Garden;

class Main extends State {

    HiRes16Color screen; // the screenmode we want to draw with


    // start the game using Main as the initial state
    // and TIC80 as the menu's font
    public static void main(String[] args){
        System.out.println("Shutdown screen");
        Game.run( TIC80.font(), new Main() );
    }
    
    // Avoid allocation in a State's constructor.
    // Allocate on init instead.
    void init(){
        screen = Globals.screen;
    }
    
    // Might help in certain situations
    void shutdown(){
        System.out.println("Shutdown screen");
        screen = null;
    }
    
    // update is called by femto.Game every frame
    void update(){
        screen.clear(2);
        // Change to a new state when A is pressed
        if( Button.A.justPressed() )
            Game.changeState( new Garden() );

        // Print some text
        screen.setTextPosition( 100, 84 );
        screen.print("Main");
        
        
        // Update the screen with everything that was drawn
        screen.flush();
    }
    
}
