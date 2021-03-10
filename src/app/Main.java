package app;

import dominio.*;
import persistencia.*;
import ui.*;

public class Main {
	public static void main(String[] args) throws Exception {
		// MVC en modo terminal
		System.out.println("MVC Consola");
//		String dir = System.getProperty("user.dir");
		
		// Run con un Repositorio SQL/Mem
		//ISistemaDAO repo = new SistemaSQLite();
		ISistemaDAO repo = new SistemaMFile();
		Sistema sistema = new Sistema(repo);
		VistaConsola vista = new VistaConsola("Gastos");
		ControladorConsola controlador = new ControladorConsola(sistema, vista);
		controlador.run();
		
	}
}