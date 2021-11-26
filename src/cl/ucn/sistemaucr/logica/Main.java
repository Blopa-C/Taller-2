package cl.ucn.sistemaucr.logica;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		SistemaUCR sistema = new SistemaUCRImpl();
		Scanner scan = new Scanner(System.in);
		
		leerArchivoAsignaturas(sistema);
	}

	private static void leerArchivoAsignaturas(SistemaUCR sistema) throws IOException {
		Scanner scan = new Scanner(new File("asignaturas.txt"));
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
				sistema.ingresarAsignaturaObligatoria(codigo, nombre, creditos, nivel, prerrequisitos);
			}
			else {
				int cantPrerreq = Integer.parseInt(partes[4]);
				sistema.ingresarAsignaturaOpcional(codigo, nombre, creditos, cantPrerreq);
			}
		}
		scan.close();
	}
}
