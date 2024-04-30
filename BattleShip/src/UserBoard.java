package BattleShip.src;

import java.util.ArrayList;
import java.util.Random;


public class UserBoard extends Board{

    private ArrayList<Move> moves = new ArrayList<>();
    private Random rand;

    public UserBoard(String url){
        super(url);
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                moves.add(new Move(i, j));
            }
        }
        rand = new Random();
    }

    public String[] makeComputerMove(){
        Move move = pickRandomMove();
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
        String hit = (status == 'H' | status == 'x') ? null : String.format("You sank my %s", ship);
        return new String[]{move.toString(), hit};
    
    }

     public String[] makeDefinedComputerMove(Move move){
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
        String hit = (status == 'H' | status == 'x') ? null : String.format("You sank my %s", ship);
        return new String[]{move.toString(), hit};
    
    }

    public Move pickRandomMove(){
        if (moves.size() == 0){
            return null;
        }
        return moves.remove(rand.nextInt(moves.size()-1));
    }

    @Override
    public String toString(){
        String out = "";
        for (ArrayList<CellStatus> row : getLayout()){
            for (CellStatus status : row){
                out += status.toString().charAt(1) + "\t";
            }
            out += "\n";
        }
        return out;
    }
}