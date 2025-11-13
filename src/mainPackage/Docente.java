package mainPackage;

public class Docente extends Usuario {
	private String profesion;
	private AcademicGrade grado;

	protected Docente(String rut, String nombreCompleto, Gender genero, String prestamo, String profesion, AcademicGrade grado) {
		super(rut, nombreCompleto, genero, prestamo);
		this.profesion = profesion;
		this.grado = grado;
	}
	
	public static Docente create(String rut, String nombreCompleto, Gender genero, String prestamo, String profesion, AcademicGrade grado) {
		Docente docente = new Docente(rut, nombreCompleto, genero, prestamo, profesion, grado);
		
		if(!Utils.alreadyExists(File.USUARIOS, docente.rut)) {
			String userAsString = docente.toString();
			Utils.saveToFile(File.USUARIOS, userAsString);
			
			return docente;
		} else {
			return null;
		}
	}
	
	@Override
    public String toString() {
        return rut + ";" + nombreCompleto + ";" + genero + ";" + prestamo + ";" + profesion + ";" + grado.toString() + ";" + "DOCENTE";
    }
	
	public AcademicGrade getGrado() {
		return grado;
	}
	
	public String getProfesion() {
		return profesion;
	}
}