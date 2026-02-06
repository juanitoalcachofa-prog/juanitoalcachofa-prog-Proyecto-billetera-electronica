package acciones;

import java.util.ArrayList;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String contrasena;
    private double sueldo;
    private ArrayList<Cuenta> listaCuentas;
    private ArrayList<Prestamo> listaPrestamos;
    private ArrayList<Transaccion> listaTransacciones;
    private FormularioGastos formularioGastos;

    public Usuario(int idUsuario, String nombreUsuario, String contrasena, double sueldo) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.sueldo = sueldo;
        this.listaCuentas = new ArrayList<>();
        this.listaPrestamos = new ArrayList<>();
        this.listaTransacciones = new ArrayList<>();
    }

    public int getIdUsuario() { return idUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
    public double getSueldo() { return sueldo; }
    public ArrayList<Cuenta> getListaCuentas() { return listaCuentas; }
    public ArrayList<Prestamo> getListaPrestamos() { return listaPrestamos; }
    public ArrayList<Transaccion> getListaTransacciones() { return listaTransacciones; }
    public FormularioGastos getFormularioGastos() { return formularioGastos; }

    public boolean autenticar(String contrasenaIngresada) {
        return contrasena.equals(contrasenaIngresada);
    }

    public void agregarCuenta(Cuenta c) { listaCuentas.add(c); }
    public void agregarPrestamo(Prestamo p) { listaPrestamos.add(p); }
    public void agregarTransaccion(Transaccion t) { listaTransacciones.add(t); }
    public void setFormularioGastos(FormularioGastos f) { this.formularioGastos = f; }
}