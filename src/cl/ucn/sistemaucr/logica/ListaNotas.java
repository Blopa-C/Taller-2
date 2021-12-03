package cl.ucn.sistemaucr.logica;

import cl.ucn.sistemaucr.dominio.*;

public class ListaNotas {
	private NotaFinal[] lista;
	private int cantElem;
	private int max;
	
	public ListaNotas(int max) {
		lista = new NotaFinal[max];
		cantElem = 0;
		this.max = max;
	}
	
	public int getCantNotas() {
		return cantElem;
	}
	
	public boolean ingresarNota(NotaFinal obj ) {
		if (cantElem < max) {
			lista[cantElem] = obj;
			cantElem++;
			return true;
		}
		else {
			return false;
		}
	}
	
	public NotaFinal getNotaAt(int i) {
		if (i >= 0 && i < cantElem) {
			return lista[i];
		}
		else {
			return null;
		}
	}
	
	public int indexOf(Asignatura asig) {
		for (int i = 0; i < cantElem; i++) {
			if (lista[i].getAsignatura() == asig) {
				return i;
			}
		}
		return -1;
	}
}
