package BattleShip.src;

public class Move {
    private int row;
    private int col;


    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Move(String loc){
        this.row = (int) loc.charAt(0) - 65;
        this.col = Integer.valueOf(loc.substring(1))-1;
    }


    public int row() {
        return this.row;
    }

    public int col() {
        return this.col;
    }

    @Override
    public String toString(){
        return String.format("%s%d",(char)(row+65),col+1);
    }

    @Override
    public boolean equals(Object obj){
        Move move = (Move)obj;
        return row == move.row && col == move.col;
    }

}
