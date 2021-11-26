package cl.ucn.sistemaucr.logica;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		SistemaUCR sistema = new SistemaUCRImpl();
		Scanner scan = new Scanner(System.in);
		
		leerArchivoAsignaturas(sistema);
		leerArchivoProfesores(sistema);
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
