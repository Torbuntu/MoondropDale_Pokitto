import femto.mode.HiRes16Color;
import code.Globals;
import sprites.Tor;

class Player {
    // face: 0:left, 1:up,2:right,3:down
    int character, face;
    int x, y, w, h, flash;
    Tor tor;
    boolean cursor = true;
    
    Player(){
        x = 32;
        y = 32;
        
        face = 0;
        character = Globals.character;
        if(character ==1){
            tor = new Tor();
            tor.idleHori();
            w = tor.width();
            h = tor.height();
        }else{
            //lol = new Lol();
            
        }
    }
    
    void moveLeft(){
        System.out.println("Left");
        tor.idleHori();
        tor.setMirrored(false);
        if(face != 0)face = 0;
        else x-=20;
    }
    void moveRight(){
        System.out.println("Right");
        tor.idleHori();
        tor.setMirrored(true);
        if(face != 2)face = 2;
        else x += 20;
    }
    void moveUp(){
        System.out.println("Up");
        tor.idleUp();
        if(face != 1)face = 1;
        else y -= 16;
    }
    void moveDown(){
        System.out.println("Down");
        tor.idleDown();
        if(face != 3) face = 3;
        else y += 16;
    }
    

    void render(HiRes16Color screen){
        flash++;
        if(flash>50)flash = 0;
        if(character == 1){
            tor.draw(screen, x, y);
        }else{
            
        }
        if(cursor && flash > 5){
            switch(face){
                case 0:
                    screen.drawRect(x-w, y+8, 20, 16, 14);
                    break;
                case 1:
                    screen.drawRect(x, y-16, 20, 16, 14);
                    break;
                case 2:
                    screen.drawRect(x+w, y+8, 20, 16, 14);
                    break;
                case 3:
                    screen.drawRect(x, y+h, 20, 16, 14);
                    break;
            }
            
        }
    }
    
    void dispose(){
        tor = null;
    }
}