package cl.ucn.sistemaucr.logica;

import cl.ucn.sistemaucr.dominio.Paralelo;
import cl.ucn.sistemaucr.dominio.Asignatura;

public class ListaParalelos {
	private Paralelo[] lista;
	private int cantElem;
	private int max;
	
	public ListaParalelos(int max) {
		lista = new Paralelo[max];
		cantElem = 0;
		this.max = max;
	}
	
	public int getCantParalelos() {
		return cantElem;
	}
	
	public boolean ingresarParalelo(Paralelo obj) {
		if (cantElem < max) {
			lista[cantElem] = obj;
			cantElem++;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean eliminarParalelo(Asignatura asignatura) {
		int i = indexOf(asignatura);
		if (i == -1) {
			return false;
		}
		else {
			for (int j = i; j < cantElem; j++) {
				lista[j] = lista[j + 1];
			}
			cantElem--;
			return true;
		}
	}
	
	public int indexOf(int numero) {
		for (int i = 0; i < cantElem; i++) {
			if (lista[i].getNumero() == numero) {
				return i;
			}
		}
		return -1;
	}
	
	public int indexOf(Asignatura asignatura) {
		for (int i = 0; i < cantElem; i++) {
			if (lista[i].getAsignatura() == asignatura) {
				return i;
			}
		}
		return -1;
	}
	
	public Paralelo getParaleloAt(int i) {
		if (i >= 0 && i < cantElem) {
			return lista[i];
		}
		else {
			return null;
		}
	}
}
