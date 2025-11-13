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
                	
                	System.out.println("Tipo de usuario (ingrese 'D' para Docente y 'E' para Estudiante)");
                	String tipo = sc.nextLine().toUpperCase();
                	while(!tipo.equals("D") && !tipo.equals("E")) {
                		System.out.println("Por favor ingrese una opción válida.");
                		System.out.println("Tipo de usuario (ingrese 'D' para Docente y 'E' para Estudiante)");
                		tipo = sc.nextLine().toUpperCase();
                	}
                	
                	Gender gender = genero == "M" ? Gender.M : Gender.F;
                	if(tipo.equals("E")) {
                		System.out.println("Ingrese su carrera: ");
                    	String carrera = sc.nextLine();
                    	while(carrera.isEmpty()) {
                    		System.out.println("Por favor ingrese una carrera válida (diferente a vacío).");
                    		System.out.println("Ingrese su carrera: ");
                    		carrera = sc.nextLine();
                    	}
                    	
                    	Estudiante newEstudiante = Estudiante.create(rut, nombreCompleto, gender, isbn, carrera);
                    	
                    	if(newEstudiante != null) {
                    		System.out.println("¡Estudiante creado exitosamente!");
                    		System.out.println(newEstudiante.toString());
                    	} else {
                    		System.out.println("El estudiante ya existe. No se agregaron nuevos estudiantes.");
                    	}
                	} else {
                		System.out.println("Ingrese su profesión: ");
                    	String profesion = sc.nextLine();
                    	while(profesion.isEmpty()) {
                    		System.out.println("Por favor ingrese una profesión válida (diferente a vacío).");
                    		System.out.println("Ingrese su profesión: ");
                    		profesion = sc.nextLine();
                    	}
                    	
                    	System.out.println("Ingrese su grado académico (ingrese 'D' para Doctor y 'M' para Magister)");
                    	String grado = sc.nextLine().toUpperCase();
                    	while(!grado.equals("D") && !grado.equals("M")) {
                    		System.out.println("Por favor ingrese una opción válida.");
                    		System.out.println("Ingrese su grado académico (ingrese 'D' para Doctor y 'M' para Magister)");
                    		grado = sc.nextLine().toUpperCase();
                    	}
                    	
                    	AcademicGrade gradoAcademico = null;
                    	if (grado.equals("D")) {
                    		gradoAcademico = AcademicGrade.DOCTOR;
                    	} else if (grado.equals("M")) {
                    		gradoAcademico = AcademicGrade.MAGISTER;
                    	} else {
                    		gradoAcademico = null;
                    	}
                    	
                    	Docente newDocente = Docente.create(rut, nombreCompleto, gender, isbn, profesion, gradoAcademico);
                    	if(newDocente != null) {
                    		System.out.println("¡Docente registrado exitosamente!");
                    		System.out.println(newDocente.toString());
                    	} else {
                    		System.out.println("El docente ya existe. No se agregaron nuevos docentes.");
                    	}
                	}
                	
                	break;
                
                case 6:
                	System.out.println("========== Editar usuario ==========\n");
                	System.out.println("Ingrese el RUT usuario a editar: ");
                	String rutEditar = sc.nextLine();
                	while(!Usuario.isValidRut(rutEditar)) {
                		System.out.println("Por favor ingrese un rut válido en formato '12345678-9' (sin puntos y con guión).");
                    	System.out.println("Ingrese el RUT usuario a editar: ");
                    	rutEditar = sc.nextLine();
                	}
                	                	
                	String userType = Usuario.getUserType(rutEditar);
                	
                	if(userType.equals("NN")) {
                		System.out.println("No se encontró el usuario.");
                		break;
                	}
                	
                	String userRecord = Usuario.getUserRecord(rutEditar);
                	
                	System.out.println("Editando el registro: " + userRecord);
                	
                	System.out.println("NOMBRE COMPLETO: ");
                	String nombreEditar = sc.nextLine();
                	while(nombreEditar.isEmpty()) {
                		System.out.println("NOMBRE COMPLETO: ");
                	}
                	
                	System.out.println("GENERO: ");
                	String generoEditar = sc.nextLine().toUpperCase();
                	while(!generoEditar.equals("M") && !generoEditar.equals("F")) {
                		System.out.println("GENERO: ");
                    	generoEditar = sc.nextLine().toUpperCase();
                	}
                	
                	System.out.println("¿TIENE PRESTAMO? ('S'/'N'): ");
                	String prestamoEditar = sc.nextLine().toUpperCase();
                	while(prestamoEditar.isEmpty()) {
                		System.out.println("PRESTAMO: ");
                    	prestamoEditar = sc.nextLine().toUpperCase();
                	}
                	
                	String isbnEditar;
                	if(prestamoEditar.equals("S")) {
                		System.out.println("Ingrese ISBN del libro prestado: ");
                		isbnEditar = sc.nextLine().toUpperCase();
                		while(isbnEditar.isEmpty()) {
                			System.out.println("Ingrese un ISBN válido: ");
                			isbnEditar = sc.nextLine().toUpperCase();
                		}
                	} else {
                		isbnEditar = "0";
                	}
                	
                	Usuario.delete(rutEditar);
                	Gender generoEditado = generoEditar == "M" ? Gender.M : Gender.F;
                	
                	if(userType.equals("D")) {
                		System.out.println("PROFESION: ");
                    	String profesionEditar = sc.nextLine();
                    	while(profesionEditar.isEmpty()) {
                    		System.out.println("PROFESION: ");
                        	profesionEditar = sc.nextLine();
                    	}
                    	
                    	System.out.println("GRADO ACADEMICO ('D'/'M'): ");
                    	String gradoAcademicoEditar = sc.nextLine().toUpperCase();
                    	while(!gradoAcademicoEditar.equals("D") && !gradoAcademicoEditar.equals("M")) {
                    		System.out.println("GRADO ACADEMICO ('D'/'M'): ");
                    		gradoAcademicoEditar = sc.nextLine().toUpperCase();
                    	}
                    	
                    	AcademicGrade grado = null;
                    	if (gradoAcademicoEditar.equals("D")) {
                    		grado = AcademicGrade.DOCTOR;
                    	} else if (gradoAcademicoEditar.equals("M")) {
                    		grado = AcademicGrade.MAGISTER;
                    	} else {
                    		grado = null;
                    	}
                    	
                    	Docente newDocente = Docente.create(rutEditar, nombreEditar, generoEditado, isbnEditar, profesionEditar, grado);
                    	if(newDocente != null) {
                    		System.out.println("¡Docente registrado exitosamente!");
                    		System.out.println(newDocente.toString());
                    	} else {
                    		System.out.println("El docente ya existe. No se agregaron nuevos docentes.");
                    	}
                    	
                	} else {
                		System.out.println("CARRERA: ");
                    	String carreraEditar = sc.nextLine();
                    	while(carreraEditar.isEmpty()) {
                    		System.out.println("NOMBRE COMPLETO: ");
                        	carreraEditar = sc.nextLine();
                    	}
                    	
                    	Estudiante newEstudiante = Estudiante.create(rutEditar, nombreEditar, generoEditado, isbnEditar, carreraEditar);
                    	if(newEstudiante != null) {
                    		System.out.println("¡Estudiante creado exitosamente!");
                    		System.out.println(newEstudiante.toString());
                    	} else {
                    		System.out.println("El estudiante ya existe. No se agregaron nuevos estudiantes.");
                    	}
                	}
                	
                	break;
                	
                case 7:
                	System.out.println("========== Eliminar usuario ==========\n");
                	System.out.println("Ingrese el RUT usuario a eliminar: ");
                	String rutEliminar = sc.nextLine();
                	while(!Usuario.isValidRut(rutEliminar)) {
                		System.out.println("Por favor ingrese un rut válido en formato '12345678-9' (sin puntos y con guión).");
                    	System.out.println("Ingrese el RUT usuario a eliminar: ");
                    	rutEliminar = sc.nextLine();
                	}
                	
                	Usuario.delete(rutEliminar);
                	
                	System.out.println("Si el usuario existía, fue eliminado. De lo contrario, no se hicieron cambios.");
                	
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