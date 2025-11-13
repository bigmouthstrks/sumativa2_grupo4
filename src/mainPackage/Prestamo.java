package mainPackage;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;


public class Prestamo implements Actualiza {
    private Integer isbn;
    private String rut;
    private Date solicitud;
    private Date entrega;
    private Integer dias;

    public Prestamo(Integer isbn, String rut, Date solicitud, Integer dias) {
        this.isbn = isbn;
        this.rut = rut;
        this.solicitud = solicitud;
        this.dias = dias;
        this.entrega = calcularFechaEntrega(solicitud, dias);
    }

    private Date calcularFechaEntrega(Date solicitud, Integer dias) {
        long tiempoEntrega = solicitud.getTime() + (long) dias * 24 * 60 * 60 * 1000;
        return new Date(tiempoEntrega);
    }

    public static void realizarPrestamo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese ISBN del libro: ");
        int isbn = sc.nextInt();
        sc.nextLine();

        if (!existeLibro(isbn)) {
            System.out.println("Libro no encontrado.");
            return;
        }
        if (!hayDisponibles(isbn)) {
            System.out.println("No hay ejemplares disponibles.");
            return;
        }

        System.out.print("Ingrese RUN del usuario: ");
        String rut = sc.nextLine();
        if (!existeUsuario(rut)) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        if (!usuarioDisponible(rut)) {
            System.out.println("Usuario ya tiene un préstamo activo.");
            return;
        }

        String tipo = obtenerTipoUsuario(rut);
        int maxDias = tipo.equalsIgnoreCase("DOCENTE") ? 20 : 10;

        System.out.print("Ingrese cantidad de días (máx " + maxDias + "): ");
        int dias = sc.nextInt();
        if (dias > maxDias) dias = maxDias;

        Date fechaActual = new Date();

        Prestamo p = new Prestamo(isbn, rut, fechaActual, dias);

        p.actualizaLibro(isbn);
        p.actualizaUsuario(rut);
        p.registrarPrestamoArchivo(p);

        p.generaTarjeta();
    }

    private static boolean existeLibro(Integer isbn) {
        try (BufferedReader br = new BufferedReader(new FileReader("libros.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length > 0 && partes[0].equals(isbn.toString())) return true;
            }
        } catch (IOException e) {
            System.out.println("Error al leer libros: " + e.getMessage());
        }
        return false;
    }

    private static boolean hayDisponibles(Integer isbn) {
        try (BufferedReader br = new BufferedReader(new FileReader("libros.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 5 && partes[0].equals(isbn.toString())) {
                    return Integer.parseInt(partes[4]) > 0;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer libros: " + e.getMessage());
        }
        return false;
    }

    private static boolean existeUsuario(String rut) {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 2 && partes[0].equalsIgnoreCase(rut)) return true;
            }
        } catch (IOException e) {
            System.out.println("Error al leer usuarios: " + e.getMessage());
        }
        return false;
    }

    private static boolean usuarioDisponible(String rut) {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes[0].equalsIgnoreCase(rut)) {

                    if (Arrays.asList("ESTUDIANTE", "DOCENTE").contains(partes[partes.length - 1].toUpperCase())) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
        }
        return false;
    }

    private static String obtenerTipoUsuario(String rut) {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes[0].equalsIgnoreCase(rut)) {
                    for (String parte : partes) {
                        if (parte.equalsIgnoreCase("ESTUDIANTE") || parte.equalsIgnoreCase("DOCENTE")) {
                            return parte;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer tipo usuario: " + e.getMessage());
        }
        return "ESTUDIANTE";
    }

    @Override
    public boolean actualizaLibro(Integer isbn) {
        final String fileName = "libros.txt";
        File archivo = new File(fileName);
        List<String> lineas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 6 && partes[0].equals(isbn.toString())) {
                    encontrado = true;
                    int disponibles = Integer.parseInt(partes[4]);
                    if (disponibles > 0) disponibles -= 1;
                    partes[4] = String.valueOf(disponibles);
                    linea = String.join(";", partes);
                }
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
            return false;
        }

        if (encontrado) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
                for (String l : lineas) {
                    bw.write(l);
                    bw.newLine();
                }
                System.out.println("- Libro actualizado correctamente.");
            } catch (IOException e) {
                System.out.println("Error al actualizar archivo: " + e.getMessage());
                return false;
            }
        }
        return encontrado;
    }

    @Override
    public boolean actualizaUsuario(String rut) {
        final String fileName = "usuarios.txt";
        List<String> lineas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");

                if (
                        Arrays.asList("ESTUDIANTE", "DOCENTE").contains(partes[partes.length - 1].toUpperCase())
                                &&
                                partes[0].equalsIgnoreCase(rut)
                ) {
                    encontrado = true;

                    linea = String.join(";", partes) + ";" + this.isbn;
                    System.out.println("- Usuario actualizado con nuevo préstamo (ISBN: " + this.isbn + ").");
                }
                else {
                    System.out.println("El usuario ya tiene un préstamo activo con ISBN: " + partes[partes.length - 1]);
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
            System.out.println("Usuario no encontrado en el archivo." + lineas);
        }

        return encontrado;
    }

    private void registrarPrestamoArchivo(Prestamo p) {
        final String fileName = "prestamos.txt";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setTime(p.getFechaPrestamo());
        cal.add(Calendar.DAY_OF_MONTH, p.getDiasPrestamo());
        String fechaDevolucion = sdf.format(cal.getTime());

        String linea = p.getIsbn() + ";" + p.getRut() + ";" +
                sdf.format(p.getFechaPrestamo()) + ";" + fechaDevolucion;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al registrar préstamo: " + e.getMessage());
        }
    }

    public void generaTarjeta() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("\n|===== TARJETA DE PRÉSTAMO =====|");
        System.out.println("| ISBN: " + isbn);
        System.out.println("| RUT: " + rut);
        System.out.println("| Fecha solicitud: " + sdf.format(solicitud));
        System.out.println("| Fecha entrega:   " + sdf.format(entrega));
        System.out.println("| Días préstamo:   " + dias);
        System.out.println("|_______________________________|");
    }

    public int getIsbn() {
        return isbn;
    }

    public String getRut() {
        return rut;
    }

    public Date getFechaPrestamo() {
        return solicitud;
    }

    public int getDiasPrestamo() {
        return dias;
    }
}