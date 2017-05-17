package servidor;

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import processing.core.PApplet;
import processing.data.XML;

public class ControlXMLUsuarios {

	private XML usuarios;
	private PApplet app;

	public ControlXMLUsuarios(PApplet app) {
		this.app = app;
		File dato = new File("../data/BD_usuarios.xml");
		if (dato.exists()) {
			usuarios = app.loadXML("../data/BD_usuarios.xml");
		} else {
			usuarios = app.parseXML("<usuarios></usuarios>");
			app.saveXML(usuarios, "../data/BD_usuarios.xml");
			System.out.println("GUARDADO");
		}
	}

	public boolean agregarUsuario(String nombre) {
		boolean existe = false;
		boolean agregado = false;
		XML[] hijos = usuarios.getChildren("usuario");
		for (int i = 0; i < hijos.length; i++) {
			System.out.println(hijos[i].getString("usuario"));
			if (hijos[i].getString("nombre").equals(nombre)) {
				existe = true; // el usuario existe
			}
		}
		if (!existe) {
			XML hijo = usuarios.addChild("usuario");
			hijo.setString("nombre", nombre);

			app.saveXML(usuarios, "../data/BD_usuarios.xml");
			agregado = true;
		}
		return agregado;
	}
}
