package code.stages;

import femto.mode.HiRes16Color;
import femto.Game;
import femto.State;
import femto.input.Button;
import femto.palette.Psygnosia;
import femto.font.TIC80;
import femto.sound.Stream;

import code.Globals;
import code.Main;
import code.entities.Player;
import code.managers.Inventory;
import code.managers.CropManager;
import code.stages.Title;

import sprites.Hoe;
import sprites.Planter;
import sprites.Water;
import sprites.Basket;
import sprites.FishingRod;
import sprites.Pond;
import sprites.Wave;
import sprites.Butterfly;
import sprites.Eyes;

import images.Arm;
import images.Corner;
import images.ActionIcon;
import images.GrassV;
import images.GrassH;
import images.Stone;
import images.Door;
import images.TimeWindowSunny;
import images.TimeWindowRainy;
import images.Moon;
import images.Tree;
import images.SmallTree;
import images.Roof;
import images.UnTilled;
import images.Tilled;
import images.WetTilled;
import images.ButterflyAvatar;
import images.MusicOn;
import images.MusicOff;
import images.ButtonC;
import images.ShadowA;
import images.ShadowB;
import images.Rocks;

import audio.Donk;
import audio.Splash;
import audio.Coin;
import audio.Plow;
import audio.Harvest;
import audio.Notice;
import audio.Bury;
import audio.Select;
import audio.PowerOn;
import audio.PowerOff;

class Garden extends State {

    HiRes16Color screen;

    Butterfly butterfly;
    byte butterflyMove, butterflyLine;
    String[] butterflyText;
    boolean butterflyCaptive;

    byte tipLine;
    String[] tips;

    Eyes eyes;

    CropManager cropManager;
    Player player;
    Inventory inventory;
    GrassV grassV;
    GrassH grassH;
    Stone stone;
    Door door;
    ActionIcon actionIcon;
    ButterflyAvatar butterflyAvatar;
    MusicOn musicOn;
    MusicOff musicOff;
    ButtonC buttonC;

    TimeWindowSunny timeWindowSunny;
    TimeWindowRainy timeWindowRainy;
    Moon moon;
    Tree tree;
    SmallTree smallTree;
    Roof roof;
    ShadowA shadowA;
    ShadowB shadowB;
    Rocks rocks;

    Tilled tilled;
    UnTilled unTilled;
    WetTilled wetTilled;

    Donk donk;
    Splash splash;
    Coin coin;
    Plow plow;
    Harvest harvest;
    Notice notice;
    Bury bury;
    Select select;
    PowerOn powerOn;
    PowerOff powerOff;

    Hoe hoe;
    byte swingHoe;

    Planter planter;
    byte swingPlanter;

    Water water;
    byte swingWater;

    Basket basket;
    byte swingBasket;

    FishingRod rod;
    byte swingRod;

    Pond pond;
    Wave wave;
    Arm arm;
    Corner corner;

    byte hour, cursor, menuOffset;
    short day, dayProgress, moveTime;

    // Used in the checkTool `switch` to avoid lots of `if` checks
    byte tool;

    // 0: regular, 1: pause/menu, 2: summary, 3: fishing
    byte gameState;

    boolean exit, move, shop, summary, playMusic, endGame;
    boolean raining, confirm, foundSomething;
    boolean fishingAvailable, catchSuccess, emptyCan, emptySeed;
    byte caughtSize, catchTime, fishTime, treeDialog, foundItem;

    final private short w = 10, h = 8;

    // Field plots arrays. 
    byte[] type;
    byte[] growth;
    byte[] watered;

    void init() {
        donk = new Donk(2);
        splash = new Splash(2);
        coin = new Coin(2);
        plow = new Plow(2);
        harvest = new Harvest(2);
        notice = new Notice(2);
        bury = new Bury(2);
        select = new Select(2);
        powerOn = new PowerOn(2);
        powerOff = new PowerOff(2);

        screen = Globals.screen;
        day = Globals.saveManager.day;
        fishingAvailable = Globals.saveManager.fishing;
        player = new Player(Globals.character);
        inventory = new Inventory();
        cropManager = new CropManager();

        foundItem = treeDialog = dayProgress = hour = cursor = gameState = 0;
        tipLine = swingRod = swingBasket = swingWater = swingHoe = swingPlanter = 0;
        tool = 1;

        byte[] field = Globals.load("field");

        type = new byte[120];
        growth = new byte[120];
        watered = new byte[120];
        byte id = 0;
        for (int i = 0; i < (120 * 2); i += 2) {
            if (id > 120) return;
            watered[id] = 0;
            type[id] = field[i];
            growth[id] = field[i + 1];
            id++;
        }

        butterfly = new Butterfly();
        butterfly.x = 100;
        butterfly.y = 100;
        butterfly.flap();
        butterflyMove = 100;
        butterflyLine = 0;
        butterflyText = new String[] {
            "It ignores you.",
            "It flaps happily in the wind.",
            "It does a loop! Whee~!",
            "It gives a blank look...",
            "It lands on your nose. Achoo~!",
            "It leads you to treasure! $10!"
        };

        tips = new String[] {
            "Order seeds from the Shop in Menu.",
            "Check the Tree daily.",
            "Fishing is easy money here!",
            "When plowed, rain water holds!",
            "The Hoe tills earth for planting.",
            "Use the river to fill the Water Can.",
            "Use [C] to open the menu and shop!",
            "Use [B] to switch equipped tools.",
            "Don't forget Save & Quit when done!",
            "The moon shows when the day is done.",
            "Move the pesky Butterfly with [A]"
        };

        timeWindowSunny = new TimeWindowSunny();
        timeWindowRainy = new TimeWindowRainy();
        moon = new Moon();
        tree = new Tree();
        smallTree = new SmallTree();
        shadowA = new ShadowA();
        shadowB = new ShadowB();
        roof = new Roof();
        musicOn = new MusicOn();
        musicOff = new MusicOff();
        buttonC = new ButtonC();
        rocks = new Rocks();
        
        tilled = new Tilled();
        unTilled = new UnTilled();
        wetTilled = new WetTilled();

        arm = new Arm();
        corner = new Corner();

        butterflyAvatar = new ButterflyAvatar();

        grassV = new GrassV();
        grassH = new GrassH();
        stone = new Stone();
        door = new Door(); //huehue
        actionIcon = new ActionIcon();
        pond = new Pond();
        pond.wave();
        wave = new Wave();
        wave.wave();

        hoe = new Hoe();
        planter = new Planter();
        water = new Water();
        basket = new Basket();
        rod = new FishingRod();
        rod.swing();

        eyes = new Eyes();
        eyes.idle();

        // booleans
        move = playMusic = true;
        endGame = emptyCan = emptySeed = butterflyCaptive = foundSomething = shop = catchSuccess = summary = raining = confirm = false;
        moveTime = 0;

        Stream.play("music/mdgard.raw");
    }

    void shutdown() {
        screen = null;
        player.dispose();
        player = null;
        inventory = null;
        grassH = null;
        grassV = null;
        pond = null;
        hoe = null;
        planter = null;
        water = null;
        basket = null;
        rod = null;
    }

    void update() {
        if (playMusic) Stream.update();
        // 0: regular, 1: pause/menu, 2: fishing, 3: summary, 4: Interact with butterfly, 5: Interact with Tree
        switch (gameState) {
            case 0:
                render();
                updatePrimaryDay();
                updateButterfly();
                break;
            case 1:
                render();
                updateHandheldMenu();
                break;
            case 2:
                render();
                updateFishing();
                updateButterfly();
                break;
            case 3:
                updateSummary();
                break;
            case 4:
                render();
                dialog("Hello Butterfly! How are you today?");
                dialogLine((String) butterflyText[butterflyLine]);
                if (Button.B.justPressed()) {
                    gameState = 0;
                    select.play();
                    butterflyCaptive = true;
                }

                updateButterfly();
                break;
            case 5:
                render();
                updateTree();
                updateButterfly();
                break;
        }

        if (gameState != 3) {
            // Render the HUD (equipped item, seed amount, Monies);
            inventory.drawHud(screen, fishingAvailable);

            // Render day progression.
            if (raining) {
                timeWindowRainy.draw(screen, 184, 156);
            } else {
                timeWindowSunny.draw(screen, 184, 156);
            }
            moon.draw(screen, 185 + (dayProgress * 26 / 200), 161);

            screen.setTextPosition(124, 162);
            screen.print("Day:" + (int) day);
            
            buttonC.draw(screen, 154, 167);
            screen.setTextPosition(124, 169);
            screen.print("Menu: ");

        }

        // FPS
        //screen.setTextPosition(0, 0);
        //screen.print(screen.fps());
        screen.flush();
    }

    void updateButterfly() {
        if (butterflyCaptive) {
            butterflyAvatar.draw(screen, 202, 136);
            return;
        }
        butterflyMove--;
        if (butterflyMove < 0) {
            butterflyMove = 100;
            if (butterfly.x > 180) {
                butterfly.x -= 10;
            } else if (butterfly.x < 20) {
                butterfly.x += 10;
            } else {
                butterfly.x += Math.random(-10, 11);
            }

            if (butterfly.y > 130) {
                butterfly.y -= 10;
            } else if (butterfly.y < 40) {
                butterfly.y += 10;
            } else {
                butterfly.y += Math.random(-8, 9);
            }
        }
        butterfly.draw(screen);
    }

    void updateTree() {
        switch (treeDialog) {
            case 0:
                dialog("Hello there!");
                if (foundSomething) {
                    dialogLine("I found something today!");
                    if (Button.B.justPressed()) {
                        treeDialog = 1;
                        select.play();
                    }
                } else {
                    dialogLine("Nothing found today...");
                    if (Button.B.justPressed()) {
                        treeDialog = 2;
                        gameState = 0;
                        select.play();
                    }
                }

                break;
            case 1:
                dialog("You can have this.");
                switch (foundItem) {
                    case 0:
                        dialogLine("It's a Turnip seed!");
                        break;
                    case 1:
                        dialogLine("It's a Radish seed!");
                        break;
                    case 2:
                        dialogLine("It's a Daisy seed!");
                        break;
                    case 3:
                        dialogLine("It's a Coffee seed!");
                        break;
                    case 4:
                        dialogLine("It's a Tea seed!");
                        break;
                    case 5:
                        dialogLine("It's a GreenBean seed!");
                        break;
                    case 6:
                        dialogLine("It's a Tomato seed!");
                        break;
                    case 7:
                        dialogLine("It's a Blueberry seed!");
                        break;
                    case 8:
                        dialogLine("It's a Magic seed!");
                        break;
                }
                if (Button.B.justPressed()) {
                    inventory.quantities[foundItem]++;
                    treeDialog = 2;
                    gameState = 0;
                    select.play();
                }
                break;
            case 2:
                dialog("Let's talk tomorrow.");
                if (Button.B.justPressed()) {
                    gameState = 0;
                    select.play();
                }
                break;
        }
    }

    void updatePrimaryDay() {

        hour++;
        if (hour > 30) {
            dayProgress++;
            hour = 0;
        }
        if(endGame){
            dialog("You did it! You harvested Magic Fruit.");
            dialogLine("The land is now yours!");
            if(Button.B.justPressed())endGame = false;
            return;
        }
        if (dayProgress > 200) {
            dayProgress = 0;
            raining = (Math.random(0, 3) == 1);
            if (raining) Stream.play("music/mdrain.raw");
            else Stream.play("music/mdgard.raw");

            for (byte i = 0; i < 120; i++) {
                if (growth[i] > 0) {
                    if (type[i] > 0 && watered[i] == 1) {
                        if (growth[i] < 9) growth[i]++;
                    }
                    // set water based on raining
                    watered[i] = raining ? 1 : 0;
                }
            }
            foundSomething = (Math.random(0, 2) == 1);
            if (foundSomething) {
                foundItem = getFoundItem();
            }
            treeDialog = 0;
            gameState = 3;
            summary = true;
            return;
        }
        if (confirm) {
            dialog("End day early?");
            dialogLine("[A] - Yes, [B] - No");
            if (Button.A.justPressed()) {
                dayProgress = 201;
                confirm = false;
                select.play();
            }
            if (Button.B.justPressed()) {
                select.play();
                confirm = false;
            }
            return;
        }

        // Shift equipped item
        if (Button.B.justPressed()) {
            inventory.equippedTool++;
            if (inventory.equippedTool > 4) {
                if (fishingAvailable) {
                    inventory.equippedTool = 0;
                } else {
                    inventory.equippedTool = 1;
                }
            }
        }

        if (Button.C.justPressed()) {
            gameState = 1;
            menuOffset = 120;
            exit = false;
            powerOn.play();
        }

        // handle movement
        if (!move) return;

        if (Button.A.justPressed()) {
            // CheckButterfly
            if (butterflyCaptive) {
                butterflyCaptive = false;
                butterfly.x = player.x;
                butterfly.y = player.y;
                return;
            }

            if (butterfly.x < player.x + player.w &&
                butterfly.x + butterfly.width() > player.x &&
                butterfly.y < player.y + player.h &&
                butterfly.y + butterfly.height() > player.y) {
                if (Math.random(0, 10) == 5) {
                    butterflyLine = 5;
                    gameState = 4;
                    inventory.monies += 10;
                    coin.play();
                    return;
                } else {
                    butterflyLine = Math.random(0, 5);
                    gameState = 4;
                    return;
                }
            }

            // CheckEndDay
            if (player.x >= 150 && player.x < 150 + door.width() && player.y < 32) {
                confirm = true;
                return;
            }

            // CheckTree
            if (player.x >= 180 && player.x <= 210 && player.y < 40) {
                gameState = 5;
                return;
            }

            // 0:fishing rod, 1:hoe, 2:water, 3:basket, 4:planter 
            switch (inventory.equippedTool) {
                case 0:
                    useFishingRod();
                    break;
                case 1:
                    useHoe();
                    break;
                case 2:
                    useWater();
                    break;
                case 3:
                    useBasket();
                    break;
                case 4:
                    usePlanter();
                    break;
            }
        }

        if (moveTime > 10) moveTime = 1;
        if (Button.Left.isPressed()) {
            if (moveTime == 0 || moveTime == 10) player.moveLeft();
            moveTime++;
            return;
        }
        if (Button.Right.isPressed()) {
            if (moveTime == 0 || moveTime == 10) player.moveRight();
            moveTime++;
            return;
        }
        if (Button.Up.isPressed()) {
            if (moveTime == 0 || moveTime == 10) player.moveUp();
            moveTime++;
            return;
        }
        if (Button.Down.isPressed()) {
            if (moveTime == 0 || moveTime == 10) player.moveDown();
            moveTime++;
            return;
        }
        moveTime = 0;
    }

    byte getFoundItem() {
        byte found = Math.random(0, 9);
        if (inventory.unlocked[found]) {
            return found;
        } else {
            return 0;
        }
    }

    void render() {
        // -- Render --
        if (raining) screen.clear(11);
        else screen.clear(10);
        //screen.clear(10);

        renderScene();

        // crops
        for (int i = 0; i < 120; i++) {
            byte x = (i % 12);
            byte y = (i / 12);
            byte id = x + y * 12;
            if (type[id] == 0 && growth[id] == 0) {
                unTilled.draw(screen, 50 + x * 10, 64 + y * 8);
                continue;
            }
            if (growth[id] > 0) {
                if (watered[id] == 1) wetTilled.draw(screen, 50 + x * 10, 64 + y * 8);
                else tilled.draw(screen, 50 + x * 10, 64 + y * 8);
            }
            if (type[id] > 0) renderCrop(id, 50 + x * 10, 64 + y * 8);
        }

        player.render(screen);

        // Swinging tools and render if active
        checkToolUse();

        // Always render the cursor on top of all the crops
        if (inField()) {
            player.renderCursor(screen);
        }

    }

    void updateSummary() {
        screen.clear(1);

        if (summary) {
            if (Button.B.justPressed()) {
                select.play();
                summary = false;
                tipLine = Math.random(0, 4);
            }
            dialog("The day is over. You rest.");
            if (raining) {
                dialogLine("It rains during the night.");
            }

        } else {
            if (Button.B.justPressed() || Button.C.justPressed()) {
                gameState = 0;
                day++;
                select.play();
                player.moveDown();
            }
            if(Button.Right.justPressed()){
                tipLine++;
                if(tipLine > 10)tipLine = 0;
            }
            if(Button.Left.justPressed()){
                tipLine--;
                if(tipLine < 0)tipLine = 10;
            }
            dialog((String) tips[tipLine]);
            dialogLine("[<-] Or [->] for next tip!");
        }

    }

    /**
     * 1 - turnip
     * 2 - radish
     * 3 - daisy
     * 4 - coffee
     * 5 - tea
     * 6 - greenBean
     * 7 - tomato
     * 8 - blueberry
     * 9 - magicFruit
     * 
     */
    void renderCrop(byte id, short x, short y) {
        cropManager.draw(screen, type[id], growth[id], x, y);
    }

    void seedShop() {
        screen.setTextColor(10);
        screen.setTextPosition(43, 43);
        screen.print("-- Seed Shop --");

        screen.setTextPosition(45, 56);
        screen.print("$" + inventory.monies);

        screen.setTextPosition(45, 64);
        if (cursor == 10) screen.print("- $100");
        else screen.print("- $" + (int) inventory.getCost(cursor));


        // List available seeds.
        screen.setTextPosition(45, 80);
        if (cursor < 9) {
            screen.print("> Seed:");
            inventory.drawSeed(screen, cursor);
        } else {
            screen.print("Seed:");
        }

        if (!fishingAvailable) {
            screen.setTextPosition(45, 100);
            if (cursor == 10) screen.print("> ");
            screen.print("Fishing Rod: ");
            inventory.fishingIcon.draw(screen, 126, 100);
        } else {
            screen.setTextPosition(45, 100);
            if (cursor == 10) screen.print("> ");
            screen.print("Tool Upgrades");
            screen.setTextPosition(45, 110);
            screen.print("   Coming Soon!");
        }

        screen.setTextPosition(45, 126);
        screen.print("[A - Buy] [B - Back]");

        if (Button.Down.justPressed()) {
            cursor = 10;
        }
        if (Button.Up.justPressed()) {
            cursor = 0;
        }

        if (Button.Right.justPressed()) {
            if (cursor < 8) cursor++;
        }
        if (Button.Left.justPressed()) {
            if (cursor > 0) cursor--;
        }

        if (Button.A.justPressed()) {
            if (cursor > 9) {
                if (inventory.monies > 100) {
                    fishingAvailable = true;
                    inventory.monies -= 100;
                    coin.play();
                } else {
                    donk.play();
                }
            } else {
                if (inventory.buySuccess(cursor)) {
                    coin.play();
                } else {
                    donk.play();
                }
            }
        }

        if (Button.B.justPressed()) {
            shop = false;
            select.play();
            cursor = 1;
        }
    }

    void updateHandheldMenu() {

        screen.drawHLine(40, 20 + menuOffset, 140, 1);
        screen.fillRect(40, 21 + menuOffset, 140, 20, 9);
        corner.draw(screen, 20, 20 + menuOffset, false, false, true);
        corner.draw(screen, 180, 20 + menuOffset, true, false, true);

        // Left border
        screen.drawVLine(20, 40 + menuOffset, 112, 1);
        screen.fillRect(21, 40 + menuOffset, 19, 112, 9);

        // Right border
        screen.drawVLine(199, 40 + menuOffset, 112, 1);
        screen.fillRect(180, 40 + menuOffset, 19, 112, 9);

        // arms
        arm.draw(screen, 1, 90 + menuOffset, false, false, true);
        arm.draw(screen, 199, 90 + menuOffset, true, false, true);

        // Render handheld screen
        screen.fillRect(40, 40 + menuOffset, 140, 96, 5);
        screen.fillRect(42, 42 + menuOffset, 136, 92, 1);

        // Bottom border
        screen.fillRect(40, 136 + menuOffset, 140, 16, 9, true);

        if (!exit && menuOffset > 0) {
            menuOffset -= 10;
            return;
        }

        if (exit && menuOffset < 120) {
            menuOffset += 10;
            if (menuOffset == 120) gameState = 0;
            return;
        }

        if (shop) {
            seedShop();
            return;
        }


        screen.setTextColor(10);
        screen.setTextPosition(43, 43);
        screen.print("-- Menu --");

        if (confirm) {
            screen.setTextPosition(45, 80);
            screen.print("Save & Quit Game?");
            screen.setTextPosition(45, 100);
            screen.print("[A - Yes] [B - No]");
            if (Button.A.justPressed()) {
                saveAndQuit();
            }
            if (Button.B.justPressed()) {
                confirm = false;
                select.play();
            }
            return;
        }

        // Seed Select (cursor == 0)
        screen.setTextPosition(45, 80);
        if (cursor == 0) screen.print("> ");
        screen.print("Seed:");
        inventory.drawSeed(screen);

        // Music On/Off
        screen.setTextPosition(45, 96);
        if (cursor == 1) screen.print("> ");
        screen.print("Music: ");
        if (playMusic) {
            musicOn.draw(screen, 110, 96);
        } else {
            musicOff.draw(screen, 110, 96);
        }

        // Seed Shop (cursor == 2)
        screen.setTextPosition(45, 106);
        if (cursor == 2) screen.print("> ");
        screen.print("Enter Shop");

        // Save and quit (cursor == 3)
        screen.setTextPosition(45, 116);
        if (cursor == 3) screen.print("> ");
        screen.print("Save & Quit");

        screen.setTextPosition(45, 126);
        screen.print("[B - Close Menu]");

        if (Button.Up.justPressed()) cursor--;
        if (Button.Down.justPressed()) cursor++;
        if (cursor < 0) cursor = 0;
        if (cursor > 3) cursor = 3;


        if (Button.Left.justPressed()) {
            if (cursor == 0) {
                if (inventory.equippedSeed > 0) {
                    if (inventory.unlocked[inventory.equippedSeed - 1]) {
                        inventory.equippedSeed--;
                    }
                }
            }
            if (cursor == 1) {
                playMusic = false;
            }
        }
        if (Button.Right.justPressed()) {
            if (cursor == 0) {

                if (inventory.equippedSeed < 8) {
                    if (inventory.unlocked[inventory.equippedSeed + 1]) {
                        inventory.equippedSeed++;
                    }
                }
            }
            if (cursor == 1) {
                playMusic = true;
            }
        }

        if (Button.A.justPressed()) {
            if (cursor == 1) {
                playMusic = !playMusic;
            }
            if (cursor == 2) {
                cursor = 0;
                shop = true;
            }
            if (cursor == 3) {
                confirm = true;
            }
        }

        if (Button.B.justPressed() || Button.C.justPressed()) {
            exit = true;
            powerOff.play();
        }

    }

    /**
     * Set the time before fish is ON!
     * Switch to fishing game state.
     * 
     */
    void useFishingRod() {
        if (player.x < 30 && player.face == 0) {
            //swing fishing rod
            fishTime = Math.random(50, 120);
            gameState = 2;
            tool = 0;
        }
    }

    void useHoe() {
        if (inField()) {
            // this will remove any growing crop and un-water
            //crops[getFieldId()].till();
            short id = getFieldId();
            if (type[id] == 0 && growth[id] == 0) {
                growth[id] = 1;
            }
            swingHoe = 20;
            move = false;
            if (player.face == 2) hoe.setMirrored(true);
            else hoe.setMirrored(false);
            tool = 1;
            plow.play();
        }
    }

    void useWater() {
        // If at the pond, fill the water
        if (player.x < 40) {
            inventory.fill = 8;
            emptyCan = false;
            splash.play();
        } else if (inField() && inventory.fill > 0) {
            short id = getFieldId();
            if (growth[id] > 0) {
                inventory.fill--;
                watered[id] = 1;
                if (player.face == 2) water.setMirrored(true);
                else water.setMirrored(false);
                swingWater = 20;
                move = false;
                tool = 2;
                splash.play();
            }
        } else {
            emptyCan = true;
            donk.play();
            move = false;
            tool = 2;
            swingWater = 10;
        }
    }

    void useBasket() {
        // Basket for harvesting
        if (inField()) {
            short id = getFieldId();
            if (type[id] == 0) {
                donk.play();
                return;
            }
            switch (type[id]) {
                case 0: //nothing
                    return;
                    break; //Nothing to do here.
                case 1:
                    // Turnip
                    if (growth[id] < 4) return;
                    break;
                case 2:
                    // Radish
                    if (growth[id] < 5) return;
                    break;
                case 3:
                    // Daisy
                    if (growth[id] < 5) return;
                    break;
                case 4:
                    // Coffee
                    if (growth[id] < 6) return;
                    break;
                case 5:
                    // Tea
                    if (growth[id] < 6) return;
                    break;
                case 6:
                    // GreenBeans
                    if (growth[id] < 7) return;
                    break;
                case 7:
                    // Tomato
                    if (growth[id] < 7) return;
                    break;
                case 8:
                    // Blueberry
                    if (growth[id] < 8) return;
                    break;
                case 9:
                    // Magic fruit
                    if (growth[id] < 8) return;
                    break;
            }

            watered[id] = 0;
            growth[id] = 0;
            if(inventory.harvest(type[id]) && !Globals.saveManager.complete){
                Globals.saveManager.complete = true;
                endGame = true;
            }
            type[id] = 0;

            if (player.face == 2) basket.setMirrored(true);
            else basket.setMirrored(false);
            swingBasket = 20;
            move = false;
            tool = 3;
            harvest.play();
        }
    }

    void usePlanter() {
        if (inField()) {
            if (inventory.hasQuantity()) {
                emptySeed = false;
                short id = getFieldId();
                // only plant a crop if the soil is tilled and no plant exists
                if (type[id] == 0 && growth[id] == 1) {
                    if (player.face == 2) planter.setMirrored(true);
                    else planter.setMirrored(false);
                    type[id] = (inventory.equippedSeed + 1);
                    growth[id] = 2;
                    inventory.planted();
                    swingPlanter = 20;
                    move = false;
                    tool = 4;
                    bury.play();
                }
            } else {
                emptySeed = true;
                donk.play();
                move = false;
                tool = 4;
                swingPlanter = 10;
            }
        }
    }

    /**
     * Check if the player is inside the field area
     */
    boolean inField() {
        return (player.x >= 50 && player.x < 170 &&
            player.y >= 64 && player.y < 140);
    }

    /**
     * Get the Crop ID based on player's position 
     */
    short getFieldId() {
        short x = (player.x - 50) / w;
        short y = (player.y - 64) / h;
        // 12 is the width of the field
        short id = x + y * 12;
        System.out.println(id);
        return id;
    }

    void checkToolUse() {
        switch (tool) {
            case 0:
                break;
            case 1:
                if (swingHoe > 0) {
                    swingHoe--;
                    hoe.swing();
                    hoe.draw(screen, player.x, player.y);
                    if (swingHoe == 0) move = true;
                }
                break;
            case 2:
                if (swingWater > 0) {
                    if (!emptyCan) {
                        water.swing();
                        water.draw(screen, player.x, player.y);
                    } else {
                        actionIcon.draw(screen, 50, 142);
                    }

                    swingWater--;
                    if (swingWater == 0) move = true;

                }
                break;
            case 3:
                if (swingBasket > 0) {
                    swingBasket--;
                    basket.swing();
                    basket.draw(screen, player.x, player.y);
                    if (swingBasket == 0) move = true;
                }
                break;
            case 4:
                if (swingPlanter > 0) {
                    if (!emptySeed) {
                        planter.swing();
                        planter.draw(screen, player.x, player.y);
                    } else {
                        actionIcon.draw(screen, 108, 142);
                    }
                    swingPlanter--;
                    if (swingPlanter == 0) move = true;
                }
                break;
        }
    }

    void renderScene() {
        // Pond
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 19; y++) {
                pond.draw(screen, x * w, y * h);
            }
        }
        for (int i = 0; i < 19; i++) {
            wave.draw(screen, 28, i * h);
        }
        rocks.draw(screen, 36, 66);

        // house
        screen.drawVLine(129, 10, 18, 1);
        for (int i = 0; i < 9; i++) {
            stone.draw(screen, 130 + i * 10, 12);
            stone.draw(screen, 130 + i * 10, 20);
            shadowA.draw(screen, 128+i*10, 28);
        }
        shadowA.draw(screen, 216, 28);
        door.draw(screen, 150, 12);

        // Roof
        shadowB.draw(screen, 119, 14);
        screen.drawVLine(119, 0, 15, 1);
        for (byte i = 0; i < 4; i++) {
            roof.draw(screen, 120 + i * 32, 0);
        }
        

        for (int i = 0; i < 12; i++) {
            grassH.draw(screen, 50 + i * 10, 60);
            grassH.draw(screen, 50 + i * 10, 142, true, true, false);
        }
        for (int i = 0; i < 10; i++) {
            grassV.draw(screen, 46, 64 + i * 8);
            grassV.draw(screen, 168, 64 + i * 8, true, false, false);
        }

        // Tree 
        tree.draw(screen, 184, 4);
        
        // Forest (3)
        smallTree.draw(screen, 38, 0);
        smallTree.draw(screen, 38, 10);
        smallTree.draw(screen, 60, 10);
        rocks.draw(screen, 80, 28);
        
        // Lone tree
        smallTree.draw(screen, 186, 118);
        rocks.draw(screen, 176, 136);
        
    }

    /**
     * Before updateFishing is entered, a random int for fishTime is set.
     * when fishTime is 0, we play hook and set to -1 to not play it again.
     * Then catchTime is set and the player must press `A` before it is over.
     * 
     * If successfull, we give a random money for fish size.
     */
    void updateFishing() {

        if (catchSuccess) {
            if (Button.B.justPressed()) {
                catchSuccess = false;
                if(caughtSize <= 25)inventory.monies += (int)(caughtSize * 2);
                gameState = 0;
                select.play();
            }
            if(caughtSize > 25){
                dialog("You caught... trash...");
                dialogLine("Ew, did it just move?!");
            }else{
                dialog("You caught a fish!\n");
                dialogLine("It is " + (int) caughtSize + "cm! You sell for $" + (int)(caughtSize * 2));
            }
            return;
        }

        rod.draw(screen, player.x + 4, player.y - 2);

        if (Button.B.justPressed()) {
            //cancel out
            select.play();
            catchSuccess = false;
            gameState = 0;
            return;
        }

        if (fishTime > 0) fishTime--;
        if (fishTime == 0) {
            fishTime--;
            notice.play();
            catchTime = 25;
        }

        if (catchTime > 0) {
            actionIcon.draw(screen, player.x + 11, player.y - 14);
            catchTime--;
            if (Button.A.justPressed()) {
                caughtSize = Math.random(2, 45);
                catchSuccess = true;
                catchTime = 0;
            }
        } else if (fishTime < 0) {
            dialog("The fish got away...");
            if (Button.B.justPressed()) {
                gameState = 0;
                select.play();
                catchSuccess = false;
            }
        }
    }

    void dialog(String text) {
        screen.fillRect(0, 0, 220, 30, 1);
        screen.drawRect(1, 1, 217, 27, 14);

        if (gameState == 5) {
            eyes.draw(screen, 204, 4);
        }
        if (gameState == 4) {
            butterflyAvatar.draw(screen, 204, 4);
        }

        screen.setTextPosition(3, 3);
        screen.setTextColor(2);
        screen.print(text);

        screen.setTextPosition(202, 22);
        screen.print("[B]");
    }
    void dialogLine(String text) {
        screen.setTextPosition(3, 11);
        screen.print(text);
    }

    /**
     * Collect crops type and growth into a byte array. 
     * crops.length*2 because each crop has 2 pieces of data (type, growth).
     * 
     * Globals.save will write the data to the "field" file.
     * 
     * Quit to the title screen.
     */
    void saveAndQuit() {
        // Collect field to byte array
        byte[] field = new byte[120 * 2];
        byte id = 0;
        for (int i = 0; i < (120 * 2); i += 2) {
            field[i] = (byte) type[id];
            field[i + 1] = (byte) growth[id];
            id++;
        }

        // Collect items to byte array
        byte[] items = new byte[16];
        id = 0;
        for (int j = 0; j < 16; j += 2) {
            items[j] = inventory.unlocked[id] ? 1 : 0;
            items[j + 1] = inventory.quantities[id];
            id++;
        }
        Globals.saveManager.fishing = fishingAvailable;

        Globals.save(field, items, inventory.monies, day);

        Game.changeState(new Title());
    }

}