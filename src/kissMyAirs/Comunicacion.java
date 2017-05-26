package kissMyAirs;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import serial.Registrar;

public class Comunicacion extends Observable implements Observer, Runnable {

	private ServerSocket ss;
	private ArrayList<ControlCliente> clientes;
	private ControlXMLUsuarios cxmlUsuarios;

	public Comunicacion(PApplet app) {
		cxmlUsuarios = new ControlXMLUsuarios(app);
		clientes = new ArrayList<ControlCliente>();
		try {
			ss = new ServerSocket(5000);
			System.out.println("[ SERVIDOR INICIADO EN: " + ss.toString() + " ]");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("[ ESPERANDO CLIENTE ]");
				System.out.println(InetAddress.getLocalHost());
				clientes.add(new ControlCliente(ss.accept(), this));
				System.out.println("[ NUEVO CLIENTE ES: " + clientes.get(clientes.size() - 1).toString() + " ]");
				System.out.println("[ CANTIDAD DE CLIENTES: " + clientes.size() + " ]");
				Thread.sleep(100);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable observado, Object obj) {
		if (obj instanceof Registrar) {
			Registrar notificacion = (Registrar) obj;
			String name= notificacion.getName();

			if (name.contains("cliente_no_disponible")) {
				clientes.remove(observado);
				System.out.println("[ SE HA IDO UN CLIENTE, QUEDAN: " + clientes.size() + " ]");
			}
		}
		
		setChanged();
		notifyObservers(obj);
		clearChanged();
		
		
		
	}
}