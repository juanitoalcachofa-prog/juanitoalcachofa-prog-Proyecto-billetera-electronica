package acciones;

import java.time.LocalDate;

public class Transaccion {
    private String tipoTransaccion;
    private double montoTransaccion;
    private LocalDate fechaTransaccion;

    public Transaccion(String tipoTransaccion, double montoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
        this.montoTransaccion = montoTransaccion;
        this.fechaTransaccion = LocalDate.now();
    }

    public String getTipoTransaccion() { return tipoTransaccion; }
    public double getMontoTransaccion() { return montoTransaccion; }
    public LocalDate getFechaTransaccion() { return fechaTransaccion; }

    @Override
    public String toString() {
        return "[" + fechaTransaccion + "] " + tipoTransaccion + " -> $" + montoTransaccion;
    }
}
