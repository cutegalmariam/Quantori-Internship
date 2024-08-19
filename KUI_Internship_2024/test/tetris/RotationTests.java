package tetris;

import static org.junit.Assert.*;

import org.junit.Test;

public class RotationTests {

	@Test
	public void test() {
		var model = new TetrisModel(TetrisModel.DEFAULT_WIDTH, TetrisModel.DEFAULT_HEIGHT, TetrisModel.DEFAULT_COLORS_NUMBER);
		model.state.figure = FigureFactory.J();
		model.rotate();
		var rotatedJ = FigureFactory.J();
		for (int i = 0; i < rotatedJ.length; i++) {
			assertArrayEquals(model.state.figure[i], rotatedJ[i ]);
		}
	}

}
