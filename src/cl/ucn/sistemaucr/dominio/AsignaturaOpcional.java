package cl.ucn.sistemaucr.dominio;

public class AsignaturaOpcional extends Asignatura {
	private int creditosPrerrequisito;

	public AsignaturaOpcional(String codigo, String nombre, int creditos, 
			int creditosPrerrequisito) {
		super(codigo, nombre, creditos);
		this.creditosPrerrequisito = creditosPrerrequisito;
	}

	public int getCreditosPrerrequisito() {
		return creditosPrerrequisito;
	}

	public void setCreditosPrerrequisito(int creditosPrerrequisito) {
		this.creditosPrerrequisito = creditosPrerrequisito;
	}
}
