package main;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class Game {
	
	static JFrame window;
	public static final String TITLE = "The Bug";
	
	public static void main(String[] args) {
		
		window = new JFrame(TITLE);
		window.setLocationRelativeTo(null);
		window.add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
	}
	
}
