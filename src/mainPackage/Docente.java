package mainPackage;

public class Docente extends Usuario {
	String profesion;
	AcademicGrade grado;

	protected Docente(String nombreCompleto, String rut, Gender genero, String prestamo) {
		super(nombreCompleto, rut, genero, prestamo);
		
	}
}