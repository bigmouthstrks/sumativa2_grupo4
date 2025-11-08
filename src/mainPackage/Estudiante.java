package mainPackage;

public class Estudiante extends Usuario {
	String carrera;

	protected Estudiante(String nombreCompleto, String rut, Gender genero, String prestamo, String carrera) {
		super(nombreCompleto, rut, genero, prestamo);
		this.carrera = carrera;
	}
}
