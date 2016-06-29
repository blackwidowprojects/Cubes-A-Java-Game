package com.game.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.game.main.Game.STATE;

public class Menu extends MouseAdapter {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Spawn spawn;
	private Random r = new Random();
	
	public Menu(Game game, Handler handler, HUD hud, Spawn spawn) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawn = spawn;
		
	}
	
	public void render(Graphics g) {
		
		// Menu Screen
		if(game.gameState == STATE.Menu) {
			Font font = new Font("arial", 1, 80);
			Font font2 = new Font("arial", 1, 40);
			
			g.setColor(Color.white);
			
			g.drawRect(390, 190, 220, 270);
			
			g.setFont(font);
			g.drawString("Cubes!", (Game.WIDTH/2) - 140, 100);
			

			g.setFont(font2);
			g.drawRect(400, 200, 200, 50);
			g.drawString("Play", 458, 239);
			
			g.drawRect(400, 300, 200, 50);
			g.drawString("Help", 458, 339);
			
			g.drawRect(400, 400, 200, 50);
			g.drawString("Quit", 458, 439);
			
		}
		// Help Screen
		else if (game.gameState == STATE.Help) {

			Font font = new Font("arial", 1, 80);
			Font font2 = new Font("arial", 1, 40);
			Font font3 =  new Font("arial", 1, 14);
		
			g.setColor(Color.white);
			
			g.drawRect(390, 190, 220, 270);
			
			g.setFont(font);
			g.drawString("Help", (Game.WIDTH/2) - 100, 100);
			
			g.setFont(font2);
			g.drawRect(400, 200, 200, 50);
			g.drawString("Back", 458, 239);
			
			g.setFont(font3);
			g.drawString("Use the ARROW keys (UP, DOWN, LEFT, RIGHT) to evade enemies!", 250, 150);
			g.drawString("Touching an enemy will cause you to take damage! Pick up green health packs to heal!", 200, 170);
		}
		// Game Over
		else if (game.gameState == STATE.End) {

			Font font = new Font("arial", 1, 80);
			Font font2 = new Font("arial", 1, 40);
		
			g.setColor(Color.white);
			
			g.setFont(font);
			g.drawString("Game OVER!", (Game.WIDTH/2) - 250, 100);

			g.setFont(font2);
			g.drawString("You lost...", 420, 170);
			g.drawString("Score: " + hud.getScore(), 380, 390);
			

			g.drawRect(400, 200, 200, 50);
			g.drawString("Try Again", 410, 239);
			
		}
	}
	
	public void tick() {
		
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		// Menu Controls
		if(game.gameState == STATE.Menu) { 
			
			// Play Button
			if(mouseOver(mx, my, 400, 200, 200, 50)) {

				AudioPlayer.getSound("menu_sound").play(1.0f, 0.5f);
				handler.clearEnemies();
				
				game.gameState = STATE.Game;
				
				handler.addObject(new Player((Game.WIDTH/2) - 32 , (Game.HEIGHT/2) - 32, ID.Player, handler));
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
			}
			
			// Help Button
				
			if(mouseOver(mx, my, 400, 300, 200, 50)) {
				AudioPlayer.getSound("menu_sound").play(1.0f, 0.5f);
				game.gameState = STATE.Help;

			}
			
			// Quit Button
			if(mouseOver(mx, my, 400, 400, 200, 50)) {
				System.exit(1);;
				}			
			
		}
		
		// Help Controls
		else if(game.gameState == STATE.Help) {
			
			// Back Button
				
			if(mouseOver(mx, my, 400, 200, 200, 50)) {
				AudioPlayer.getSound("menu_sound").play(1.0f, 0.5f);
				game.gameState = STATE.Menu;

			}
		}
		
		// Game Over Controls
		else if(game.gameState == STATE.End) {
			
			// Try Again Button
				
			if(mouseOver(mx, my, 400, 200, 200, 50)) {
				AudioPlayer.getSound("menu_sound").play(1.0f, 0.5f);
				game.gameState = STATE.Menu;
				HUD.HEALTH = 100;
				hud.setLevel(1);
				spawn.setNextLevel(2);
				hud.setScore(0);
				spawn.setScoreKeep(0);
				for(int i = 0; i < 10; i++) {
					handler.addObject(new MenuParticle((r.nextInt(Game.WIDTH - 50)), (r.nextInt(150) + 1), ID.MenuParticle, handler, game));		
				}

			}
		}
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < (x + width)) {
			if(my > y && my < (y + height)) {
				return true;
			} else return false;
		} else return false;
	}
	
	public void mouseReleased(MouseEvent e) {
		
		
	}
	
}
