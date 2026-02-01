package acciones;

import java.util.ArrayList;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String contrasena;
    private ArrayList<Cuenta> listaCuentas;
    private ArrayList<Prestamo> listaPrestamos;

    public Usuario(int idUsuario, String nombreUsuario, String contrasena) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        listaCuentas = new ArrayList<>();
        listaPrestamos = new ArrayList<>();
    }

    public int getIdUsuario() { return idUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
    public ArrayList<Cuenta> getListaCuentas() { return listaCuentas; }
    public ArrayList<Prestamo> getListaPrestamos() { return listaPrestamos; }

    public boolean autenticar(String contrasenaIngresada) {
        return contrasena.equals(contrasenaIngresada);
    }

    public void agregarCuenta(Cuenta c) { listaCuentas.add(c); }
    public void agregarPrestamo(Prestamo p) { listaPrestamos.add(p); }
}
