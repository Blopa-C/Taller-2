package cl.ucn.sistemaucr.logica;

public interface SistemaUCR {
	/***
	 * A student is entered into the system
	 * @param rut
	 * @param correo
	 * @param nivel
	 * @param contrasena
	 * @return a true if the entry 
	 * was successful and a false if the student
	 * could not be entered
	 */
	boolean ingresarAlumno(String rut, String correo, int nivel, String contrasena);
	/***
	 * A compulsory subject is entered into the system
	 * @param codigo
	 * @param nombre
	 * @param creditos
	 * @param nivel
	 * @param prerrequisitos
	 * @return a true if the subject was 
	 * entered correctly and a false if it was 
	 * not entered correctly
	 */
	boolean ingresarAsignaturaObligatoria(String codigo, String nombre, int creditos,
			int nivel, String[] prerrequisitos);
	/***
	 * An optional subject is entered into the system
	 * @param codigo
	 * @param nombre
	 * @param creditos
	 * @param creditosPrerrequisito
	 * @return a true if 
	 * I entered correctly and a false
	 * if it could not be entered
	 */
	boolean ingresarAsignaturaOpcional(String codigo, String nombre, int creditos,
			int creditosPrerrequisito);
	/***
	 * A teacher is entered into the system
	 * @param rut
	 * @param correo
	 * @param contrasena
	 * @param salario
	 * @return a true if 
	 * I entered correctly and a false if it could not be entered
	 */
	boolean ingresarProfesor(String rut, String correo, String contrasena, int salario);
	/***
	 * A parallel is entered into the system, associating a teacher and subject, 
	 * there must be both the subject and the teacher
	 * @param numero
	 * @param codigo
	 * @param rutProfesor
	 * @return true if the entry was correct and false 
	 * if the entry was not correct
	 */
	boolean ingresarParalelo(int numero, String codigo, String rutProfesor);
	/***
	 * A student is associated with a grade and it is added 
	 * to the student's grade list, the subject must exist
	 * @param correoAlumno
	 * @param codigoAsignatura
	 * @param nota
	 * @return true if the entry is correct 
	 * and false if it is not entered correctly
	 */
	boolean asociarNotaAlumno(String correoAlumno, String codigoAsignatura, double nota);
	/***
	 * A student is associated with a parallel, 
	 *there must be the subject and the parallel 
	 * @param correoAlumno
	 * @param codigoAsignatura
	 * @param numero
	 * @return true if the entry is correct
	 * and false if the entry was not made correctly
	 */
	boolean asociarParaleloAlumno(String correoAlumno, String codigoAsignatura, int numero);
	/***
	 * The student's password is validated, the student must exist
	 * @param correo
	 * @param contrasena
	 * @return If the student exists and the password entered matches 
	 * that of the student's record, it returns true, otherwise it 
	 * returns false
	 */
	boolean validarAlumno(String correo, String contrasena);
	/***
	 *The teacher's password is validated with that 
	 * of the system registry, the teacher must exist
	 * @param correo
	 * @param contrasena
	 * @return If the teacher exists and the password entered matches 
	 * that of the teacher's record, it returns true, otherwise it 
	 * returns false
	 */
	boolean validarProfesor(String correo, String contrasena);
	/***
	 * The data of the subjects available to the student are obtained
	 * @param correoAlumno
	 * @return returns a String with the data of the subjects
	 */
	String obtenerAsignaturasDisponibles(String correoAlumno);
	/***
	 * The data of the parallels 
	 * available in the entered subject are obtained
	 * @param codigoAsignatura
	 * @return Returns a String with the data of 
	 * the parallels available in the subject
	 */
	String obtenerParalelosDisponibles(String codigoAsignatura);
	/***
	 * verifies if the student meets the credit 
	 * requirement to enroll a course
	 * @param correoAlumno
	 * @param codigoAsignatura
	 * @param numero
	 * @return a true if you have credit 
	 * availability and a false if you do not have enough credits
	 */
	boolean verificarCreditos(String correoAlumno, String codigoAsignatura, int numero);
	/***
	 * check if the student has a subject registered
	 * @param correoAlumno
	 * @return a false if you do not have 
	 * enrolled subjects and a true if you have any enrolled
	 */
	boolean chequearAsignaturasInscritas(String correoAlumno); // Nuevo
	/***
	 * the data of the enrolled subjects of the entered student is obtained
	 * @param correoAlumno
	 * @return the data of the enrolled subjects
	 */
	String obtenerAsignaturasInscritas(String correoAlumno);
	/***
	 * A student's registered subject is eliminated 
	 * and all data is updated, the subject must exist
	 * @param correoAlumno
	 * @param codigoAsignatura
	 * @return true if the delete was successful and false otherwise
	 */
	boolean eliminarAsignaturaInscrita(String correoAlumno, String codigoAsignatura);
	/***
	 * It is checked if the teacher has taught subjects
	 * @param correoProfesor
	 * @return a true if it has taught subjects
	 *  and a false if it does not have any
	 */
	boolean chequarAsignaturasDictadas(String correoProfesor);
	/***
	 * The data of the parallels dictated by a 
	 * teacher in the semester are obtained
	 * @param correoProfesor
	 * @return a string with the data 
	 * of the subjects taught by the teacher
	 */
	String obtenerParalelosProfesor(String correoProfesor);
	/***
	 * The data of the students enrolled in the parallel entered is obtained, 
	 * the subject and the parallel must exist
	 * @param codigoAsignatura
	 * @param numeroParalelo
	 * @return a string with the data of the students enrolled in the parallel
	 */
	String obtenerAlumnosInscritos(String codigoAsignatura, int numeroParalelo);
	/***
	 * A note is entered for the student,the student must exist
	 * @param correoAlumno
	 * @param codigoAsignatura
	 * @param nota
	 * @return returns true if input is correct, false otherwise
	 */
	boolean ingresarNota(String correoAlumno, String codigoAsignatura, double nota); 
	/***
	 * the data of the graduated students are obtained
	 * @return a string with the data of the graduated students
	 */
	String obtenerInfoAlumnosEgresados();
	/***
	 * Information is obtained from the students
	 * @return a string with the students' data
	 */
	String obtenerInfoEstudiantes();
	/***
	 * check if there is any parallel with quota
	 * @param codigoAsignatura
	 * @return false if there are no spots 
	 * available and true if there is at least one spot left
	 */
	boolean chequearCuposParalelos(String codigoAsignatura); 
}
