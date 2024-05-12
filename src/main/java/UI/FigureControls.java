package UI;

import Core.Board;
import Core.Game;
import Figure.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FigureControls
{
    private final Board board;
    private final Game game;

    private int mouseX;
    private int mouseY;


    public FigureControls(Board board, Game game) {
        this.board = board;
        this.game = game;
    }


    public void onDragMoveStart(Figure figure, JLabel display) {
        display.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Graphics.lightMoveOverlay(figure, board, figure.getPlayer() == game.getPlayer());
            }
        });
    }


    public void dragMove(Figure figure, JLabel display) {
        display.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (figure.getPlayer() == game.getPlayer()) {
                    mouseX = (int)(MouseInfo.getPointerInfo().getLocation().getX() - Graphics.frame.getLocation().getX());
                    mouseY = (int)(MouseInfo.getPointerInfo().getLocation().getY() - Graphics.frame.getLocation().getY());
                    display.setBounds(mouseX - 25, mouseY - 50, display.getWidth(), display.getHeight());
                }
            }
        });
    }


    public void onDragMoveEnd(Figure figure, JLabel display) {
        display.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (figure.getPlayer() == game.getPlayer()) {
                    for (int i = 0; i < 8; i++) {
                        for (int i2 = 0; i2 < 8; i2++) {
                            Graphics.moveOverlay[i][i2].setVisible(false);
                            Graphics.moveOverlay[i][i2].setBackground(new Color(0xB514FF00, true));
                        }
                    }
                    mouseX /= 50;
                    mouseY = (mouseY - 25) / 50;
                    if (figure.getPlayer() == game.getPlayer() && figure.canMoveTo(mouseX, mouseY)) {
                        game.makeMove(figure.getX(), figure.getY(), mouseX, mouseY);
                    } else
                        display.setBounds(figure.getX() * 50, figure.getY() * 50, display.getWidth(), display.getHeight());
                }
            }
        });
    }
}
