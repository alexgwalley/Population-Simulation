package main;

public class Game implements Runnable {
	
	private WindowManager window;

	private Thread thread;
	
	public static void main(String[] args) {
		
	}

	@Override
	public void run() {
		init();
		
		while(true) {
			update();
			render();
		}
		
	}
	
	private void init() {
		window = new WindowManager("Population Simulator", 600, 400);
		
		
		
	}
	
	private void update() {
		
	}
	
	private void render() {
		
	}
	
}
