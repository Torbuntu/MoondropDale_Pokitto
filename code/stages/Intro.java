package code.stages;

import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.font.TIC80;
import femto.sound.Stream;

import code.Globals;

public class Intro extends State {
    HiRes16Color screen;
    byte menuOffset;
    String[] dialog;
    byte line;
    boolean up;
    
    void init(){
        screen = Globals.screen;
        screen.textLeftLimit = 44;
        screen.textRightLimit = 174;
        screen.setTextColor(10);
        up = true;
        menuOffset = 120;
        line = 0;
        dialog = new String[]{
            "-- 1 New Message --",
            
            "Greetings. This is a"+ 
            "\nmessage from the City"+
            "\nPlanning Council of"+
            "\nMoondrop Dale.",
            
            "This is to inform you "+
            "\nthat you have been "+
            "\nselected to inherit"+
            "\na section of land here"+
            "\nin town.",
            
            "The conditions state:"+
            "\n\n\"If you intend to take"+
            "\nownership, you must"+
            "\nwork the fields, and"+
            "\nrevive the garden to"+ 
            "\nits former glory.\"",
            
            "The previous owner was"+
            "\nworking on growing "+
            "\na \"Magic Fruit\", a"+
            "\nmysterious crop that"+
            "\nonly grows here, in "+
            "\nMoondrop Dale...",
            
            "If you can manage to"+
            "\ngrow Magic Fruit,"+
            "\nthe land is yours, \nforever.",
            
            "See you soon, \n\n-CPC, Moondrop Dale."
        };
        Stream.play("/music/mdintro.raw");
    }
    void update(){
        Stream.update();
        screen.clear(1);
        // Render handheld screen
        screen.drawRect(40, 10 + menuOffset, 140, 96, 5);
        
        if(up){
            if(menuOffset > 0)menuOffset-=10;
            else{
                screen.setTextPosition(44, 14);
                
                screen.print(dialog[line]);
                
                screen.setTextPosition(44, 100);
                if(line < 6)screen.print("[B - Next]");
                else screen.print("[B - Close]");
                if(Button.B.justPressed()){
                    line++;
                    if(line > 6){
                        up = false;
                    }
                }
                
            }
        }else{
            if(menuOffset < 120)menuOffset+=10;
            else{
                Game.changeState(new Garden());
            }
        }
        screen.flush();
    }
    void shutdown(){
        screen.textLeftLimit = 0;
        screen.textRightLimit = screen.width();
        screen = null;
        dialog = null;
    }
}