package dominio;

public enum ModeloTipo {
	SUMA {
		@Override
		public String toString() {
			return "+";
		}
	},
	MULTIPLICACION {
		@Override
		public String toString() {
			return "*";
		}
	}
}
