package acciones;

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
}