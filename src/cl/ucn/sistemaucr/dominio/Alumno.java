package cl.ucn.sistemaucr.dominio;

public class Alumno {
	private String rut;
	private String correo;
	private int nivel;
	private String contrasena;
	private ListaNotas asignaturasCursadas;
	private ListaParalelos asignaturasInscritas;
	
	public Alumno(String rut, String correo, int nivel, String contrasena) {
		this.rut = rut;
		this.correo = correo;
		this.nivel = nivel;
		this.contrasena = contrasena;
		asignaturasCursadas = new ListaNotas(1000);
		asignaturasInscritas = new ListaParalelos(1000);
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public ListaNotas getAsignaturasCursadas() {
		return asignaturasCursadas;
	}

	public void setAsignaturasCursadas(ListaNotas asignaturasCursadas) {
		this.asignaturasCursadas = asignaturasCursadas;
	}

	public ListaParalelos getAsignaturasInscritas() {
		return asignaturasInscritas;
	}

	public void setAsignaturasInscritas(ListaParalelos asignaturasInscritas) {
		this.asignaturasInscritas = asignaturasInscritas;
	}
}
