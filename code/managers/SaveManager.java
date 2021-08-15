package code.managers;

class SaveManager extends femto.Cookie {
    SaveManager() {
        super();
        begin("MDDALE");
    }
    short day;
    byte character;
    int monies;
    boolean fishing;
}