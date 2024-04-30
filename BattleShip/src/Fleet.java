package BattleShip.src;

public class Fleet {
    private Ship battleShip;
    private Ship aircraftCarrier;
    private Ship cruiser;
    private Ship sub;
    private Ship destroyer;

    public Fleet(){
        this.battleShip = new Battleship();
        this.aircraftCarrier = new AircraftCarrier();
        this.cruiser = new Cruiser();
        this.sub = new Submarine();
        this.destroyer = new Destroyer();
    }

    public boolean updateFleet(ShipType ship){
        switch (ship){
            case ST_AIRCRAFT_CARRIER: return aircraftCarrier.hit();
            case ST_BATTLESHIP: return battleShip.hit();
            case ST_CRUISER: return cruiser.hit();
            case ST_DESTROYER: return destroyer.hit();
            case ST_SUB: return sub.hit();
            default: return false;
        }
    }

    public boolean gameOver(){
        return battleShip.getSunk() && aircraftCarrier.getSunk() && cruiser.getSunk() && sub.getSunk() && destroyer.getSunk();
    }
}
