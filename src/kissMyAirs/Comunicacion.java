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
public class Comunicacion extends Thread implements Observer {

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
				sleep(100);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable observado, Object mensajeString) {
		String notificacion = (String) mensajeString;
		if (notificacion.contains("signup_req:")) {
			String[] partes = notificacion.split(":");
			boolean resultadoAgregar = cxmlUsuarios.agregarUsuario(partes[1]);
			((ControlCliente) observado).enviarMensaje("signup_resp:" + (resultadoAgregar == true ? 1 : 0));
		}
		if (notificacion.contains("cliente_no_disponible")) {
			clientes.remove(observado);
			System.out.println("[ SE HA IDO UN CLIENTE, QUEDAN: " + clientes.size() + " ]");
		}
	}
}