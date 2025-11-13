package mainPackage;

import java.util.ArrayList;

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
	
	public static ArrayList<Docente> fromText(String text) {
        ArrayList<Docente> list = new ArrayList<>();
        if (text == null || text.trim().isEmpty()) return list;

        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            if (line == null) continue;
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.contains(";") ? line.split(";") : line.split("\\|");
            for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();

            if (parts.length >= 7) {
                String role = parts[parts.length - 1].toUpperCase();
                if (!"DOCENTE".equals(role)) continue;

                try {
                    String rut = parts[0];
                    String nombre = parts[1];
                    Gender genero = Gender.valueOf(parts[2]);
                    String prestamo = parts[3];
                    String profesion = parts[4];
                    AcademicGrade grado = AcademicGrade.valueOf(parts[5]);

                    Docente d = new Docente(rut, nombre, genero, prestamo, profesion, grado);
                    list.add(d);
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
                    System.out.println("⚠️ Línea DOCENTE inválida: \"" + line + "\" -> " + ex.getMessage());
                    continue;
                }
            }
        }
        return list;
    }
}