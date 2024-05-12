package Figure;

import Core.Board;

public class Rook extends Figure
{
    static public int[][] moveSet = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public Rook(int player, int x, int y) {
        super(Enum.ROOK, player, x, y);
    }

    public void generateMoves(Board board) {
        getMoves().clear();
        getMoves().addAll(FigureMovement.patternMove(board, moveSet, getX(), getY(), getPlayer(), false));
    }
}
