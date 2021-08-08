package code;

import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.palette.Psygnosia;
import femto.font.TIC80;

import code.managers.SaveManager;

import femto.File;

public class Globals {
    static void init(){
        // just to make sure this exists.
    }
    static final SaveManager saveManager = new SaveManager();

    public static final HiRes16Color screen = new HiRes16Color(MoonDropDale.palette(), TIC80.font());

    // 1 = Tor, 2 = Lol 
    public static int character = 0; 
    
    public static byte[] load(String path){
        File file = new File();
        byte[] result;
        if(file.openRO(path)){
            result = file.toArray();
        }
        file.close();
        return result;
    }
    
    public static void save(String path, byte[] data){
        saveManager.saveCookie();
        File file = new File();
        if(file.openRW(path)){
            file.write(data);
        }
        file.close();
    }
    
}