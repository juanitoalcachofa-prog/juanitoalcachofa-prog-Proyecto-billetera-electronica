package sistema;

import acciones.Usuario;
import acciones.Cuenta;
import acciones.Transaccion;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorGastos {
    private ArrayList<Transaccion> listaTransacciones;
    private GestorNotificaciones gestorNotificaciones;

    public GestorGastos(ArrayList<Transaccion> listaTransacciones, GestorNotificaciones gestorNotificaciones) {
        this.listaTransacciones = listaTransacciones;
        this.gestorNotificaciones = gestorNotificaciones;
    }

    public void registrarGasto(Usuario usuario, Scanner sc) {
        System.out.print("Seleccione cuenta: ");
        int idCuenta = Integer.parseInt(sc.nextLine());
        Cuenta cuenta = usuario.getListaCuentas().stream()
                .filter(c -> c.getIdCuenta() == idCuenta)
                .findFirst()
                .orElse(null);

        if (cuenta == null) {
            System.out.println("Cuenta no encontrada.");
            return;
        }

        System.out.print("Monto del gasto: ");
        double monto = Double.parseDouble(sc.nextLine());
        System.out.print("Categoría del gasto: ");
        String categoria = sc.nextLine();

        if (cuenta.retirar(monto)) {
            int idTransaccion = listaTransacciones.size() + 1;
            Transaccion t = new Transaccion(idTransaccion, usuario.getIdUsuario(),
                                            cuenta.getIdCuenta(), "Gasto", monto, categoria);
            listaTransacciones.add(t);
            usuario.agregarTransaccion(t);

            // Notificación automática
            gestorNotificaciones.generarNotificacion(usuario,
                    "Gasto de $" + monto + " en categoría " + categoria +
                    " desde cuenta " + cuenta.getIdCuenta());

            System.out.println("Gasto registrado correctamente.");
        } else {
            System.out.println("Saldo insuficiente en la cuenta.");
        }
    }
}