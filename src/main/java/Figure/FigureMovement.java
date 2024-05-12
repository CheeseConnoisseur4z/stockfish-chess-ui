package Figure;

import Core.Board;

import java.util.ArrayList;

public class FigureMovement
{
    public static ArrayList<int[]> patternMove(Board board, int[][] moveSet, int figureX, int figureY, int figurePlayer, boolean once) {
        ArrayList<int[]> moves = new ArrayList<>();
        boolean onBoard;
        boolean ate;
        for (int[] move : moveSet) {
            int sx = figureX + move[0];
            int sy = figureY + move[1];
            ate = false;
            while (true) {
                onBoard = board.inBounds(sx, sy);
                if (onBoard)
                    ate = board.notNull(sx, sy);
                if (ate || !onBoard)
                    break;
                moves.add(new int[]{sx, sy});
                if (once)
                    break;
                sx += move[0];
                sy += move[1];
            }
            if (ate && board.figureAt(sx, sy).getPlayer() != figurePlayer)
                moves.add(new int[]{sx, sy});
        }
        return moves;
    }

    /*
    conditions for calling this:
    1. king hadn't castled yet
    2. row is clear
    3. it does not result in a check

    public static ArrayList<Rook> validCastlingPartners() {

    }
    */

    public static ArrayList<int[]> enPassant(Board board, int figureX, int figureY, int verticalDirection) {
        ArrayList<int[]> moves = new ArrayList<>();
        return moves;
    }

    public static int[] pawnMove(Board board, int figureX, int figureY, int verticalDirection) {
        int[] move = new int[0];
        if (board.inBounds(figureX, figureY + verticalDirection) && !board.notNull(figureX, figureY + verticalDirection))
            move = new int[]{figureX, figureY + verticalDirection};
        return move;
    }

    public static ArrayList<int[]> pawnEatMove(Board board, int figureX, int figureY, int verticalDirection) {
        ArrayList<int[]> moves = new ArrayList<>();
        for (int i = -1; i <= 1; i += 2)
            if (board.inBounds(figureX - i, figureY + verticalDirection) &&
                    board.notNull(figureX - i, figureY + verticalDirection) &&
                    !board.differentPlayer(figureX - i, figureY + verticalDirection, board.figureAt(figureX, figureY)))
                moves.add(new int[]{figureX - i, figureY + verticalDirection});
        return moves;
    }
}
