package sistema;

import acciones.*;
import guardado.ArchivoTexto;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaBilletera {
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Cuenta> listaCuentas;
    private ArrayList<Prestamo> listaPrestamos;
    private ArrayList<Notificacion> listaNotificaciones;
    private Scanner entradaScanner;
    private static final String GUARDAR_SEP = " | ";

    public SistemaBilletera() {
        listaUsuarios = new ArrayList<>();
        listaCuentas = new ArrayList<>();
        listaPrestamos = new ArrayList<>();
        listaNotificaciones = new ArrayList<>();
        entradaScanner = new Scanner(System.in);

        cargarUsuarios();
        cargarCuentas();
        cargarPrestamos();
        cargarNotificaciones();
    }

    private void cargarUsuarios() {
        ArrayList<String> datosUsuarios = ArchivoTexto.leerArchivo("datos/usuarios.txt");
        for (String linea : datosUsuarios) {
            String[] d = linea.split("\\|");
            if (d.length >= 3)
                listaUsuarios.add(new Usuario(Integer.parseInt(d[0].trim()), d[1].trim(), d[2].trim()));
        }
    }

    private void cargarCuentas() {
        ArrayList<String> datosCuentas = ArchivoTexto.leerArchivo("datos/cuentas.txt");
        for (String linea : datosCuentas) {
            String[] d = linea.split("\\|");
            if (d.length >= 3) {
                Cuenta c = new Cuenta(Integer.parseInt(d[0].trim()), Integer.parseInt(d[1].trim()), Double.parseDouble(d[2].trim()));
                listaCuentas.add(c);
                listaUsuarios.stream().filter(u -> u.getIdUsuario() == c.getIdUsuarioPropietario()).forEach(u -> u.agregarCuenta(c));
            }
        }
    }

    private void cargarPrestamos() {
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

    private void cargarNotificaciones() {
        ArrayList<String> datosNotificaciones = ArchivoTexto.leerArchivo("datos/notificaciones.txt");
        for (String linea : datosNotificaciones) {
            String[] d = linea.split("\\|");
            if (d.length >= 3) listaNotificaciones.add(new Notificacion(Integer.parseInt(d[0].trim()), Integer.parseInt(d[1].trim()), d[2].trim()));
        }
    }

    public Usuario buscarUsuarioPorNombre(String nombre) {
        return listaUsuarios.stream().filter(u -> u.getNombreUsuario().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }

    public Cuenta buscarCuentaPorId(int id) {
        return listaCuentas.stream().filter(c -> c.getIdCuenta() == id).findFirst().orElse(null);
    }

    public void crearUsuario(String nombre, String contrasena) {
        int idNuevo = listaUsuarios.stream().mapToInt(Usuario::getIdUsuario).max().orElse(0) + 1;
        Usuario u = new Usuario(idNuevo, nombre, contrasena);
        listaUsuarios.add(u);
        ArchivoTexto.guardarLinea("datos/usuarios.txt", idNuevo + GUARDAR_SEP + nombre + GUARDAR_SEP + contrasena);
    }

    public void crearCuenta(Usuario usuario, double saldoInicial) {
        int idNueva = listaCuentas.stream().mapToInt(Cuenta::getIdCuenta).max().orElse(0) + 1;
        Cuenta c = new Cuenta(idNueva, usuario.getIdUsuario(), saldoInicial);
        listaCuentas.add(c);
        usuario.agregarCuenta(c);
        ArchivoTexto.guardarLinea("datos/cuentas.txt", idNueva + GUARDAR_SEP + usuario.getIdUsuario() + GUARDAR_SEP + saldoInicial);
    }

    public void solicitarPrestamo(Usuario usuario, double monto, int meses) {
        double interes = 0.05;
        double cuota = (monto / meses) + (monto * interes);
        int idNuevo = listaPrestamos.stream().mapToInt(Prestamo::getIdPrestamo).max().orElse(0) + 1;
        Prestamo p = new Prestamo(idNuevo, usuario.getIdUsuario(), monto, interes, meses, 0, cuota, "ACTIVO");
        listaPrestamos.add(p);
        usuario.agregarPrestamo(p);

        ArchivoTexto.guardarLinea("datos/prestamos.txt",
                idNuevo + GUARDAR_SEP + usuario.getIdUsuario() + GUARDAR_SEP + monto + GUARDAR_SEP +
                        interes + GUARDAR_SEP + meses + GUARDAR_SEP + "0" + GUARDAR_SEP + cuota + GUARDAR_SEP + "ACTIVO"
        );

        crearNotificacion(usuario.getIdUsuario(), "Se aprobó su préstamo por $" + monto + ". Cuota mensual: $" + cuota);
    }

    public boolean pagarCuota(Usuario usuario) {
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
            System.out.println((i + 1) + ". Monto: $" + p.getMontoSolicitado() +
                    " | Cuota: $" + p.getCuotaMensual() +
                    " | Avance: " + p.getMesesPagados() + "/" + p.getCantidadMeses());
        }

        System.out.print("Seleccione el préstamo a pagar: ");
        int opPrestamo = Integer.parseInt(entradaScanner.nextLine()) - 1;
        if (opPrestamo < 0 || opPrestamo >= activos.size()) return false;

        Prestamo prestamoElegido = activos.get(opPrestamo);

        System.out.println("\n--- TUS CUENTAS ---");
        for (int i = 0; i < usuario.getListaCuentas().size(); i++) {
            Cuenta c = usuario.getListaCuentas().get(i);
            System.out.println((i + 1) + ". Cuenta ID: " + c.getIdCuenta() + " | Saldo: $" + c.getSaldoActual());
        }

        System.out.print("Seleccione la cuenta para pagar: ");
        int opCuenta = Integer.parseInt(entradaScanner.nextLine()) - 1;
        if (opCuenta < 0 || opCuenta >= usuario.getListaCuentas().size()) return false;

        Cuenta cuentaElegida = usuario.getListaCuentas().get(opCuenta);

        if (cuentaElegida.getSaldoActual() >= prestamoElegido.getCuotaMensual()) {
            cuentaElegida.retirar(prestamoElegido.getCuotaMensual());
            prestamoElegido.pagarCuota();
            actualizarPrestamosArchivo();

            crearNotificacion(usuario.getIdUsuario(),
                    "Pago de cuota registrado: $" + prestamoElegido.getCuotaMensual() +
                            " | Avance: " + prestamoElegido.getMesesPagados() + "/" + prestamoElegido.getCantidadMeses());

            if (prestamoElegido.getEstadoPrestamo().equals("FINALIZADO")) {
                System.out.println("¡Préstamo finalizado!");
            }

            return true;
        } else {
            System.out.println("Saldo insuficiente en la cuenta seleccionada.");
            return false;
        }
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

    public void crearNotificacion(int idUsuario, String mensaje) {
        int idNueva = listaNotificaciones.stream().mapToInt(Notificacion::getIdNotificacion).max().orElse(0) + 1;
        Notificacion n = new Notificacion(idNueva, idUsuario, mensaje);
        listaNotificaciones.add(n);
        ArchivoTexto.guardarLinea("datos/notificaciones.txt", idNueva + GUARDAR_SEP + idUsuario + GUARDAR_SEP + mensaje);
    }

    public void verNotificaciones(Usuario usuario) {
        System.out.println("\n=== NOTIFICACIONES ===");
        boolean hay = false;
        for (Notificacion n : listaNotificaciones) {
            if (n.getIdUsuarioPropietario() == usuario.getIdUsuario()) {
                System.out.println(n);
                hay = true;
            }
        }
        if (!hay) System.out.println("No tienes notificaciones nuevas.");
    }

    public void iniciar() {
        System.out.println("========== BIENVENIDO A LA BILLETERA ELECTRÓNICA ==========");
        System.out.print("Ingrese su nombre de usuario: ");
        String nombreIngresado = entradaScanner.nextLine();
        Usuario usuarioActual = buscarUsuarioPorNombre(nombreIngresado);

        if (usuarioActual == null) {
            System.out.println("Usuario no encontrado. ¿Desea crearlo? (s/n)");
            if (entradaScanner.nextLine().equalsIgnoreCase("s")) {
                System.out.print("Ingrese contraseña nueva: ");
                String contraseñaNueva = entradaScanner.nextLine();
                crearUsuario(nombreIngresado, contraseñaNueva);
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
            System.out.println("8. Ver notificaciones");
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
                    crearCuenta(usuarioActual, Double.parseDouble(entradaScanner.nextLine()));
                    System.out.println("Cuenta creada.");
                }
                case 3 -> {
                    System.out.print("ID Cuenta: ");
                    int id = Integer.parseInt(entradaScanner.nextLine());
                    Cuenta cuenta = buscarCuentaPorId(id);
                    if (cuenta != null && cuenta.getIdUsuarioPropietario() == usuarioActual.getIdUsuario()) {
                        System.out.print("Monto: ");
                        cuenta.depositar(Double.parseDouble(entradaScanner.nextLine()));
                        System.out.println("Depósito exitoso.");
                    } else System.out.println("Cuenta no encontrada o no te pertenece.");
                }
                case 4 -> {
                    System.out.print("ID Cuenta: ");
                    int id = Integer.parseInt(entradaScanner.nextLine());
                    Cuenta cuenta = buscarCuentaPorId(id);
                    if (cuenta != null && cuenta.getIdUsuarioPropietario() == usuarioActual.getIdUsuario()) {
                        System.out.print("Monto: ");
                        if (cuenta.retirar(Double.parseDouble(entradaScanner.nextLine())))
                            System.out.println("Retiro exitoso.");
                        else System.out.println("Saldo insuficiente.");
                    } else System.out.println("Cuenta no encontrada.");
                }
                case 5 -> {
                    System.out.print("ID Cuenta origen: ");
                    Cuenta origen = buscarCuentaPorId(Integer.parseInt(entradaScanner.nextLine()));
                    System.out.print("ID Cuenta destino: ");
                    Cuenta destino = buscarCuentaPorId(Integer.parseInt(entradaScanner.nextLine()));
                    if (origen != null && destino != null && origen.getIdUsuarioPropietario() == usuarioActual.getIdUsuario()) {
                        System.out.print("Monto: ");
                        if (origen.transferir(destino, Double.parseDouble(entradaScanner.nextLine()), this, usuarioActual))
                            System.out.println("Transferencia exitosa.");
                        else System.out.println("No se pudo transferir (Saldo insuficiente).");
                    } else System.out.println("Datos de cuenta incorrectos.");
                }
                case 6 -> {
                    System.out.print("Monto solicitado: ");
                    double monto = Double.parseDouble(entradaScanner.nextLine());
                    System.out.print("Meses: ");
                    int meses = Integer.parseInt(entradaScanner.nextLine());
                    solicitarPrestamo(usuarioActual, monto, meses);
                }
                case 7 -> {
                    if (pagarCuota(usuarioActual)) System.out.println("Pago realizado.");
                    else System.out.println("No tiene préstamos activos o saldo insuficiente.");
                }
                case 8 -> verNotificaciones(usuarioActual);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcionMenu != 0);
        System.out.println("Gracias por usar la Billetera Electrónica. ¡Hasta pronto!");
    }
}
