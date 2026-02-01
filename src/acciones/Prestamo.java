package acciones;

public class Prestamo {
    private int idPrestamo;
    private int idUsuarioPropietario;
    private double montoSolicitado;
    private double interes;
    private int cantidadMeses;
    private int mesesPagados;
    private double cuotaMensual;
    private String estadoPrestamo;

    public Prestamo(int idPrestamo, int idUsuarioPropietario, double montoSolicitado, double interes,
                    int cantidadMeses, int mesesPagados, double cuotaMensual, String estadoPrestamo) {
        this.idPrestamo = idPrestamo;
        this.idUsuarioPropietario = idUsuarioPropietario;
        this.montoSolicitado = montoSolicitado;
        this.interes = interes;
        this.cantidadMeses = cantidadMeses;
        this.mesesPagados = mesesPagados;
        this.cuotaMensual = cuotaMensual;
        this.estadoPrestamo = estadoPrestamo;
    }

    public int getIdPrestamo() { return idPrestamo; }
    public int getIdUsuarioPropietario() { return idUsuarioPropietario; }
    public double getMontoSolicitado() { return montoSolicitado; }
    public double getInteres() { return interes; }
    public int getCantidadMeses() { return cantidadMeses; }
    public int getMesesPagados() { return mesesPagados; }
    public double getCuotaMensual() { return cuotaMensual; }
    public String getEstadoPrestamo() { return estadoPrestamo; }

    public void pagarCuota() {
        mesesPagados++;
        if (mesesPagados >= cantidadMeses) estadoPrestamo = "FINALIZADO";
    }
}
