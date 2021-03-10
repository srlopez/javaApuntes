package negocio;

import java.util.Calendar;

import dominio.Categoria;

/**
 * Requisitos Lógicos - NO FUNCIONALES
 * REGLAS DE NEGOCIO
 */
public class ReglasDeNegocio {

	public static boolean rl1(Categoria categoria) throws Exception {
		return  rethrow((categoria.id>=0 && categoria.id<100), "RL1 No se cumple que ... ("+categoria+")") ;
	}
	
	public static boolean rl2() throws Exception {
		Calendar rightNow = Calendar.getInstance();
		int hora = rightNow.get(Calendar.HOUR_OF_DAY);
		return  rethrow((hora>=8 && hora<15), "RL2 Fuera de horario ("+hora+")") ;
	}

	private static boolean rethrow( boolean exprCierta, String msg) throws Exception {
		//if(!exprCierta) throw new Exception(msg);
		return true;
	}

}
