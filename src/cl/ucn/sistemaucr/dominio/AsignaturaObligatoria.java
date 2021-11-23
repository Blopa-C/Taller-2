package cl.ucn.sistemaucr.dominio;

public class AsignaturaObligatoria extends Asignatura {
	private int nivel;
	private String[] prerrequisitos;
	
	public AsignaturaObligatoria(String codigo, String nombre, int creditos, int nivel, 
			String[] prerrequisitos) {
		super(codigo, nombre, creditos);
		this.nivel = nivel;
		this.prerrequisitos = prerrequisitos;
	}
	
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public String[] getPrerrequisitos() {
		return prerrequisitos;
	}
	public void setPrerrequisitos(String[] prerrequisitos) {
		this.prerrequisitos = prerrequisitos;
	}
}
