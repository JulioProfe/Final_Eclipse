package kissMyAirs;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PImage;
import serial.Posicion;
import serial.Registrar;
import serial.Select;

public class Logica implements Observer {
	private Comunicacion server;

	private PApplet app;
	private PImage[] interfaz;
	private PImage[] shoes;
	private String[] names;
	private int pantallas;
	private Personaje[] jugadores;

	public Logica(PApplet app) {
		// TODO Auto-generated constructor stub
		this.app = app;
		server = new Comunicacion(app);
		server.addObserver(this);
		new Thread(server).start();
		names = new String[3];
		jugadores = new Personaje[3];
		cargarImgs();
	}

	public void ejecutar() {
		pantallas();
	}

	private void pantallas() {
		switch (pantallas) {
		case 0:
			app.image(interfaz[0], 0, 0);
			if (jugadores[0]!=null && jugadores[1]!=null && jugadores[2]!=null) {
				pantallas=1;
			}
			break;

		case 1:
			app.image(interfaz[1], 0, 0);
			for (int i = 0; i < jugadores.length; i++) {
				jugadores[i].setX((app.width/3 +100) *i);
				jugadores[i].pintar();
			}
			break;

		default:
			break;
		}
	}

	private void cargarImgs() {
		interfaz = new PImage[2];
		shoes = new PImage[3];

		interfaz[0] = app.loadImage("../data/Interfaz/INICIO.png");
		interfaz[1] = app.loadImage("../data/Interfaz/PLAYERS.png");
		
		for (int i = 0; i < shoes.length; i++) {
			shoes[i] = app.loadImage("../data/Shoes/shoe_"+(i+1)+".png");
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Posicion) {
			// MOVIMIENTO
		}

		if (arg instanceof Registrar) {
			// NOMBRE DE USUARIO

			Registrar temp = (Registrar) arg;
			if (names[0] == null) {
				names[0] = temp.getName();
				System.out.println("Nombre de usuario 1 es: " + names[0]);
			} else if (names[1] == null) {
				names[1] = temp.getName();
				System.out.println("Nombre de usuario 2 es: " + names[1]);

			} else if (names[2] == null) {
				names[2] = temp.getName();
				System.out.println("Nombre de usuario 3 es: " + names[2]);

			}

		}

		if (arg instanceof Select) {
			Select temp = (Select) arg;
			for (int i = 0; i < names.length; i++) {
				String tempName = (String) names[i];
				if (tempName != null) {
					if (tempName.contains(temp.getName())) {
						if (jugadores[0] == null) {
							jugadores[0] = new Personaje(app, shoes[temp.getShoe()], temp.getName());
							System.out.println("Jugador 1 se llama " + names[0]);
						}

						else if (jugadores[1] == null) {
							jugadores[1] = new Personaje(app, shoes[temp.getShoe()], temp.getName());
							System.out.println("Jugador 2 se llama " + names[1]);
						}

						else if (jugadores[2] == null) {
							jugadores[2] = new Personaje(app, shoes[temp.getShoe()], temp.getName());
							System.out.println("Jugador 3 se llama " + names[2]);
						}
						System.out.println("El jugador llamado " + temp.getName() + " escogió los zapatos # "
								+ temp.getShoe());
					}
				}
			}

		}

	}
}
