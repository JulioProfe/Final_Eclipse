package kissMyAirs;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PFont;
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
	private PImage beso;
	private PImage ganar;
	private String[] names;
	private Boolean[] iniciar;
	private int pantallas = 0;
	private int desplazamiento = -3920;
	private Personaje[] jugadores;
	private Personaje ganador = null;
	private PFont font;
	
	private Minim minim;
	private AudioPlayer fondo;

	private ArrayList<Beso> recolectables;

	public Logica(PApplet app) {
		// TODO Auto-generated constructor stub
		this.app = app;
		server = new Comunicacion(app);
		server.addObserver(this);
		new Thread(server).start();
		
		minim = new Minim(app);
		fondo = minim.loadFile("../data/Musica/fondo.mp3");
		fondo.loop();
		
		font = app.createFont("../data/types/FUTURAEXTRABOLDITA.TTF", 32);
		app.textFont(font);
		recolectables = new ArrayList<Beso>();
		names = new String[3];
		jugadores = new Personaje[3];
		iniciar = new Boolean[3];
		cargarImgs();
		iniciales();
	}

	public void ejecutar() {
		sonidoFondo();
		pantallas();
	}
	
	public void sonidoFondo() {
		if (fondo.position() == fondo.length()) {
			fondo.rewind();
		}
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

			for (int j = 0; j < jugadores.length; j++) {
				jugadores[j].pintar();
				jugadores[j].pintarPuntaje();
				jugadores[j].setY(app.height - 200);

				if (jugadores[j].getPuntaje() >= 120) {
					ganador = jugadores[j];
					pantallas = 3;

				}

				for (int i = 0; i < recolectables.size(); i++) {
					Beso tempB = (Beso) recolectables.get(i);

					tempB.mover();
					tempB.pintar();

					if (tempB.getY() > app.height + 50) {
						recolectables.remove(tempB);
						int r = (int) app.random(0, 6);
						addNew(r);
					}

					if (jugadores[j].validarPos(tempB.getX(), tempB.getY())) {
						recolectables.remove(tempB);
						jugadores[j].setPuntaje(10);
						int r = (int) app.random(0, 6);
						addNew(r);
					}
				}

			}




		case 3:
			if (ganador != null) {
				app.image(ganar, 0, 0);
				app.fill(255);
				app.textSize(160);
				app.textAlign(PApplet.CENTER, PApplet.CENTER);
				app.text(ganador.getName().toUpperCase(), 735, 540);
				app.noFill();
			}
			break;

		}
	}

	private void addNew(int n) {

		switch (n) {
		case 0:
			Beso tempBeso = new Beso(app, beso, 330, (int) app.random(-150, -90), (int) app.random(2, 8));
			recolectables.add(tempBeso);
			break;

		case 1:
			Beso tempBeso1 = new Beso(app, beso, 575, (int) app.random(-150, -90), (int) app.random(2, 8));
			recolectables.add(tempBeso1);
			break;
		case 2:
			Beso tempBeso2 = new Beso(app, beso, 830, (int) app.random(-150, -90), (int) app.random(2, 8));
			recolectables.add(tempBeso2);
			break;
		case 3:
			Beso tempBeso3 = new Beso(app, beso, 1070, (int) app.random(-150, -90), (int) app.random(2, 8));
			recolectables.add(tempBeso3);
			break;
		case 4:
			Beso tempBeso4 = new Beso(app, beso, 1330, (int) app.random(-150, -90), (int) app.random(2, 8));
			recolectables.add(tempBeso4);
			break;
		case 5:
			Beso tempBeso5 = new Beso(app, beso, 1590, (int) app.random(-150, -90), (int) app.random(2, 8));
			recolectables.add(tempBeso5);
			break;
		}
	}

	private void cargarImgs() {
		interfaz = new PImage[2];
		shoes = new PImage[3];
		pista = new PImage();
		beso = new PImage();
		ganar = new PImage();

		ganar = app.loadImage("../data/Interfaz/GANADOR.png");
		beso = app.loadImage("../data/Interfaz/beso.png");
		pista = app.loadImage("../data/Interfaz/pista.png");
		interfaz[0] = app.loadImage("../data/Interfaz/INICIO.png");
		interfaz[1] = app.loadImage("../data/Interfaz/PLAYERS.png");

		for (int i = 0; i < shoes.length; i++) {
			shoes[i] = app.loadImage("../data/Shoes/shoe_" + (i + 1) + ".png");
		}

	}

	public void iniciales() {
		for (int i = 0; i < 6; i++) {
			int temp = (int) app.random(0, 6);

			switch (temp) {
			case 0:
				Beso tempBeso = new Beso(app, beso, 330, (int) app.random(-150, -90), (int) app.random(2, 8));
				recolectables.add(tempBeso);
				break;

			case 1:
				Beso tempBeso1 = new Beso(app, beso, 575, (int) app.random(-150, -90), (int) app.random(2, 8));
				recolectables.add(tempBeso1);
				break;
			case 2:
				Beso tempBeso2 = new Beso(app, beso, 830, (int) app.random(-150, -90), (int) app.random(2, 8));
				recolectables.add(tempBeso2);
				break;
			case 3:
				Beso tempBeso3 = new Beso(app, beso, 1070, (int) app.random(-150, -90), (int) app.random(2, 8));
				recolectables.add(tempBeso3);
				break;
			case 4:
				Beso tempBeso4 = new Beso(app, beso, 1330, (int) app.random(-150, -90), (int) app.random(2, 8));
				recolectables.add(tempBeso4);
				break;
			case 5:
				Beso tempBeso5 = new Beso(app, beso, 1590, (int) app.random(-150, -90), (int) app.random(2, 8));
				recolectables.add(tempBeso5);
				break;

			default:
				break;
			}
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
