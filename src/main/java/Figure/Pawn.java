package Figure;

import Core.Board;

public class Pawn extends Figure
{

    public Pawn(int player, int x, int y) {
        super(Enum.PAWN, player, x, y);
    }

    public void generateMoves(Board board) {
        getMoves().clear();
        int[] pawnMove = FigureMovement.pawnMove(board, this.getX(), this.getY(), (getPlayer() == 0) ? -1 : 1);
        if (pawnMove.length != 0) {
            getMoves().add(pawnMove);
            if (!relativelyKindlyAskTheFigureIfItHadMovedAlready()) {
                pawnMove = FigureMovement.pawnMove(board, this.getX(), this.getY(), (getPlayer() == 0) ? -2 : 2);
                if (pawnMove.length != 0)
                    getMoves().add(pawnMove);
            }
        }
        getMoves().addAll(FigureMovement.pawnEatMove(board, this.getX(), this.getY(), (getPlayer() == 0) ? -1 : 1));
        getMoves().addAll(FigureMovement.enPassant(board, this.getX(), this.getY(), (getPlayer() == 0) ? -1 : 1));
    }
}
