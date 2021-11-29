package cl.ucn.sistemaucr.logica;

import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

	public static void main(String[] args) throws IOException {
		SistemaUCR sistema = new SistemaUCRImpl();
		Scanner scan = new Scanner(System.in);
		/*
		 * leerArchivoAsignaturas(sistema);
		 * leerArchivoProfesores(sistema);
		 * leerArchivoParalelos(sistema);
		 * leerArchivoEstudiantes(sistema);
		 */
		iniciarSesion(sistema, scan);
	}

	private static void iniciarSesion(SistemaUCR sistema, Scanner scan) {
		System.out.println(("\n>--- INICIO DE SESION ---<\n"));
		System.out.print("Correo: ");
		String correo = scan.nextLine();
		System.out.print("Contraseña: ");
		String contrasena = scan.nextLine();
		try {
			if (correo.equals("Admin") && contrasena.equals("GHI_789")) {
				System.out.println("aqui admin");
				// cerrarSemestre();
			}
			else if (correo.split("@")[1].equals("alumnos.ucn.cl")) {
				try {
					if (sistema.validarAlumno(correo, contrasena)) {
						int op = ingresarFecha(scan);
						switch (op) {
						case 1:
							menuInicioSemestreAlumno(sistema, scan, correo);
							break;
						case 2:
							break;
						case 3:
							break;
						case 4:
							break;
						case 5:
							break;
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
			else if (correo.split("@")[1].equals("ucn.cl")) {
				try {
					if (sistema.validarProfesor(correo, contrasena)) {
						// ingresarFecha();
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
			System.out.println("Correo invalido");
		}
		iniciarSesion(sistema, scan);
	}

	private static void menuInicioSemestreAlumno(SistemaUCR sistema, Scanner scan,
			String correo) {
		System.out.println("\n>--- MENU INICIO DE SEMESTRE ---<\n");
		System.out.println("[1] Inscripcion de asignaturas");
		System.out.println("[2] Eliminacion de asignaturas");
		System.out.print("Opcion: ");
		String opcion = scan.nextLine();
		switch (opcion) {
		case "1":
			inscribirAsignatura(sistema, scan, correo);
			break;
		case "2":
			eliminarAsignatura(sistema, scan, correo);
			break;
		default:
			System.out.println("\n** Opcion invalida. Intente nuevamente **");
			Main.menuInicioSemestreAlumno(sistema, scan, correo);
		}
	}
	
	private static void inscribirAsignatura(SistemaUCR sistema, Scanner scan, String correo) {
		System.out.println("\n>--- INSCRIPCION DE ASIGNATURAS<---\n");
		
	}

	private static int ingresarFecha(Scanner scan) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Ingrese la fecha (dd/mm/aaaa): ");
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
			String[] partes = scan.nextLine().split("");
			int cont = 0;
			String rut = partes[cont++];
			String correo = partes[cont++];
			int nivel = Integer.parseInt(partes[cont++]);
			String contrasena = partes[cont++];
			if (!sistema.ingresarAlumno(rut, correo, nivel, contrasena)) {
				System.out.println("No hay espacio para mas estudiantes en el sistema");
				break outer;
			}
			int cantAsig = Integer.parseInt(partes[cont++]);
			for (int i = 0; i < cantAsig; i++) {
				String codigo = partes[cont++];
				double nota = Double.parseDouble(partes[cont++]);
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
			cantAsig = Integer.parseInt(partes[cont++]);
			for (int i = 0; i < cantAsig; i++) {
				String codigo = partes[cont++];
				int numParal = Integer.parseInt(partes[cont++]);
				try {
					sistema.asociarNotaAlumno(correo, codigo, numParal);
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
			String[] partes = scan.nextLine().split("");
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
			String[] partes = scan.nextLine().split("");
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
			String[] partes = scan.nextLine().split("");
			String codigo = partes[0];
			String nombre = partes[1];
			int creditos = Integer.parseInt(partes[2]);
			String tipo = partes[3];
			if (tipo.equals("obligatoria")) {
				int nivel = Integer.parseInt(partes[4]);
				int cantAsig = Integer.parseInt(partes[5]);
				String[] prerrequisitos = new String[cantAsig];
				int j = 0;
				for (int i = 6; i < i + cantAsig; i++) {
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
