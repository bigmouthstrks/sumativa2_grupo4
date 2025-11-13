package mainPackage;

import java.util.*;

public class Usuario {
	protected String rut;
	protected String nombreCompleto;
	protected Gender genero;
	protected Integer prestamo;

	protected Usuario(String rut, String nombreCompleto, Gender genero, Integer prestamo) {
		this.rut = rut;
		this.nombreCompleto = nombreCompleto;
		this.genero = genero;
		this.prestamo = prestamo;
	}
	
	public static void delete(String rut) {
		Utils.deleteRecordById(File.USUARIOS, rut);
	}
	
    @Override
    public String toString() {
        return rut + ";" + nombreCompleto + ";" + genero + ";" + prestamo;
    }
    
	protected static boolean isValidLoan(String loan) {
		if(loan.equals("0") || !loan.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String getUserRecord(String rut) {
		String record = Utils.getRecordById(File.USUARIOS, rut);
		
		if(record.isEmpty()) {
			return "";
		} else {
			return record;
		}
	}
	
	public static String getUserType(String rut) {
		String record = Utils.getRecordById(File.USUARIOS, rut);		
		String[] list = record.split(";");
		String type = list[list.length - 1];
		
		if(type.equals("DOCENTE")) {
			return "D";
		} else if (type.equals("ESTUDIANTE")) {
			return "E";
		} else {
			return "NN";
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