package sistema;

import acciones.Prestamo;
import acciones.Usuario;
import acciones.Transaccion;
import guardado.ArchivoTexto;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorPrestamos {
    private ArrayList<Prestamo> listaPrestamos;
    private ArrayList<Usuario> listaUsuarios;
    private GestorNotificaciones gestorNotificaciones;
    private static final String GUARDAR_SEP = " | ";

    public GestorPrestamos(ArrayList<Prestamo> listaPrestamos,
                           ArrayList<Usuario> listaUsuarios,
                           GestorNotificaciones gestorNotificaciones) {
        this.listaPrestamos = listaPrestamos;
        this.listaUsuarios = listaUsuarios;
        this.gestorNotificaciones = gestorNotificaciones;
    }

    public void cargarPrestamos() {
        ArrayList<String> datosPrestamos = ArchivoTexto.leerArchivo("datos/prestamos.txt");
        for (String linea : datosPrestamos) {
            String[] d = linea.split("\\|");
            if (d.length >= 8) {
                Prestamo p = new Prestamo(Integer.parseInt(d[0].trim()), Integer.parseInt(d[1].trim()),
                        Double.parseDouble(d[2].trim()), Double.parseDouble(d[3].trim()),
                        Integer.parseInt(d[4].trim()), Integer.parseInt(d[5].trim()),
                        Double.parseDouble(d[6].trim()), d[7].trim());
                listaPrestamos.add(p);
                listaUsuarios.stream().filter(u -> u.getIdUsuario() == p.getIdUsuarioPropietario()).forEach(u -> u.agregarPrestamo(p));
            }
        }
    }

    public void solicitarPrestamo(Usuario usuario, Scanner sc) {
        System.out.print("Monto solicitado: ");
        double monto = Double.parseDouble(sc.nextLine());
        System.out.print("Meses: ");
        int meses = Integer.parseInt(sc.nextLine());
        System.out.print("Ingrese tasa de interés mensual (ej. 0.05 para 5%): ");
        double interes = Double.parseDouble(sc.nextLine());

        double cuota = (monto / meses) + (monto * interes);
        int idNuevo = listaPrestamos.stream().mapToInt(Prestamo::getIdPrestamo).max().orElse(0) + 1;
        Prestamo p = new Prestamo(idNuevo, usuario.getIdUsuario(), monto, interes, meses, 0, cuota, "ACTIVO");
        listaPrestamos.add(p);
        usuario.agregarPrestamo(p);

        ArchivoTexto.guardarLinea("datos/prestamos.txt",
                idNuevo + GUARDAR_SEP + usuario.getIdUsuario() + GUARDAR_SEP + monto + GUARDAR_SEP +
                        interes + GUARDAR_SEP + meses + GUARDAR_SEP + "0" + GUARDAR_SEP + cuota + GUARDAR_SEP + "ACTIVO");

        usuario.agregarTransaccion(new Transaccion(idNuevo, usuario.getIdUsuario(), 0, "Préstamo", monto, "Ingreso"));
        gestorNotificaciones.generarNotificacion(usuario, "Préstamo solicitado por $" + monto + " con interés mensual de " + interes);

        System.out.println("Préstamo aprobado. Cuota mensual: $" + cuota);
    }

    public void pagarCuota(Usuario usuario, Scanner sc) {
        ArrayList<Prestamo> activos = new ArrayList<>();
        for (Prestamo p : usuario.getListaPrestamos())
            if (p.getEstadoPrestamo().equals("ACTIVO")) activos.add(p);

        if (activos.isEmpty()) {
            System.out.println("No tiene préstamos activos.");
            return;
        }

        System.out.println("\n--- PRÉSTAMOS ACTIVOS ---");
        for (int i = 0; i < activos.size(); i++) {
            Prestamo p = activos.get(i);
            System.out.println((i + 1) + ". Monto: $" + p.getMontoSolicitado() +
                    " | Cuota: $" + p.getCuotaMensual() +
                    " | Saldo pendiente: $" + p.getSaldoPendiente());
        }

        System.out.print("Seleccione el préstamo a pagar: ");
        int opPrestamo = Integer.parseInt(sc.nextLine()) - 1;
        if (opPrestamo < 0 || opPrestamo >= activos.size()) return;

        Prestamo prestamoElegido = activos.get(opPrestamo);
        prestamoElegido.pagarCuota();
        usuario.agregarTransaccion(new Transaccion(prestamoElegido.getIdPrestamo(), usuario.getIdUsuario(), 0, "Pago cuota", prestamoElegido.getCuotaMensual(), "Deuda"));
        actualizarPrestamosArchivo();
        System.out.println("Pago de cuota registrado.");
    }

    public void pagarParcial(Usuario usuario, Scanner sc) {
        ArrayList<Prestamo> activos = new ArrayList<>();
        for (Prestamo p : usuario.getListaPrestamos())
            if (p.getEstadoPrestamo().equals("ACTIVO")) activos.add(p);

        if (activos.isEmpty()) {
            System.out.println("No tiene préstamos activos.");
            return;
        }

        System.out.println("\n--- PRÉSTAMOS ACTIVOS ---");
        for (int i = 0; i < activos.size(); i++) {
            Prestamo p = activos.get(i);
            System.out.println((i + 1) + ". Monto: $" + p.getMontoSolicitado() +
                    " | Saldo pendiente: $" + p.getSaldoPendiente());
        }

        System.out.print("Seleccione el préstamo a pagar parcialmente: ");
        int opPrestamo = Integer.parseInt(sc.nextLine()) - 1;
        if (opPrestamo < 0 || opPrestamo >= activos.size()) return;

        Prestamo prestamoElegido = activos.get(opPrestamo);
        System.out.print("Monto a pagar: ");
        double monto = Double.parseDouble(sc.nextLine());
        prestamoElegido.pagarParcial(monto);
        usuario.agregarTransaccion(new Transaccion(prestamoElegido.getIdPrestamo(), usuario.getIdUsuario(), 0, "Pago parcial", monto, "Deuda"));
        actualizarPrestamosArchivo();
        System.out.println("Pago parcial registrado.");
    }

    private void actualizarPrestamosArchivo() {
        ArrayList<String> lineas = new ArrayList<>();
        for (Prestamo p : listaPrestamos) {
            lineas.add(p.getIdPrestamo() + GUARDAR_SEP + p.getIdUsuarioPropietario() + GUARDAR_SEP +
                    p.getMontoSolicitado() + GUARDAR_SEP + p.getInteres() + GUARDAR_SEP +
                    p.getCantidadMeses() + GUARDAR_SEP + p.getMesesPagados() + GUARDAR_SEP +
                    p.getCuotaMensual() + GUARDAR_SEP + p.getEstadoPrestamo());
        }
        ArchivoTexto.sobreescribirArchivo("datos/prestamos.txt", lineas);
    }
}