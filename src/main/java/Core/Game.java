package Core;

import UI.FigureControls;
import UI.Graphics;


public class Game
{
    private final Stockfish stockfish = new Stockfish();
    private final Board board = new Board();
    private int player = 0;
    private int fishThinkingTime = 5;


    public void start() {
        board.addFiguresToBoard();
        readyGraphics();
        stockfish.startProcess();
    }


    public void makeMove(int fX, int fY, int tX, int tY) {
        System.out.println(fX + ", " + fY + " -> " + tX + ", " + tY);
        if (board.notNull(tX, tY))
            Graphics.frame.remove(Graphics.figureJLabelHashMap.get(board.figureAt(tX, tY)));
        board.moveFigure(fX, fY, tX, tY);
        Graphics.figureJLabelHashMap.get(board.figureAt(tX, tY)).setBounds(tX * 50, tY * 50, 50, 50);
        turnChange();
    }


    private void turnChange() {
        player = (player == 0) ? 1 : 0;
        board.changeKing();
        board.callMoveGeneration();
        board.validateMoves();
        if (!board.doFiguresHaveTheAbilityToTraverseTheBoard())
            matchOver(board.checkCheck());
        if (player == 1)
            theFishMakesAMove();
    }


    private void theFishMakesAMove() {
        String fen = board.getFen();
        String fishSays = stockfish.getBestMove(fen, fishThinkingTime);
        makeMove(fishSays.charAt(0) - 97, 8  - (fishSays.charAt(1) - 48), fishSays.charAt(2) - 97, 8 - (fishSays.charAt(3) - 48));
    }


    private void matchOver(boolean check) {
        if (check)
            System.out.println("player" + ((player == 0) ? 1 : 0) + " won");
        else
            System.out.println("stalemate");
        stockfish.suicide();
        while (true) {}
    }


    private void readyGraphics() {
        Graphics.frame.setSize(414, 436);
        FigureControls figureControls = new FigureControls(board, this);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board.notNull(x, y))
                    Graphics.createFigureDisplay(board.figureAt(x, y), figureControls);
            }
        }
        Graphics.addMoveOverlayToFrame(true);
        Graphics.tileDisplayToFrame(true);
        Graphics.frame.setSize(413, 435);
    }


    public int getPlayer() {
        return this.player;
    }
}