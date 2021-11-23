package cl.ucn.sistemaucr.dominio;

public class Profesor {
	private String rut;
	private String correo;
	private String contrasena;
	private int salario;
	private ListaParalelos asignaturas;
	
	public Profesor(String rut, String correo, String contrasena, int salario) {
		this.rut = rut;
		this.correo = correo;
		this.contrasena = contrasena;
		this.salario = salario;
		asignaturas = new ListaParalelos(4);
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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public int getSalario() {
		return salario;
	}

	public void setSalario(int salario) {
		this.salario = salario;
	}

	public ListaParalelos getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(ListaParalelos asignaturas) {
		this.asignaturas = asignaturas;
	}	
}
