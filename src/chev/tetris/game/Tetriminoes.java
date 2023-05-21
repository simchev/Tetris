package chev.tetris.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.BreakIterator;

import chev.tetris.main.GamePanel;
import chev.tetris.states.PlayState;

public abstract class Tetriminoes {
	
	protected Block[] blocks;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean done;
	private int blockSize;
	
	public Tetriminoes(int width, int height) {
		this.width = width;
		this.height = height;
		this.blockSize = Block.BLOCKSIZE;
		blocks = new Block[4];
		done = false;
		startPosition();
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void startPosition() {
		this.x = TabBlock.WIDTH / 2 - ((width == 2) ? 1 : 2) * blockSize;
		this.y = (width == 4) ? -blockSize : 0;
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < blocks.length; i++) {
			blocks[i].draw(g, x + blocks[i].getX() * blockSize, y + blocks[i].getY() * blockSize, blockSize);
		}
	}
	
	public void quickDrop(Block[][] mapBlocks) {
		for (int i = y / blockSize; i < mapBlocks[0].length; i++) {
			if (done)
				break;
			
			drop(mapBlocks);
		}
	}
	
	public void drop(Block[][] mapBlocks) {
		lookBellow(mapBlocks);
		if (!done)
			y += blockSize;
	}
	
	public boolean checkLoose(Block[][] mapBlocks) {
		boolean lost = false;
		for (int i = 4 - ((width == 2) ? 0 : 1); i < 6 + ((width == 4) ? 1 : 0); i++) {
			for (int j = 0; j < height; j++) {
				if (mapBlocks[i][j] != null) {
					lost = true;
					break;
				}
			}
			if (lost)
				break;
		}
		return lost;
	}
	
	public void lookBellow(Block[][] mapBlocks) {
		boolean somethingBellow = false;
		
		for (int j = 0; j < blocks.length; j++) {
			if (y + blocks[j].getY() * blockSize == TabBlock.HEIGHT - blockSize || 
					mapBlocks[(x + blocks[j].getX() * blockSize) / blockSize][(y + blocks[j].getY() * blockSize) / blockSize + 1] != null) {
				somethingBellow = true;
				break;
			}
		}
		
		if (somethingBellow) {
			done = true;
		}
		else {
			done = false;
		}
	}
	
	public void moveLeft(Block[][] mapBlocks) {
		boolean somethingLeft = false;
		
		for (int j = 0; j < blocks.length; j++) {
			if (x + blocks[j].getX() * blockSize == 0 ||
					mapBlocks[(x + blocks[j].getX() * blockSize) / blockSize - 1][(y + blocks[j].getY() * blockSize) / blockSize] != null) {
				somethingLeft = true;
				break;
			}
		}
		
		if (!somethingLeft) {
			x -= blockSize;
		}
		
		lookBellow(mapBlocks);
	}
	
	public void moveRight(Block[][] mapBlocks) {
		boolean somethingRight = false;
		
		for (int j = 0; j < blocks.length; j++) {
			if (x + blocks[j].getX() * blockSize == TabBlock.WIDTH - blockSize ||
					mapBlocks[(x + blocks[j].getX() * blockSize) / blockSize + 1][(y + blocks[j].getY() * blockSize) / blockSize] != null) {
				somethingRight = true;
				break;
			}
		}
		
		if (!somethingRight) {
			x += blockSize;
		}
		
		lookBellow(mapBlocks);
	}
	
	public void rotateLeft(Block[][] mapBlocks) {
		for (int i = 0; i < 4; i++) {
			int y = width - 1 - blocks[i].getX();
			int x = blocks[i].getY();
			blocks[i].setRelativePosition(x, y);
		}
		
		fixRotation(mapBlocks);
		lookBellow(mapBlocks);
	}
	
	public void rotateRight(Block[][] mapBlocks) {
		for (int i = 0; i < 4; i++) {
			int x = width - 1 - blocks[i].getY();
			int y = blocks[i].getX();
			blocks[i].setRelativePosition(x, y);
		}
		
		fixRotation(mapBlocks);
		lookBellow(mapBlocks);
	}
	
	private void fixRotation(Block[][] mapBlocks) {
		int positionx;
		int positiony;
		int compteur = 0;
		boolean good = false;
		
		for (int i = 0; i < 4; i++) {
			positionx = x + blocks[i].getX() * blockSize;
			positiony = y + blocks[i].getY() * blockSize;
			
			if (positionx < 0) {
				x += blockSize;
			}
			else if (positionx >= TabBlock.WIDTH) {
				x -= blockSize;
			}
			
			if (positiony < 0) {
				y += blockSize;
			}
			else if (positiony >= TabBlock.HEIGHT) {
				y -= blockSize;
			}
		}
		
		while (!good) {
			for (int i = 0; i < 4; i++) {
				positionx = x + blocks[i].getX() * blockSize;
				positiony = y + blocks[i].getY() * blockSize;
				
				if (y < 0) {
					good = true;
					break;
				}
				
				if (mapBlocks[positionx / blockSize][positiony / blockSize] != null) {
					y -= blockSize;
					compteur++;
					break;
				}
			}
			
			if (compteur == 0) {
				good = true;
			}
			
			compteur = 0;
		}
	}
	
	public abstract void resetPosition();

	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getBlockSize() { return blockSize; }
	public Block[] getBlocks() { return blocks; }
	public boolean isDone() { return done; }
	
	public void setBlockSize(int size) { blockSize = size; }
}
