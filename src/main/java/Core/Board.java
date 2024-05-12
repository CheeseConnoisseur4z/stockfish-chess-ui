package Core;

import Figure.*;
import Figure.Enum;

import java.util.ArrayList;

public class Board
{
    private final Figure[][] board = new Figure[8][8];
    private King normalKing;
    private King blackKing;
    private King king;
    private final ArrayList<Figure> normalFiguresInPlay = new ArrayList<>();
    private final ArrayList<Figure> blackFiguresInPlay = new ArrayList<>();


    public void callMoveGeneration() {
        for (Figure figure : normalFiguresInPlay)
            figure.generateMoves(this);
        for (Figure figure : blackFiguresInPlay)
            figure.generateMoves(this);
    }


    public void validateMoves() {
        if (!checkCheck()) {
            ArrayList<int[]> moves = FigureMovement.patternMove(this,
                    new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1} ,{-1, 1}, {1, 1}, {-1, -1}, {1, -1}},
                    king.getX(), king.getY(), (king.getPlayer() == 0) ? 1 : 0, false);
            removeInvalidMoves(king);
            for (int[] move : moves) {
                if (notNull(move[0], move[1])) {
                    Figure figure = figureAt(move[0], move[1]);
                    if (figure.getPlayer() == king.getPlayer()) {
                        removeInvalidMoves(figure);
                    }
                }
            }
        } else
            for (Figure figure : (king.getPlayer() == 0) ? normalFiguresInPlay : blackFiguresInPlay)
                removeInvalidMoves(figure);
        for (int[] move : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1} ,{-1, 1}, {1, 1}, {-1, -1}, {1, -1}})
            king.getMoves().removeIf(m -> m[0] == move[0] + otherKing().getX() && m[1] == move[1] + otherKing().getY());
    }


    private void removeInvalidMoves(Figure figure) {
        ArrayList<int[]> toRemove = new ArrayList<>();
        int startX = figure.getX();
        int startY = figure.getY();
        figure.getMoves().forEach(m -> {
            Figure saveFigure = figureAt(m[0], m[1]);
            board[m[0]][m[1]] = board[startX][startY];
            figure.doNotInformTheFigureThatItHadBeenMovedBecauseItsASecret(m[0], m[1]);
            board[startX][startY] = null;
            if (checkCheck())
                toRemove.add(m);
            board[startX][startY] = board[m[0]][m[1]];
            figure.doNotInformTheFigureThatItHadBeenMovedBecauseItsASecret(startX, startY);
            board[m[0]][m[1]] = saveFigure;
        });
        figure.getMoves().removeAll(toRemove);
    }


    public boolean doFiguresHaveTheAbilityToTraverseTheBoard() {
        for (Figure figure : (king.getPlayer() == 0) ? normalFiguresInPlay : blackFiguresInPlay) {
            if (!figure.getMoves().isEmpty())
                return true;
        }
        return false;
    }


    public boolean checkCheck() {
        return isKingAttackedBy(Rook.moveSet, Enum.ROOK, Enum.QUEEN) ||
                isKingAttackedBy(Bishop.moveSet, Enum.BISHOP, Enum.QUEEN) ||
                isKingAttackedBy(Knight.moveSet, Enum.KNIGHT, null) ||
                isKingAttackedBy(null, Enum.PAWN, null);
    }


    private boolean isKingAttackedBy(int[][] moveSet, Enum ID1, Enum ID2) {
        ArrayList<int[]> moves;
        if (moveSet != null)
            moves = FigureMovement.patternMove(this, moveSet, king.getX(), king.getY(), king.getPlayer(), ID1 == Enum.KNIGHT);
        else
            moves = FigureMovement.pawnEatMove(this, king.getX(), king.getY(), (king.getPlayer() == 0) ? -1 : 1);
        Figure figure;
        for (int[] move : moves) {
            if (notNull(move[0], move[1])) {
                figure = figureAt(move[0], move[1]);
                if (figure.getPlayer() != king.getPlayer() && (figure.ID == ID1 || figure.ID == ID2))
                    return true;
            }
        }
        return false;
    }


    public void moveFigure(int fX, int fY, int tX, int tY) {
        if (board[tX][tY] != null)
            ((king.getPlayer() == 0) ? blackFiguresInPlay : normalFiguresInPlay).remove(board[tX][tY]);
        board[tX][tY] = board[fX][fY];
        board[fX][fY] = null;
        board[tX][tY].kindlyInformTheFigureThatItHadBeenMovedAndToWhereItHadBeenMoved(tX, tY);
    }


    public void addFiguresToBoard() {
        for (int x = 0; x < 8; x++) {
            board[x][1] = new Pawn(1, x, 1);
            board[x][6] = new Pawn(0, x, 6);
        }

        board[0][0] = new Rook(1, 0, 0);
        board[1][0] = new Knight(1, 1, 0);
        board[2][0] = new Bishop(1, 2, 0);
        board[3][0] = new Queen(1, 3, 0);
        blackKing = new King(1, 4, 0);
        board[4][0] = blackKing;
        board[5][0] = new Bishop(1, 5, 0);
        board[6][0] = new Knight(1, 6, 0);
        board[7][0] = new Rook(1, 7, 0);

        board[0][7] = new Rook(0, 0, 7);
        board[1][7] = new Knight(0, 1, 7);
        board[2][7] = new Bishop(0, 2, 7);
        board[3][7] = new Queen(0, 3, 7);
        normalKing = new King(0, 4, 7);
        board[4][7] = normalKing;
        board[5][7] = new Bishop(0, 5, 7);
        board[6][7] = new Knight(0, 6, 7);
        board[7][7] = new Rook(0, 7, 7);

        king = normalKing;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (notNull(x, y))
                    if (board[x][y].getPlayer() == 0)
                        normalFiguresInPlay.add(board[x][y]);
                    else
                        blackFiguresInPlay.add(board[x][y]);
            }
        }

        callMoveGeneration();
        validateMoves();
    }


    public String getFen() {
        StringBuilder fen = new StringBuilder();
        for (int y = 0; y < 8; y++) {
            int c = 0;
            for (int x = 0; x < 8; x++) {
                if (notNull(x, y)) {
                    if (c != 0) {
                        fen.append(c);
                        c = 0;
                    }
                    int ascii = -32 + board[x][y].getPlayer() * 32;
                    switch (board[x][y].ID) {
                        case PAWN:
                            ascii += 'p';
                            break;
                        case BISHOP:
                            ascii += 'b';
                            break;
                        case ROOK:
                            ascii += 'r';
                            break;
                        case QUEEN:
                            ascii += 'q';
                            break;
                        case KING:
                            ascii += 'k';
                            break;
                        case KNIGHT:
                            ascii += 'n';
                            break;
                    }
                    fen.append((char)ascii);
                } else
                    c++;
            }
            if (c != 0)
                fen.append(c);
            fen.append("/");
        }
        fen.deleteCharAt(fen.length() - 1);
        fen.append(" ").append((king == normalKing) ? "w" : "b").append(" - ");
        return fen.toString();
    }


    public void changeKing() {
        king = (king == normalKing) ? blackKing : normalKing;
    }

    private King otherKing() {
        return (king == normalKing) ? blackKing : normalKing;
    }

    public boolean differentPlayer(int x, int y, Figure figure) {
        return board[x][y].getPlayer() == figure.getPlayer();
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public Figure figureAt(int x, int y) {
        return board[x][y];
    }

    public boolean notNull(int x, int y) {
        return board[x][y] != null;
    }
}