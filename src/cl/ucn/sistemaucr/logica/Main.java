package cl.ucn.sistemaucr.logica;

import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

	public static void main(String[] args) throws IOException {
		SistemaUCR sistema = new SistemaUCRImpl();
		Scanner scan = new Scanner(System.in);
		
		  leerArchivoAsignaturas(sistema);
		  leerArchivoProfesores(sistema);
		  leerArchivoParalelos(sistema);
		  leerArchivoEstudiantes(sistema);
		 
		iniciarSesion(sistema, scan);
	}

	private static void iniciarSesion(SistemaUCR sistema, Scanner scan) throws IOException {
		System.out.println(("\n>--- INICIO DE SESION ---<\n"));
		System.out.print("Correo: ");
		String correo = scan.nextLine();
		System.out.print("Contraseña: ");
		String contrasena = scan.nextLine();
		try {
			if (correo.equals("Admin") && contrasena.equals("GHI_789")) {
				int op = ingresarFecha(scan);
				switch (op) {
				case 1:
				case 2:
				case 3:
					System.out.println("\n** No hay acciones disponibles **");
					System.exit(0);
				case 4:
					cerrarSemestre(sistema);
				case 5:
					System.out.println("\n** Disfrute sus vacaciones **");
					System.exit(0);
				}
			}
			else if (correo.split("@")[1].equals("alumnos.ucn.cl")) {
				try {
					if (sistema.validarAlumno(correo, contrasena)) {
						int op = ingresarFecha(scan);
						switch (op) {
						case 1:
							menuInicioSemestreAlumno(sistema, scan, correo);
						case 2:
							menuMitadSemestreAlumno(sistema, scan, correo);
						case 3:
						case 4:
							System.out.println("\n** No hay acciones disponibles **");
							System.exit(0);
						case 5:
							System.out.println("\n** Disfrute sus vacaciones **");
							System.exit(0);
						}
					}
					else {
						System.out.println("\n** Contraseña incorrecta **");
					}
				}
				catch (NullPointerException e) {
					System.out.println("\n** " + e.getMessage() + " **");
				}
			}
			else if (correo.split("@")[1].equals("ucn.cl")) {
				try {
					if (sistema.validarProfesor(correo, contrasena)) {
						int op = ingresarFecha(scan);
						switch (op) {
						case 1:
							menuInicioSemestreProfesor(sistema, scan, correo);
						case 2:
							System.out.println("\n** No hay acciones disponibles **");
							System.exit(0);
						case 3:
							menuFinalSemestreProfesor(sistema, scan, correo);
							break;
						case 4:
							System.out.println("\n** No hay acciones disponibles **");
							System.exit(0);
						case 5:
							System.out.println("\n** Disfrute sus vacaciones **");
							System.exit(0);
						}
					}
					else {
						System.out.println("Contraseña incorrecta");
					}
				}
				catch (NullPointerException e) {
					System.out.println("\n** " + e.getMessage() + " **");
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("\n** Correo invalido **");
		}
		iniciarSesion(sistema, scan);
	}

	private static void menuFinalSemestreProfesor(SistemaUCR sistema, Scanner scan, 
			String correo) throws IOException {
		System.out.println("\n>--- MENU FINAL DE SEMESTRE ---<\n");
		System.out.println("[1] Ingresar nota final");
		System.out.println("[2] Salir");
		System.out.print("Opcion: ");
		String opcion = scan.nextLine();
		switch (opcion) {
		case "1":
			ingresarNotaFinal(sistema, scan, correo);
			break;
		case "2":
			Main.sobreescribirArchivoEstudiantes(sistema);
			System.exit(0);
		default:
			System.out.println("\n** Opcion invalida. Intente nuevamente **");
		}
		Main.menuFinalSemestreProfesor(sistema, scan, correo);
	}

	private static void ingresarNotaFinal(SistemaUCR sistema, Scanner scan, String correo) {
		System.out.println("\n>--- INGRESAR NOTA FINAL ---<\n");
		if (!sistema.chequarAsignaturasDictadas(correo)) {
			System.out.println("** No dicta asignaturas este semestre **");
		}
		else {
			System.out.println("* Paralelos dictados:\n");
			System.out.println(sistema.obtenerParalelosProfesor(correo));
			System.out.print("Codigo de la asignatura: ");
			String codigo = scan.nextLine();
			System.out.print("Numero de paralelo: ");
			int numero = Integer.parseInt(scan.nextLine());
			try {
				System.out.println("\n* Alumnos inscritos:\n");
				System.out.println(sistema.obtenerAlumnosInscritos(codigo, numero));
				System.out.println("\nElija un alumno: ");
				String correoAlumno = scan.nextLine();
				try {
					System.out.print("Nota: ");
					double nota = Double.parseDouble(scan.nextLine());
					if (sistema.ingresarNota(correoAlumno, codigo, nota)) {
						System.out.println("\n** Nota ingresada correctamente! **");
					}
					else {
						System.out.println("\n** Alumno no inscrito en este paralelo **");
					}
				}
				catch (NullPointerException e) {
					System.out.println("\n** " + e.getMessage() + " **");
				}
			}
			catch (NullPointerException e) {
				System.out.println("\n** " + e.getMessage() + " **");
			}
		}
	}

	private static void menuInicioSemestreProfesor(SistemaUCR sistema, 
			Scanner scan, String correo) throws IOException {
		System.out.println("\n>--- MENU INICIO DE SEMESTRE ---<\n");
		System.out.println("[1] Chequear alumnos");
		System.out.println("[2] Salir");
		System.out.print("Opcion: ");
		String opcion = scan.nextLine();
		switch (opcion) {
		case "1":
			Main.chequeoAlumnos(sistema, scan, correo);
			break;
		case "2":
			Main.sobreescribirArchivoEstudiantes(sistema);
			System.exit(0);
		default:
			System.out.println("\n** Opcion invalida. Intente nuevamente **");
		}
		Main.menuInicioSemestreProfesor(sistema, scan, correo);
	}

	private static void chequeoAlumnos(SistemaUCR sistema, Scanner scan, String correo) 
			throws IOException {
		System.out.println("\n>--- CHEQUEAR ALUMNOS ---<\n");
		if (!sistema.chequarAsignaturasDictadas(correo)) {
			System.out.println("** No dicta asignaturas este semestre **");
		}
		else {
			System.out.println("* Paralelos dictados:\n");
			System.out.println(sistema.obtenerParalelosProfesor(correo));
			System.out.print("\nCodigo de la asignatura: ");
			String codigo = scan.nextLine();
			System.out.print("Numero de paralelo: ");
			int numero = Integer.parseInt(scan.nextLine());
			try {
				System.out.println("\n* Alumnos inscritos:\n");
				System.out.println(sistema.obtenerAlumnosInscritos(codigo, numero));
			}
			catch (NullPointerException e) {
				System.out.println("/n** " + e.getMessage() + " **");
			}
		}
	}

	private static void menuMitadSemestreAlumno(SistemaUCR sistema, Scanner scan, 
			String correo) throws IOException {
		System.out.println("\n>--- MENU MITAD DE SEMESTRE ---<\n");
		System.out.println("[1] Eliminar asignatura");
		System.out.println("[2] Salir");
		System.out.print("Opcion: ");
		String opcion = scan.nextLine();
		switch (opcion) {
		case "1":
			eliminarAsignatura(sistema, scan, correo);
			break;
		case "2":
			Main.sobreescribirArchivoEstudiantes(sistema);
			System.exit(0);
		default:
			System.out.println("\n** Opcion invalida. Intente nuevamente **");
		}
		Main.menuMitadSemestreAlumno(sistema, scan, correo);
	}

	private static void cerrarSemestre(SistemaUCR sistema) throws IOException {
		System.out.println("\n** Cerrando semestre... **");
		FileWriter fw1 = new FileWriter("egresados.txt");
		String infoEgresados = sistema.obtenerInfoAlumnosEgresados();
		fw1.write(infoEgresados);
		fw1.close();
		Main.sobreescribirArchivoEstudiantes(sistema);
		System.exit(0);
	}

	private static void menuInicioSemestreAlumno(SistemaUCR sistema, Scanner scan,
			String correo) throws IOException {
		System.out.println("\n>--- MENU INICIO DE SEMESTRE ---<\n");
		System.out.println("[1] Inscripcion de asignaturas");
		System.out.println("[2] Eliminacion de asignaturas");
		System.out.println("[3] Salir");
		System.out.print("Opcion: ");
		String opcion = scan.nextLine();
		switch (opcion) {
		case "1":
			inscribirAsignatura(sistema, scan, correo);
			break;
		case "2":
			eliminarAsignatura(sistema, scan, correo);
			break;
		case "3":
			Main.sobreescribirArchivoEstudiantes(sistema);
			System.exit(0);
		default:
			System.out.println("\n** Opcion invalida. Intente nuevamente **");
		}
		Main.menuInicioSemestreAlumno(sistema, scan, correo);
	}
	
	private static void eliminarAsignatura(SistemaUCR sistema, Scanner scan, String correo) 
			throws IOException {
		System.out.println("\n>--- ELIMINAR ASIGNATURA ---<\n");
		if (!sistema.chequearAsignaturasInscritas(correo)) {
			System.out.println("** No tienes asignaturas inscritas **");
		}
		else {
			System.out.println("* Asignaturas inscritas:\n");
			System.out.println(sistema.obtenerAsignaturasInscritas(correo));
			System.out.print("\nCodigo de la asignatura que desea eliminar: ");
			String codigo = scan.nextLine();
			try {
				if (sistema.eliminarAsignaturaInscrita(correo, codigo)) {
					System.out.println("\n** Asignatura eliminada! **");
				}
				else {
					System.out.println("\n** No tienes inscrita esta asignatura **");
				}
			}
			catch (NullPointerException e) {
				System.out.println("\n** " + e.getMessage() + " **");
			}
		}
	}

	private static void sobreescribirArchivoEstudiantes(SistemaUCR sistema) throws IOException {
		FileWriter fw = new FileWriter("estudiantes.txt");
		String infoEstudiantes = sistema.obtenerInfoEstudiantes();
		fw.write(infoEstudiantes);
		fw.close();
	}

	private static void inscribirAsignatura(SistemaUCR sistema, Scanner scan, String correo) {
		System.out.println("\n>--- INSCRIBIR ASIGNATURA ---<\n");
		System.out.println("* Asignaturas disponibles:\n");
		System.out.println(sistema.obtenerAsignaturasDisponibles(correo));
		System.out.print("\nCodigo de la asignatura que desea inscribir: ");
		String codigo = scan.nextLine();
		try {
			if (!sistema.chequearCuposParalelos(codigo)) {
				System.out.println("\n** No hay paralelos con cupos disponibles **");
			}
			else {
				System.out.println("\n* Paralelos disponibles:\n");
				System.out.println(sistema.obtenerParalelosDisponibles(codigo));
				System.out.print("\nNumero del paralelo que desea inscribir: ");
				int numero = Integer.parseInt(scan.nextLine());
				try {
					if (!sistema.verificarCreditos(correo, codigo, numero)) {
						System.out.println("\n** Exceso de creditos **");
					}
					else {
						sistema.asociarParaleloAlumno(correo, codigo, numero);
						System.out.println("\n** Paralelo inscrito! **");
					}
				}
				catch (NullPointerException e) {
					System.out.println("\n** " + e.getMessage() + " **");
				}
			}
		}
		catch (NullPointerException e) {
			System.out.println("\n** " + e.getMessage() + " **");
		}
	}

	private static int ingresarFecha(Scanner scan) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.print("Ingrese la fecha (dd/mm/aaaa): ");
		String fecha = scan.nextLine();
		int op = 0;
		try {
			Date dateInput = dateFormat.parse(fecha);
			Date date1 = dateFormat.parse("07/03/2021");
			Date date2 = dateFormat.parse("02/05/2021");
			Date date3= dateFormat.parse("11/07/2021");
			Date dateClosure = dateFormat.parse("26/07/2021");
			if (dateInput.after(date1) && 
					(dateInput.before(date2)) || dateInput.equals(date2)) {
				op = 1; // Inicio de semestre
			}
			else if(dateInput.after(date2) && 
					(dateInput.before(date3) || dateInput.equals(date3))) {
				op =  2; // Mitad de semestre
			}
			else if (dateInput.after(date3) && dateInput.before(dateClosure)) {
				op = 3; // Final de semestre
			}
			else if (dateInput.equals(dateClosure)) {
				op = 4; // Cierre del semestre
			}
			else {
				op = 5; // Fecha fuera de rango
			}
		}
		catch (ParseException ex) {
			System.out.println("Opcion invalida. Intente nuevamente");
			ingresarFecha(scan);
		}
		return op;
	}

	private static void leerArchivoEstudiantes(SistemaUCR sistema) throws IOException {
		Scanner scan = new Scanner(new File("estudiantes.txt"));
		outer:
		while (scan.hasNextLine()) {
			String[] partes = scan.nextLine().split(",");
			String rut = partes[0];
			String correo = partes[1];
			int nivel = Integer.parseInt(partes[2]);
			String contrasena = partes[3];
			if (!sistema.ingresarAlumno(rut, correo, nivel, contrasena)) {
				System.out.println("No hay espacio para mas estudiantes en el sistema");
				break outer;
			}
			int cantAsig = Integer.parseInt(scan.nextLine());
			for (int i = 0; i < cantAsig; i++) {
				partes = scan.nextLine().split(",");
				String codigo = partes[0];
				double nota = Double.parseDouble(partes[1]);
				try {
					if (!sistema.asociarNotaAlumno(correo, codigo, nota)) {
						System.out.print("No hay espacio para mas notas en el alumno");
						continue outer;
					}
				}
				catch (NullPointerException e) {
					System.out.println(e.getMessage());
				}
			}
			cantAsig = Integer.parseInt(scan.nextLine());
			for (int i = 0; i < cantAsig; i++) {
				partes = scan.nextLine().split(",");
				String codigo = partes[0];
				int numParal = Integer.parseInt(partes[1]);
				try {
					sistema.asociarParaleloAlumno(correo, codigo, numParal);
				}
				catch (NullPointerException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		scan.close();
	}

	private static void leerArchivoParalelos(SistemaUCR sistema) throws IOException {
		Scanner scan = new Scanner(new File("paralelos.txt"));
		outer:
		while (scan.hasNextLine()) {
			String[] partes = scan.nextLine().split(",");
			int cont = 0;
			int numero = Integer.parseInt(partes[cont++]);
			String codigo = partes[cont++];
			String rutProfesor = partes[cont++];
			try {
				if (!sistema.ingresarParalelo(numero, codigo, rutProfesor)) {
					System.out.println("No hay espacio para mas paralelos en el sistema");
					break outer;
				}
			}
			catch (NullPointerException e) {
				System.out.println(e.getMessage());
			}
		}
		scan.close();
	}

	private static void leerArchivoProfesores(SistemaUCR sistema) throws IOException {
		Scanner scan = new Scanner(new File("profesores.txt"));
		outer:
		while (scan.hasNextLine()) {
			String[] partes = scan.nextLine().split(",");
			String rut = partes[0];
			String correo = partes[1];
			String contrasena = partes[2];
			int salario = Integer.parseInt(partes[3]);
			if (!sistema.ingresarProfesor(rut, correo, contrasena, salario)) {
				System.out.println("No hay espacio para mas profesores en el sistema");
				break outer;
			}
		}
		scan.close();
	}

	private static void leerArchivoAsignaturas(SistemaUCR sistema) throws IOException {
		Scanner scan = new Scanner(new File("asignaturas.txt"));
		outer:
		while (scan.hasNextLine()) {
			String[] partes = scan.nextLine().split(",");
			String codigo = partes[0];
			String nombre = partes[1];
			int creditos = Integer.parseInt(partes[2]);
			String tipo = partes[3];
			if (tipo.equals("obligatoria")) {
				int nivel = Integer.parseInt(partes[4]);
				int cantAsig = Integer.parseInt(partes[5]);
				String[] prerrequisitos = new String[cantAsig];
				int j = 0;
				for (int i = 6; i < 6 + cantAsig; i++) {
					prerrequisitos[j++] = partes[i];
				}
				if (!sistema.ingresarAsignaturaObligatoria(codigo, nombre, creditos, nivel, 
						prerrequisitos)) {
					System.out.println("No hay espacio para mas asignaturas en el sistema");
					break outer;
				};
			} 
			else {
				int cantPrerreq = Integer.parseInt(partes[4]);
				if (!sistema.ingresarAsignaturaOpcional(codigo, nombre, creditos, cantPrerreq)) {
					System.out.println("No hay espacio para mas asignaturas en el sistema");
					break outer;
				};
			}
		}
		scan.close();
	}
}
