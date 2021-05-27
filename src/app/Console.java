package app;

import servicios.*;
import sistema.*;
import ui.console.*;

public class Console {
	public static void main(String[] args) {
		// String dir = System.getProperty("user.dir");

		// Empezamos
		System.out.println("Empezamos...");

		// Preparamos los objetos que van a interaccionar
		//IDAL repo = new RepoSQLite();
		IDAL repo = new RepoMFile();
		Sistema sistema = new Sistema(repo);
		Vista vista = new Vista("Gastos");
		Controlador controlador = new Controlador(sistema, vista);

		// Una vez preparados lo lanzamos.
		controlador.run();
		// Fin
		System.out.println("Finalizamos.");
	}
}