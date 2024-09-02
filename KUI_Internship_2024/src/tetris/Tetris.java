package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Tetris {

	static final Color[] COLORS = { Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.CYAN, Color.MAGENTA,
			Color.ORANGE, Color.YELLOW };

	private static ScheduledExecutorService service;
	private static ScheduledFuture<?> slideDownTask;
	private static int currentSpeed;

	public static void main(String[] args) {

		JFrame frame = new JFrame("Tetris");

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 700));

		frame.add(panel);

		frame.pack();

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.setVisible(true);

		Graphics2D graphics = (Graphics2D) panel.getGraphics();

		TetrisModel model = new TetrisModel(TetrisModel.DEFAULT_WIDTH, TetrisModel.DEFAULT_HEIGHT,
				TetrisModel.DEFAULT_COLORS_NUMBER);

		View view = new View(new Graphics() {

			@Override
			public void drawBoxAt(int i, int j, int value) {
				graphics.setColor(COLORS[value]);
				graphics.fillRect(i, j, View.BOX_SIZE, View.BOX_SIZE);
			}

		});

		Controller controller = new Controller(model, view);

		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT: {
						controller.moveLeft();
						controller.fullRowRemoval();
						break;
					}
					case KeyEvent.VK_RIGHT: {
						controller.moveRight();
						controller.fullRowRemoval();
						break;
					}
					case KeyEvent.VK_UP: {
						controller.rotate();
						controller.fullRowRemoval();
						break;
					}
					case KeyEvent.VK_DOWN: {
						controller.drop();
						controller.fullRowRemoval();
						break;
					}
				}
			}
		});

		service = Executors.newSingleThreadScheduledExecutor();
		currentSpeed = TetrisModel.SPEED;
		scheduleSlideDown(controller, currentSpeed);

	}

	private static void scheduleSlideDown(Controller controller, int speed) {
		if (slideDownTask != null && !slideDownTask.isCancelled()) {
			slideDownTask.cancel(false);
		}
		slideDownTask = service.scheduleAtFixedRate(controller::slideDown, 0, speed, TimeUnit.MILLISECONDS);
	}

	public static void adjustSpeed(Controller controller, int newSpeed) {
		if (newSpeed != currentSpeed) {
			currentSpeed = newSpeed;
			scheduleSlideDown(controller, newSpeed);
		}
	}
}
