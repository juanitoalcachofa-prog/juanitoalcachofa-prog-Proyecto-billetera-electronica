package acciones;

public class FormularioGastos {
    private String categoria;
    private double porcentajeSueldo;

    public FormularioGastos(String categoria, double porcentajeSueldo) {
        this.categoria = categoria;
        this.porcentajeSueldo = porcentajeSueldo;
    }

    public String getCategoria() { return categoria; }
    public double getPorcentajeSueldo() { return porcentajeSueldo; }
}