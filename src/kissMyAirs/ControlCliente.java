package kissMyAirs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;

import serial.Posicion;
import serial.Registrar;

public class ControlCliente extends Observable implements Runnable {

	private Socket s;
	private Observer jefe;
	private boolean disponible;

	public ControlCliente(Socket s, Observer jefe) {
		this.s = s;
		this.jefe = jefe;
		disponible = true;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		while (disponible) {
			try {
				recibirMensajes();
				Thread.sleep(33);
			} catch (IOException e) {
				System.out.println("[ PROBLEMA CON CLIENTE: " + e + " ]");
				setChanged();
				jefe.update(this, "cliente_no_disponible");
				disponible = false;
				clearChanged();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			s = null;
		}
	}

	// private void recibirMensajes() throws IOException {
	// DataInputStream dis = new DataInputStream(s.getInputStream());
	// String mensaje = dis.readUTF();
	// System.out.println("[ MENSAJE A RECIBIDO: " + mensaje + " ]");
	// jefe.update(this, mensaje);
	// }

	public void recibirMensajes() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		Object recibido = in.readObject();
		if (recibido instanceof Registrar) {
			Registrar temp = (Registrar) recibido;
			System.out.println("Cliente: Llegó " + temp.getName());
		}
		
		if (recibido instanceof Posicion) {
			Posicion temp= (Posicion) recibido;
			System.out.println("Llegaron posiciones: "+ temp.getPos() + " de "+ temp.getName());
		}
		jefe.update(null, recibido);
	}

	// public void enviarMensaje(String mensaje) {
	// try {
	// DataOutputStream dos = new DataOutputStream(s.getOutputStream());
	// dos.writeUTF(mensaje);
	// System.out.println("[ MENSAJE A ENVIADO: " + mensaje + " ]");
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	public void enviarMensaje(Object o) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
		out.writeObject(o);
		System.out.println("Servidor: Se envio: " + o.getClass());
	}

	@Override
	public String toString() {
		return s.toString();
	}

}
