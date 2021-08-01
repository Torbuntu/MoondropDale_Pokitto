package code;

import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.palette.Psygnosia;
import femto.font.TIC80;

import code.managers.SaveManager;

public class Globals {
    static void init(){
        // just to make sure this exists.
    }
    static final SaveManager saveManager = new SaveManager();

    public static final HiRes16Color screen = new HiRes16Color(MoonDropDale.palette(), TIC80.font());

    // 1 = Tor, 2 = Lol 
    public static int character = 0; 
    
}