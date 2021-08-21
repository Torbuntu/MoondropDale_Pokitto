package code.stages;

import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.font.TIC80;
import femto.sound.Stream;

import code.Globals;

import code.stages.Garden;

import sprites.Butterfly;
import sprites.Tor;
import sprites.Lol;
import sprites.TitleMoon;
import images.TitleBanner;

import audio.Coin;

class Title extends State {

    Butterfly butterfly;
    Tor tor;
    Lol lol;
    TitleBanner title;
    TitleMoon moon;
    HiRes16Color screen;
    
    Coin coin;
    
    byte stage, cursor, butterflyMove;
    boolean resume;

    void init() {
        coin = new Coin(2);
        screen = Globals.screen;
        resume = (Globals.character > 0);
        lol = new Lol();
        lol.idleDown();

        tor = new Tor();
        tor.idleDown();

        title = new TitleBanner();
        moon = new TitleMoon();
        moon.idle();
        
        butterfly = new Butterfly();
        butterfly.x = 100;
        butterfly.y = 100;
        butterfly.flap();
        butterflyMove = 100;

        Stream.play("music/mddal.raw");

        stage = 0;
        cursor = resume ? 0 : 1;
    }

    void shutdown() {
        screen = null;
        title = null;
        moon = null;
        tor = null;
        lol = null;
        butterfly = null;
    }
    
    void updateButterfly() {
        butterflyMove--;
        if (butterflyMove < 0) {
            butterflyMove = 100;
            if (butterfly.x > 200) {
                butterfly.x -= 10;
            } else if (butterfly.x < 10) {
                butterfly.x += 10;
            } else {
                butterfly.x += Math.random(-10, 11);
            }

            if (butterfly.y > 130) {
                butterfly.y -= 10;
            } else if (butterfly.y < 20) {
                butterfly.y += 10;
            } else {
                butterfly.y += Math.random(-8, 9);
            }
        }
        butterfly.draw(screen);
    }

    void update() {
        Stream.update();

        screen.clear(10);
        screen.setTextColor(2);

        screen.fillRect(0,0,220, 80,15);
        title.draw(screen, 32, 10);
        moon.draw(screen, 40, 36);

        switch (stage) {
            case 0: //New,[Continue]
                if (Button.Down.justPressed()) {
                    cursor = 1;
                }

                if (resume) {
                    if (Button.Up.justPressed()) {
                        cursor = 0;
                    }
                    screen.setTextPosition(0, 84);
                    screen.println("Welcome Back to Moondrop Dale!");

                    screen.setTextPosition(0, 94);
                    if (cursor == 0) screen.print("> ");
                    screen.println("Continue");

                } else {
                    screen.setTextPosition(0, 84);
                    screen.println("Welcome to Moondrop Dale!");
                }

                screen.setTextPosition(0, 104);
                if (cursor == 1) screen.print("> ");
                screen.println("New Garden");

                if (Button.A.justPressed()) {
                    coin.play();
                    if (cursor == 1) {
                        stage = 1;
                    } else Game.changeState(new Garden());
                }

                break;
            case 1: //Character select
                if (Button.A.justPressed()) {
                    Globals.reset();
                    coin.play();
                    Game.changeState(new Garden());
                }
                if (Button.Left.justPressed()) {
                    Globals.character = 1;
                }

                if (Button.Right.justPressed()) {
                    Globals.character = 2;
                }

                screen.setTextPosition(0, 84);
                screen.println("Welcome to Moondrop Dale!");
                screen.println("Select a player: ");

                if (Globals.character == 1) {
                    screen.drawRect(38, 98, 24, 26, 14);
                    screen.setTextPosition(0, 100);
                    screen.print("Tor!");
                } else {
                    screen.drawRect(98, 98, 24, 26, 14);
                    screen.setTextPosition(0, 100);
                    screen.print("Lol!");
                }

                tor.draw(screen, 40, 100);
                lol.draw(screen, 100, 100);
                break;
        }
        updateButterfly();

        screen.flush();
    }

}