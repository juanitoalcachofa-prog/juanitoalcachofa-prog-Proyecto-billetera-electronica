package sistema;

import acciones.Usuario;
import acciones.Cuenta;
import acciones.Prestamo;
import acciones.Transaccion;
import acciones.Notificacion;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaBilletera {
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Cuenta> listaCuentas;
    private ArrayList<Prestamo> listaPrestamos;
    private ArrayList<Notificacion> listaNotificaciones;
    private ArrayList<Transaccion> listaTransacciones;
    private Scanner entradaScanner;
    private static final String GUARDAR_SEP = " | ";

    private GestorUsuarios gestorUsuarios;
    private GestorCuentas gestorCuentas;
    private GestorPrestamos gestorPrestamos;
    private GestorNotificaciones gestorNotificaciones;
    private GestorGastos gestorGastos;
    private GestorConsejos gestorConsejos;

    public SistemaBilletera() {
        listaUsuarios = new ArrayList<>();
        listaCuentas = new ArrayList<>();
        listaPrestamos = new ArrayList<>();
        listaNotificaciones = new ArrayList<>();
        listaTransacciones = new ArrayList<>();
        entradaScanner = new Scanner(System.in);

        gestorUsuarios = new GestorUsuarios(listaUsuarios);
        gestorCuentas = new GestorCuentas(listaCuentas, listaUsuarios, gestorNotificaciones);
        gestorNotificaciones = new GestorNotificaciones(listaNotificaciones);
        gestorPrestamos = new GestorPrestamos(listaPrestamos, listaUsuarios, gestorNotificaciones);
        gestorGastos = new GestorGastos(listaTransacciones, gestorNotificaciones);
        gestorConsejos = new GestorConsejos();
        gestorUsuarios.cargarUsuarios();
        gestorCuentas.cargarCuentas();
        gestorPrestamos.cargarPrestamos();
        gestorNotificaciones.cargarNotificaciones();
    }

    public void iniciar() {
        System.out.println("========== BIENVENIDO A LA BILLETERA ELECTRÓNICA ==========");
        System.out.print("Ingrese su nombre de usuario: ");
        String nombreIngresado = entradaScanner.nextLine();
        Usuario usuarioActual = gestorUsuarios.buscarUsuarioPorNombre(nombreIngresado);

        if (usuarioActual == null) {
            System.out.println("Usuario no encontrado. ¿Desea crearlo? (s/n)");
            if (entradaScanner.nextLine().equalsIgnoreCase("s")) {
                System.out.print("Ingrese contraseña nueva: ");
                String contrasenaNueva = entradaScanner.nextLine();
                System.out.print("Ingrese su sueldo mensual: ");
                double sueldo = Double.parseDouble(entradaScanner.nextLine());
                gestorUsuarios.crearUsuario(nombreIngresado, contrasenaNueva, sueldo);
                System.out.println("Usuario creado exitosamente. Ejecute de nuevo para ingresar.");
            }
            return;
        }

        System.out.print("Contraseña: ");
        if (!usuarioActual.autenticar(entradaScanner.nextLine())) {
            System.out.println("Contraseña incorrecta.");
            return;
        }

        int opcionMenu;
        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Ver cuentas");
            System.out.println("2. Crear cuenta");
            System.out.println("3. Depositar");
            System.out.println("4. Retirar");
            System.out.println("5. Transferir");
            System.out.println("6. Solicitar préstamo");
            System.out.println("7. Pagar cuota");
            System.out.println("8. Pagar préstamo parcial");
            System.out.println("9. Registrar gasto");
            System.out.println("10. Ver notificaciones");
            System.out.println("11. Ver consejos financieros");
            System.out.println("0. Salir");
            System.out.print("Elija opción: ");

            try {
                opcionMenu = Integer.parseInt(entradaScanner.nextLine());
            } catch (NumberFormatException e) { opcionMenu = -1; }

            switch (opcionMenu) {
                case 1 -> usuarioActual.getListaCuentas().forEach(
                        c -> System.out.println("Cuenta ID: " + c.getIdCuenta() + " | Saldo: $" + c.getSaldoActual()));
                case 2 -> {
                    System.out.print("Saldo inicial: ");
                    gestorCuentas.crearCuenta(usuarioActual, Double.parseDouble(entradaScanner.nextLine()));
                    System.out.println("Cuenta creada.");
                }
                case 3 -> gestorCuentas.depositar(usuarioActual, entradaScanner);
                case 4 -> gestorCuentas.retirar(usuarioActual, entradaScanner);
                case 5 -> gestorCuentas.transferir(usuarioActual, entradaScanner, this);
                case 6 -> gestorPrestamos.solicitarPrestamo(usuarioActual, entradaScanner);
                case 7 -> gestorPrestamos.pagarCuota(usuarioActual, entradaScanner);
                case 8 -> gestorPrestamos.pagarParcial(usuarioActual, entradaScanner);
                case 9 -> gestorGastos.registrarGasto(usuarioActual, entradaScanner);
                case 10 -> gestorNotificaciones.verNotificaciones(usuarioActual);
                case 11 -> System.out.println(gestorConsejos.generarConsejos(usuarioActual));
                case 12 -> {GestorReportes gestorReportes = new GestorReportes();
    System.out.println(gestorReportes.generarReporteFinanciero(usuarioActual));
}
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcionMenu != 0);

        System.out.println("Gracias por usar la Billetera Electrónica. ¡Hasta pronto!");
    }
}