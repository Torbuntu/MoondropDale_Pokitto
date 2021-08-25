package code.stages;

import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.font.TIC80;
import femto.sound.Stream;

import code.Globals;

import code.stages.Garden;
import code.stages.Intro;

import sprites.Butterfly;
import sprites.Tor;
import sprites.Lol;
import sprites.TitleMoon;

import images.TitleBanner;
import images.Tree;
import images.Rocks;

import audio.Coin;

class Title extends State {

    Butterfly butterfly;
    Tor tor;
    Lol lol;
    TitleBanner title;
    TitleMoon moon;
    Tree tree;
    Rocks rocks;
    HiRes16Color screen;
    
    Coin coin;
    
    byte stage, cursor, butterflyMove, tic;
    boolean resume, moving;

    void init() {
        coin = new Coin(2);
        screen = Globals.screen;
        resume = Globals.character != 0;
        lol = new Lol();
        lol.idleDown();
        lol.x = Math.random(60, 160);
        lol.y = Math.random(120, 160);

        tor = new Tor();
        tor.idleDown();
        tor.x = Math.random(60, 160);
        tor.y = Math.random(120, 160);

        title = new TitleBanner();
        moon = new TitleMoon();
        moon.idle();
        
        tree = new Tree();
        rocks = new Rocks();
        
        butterfly = new Butterfly();
        butterfly.x = 100;
        butterfly.y = 100;
        butterfly.flap();
        butterflyMove = 100;

        Stream.play("music/mddal.raw");

        tic = stage = 0;
        cursor = resume ? 0 : 1;
        moving = false;
        
    }

    void shutdown() {
        screen = null;
        title = null;
        moon = null;
        tor = null;
        lol = null;
        butterfly = null;
        tree = null;
        title = null;
        coin = null;
    }
    
    void updateLol(){
        byte dir = Math.random(0, 5);
        if(lol.x >= 150)dir = 0;
        if(lol.x <= 30)dir = 2;
        if(lol.y <= 100)dir=3;
        if(lol.y > 168)dir=1;
        switch(dir){
            case 0:
                lol.idleHori();
                lol.setMirrored(false);
                lol.x-=4;
                break;
            case 1:
                lol.idleUp();
                lol.y-=4;
                break;
            case 2:
                lol.idleHori();
                lol.setMirrored(true);
                lol.x+=4;
                break;
            case 3:
                lol.idleDown();
                lol.y+=4;
                break;
        }
        
    }
    void updateTor(){
        byte dir = Math.random(0, 5);
        if(tor.x >= 150)dir = 0;
        if(tor.x <= 30)dir = 2;
        if(tor.y <= 100)dir=3;
        if(tor.y > 168)dir=1;
        switch(dir){
            case 0:
                tor.idleHori();
                tor.setMirrored(false);
                tor.x-=4;
                break;
            case 1:
                tor.idleUp();
                tor.y-=4;
                break;
            case 2:
                tor.idleHori();
                tor.setMirrored(true);
                tor.x+=4;
                break;
            case 3:
                tor.idleDown();
                tor.y+=4;
                break;
        }
        
    }


    void update() {
        Stream.update();

        screen.clear(10);
        screen.setTextColor(2);
        
        // Blue sky
        screen.fillRect(0,0,220, 99,15);
        
        // Text bar
        screen.fillRect(0,86,220,13, 1);
        screen.drawRect(1,87,217,10,14);
        
        title.draw(screen, 32, 10);
        moon.draw(screen, 40, 36);
        
        tree.draw(screen, 160, 120);
        rocks.draw(screen, 168, 151);
        rocks.draw(screen, 160+tree.width(), 145);
        tic++;
        if(tic>60)tic = 0;
        
        switch (stage) {
            case 0: //New,[Continue]
                if (Button.Down.justPressed()) {
                    cursor = 1;
                }
                if(tic == 30){
                    updateLol();
                    updateTor();
                }
                tor.draw(screen);
                lol.draw(screen);

                if (resume) {
                    if (Button.Up.justPressed()) {
                        cursor = 0;
                    }
                    screen.setTextPosition(30, 90);
                    screen.println("Welcome Back to Moondrop Dale!");

                    screen.setTextPosition(34, 130);
                    if (cursor == 0) {
                        butterfly.draw(screen, 24, 128);
                    }
                    screen.println("Continue");

                } else {
                    screen.setTextPosition(30, 90);
                    screen.println("Welcome to Moondrop Dale!");
                }

                screen.setTextPosition(34, 140);
                if (cursor == 1) {
                    butterfly.draw(screen, 24, 138);
                }
                screen.println("New Garden");

                if (Button.A.justPressed()) {
                    coin.play();
                    if (cursor == 1) {
                        stage = 1;
                        Globals.character = 2;
                        moving = true;
                    } else Game.changeState(new Garden());
                }

                break;
            case 1: //Character select
                if(moving){
                    
                    if(tor.x != 60){
                        tor.idleHori();
                        if(tor.x > 60){
                            tor.setMirrored(false);
                            tor.x-=1;
                        }else {
                            tor.setMirrored(true);
                            tor.x+=1;
                        }
                    }
                    if(tor.y != 130){
                        if(tor.y > 130){
                            tor.idleUp();
                            tor.y-=1;
                        }else {
                            tor.idleDown();
                            tor.y+=1;
                        }
                    }
                    
                    if(lol.x != 110){
                        lol.idleHori();
                        if(lol.x > 110){
                            lol.setMirrored(false);
                            lol.x-=1;
                        }else {
                            lol.setMirrored(true);
                            lol.x+=1;
                        }
                    }
                    if(lol.y != 130){
                        if(lol.y > 130){
                            lol.idleUp();
                            lol.y-=1;
                        }else {
                            lol.idleDown();
                            lol.y+=1;
                        }
                    }
                    moving = (tor.x != 60||tor.y != 130||lol.x!=110||lol.y!=130);
                    tor.draw(screen);
                    lol.draw(screen);
                    break;
                }else{
                    tor.idleDown();
                    lol.idleDown();
                }
                
                if (Button.A.justPressed()) {
                    Globals.reset();
                    coin.play();
                    Game.changeState(new Intro());
                }
                if (Button.Left.justPressed()) {
                    Globals.character = 1;
                }

                if (Button.Right.justPressed()) {
                    Globals.character = 2;
                }

                screen.setTextPosition(30, 90);
                screen.print("Select a player: ");
                tor.draw(screen, 60, 130);
                lol.draw(screen, 110, 130);

                if (Globals.character == 1) {
                    screen.print("Tor!");
                    butterfly.draw(screen, 58, 125);
                } else {
                    screen.print("Lol!");
                    butterfly.draw(screen, 108, 125);
                }
                
                break;
        }

        screen.flush();
    }
}