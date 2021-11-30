package cl.ucn.sistemaucr.dominio;

import cl.ucn.sistemaucr.logica.ListaAsignaturas;
import cl.ucn.sistemaucr.logica.ListaParalelos;

public class Profesor {
	private String rut;
	private String correo;
	private String contrasena;
	private int salario;
	private ListaAsignaturas asignaturasDictadas;//nuevo
	private ListaParalelos asignaturas;
	
	public Profesor(String rut, String correo, String contrasena, int salario) {
		this.rut = rut;
		this.correo = correo;
		this.contrasena = contrasena;
		this.salario = salario;
		asignaturas = new ListaParalelos(4);
		asignaturasDictadas = new ListaAsignaturas(1000);//new
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

	public ListaAsignaturas getAsignaturasDictadas() {
		return asignaturasDictadas;
	}

	public void setAsignaturasDictadas(ListaAsignaturas asignaturasDictadas) {
		this.asignaturasDictadas = asignaturasDictadas;
	}	
	
	public String obtenerAlumnosPorParalelo(String codAsignatura) {
		String salida = "";
		for(int i =0; i < asignaturas.getCantParalelos(); i++) {
			Paralelo asignatura = asignaturas.getParaleloAt(i);
			if(asignatura.getAsignatura().getCodigo().equals(codAsignatura)){
				salida += asignatura.getAlumnosInscritos();
			}
		}
		return salida;
	}
}
