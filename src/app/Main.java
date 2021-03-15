package app;

import dominio.*;
import persistencia.*;
import ui.*;

public class Main {
	public static void main(String[] args) {
		System.out.println("Empezamos...");
		// String dir = System.getProperty("user.dir");

		// ISistemaDAO repo = new RepoSQLite();
		IRepositorio repo = new RepoMFile();
		Sistema sistema = new Sistema(repo);
		VistaConsola vista = new VistaConsola("Gastos");
		ControladorConsola controlador = new ControladorConsola(sistema, vista);
		controlador.run();
		System.out.println("Fin App.");
	}
}