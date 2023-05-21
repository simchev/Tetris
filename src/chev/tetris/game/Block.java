package chev.tetris.game;

import java.awt.Color;
import java.awt.Graphics2D;

import chev.tetris.states.PlayState;

public class Block {

	// Universal size
	public static final int BLOCKSIZE = 40;
	public static final int WAITSIZE = 25;
	
	// Relative position
	private int x;
	private int y;
	
	// Color
	private Color color;
	
	public Block(int x, int y, Color c) {
		this.x = x;
		this.y = y;
		color = c;
	}
	
	public void setRelativePosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public void draw(Graphics2D g, int x, int y, int size) {
		if (y >= 0) {
			x += PlayState.GAMEX;
			y += PlayState.GAMEY;
			
			g.setColor(color);
			g.fillRect(x, y, size, size);
			
			g.setColor(color.brighter());
			g.fillRect(x, y, size, 2);
			g.fillRect(x, y, 2, size);
			
			g.setColor(color.darker());
			g.fillRect(x + size - 2, y, 2, size);
			g.fillRect(x, y + size - 2, size, 2);
		}
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public Color getColor() { return color; }
}
