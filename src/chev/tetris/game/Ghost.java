package chev.tetris.game;

import java.awt.Color;

public class Ghost extends Tetriminoes {

	public Ghost(Tetriminoes t) {
		super(t.getWidth(), t.getHeight());
		Block[] tetBlocks = t.getBlocks();
		
		for (int i = 0; i < 4; i++) {
			blocks[i] = new Block(tetBlocks[i].getX(), tetBlocks[i].getY(), Color.gray);
		}
		
		setPosition(t.getX(), t.getY());
	}

	public void resetPosition() {
		
	}
}
