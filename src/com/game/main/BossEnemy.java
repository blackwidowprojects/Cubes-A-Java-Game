package com.game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class BossEnemy extends GameObject{

	private Random r = new Random();
	private Handler handler;
	
	private int timer = 100;
	private int timer2 = 50;
	
	public BossEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		velX = 0;
		velY = 2;

		damage = 10;
		
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x,(int) y, 64, 64);
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		if(timer <= 0)	velY = 0;
		else timer --;
		
		if(timer <= 0) timer2 --;
		
		if(timer2 <= 0) {
			if(velX == 0) velX = 2;
			
			if(velX > 0) velX += 0.01f;
			else if(velX < 0) velX -= 0.01f;
			
			velX = Game.clamp(velX, -10, 10);
			
			int spawn = r.nextInt(10);
			if(spawn == 0) handler.addObject(new BossEnemyBullet((int) x + 32,(int) y + 32, ID.BasicEnemy, handler));	
		}

		if( x <= 0 || x >= Game.WIDTH - 64) velX *= -1;
		// if( y <= 0 || y >= Game.HEIGHT - 80) velY *= -1;
		
		// handler.addObject(new Trail((int) x,(int) y, ID.Trail, Color.magenta, 64, 64, 0, handler));
	}

	
	public void render(Graphics g) {
		g.setColor(Color.magenta);
		g.fillRect((int) x,(int) y, 64, 64);
		
	}

}

