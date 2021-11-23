package cl.ucn.sistemaucr.logica;

import cl.ucn.sistemaucr.dominio.Alumno;

public class ListaAlumnos {
	private Alumno[] lista;
	private int cantAlum;
	private int max;
	
	public ListaAlumnos(int max) {
		lista = new Alumno[max];
		cantAlum = 0;
		this.max = max;
	}
	
	public int getCantAlumnos() {
		return cantAlum;
	}
	
	public boolean ingresarAlumno(Alumno obj) {
		if (cantAlum < max) {
			lista[cantAlum] = obj;
			cantAlum++;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean eliminarAlumno(String correo) {
		int i = indexOf(correo);
		if (i == -1) {
			return false;
		}
		else {
			for (int j = i; j < cantAlum; j++) {
				lista[j] = lista[j + 1];
			}
			cantAlum--;
			return true;
		}
	}
	
	public int indexOf(String correo) {
		for (int i = 0; i < cantAlum; i++) {
			if (lista[i].getCorreo().equals(correo)) {
				return i;
			}
		}
		return -1;
	}
	
	public Alumno getAlumnoAt(int i) {
		if (i >= 0 && i < cantAlum) {
			return lista[i];
		}
		else {
			return null;
		}
	}
}
