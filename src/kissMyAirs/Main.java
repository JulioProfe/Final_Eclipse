package kissMyAirs;

import processing.core.PApplet;

public class Main extends PApplet {
	private Logica log;

	public static void main(String[] args) {
		PApplet.main("kissMyAirs.Main");
	}

	@Override
	public void settings() {
		// TODO Auto-generated method stub
		// size(1200, 700);
		fullScreen();
	}

	@Override
	public void setup() {
		log = new Logica(this);

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		background(255);
		log.ejecutar();
	}
}
