package Figure;

import Core.Board;

public class Knight extends Figure
{
    public static int[][] moveSet = new int[][]{{1, 2}, {-1, 2}, {2, 1}, {-2, 1}, {-1, -2}, {1, -2}, {-2, -1}, {2, -1}};

    public Knight(int player, int x, int y) {
        super(Enum.KNIGHT, player, x, y);
    }

    public void generateMoves(Board board) {
        getMoves().clear();
        getMoves().addAll(FigureMovement.patternMove(board, moveSet, getX(), getY(), getPlayer(), true));
    }
}
