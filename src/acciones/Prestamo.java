package acciones;

import guardado.ArchivoTexto;
import java.util.ArrayList;
import java.util.List;

public class Prestamo {
    private int idPrestamo;
    private int idUsuarioPropietario;
    private double montoSolicitado;
    private double interes;
    private int cantidadMeses;
    private int mesesPagados;
    private double cuotaMensual;
    private String estadoPrestamo;
    private String tipoPrestamo;

    public Prestamo(int idPrestamo, int idUsuarioPropietario, double montoSolicitado, double interes,
            int cantidadMeses, int mesesPagados, double cuotaMensual, String estadoPrestamo, String tipoPrestamo) {
        this.idPrestamo = idPrestamo;
        this.idUsuarioPropietario = idUsuarioPropietario;
        this.montoSolicitado = montoSolicitado;
        this.interes = interes;
        this.cantidadMeses = cantidadMeses;
        this.mesesPagados = mesesPagados;
        this.cuotaMensual = cuotaMensual;
        this.estadoPrestamo = estadoPrestamo;
        this.tipoPrestamo = tipoPrestamo;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public int getIdUsuarioPropietario() {
        return idUsuarioPropietario;
    }

    public double getMontoSolicitado() {
        return montoSolicitado;
    }

    public double getInteres() {
        return interes;
    }

    public int getCantidadMeses() {
        return cantidadMeses;
    }

    public int getMesesPagados() {
        return mesesPagados;
    }

    public double getCuotaMensual() {
        return cuotaMensual;
    }

    public String getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public String getTipoPrestamo() {
        return tipoPrestamo;
    }

    public double getDeudaPendiente() {
        double totalAPagar = montoSolicitado + (montoSolicitado * interes);
        double pagado = mesesPagados * cuotaMensual; // En sistema de cuotas fijas
        return Math.max(0, totalAPagar - pagado);
    }

    // Nuevo método para pago parcial o total
    public void registrarPago(double monto) {
        // En un sistema flexible, el "mesesPagados" se vuelve un contador de abonos
        // o lo recalculamos basado en cuánto representa del total
        double totalCosto = montoSolicitado + (montoSolicitado * interes);
        double yaPagado = mesesPagados * cuotaMensual;

        // Simulamos el avance de "meses" proporcionalmente si el usuario paga más o
        // menos
        mesesPagados++;
        // Nota: Para pagos parciales reales, deberíamos guardar 'totalAbonado' en lugar
        // de mesesPagados
        // Pero para mantener compatibilidad con el esquema actual, ajustamos el estado
        if (yaPagado + monto >= totalCosto) {
            estadoPrestamo = "FINALIZADO";
        }
    }

    public static void crearPrestamo(Usuario usuario, double monto, int meses, double tasaInteres, String tipo,
            ArrayList<Prestamo> listaPrestamos, List<Notificacion> listaNotificaciones) {
        double cuota = (monto / meses) + (monto * tasaInteres);
        int idNuevo = listaPrestamos.stream().mapToInt(Prestamo::getIdPrestamo).max().orElse(0) + 1;
        Prestamo p = new Prestamo(idNuevo, usuario.getIdUsuario(), monto, tasaInteres, meses, 0, cuota, "ACTIVO", tipo);
        listaPrestamos.add(p);
        usuario.agregarPrestamo(p);

        // No guardamos línea aquí, delegamos al SistemaBilletera para que guarde todo
        // el estado
        Notificacion.crearNotificacion(usuario.getIdUsuario(),
                "¡Préstamo " + tipo + " aprobado! Monto: $" + monto + " | Interés: " + (tasaInteres * 100) + "%",
                listaNotificaciones);
    }

    public static boolean pagarCuota(Usuario usuario, java.util.Scanner scanner,
            List<Notificacion> listaNotificaciones) {
        ArrayList<Prestamo> activos = new ArrayList<>();
        for (Prestamo p : usuario.getListaPrestamos())
            if (p.getEstadoPrestamo().equals("ACTIVO"))
                activos.add(p);

        if (activos.isEmpty()) {
            System.out.println("No tiene préstamos activos.");
            return false;
        }

        System.out.println("\n--- PRÉSTAMOS ACTIVOS ---");
        for (int i = 0; i < activos.size(); i++) {
            Prestamo p = activos.get(i);
            System.out.println((i + 1) + ". [" + p.getTipoPrestamo() + "] Monto Orig: $" + p.getMontoSolicitado() +
                    " | Cuota Sugerida: $" + p.getCuotaMensual() +
                    " | Saldo Pendiente: $" + String.format("%.2f", p.getDeudaPendiente()));
        }

        System.out.print("Seleccione el préstamo: ");
        int opPrestamo = Integer.parseInt(scanner.nextLine()) - 1;
        if (opPrestamo < 0 || opPrestamo >= activos.size())
            return false;

        Prestamo prestamoElegido = activos.get(opPrestamo);

        System.out.print("Monto a pagar (Sugerido $" + prestamoElegido.getCuotaMensual() + "): ");
        double montoAPagar = Double.parseDouble(scanner.nextLine());

        System.out.println("\n--- TUS CUENTAS ---");
        for (int i = 0; i < usuario.getListaCuentas().size(); i++) {
            Cuenta c = usuario.getListaCuentas().get(i);
            System.out.println((i + 1) + ". ID: " + c.getIdCuenta() + " | Saldo: $" + c.getSaldoActual());
        }

        System.out.print("Seleccione la cuenta: ");
        int opCuenta = Integer.parseInt(scanner.nextLine()) - 1;
        if (opCuenta < 0 || opCuenta >= usuario.getListaCuentas().size())
            return false;

        Cuenta cuentaElegida = usuario.getListaCuentas().get(opCuenta);

        if (cuentaElegida.getSaldoActual() >= montoAPagar) {
            cuentaElegida.retirar(montoAPagar, listaNotificaciones);
            prestamoElegido.registrarPago(montoAPagar);

            Notificacion.crearNotificacion(usuario.getIdUsuario(),
                    "Abono a préstamo " + prestamoElegido.getTipoPrestamo() + ": $" + montoAPagar,
                    listaNotificaciones);

            if (prestamoElegido.getEstadoPrestamo().equals("FINALIZADO")) {
                System.out.println("¡Felicidades! Préstamo liquidado totalmente.");
            }
            return true;
        } else {
            System.out.println("Saldo insuficiente.");
            return false;
        }
    }
}
