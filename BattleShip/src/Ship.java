package BattleShip.src;


public abstract class Ship {
    private int size;
    private int hits = 0;
    private boolean sunk = false;
    
    public Ship(int size) {
        this.size = size;
    }

    public boolean hit(){
        hits ++;
        sunk = (size == hits);
        return sunk;
    }

    public boolean getSunk(){
        return sunk;
    }
}
