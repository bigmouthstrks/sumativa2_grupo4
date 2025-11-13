package mainPackage;

public class Estudiante extends Usuario {
	private String carrera;

	protected Estudiante(String rut, String nombreCompleto, Gender genero, String prestamo, String carrera) {
		super(rut, nombreCompleto, genero, prestamo);
		this.carrera = carrera;
	}
	
	public static Estudiante create(String rut, String nombreCompleto, Gender genero, String prestamo, String carrera) {
		Estudiante estudiante = new Estudiante(rut, nombreCompleto, genero, prestamo, carrera);
		
		if(!Utils.alreadyExists(File.USUARIOS, estudiante.rut)) {
			String userAsString = estudiante.toString();
			Utils.saveToFile(File.USUARIOS, userAsString);
			
			return estudiante;
		} else {
			return null;
		}
	}
	
	@Override
    public String toString() {
        return rut + ";" + nombreCompleto + ";" + genero + ";" + prestamo + ";" + carrera + ";" + "ESTUDIANTE";
    }
	
	public String getCarrera() {
		return carrera;
	}
}
