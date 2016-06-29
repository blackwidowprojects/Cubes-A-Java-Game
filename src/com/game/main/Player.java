package com.game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Player extends GameObject {

	Random r = new Random();
	Handler handler;
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;

	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x,(int) y,32,32);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		x = Game.clamp(x, 0, Game.WIDTH - 32);
		y = Game.clamp(y, 0,  Game.HEIGHT - 54);
		
		collision();
	}
	
	private void collision() {
		for (int i = 0; i < handler.object.size(); i++){
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.BasicEnemy || tempObject.getID() == ID.FastEnemy || tempObject.getID() == ID.BigEnemy || tempObject.getID() == ID.BossEnemy) {
				if(getBounds().intersects(tempObject.getBounds())) {
					
					// Damage Code
					HUD.HEALTH -= tempObject.damage;
				}
			}
			else if(tempObject.getID() == ID.HealthPack) {
				if(getBounds().intersects(tempObject.getBounds())) {
					
					// Healing Code
					HUD.HEALTH += 20;
					handler.removeObject(tempObject);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int) x, (int) y, 32, 32);
		
	}

	
	
}
