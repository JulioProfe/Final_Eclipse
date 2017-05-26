package kissMyAirs;

import processing.core.PApplet;
import processing.core.PImage;

public class Logica {
	private Comunicacion server;

	private PApplet app;
	private PImage[] interfaz;

	private int pantallas;

	public Logica(PApplet app) {
		// TODO Auto-generated constructor stub
		this.app = app;
		server = new Comunicacion(app);
		server.start();
		cargarImgs();
	}

	public void ejecutar() {
		pantallas();
	}

	private void pantallas() {
		switch (pantallas) {
		case 0:
			app.image(interfaz[0], 0, 0);
			break;

		case 1:
			app.image(interfaz[1], 0, 0);

			break;

		default:
			break;
		}
	}

	private void cargarImgs() {
		interfaz = new PImage[2];

		interfaz[0] = app.loadImage("../data/Interfaz/INICIO.png");
		interfaz[1] = app.loadImage("../data/Interfaz/PLAYERS.png");

	}
}
