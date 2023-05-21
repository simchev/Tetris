package chev.tetris.util;

public class StopWatch {
	
	private long time;
	private long elapsed;
	private boolean running;
	
	public StopWatch() {
		running = false;
	}
	
	public void start() {
		time = System.nanoTime();
		running = true;
	}
	
	public void restart() {
		start();
	}
	
	public void suspend() {
		if (running) {
			elapsed = getElapsedNano();
			running = false;
		}
	}
	
	public void resume() {
		if (!running) {
			start();
			time -= elapsed;
			running = true;
		}
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public long getElapsedNano() {
		return running ? (System.nanoTime() - time) : elapsed;
	}
	
	public int getElapsedMilli() {
		return (int)(getElapsedNano() / 1_000_000);
	}
	
	public int getElapsedSeconds() {
		return (getElapsedMilli() / 1000);
	}
	
	public int getElapsedMinutes() {
		return (getElapsedSeconds() / 60);
	}

}
