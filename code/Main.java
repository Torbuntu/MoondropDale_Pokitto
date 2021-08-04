package code;

import femto.Game;
import femto.State;
import femto.sound.Mixer;
import femto.font.TIC80;

import code.stages.Title;
import code.Globals;

class Main extends State {

    // start the game using Main as the initial state
    // and TIC80 as the menu's font
    public static void main(String[] args){
        Globals.init();
        Game.run( TIC80.font(), new Title() );
    }
    
    void init(){
    }
    
    void shutdown(){
    }
    
    void update(){
    }
    
}
