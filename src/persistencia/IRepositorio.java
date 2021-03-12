package persistencia;

import java.util.List;

import dominio.Apunte;
import dominio.Categoria;

public interface IRepoDAO {
	// De operativa
    void inicializar();
    
    void finalizar();
		
    // Comandos de Categorias y Apuntes
    void cmdRegistrarCategoria(Categoria categoria) throws Exception ;
    
    void cmdRegistrarApunte(Apunte apunte) throws Exception ;

    // Querys de Categorias y Apuntes
    List<Categoria> qrySubCategorias(Categoria categoria);

    List<Categoria> qryCategorias();

    Categoria qryCategoriaID(int id);

    List<Apunte> qryApuntesTodos();

    List<String> qryImportes();

    // Otros
    void cmdReset();

}
