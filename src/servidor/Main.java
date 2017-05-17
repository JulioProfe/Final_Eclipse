package servidor;

import processing.core.PApplet;

public class Main extends PApplet {
	Comunicacion server;
	private Logica log;

	public static void main(String[] args) {
		PApplet.main("servidor.Main");
	}

	@Override
	public void settings() {
		// TODO Auto-generated method stub
		size(1200, 700);
	}

	@Override
	public void setup() {
		log = new Logica(this);
		server = new Comunicacion(this);
		server.start();
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		background(255);
	}
}
