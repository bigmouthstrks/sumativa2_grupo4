package mainPackage;

public enum File {
	USUARIOS("src/mainPackage/usuarios.txt");
	
	private final String path;
	
	File(String path) {
		this.path = path;
	}
	
	// getter
    public String getPath() {
        return path;
    }
}