package sistema;

import acciones.Usuario;
import acciones.FormularioGastos;

public class GestorConsejos {

    public String generarConsejos(Usuario usuario) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== CONSEJOS FINANCIEROS ===\n");
        sb.append("Sueldo registrado: $").append(usuario.getSueldo()).append("\n");

        if (usuario.getFormularioGastos() != null) {
            FormularioGastos f = usuario.getFormularioGastos();
            double limite = usuario.getSueldo() * f.getPorcentajeSueldo();
            sb.append("Categoría: ").append(f.getCategoria())
              .append(" | Límite recomendado: $").append(limite).append("\n");
        } else {
            sb.append("No has configurado tu formulario de gastos.\n");
            sb.append("Consejo: destina 50% a necesidades, 30% a deseos y 20% a ahorro.\n");
        }

        return sb.toString();
    }

    public String analizarEndeudamiento(Usuario usuario) {
        double deudaTotal = usuario.getListaPrestamos().stream()
                .mapToDouble(p -> p.getSaldoPendiente())
                .sum();
        if (deudaTotal > usuario.getSueldo() * 0.5) {
            return "⚠ Atención: tu deuda supera el 50% de tu sueldo. Considera reducir gastos o evitar nuevos préstamos.";
        }
        return "Tu nivel de endeudamiento está dentro de un rango manejable.";
    }
}