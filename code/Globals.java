package code;

import femto.mode.HiRes16Color;
import femto.File;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.palette.Psygnosia;
import femto.font.TIC80;

import code.managers.SaveManager;

public class Globals {
    /**
     *  Makes sure globals are initialized.
     */
    static void init(){
        character = saveManager.character;
    }
    
    static void reset(){
        saveManager.character = 0;
        saveManager.monies = 0;
        saveManager.day = 0;
        saveManager.fishing = false;
        
        byte[] field = new byte[120 * 2];
        for (int i = 0; i < (120 * 2); i++) {
            field[i] = (byte) 0;
        }

        // Begin with unlocked Turnip and 6 quanity
        byte[] items = new byte[16];
        items[0] = 1;
        items[1] = 6;
        for (int j = 2; j < 16; j ++) {
            items[j] = (byte)0;
        }
        File file = new File();
        if(file.openRW("/data/mddale/field")){
            file.write(field);
        }
        file.close();
        if(file.openRW("/data/mddale/items")){
            file.write(items);
        }
        file.close();
    }
    
    static final SaveManager saveManager = new SaveManager();

    public static final HiRes16Color screen = new HiRes16Color(MoonDropDale.palette(), TIC80.font());

    // 1 = Tor, 2 = Lol 
    public static byte character; 
    
    public static byte[] load(String path){
        File file = new File();
        byte[] result;
        if(file.openRO("/data/mddale/"+path)){
            result = file.toArray();
        }
        file.close();
        return result;
    }
    
    public static void save(byte[] field, byte[] items, int money, int day){
        saveManager.monies = money;
        saveManager.day = day;
        saveManager.character = character;
        saveManager.saveCookie();
        File file = new File();
        if(file.openRW("/data/mddale/field")){
            file.write(field);
        }
        file.close();
        if(file.openRW("/data/mddale/items")){
            file.write(items);
        }
        file.close();
    }
    
}