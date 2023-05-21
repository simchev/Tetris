package chev.tetris.main;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import chev.tetris.states.StateManager;
import chev.tetris.util.StopWatch;

@SuppressWarnings("serial")
public class Game implements Runnable {
	
	// Game informations
	public static final String GAMENAME = "Tetris";
	
	// Game frame
	private GameFrame gameFrame;
	
	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private int time = 1000;
	private int targetTime = time / FPS;
	private StopWatch stopwatch;
	
	public Game() {
		gameFrame = new GameFrame();
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
		
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}
	
	public void run() {
		init();
		
		int wait;
		
		while (running) {
			stopwatch.start();
			gameFrame.update();
			wait = targetTime - stopwatch.getElapsedMilli();
			
			if (wait > 0) {
				try {
					Thread.sleep(wait);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void init() {
		stopwatch = new StopWatch();
		running = true;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
	}
}
