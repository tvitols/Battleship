package BattleShip.src;

import java.util.ArrayList;

public class ComputerBoard extends Board{
    public ComputerBoard(String url){
        super(url);
    }

    public String makePlayerMove(Move move){
        applyMoveToLayout(move);
        char status = getLayout().get(move.row()).get(move.col()).toString().charAt(0);
        String ship = "";
        switch(status){
            case 'A': ship = "Aircraft Carrier"; break;
            case 'B': ship = "Battleship"; break;
            case 'C': ship = "Cruiser"; break;
            case 'D': ship = "Destroyer"; break;
            case 'S': ship = "Submarine"; break;
            default: ship = null;}
        return  (status == 'H' | status == 'x') ? null : String.format("You sank my %s", ship);
    }

    @Override
    public String toString(){
        String out = "";
        for (ArrayList<CellStatus> row : getLayout()){
            for (CellStatus status : row){
                out += status.toString().charAt(0) + "\t";
            }
            out += "\n";
        }
        return out;
    }
}
