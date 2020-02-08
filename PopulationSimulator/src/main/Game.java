package main;

public class Game implements Runnable {
	
	private WindowManager window;

	private Thread thread;
	private boolean running;
	
	private String title;
	private int width;
	private int height;
	
	public Game(String t, int w, int h) {
		title = t;
		width = w;
		height = h;
	}
	
	public static void main(String[] args) {
		
	}

	@Override
	public void run() {
		init();
		
		while(running) {
			update();
			render();
		}
		
		stop();
		
	}
	
	private void init() {
		window = new WindowManager(title, width, height);
		
	}
	
	private void update() {
		
	}
	
	private void render() {
		
	}
	
	public synchronized void start() {
		if(running) return;
		
		running = true;
		thread = new Thread(this);
		System.out.println("Game thread created");
		
		thread.start();
		
	}
	
	public synchronized void stop() {
		if(!running) return;
		
		try {
			thread.join();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
}
