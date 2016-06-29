package com.game.main;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.main.Game.STATE;

public class MenuParticle extends GameObject{

	private Game game;
	private Handler handler;
	private Random r = new Random();

	private int red = r.nextInt(255);
	private int green = r.nextInt(255);
	private int blue = r.nextInt(255);
	private Color color;
	
	public MenuParticle(int x, int y, ID id, Handler handler, Game game) {
		super(x, y, id);
		this.game = game;
		this.handler = handler;
		
		velX = r.nextInt(5) + 1;
		velY = r.nextInt(7) + 4;
		
		color = new Color(red, green, blue);
		
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x,(int) y,16,16);
	}
	
	public void tick() {
		x += velX;
		y += velY;

		if( x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
		if( y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;

		
		if(game.gameState == STATE.Menu || game.gameState == STATE.Help) {
			if(x >= (380 - 16) && x <= (400 + 220)) {
				if(y >= (180 - 16) && y <= (200 + 270)) {
					
					if(y < 200 || y > 450) velY *= -1;
					if(x < 400 || x > 600) velX *= -1;
					
				}
			} 
		}
		
		handler.addObject(new Trail((int) x,(int) y, ID.Trail, color, 16, 16, 0.02f, handler));
	}

	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect((int) x,(int) y,  16,  16);
		
	}

}