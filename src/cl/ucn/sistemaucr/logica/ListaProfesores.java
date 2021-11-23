package cl.ucn.sistemaucr.logica;

import cl.ucn.sistemaucr.dominio.Profesor;

public class ListaProfesores {
	private Profesor[] lista;
	private int cantElem;
	private int max;
	
	public ListaProfesores(int max) {
		lista = new Profesor[max];
		cantElem = 0;
		this.max = max;
	}
	
	public int getCantProfesores() {
		return cantElem;
	}
	
	public boolean ingresarProfesor(Profesor obj) {
		if (cantElem < max) {
			lista[cantElem] = obj;
			cantElem++;
			return true;
		}
		else {
			return false;
		}
	}
	
	public int indexOf(String correo) {
		for (int i = 0; i < cantElem; i++) {
			if (lista[i].getCorreo().equals(correo)) {
				return i;
			}
		}
		return -1;
	}
	
	public Profesor getProfesorAt(int i) {
		if (i >= 0 && i < cantElem) {
			return lista[i];
		}
		else {
			return null;
		}
	}
}
