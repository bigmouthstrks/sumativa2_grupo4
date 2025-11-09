package mainPackage;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class Libro {
    Integer isbn;
    String titulo;
    String autor;
    Integer cantidad;
    Integer disponibles;
    String imagen;

    public Libro(
            Integer isbn,
            String titulo,
            String autor,
            Integer cantidad,
            Integer disponibles,
            String imagen
    ) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.cantidad = cantidad;
        this.disponibles = disponibles;
        this.imagen = imagen;
    };

    public Integer getIsbn() { return isbn; }
    public Integer getCantidad() { return cantidad; }
    public Integer getDisponibles() { return disponibles; }

    public static Libro crearLibro(
            Integer isbn,
            String titulo,
            String autor,
            Integer cantidad,
            Integer disponibles,
            String imagen
    ) {
        if (cantidad == null || cantidad <= 0) {
            System.err.println("Error: Cantidad debe ser mayor a '0'.");
            return null;
        }

        if (disponibles == null || disponibles < 0 || disponibles > cantidad) {
            System.err.println("Error: Disponibles debe ser un valor entre 0 y la Cantidad total (" + cantidad + ").");
            return null;
        }

        Libro newBook = new Libro(isbn, titulo, autor, cantidad, disponibles, imagen);
        guardarEnTxt(newBook);

        return newBook;
    }

    private static void guardarEnTxt(Libro libro) {
        final String file_name = "libros.txt";
        Integer isbn = libro.getIsbn();
        boolean encontrado = false;

        try {
            File archivo = new File(file_name);

            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");

                if (partes.length > 0 && partes[0].equals(isbn.toString())) {
                    encontrado = true;
                    System.out.println("Error. Libro ya se encuentra en la BBDD (ISBN: " + isbn + ")");
                    break;
                }
            }
            br.close();

            if (!encontrado) {
                FileWriter write = new FileWriter(file_name, true);
                BufferedWriter bw = new BufferedWriter(write);
                bw.write(libro.toString());
                bw.newLine();
                bw.close();
                write.close();
                System.out.println("Libro agregado correctamente (ISBN: " + isbn + ")");
            }

        } catch (IOException e) {
            System.out.println("Error al guardar libro: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return isbn + ";" + titulo + ";" + autor + ";" + cantidad + ";" + disponibles + ";" + imagen;
    }

    public void setDisponibles(Integer disponibles) {
        this.disponibles = disponibles;
    }

    public static void eliminarLibro(Integer isbn) {
        final String file_name = "libros.txt";
        File archivo = new File(file_name);
        List<String> lineas = new ArrayList<>();
        boolean encontrado = false;

        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length > 0 && partes[0].equals(isbn.toString())) {
                    encontrado = true;
                    continue;
                }
                lineas.add(linea);
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false)); // false → sobrescribe
            for (String l : lineas) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();

            if (encontrado)
                System.out.println("Libro eliminado correctamente ISBN: " + isbn);
            else
                System.out.println("Error. No se encontró un libro con ISBN: " + isbn);

        } catch (IOException e) {
            System.out.println("Error en la ejecución: " + e.getMessage());
        }
    }
}
