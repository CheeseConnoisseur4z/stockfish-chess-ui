package Figure;

import Core.Board;

public class King extends Figure
{
    static int[][] moveSet = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, 1}, {1, 1}, {-1, -1}, {1, -1}};

    public King(int player, int x, int y) {
        super(Enum.KING, player, x, y);
    }

    public void generateMoves(Board board) {
        getMoves().clear();
        getMoves().addAll(FigureMovement.patternMove(board, moveSet, getX(), getY(), getPlayer(), true));
    }
}
