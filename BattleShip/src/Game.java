package BattleShip.src;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import java.io.File;

import BattleShip.src.ImageHandler.ImageName;

public class Game{
    private UserBoard player;
    private ComputerBoard computer;
    private boolean userTurn;

    private static final ShipType[] ordered = {ShipType.ST_AIRCRAFT_CARRIER,ShipType.ST_BATTLESHIP,ShipType.ST_CRUISER,ShipType.ST_SUB,ShipType.ST_DESTROYER};

    private HashMap<String,Object[]> playerShips;
    private HashMap<String,Object[]> computerShips;

    private int difficulty;

    private Computer comptuterAI;

    public Game(int difficulty) {
        setCompShips();
        computer = new ComputerBoard("compFleet.txt");
        player = new UserBoard("userFleet.txt");
        playerShips = loadShips("userFleet.txt");
        computerShips = loadShips("compFleet.txt");
        userTurn = new Random().nextBoolean();
        this.difficulty = difficulty;
        if (difficulty ==  1){
            comptuterAI = new Computer(player);
        }

    }

    public static void setShips(Cell[] startCell, boolean[] vert){
        String out = "";
        for (int i = 0; i < 5; i++){
            switch (i){
                case 0: out += String.format("A %s%d %s%d\n",(char)(startCell[0].row()+65),startCell[0].col()+1, vert[0] ? (char)(startCell[0].row()+69) : (char)(startCell[0].row()+65), vert[0] ? startCell[0].col()+1 : startCell[0].col()+5); break;
                case 1: out += String.format("B %s%d %s%d\n",(char)(startCell[1].row()+65),startCell[1].col()+1, vert[1] ? (char)(startCell[1].row()+68) : (char)(startCell[1].row()+65), vert[1] ? startCell[1].col()+1 : startCell[1].col()+4); break;
                case 2: out += String.format("C %s%d %s%d\n",(char)(startCell[2].row()+65),startCell[2].col()+1, vert[2] ? (char)(startCell[2].row()+67) : (char)(startCell[2].row()+65), vert[2] ? startCell[2].col()+1 : startCell[2].col()+3); break;
                case 3: out += String.format("S %s%d %s%d\n",(char)(startCell[3].row()+65),startCell[3].col()+1, vert[3] ? (char)(startCell[3].row()+67) : (char)(startCell[3].row()+65), vert[3] ? startCell[3].col()+1 : startCell[3].col()+3); break;
                case 4: out += String.format("D %s%d %s%d\n",(char)(startCell[4].row()+65),startCell[4].col()+1, vert[4] ? (char)(startCell[4].row()+66) : (char)(startCell[4].row()+65), vert[4] ? startCell[4].col()+1 : startCell[4].col()+2); 
            }
        }
        System.out.println(out);
        try {
            FileWriter fw = new FileWriter(new File("BATTLESHIP/src/userFleet.txt"));
            fw.write(out);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setCompShips(){
        int[] offset = {6,7,8,8,9};
        Random r = new Random();
        String out = "";
        for (int i = 0; i < 5; i++){

            boolean vert;
            Cell startCell;

            do {

                vert = r.nextBoolean();

                startCell = vert ? new Cell(r.nextInt(offset[i]), r.nextInt(9)) : new Cell(r.nextInt(9),r.nextInt(offset[i]));
                
            } while (!validPlacement(ordered[i],startCell,vert,out));
            
            switch (i){
                case 0: out += String.format("A %s%d %s%d\n",(char)(startCell.row()+65),startCell.col()+1, vert ? (char)(startCell.row()+69) : (char)(startCell.row()+65), vert ? startCell.col()+1 : startCell.col()+5); break;
                case 1: out += String.format("B %s%d %s%d\n",(char)(startCell.row()+65),startCell.col()+1, vert ? (char)(startCell.row()+68) : (char)(startCell.row()+65), vert ? startCell.col()+1 : startCell.col()+4); break;
                case 2: out += String.format("C %s%d %s%d\n",(char)(startCell.row()+65),startCell.col()+1, vert ? (char)(startCell.row()+67) : (char)(startCell.row()+65), vert ? startCell.col()+1 : startCell.col()+3); break;
                case 3: out += String.format("S %s%d %s%d\n",(char)(startCell.row()+65),startCell.col()+1, vert ? (char)(startCell.row()+67) : (char)(startCell.row()+65), vert ? startCell.col()+1 : startCell.col()+3); break;
                case 4: out += String.format("D %s%d %s%d\n",(char)(startCell.row()+65),startCell.col()+1, vert ? (char)(startCell.row()+66) : (char)(startCell.row()+65), vert ? startCell.col()+1 : startCell.col()+2); 
            }
        }
        System.out.println(out);
        try {
            FileWriter fw = new FileWriter(new File("BATTLESHIP/src/compFleet.txt"));
            fw.write(out);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean validPlacement(ShipType ship, Cell place, boolean vert, String currentShips){
        if (currentShips.equals("")){
            return true;
        }
        String[] placements = currentShips.split("\n");
        Cell[] cellsOccupied = new Cell[placements.length*6];
        int count = 0;
        for (String s : placements){
            String[] line = s.split(" ");
            Cell startCell = new Cell(line[1]);
            Cell endCell = new Cell(line[2]);
            boolean shipVert = startCell.col() == endCell.col();
            for (int i = shipVert ? startCell.row() -1 : startCell.col()-1; i < (shipVert ? endCell.row() +1 : endCell.col() +1 ); i++){
                cellsOccupied[count] = shipVert ? new Cell(i,startCell.col()) : new Cell(startCell.row(),i);
                count++;
            }
        }
        
        switch (ship){
            case ST_AIRCRAFT_CARRIER:
                for (int i = vert ? place.row() : place.col(); i < (vert ? place.row() + 6 : place.col() + 6); i++){
                    for (Cell c : cellsOccupied){
                            if (c == null ? false : c.equals(vert ? new Cell(i,place.col()) : new Cell(place.row(),i))){
                                return false;
                            }
                    }
                }break;
            case ST_BATTLESHIP: 
                for (int i = vert ? place.row() : place.col(); i < (vert ? place.row() + 5 : place.col() + 5); i++){
                    for (Cell c : cellsOccupied){
                            if (c == null ? false : c.equals(vert ? new Cell(i,place.col()) : new Cell(place.row(),i))){
                                return false;
                            }
                    }
                }break;
            case ST_CRUISER:
                for (int i = vert ? place.row() : place.col(); i < (vert ? place.row() + 4 : place.col() + 4); i++){
                    for (Cell c : cellsOccupied){
                            if (c == null ? false : c.equals(vert ? new Cell(i,place.col()) : new Cell(place.row(),i))){
                                return false;
                            }
                    }
                }break;
            case ST_DESTROYER:
                for (int i = vert ? place.row() : place.col(); i < (vert ? place.row() + 4 : place.col() + 4); i++){
                        for (Cell c : cellsOccupied){
                            if (c == null ? false : c.equals(vert ? new Cell(i,place.col()) : new Cell(place.row(),i))){
                                return false;
                            }
                    }
                    }break;
            case ST_SUB:
                    for (int i = vert ? place.row() : place.col(); i < (vert ? place.row() + 3 : place.col() + 3); i++){
                    for (Cell c : cellsOccupied){
                            if (c == null ? false : c.equals(vert ? new Cell(i,place.col()) : new Cell(place.row(),i))){
                                return false;
                            }
                    }
                }break;
        }

        return true;
    }

    public static boolean validPlacement(int ship, Cell place, boolean vert, Cell[] currentShips, boolean[] currentVerts){
        Cell[] cellsOccupied = new Cell[currentShips.length*5];
        int[] lengths = {5,4,3,3,2};
        int count = 0;
        for (int i = 0; i < ship; i++){
            Cell startCell = currentShips[i];
            boolean shipVert = currentVerts[i];
            Cell endCell = shipVert ? new Cell(startCell.row()+lengths[i],startCell.col()) : new Cell(startCell.row(),startCell.col()+lengths[i]) ;
            for (int j = shipVert ? startCell.row() : startCell.col(); j < (shipVert ? endCell.row() : endCell.col()); j++){
                cellsOccupied[count] = shipVert ? new Cell(j,startCell.col()) : new Cell(startCell.row(),j);
                count++;
            }
        }

        Cell placeCell = new Cell(place.row(),place.col());
        switch (ordered[ship]){
            case ST_AIRCRAFT_CARRIER:
                for (int i = vert ? placeCell.row() : placeCell.col(); i < (vert ? placeCell.row() + 5 : placeCell.col() + 5); i++){
                    for (Cell c : cellsOccupied){
                            if (c == null ? false : c.equals(vert ? new Cell(i,placeCell.col()) : new Cell(placeCell.row(),i))){
                                return false;
                            }
                    }
                }break;
            case ST_BATTLESHIP: 
                for (int i = vert ? placeCell.row() : placeCell.col(); i < (vert ? placeCell.row() + 4 : placeCell.col() + 4); i++){
                    for (Cell c : cellsOccupied){
                            if (c == null ? false : c.equals(vert ? new Cell(i,placeCell.col()) : new Cell(placeCell.row(),i))){
                                return false;
                            }
                    }
                }break;
            case ST_CRUISER:
                for (int i = vert ? placeCell.row() : placeCell.col(); i < (vert ? placeCell.row() + 3 : placeCell.col() + 3); i++){
                    for (Cell c : cellsOccupied){
                            if (c == null ? false : c.equals(vert ? new Cell(i,placeCell.col()) : new Cell(placeCell.row(),i))){
                                return false;
                            }
                    }
                }break;
            case ST_DESTROYER:
                for (int i = vert ? placeCell.row() : placeCell.col(); i < (vert ? placeCell.row() + 3 : placeCell.col() + 3); i++){
                        for (Cell c : cellsOccupied){
                            if (c == null ? false : c.equals(vert ? new Cell(i,placeCell.col()) : new Cell(placeCell.row(),i))){
                                return false;
                            }
                    }
                    }break;
            case ST_SUB:
                    for (int i = vert ? placeCell.row() : placeCell.col(); i < (vert ? placeCell.row() + 2 : placeCell.col() + 2); i++){
                    for (Cell c : cellsOccupied){
                            if (c == null ? false : c.equals(vert ? new Cell(i,placeCell.col()) : new Cell(placeCell.row(),i))){
                                return false;
                            }
                    }
                }break;
        }

        return true;
    }

    public HashMap<String,Object[]> loadShips(String url){
        Scanner load = new Scanner(System.in);
        try {
            load = new Scanner(new File("BATTLESHIP/src/" + url));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("file not found");
        }

        HashMap<String,Object[]> out = new HashMap<>(5);

        while (load.hasNextLine()){
            String[] line = load.nextLine().split(" ");
            Move startCell = new Move(line[1]);
            Move endCell = new Move(line[2]);
            boolean vert = startCell.col() != endCell.col();
            out.put(line[0], new Object[]{vert,startCell,null});
        }
        for (String ship : out.keySet()){
            Object[] newShip = out.get(ship);
            switch (ship){
                case "A": newShip[2] = 211; break;
                case "B": newShip[2] = 168; break;
                case "C": newShip[2] = 126; break;
                case "D": newShip[2] = 84; break;
                case "S": newShip[2] = 126;
            }
            @SuppressWarnings("unused")
            Object[] __ = out.replace(ship, newShip);
        }
        return out;
        
    }

    public boolean turn(){
        return userTurn;
    }

    public static boolean bounds(int x, int y,boolean user){
        return (x >= 406 && x <= 840) && (user ? (y >= 82 && y <= 516):(y >= 597 && y <= 1032));
    }
    public boolean bounds(int x, int y){
        return (x >= 406 && x <= 840) && (turn() ? (y >= 82 && y <= 516):(y >= 597 && y <= 1032));
    }

    public boolean playing(){
        return !computer.gameOver() && !player.gameOver();
    }

    public boolean getWinner(){
        return player.gameOver();
    }

    public ImageName getCellStatus(int x, int y, boolean board){
        if (board){
            switch(player.getLayout().get(x).get(y).toString().charAt(0)){
                case 'o': return null;
                case 'H': return ImageName.BAD_HIT;
                case 'x': return ImageName.MISS;
                default: return ImageName.SUNK;
            }
        }
        switch(computer.getLayout().get(x).get(y).toString().charAt(0)){
                case 'o': return null;
                case 'H': return ImageName.GOOD_HIT;
                case 'x': return ImageName.MISS;
                default: return ImageName.SUNK;
            }

    }

    public Object[] getShip(ShipType ship, boolean user){
        switch (ship){
            case ST_AIRCRAFT_CARRIER: return user ? playerShips.get("A") : computerShips.get("A");
            case ST_BATTLESHIP: return user ? playerShips.get("B") : computerShips.get("B");
            case ST_CRUISER: return user ? playerShips.get("C") : computerShips.get("C");
            case ST_DESTROYER: return user ? playerShips.get("D") : computerShips.get("D");
            case ST_SUB: return user ? playerShips.get("S") : computerShips.get("S");
            default : return null;
        }
    }

    public Object[][] getShips(boolean user){
        Object[][] out = new Object[5][2];
        int count = 0;
        for (ShipType s : ShipType.values()){
            Object[] ship = getShip(s, user);
            ImageName image = null;
            Move cell  = (Move)ship[1];
            char stat = user ? player.getLayout().get(cell.row()).get(cell.col()).toString().charAt(0) : computer.getLayout().get(cell.row()).get(cell.col()).toString().charAt(0);
            if ((stat != 'o' && stat != 'x' && stat != 'H') || user){
                switch (s){
                    case ST_AIRCRAFT_CARRIER: image = (boolean)ship[0] ? ImageName.AIRCRAFTCARRIER : ImageName.AIRCRAFTCARRIER_VERT; break;
                    case ST_BATTLESHIP: image = (boolean)ship[0] ? ImageName.BATTLESHIP : ImageName.BATTLESHIP_VERT; break;
                    case ST_CRUISER: image = (boolean)ship[0] ? ImageName.CRUISER : ImageName.CRUISER_VERT; break;
                    case ST_DESTROYER: image = (boolean)ship[0] ? ImageName.DESTROYER : ImageName.DESTROYER_VERT; break;
                    case ST_SUB:image = (boolean)ship[0] ? ImageName.SUBMARINE : ImageName.SUBMARINE_VERT; break;
                    default : break;
                }
            }
            out[count] = new Object[]{image,ship[1],ship[0],ship[2],stat};
            count++;
        }
        return out;
    }

    public String takeTurn(int x, int y){
        Cell cell = Cell.getCell(x, y, userTurn);
        userTurn = !userTurn;
        String turn = computer.makePlayerMove(new Move(cell.row(),cell.col()));
        String[] list = {};

        if (turn != null){
            list = turn.split(" ");
        }

        return ( turn != null ?  list[list.length-1]: String.valueOf(computer.getLayout().get(cell.row()).get(cell.col()).toString().charAt(0)));
    }
    
    public String[] compTurn(){
        
        String[] turn;

        if (difficulty == 1){
             turn = player.makeDefinedComputerMove(comptuterAI.playGame());
        }else{
            turn = player.makeComputerMove();
        }
        Move cell = new Move(turn[0]);

        String moveResult = String.valueOf(player.getLayout().get(cell.row()).get(cell.col()).toString().charAt(0));

        if (difficulty == 1){
            comptuterAI.updateModel(cell,moveResult);
        }

        String[] list = {};

        if (turn[1] != null){
            list = turn[1].split(" ");
        }

        userTurn = !userTurn;

        return new String[]{turn[0],(turn[1] != null ? list[list.length-1] : moveResult)};
    }

}
