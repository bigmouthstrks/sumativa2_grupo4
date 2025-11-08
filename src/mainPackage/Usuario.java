package mainPackage;

import java.util.*;

public class Usuario {
	String nombreCompleto;
	String rut;
	Gender genero;
	String prestamo;

	protected Usuario(String nombreCompleto, String rut, Gender genero, String prestamo) {
		this.nombreCompleto = nombreCompleto;
		this.genero = genero;
		this.prestamo = prestamo;
		this.rut = rut;
	}

	/* Función que devuelve una instancia de la clase Usuario si los parámetros son correctos, si no, devuelve null */
	public static Usuario create(String nombreCompleto, String rut, Gender genero, String prestamo) {
		if(isValidRut(rut) && isValidLoan(prestamo)) {
			return new Usuario(nombreCompleto, rut, genero, prestamo);
		} else {
			return null;
		}
	}
	
	protected static boolean isValidLoan(String loan) {
		if(loan == "0" || (loan != null || loan != "")) {
			return true;
		} else {
			return false;
		}
	}

	/* Función que devuelve true si el rut pasado como argumento es válido y false si no lo es. */
	protected static boolean isValidRut(String rut) {
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

	@Override
    public String toString() {
        return "Usuario(" +
                "nombre: '" + nombreCompleto + '\'' +
                ", rut: '" + rut + '\'' +
                ", genero: '" + genero + '\'' +
                ", prestamo: '" + prestamo + '\'' +
                ')';
    }
}