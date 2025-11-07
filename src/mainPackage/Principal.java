package mainPackage;

public class Principal {
	public static void main(String args[]) {
		Usuario user = Usuario.crear(
			"Benjamin Caceres", 
			"19617271-8", 
			"M", 
			"Ing. Informatica",
			"Prestamo"
		);
		System.out.println(user);
	}
}
