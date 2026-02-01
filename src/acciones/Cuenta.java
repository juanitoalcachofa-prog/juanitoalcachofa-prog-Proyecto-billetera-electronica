package acciones;

import sistema.SistemaBilletera;

public class Cuenta {
    private int idCuenta;
    private int idUsuarioPropietario;
    private double saldoActual;

    public Cuenta(int idCuenta, int idUsuarioPropietario, double saldoInicial) {
        this.idCuenta = idCuenta;
        this.idUsuarioPropietario = idUsuarioPropietario;
        this.saldoActual = saldoInicial;
    }

    public int getIdCuenta() { return idCuenta; }
    public int getIdUsuarioPropietario() { return idUsuarioPropietario; }
    public double getSaldoActual() { return saldoActual; }

    public void depositar(double monto) { saldoActual += monto; }

    public boolean retirar(double monto) {
        if (saldoActual >= monto) {
            saldoActual -= monto;
            return true;
        }
        return false;
    }

    public boolean transferir(Cuenta destino, double monto, SistemaBilletera sistema, Usuario usuario) {
        if (retirar(monto)) {
            destino.depositar(monto);
            sistema.crearNotificacion(usuario.getIdUsuario(), "Transferencia realizada: $" + monto +
                    " de cuenta " + idCuenta + " a cuenta " + destino.getIdCuenta());
            sistema.crearNotificacion(destino.getIdUsuarioPropietario(), "Transferencia recibida: $" + monto +
                    " en cuenta " + destino.getIdCuenta());
            return true;
        }
        return false;
    }
}
