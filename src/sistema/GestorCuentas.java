package sistema;

import acciones.Cuenta;
import acciones.Usuario;
import acciones.Transaccion;
import guardado.ArchivoTexto;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorCuentas {
    private ArrayList<Cuenta> listaCuentas;
    private ArrayList<Usuario> listaUsuarios;
    private GestorNotificaciones gestorNotificaciones;
    private static final String GUARDAR_SEP = " | ";

    public GestorCuentas(ArrayList<Cuenta> listaCuentas,
                         ArrayList<Usuario> listaUsuarios,
                         GestorNotificaciones gestorNotificaciones) {
        this.listaCuentas = listaCuentas;
        this.listaUsuarios = listaUsuarios;
        this.gestorNotificaciones = gestorNotificaciones;
    }

    public void cargarCuentas() {
        ArrayList<String> datosCuentas = ArchivoTexto.leerArchivo("datos/cuentas.txt");
        for (String linea : datosCuentas) {
            String[] d = linea.split("\\|");
            if (d.length >= 3) {
                Cuenta c = new Cuenta(
                        Integer.parseInt(d[0].trim()),
                        Integer.parseInt(d[1].trim()),
                        Double.parseDouble(d[2].trim())
                );
                listaCuentas.add(c);
                listaUsuarios.stream()
                        .filter(u -> u.getIdUsuario() == c.getIdUsuarioPropietario())
                        .forEach(u -> u.agregarCuenta(c));
            }
        }
    }

    public void crearCuenta(Usuario usuario, double saldoInicial) {
        int idNueva = listaCuentas.stream().mapToInt(Cuenta::getIdCuenta).max().orElse(0) + 1;
        Cuenta c = new Cuenta(idNueva, usuario.getIdUsuario(), saldoInicial);
        listaCuentas.add(c);
        usuario.agregarCuenta(c);
        ArchivoTexto.guardarLinea("datos/cuentas.txt",
                idNueva + GUARDAR_SEP + usuario.getIdUsuario() + GUARDAR_SEP + saldoInicial);
        gestorNotificaciones.generarNotificacion(usuario,
                "Se creó una nueva cuenta con saldo inicial de $" + saldoInicial);
    }

    public void depositar(Usuario usuario, Scanner sc) {
        System.out.print("ID Cuenta: ");
        int id = Integer.parseInt(sc.nextLine());
        Cuenta cuenta = buscarCuentaPorId(id);
        if (cuenta != null && cuenta.getIdUsuarioPropietario() == usuario.getIdUsuario()) {
            System.out.print("Monto: ");
            double monto = Double.parseDouble(sc.nextLine());
            cuenta.depositar(monto);
            usuario.agregarTransaccion(new Transaccion(id, usuario.getIdUsuario(), cuenta.getIdCuenta(), "Depósito", monto, "Ingreso"));
            gestorNotificaciones.generarNotificacion(usuario, "Depósito de $" + monto + " en cuenta " + cuenta.getIdCuenta());
            System.out.println("Depósito exitoso.");
        } else System.out.println("Cuenta no encontrada o no te pertenece.");
    }

    public void retirar(Usuario usuario, Scanner sc) {
        System.out.print("ID Cuenta: ");
        int id = Integer.parseInt(sc.nextLine());
        Cuenta cuenta = buscarCuentaPorId(id);
        if (cuenta != null && cuenta.getIdUsuarioPropietario() == usuario.getIdUsuario()) {
            System.out.print("Monto: ");
            double monto = Double.parseDouble(sc.nextLine());
            if (cuenta.retirar(monto)) {
                usuario.agregarTransaccion(new Transaccion(id, usuario.getIdUsuario(), cuenta.getIdCuenta(), "Retiro", monto, "Gasto"));
                gestorNotificaciones.generarNotificacion(usuario, "Retiro de $" + monto + " en cuenta " + cuenta.getIdCuenta());
                System.out.println("Retiro exitoso.");
            } else System.out.println("Saldo insuficiente.");
        } else System.out.println("Cuenta no encontrada.");
    }

    public void transferir(Usuario usuario, Scanner sc, SistemaBilletera sistema) {
        System.out.print("ID Cuenta origen: ");
        Cuenta origen = buscarCuentaPorId(Integer.parseInt(sc.nextLine()));
        System.out.print("ID Cuenta destino: ");
        Cuenta destino = buscarCuentaPorId(Integer.parseInt(sc.nextLine()));
        if (origen != null && destino != null && origen.getIdUsuarioPropietario() == usuario.getIdUsuario()) {
            System.out.print("Monto: ");
            double monto = Double.parseDouble(sc.nextLine());
            if (origen.retirar(monto)) {
                destino.depositar(monto);
                usuario.agregarTransaccion(new Transaccion(destino.getIdCuenta(), usuario.getIdUsuario(), origen.getIdCuenta(), "Transferencia", monto, "Movimiento"));
                gestorNotificaciones.generarNotificacion(usuario, "Transferencia de $" + monto + " desde cuenta " + origen.getIdCuenta() + " hacia cuenta " + destino.getIdCuenta());
                System.out.println("Transferencia exitosa.");
            } else System.out.println("Saldo insuficiente.");
        } else System.out.println("Datos de cuenta incorrectos.");
    }

    private Cuenta buscarCuentaPorId(int id) {
        return listaCuentas.stream().filter(c -> c.getIdCuenta() == id).findFirst().orElse(null);
    }
}