package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TetrisPanel extends JPanel {
    private TetrisModel model;
    private View view;

    public TetrisPanel(TetrisModel model) {
        this.model = model;
        this.view = new View();
        setPreferredSize(new Dimension(500, 700));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        view.setGraphics(graphics);
        view.draw(model);
    }
}

