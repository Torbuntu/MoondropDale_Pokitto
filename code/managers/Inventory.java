package code.managers;

import femto.mode.HiRes16Color;
import code.Globals;

// For the hud
import sprites.SeedIcons;

import images.HoeIcon;
import images.WaterIcon;
import images.PlanterIcon;
import images.BasketIcon;
import images.FishingRodIcon;

class Inventory {
    // 0:Fishing rod, 1:Hoe, 2:water, 3:basket, 4:Planter -> Seed 
    byte equippedTool;

    // start with 0 (even if we don't have seeds)
    byte equippedSeed;

    // Seed quantity
    /*
    int turnipQ;    0
    int radishQ;    1
    int daisyQ;     2
    int coffeeQ;    3
    int teaQ;       4
    int greenBeanQ; 5
    int tomatoQ;    6
    int blueberryQ; 7
    int magicFruitQ;8
    */

    int monies;

    HoeIcon hoeIcon;
    WaterIcon waterIcon;
    PlanterIcon planterIcon;
    BasketIcon basketIcon;
    FishingRodIcon fishingIcon;

    // Start with a full bucket to not irritate users.
    byte fill;

    SeedIcons seedIcons;

    short[] quantities, cost, unlockPrice;
    boolean[] unlocked;

    Inventory() {

        quantities = new short[9];
        unlocked = new boolean[9];

        byte[] items = Globals.load("items");
        byte id = 0;
        for (byte i = 0; i < 16; i += 2) {
            quantities[id] = items[i + 1];
            unlocked[id] = items[i] == 1;
            id++;
        }
        cost = new short[] {
            1,
            3,
            5,
            8,
            13,
            21,
            34,
            55,
            150
        };
        unlockPrice = new short[] {
            0,
            50,
            150,
            350,
            650,
            950,
            1050,
            5000,
            25000
        };

        monies = Globals.saveManager.monies;

        seedIcons = new SeedIcons();
        seedIcons.turnip();

        hoeIcon = new HoeIcon();
        waterIcon = new WaterIcon();
        planterIcon = new PlanterIcon();
        basketIcon = new BasketIcon();
        fishingIcon = new FishingRodIcon();

        fill = 8;
        equippedSeed = 0;
        equippedTool = 1;
    }

    /**
     * Manages increase in Monies when a crop
     * is harvested.    
     * 
     */
    void harvest(byte id) {
        switch (id) {
            case 0:
                break;
            case 1:
                monies += 5;
                break;
            case 2:
                monies += 15;
                break;
            case 3:
                monies += 40;
                break;
            case 4:
                monies += 65;
                break;
            case 5:
                monies += 80;
                break;
            case 6:
                monies += 105;
                break;
            case 7:
                monies += 150;
                break;
            case 8:
                monies += 175;
                break;
            case 9:
                monies += 500;
                break;
        }
    }

    public int getCost(byte id) {
        if (unlocked[id]) return cost[id];
        else return unlockPrice[id];
    }

    public boolean hasQuantity() {
        return quantities[equippedSeed] > 0;
    }

    public void planted() {
        quantities[equippedSeed]--;
    }

    public boolean buySuccess(byte id) {
        if (unlocked[id] && quantities[id] < 99) {
            if (monies >= cost[id]) {
                monies -= cost[id];
                quantities[id]++;
                return true;
            } else {
                return false;
            }
        } else {
            if (monies >= unlockPrice[id]) {
                monies -= unlockPrice[id];
                unlocked[id] = true;
                return true;
            } else {
                return false;
            }
        }
    }

    // 0:fishing rod, 1:hoe, 2:water, 3:basket, 4:planter 
    void drawHud(HiRes16Color screen, boolean fish) {
        // UI box
        screen.fillRect(0, 152, 220, 24, 1);
        screen.drawRect(1, 153, 120, 22, 14);
        screen.setTextColor(2);
        screen.setTextPosition(124, 155);
        screen.print("$" + monies);

        // Unlock the fishing rod?
        if (fish) fishingIcon.draw(screen, 4, 155);

        hoeIcon.draw(screen, 24, 155);

        waterIcon.draw(screen, 44, 155);
        screen.drawHLine(47, 173, (int)(fill * 14 / 8), 14);

        basketIcon.draw(screen, 64, 155);

        planterIcon.draw(screen, 84, 155);
        switch (equippedSeed) {
            case 0:
                seedIcons.turnip();
                break;
            case 1:
                seedIcons.radish();
                break;
            case 2:
                seedIcons.daisy();
                break;
            case 3:
                seedIcons.coffee();
                break;
            case 4:
                seedIcons.tea();
                break;
            case 5:
                seedIcons.greenBean();
                break;
            case 6:
                seedIcons.tomato();
                break;
            case 7:
                seedIcons.blueberry();
                break;
            case 8:
                seedIcons.magicFruit();
                break;
        }
        if (equippedTool == 4) {
            screen.setPixel(105, 158, 14);
            screen.drawRect(106, 154, 13, 11, 14);
        }
        seedIcons.draw(screen, 108, 156);
        
        screen.setTextPosition(107, 167);
        screen.print( (int) quantities[equippedSeed]);

        // Show selected
        screen.drawRect(3 + equippedTool * 20, 154, 21, 17, 14);
    }

    public void drawTool(HiRes16Color screen) {
        switch (equippedTool) {
            case 0:
                fishingIcon.draw(screen, 146, 50);
                break;
            case 1:
                hoeIcon.draw(screen, 146, 50);
                break;
            case 2:
                waterIcon.draw(screen, 146, 50);
                break;
            case 3:
                basketIcon.draw(screen, 146, 50);
                break;
            case 4:
                planterIcon.draw(screen, 146, 50);
                break;
        }
    }

    public void drawSeed(HiRes16Color screen, byte id) {
        screen.setTextPosition(110, 88);
        screen.print("x" + (int) quantities[id]);

        screen.setTextPosition(110, 80);
        switch (id) {
            case 0:
                seedIcons.turnip();
                screen.print("Turnip");
                break;
            case 1:
                if (unlocked[1]) {
                    seedIcons.radish();
                    screen.print("Radish");
                } else {
                    seedIcons.lock();
                    screen.print("Radish");
                }
                break;
            case 2:
                if (unlocked[2]) {
                    seedIcons.daisy();
                    screen.print("Daisy");
                } else {
                    seedIcons.lock();
                    screen.print("Daisy");
                }
                break;
            case 3:
                if (unlocked[3]) {
                    seedIcons.coffee();
                    screen.print("Coffee");
                } else {
                    seedIcons.lock();
                    screen.print("Coffee");
                }
                break;
            case 4:
                if (unlocked[4]) {
                    seedIcons.tea();
                    screen.print("Tea");
                } else {
                    seedIcons.lock();
                    screen.print("Tea");
                }
                break;
            case 5:
                if (unlocked[5]) {
                    seedIcons.greenBean();
                    screen.print("Green Beans");
                } else {
                    seedIcons.lock();
                    screen.print("Green Beans");
                }
                break;
            case 6:
                if (unlocked[6]) {
                    seedIcons.tomato();
                    screen.print("Tomato");
                } else {
                    seedIcons.lock();
                    screen.print("Tomato");
                }
                break;
            case 7:
                if (unlocked[7]) {
                    seedIcons.blueberry();
                    screen.print("Blueberry");
                } else {
                    seedIcons.lock();
                    screen.print("Blueberry");
                }
                break;
            case 8:
                if (unlocked[8]) {
                    seedIcons.magicFruit();
                    screen.print("Magic Fruit");
                } else {
                    seedIcons.lock();
                    screen.print("Magic Fruit");
                }
                break;
        }
        seedIcons.draw(screen, 110, 64);
    }

    public void drawSeed(HiRes16Color screen) {
        drawSeed(screen, equippedSeed);
    }

}