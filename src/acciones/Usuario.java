package acciones;

import guardado.ArchivoTexto;
import java.util.ArrayList;
import java.util.List;
import sistema.SistemaBilletera;

public class Usuario extends Persona {
    private ArrayList<Cuenta> listaCuentas;
    private ArrayList<Prestamo> listaPrestamos;
    private ArrayList<Gasto> listaGastos;
    private double ingresoMensual;

    public Usuario(int id, String nombre, String contrasena) {
        this(id, nombre, contrasena, 0.0);
    }

    public Usuario(int id, String nombre, String contraseña, double ingresoMensual) {
        super(id, nombre, contraseña);
        this.ingresoMensual = ingresoMensual;
        listaCuentas = new ArrayList<>();
        listaPrestamos = new ArrayList<>();
        listaGastos = new ArrayList<>();
    }

    public int getIdUsuario() {
        return id;
    }

    public String getNombreUsuario() {
        return nombre;
    }

    public ArrayList<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    public ArrayList<Prestamo> getListaPrestamos() {
        return listaPrestamos;
    }

    public ArrayList<Gasto> getListaGastos() {
        return listaGastos;
    }

    public double getIngresoMensual() {
        return ingresoMensual;
    }

    public void setIngresoMensual(double ingresoMensual) {
        this.ingresoMensual = ingresoMensual;
    }

    public void agregarCuenta(Cuenta c) {
        listaCuentas.add(c);
    }

    public void agregarPrestamo(Prestamo p) {
        listaPrestamos.add(p);
    }

    public void agregarGasto(Gasto g) {
        listaGastos.add(g);
    }

    public double getSaldoTotal() {
        return listaCuentas.stream().mapToDouble(Cuenta::getSaldoActual).sum();
    }

    public double getDeudaMensualTotal() {
        return listaPrestamos.stream()
                .filter(p -> p.getEstadoPrestamo().equals("ACTIVO"))
                .mapToDouble(Prestamo::getCuotaMensual)
                .sum();
    }

    public String obtenerAsesoriaFinanciera() {
        if (ingresoMensual <= 0)
            return "Por favor, actualice su ingreso mensual para recibir asesoría.";

        double saldoTotal = getSaldoTotal();
        double deudaMensual = getDeudaMensualTotal();

        double endeudamiento = (deudaMensual / ingresoMensual) * 100;
        double ahorro = (saldoTotal / ingresoMensual) * 100;

        if (endeudamiento > 40) {
            return "Su nivel de endeudamiento supera el 40%. Priorice el pago de deudas y evite nuevos préstamos.";
        }
        if (ahorro < 10) {
            return "Su ahorro es inferior al 10%. Reduzca gastos variables y establezca una meta del 20%.";
        }
        if (ahorro >= 20) {
            return "Situación estable. Cree un fondo de emergencia equivalente a tres meses de gastos.";
        }
        return "Mantenga el control mensual y mejore la distribución de sus gastos.";
    }

    public String generarResumen() {
        if (listaGastos.isEmpty())
            return "No se registran gastos aún.";

        StringBuilder sb = new StringBuilder("\n=== RESUMEN DE GASTOS POR CATEGORÍA ===\n");
        java.util.Map<String, Double> gastosPorCat = new java.util.HashMap<>();

        for (Gasto g : listaGastos) {
            gastosPorCat.put(g.getCategoria(), gastosPorCat.getOrDefault(g.getCategoria(), 0.0) + g.getMonto());
        }

        for (String cat : gastosPorCat.keySet()) {
            double monto = gastosPorCat.get(cat);
            sb.append("- ").append(cat).append(": $").append(monto).append("\n");
        }

        sb.append("TOTAL GASTADO: $").append(getGastosTotales()).append("\n");
        return sb.toString();
    }

    public double getGastosTotales() {
        return listaGastos.stream().mapToDouble(Gasto::getMonto).sum();
    }

    public double getDeudasTotales() {
        return listaPrestamos.stream()
                .filter(p -> p.getEstadoPrestamo().equals("ACTIVO"))
                .mapToDouble(p -> p.getMontoSolicitado()
                        - (p.getMesesPagados() * (p.getMontoSolicitado() / p.getCantidadMeses())))
                .sum();
    }

    public static Usuario crearUsuario(String nombre, String contraseña, double ingreso,
            ArrayList<Usuario> listaUsuarios, List<Notificacion> listaNotificaciones) {
        int idNuevo = listaUsuarios.stream().mapToInt(Usuario::getIdUsuario).max().orElse(0) + 1;
        Usuario u = new Usuario(idNuevo, nombre, contraseña, ingreso);
        listaUsuarios.add(u);
        Notificacion.crearNotificacion(idNuevo, "¡Bienvenido a su Billetera Electrónica, " + nombre + "!",
                listaNotificaciones);
        return u;
    }
}
