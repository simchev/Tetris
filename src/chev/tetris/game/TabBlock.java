package chev.tetris.game;

import java.awt.Graphics2D;

public class TabBlock {
	
	// dimensions
	public static final int WIDTH = 400;
	public static final int HEIGHT = 800;
	
	private Block[][] blocks;
	private int width;
	private int height;
	
	public TabBlock(int width, int height) {
		this.width = width;
		this.height = height;
		blocks = new Block[width][height];
	}
	
	public void addBlocks(Tetriminoes t) {
		Block[] tetBlocks = t.getBlocks();
		int blockSize = Block.BLOCKSIZE;
	
		for (int i = 0; i < 4; i++) {
			blocks[(t.getX() + tetBlocks[i].getX() * blockSize) / blockSize][(t.getY() + tetBlocks[i].getY() * blockSize) / blockSize] = tetBlocks[i];
		}
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (blocks[i][j] != null)
					blocks[i][j].draw(g, i * Block.BLOCKSIZE, j * Block.BLOCKSIZE, Block.BLOCKSIZE);
			}
		}
	}
	
	public int clearLines() {
		int lineCleared = 0;
		int blocksOnLine = 0;
		Block b;
		
		// clear all lines to be cleared
		for (int i = HEIGHT / Block.BLOCKSIZE - 1; i >= 0; i--) {
			if (lineCleared == 4) {
				break;
			}
			
			for (int j = 0; j < WIDTH / Block.BLOCKSIZE; j++) {
				if (blocks[j][i] != null)
					blocksOnLine++;
			}
			
			if (blocksOnLine == WIDTH / Block.BLOCKSIZE) {
				for (int j = 0; j < WIDTH / Block.BLOCKSIZE; j++) {
					blocks[j][i] = null;
				}
				
				if (i != 0) {
					for (int j = 0; j < WIDTH / Block.BLOCKSIZE; j++) {
						for (int k = i - 1; k >= 0; k--) {
							b = blocks[j][k];
							blocks[j][k + 1] = blocks[j][k];
							blocks[j][k] = null;
						}
					}
				}
				
				i++;
				lineCleared++;
			}
			
			blocksOnLine = 0;
		}
		
		return lineCleared;
	}
	
	public Block[][] getTabBlocks() { return blocks; }
}
