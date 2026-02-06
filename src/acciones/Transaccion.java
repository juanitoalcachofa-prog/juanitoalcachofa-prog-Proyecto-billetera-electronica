package acciones;

import java.time.LocalDate;

public class Transaccion {
    private int idTransaccion;
    private int idUsuario;
    private int idCuenta;
    private String tipoTransaccion;
    private double montoTransaccion;
    private LocalDate fechaTransaccion;
    private String categoria;

    public Transaccion(int idTransaccion, int idUsuario, int idCuenta, String tipoTransaccion, double montoTransaccion, String categoria) {
        this.idTransaccion = idTransaccion;
        this.idUsuario = idUsuario;
        this.idCuenta = idCuenta;
        this.tipoTransaccion = tipoTransaccion;
        this.montoTransaccion = montoTransaccion;
        this.categoria = categoria;
        this.fechaTransaccion = LocalDate.now();
    }

    @Override
    public String toString() {
        return "[" + fechaTransaccion + "] " + tipoTransaccion + " -> $" + montoTransaccion + " (" + categoria + ")";
    }
}