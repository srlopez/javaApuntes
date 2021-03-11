package dominio;

import java.util.List;

import persistencia.IRepoDAO;

public class Sistema {

	IRepoDAO repositorio;

	public Sistema(IRepoDAO repositorio) {
		this.repositorio = repositorio;
		this.repositorio.inicializar();
	}

	public  void finalizar() {
		this.repositorio.finalizar();
	}

	// VALIDAR IDENTIDAD
	public boolean esIDValido(String id) {
		// TODO:
        return true;
    }

	// FACHADA PARA OBTENER LOS MODELOS DE DOMINIO
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
