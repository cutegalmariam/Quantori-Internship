package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Tetris {

	static final Color[] COLORS = { Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.CYAN, Color.MAGENTA,
			Color.ORANGE, Color.YELLOW };

	private static ScheduledExecutorService service;
	private static ScheduledFuture<?> slideDownTask;
	private static int currentSpeed;

	public static void main(String[] args) {

		JFrame frame = new JFrame("Tetris");
		TetrisModel model = new TetrisModel(TetrisModel.DEFAULT_WIDTH, TetrisModel.DEFAULT_HEIGHT,
				TetrisModel.DEFAULT_COLORS_NUMBER);

		TetrisPanel gamePanel = new TetrisPanel(model);

		JPanel sidePanel = new JPanel(new GridBagLayout());
		sidePanel.setPreferredSize(new Dimension(150, 700)); // Increase width if needed

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 10, 40); // Add padding around components
		gbc.anchor = GridBagConstraints.WEST;

		JLabel scoreLabel = new JLabel("Score: " + TetrisModel.SCORE);
		JLabel levelLabel = new JLabel("Level: " + TetrisModel.LEVEL);
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
		levelLabel.setFont(new Font("Arial", Font.BOLD, 20));

		sidePanel.add(scoreLabel, gbc);

		gbc.gridy = 1; // Move to next row
		sidePanel.add(levelLabel, gbc);

		frame.setLayout(new BorderLayout());
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.add(sidePanel, BorderLayout.EAST);

		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Example of updating score and level
		model.addListener(updatedModel -> {
			scoreLabel.setText("Score: " + TetrisModel.SCORE);
			levelLabel.setText("Level: " + TetrisModel.LEVEL);
			gamePanel.repaint();  // Trigger a repaint to update the game panel
		});

		View view = new View();
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
