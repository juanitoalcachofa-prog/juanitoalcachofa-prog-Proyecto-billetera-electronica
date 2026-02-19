package acciones;

import java.time.LocalDate;

public class Gasto {
    private int idGasto;
    private int idUsuario;
    private double monto;
    private String categoria;
    private LocalDate fecha;

    public Gasto(int idGasto, int idUsuario, double monto, String categoria, LocalDate fecha) {
        this.idGasto = idGasto;
        this.idUsuario = idUsuario;
        this.monto = monto;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    public int getIdGasto() {
        return idGasto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public double getMonto() {
        return monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "[" + fecha + "] " + categoria + ": $" + monto;
    }
}
