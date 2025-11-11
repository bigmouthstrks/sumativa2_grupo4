package mainPackage;

import java.util.*;

public class Usuario {
	private String rut;
	private String nombreCompleto;
	private Gender genero;
	private String carrera;
	private String prestamo;

	protected Usuario(String rut, String nombreCompleto, Gender genero, String carrera, String prestamo) {
		this.rut = rut;
		this.nombreCompleto = nombreCompleto;
		this.genero = genero;
		this.carrera = carrera;
		this.prestamo = prestamo;
	}

	/* Función que devuelve una instancia de la clase Usuario si los parámetros son correctos, si no, devuelve null */
	public static Usuario create(String rut, String nombreCompleto, Gender genero, String carrera, String prestamo) {
		Usuario user = new Usuario(rut, nombreCompleto, genero, carrera, prestamo);
		
		if(!Utils.alreadyExists(File.USUARIOS, user.rut)) {
			String userAsString = user.toString();
			Utils.saveToFile(File.USUARIOS, userAsString);
			
			return user;
		} else {
			return null;
		}
	}
	
	public static void edit(String rut, String nombreCompleto, Gender genero, String carrera, String prestamo) {
		if(isValidRut(rut) && isValidLoan(prestamo)) {
			Usuario newUser = new Usuario(rut, nombreCompleto, genero, carrera, prestamo);
			Utils.deleteRecordById(File.USUARIOS, rut);
			Utils.saveToFile(File.USUARIOS, newUser.toString());
		}
	}
	
	public static void delete(String rut) {
		ArrayList<Usuario> existingUsers = getUsers();
		
		for(Usuario user: existingUsers) {
			if(user.rut.equals(rut)) {
				Utils.deleteRecordById(File.USUARIOS, rut);
			}
		}
	}
	
	public static ArrayList<Usuario> getUsers() {
		String fileContents = Utils.readFromFile(File.USUARIOS);
		ArrayList<Usuario> users = fromText(fileContents);
		
		return users;
	}
	
	public static Usuario getUserByRUT(String rut) {
		String usersAsText = Utils.readFromFile(File.USUARIOS);
		ArrayList<Usuario> users = fromText(usersAsText);
		
		Usuario foundUser = null;
		
		for(Usuario user: users) {
			if(user.rut.equals(rut)) {				
				foundUser = user;
				break;
			}
		}
		return foundUser;
	}
	
    @Override
    public String toString() {
        return rut + ";" + nombreCompleto + ";" + genero + ";" + carrera + ";" + prestamo;
    }
	
    private static ArrayList<Usuario> fromText(String text) {
        ArrayList<Usuario> list = new ArrayList<>();
        String[] lines = text.split("\\r?\\n");

        for (String line: lines) {
            if (line.trim().isEmpty()) {
            	continue;
            }
            String[] datos = line.split("\\|");
            if (datos.length == 4) {
                Usuario user = new Usuario(datos[0], datos[1], Gender.valueOf(datos[2]), datos[3], datos[4]);
                list.add(user);
            }
        }
        return list;
    }
    
	protected static boolean isValidLoan(String loan) {
		if(loan.equals("0") || !loan.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/* Función que devuelve true si el rut pasado como argumento es válido y false si no lo es. */
	public static boolean isValidRut(String rut) {
		rut = rut.replace(".", "");
		rut = rut.toUpperCase();

		String[] parts = rut.split("-");
		String inputDV = Integer.parseInt(parts[1]) == 10 ? "K" : parts[1];
		String numericPart = parts[0];
		String[] numericPartAsArray = numericPart.split("");
		Integer numericPartAsInt = 0;
		List<Integer> sequenceNumbers = List.of(2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 7);
		
		for (int i = numericPartAsArray.length - 1, j = 0; i >= 0 && j < numericPartAsArray.length; i--, j++) {
			Integer num = Integer.parseInt(numericPartAsArray[i]) * sequenceNumbers.get(j);
			numericPartAsInt += num;
		}
		
		int calculatedDV = 11 - numericPartAsInt % 11;
		
		if(calculatedDV == 10) {
			if(inputDV == "K") {
				return true;
			} else {
				return false;
			}
		} else {
			if(Integer.parseInt(inputDV) == calculatedDV) {
				return true;
			} else {
				return false;
			}
		}
	}
}