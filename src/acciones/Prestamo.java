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
    private double saldoPendiente;

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
        this.saldoPendiente = montoSolicitado + (montoSolicitado * interes);
    }

    public int      getIdPrestamo() { return idPrestamo; }
    public int      getIdUsuarioPropietario() { return idUsuarioPropietario; }
    public double   getMontoSolicitado() { return montoSolicitado; }
    public double   getSaldoPendiente() { return saldoPendiente; }
    public String   getEstadoPrestamo() { return estadoPrestamo; }
    public double   getInteres() {return interes;}
    public void     setInteres(double interes) {this.interes = interes;}
    public int      getCantidadMeses() {return cantidadMeses;}
    public void     setCantidadMeses(int cantidadMeses) {this.cantidadMeses = cantidadMeses;}
    public int      getMesesPagados() {return mesesPagados;}
    public void     setMesesPagados(int mesesPagados) {this.mesesPagados = mesesPagados;}
    public double   getCuotaMensual() {return cuotaMensual; }
    
    public void pagarCuota() {
        mesesPagados++;
        saldoPendiente -= cuotaMensual;
        if (saldoPendiente <= 0) {
            saldoPendiente = 0;
            estadoPrestamo = "FINALIZADO";
        }
    }
    public void pagarParcial(double monto) {
        if (monto > 0 && saldoPendiente > 0) {
            saldoPendiente -= monto;
            if (saldoPendiente <= 0) {
                saldoPendiente = 0;
                estadoPrestamo = "FINALIZADO";
            }
        }
    }
}