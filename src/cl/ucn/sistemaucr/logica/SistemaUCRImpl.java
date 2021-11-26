package cl.ucn.sistemaucr.logica;

import cl.ucn.sistemaucr.dominio.*;

public class SistemaUCRImpl implements SistemaUCR {
	private ListaAlumnos alumnos;
	private ListaProfesores profesores;
	private ListaAsignaturas asignaturas;
	private ListaParalelos paralelos;
	
	public SistemaUCRImpl() {
		alumnos = new ListaAlumnos(1000);
		profesores = new ListaProfesores(1000);
		asignaturas = new ListaAsignaturas(1000);
		paralelos = new ListaParalelos(1000);
	}
	
	@Override
	public boolean ingresarAlumno(String rut, String correo, int nivel, String contrasena) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ingresarAsignaturaObligatoria(String codigo, String nombre, int creditos,
			int nivel, String[] prerrequisitos) {
		Asignatura asig = new AsignaturaObligatoria(codigo, nombre, creditos, nivel,
				prerrequisitos);
		return asignaturas.ingresarAsignatura(asig);
	}

	@Override
	public boolean ingresarAsignaturaOpcional(String codigo, String nombre, int creditos, 
			int creditosPrerreq) {
		Asignatura asig = new AsignaturaOpcional(codigo, nombre, creditos, creditosPrerreq);
		return asignaturas.ingresarAsignatura(asig);
	}

	@Override
	public boolean ingresarProfesor(String rut, String correo, String contrasena, int salario) {
		Profesor p = new Profesor(rut, correo, contrasena, salario);
		return profesores.ingresarProfesor(p);
	}

	@Override
	public boolean ingresarParalelo(int numero, String codigo, String rutProfesor) {
		int iAsig = asignaturas.indexOf(codigo);
		int iProf = profesores.indexOf(rutProfesor);
		if (iAsig == -1 || iProf == -1) {
			throw new NullPointerException("Asignatura y/o profesor no existen");
		}
		else {
			Asignatura asig = asignaturas.getAsignaturaAt(iAsig);
			Profesor prof = profesores.getProfesorAt(iProf);
			Paralelo paral = new Paralelo(numero, asig, prof);
			return paralelos.ingresarParalelo(paral);
		}
	}

	@Override
	public boolean asociarNotaAlumno(String correoAlumno, String codigoAsignatura, double nota) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean asociarParaleloAlumno(String correoAlumno, String codigoAsignatura,
			int numero) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validarAlumno(String correo, String contrasena) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validarProfesor(String correo, String contrasena) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int verificarFecha(String fecha) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String obtenerAsignaturasDisponibles(String correoAlumno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenerParalelosDisponibles(String codigoAsignatura) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verificarCreditos(String correoAlumno, String codigoAsignatura, int numero) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String obtenerAsignaturasInscritas(String correoAlumno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean eliminarAsignaturaInscrita(String correoAlumno, String codigoAsignatura) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String obtenerParalelosProfesor(String correoProfesor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenerAlumnosInscritos(String codigoAsignatura, int numeroParalelo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenerInfoAlumnosEgresados() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenerInfoEstudiantes() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
