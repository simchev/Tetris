package chev.tetris.game;

import java.awt.Color;

public class SQ extends Tetriminoes{

	private Color color;
	private static int width = 2;
	private static int height = 2;

	public SQ() {
		super(width, height);
		color = new Color(255, 240, 0);
		blocks[0] = new Block(0, 0, color);
		blocks[1] = new Block(0, 1, color);
		blocks[2] = new Block(1, 0, color);
		blocks[3] = new Block(1, 1, color);
	}
	
	public void resetPosition() {
		blocks[0].setRelativePosition(0, 0);
		blocks[1].setRelativePosition(0, 1);
		blocks[2].setRelativePosition(1, 0);
		blocks[3].setRelativePosition(1, 1);
	}
}
