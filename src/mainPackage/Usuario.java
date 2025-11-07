package mainPackage;

public class Usuario {
	String nombreCompleto;
	String rut;
	String genero;
	String carrera;
	String prestamo;
	
	protected Usuario(String nombreCompleto, String rut, String genero, String carrera, String prestamo) {
		this.nombreCompleto = nombreCompleto;
		this.genero = genero;
		this.carrera = carrera;
		this.prestamo = prestamo;
		this.rut = rut;
	}
	
	public static Usuario crear(String nombreCompleto, String rut, String genero, String carrera, String prestamo) {
		return new Usuario(nombreCompleto, rut, genero, carrera, prestamo);
	}
	
	/* Función que devuelve true si el rut pasado como argumento es válido y false si no lo es */
	protected boolean isValidRut(String rut) {
		rut = rut.replace(".", "");
		rut = rut.toUpperCase();
		
		/* Crea un array que contiene en el índice 0 la parte numérica del rut y en el índice 1 el dígito verificador */
		String[] parts = rut.split("-");
		
		/* Crea un array que contiene sólo el primer item del array 'parts' */
		String numericPart = parts[0];
		
		
		return true;
	}
	
	@Override
    public String toString() {
        return "Usuario(" +
                "nombre: '" + nombreCompleto + '\'' +
                ", rut: '" + rut + '\'' +
                ", genero: '" + genero + '\'' +
                ", carrera: '" + carrera + '\'' +
                ", prestamo: '" + prestamo + '\'' +
                ')';
    }
}