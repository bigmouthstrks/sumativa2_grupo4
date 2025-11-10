package mainPackage;

public class Docente extends Usuario {
	private AcademicGrade grado;

	public Docente(String nombre, String rut, Gender genero, String carrera, String prestamo, AcademicGrade grado) {
		super(nombre, rut, genero, carrera, prestamo);
		this.grado = grado;
	}
	
	public AcademicGrade getGrado() {
		return grado;
	}
}