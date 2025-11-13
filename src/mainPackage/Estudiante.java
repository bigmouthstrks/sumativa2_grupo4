package mainPackage;

import java.util.ArrayList;

public class Estudiante extends Usuario {
	private String carrera;

	protected Estudiante(String rut, String nombreCompleto, Gender genero, Integer prestamo, String carrera) {
		super(rut, nombreCompleto, genero, prestamo);
		this.carrera = carrera;
	}
	
	public static Estudiante create(String rut, String nombreCompleto, Gender genero, Integer prestamo, String carrera) {
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
	
	public static ArrayList<Estudiante> fromText(String text) {
        ArrayList<Estudiante> list = new ArrayList<>();
        if (text == null || text.trim().isEmpty()) return list;

        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            if (line == null) continue;
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.contains(";") ? line.split(";") : line.split("\\|");
            
            for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();

            if (parts.length >= 6) {
                String role = parts[parts.length - 1].toUpperCase();
                if (!"ESTUDIANTE".equals(role)) continue;

                try {
                    String rut = parts[0];
                    String nombre = parts[1];
                    Gender genero = Gender.valueOf(parts[2]);
                    Integer prestamo = Integer.parseInt(parts[3]);
                    String carrera = parts[4];

                    Estudiante e = new Estudiante(rut, nombre, genero, prestamo, carrera);
                    list.add(e);
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
                    System.out.println("⚠️ Línea ESTUDIANTE inválida: \"" + line + "\" -> " + ex.getMessage());
                    continue;
                }
            }
        }
        return list;
    }
}
