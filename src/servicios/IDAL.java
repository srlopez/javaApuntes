package servicios;

import java.util.List;

import sistema.model.*;

public interface IDAL {
	// De operativa
    void inicializar();
    
    void finalizar();
		
    // Comandos de Categorias y Apuntes
    void cmdRegistrarCategoria(Categoria categoria) throws Exception ;
    
    void cmdRegistrarApunte(Apunte apunte) throws Exception ;

    void cmdUpdateCategoria(Categoria categoria) throws Exception ;

    void cmdDeleteCategoria(Categoria categoria) throws Exception ;

    // Querys de Categorias y Apuntes
    List<Categoria> qrySubCategorias(Categoria categoria);

    List<Categoria> qryCategorias();

    Categoria qryCategoriaID(int id);

    List<Apunte> qryApuntesTodos();

    List<String> qryImportes(int id);

    // Otros
    void cmdReset();


}
