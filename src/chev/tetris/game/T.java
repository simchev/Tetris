package chev.tetris.game;

import java.awt.Color;

public class T extends Tetriminoes {
	
	private Color color;
	private static int width = 3;
	private static int height = 2;

	public T() {
		super(width, height);
		color = new Color(170, 35, 255);
		blocks[0] = new Block(0, 1, color);
		blocks[1] = new Block(1, 0, color);
		blocks[2] = new Block(1, 1, color);
		blocks[3] = new Block(2, 1, color);
	}
	
	public void resetPosition() {
		blocks[0].setRelativePosition(0, 1);
		blocks[1].setRelativePosition(1, 0);
		blocks[2].setRelativePosition(1, 1);
		blocks[3].setRelativePosition(2, 1);
	}
}
