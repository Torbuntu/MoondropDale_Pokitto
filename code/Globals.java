import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.palette.Psygnosia;
import femto.font.TIC80;

public class Globals {
    public static final HiRes16Color screen = new HiRes16Color(MoonDropDale.palette(), TIC80.font());

    // 1 = Tor, 2 = Lol 
    public static int character = 1; 
    
}