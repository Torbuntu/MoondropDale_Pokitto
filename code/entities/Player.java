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
        y = 64;
        
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
        switch(face){
            case 0: break;
            case 1:
                y+=16;
                break;
            case 2:
                // We jump over the player sprite
                x-=20;
                break;
            case 3:
                y-=16;
                break;
        }
        x-=20;
        face = 0;
    }
    void moveRight(){
        System.out.println("Right");
        character == 1 ? tor.idleHori() : lol.idleHori();
        character == 1 ? tor.setMirrored(true) : lol.setMirrored(true);
        switch(face){
            case 0: 
                // We jump over the player sprite
                x+=20;
                break;
            case 1:
                y+=16;
                break;
            case 2: break;
            case 3:
                y-=16;
                break;
        }
        x+=20;
        face = 2;

    }
    void moveUp(){
        System.out.println("Up");
        character == 1 ? tor.idleUp() : lol.idleUp();
        switch(face){
            case 0:
                x+=20;
                break;
            case 1: break;
            case 2:
                x-=20;
                break;
            case 3:
                // We jump over the player sprite
                y-=16;
                break;
        }
        y -= 16;
        face = 1;
    }
    void moveDown(){
        System.out.println("Down");
        character == 1 ? tor.idleDown() : lol.idleDown();
        switch(face){
            case 0:
                x+=20;
                break;
            case 1: 
                // We jump over the player sprite
                y += 16;
                break;
            case 2:
                x-=20;
                break;
            case 3: break;
        }
        y += 16;
        face = 3;
    }
    

    void render(HiRes16Color screen){

        if(x >= 20 && x < 140 && y >= 64 && y < 160){
            screen.drawRect(x, y, 20, 16, 14);
        }
        
        switch(face){
            case 0:
                drawPlayer(screen, x+20, y-8);
                break;
            case 1:
                drawPlayer(screen, x, y+10);
                break;
            case 2:
                drawPlayer(screen, x-20, y-8);
                break;
            case 3:
                drawPlayer(screen, x, y-20);
                break;
        }

    }
    
    /**
     * This is the Y where the player sprite is rendered
     */ 
    int getRenderY(){
        switch(face){
            case 0:
            case 2:
                return y-8;
                break;
            case 1:
                return y+10;
                break;
            case 3:
                return y-20;
                break;
        }
    }
    
    void drawPlayer(HiRes16Color screen, int x, int y){
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