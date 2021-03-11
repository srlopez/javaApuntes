package dominio;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Apunte {
    // public int id;
    public Date fh;
    public Categoria categoria;
    public Categoria subCategoria;
    public float importe;
    public String user;
    public String detalle;

    public Apunte(Categoria categoria, Categoria subCategoria, float importe, String user, String detalle) {
        this(new Date(), categoria, subCategoria, importe, user, detalle);
    }

    public Apunte(Date fh, Categoria categoria, Categoria subCategoria, float importe, String user, String detalle) {
        this.fh = fh;
        this.categoria = categoria;
        this.subCategoria = subCategoria;
        this.importe = importe;
        this.user = user;
        this.detalle = detalle;
    }

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(fh) + "," + categoria.id + "," + subCategoria.id + "," + importe + "," + user+ "," + detalle;
    }
}
