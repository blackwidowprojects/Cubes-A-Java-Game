package com.game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class HealthPack extends GameObject{

	private Handler handler;
	private float alpha = 1;
	private float life = 0.005f;
	
	public HealthPack(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x,(int) y,16,16);
	}
	
	public void tick() {
		if(alpha > life) {
			alpha -= life - 0.001f;
		} else handler.removeObject(this);
		
		
	}

	
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int) x,(int) y,  16,  16);
		
	}

}
