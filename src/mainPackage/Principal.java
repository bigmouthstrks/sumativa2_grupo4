package mainPackage;
import java.util.Date;
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
            System.out.println("  5. Salir\n");
            System.out.print("Seleccione una opción: ");

            while (!sc.hasNextInt()) {
                System.out.println("Debe ingresar un número.");
                sc.next();
            }

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese ISBN:");
                    int isbn = sc.nextInt(); sc.nextLine();
                    System.out.println("Ingrese título:");
                    String titulo = sc.nextLine();
                    System.out.println("Ingrese autor:");
                    String autor = sc.nextLine();
                    System.out.println("Ingrese cantidad:");
                    int cantidad = sc.nextInt(); sc.nextLine();
                    System.out.println("Ingrese disponibles:");
                    int disponibles = sc.nextInt(); sc.nextLine();
                    System.out.println("Ingrese nombre de imagen:");
                    String imagen = sc.nextLine();

                    Libro.crearLibro(isbn, titulo, autor, cantidad, disponibles, imagen);
                    break;

                case 2:
                    System.out.println("Ingrese ISBN del libro a eliminar:");
                    int isbnEliminar = sc.nextInt();
                    Libro.eliminarLibro(isbnEliminar);
                    break;

                case 3:
                    Prestamo.realizarPrestamo();
                    break;

                case 4:
                    System.out.println("Funcionalidad 4 pendiente...");
                    break;

                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

        } while (opcion != 5);

        sc.close();
    }
}