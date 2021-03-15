package ui;

import java.util.List;

import dominio.*;
import negocio.*;

public class ControladorConsola {
	Sistema sistema;
	VistaConsola vista;
	ModoTerminal modo;

	String usuarioActivo = "";

	public ControladorConsola(Sistema sistema, VistaConsola vista) {
		this.sistema = sistema;
		this.vista = vista;
	}

	/**
	 * Ciclo de Ejecución
	 */
	public void run() {
		char opcion;
		loop: while (true) {
			modo = vista.mostrarMenu();
			opcion = vista.leerOpcionMenu();
			switch (opcion) {
			// MODO
			case 'L':
				validarCambioDeModo();
				break;
			case '1': {
				ucConsultarCategorias();
				break;
			}
			case '2': {
				ucConsultarSubCategorias();
				break;
			}
			case '3': {
				ucConsultarApuntes();
				break;
			}
			case '4': {
				ucRegistrarApunte();
				break;
			}
			case '5': {
				ucVerGrafico();
				break;
			}
			case '6': {
				ucRegistrarCategoria();
				break;
			}
			case '7': {
				ucRegistrarSubCategoria();
				break;
			}
			case '8': {
				ucUpdateDeleteCategoria();
				break;
			}			
			case '9': {
				ucReset();
				break;
			}
			case 'E':
			case 'F': {
				break loop;
			}
			default:
			}
		}
		sistema.finalizar();
		vista.mostrarMsg("MVC Fin");
	}

	// Modo de la vista
	void validarCambioDeModo() {
		if (modo == ModoTerminal.NORMAL) {
			usuarioActivo = vista.leerCredenciales();
			String id = usuarioActivo.toLowerCase();
			if (sistema.esIDValido(usuarioActivo)) {
				if (id.charAt(0) == 'a')
					vista.establecerModo(ModoTerminal.ADMIN);
				if (id.charAt(0) == 'u')
					vista.establecerModo(ModoTerminal.USER);
			}
		} else {
			usuarioActivo = "";
			vista.establecerModo(ModoTerminal.NORMAL);
		}
	}

	// Casos de USOS
	public void ucConsultarCategorias() {
		vista.mostrarMsg("= CATEGORIAS =");
		vista.mostrarMsgs(sistema.qryCategorias());
	}

	public void ucConsultarSubCategorias() {
		try {
			// Obtencion de información de usuario
			vista.mostrarMsg("= CATEGORIAS =");
			int idParent = vista.seleccionarCategoria(sistema.qryCategorias(), "Selecciona una categoría");
			if (idParent == -1)
				return;
			Categoria cat = sistema.qryCategoriaID(idParent);
			vista.mostrarMsg("= SUBCATEGORIAS " + cat.descripcion + " =");
			vista.mostrarMsgs(sistema.qrySubCategorias(cat));
		} catch (Exception e) {
			vista.mostrarMsg("UC3 Error: %s", e.getMessage());
		}
	};

	private void ucConsultarApuntes() {
		vista.mostrarMsg("= APUNTES =");
		vista.mostrarMsgs(sistema.qryApuntes());
	}

	private void ucRegistrarApunte() {
		try {
			// Obtencion de información de usuario
			int idParent = vista.seleccionarCategoria(sistema.qryCategorias(), "Selecciona una categoría");
			if (idParent == -1)
				return;
			Categoria c1 = sistema.qryCategoriaID(idParent);

			int idSub = vista.seleccionarCategoria(sistema.qrySubCategorias(c1), "Selecciona una subcategoría");
			if (idSub == -1)
				return;
			Categoria c2 = sistema.qryCategoriaID(idSub);

			float importe = vista.leerDatoFloat("Importe");
			String detalle = vista.leerDatoString("Detalle");
			Apunte apunte = new Apunte(c1, c2, importe, usuarioActivo, detalle);
			// Ejecución de Caso de Uso
			sistema.registrarApunte(apunte);
			// Presentación al usuario
			vista.mostrarMsg("Registro correcto");
		} catch (Exception e) {
			vista.mostrarMsg("UC Error: %s", e.getMessage());
		}
	}

	private void ucVerGrafico() {
		vista.mostrarMsg("= IMPORTES =");
		vista.mostrarMsgs(sistema.qryImportes());
	}

	public void ucRegistrarCategoria() {
		try {
			// Obtencion de información de usuario
			int id = vista.leerDatoInt("Identificador de categoría");
			String descripcion = vista.leerDatoString("Descripción #" + id);
			Categoria categoria = new Categoria(id, descripcion);
			// Verificación Reglas de Negocio
			ReglasDeNegocio.rl1(categoria);
			ReglasDeNegocio.rl2();
			// Ejecución de Caso de Uso
			sistema.registrarCategoria(categoria);
			// Presentación al usuario
			vista.mostrarMsg("Registro correcto");
		} catch (Exception e) {
			vista.mostrarMsg("UC Error: %s", e.getMessage());
		}
	}

	public void ucUpdateDeleteCategoria() {
		try {
			vista.mostrarMsg("= CATEGORIAS =");
			int id = vista.seleccionarCategoria(sistema.qryCategorias(), "Selecciona una categoría");
			if (id == -1)
				return;
			String ud = vista.leerDatoString("D=Delete, U=Update, (C=Cancelar)").toLowerCase();
			Categoria cat = sistema.qryCategoriaID(id);
			if (ud.equals("u")) {
				String descripcion = vista.editDatoString("Descripción #" + id, cat.descripcion);
				cat.descripcion = descripcion;
				// Ejecución de Caso de Uso
				sistema.cmdUpdateCategoria(cat);
				// Presentación al usuario
				vista.mostrarMsg("Registro actualizado");
			};
			if (ud.equals("d")) {
				sistema.cmdDeleteCategoria(cat);
				// Presentación al usuario
				vista.mostrarMsg("Registro elimnado");
			};
		} catch (Exception e) {
			vista.mostrarMsg("UC Error: %s", e.getMessage());
		}
	}

	public void ucRegistrarSubCategoria() {
		try {
			// Obtencion de información de usuario
			int idParent = vista.seleccionarCategoria(sistema.qryCategorias(), "Selecciona una categoría");
			if (idParent == -1)
				return;
			int id = vista.leerDatoInt("Identificador de Subcategoría");
			String descripcion = vista.leerDatoString("Descripción #" + id);
			Categoria categoria = new Categoria(id, descripcion, idParent);
			// Verificación Reglas de Negocio
			ReglasDeNegocio.rl1(categoria);
			ReglasDeNegocio.rl2();
			// Ejecución de Caso de Uso
			sistema.registrarCategoria(categoria);
			// Presentación al usuario
			vista.mostrarMsg("Registro correcto");
		} catch (Exception e) {
			vista.mostrarMsg("UC Error: %s", e.getMessage());
		}
	}

	public void ucReset() {
		sistema.resetRepositorio();
	}

	// NADA
	public void useCase0() {
		vista.mostrarMsg("UC No implementado");
	}

}
