package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import dominio.Apunte;
import dominio.Categoria;

public class RepoMFile implements IRepositorio {

	String filenameA = "data/apuntes.csv";
	String filenameC = "data/categorias.csv";
	List<Apunte> listaA = new ArrayList<>();
	List<Categoria> listaC = new ArrayList<>();

	@Override
	public void inicializar() {
		listaC = loadFile(filenameC, line -> {
				try {
				return this.lineToCategoria(line);
			} catch (Exception e) {
				// e.printStackTrace();
			}
			return null;
		});
		listaA = loadFile(filenameA, line -> {
			try {
				return this.lineToApunte(line);
			} catch (Exception e) {
				// e.printStackTrace();
			}
			return null;
		});
	}

	@Override
	public void finalizar() {
		saveFile(filenameC, listaC);
		saveFile(filenameA, listaA);
	}

	@Override
	public void cmdRegistrarCategoria(Categoria categoria) throws Exception {
		Categoria c = qryCategoriaID(categoria.id);
		if (c == null)
			listaC.add(categoria);
		else
			throw new Exception("No te pases");
	}

	@Override
	public void cmdUpdateCategoria(Categoria categoria) throws Exception {
		Categoria c = qryCategoriaID(categoria.id);
		listaC.remove(c);
		listaC.add(categoria);
	}

	@Override
	public void cmdDeleteCategoria(Categoria categoria) throws Exception {		
		Categoria c = qryCategoriaID(categoria.id);
		listaC.remove(c);
	}

	@Override
	public List<Categoria> qrySubCategorias(Categoria categoria) {
		List<Categoria> result = new ArrayList<>();
		for (Categoria item : listaC) {
			if (item.idParent == categoria.id)
				result.add(item);
		}
		return result;
	}

	@Override
	public List<Categoria> qryCategorias() {
		List<Categoria> result = new ArrayList<>();
		for (Categoria item : listaC) {
			if (item.idParent == 0 && item.id > 0)
				result.add(item);
		}
		return result;
	}

	@Override
	public void cmdRegistrarApunte(Apunte apunte) throws Exception {
		listaA.add(apunte);
	}

	@Override
	public Categoria qryCategoriaID(int id) {
		Categoria result = null;
		for (Categoria c : listaC) {
			if (c.id == id)
				return c;
		}
		return result;
	}

	@Override
	public List<Apunte> qryApuntesTodos() {
		return listaA;
	}

	@Override
	public void cmdReset() {
		listaA.clear();
		listaC.clear();
	}

	public <T> void saveFile(String filename, List<T> lista) {
		FileWriter fw;
		try {
			fw = new FileWriter(filename);
			for (T item : lista) {
				fw.write(item.toString() + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public <T> List<T> loadFile(String filename, Function<String, T> mapperFunction) {
		List<T> lista = new ArrayList<T>();

		File miFile = new File(filename);
		if (miFile.exists()) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(filename));
				String line = reader.readLine();
				while (line != null) {
					// System.out.println(line);
					try {
						T item = mapperFunction.apply(line);
						// System.out.println(item);
						if(item != null)	lista.add(item);
					} catch (Exception e) {
						e.printStackTrace();
					}
					line = reader.readLine();
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lista;
	}

	private Apunte lineToApunte(String line) throws Exception {
		String[] item = line.trim().split(",");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date fh = dateFormat.parse(item[0]);

		int categoria = Integer.parseInt(item[1]);
		int subCategoria = Integer.parseInt(item[2]);
		float importe = Float.parseFloat(item[3]);
		String user = item[4];
		String detalle = item[5];
		Categoria c1 = qryCategoriaID(categoria);
		Categoria c2 = qryCategoriaID(subCategoria);

		return new Apunte(fh, c1, c2, importe, user, detalle);
	}

	private Categoria lineToCategoria(String line) throws Exception {
		String[] item = line.trim().split(",");
		int id = Integer.parseInt(item[0].trim());
		int idParent = Integer.parseInt(item[1].trim());
		String descripcion = item[2];
		return new Categoria(id, descripcion, idParent);
	}

	@Override
	public List<String> qryImportes() {
		Map<Integer, Float> map = new HashMap<Integer, Float>();
		try{
		for (Apunte item : listaA) {
			Float v = map.getOrDefault(item.categoria.id, (float) 0.0);
			map.put(item.categoria.id, v + item.importe);
			v = map.getOrDefault(item.subCategoria.id, (float) 0.0);
			map.put(item.subCategoria.id, v + item.importe);
		}
		// Aplicación de programación funcional
		// para obtener el resultado.
		return map.entrySet()
			.stream()
			.sorted(Map.Entry.<Integer, Float>comparingByValue().reversed())
			// .limit(3)
			.map(e -> String.format("%2d", e.getKey()) + "\t" + e.getValue()).collect(Collectors.toList());
		}catch(Exception e){}
		return new ArrayList<>();
	}



}
