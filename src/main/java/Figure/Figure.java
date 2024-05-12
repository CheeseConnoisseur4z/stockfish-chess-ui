package Figure;

import Core.Board;

import java.util.ArrayList;

public abstract class Figure
{
    private final ArrayList<int[]> moves = new ArrayList<>();
    private final int[] position = new int[2];
    public Enum ID;
    private final int player;
    private int movedTimes = 0;


    public Figure(Enum ID, int player, int x, int y) {
        this.ID = ID;
        this.player = player;
        this.position[0] = x;
        this.position[1] = y;
    }


    public abstract void generateMoves(Board board);


    public boolean canMoveTo(int tX, int tY) {
        for (int[] move : moves) {
            if (move[0] == tX && move[1] == tY)
                return true;
        }
        return false;
    }


    public void kindlyInformTheFigureThatItHadBeenMovedAndToWhereItHadBeenMoved(int tX, int tY) {
        this.position[0] = tX;
        this.position[1] = tY;
        this.movedTimes++;
    }


    public void doNotInformTheFigureThatItHadBeenMovedBecauseItsASecret(int tX, int tY) {
        this.position[0] = tX;
        this.position[1] = tY;
    }


    public boolean relativelyKindlyAskTheFigureIfItHadMovedAlready() {
        return movedTimes > 0;
    }


    public int getPlayer() {
        return this.player;
    }

    public ArrayList<int[]> getMoves() { return this.moves; }

    public int getX() { return this.position[0]; }

    public int getY() { return this.position[1]; }

}
