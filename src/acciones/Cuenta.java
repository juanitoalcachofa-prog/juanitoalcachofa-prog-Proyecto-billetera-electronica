package acciones;

import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private int idCuenta;
    private int idUsuarioPropietario;
    private double saldoActual;

    public Cuenta(int idCuenta, int idUsuarioPropietario, double saldoInicial) {
        this.idCuenta = idCuenta;
        this.idUsuarioPropietario = idUsuarioPropietario;
        this.saldoActual = saldoInicial;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public int getIdUsuarioPropietario() {
        return idUsuarioPropietario;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public void depositar(double monto, List<Notificacion> listaNotificaciones) {
        if (monto > 0) {
            saldoActual += monto;
            Notificacion.crearNotificacion(idUsuarioPropietario,
                    "Depósito recibido: $" + monto + " - Nuevo saldo: $" + saldoActual, listaNotificaciones);
        }
    }

    public boolean retirar(double monto, List<Notificacion> listaNotificaciones) {
        if (monto > 0 && saldoActual >= monto) {
            saldoActual -= monto;
            Notificacion.crearNotificacion(idUsuarioPropietario,
                    "Retiro/Gasto realizado: $" + monto + " - Nuevo saldo: $" + saldoActual, listaNotificaciones);
            return true;
        }
        return false;
    }

    public static void crearCuenta(Usuario usuario, double saldoInicial, ArrayList<Cuenta> listaCuentas,
            List<Notificacion> listaNotificaciones) {
        int idNueva = listaCuentas.stream().mapToInt(Cuenta::getIdCuenta).max().orElse(0) + 1;
        Cuenta c = new Cuenta(idNueva, usuario.getIdUsuario(), saldoInicial);
        listaCuentas.add(c);
        usuario.agregarCuenta(c);
        Notificacion.crearNotificacion(usuario.getIdUsuario(),
                "Nueva cuenta creada con ID: " + idNueva + " y saldo: $" + saldoInicial, listaNotificaciones);
    }
}
