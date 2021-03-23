package dominio;

public class Categoria {
    public int id;
    public int idParent;
    public String descripcion;

    public Categoria(int id, String descripcion) throws IllegalArgumentException {
    	if (id<1 || id>9) throw new IllegalArgumentException("Identificador "+id+" no permitido para categoría");
        this.id = id;
        this.idParent = 0;
        this.descripcion = descripcion;
    }

    public Categoria(int id, String descripcion, int idParent) throws IllegalArgumentException {
    	if (idParent<0 || idParent>9) throw new IllegalArgumentException("Identificador "+idParent+" no permitido para categoría");
    	if (id<idParent*10 || id>idParent*10+9) throw new IllegalArgumentException("Identificador "+id+" no permitido para subcategoría");
        this.id = id;
        this.idParent = idParent;
        this.descripcion = descripcion.replace(',', '_');
    }

    public String toString() {
        return String.format("%2d;%2d;%s", id, idParent, descripcion);
    }
}
