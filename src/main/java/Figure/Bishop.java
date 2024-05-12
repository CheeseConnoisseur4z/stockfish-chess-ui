package Figure;

import Core.Board;

public class Bishop extends Figure
{
    public static int[][] moveSet = new int[][]{{-1, 1}, {1, 1}, {-1, -1}, {1, -1}};

    public Bishop(int player, int x, int y) {
        super(Enum.BISHOP, player, x, y);
    }

    public void generateMoves(Board board) {
        getMoves().clear();
        getMoves().addAll(FigureMovement.patternMove(board, moveSet, getX(), getY(), getPlayer(), false));
    }
}
