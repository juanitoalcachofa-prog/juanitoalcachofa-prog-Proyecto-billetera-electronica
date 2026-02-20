package acciones;

import java.util.ArrayList;
import java.util.List;

public class AnalisisFinanzas {

    public static List<String> generarConsejosDetallados(Usuario u) {
        List<String> consejos = new ArrayList<>();
        double ingresos = u.getIngresoMensual();
        double gastos = u.getGastosTotales();
        double deudas = u.getDeudasTotales();
        double saldo = ingresos - gastos;
        double ratioGastos = (ingresos > 0) ? (gastos / ingresos) : 1;

        consejos.add("--- REPORTE DE SALUD FINANCIERA PARA " + u.getNombreUsuario().toUpperCase() + " ---");


        if (ratioGastos > 0.8) {
            consejos.add("[ALERTA CRÍTICA] Tus gastos representan el " + String.format("%.2f", ratioGastos * 100)
                    + "% de tus ingresos.");
            consejos.add(
                    "-> Consejo: Identifica 'gastos hormiga' (cafés, suscripciones que no usas) y recórtalos de inmediato.");
        } else if (ratioGastos > 0.5) {
            consejos.add(
                    "[PRECAUCIÓN] Estás en la zona media. Intenta aplicar la regla 50/30/20 (50% Necesidades, 30% Deseos, 20% Ahorro).");
        } else {
            consejos.add("[EXCELENTE] Mantienes tus gastos bajo control. Es un buen momento para invertir.");
        }


        if (deudas > 0) {
            consejos.add("[DEUDAS] Tienes una carga de deudas de $" + deudas);
            consejos.add(
                    "-> Consejo: Usa el método 'Avalancha': paga primero la deuda con la tasa de interés más alta.");
        }


        if (saldo > 0) {
            double sugerenciaAhorro = saldo * 0.15;
            consejos.add("[AHORRO SUGERIDO] Basado en tu sueldo actual, podrías separar $"
                    + String.format("%.2f", sugerenciaAhorro) + " este mes.");
        } else {
            consejos.add("[RIESGO] Tu saldo es negativo o cero. No realices compras a crédito hasta estabilizarte.");
        }

        return consejos;
    }
}
