/* By: Brandon Widow 
 * 2016
 * 
 * Java Game Tutorial: RealTutsGML
 * https://www.youtube.com/user/RealTutsGML
 * 
 * Music: Kubbi - Up In My Jam (All Of A Sudden)
 * http://kubbi.bandcamp.com/album/gas-powered-ep
 * 
 */

package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = -473349850293143017L;
	
	public static final int WIDTH = 1024, HEIGHT = WIDTH / 16 * 9;
	
	public STATE gameState = STATE.Menu;
	
	private Thread thread;
	private boolean running = false;
	
	public static boolean paused = false;
	
	private Random r = new Random();
	private Handler handler;
	private HUD hud;
	private Spawn spawn;
	private Menu menu;
	
	public enum STATE {
		Menu,
		Game,
		Help,
		End
	};
	
	public Game() {

		handler = new Handler();
		hud = new HUD();
		spawn = new Spawn(handler, hud);
		menu = new Menu(this, handler, hud, spawn);
		
		this.addMouseListener(menu);
		this.addKeyListener(new KeyInput(handler, this));
		
		AudioPlayer.load();
		AudioPlayer.getMusic("music").loop(1.0f, 0.1f);
		
		new Window(WIDTH, HEIGHT, "Cubes!", this);
	
		
		if(gameState == STATE.Game) {
			handler.addObject(new Player((WIDTH/2) - 32 , (HEIGHT/2) - 32, ID.Player, handler));
			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
		}
		
		else {
			for(int i = 0; i < 10; i++) {
				handler.addObject(new MenuParticle((r.nextInt(Game.WIDTH - 50)), (r.nextInt(150) + 1), ID.MenuParticle, handler, this));		
			}
		}
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try{
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running) render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
		
	}
	
	private void tick() {
		
		if(gameState == STATE.Game) {
			if(!paused) {
				handler.tick();
				hud.tick();
				spawn.tick();
				if(HUD.HEALTH <= 0) {
					handler.clearAll();
					gameState = STATE.End;
				}
			}
		} 
		else if(gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Help) {
			handler.tick();
			menu.tick();
		}
	}
	
	private void render() {
		BufferStrategy bufferStrat = this.getBufferStrategy();
		if(bufferStrat == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bufferStrat.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		if(paused) {
			g.setColor(Color.white);
			g.drawString("PAUSED", 100, 100);
		}
		
		if(gameState == STATE.Game) {
			hud.render(g);
		} else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End) {
			menu.render(g);
		}
		
		g.dispose();
		bufferStrat.show();
	}
	
	public static float clamp(float var, float min, float max) {
		if(var >= max) return var = max;
		else if( var <= min) return var = min;
		else return var;
	}
	
	public static void main ( String[] args ) {
		new Game();
	}

}
