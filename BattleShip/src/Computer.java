package BattleShip.src;

import java.util.ArrayList;
import java.util.Random;


public class Computer{

    private ArrayList<Move> moves = new ArrayList<>();

    private boolean[] shipsRemaining = {true, true, true, true, true};

    ArrayList<Move> cellsToSearch;

    private UserBoard board;

    public Computer(UserBoard board) {
        for (int i = 0; i < UserBoard.SIZE; i++){
            for (int j = 0; j < UserBoard.SIZE/2; j++){
                if (i%2 == 0){
                    moves.add(new Move(i, j*2));
                }else{
                    moves.add(new Move(i,(j*2)+1));
                }
                
            }
        }        

        cellsToSearch = new ArrayList<>();
        this.board = board;

    }

    public ArrayList<Move> hunt(Move target){

        Move posCol = new Move(target.row(),target.col()+1);
        Move minCol = new Move(target.row(),target.col()-1);
        Move posRow = new Move(target.row()+1,target.col());
        Move minRow = new Move(target.row()-1,target.col());

        if(posCol.col() < 10 && board.moveAvailable(posCol)){
            cellsToSearch.add(posCol);
        }
        if(minCol.col() >= 0 && board.moveAvailable(minCol)){
            cellsToSearch.add(minCol);
        }
        if(posRow.row() < 10 && board.moveAvailable(posRow)){
            cellsToSearch.add(posRow);
        }
        if(minRow.row() >= 0 && board.moveAvailable(minRow)){
            cellsToSearch.add(minRow);
        }
        
        for (int i = 0; i < cellsToSearch.size(); i++){
            if (cellsToSearch.get(i).row() == 0 && cellsToSearch.get(i).col() == 0){
                System.out.println(cellsToSearch.remove(i));
            }
        }

        return cellsToSearch;

    }


    public Move search(){
        if (moves.size() == 0){
            return null;
        }
        return moves.remove(new Random().nextInt(moves.size()-1));
    }

    public Move playGame(){
        if (cellsToSearch.size() == 0){
            return search();
        }

        moves.remove(cellsToSearch.get(0));
        return cellsToSearch.remove(0);

    }

    public void updateModel(Move move, String moveResult){
        switch (moveResult){
            case "o": break;
            case "H":   hunt(move); break;
            case "x": break;
            case "A": shipsRemaining[4] = false; break;
            case "B": shipsRemaining[3] = false; break;
            case "C": shipsRemaining[2] = false; break;
            case "D": shipsRemaining[1] = false; break;
            case "S": shipsRemaining[0] = false; break;
        }
    }
    
}
