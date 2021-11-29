package cl.ucn.sistemaucr.dominio;

import cl.ucn.sistemaucr.logica.ListaNotas;
import cl.ucn.sistemaucr.logica.ListaParalelos;

public class Alumno {
	private String rut;
	private String correo;
	private int nivel;
	private String contrasena;
	private int creditos;
	private ListaNotas asignaturasCursadas;
	private ListaParalelos asignaturasInscritas;
	
	public Alumno(String rut, String correo, int nivel, String contrasena) {
		this.rut = rut;
		this.correo = correo;
		this.nivel = nivel;
		this.contrasena = contrasena;
		creditos = 0;
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

	public int getCreditos() {
		return creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
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
