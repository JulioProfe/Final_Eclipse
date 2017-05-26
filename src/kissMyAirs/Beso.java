package kissMyAirs;

import processing.core.PApplet;
import processing.core.PImage;

public class Beso {

	private PImage img;
	private int x, y, vel;
	private PApplet app;

	public Beso(PApplet app, PImage img, int x, int y, int vel) {

		this.img = img;
		this.app = app;
		this.x = x;
		this.y = y;
		this.vel=vel;
	}
	
	public void pintar(){
		app.imageMode(PApplet.CENTER);
		app.image(img, x, y);
		app.imageMode(PApplet.CORNER);
	}
	
	public void mover(){
		y+=vel;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getVel() {
		return vel;
	}
	
	
	
	

}
