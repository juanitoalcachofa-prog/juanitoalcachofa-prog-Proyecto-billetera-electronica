package sistema;

import acciones.Usuario;
import acciones.Transaccion;
import acciones.Prestamo;

public class GestorReportes {

    public String generarReporteFinanciero(Usuario usuario) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== REPORTE FINANCIERO ===\n");
        sb.append("Usuario: ").append(usuario.getNombreUsuario()).append("\n");
        sb.append("Sueldo: $").append(usuario.getSueldo()).append("\n\n");

        sb.append("--- Cuentas ---\n");
        usuario.getListaCuentas().forEach(c ->
            sb.append("Cuenta ").append(c.getIdCuenta())
              .append(" | Saldo: $").append(c.getSaldoActual()).append("\n"));

        sb.append("\n--- Préstamos ---\n");
        usuario.getListaPrestamos().forEach(p ->
            sb.append("Préstamo ").append(p.getIdPrestamo())
              .append(" | Saldo pendiente: $").append(p.getSaldoPendiente())
              .append(" | Estado: ").append(p.getEstadoPrestamo()).append("\n"));

        sb.append("\n--- Transacciones ---\n");
        for (Transaccion t : usuario.getListaTransacciones()) {
            sb.append(t.toString()).append("\n");
        }

        return sb.toString();
    }
}