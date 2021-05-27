package sistema;

import java.util.List;

import servicios.IDAL;
import sistema.model.Apunte;
import sistema.model.Categoria;

public class Sistema {

	IDAL repositorio;

	public Sistema(IDAL repositorio) {
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
	public void registrarCategoria( Categoria categoria ) throws Exception {
		try {
			repositorio.cmdRegistrarCategoria(categoria);
		} catch (Exception e) {
			throw e;
		}
	}
		
    public void cmdUpdateCategoria(Categoria categoria) throws Exception {
		try {
			repositorio.cmdUpdateCategoria(categoria);
		} catch (Exception e) {
			throw e;
		}
	}

    public void cmdDeleteCategoria(Categoria categoria) throws Exception {
		try {
			repositorio.cmdDeleteCategoria(categoria);
		} catch (Exception e) {
			throw e;
		}
	}

	public void registrarApunte(Apunte apunte) {
		try {
			repositorio.cmdRegistrarApunte(apunte);
		} catch (Exception e) {
			e.printStackTrace();
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

	public List<String> qryImportes(int id){
		return repositorio.qryImportes(id);
	}

	public void resetRepositorio(){
		repositorio.cmdReset();
	}


}
