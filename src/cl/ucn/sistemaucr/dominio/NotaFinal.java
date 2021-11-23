package cl.ucn.sistemaucr.dominio;

public class NotaFinal {
	private double nota;
	private Asignatura asignatura;
	
	public NotaFinal(double nota, Asignatura asignatura) {
		this.nota = nota;
		this.asignatura = asignatura;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}
}
