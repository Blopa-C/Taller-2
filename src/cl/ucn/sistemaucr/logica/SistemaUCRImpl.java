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
				boolean ingreso = inscritos.ingresarAlumno(alum);
				if (ingreso) {
					alum.getAsignaturasInscritas().ingresarParalelo(paral);
					return true;
				}
				else return false;
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
	
	private boolean chequearPrerrequisitos(String[] prerreqs, Alumno alum) {
		ListaNotas notas = alum.getAsignaturasCursadas();
		for (int i = 0; i < prerreqs.length; i++) {
			String prerreq = prerreqs[i];
			boolean flag = false;
			for (int j = 0; j < notas.getCantNotas(); j++) {
				NotaFinal notaF = notas.getNotaAt(j);
				Asignatura asig = notaF.getAsignatura();
				if (prerreq.equals(asig.getCodigo()) && notaF.getNota() >= 3.95) {
					flag = true;
				}
			}
			if (!flag) {
				return false;
			}
		}
		return true;
	}

	private ListaAsignaturas obtenerDisponibles(String correoAlumno) {
		Alumno alum = alumnos.getAlumnoAt(alumnos.indexOf(correoAlumno));
		ListaAsignaturas disponibles = new ListaAsignaturas(1000);
		for (int i = 0; i < asignaturas.getCantAsignaturas(); i++) {
			Asignatura asig = asignaturas.getAsignaturaAt(i);
			if (asig instanceof AsignaturaObligatoria) {
				String[] prerreq = ((AsignaturaObligatoria)asig).getPrerrequisitos();
				if (chequearPrerrequisitos(prerreq, alum)) {
					disponibles.ingresarAsignatura(asig);
				}
			}
			else {
				int credPrerreq = ((AsignaturaOpcional)asig).getCreditosPrerrequisito();
				if (alum.getCreditos() >= credPrerreq) {
					disponibles.ingresarAsignatura(asig);
				}
			}
		}
		eliminarYaCursadas(alum, disponibles);
		return disponibles;
	}
	
	private void eliminarYaCursadas(Alumno alum, ListaAsignaturas disponibles) {
		ListaNotas notas = alum.getAsignaturasCursadas();
		for (int i = 0; i < disponibles.getCantAsignaturas(); i++) {
			Asignatura asig = disponibles.getAsignaturaAt(i);
			int iNota = notas.indexOf(asig);
			if (iNota != -1) {
				NotaFinal notaF = notas.getNotaAt(iNota);
				if (notaF.getNota() >= 3.95) {
					disponibles.eliminarAsignatura(asig.getCodigo());
				}
			}
		}
	}
	
	@Override
	public String obtenerAsignaturasDisponibles(String correoAlumno) {
		String salida = "";
		ListaAsignaturas disponibles = obtenerDisponibles(correoAlumno);
		for (int i = 0; i < disponibles.getCantAsignaturas(); i++) {
			Asignatura asig = disponibles.getAsignaturaAt(i);
			salida += "- " + asig.getNombre() + " (" + asig.getCodigo() + ")\n";
		}
		return salida.trim();
	}

	@Override
	public boolean chequearCuposParalelos(String codigoAsignatura) {
		int iAsig = asignaturas.indexOf(codigoAsignatura);
		if (iAsig == -1) {
			throw new NullPointerException("La asignatura no existe");
		}
		else {
			Asignatura asig = asignaturas.getAsignaturaAt(iAsig);
			ListaParalelos paralelos = asig.getParalelos();
			for (int i = 0; i < paralelos.getCantParalelos(); i++) {
				Paralelo paral = paralelos.getParaleloAt(i);
				ListaAlumnos inscritos = paral.getAlumnosInscritos();
				if (inscritos.getCantAlumnos() < 100) {
					return true;
				}
			}
			return false;
		}
	}
	
	@Override
	public String obtenerParalelosDisponibles(String codigoAsignatura) {
		Asignatura asig = asignaturas.getAsignaturaAt(asignaturas.indexOf(codigoAsignatura));
		ListaParalelos paralelos = asig.getParalelos();
		String salida = "";
		for (int i = 0; i < paralelos.getCantParalelos(); i++) {
			Paralelo paral = paralelos.getParaleloAt(i);
			ListaAlumnos inscritos = paral.getAlumnosInscritos();
			int cupos = 100 - inscritos.getCantAlumnos();
			if (i > 0) {
				salida += "- Paralelo " + paral.getNumero() + " - Cupos: " + cupos + "\n";
			}
		}
		return salida.trim();
	}
	
	private int calcularCreditosAlumno(Alumno alum) {
		ListaParalelos inscritas = alum.getAsignaturasInscritas();
		int creditos = 0;
		for (int i = 0; i < inscritas.getCantParalelos(); i++) {
			Paralelo paral = inscritas.getParaleloAt(i);
			Asignatura asig = paral.getAsignatura();
			creditos += asig.getCreditos();
		}
		return creditos;
	}

	@Override
	public boolean verificarCreditos(String correoAlumno, String codigoAsignatura,
			int numero) {
		Alumno alum = alumnos.getAlumnoAt(alumnos.indexOf(correoAlumno));
		Asignatura asig = asignaturas.getAsignaturaAt(asignaturas.indexOf(codigoAsignatura));
		ListaParalelos paralelos = asig.getParalelos();
		int iParalelo = paralelos.indexOf(numero);
		if (iParalelo == -1) {  
			throw new NullPointerException("El paralelo no existe");
		}
		else {
			int creditos = calcularCreditosAlumno(alum);
			if (creditos + asig.getCreditos() > 40) {
				return false;
			}
			else {
				return true;
			}
		}
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
	public boolean chequarAsignaturasDictadas(String correoProfesor) {
		Profesor prof = profesores.getProfesorAt(profesores.indexOf(correoProfesor));
		ListaParalelos paralelos = prof.getParalelos();
		if (paralelos.getCantParalelos() == 0) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public String obtenerParalelosProfesor(String correoProfesor) {
		Profesor profe = profesores.getProfesorAt(profesores.indexOf(correoProfesor));
		String salida = "";
		ListaParalelos paralelos = profe.getParalelos();
		for (int i = 0; i < paralelos.getCantParalelos(); i++) {
			Paralelo paral = paralelos.getParaleloAt(i);
			Asignatura asig = paral.getAsignatura();
			salida += "- " + asig.getNombre() + " (" + asig.getCodigo() + ") - Paralelo "
					+ paral.getNumero() + "\n";
		}
		return salida.trim();
	}

	@Override
	public String obtenerAlumnosInscritos(String codigoAsignatura, int numeroParalelo) {
		Asignatura asig = asignaturas.getAsignaturaAt(asignaturas.indexOf(codigoAsignatura));
		if (asig == null) {
			throw new NullPointerException("La asigntura no existe");
		}
		else {
			ListaParalelos paralelos = asig.getParalelos();
			Paralelo paral = paralelos.getParaleloAt(paralelos.indexOf(numeroParalelo));
			if (paral == null) {
				throw new NullPointerException("El paralelo no existe");
			}
			else {
				String salida = "";
				ListaAlumnos inscritos = paral.getAlumnosInscritos();
				for (int i = 0; i < inscritos.getCantAlumnos(); i++) {
					Alumno alum = inscritos.getAlumnoAt(i);
					salida += "- " + alum.getCorreo() + "\n";
				}
				return salida.trim();
			}
		}
	}
	
	@Override
	public boolean ingresarNota(String correoAlumno, String codigoAsignatura, double nota) {
		int index = alumnos.indexOf(correoAlumno);
		Alumno alumno = alumnos.getAlumnoAt(index);
		if (alumno == null) {
			throw new NullPointerException("Alumno no registrado");    
		} else {
			Asignatura asig = asignaturas.getAsignaturaAt(asignaturas.indexOf(codigoAsignatura));
			ListaNotas notasAlum = alumno.getAsignaturasCursadas();
			ListaParalelos inscritasAlum = alumno.getAsignaturasInscritas();
			NotaFinal notaFinal = new NotaFinal(nota, asig);
			boolean ingreso = notasAlum.ingresarNota(notaFinal);
			if (ingreso) {
				inscritasAlum.eliminarParalelo(asig);
				if (notaFinal.getNota() >= 3.95) {
					alumno.setCreditos(alumno.getCreditos() + asig.getCreditos());
				}
				return true;
			}
			else return false;
		}
	}

	@Override
	public String obtenerInfoAlumnosEgresados() {
		String salida = "";
		for (int i = 0; i < alumnos.getCantAlumnos(); i++) {
			Alumno alum = alumnos.getAlumnoAt(i);
			boolean estaEgresado = true;
			ListaNotas notas = alum.getAsignaturasCursadas();
			if (notas.getCantNotas() == asignaturas.getCantAsignaturas()) {
				for (int j = 0; j < notas.getCantNotas(); j++) {
					NotaFinal nota = notas.getNotaAt(j);
					if (nota.getNota() < 3.95) {
						estaEgresado = false;
					}
				}
			}
			if (estaEgresado) {
				salida += alum.getRut() + "\n";
				alumnos.eliminarAlumno(alum.getCorreo());
			}
		}
		return salida;
	}

	@Override
	public String obtenerInfoEstudiantes() {
		String salida = "";
		for (int i = 0; i < alumnos.getCantAlumnos(); i++) {
			Alumno alum = alumnos.getAlumnoAt(i);
			String rut = alum.getRut();
			String correo = alum.getCorreo();
			int nivel = alum.getNivel();
			String contra = alum.getContrasena();
			salida += rut + "," + correo + "," + nivel + "," + contra + "\n";
			ListaNotas cursadas = alum.getAsignaturasCursadas();
			salida += cursadas.getCantNotas() + "\n";
			for (int j = 0; j < cursadas.getCantNotas(); j++) {
				NotaFinal nota = cursadas.getNotaAt(j);
				Asignatura asig = nota.getAsignatura();
				salida += asig.getCodigo() + "," + nota.getNota() + "\n";
			}
			ListaParalelos inscritas = alum.getAsignaturasInscritas();
			salida += inscritas.getCantParalelos();
			for (int j = 0; j < inscritas.getCantParalelos(); j++) {
				Paralelo paral = inscritas.getParaleloAt(j);
				Asignatura asig = paral.getAsignatura();
				salida += asig.getCodigo() + "," + paral.getNumero() + "\n";
			}
		}
		return salida.trim();
	}
}
