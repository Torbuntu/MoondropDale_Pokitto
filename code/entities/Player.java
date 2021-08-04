package code.entities;

import femto.mode.HiRes16Color;
import code.Globals;

import code.managers.Inventory;

import sprites.Tor;
import sprites.Lol;


class Player {
    // face: 0:left, 1:up, 2:right, 3:down
    int character, face;
    int x, y, w, h, flash;
    Tor tor;
    Lol lol;
    boolean cursor = true;
    Inventory inventory;
    
    Player(){
        x = 20;
        y = 74;
        
        face = 0;
        character = Globals.character;
        if(character == 1){
            tor = new Tor();
            tor.idleHori();
            w = tor.width();
            h = tor.height();
        }else{
            lol = new Lol();
            lol.idleHori();
            w = lol.width();
            h = lol.height();
        }
        
        inventory = new Inventory();
    }
    
    void moveLeft(){
        System.out.println("Left");
        character == 1 ? tor.idleHori() : lol.idleHori();
        character == 1 ? tor.setMirrored(false) : lol.setMirrored(false);
        if(face != 0)face = 0;
        else x-=20;
    }
    void moveRight(){
        System.out.println("Right");
        character == 1 ? tor.idleHori() : lol.idleHori();
        character == 1 ? tor.setMirrored(true) : lol.setMirrored(true);
        if(face != 2)face = 2;
        else x += 20;
    }
    void moveUp(){
        System.out.println("Up");
        character == 1 ? tor.idleUp() : lol.idleUp();
        if(face != 1)face = 1;
        else y -= 16;
    }
    void moveDown(){
        System.out.println("Down");
        character == 1 ? tor.idleDown() : lol.idleDown();
        if(face != 3) face = 3;
        else y += 16;
    }
    

    void render(HiRes16Color screen){
        flash++;
        if(flash>50)flash = 0;
        if(cursor && flash > 5){
            switch(face){
                case 0:
                    screen.drawRect(x-w, y+6, 20, 16, 14);
                    break;
                case 1:
                    screen.drawRect(x, y-10, 20, 16, 14);
                    break;
                case 2:
                    screen.drawRect(x+w, y+6, 20, 16, 14);
                    break;
                case 3:
                    screen.drawRect(x, y+h-2, 20, 16, 14);
                    break;
            }
            
        }
        if(character == 1){
            tor.draw(screen, x, y);
        }else{
            lol.draw(screen, x, y);
        }

    }
    
    void dispose(){
        tor = null;
    }
}