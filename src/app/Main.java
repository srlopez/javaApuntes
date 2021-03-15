package app;

import dominio.*;
import persistencia.*;
import ui.*;

public class Main {
	public static void main(String[] args) {
		// String dir = System.getProperty("user.dir");

		// Empezamos
		System.out.println("Empezamos...");

		// Preparamos los objetos que van a interaccionar
		// ISistemaDAO repo = new RepoSQLite();
		IRepositorio repo = new RepoMFile();
		Sistema sistema = new Sistema(repo);
		VistaConsola vista = new VistaConsola("Gastos");
		ControladorConsola controlador = new ControladorConsola(sistema, vista);

		// Una vez preparados lo lanzamos.
		controlador.run();
		// Fin
		System.out.println("Finalizamos.");
	}
}