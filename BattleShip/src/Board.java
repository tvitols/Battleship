package BattleShip.src;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public abstract class Board {
    private ArrayList<ArrayList<CellStatus>> layout = new ArrayList<ArrayList<CellStatus>>();

    private Fleet fleet;

    public final static int SIZE = 10;

    public Board(String url){

        fleet = new Fleet();
        ArrayList<CellStatus> row = new ArrayList<CellStatus>();
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                row.add(j,CellStatus.NOTHING);
            }
            layout.add(i,new ArrayList<CellStatus>(row));
            row.clear();
        }
        Scanner load = new Scanner(System.in);

        try {
            load = new Scanner(new File("BATTLESHIP/src/" + url));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("file not found");
        }

        while (load.hasNextLine()){
            String[] line = load.nextLine().split(" ");
            Move startCell = new Move(line[1]);
            Move endCell = new Move(line[2]);
            switch (line[0].charAt(0)){
                case 'A': for (int r = startCell.row(); r<=endCell.row();r++){
                                for (int c = startCell.col(); c <= endCell.col(); c++){
                                    layout.get(r).set(c,CellStatus.AIRCRAFT_CARRIER);
                                }
                            }
                            break;
                case 'B': for (int r = startCell.row(); r<=endCell.row();r++){
                                for (int c = startCell.col(); c <= endCell.col(); c++){
                                    layout.get(r).set(c,CellStatus.BATTLESHIP);
                                }
                            }
                            break;
                case 'C': for (int r = startCell.row(); r<=endCell.row();r++){
                                for (int c = startCell.col(); c <= endCell.col(); c++){
                                    layout.get(r).set(c,CellStatus.CRUISER);
                                }
                            }
                            break;
                case 'D': for (int r = startCell.row(); r<=endCell.row();r++){
                                for (int c = startCell.col(); c <= endCell.col(); c++){
                                    layout.get(r).set(c,CellStatus.DESTROYER);
                                }
                            }
                            break;
                case 'S': for (int r = startCell.row(); r<=endCell.row();r++){
                                for (int c = startCell.col(); c <= endCell.col(); c++){
                                    layout.get(r).set(c,CellStatus.SUB);
                                }
                            }
                            break;
                default: ;}
            }
        }


    public CellStatus applyMoveToLayout(Move move){
        String cell = layout.get(move.row()).get(move.col()).toString();
        if (moveAvailable(move)){

            @SuppressWarnings ("unused")
            CellStatus dummy;
            boolean sunk;
            switch (cell.charAt(1)){
                case 'A': sunk = fleet.updateFleet(ShipType.ST_AIRCRAFT_CARRIER);
                    dummy =  sunk ? layout.get(move.row()).set(move.col(),CellStatus.AIRCRAFT_CARRIER_SUNK) : layout.get(move.row()).set(move.col(),CellStatus.AIRCRAFT_CARRIER_HIT);
                    if (sunk){
                        for (int row = 0; row < SIZE; row++){
                            for (int col = 0; col < SIZE; col++){
                                if (layout.get(row).get(col) == CellStatus.AIRCRAFT_CARRIER_HIT){
                                    layout.get(row).set(col,CellStatus.AIRCRAFT_CARRIER_SUNK);
                                }
                            }
                        }
                    }break;
                case 'B':  sunk = fleet.updateFleet(ShipType.ST_BATTLESHIP);
                    dummy = sunk ? layout.get(move.row()).set(move.col(),CellStatus.BATTLESHIP_SUNK) : layout.get(move.row()).set(move.col(),CellStatus.BATTLESHIP_HIT); 
                    if (sunk){
                        for (int row = 0; row < SIZE; row++){
                            for (int col = 0; col < SIZE; col++){
                                if (layout.get(row).get(col) == CellStatus.BATTLESHIP_HIT){
                                    layout.get(row).set(col,CellStatus.BATTLESHIP_SUNK);
                                }
                            }
                        }
                    }break;
                case 'C':  sunk = fleet.updateFleet(ShipType.ST_CRUISER);
                    dummy = sunk ? layout.get(move.row()).set(move.col(),CellStatus.CRUISER_SUNK) : layout.get(move.row()).set(move.col(),CellStatus.CRUISER_HIT); 
                    if (sunk){
                        for (int row = 0; row < SIZE; row++){
                            for (int col = 0; col < SIZE; col++){
                                if (layout.get(row).get(col) == CellStatus.CRUISER_HIT){
                                    layout.get(row).set(col,CellStatus.CRUISER_SUNK);
                                }
                            }
                        }
                    }break;
                case 'D': sunk = fleet.updateFleet(ShipType.ST_DESTROYER);
                    dummy = sunk ? layout.get(move.row()).set(move.col(),CellStatus.DESTROYER_SUNK) : layout.get(move.row()).set(move.col(),CellStatus.DESTROYER_HIT); 
                    if (sunk){
                        for (int row = 0; row < SIZE; row++){
                            for (int col = 0; col < SIZE; col++){
                                if (layout.get(row).get(col) == CellStatus.DESTROYER_HIT){
                                    layout.get(row).set(col,CellStatus.DESTROYER_SUNK);
                                }
                            }
                        }
                    }break;
                case 'S': sunk = fleet.updateFleet(ShipType.ST_SUB);
                    dummy = sunk ? layout.get(move.row()).set(move.col(),CellStatus.SUB_SUNK) : layout.get(move.row()).set(move.col(),CellStatus.SUB_HIT); 
                    if (sunk){
                        for (int row = 0; row < SIZE; row++){
                            for (int col = 0; col < SIZE; col++){
                                if (layout.get(row).get(col) == CellStatus.SUB_HIT){
                                    layout.get(row).set(col,CellStatus.SUB_SUNK);
                                }
                            }
                        }
                    }break;
                default: dummy = layout.get(move.row()).set(move.col(),CellStatus.NOTHING_HIT);
            }

        }return layout.get(move.row()).get(move.col());
    }

    public boolean moveAvailable(Move move){
        char cell = layout.get(move.row()).get(move.col()).toString().charAt(1);
        return cell != 'X' && cell != 'x';
    }


    public ArrayList<ArrayList<CellStatus>> getLayout() {
        return this.layout;
    }

    public Fleet getFleet() {
        return this.fleet;
    }

    public boolean gameOver(){
        return fleet.gameOver();
    }

}
