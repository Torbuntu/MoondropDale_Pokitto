package code.entities;

import femto.mode.HiRes16Color;

import sprites.Tor;
import sprites.Lol;


class Player {
    // face: 0:left, 1:up, 2:right, 3:down
    byte character, face;
    short x, y;
    Tor tor;
    Lol lol;
    boolean cursor = true;
    
    final private short w = 10, h = 8;
    
    Player(byte character){
        x = 50;
        y = 56;
        
        face = 0;
        this.character = character;
        if(character == 1){
            tor = new Tor();
            tor.idleHori();
        }else{
            lol = new Lol();
            lol.idleHori();
        }
    }
    
    void moveLeft(){
        if(x <= 20)return;
        character == 1 ? tor.idleHori() : lol.idleHori();
        character == 1 ? tor.setMirrored(false) : lol.setMirrored(false);
        switch(face){
            case 0: break;
            case 1:
                y+=h;
                break;
            case 2:
                // We jump over the player sprite
                x-=w;
                break;
            case 3:
                y-=h;
                break;
        }
        x-=w;
        face = 0;
    }
    void moveRight(){
        if(x >= 210)return;
        character == 1 ? tor.idleHori() : lol.idleHori();
        character == 1 ? tor.setMirrored(true) : lol.setMirrored(true);
        switch(face){
            case 0: 
                // We jump over the player sprite
                x+=w;
                break;
            case 1:
                y+=h;
                break;
            case 2: break;
            case 3:
                y-=h;
                break;
        }
        x+=w;
        face = 2;

    }
    void moveUp(){
        if(y <= 0)return;
        if(x > 130 && y <= 24)return;
        character == 1 ? tor.idleUp() : lol.idleUp();
        switch(face){
            case 0:
                x+=w;
                break;
            case 1: break;
            case 2:
                x-=w;
                break;
            case 3:
                // We jump over the player sprite
                y-=h;
                break;
        }
        y -= h;
        face = 1;
    }
    void moveDown(){
        if(y >= 152)return;
        character == 1 ? tor.idleDown() : lol.idleDown();
        switch(face){
            case 0:
                x+=w;
                break;
            case 1: 
                // We jump over the player sprite
                y += h;
                break;
            case 2:
                x-=w;
                break;
            case 3: break;
        }
        y += h;
        face = 3;
    }
    

    void render(HiRes16Color screen){
        switch(face){
            case 0:
                drawPlayer(screen, x+w, y-4);
                break;
            case 1:
                drawPlayer(screen, x, y+4);
                break;
            case 2:
                drawPlayer(screen, x-w, y-4);
                break;
            case 3:
                drawPlayer(screen, x, y-12);
                break;
        }

    }
    
    // To render the curosr always on top
    void renderCursor(HiRes16Color screen){
        screen.drawRect(x, y, w, h, 14);
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
                return y-w;
                break;
        }
        // shouldn't happen
        return 0;
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
        lol = null;
    }
}