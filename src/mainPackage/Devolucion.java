package mainPackage;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Devolucion implements Actualiza {

    private Integer isbn;
    private String rut;
    private Date fechaDevolucion;
    private static final int MULTA_POR_DIA = 1000;

    public Devolucion(Integer isbn, String rut, Date fechaDevolucion) {
        this.isbn = isbn;
        this.rut = rut;
        this.fechaDevolucion = fechaDevolucion;
    }

    public static void realizarDevolucion(Scanner sc) {
        System.out.print("Ingrese ISBN del libro a devolver: ");
        int isbn = Integer.parseInt(sc.nextLine());

        if (!existeLibro(isbn)) {
            System.out.println("Libro no encontrado.");
            return;
        }

        System.out.print("Ingrese RUN del usuario: ");
        String rut = sc.nextLine();

        if (!existeUsuario(rut)) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        if (!prestamoCoincide(isbn, rut)) {
            System.out.println("El usuario no tiene registrado este libro como préstamo.");
            return;
        }

        Devolucion d = new Devolucion(isbn, rut, new Date());

        int multa = d.calcularMulta(rut);
        if (multa > 0) {
            System.out.println("El usuario tiene una multa de $" + multa + " por retraso en la devolución.");
        }

        d.actualizaLibro(isbn);
        d.actualizaUsuario(rut);

        System.out.println("- Devolución procesada correctamente.");
    }

    private static boolean existeLibro(Integer isbn) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/mainPackage/libros.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length > 0 && partes[0].equals(isbn.toString()))
                    return true;
            }
        } catch (IOException e) {
            System.out.println("Error al leer libros: " + e.getMessage());
        }
        return false;
    }

    private static boolean existeUsuario(String rut) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/mainPackage/usuarios.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 2 && partes[0].equalsIgnoreCase(rut))
                    return true;
            }
        } catch (IOException e) {
            System.out.println("Error al leer usuarios: " + e.getMessage());
        }
        return false;
    }

    private static boolean prestamoCoincide(Integer isbn, String rut) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/mainPackage/usuarios.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 5 && partes[0].equalsIgnoreCase(rut)) {
                    return partes[partes.length -1].equals(isbn.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al validar préstamo: " + e.getMessage());
        }
        return false;
    }

    private int calcularMulta(String rut) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/mainPackage/prestamos.txt"))) {
            String linea;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 4 && partes[1].equalsIgnoreCase(rut) && partes[0].equals(isbn.toString())) {
                    Date fechaEntrega = sdf.parse(partes[3]);
                    long diff = fechaDevolucion.getTime() - fechaEntrega.getTime();
                    long diasRetraso = diff / (1000 * 60 * 60 * 24);
                    if (diasRetraso > 0) {
                        return (int) diasRetraso * MULTA_POR_DIA;
                    }
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error al calcular multa: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean actualizaLibro(Integer isbn) {
        final String fileName = "src/mainPackage/libros.txt";
        List<String> lineas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 6 && partes[0].equals(isbn.toString())) {
                    encontrado = true;
                    int disponibles = Integer.parseInt(partes[4]);
                    int cantidadTotal = Integer.parseInt(partes[3]);
                    if (disponibles < cantidadTotal) {
                        disponibles += 1;
                    }
                    partes[4] = String.valueOf(disponibles);
                    linea = String.join(";", partes);
                }
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo libros: " + e.getMessage());
            return false;
        }

        if (encontrado) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
                for (String l : lineas) {
                    bw.write(l);
                    bw.newLine();
                }
                System.out.println("- Libro actualizado correctamente (se sumó 1 disponible).");
            } catch (IOException e) {
                System.out.println("Error al actualizar archivo libros: " + e.getMessage());
                return false;
            }
        }
        return encontrado;
    }

    @Override
    public boolean actualizaUsuario(String rut) {
        final String fileName = "src/mainPackage/usuarios.txt";
        List<String> lineas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");

                if (partes.length >= 5 && partes[0].equalsIgnoreCase(rut)) {
                    encontrado = true;
                    partes = Arrays.copyOf(partes, partes.length - 1);
                    linea = String.join(";", partes);
                    System.out.println("- Usuario habilitado nuevamente para préstamo.");
                }
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer usuarios: " + e.getMessage());
            return false;
        }

        if (encontrado) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
                for (String l : lineas) {
                    bw.write(l);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error al actualizar usuarios: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("Usuario no encontrado en el archivo.");
        }
        return encontrado;
    }
}