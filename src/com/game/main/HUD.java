package com.game.main;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {

	public static float HEALTH = 100;	
	private float green = 255;
	private float red = 0;
	
	private int score = 0;
	private int level = 1;
	
	public void tick() {
		HEALTH = Game.clamp((int) HEALTH,  0,  100);
		green = Game.clamp((int) green, 0, 255);
		red  = Game.clamp((int) red, 0, 255); 
		
		green = (HEALTH * 2) + 55;
		red = ((100 - HEALTH) * 2) + 55;
		
		score ++;
	}
	
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 32);
		g.setColor(new Color((int) red, (int) green, 0));
		g.fillRect(15, 15, (int) HEALTH * 2, 32);
		g.setColor(Color.white);
		g.drawRect(15, 15, 200, 32);
	
		g.drawString("Score: " + score, 15, 64);
		g.drawString("Level: " + level, 15, 80);
		
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	public float getScore() {
		return score;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public float getLevel() {
		return level;
	}
}
