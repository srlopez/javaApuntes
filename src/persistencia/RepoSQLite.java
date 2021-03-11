package persistencia;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dominio.Apunte;
import dominio.Categoria;

public class RepoSQLite implements IRepoDAO {

	String dbname = "data/apuntes.db";
	String script = "data/schemadb.sql";

	@Override
	public void inicializar() {
		File miFile = new File(dbname);

		if (!miFile.exists()) {
			// CREAMOS LA BD Y LAS TABLAS
			try {
				Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
				Statement stmt = conn.createStatement();
				String sql = Files.readString(Path.of(script));
				// System.out.println(sql);
				stmt.executeUpdate(sql);
				stmt.close();
				conn.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
	}

	@Override
	public void finalizar() {
	}

	@Override
	public void cmdRegistrarCategoria(Categoria categoria) throws Exception {
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
			String sql = "INSERT INTO CATEGORIAS (ID, IDPARENT, DESCRIPCION) VALUES (?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoria.id);
			pstmt.setInt(2, categoria.idParent);
			pstmt.setString(3, categoria.descripcion);

			// pstmt.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Error SQL Categoría no registrado.");
			}

			// int id = -1;
			// ResultSet generatedKeys = pstmt.getGeneratedKeys();
			// generatedKeys.next();
			// id = generatedKeys.getInt(1);

			pstmt.close();
			conn.close();
			// return id;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void cmdRegistrarApunte(Apunte apunte) throws Exception {
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
			String sql = "INSERT INTO APUNTES (FH, IDCATEGORIA, IDSUBCATEGORIA, IMPORTE, USER, DETALLE) VALUES (DATETIME('now', 'localtime'),?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// pstmt.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
			pstmt.setInt(1, apunte.categoria.id);
			pstmt.setInt(2, apunte.subCategoria.id);
			pstmt.setFloat(3, apunte.importe);
			pstmt.setString(4, apunte.user);
			pstmt.setString(5, apunte.detalle);

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Error SQL Apunte no registrado.");
			}

			// int id = -1;
			// ResultSet generatedKeys = pstmt.getGeneratedKeys();
			// generatedKeys.next();
			// id = generatedKeys.getInt(1);

			pstmt.close();
			conn.close();
			// return id;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Categoria> qrySubCategorias(Categoria categoria) {
		return qryCategoriasWhere("IDPARENT=" + categoria.id);
	}

	@Override
	public List<Categoria> qryCategorias() {
		return qryCategoriasWhere("IDPARENT=0 AND ID>0");
	}

	@Override
	public List<Apunte> qryApuntesTodos() {
		return qryApuntesWhere("");
	}

	@Override
	public Categoria qryCategoriaID(int id) {
		List<Categoria> lista = qryCategoriasWhere("ID=" + id);
		return lista.isEmpty() ? null : lista.get(0);
	}

	@Override
	public List<String> qryImportes() {
		// return List.of("NO IMPLEMENTADO");
		List<String> lista = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
			String sql = " SELECT IDCATEGORIA, SUM(IMPORTE) AS VALOR " 
					+ " FROM APUNTES GROUP BY IDCATEGORIA "
					+ " UNION " 
					+ " SELECT IDSUBCATEGORIA, SUM(IMPORTE) " 
					+ " FROM APUNTES GROUP BY IDSUBCATEGORIA "
					+ " ORDER BY VALOR DESC";

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
				lista.add(String.format("%2d %4.2f", rs.getInt(1), rs.getFloat(2)));

			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		// System.out.println(lista);
		return lista;
	}

	@Override
	public void cmdReset() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
			String sql = "DELETE FROM CATEGORIAS WHERE ID>0; DELETE FROM APUNTES";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (Exception e) {
		}
	}

	// PRIVADOS

	private Apunte rsToApunte(ResultSet rs) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date fh = dateFormat.parse(rs.getString("FH"));
		// System.out.println("> "+fh+" "+rs.getInt("IDCATEGORIA")+" "+
		// rs.getInt("IDSUBCATEGORIA")+" "+ rs.getFloat("IMPORTE")+" +
		// rs.getString("DETALLE") );
		Categoria c1 = qryCategoriaID(rs.getInt("IDCATEGORIA"));
		Categoria c2 = qryCategoriaID(rs.getInt("IDSUBCATEGORIA"));
		return new Apunte(fh, c1, c2, rs.getFloat("IMPORTE"), rs.getString("USER"), rs.getString("DETALLE"));
	}

	private List<Apunte> qryApuntesWhere(String where) {
		List<Apunte> lista = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
			String sql = "SELECT APUNTES.* FROM APUNTES ";
			if (where != "")
				sql += " WHERE " + where;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Apunte item = rsToApunte(rs);
				lista.add(item);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		// System.out.println(lista);
		return lista;
	}

	private Categoria rsToCategoria(ResultSet rs) throws Exception {
		// System.out.println(">
		// "+rs.getInt("ID")+rs.getString("DESCRIPCION")+rs.getInt("IDPARENT") );
		return new Categoria(rs.getInt("ID"), rs.getString("DESCRIPCION"), rs.getInt("IDPARENT"));
	}

	private List<Categoria> qryCategoriasWhere(String where) {
		List<Categoria> lista = new ArrayList<Categoria>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
			String sql = "SELECT CATEGORIAS.* FROM CATEGORIAS ";
			if (where != "")
				sql += " WHERE " + where;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Categoria item = rsToCategoria(rs);
				lista.add(item);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		// System.out.println(lista);
		return lista;
	}

}
