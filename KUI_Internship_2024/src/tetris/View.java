package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Graphics2D;

public class View {
	private Graphics2D graphics;
	static final int BOX_SIZE = 30;
	static final int ORIGIN = 50;

	private static final int OFFSET_X = 20;
	private static final int OFFSET_Y = 30;

	public void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;
	}

	public void draw(TetrisModel model) {
		if (graphics == null) return;

		// Draw the game board
		drawBoard(model);

		// Draw the current figure
		drawFigure(model);

	}

	private void drawBoard(TetrisModel model) {
		int[][] field = model.state.field;
		for (int row = 0; row < field.length; row++) {
			for (int col = 0; col < field[row].length; col++) {
				int colorIndex = field[row][col];
				if (colorIndex != 0) {
					graphics.setColor(Tetris.COLORS[colorIndex]);
				} else {
					graphics.setColor(Color.WHITE); // Background color for empty cells
				}
				graphics.fillRect(OFFSET_X + col * BOX_SIZE, OFFSET_Y + row * BOX_SIZE, BOX_SIZE, BOX_SIZE);
				graphics.setColor(Color.BLACK); // Draw grid lines
				graphics.drawRect(OFFSET_X + col * BOX_SIZE, OFFSET_Y + row * BOX_SIZE, BOX_SIZE, BOX_SIZE);
			}
		}
	}

	private void drawFigure(TetrisModel model) {
		int[][] figure = model.state.figure;
		Pair position = model.state.position;
		for (int row = 0; row < figure.length; row++) {
			for (int col = 0; col < figure[row].length; col++) {
				if (figure[row][col] != 0) {
					graphics.setColor(Tetris.COLORS[figure[row][col]]);
					graphics.fillRect(OFFSET_X + (position.x() + col) * BOX_SIZE, OFFSET_Y + (position.y() + row) * BOX_SIZE, BOX_SIZE, BOX_SIZE);
				}
			}
		}
	}
}
