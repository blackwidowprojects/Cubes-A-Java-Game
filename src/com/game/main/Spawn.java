package com.game.main;

import java.util.Random;

public class Spawn {

	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	
	private int scoreKeep = 0;
	private int nextLevel = 2;
	
	public Spawn(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
	
	}
	
	public void tick() {
		scoreKeep ++;
		
		int spawnHealth = r.nextInt(200);
		if(spawnHealth == 0) handler.addObject(new HealthPack(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.HealthPack, handler));	
		
		if(scoreKeep >= 250) {
			scoreKeep = 0;
			hud.setLevel((int) hud.getLevel() + 1);
			

			if(hud.getLevel() == nextLevel) {
				
				if(hud.getLevel() == 10) {
					handler.clearEnemies();
					handler.addObject(new BossEnemy((Game.WIDTH/2), -120, ID.BossEnemy, handler));
				}
				
				else if(hud.getLevel() % 5 == 0) handler.addObject(new BigEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BigEnemy, handler));						
				
				else if(hud.getLevel() % 3 == 0) handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.FastEnemy, handler));
					
				else if(hud.getLevel() % 2 == 0) handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
			}
		
			nextLevel ++;
		} 
	}

	public void setScoreKeep(int scoreKeep) {
		this.scoreKeep = scoreKeep;
	}
	
	public void setNextLevel(int nextLevel) {
		this.nextLevel = nextLevel;
	}
}
