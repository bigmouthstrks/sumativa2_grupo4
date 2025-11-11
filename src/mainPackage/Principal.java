package mainPackage;

import java.util.Scanner;


public class Principal {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n===== MENÚ BIBLIOTECA =====\n");
            System.out.println("  1. Crear libro");
            System.out.println("  2. Eliminar libro");
            System.out.println("  3. Solicitar préstamo de libro");
            System.out.println("  4. Actualizar cantidad disponible");
            System.out.println("  5. Crear Usuario");
            System.out.println("  6. Editar Usuario");
            System.out.println("  7. Eliminar Usuario");
            System.out.println("  8. Salir\n");
            System.out.print("Seleccione una opción: ");

            while (!sc.hasNextInt()) {
                System.out.println("Debe ingresar un número.");
                sc.next();
            }

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
//                    System.out.println("Ingrese ISBN:");
//                    int isbn = sc.nextInt(); sc.nextLine();
//                    System.out.println("Ingrese título:");
//                    String titulo = sc.nextLine();
//                    System.out.println("Ingrese autor:");
//                    String autor = sc.nextLine();
//                    System.out.println("Ingrese cantidad:");
//                    int cantidad = sc.nextInt(); sc.nextLine();
//                    System.out.println("Ingrese disponibles:");
//                    int disponibles = sc.nextInt(); sc.nextLine();
//                    System.out.println("Ingrese nombre de imagen:");
//                    String imagen = sc.nextLine();

//                    Libro.crearLibro(isbn, titulo, autor, cantidad, disponibles, imagen);
                    break;

                case 2:
//                    System.out.println("Ingrese ISBN del libro a eliminar:");
//                    int isbnEliminar = sc.nextInt();
//                    Libro.eliminarLibro(isbnEliminar);
                    break;

                case 3:
//                    Prestamo.realizarPrestamo();
                    break;

                case 4:
                    System.out.println("Funcionalidad 4 pendiente...");
                    break;
                    
                case 5:
                	System.out.println("========== Crear usuario ==========\n");
                	System.out.println("Ingrese su rut: ");
                	String rut = sc.nextLine();
                	while(!Usuario.isValidRut(rut)) {
                		System.out.println("Por favor ingrese un rut válido en formato '12345678-9' (sin puntos y con guión).");
                		System.out.println("Ingrese rut:");
                    	rut = sc.nextLine();
                	}
                	
                	System.out.println("Ingrese su nombre completo: ");
                	String nombreCompleto = sc.nextLine();
                	while(nombreCompleto.isEmpty()) {
                		System.out.println("Por favor ingrese un nombre válido (diferente a vacío).");
                		System.out.println("Ingrese su nombre: ");
                		nombreCompleto = sc.nextLine();
                	}

                	System.out.println("Ingrese su género: ");
                	String genero = sc.nextLine().toUpperCase();
                	while(!genero.equals("M") && !genero.equals("F")) {
                		System.out.println("Por favor ingrese 'M' o 'F' como opciones válidas.");
                		System.out.println("Ingrese su género: ");
                    	genero = sc.nextLine().toUpperCase();
                	}
                	
                	System.out.println("Ingrese su carrera: ");
                	String carrera = sc.nextLine();
                	while(carrera.isEmpty()) {
                		System.out.println("Por favor ingrese una carrera válida (diferente a vacío).");
                		System.out.println("Ingrese su carrera: ");
                		nombreCompleto = sc.nextLine();
                	}
                	
                	System.out.println("¿Tiene préstamo? (ingrese 'S' para si o 'N' para no) ");
                	String prestamo = sc.nextLine().toUpperCase();
                	while(!prestamo.equals("S") && !prestamo.equals("N")) {
                		System.out.println("Por favor ingrese una opción válida.");
                		System.out.println("¿Tiene préstamo? (ingrese 's' para si o 'n' para no) ");
                		prestamo = sc.nextLine().toUpperCase();
                	}
                	
                	String isbn;
                	if(prestamo.equals("S")) {
                		System.out.println("Ingrese ISBN del libro prestado: ");
                		isbn = sc.nextLine().toUpperCase();
                		while(isbn.isEmpty()) {
                			System.out.println("Ingrese un ISBN válido: ");
                    		isbn = sc.nextLine().toUpperCase();
                		}
                	} else {
                		isbn = "0";
                	}
                	
                	Gender gender = genero == "M" ? Gender.M : Gender.F;
                	
                	Usuario newUser = Usuario.create(rut, nombreCompleto, gender, carrera, isbn);
                	
                	if(newUser != null) {
                		System.out.println("¡Usuario creado exitosamente!");
                		System.out.println(newUser.toString());
                	} else {
                		System.out.println("El usuario ya existe. No se agregaron nuevos usuarios.");
                	}
                	
                	break;
                
                case 6:
                	System.out.println("========== Editar usuario ==========\n");                	
                	break;
                	
                case 8:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

        } while (opcion != 8);

        sc.close();
    }
}