package kissMyAirs;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Personaje {
	
	private PApplet app;
	private PImage imagen;
	private int x, y;
	private String name;
	private PFont font;

	
	public Personaje(PApplet app, PImage imagen, String name) {
		this.app=app;
		this.imagen=imagen;
		this.name=name;
		font = app.createFont("../data/types/FUTURAEXTRABOLDITA.TTF", 32);
		app.textFont(font);
	}
	
	public void pintar(){
		app.imageMode(PApplet.CENTER);
		app.image(imagen, x, y);
		app.imageMode(PApplet.CORNER);
		
		app.textSize(60);
		app.textAlign(PApplet.LEFT,PApplet.BOTTOM);
		
		app.fill(130);
		app.text(name.toUpperCase(), x+93, y-87);
		
		app.fill(255);
		app.text(name.toUpperCase(), x+90, y-90);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	

}
