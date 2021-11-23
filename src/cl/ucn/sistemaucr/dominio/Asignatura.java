package cl.ucn.sistemaucr.dominio;

public class Asignatura {
	private String codigo;
	private String nombre;
	private int creditos;
	private ListaParalelos paralelos;
	
	protected Asignatura(String codigo, String nombre, int creditos) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.creditos = creditos;
		paralelos = new ListaParalelos(1000);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCreditos() {
		return creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	public ListaParalelos getParalelos() {
		return paralelos;
	}

	public void setParalelos(ListaParalelos paralelos) {
		this.paralelos = paralelos;
	}
}
