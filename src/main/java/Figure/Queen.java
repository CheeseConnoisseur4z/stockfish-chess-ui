package Figure;

import Core.Board;

public class Queen extends Figure
{
    static int[][] moveSet = new int[][]{{1, 0},  {-1, 0}, {0, 1}, {0, -1}, {-1, 1}, {1, 1}, {-1, -1}, {1, -1}};

    public Queen(int player, int x, int y) {
        super(Enum.QUEEN, player, x, y);
    }

    public void generateMoves(Board board) {
        getMoves().clear();
        getMoves().addAll(FigureMovement.patternMove(board, moveSet, getX(), getY(), getPlayer(), false));
    }
}
