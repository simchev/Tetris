package chev.tetris.game;

import java.awt.Color;

public class I extends Tetriminoes {
	
	private Color color;
	private static int width = 4;
	private static int height = 1;
	
	public I() {
		super(width, height);
		color = new Color(20, 200, 200);
		blocks[0] = new Block(0, 1, color);
		blocks[1] = new Block(1, 1, color);
		blocks[2] = new Block(2, 1, color);
		blocks[3] = new Block(3, 1, color);
	}
	
	public void resetPosition() {
		blocks[0].setRelativePosition(0, 1);
		blocks[1].setRelativePosition(1, 1);
		blocks[2].setRelativePosition(2, 1);
		blocks[3].setRelativePosition(3, 1);
	}
}
