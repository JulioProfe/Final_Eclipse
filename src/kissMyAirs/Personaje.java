package kissMyAirs;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Personaje {

	private PApplet app;
	private PImage imagen;
	private int x, y, puntaje;
	private String name;
	private PFont font;

	public Personaje(PApplet app, PImage imagen, String name) {
		this.app = app;
		this.imagen = imagen;
		this.name = name;
		font = app.createFont("../data/types/FUTURAEXTRABOLDITA.TTF", 32);
		app.textFont(font);
	}

	public void pintar() {
		app.imageMode(PApplet.CENTER);
		app.image(imagen, x, y);
		app.imageMode(PApplet.CORNER);

		app.textSize(60);
		app.textAlign(PApplet.LEFT, PApplet.BOTTOM);

		app.fill(130);
		app.text(name.toUpperCase(), x + 93, y - 87, 200, 100);

		app.fill(255);
		app.text(name.toUpperCase(), x + 90, y - 90, 200, 100);
		
		app.textAlign(PApplet.CENTER, PApplet.CENTER);
	}
	
	public void pintarPuntaje(){
		app.text(puntaje, x, 100);
	}

	public boolean validarPos(int x, int y) {
		if (app.dist(this.x, this.y, x, y) <80) {
			return true;
		} else
			return false;
	}
	
	

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje += puntaje;
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

	public String getName() {
		return name;
	}

	public void setY(int y) {
		this.y = y;
	}

}
