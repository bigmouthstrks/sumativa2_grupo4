package mainPackage;

import java.util.*;

public class Principal {
	private static List<Usuario> users = new ArrayList<>();
	
	public static void main(String args[]) {
		
	}
	
	public static void addUser(Usuario newUser) {
		boolean userAlreadyExists = false;
		
		for(Usuario existingUser: users) {
			if(newUser.rut == existingUser.rut) {
				userAlreadyExists = true;
				break;
			}
		}
		
		if(userAlreadyExists) {
			System.out.println("El RUT ya existe. No se agregará el usuario.");
		} else {
			users.add(newUser);
			System.out.println("Se ha agregado un nuevo usuario a la lista.");
		}
	}
	
	public static Usuario createUser(String name, String rut, Gender gender, String loan) {
		return Usuario.create(name, rut, gender, loan);
	}
	
	public static void deleteUserByRUT(String rut) {
		for(Usuario existingUser: users) {
			if(rut == existingUser.rut) {
				users.remove(existingUser);
				break;
			}
		}
	}
}