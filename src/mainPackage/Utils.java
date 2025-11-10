package mainPackage;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Utils {

	public static void saveToFile(File file, String content) {
	    String filePath = file.getPath();
	    try (BufferedWriter writer = new BufferedWriter(
	            new OutputStreamWriter(new FileOutputStream(filePath, true), StandardCharsets.UTF_8))) {

	        writer.newLine();
	        writer.write(content);

	        System.out.println("✅ Contenido agregado a: " + filePath);
	    } catch (IOException e) {
	        System.out.println("❌ Error al guardar archivo: " + e.getMessage());
	    }
	}

    public static String readFromFile(File file) {
    	String filePath = file.getPath();
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            System.out.println("✅ Archivo leído: " + filePath);
        } catch (IOException e) {
            System.out.println("❌ Error al leer archivo: " + e.getMessage());
        }
        return content.toString().trim();
    }
    
    public static void deleteRecordById(File file, String id) {
        String filePath = file.getPath();
        StringBuilder newContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                
                if (parts.length > 0 && !parts[0].equals(id)) {
                    newContent.append(line).append("\n");
                }
            }

        } catch (IOException e) {
            System.out.println("❌ Error al leer archivo: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath, false), StandardCharsets.UTF_8))) {

            writer.write(newContent.toString().trim());
            System.out.println("🗑️ Registro eliminado de: " + filePath);

        } catch (IOException e) {
            System.out.println("❌ Error al escribir archivo: " + e.getMessage());
        }
    }
    
    public static boolean alreadyExists(File file, String id) {
        String filePath = file.getPath();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 0 && parts[0].equals(id)) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println("❌ Error al leer archivo: " + e.getMessage());
        }

        return false;
    }
    
    public static String getRecordById(File file, String id) {
        String filePath = file.getPath();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 0 && parts[0].equals(id)) {
                    System.out.println("✅ Registro encontrado: " + id);
                    return line.trim();
                }
            }

            System.out.println("⚠️ No se encontró registro con id: " + id);

        } catch (IOException e) {
            System.out.println("❌ Error al leer archivo: " + e.getMessage());
        }

        return null;
    }

}
