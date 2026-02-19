package acciones;

public abstract class Persona implements Autenticable {
    protected int id;
    protected String nombre;
    protected String contraseña;

    public Persona(int id, String nombre, String contraseña) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    @Override
    public boolean autenticar(String contraseña) {
        return this.contraseña.equals(contraseña);
    }
}
