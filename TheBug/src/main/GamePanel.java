package main;

import gameState.GameStateManager;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;




public class GamePanel extends Canvas 
	implements Runnable, KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the resolution the screen is actually rendered at
	 * without scaling stuff
	 */
	public static int WIDTH = 640;
	public static int HEIGHT = WIDTH / 16 * 9;
	
	// dimensions of the actual window
	//along with the scale that the game is upscaled
	public static int windowWidth = getScreenWidth();
	public static int SCALE = windowWidth / WIDTH;
	public static int windowHeight = SCALE * HEIGHT;
	
	
	// game thread
	private Thread thread;
	private boolean running;
	
	// image
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

	private Graphics2D g;
	
	// game state manager
	private GameStateManager gsm;


	
	public GamePanel() {
		super();
		setPreferredSize(
			new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	/**
	 * determines the width that the final int WIDTH will be set to
	 * it is necessary to use this so that no issues with scaling occur
	 * while still maintaining the maximum resolution.
	 * @return
	 */
	private static int getScreenWidth() {
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

		int scale = screenWidth / WIDTH;
		
		return scale * WIDTH;
	}

	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	private void init() {
		
		Dimension size = new Dimension(windowWidth, windowHeight);
		
		Toolkit t = Toolkit.getDefaultToolkit();
        int x = (int)((t.getScreenSize().getWidth() - windowWidth) / 2);
        int y = (int)((t.getScreenSize().getHeight() - windowHeight) / 2);

		
		setScale();
		setPreferredSize(size);
		this.setLocation(x, y);
		
		this.setSize(windowWidth, windowHeight);
		
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		gsm = new GameStateManager();
		
	}
	
	public void run() {
		
		init();
		
		long lastTime = System.nanoTime(); //the time the last loop ran
		long timer = System.currentTimeMillis(); //running timer
		final double ns = 1000000000.0 / 60.0; // 1/60th of second in nanoseconds
		double delta = 0; //used to keep track of the time in the loop
		int frames = 0; //number of frames in this cycle
		int updates = 0; //number of updates in this cycle

		
		// game loop
		while(running) {
			
			long now = System.nanoTime();//current time
			delta += (now - lastTime) / ns;//calc how long it's been since we updated the game
			
			
			lastTime = now;
			
			//delta is now greater than 1/60th of a second, better update the game
			while(delta >= 1) {
				update();//update game
				updates++;//increment update counter
				delta--;//decrement delta counter
			}
			render();
			
			frames++;//increment the counter for rendered frames
			
			//it's been 1 second, let's update the counters
			if(System.currentTimeMillis() - timer > 1000) {
				//running timer is incremented a second
				timer += 1000;
				Game.window.setTitle(Game.TITLE + "     |     " + updates + " UPS, " + frames + " fps");
				
				//reset counters
				updates = 0;
				frames = 0;
				
			}
			
		}
		
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		clearPixels();
		draw();
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.dispose();
		bs.show();
		
	}

	private void clearPixels() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
		
	}

	private void update() {
		gsm.update();
	}
	
	private void draw() {
		gsm.draw(g);
	}
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
	
	/**
	 * Determines the amount that the rendering is being scaled up
	 * makes correction to the 
	 */
	private void setScale() {		
		System.out.println("width: " + windowWidth);
		System.out.println("height: " + windowHeight);
		System.out.println("scale: " + SCALE);
		System.out.println("renderWidth: " + WIDTH);
		System.out.println("renderHeight: " + HEIGHT);
	}

}
















