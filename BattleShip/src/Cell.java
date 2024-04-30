package BattleShip.src;

public class Cell extends Move {

    private static int[] rowsComp = new int[]{82,124,168,211,254,298,342,386,428,472,516};

    private static int[] rowsUser = new int[]{597,640,684,727,771,814,858,901,945,988,1032};

    private static int[] cols = new int[]{406,448,491,535,578,622,665,709,753,796,840};


    public Cell(int x, int y){
        super(x,y);
    }

    public Cell(String move){
        super(move);
    }

    public static Cell getCell(int x, int y, boolean user){
        int r = check(y,user ? rowsComp:rowsUser);

        int c = check(x,cols);

        return new Cell(r,c);

    }

    public int getXLocation(boolean user) {
        return user? rowsUser[this.row()] : rowsComp[this.row()];
    }

    public int getYLocation(){
        return cols[this.col()];
    }

    private static int check(int n, int[] r){
        for (int i = 0; i < r.length-1; i++){
            if (n >= r[i] && n <= r[i+1]){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }
        Cell cell = (Cell) obj;
        return this.row() == cell.row() && this.col() == cell.col();
    }

}
