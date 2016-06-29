package com.game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BigEnemy extends GameObject{

	private Handler handler;
	private GameObject player;
	
	public BigEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		for(int i = 0; i < handler.object.size(); i++) {
			if(handler.object.get(i).getID() == ID.Player) player = handler.object.get(i);
		}
		
		velX = 2;
		velY = 2;
		
		damage = 5;
		
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x,(int) y, 48, 48);
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		float diffX = x - player.getX() - 16;
		float diffY = y - player.getY() - 16;
		float distance = (float) Math.sqrt( (x-player.getX()) * (x-player.getX()) + (y-player.getY()) * (y-player.getY()) );

		velX = ((-1/distance) * diffX);
		velY = ((-1/distance) * diffY);
		
		// if( x <= 0 || x >= Game.WIDTH - 48) velX *= -1;
		// if( y <= 0 || y >= Game.HEIGHT - 64) velY *= -1;
		
		handler.addObject(new Trail((int) x,(int) y, ID.Trail, Color.orange, 48, 48, 0.05f, handler));
	}

	
	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect((int) x,(int) y, 48, 48);
		
	}

}