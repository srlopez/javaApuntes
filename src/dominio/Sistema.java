package dominio;

import java.util.List;

import persistencia.ISistemaDAO;

public class Sistema {

	ISistemaDAO repositorio;

	public Sistema(ISistemaDAO repositorio) {
		this.repositorio = repositorio;
		this.repositorio.inicializar();
	}

	public  void finalizar() 
	{	
		try {
			this.repositorio.finalizar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// VALIDAR IDENTIDAD
	public boolean esIDValido(String id) {
        return true;
    }

	// FACHADA A LOS MODELOS DE DOMINIO
	public void registrarApunte(Apunte apunte) throws Exception {
		try {
			repositorio.cmdRegistrarApunte(apunte);

		} catch (Exception e) {
			throw e;
		}
	}

	public void registrarCategoria( Categoria categoria ) throws Exception {
		try {
			repositorio.cmdRegistrarCategoria(categoria);
		} catch (Exception e) {
			throw e;
		}
	}
		
	public List<Apunte> qryApuntes() {
		return repositorio.qryApuntesTodos();
	}

	public Categoria qryCategoriaID(int id) {
		return repositorio.qryCategoriaID(id);
	}

	public List<Categoria> qryCategorias() {
		return repositorio.qryCategorias();
	}

	public List<Categoria> qrySubCategorias( Categoria categoria)  {
		return repositorio.qrySubCategorias(categoria);
	}

	public List<String> qryImportes(){
		return repositorio.qryImportes();
	}

	public void resetRepositorio(){
		repositorio.cmdReset();
	}


}