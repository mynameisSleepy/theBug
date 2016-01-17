package entity;

import tileMap.TileMap;

public class Enemy extends MapObject {
	//TODO implement walker (reskin is all that I should need)
	//TODO Climber (edit AI to just go up any wall in its positioning)
	//TODO Implement Fighter (will fight like the hero (dark link/samus essentially, weeker though)
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	
	protected boolean flinching;
	protected long flinchTimer;
	
	public Enemy(TileMap tm) {
		super(tm);
	}
	
	public boolean isDead() { return dead; }
	
	public int getDamage() { return damage; }
	
	public void hit(int damage) {
		if(dead || flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	/*
	 * TODO implement update for different movement types
	 */
	public void update() {}
	
}














