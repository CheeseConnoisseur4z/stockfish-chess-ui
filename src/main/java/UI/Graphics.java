package UI;

import Core.Board;
import Figure.Figure;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Graphics
{
    public static JFrame frame = makeFrame();
    public static JPanel[][] tileDisplay = createTileDisplay();
    public static JPanel[][] moveOverlay = createMoveOverlay();
    public static HashMap<Figure, JLabel> figureJLabelHashMap = new HashMap<>();


    static JFrame makeFrame() {
        JFrame frame = new JFrame("Chess");
        frame.setSize(0, 0);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(0xFFF4D8));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        return frame;
    }


    public static JPanel[][] createTileDisplay() {
        JPanel[][] tileDisplay = new JPanel[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                tileDisplay[x][y] = new JPanel();
                tileDisplay[x][y].setBounds(x * 50, y * 50, 51, 51);
                if ((x + y) % 2 != 0) {
                    tileDisplay[x][y].setBackground(new Color(0xFFC88E));
                } else {
                    tileDisplay[x][y].setBackground(new Color(0x795934));
                }
            }
        }
        return tileDisplay;
    }


    public static void tileDisplayToFrame(boolean add) {
        for (JPanel[] row : tileDisplay) {
            for (JPanel tile : row) {
                if (add) {
                    frame.add(tile);
                } else {
                    frame.remove(tile);
                }
            }
        }
    }


    public static void createFigureDisplay(Figure figure, FigureControls figureControls) {
        JLabel display = new JLabel();
        display.setBounds(figure.getX() * 50, figure.getY() * 50, 50, 50);
        String labelText = "unidentified figure";
        switch (figure.ID) {
            case KING:
                labelText = (figure.getPlayer() == 1) ? "♚" : "♔";
                break;
            case QUEEN:
                labelText = (figure.getPlayer() == 1) ? "♛" : "♕";
                break;
            case ROOK:
                labelText = (figure.getPlayer() == 1) ? "♜" : "♖";
                break;
            case KNIGHT:
                labelText = (figure.getPlayer() == 1) ? "♞" : "♘";
                break;
            case BISHOP:
                labelText = (figure.getPlayer() == 1) ? "♝" : "♗";
                break;
            case PAWN:
                labelText = (figure.getPlayer() == 1) ? "♟" : "♙";
                break;
        }
        display.setText(labelText);
        display.setFont(new Font("figure icon font", Font.PLAIN, 50));
        if (figure.getPlayer() == 0) {
            display.setForeground(Color.WHITE);
        }
        figureControls.onDragMoveStart(figure, display);
        figureControls.dragMove(figure, display);
        figureControls.onDragMoveEnd(figure, display);
        frame.add(display);
        figureJLabelHashMap.put(figure, display);
    }


    public static JPanel[][] createMoveOverlay() {
        JPanel[][] moveOverlay = new JPanel[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                moveOverlay[x][y] = new JPanel();
                moveOverlay[x][y].setBounds(x * 50, y * 50, 51, 51);
                moveOverlay[x][y].setBackground(new Color(0xB514FF00, true));
                moveOverlay[x][y].setVisible(false);
            }
        }
        return moveOverlay;
    }


    public static void addMoveOverlayToFrame(boolean add) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (add) {
                    frame.add(moveOverlay[x][y]);
                } else {
                    frame.remove(moveOverlay[x][y]);
                }
            }
        }
    }

    public static void lightMoveOverlay(Figure figure, Board board, boolean rightPlayer) {
        if (!rightPlayer)
            return;
        for (int[] move : figure.getMoves()) {
            moveOverlay[move[0]][move[1]].setVisible(true);
            if (board.notNull(move[0], move[1]) && board.differentPlayer(move[0], move[1], figure))
                moveOverlay[move[0]][move[1]].setBackground(new Color(0xB5FF0000, true));
            else
                moveOverlay[move[0]][move[1]].setBackground(new Color(0xB514FF00, true));
        }
        moveOverlay[figure.getX()][figure.getY()].setVisible(true);
        moveOverlay[figure.getX()][figure.getY()].setBackground(new Color(0xB5004CFF, true));
    }


    /*
    for promotion;
    public static void replaceFigureGraphics(Figure toReplace, Figure replaceWith, Board gameBoard) {
        frame.remove(toReplace.display);
        addMoveOverlayToFrame(gameBoard.moveOverlay, false);
        addAllTileDisplay(gameBoard.board, false);
        frame.add(replaceWith.display);
        addMoveOverlayToFrame(gameBoard.moveOverlay, true);
        addAllTileDisplay(gameBoard.board, true);
    }
     */
}