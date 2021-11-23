package cl.ucn.sistemaucr.logica;

import cl.ucn.sistemaucr.dominio.Paralelo;

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
	
	public int indexOf(int numero) {
		for (int i = 0; i < cantElem; i++) {
			if (lista[i].getNumero() == numero) {
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
