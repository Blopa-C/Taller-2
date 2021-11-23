package cl.ucn.sistemaucr.dominio;

import cl.ucn.sistemaucr.logica.ListaAlumnos;

public class Paralelo {
	private int numero;
	private Asignatura asignatura;
	private Profesor profesor;
	private ListaAlumnos alumnosInscritos;
	
	public Paralelo(int numero, Asignatura asignatura, Profesor profesor) {
		this.numero = numero;
		this.asignatura = asignatura;
		this.profesor = profesor;
		alumnosInscritos = new ListaAlumnos(100);
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public ListaAlumnos getAlumnosInscritos() {
		return alumnosInscritos;
	}

	public void setAlumnosInscritos(ListaAlumnos alumnosInscritos) {
		this.alumnosInscritos = alumnosInscritos;
	}
}
