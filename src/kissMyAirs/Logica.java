package kissMyAirs;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PImage;
import serial.Iniciar;
import serial.Posicion;
import serial.Registrar;
import serial.Select;

public class Logica implements Observer {
	private Comunicacion server;

	private PApplet app;
	private PImage[] interfaz;
	private PImage[] shoes;
	private PImage pista;
	private String[] names;
	private Boolean[] iniciar;
	private int pantallas = 0;
	private int desplazamiento = -3920;
	private Personaje[] jugadores;

	public Logica(PApplet app) {
		// TODO Auto-generated constructor stub
		this.app = app;
		server = new Comunicacion(app);
		server.addObserver(this);
		new Thread(server).start();
		names = new String[3];
		jugadores = new Personaje[3];
		iniciar = new Boolean[3];
		cargarImgs();
	}

	public void ejecutar() {
		pantallas();
	}

	private void pantallas() {
		switch (pantallas) {
		case 0:
			app.image(interfaz[0], 0, 0);
			if (jugadores[0] != null && jugadores[1] != null && jugadores[2] != null) {
				pantallas = 1;
			}
			break;

		case 1:
			app.image(interfaz[1], 0, 0);
			for (int i = 0; i < jugadores.length; i++) {
				app.rectMode(PApplet.CENTER);
				app.rect(jugadores[i].getX(), jugadores[i].getY(), 280, 280);
				jugadores[i].setX((app.width / 4 * i) + app.width / 5);
				jugadores[i].setY(app.height / 2);
				jugadores[i].pintar();
			}
			if (iniciar[0] != null && iniciar[1] != null && iniciar[2] != null) {
				if (iniciar[0] == true && iniciar[1] == true && iniciar[2] == true) {
					pantallas = 2;
				}
			}

			break;

		case 2:

			app.image(pista, 0, desplazamiento);
			desplazamiento += 3;
			if (desplazamiento >= (-20)) {
				desplazamiento = -3600;
			}

			for (int i = 0; i < jugadores.length; i++) {
				// jugadores[i].mover();
				jugadores[i].pintar();
				jugadores[i].setY(app.height-200);
			}

		default:
			break;
		}
	}

	private void cargarImgs() {
		interfaz = new PImage[2];
		shoes = new PImage[3];
		pista = new PImage();

		pista = app.loadImage("../data/Interfaz/pista.png");
		interfaz[0] = app.loadImage("../data/Interfaz/INICIO.png");
		interfaz[1] = app.loadImage("../data/Interfaz/PLAYERS.png");

		for (int i = 0; i < shoes.length; i++) {
			shoes[i] = app.loadImage("../data/Shoes/shoe_" + (i + 1) + ".png");
		}

	}

	@Override
	public void update(Observable o, Object arg) {
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
						System.out.println(
								"El jugador llamado " + temp.getName() + " escogió los zapatos # " + temp.getShoe());
					}
				}
			}
		}

		if (arg instanceof Iniciar) {
			Iniciar temp = (Iniciar) arg;

			if (iniciar[0] == null) {
				iniciar[0] = temp.isIniciar();
				System.out.println("JUGADOR " + temp.getName() + "ESTÁ LISTO PARA JUGAR");
			}

			else if (iniciar[1] == null) {
				iniciar[1] = temp.isIniciar();
				System.out.println("JUGADOR " + temp.getName() + "ESTÁ LISTO PARA JUGAR");

			}

			else if (iniciar[2] == null) {
				iniciar[2] = temp.isIniciar();
				System.out.println("JUGADOR " + temp.getName() + "ESTÁ LISTO PARA JUGAR");

			}
		}

		if (arg instanceof Posicion) {
			Posicion temp = (Posicion) arg;

			if (jugadores[0].getName().contains(temp.getName())) {
				double x = app.map((float) temp.getPos(), 9, -9, 330, 575);
				// jugadores[0].setLimite((int) x);
				jugadores[0].setX((int) x);

				System.out.println("El jugador # " + temp.getName() + " está en: " + x);
			} else

			if (jugadores[1].getName().contains(temp.getName())) {
				double x = app.map((float) temp.getPos(), 9, -9, 830, 1070);
				// jugadores[1].setLimite((int) x);
				jugadores[1].setX((int) x);

				System.out.println("El jugador # " + temp.getName() + " está en: " + x);
			} else

			if (jugadores[2].getName().contains(temp.getName())) {
				double x = app.map((float) temp.getPos(), 9, -9, 1330, 1590);
				// jugadores[2].setLimite((int) x);
				jugadores[2].setX((int) x);

				System.out.println("El jugador # " + temp.getName() + " está en: " + x);
			}

		}

	}
}
