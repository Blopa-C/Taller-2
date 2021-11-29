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
		Alumno alum = new Alumno(rut, correo, nivel, contrasena);
		return alumnos.ingresarAlumno(alum);
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
		int iAlum = alumnos.indexOf(correoAlumno);
		int iAsig = asignaturas.indexOf(codigoAsignatura);
		if (iAsig == -1) {
			throw new NullPointerException("Asignatura no existe");
		}
		else {
			Alumno alum = alumnos.getAlumnoAt(iAlum);
			Asignatura asig = asignaturas.getAsignaturaAt(iAsig);
			NotaFinal notaFinal = new NotaFinal(nota, asig);
			ListaNotas notas = alum.getAsignaturasCursadas();
			boolean ingreso = notas.ingresarNota(notaFinal);
			if (ingreso && nota >= 3.95) {
				alum.setCreditos(alum.getCreditos() + asig.getCreditos());
			}
			return ingreso;
		}
	}

	@Override
	public boolean asociarParaleloAlumno(String correoAlumno, String codigoAsignatura,
			int numero) {
		int iAlum = alumnos.indexOf(correoAlumno);
		Alumno alum = alumnos.getAlumnoAt(iAlum);
		int iAsig = asignaturas.indexOf(codigoAsignatura);
		if (iAsig == -1) {
			throw new NullPointerException();
		}
		else {
			Asignatura asig = asignaturas.getAsignaturaAt(iAsig);
			ListaParalelos paralelos = asig.getParalelos();
			int iParal = paralelos.indexOf(numero);
			if (iParal == -1) {
				throw new NullPointerException();
			}
			else {
				Paralelo paral = paralelos.getParaleloAt(iParal);
				ListaAlumnos inscritos = paral.getAlumnosInscritos();
				alum.getAsignaturasInscritas().ingresarParalelo(paral);
				return inscritos.ingresarAlumno(alum);
			}
		}
	}

	@Override
	public boolean validarAlumno(String correo, String contrasena) {
		int i = alumnos.indexOf(correo);
		if (i == -1) {
			throw new NullPointerException("El alumno no existe");
		}
		else {
			Alumno alum = alumnos.getAlumnoAt(i);
			if (alum.getContrasena().equals(contrasena)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	@Override
	public boolean validarProfesor(String correo, String contrasena) {
		int i = profesores.indexOf(correo);
		if (i == -1) {
			throw new NullPointerException("El profesor no existe");
		}
		else {
			Profesor prof = profesores.getProfesorAt(i);
			if (prof.getContrasena().equals(contrasena)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	@Override
	public String obtenerAsignaturasDisponibles(String correoAlumno) {
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
	public boolean chequearAsignaturasInscritas(String correoAlumno) {
		Alumno alum = alumnos.getAlumnoAt(alumnos.indexOf(correoAlumno));
		ListaParalelos inscritas = alum.getAsignaturasInscritas();
		if (inscritas.getCantParalelos() == 0) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public String obtenerAsignaturasInscritas(String correoAlumno) {
		String salida = "";
		Alumno alum = alumnos.getAlumnoAt(alumnos.indexOf(correoAlumno));
		ListaParalelos inscritas = alum.getAsignaturasInscritas();
		for (int i = 0; i < inscritas.getCantParalelos(); i++) {
			Asignatura asig = paralelos.getParaleloAt(i).getAsignatura();
			salida += "- " + asig.getNombre() + " (" + asig.getCodigo() + ")\n";
		}
		return salida.trim();
	}

	@Override
	public boolean eliminarAsignaturaInscrita(String correoAlumno, String codigoAsignatura) {
		Alumno alum = alumnos.getAlumnoAt(alumnos.indexOf(correoAlumno));
		int iAsig = asignaturas.indexOf(codigoAsignatura);
		if (iAsig == -1) {
			throw new NullPointerException("La asignatura no existe");
		}
		else {
			Asignatura asig = asignaturas.getAsignaturaAt(iAsig);
			ListaParalelos inscritasAlum = alum.getAsignaturasInscritas();
			Paralelo paral = inscritasAlum.getParaleloAt(inscritasAlum.indexOf(asig));
			if (inscritasAlum.eliminarParalelo(asig)) {
				ListaAlumnos inscritos = paral.getAlumnosInscritos();
				inscritos.eliminarAlumno(correoAlumno);
				return true;
			}
			else {
				return false;
			}
		}
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
