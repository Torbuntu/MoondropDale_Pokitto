package code.stages;

import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.font.TIC80;
import femto.sound.Stream;
import femto.sound.Mixer;

import code.Globals;

import code.stages.Garden;

import sprites.Tor;
import sprites.Lol;
import sprites.TitleBanner;

class Title extends State {

    Tor tor;
    Lol lol;
    TitleBanner title;
    HiRes16Color screen;

    byte stage, cursor;
    boolean resume;

    void init() {
        screen = Globals.screen;
        resume = (Globals.character > 0);
        lol = new Lol();
        lol.idleDown();

        tor = new Tor();
        tor.idleDown();

        title = new TitleBanner();
        title.idle();

        Mixer.init(8000);
        Stream.play("music/mddal.raw");

        stage = 0;
        cursor = resume ? 0 : 1;
    }

    void shutdown() {
        screen = null;
        title = null;
        tor = null;
        lol = null;
    }

    void update() {
        Stream.update();

        screen.clear(10);
        screen.setTextColor(2);

        title.draw(screen, 0, 0);

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
                    if (cursor == 1) {
                        stage = 1;
                    } else Game.changeState(new Garden());
                }

                break;
            case 1: //Character select
                if (Button.A.justPressed()) {
                    Globals.reset();
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

        screen.flush();
    }

}