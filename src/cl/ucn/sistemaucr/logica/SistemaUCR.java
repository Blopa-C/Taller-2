package cl.ucn.sistemaucr.logica;

public interface SistemaUCR {

	boolean ingresarAlumno(String rut, String correo, int nivel, String contrasena);
	
	boolean ingresarAsignaturaObligatoria(String codigo, String nombre, int creditos,
			int nivel, String[] prerrequisitos);
	
	boolean ingresarAsignaturaOpcional(String codigo, String nombre, int creditos,
			int creditosPrerrequisito);
	
	boolean ingresarProfesor(String rut, String correo, String contrasena, int salario);
	
	boolean ingresarParalelo(int numero, String codigo, String rutProfesor);
	
	boolean asociarNotaAlumno(String correoAlumno, String codigoAsignatura, double nota);
	
	boolean asociarParaleloAlumno(String correoAlumno, String codigoAsignatura, int numero);
	
	boolean validarAlumno(String correo, String contrasena);
	
	boolean validarProfesor(String correo, String contrasena);
	
	String obtenerAsignaturasDisponibles(String correoAlumno);
	
	String obtenerParalelosDisponibles(String codigoAsignatura);
	
	boolean verificarCreditos(String correoAlumno, String codigoAsignatura, int numero);
	
	boolean chequearAsignaturasInscritas(String correoAlumno); // Nuevo
	
	String obtenerAsignaturasInscritas(String correoAlumno);
	
	boolean eliminarAsignaturaInscrita(String correoAlumno, String codigoAsignatura);
	
	String obtenerParalelosProfesor(String correoProfesor);
	
	String obtenerAlumnosInscritos(String codigoAsignatura, int numeroParalelo);
	//nuevo
	String obtenerAsignaturasDictadas(String correoProfesor);
	
	String obtenerAlumnosInscritos(String codigoAsignatura,String correoProfesor);
	
	boolean ingresarNota(String correoAlumno, String codigoAsignatura, double nota);
	
	//falta
	//
	String obtenerInfoAlumnosEgresados();
	
	String obtenerInfoEstudiantes();
}
