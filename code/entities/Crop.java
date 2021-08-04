package code.entities;

// TODO: Make these sprites
// import sprites.Turnip;
// import sprites.Radish;
// import sprites.Daisy;
// import sprites.Coiffee;
// import sprites.Tea;
// import sprites.GreenBean;
// import sprites.Tomato;
// import sprites.Cranberry;
// import sprites.Blueberry;
// import sprites.MagicFruit;

public class Crop {
    int type, growth, water, x, y;
    
    // Turnip turnip;
    // Radish radish;
    // Daisy daisy;
    // Coffee coffee;
    // Tea tea;
    // GreenBean greenBean;
    // Tomato tomato;
    // Cranberry cranberry;
    // Blueberry blueberry;
    // MagicFruit magicFruit;
    
    public Crop(int type, int growth, int x, int y){
        this.type = type;
        this.growth = growth;
        this.x = x;
        this.y = y;
        water = 0;
    }
    
    
    void update(){}
    
    void render(){}
    
    int getType(){return type;}
    int getX(){return x;}
    int getY(){return y;}
}