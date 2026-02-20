package sistema;

import acciones.Usuario;
import acciones.Gerente;
import acciones.Cuenta;
import acciones.Prestamo;
import acciones.Notificacion;
import acciones.Gasto;
import acciones.AnalisisFinanzas;
import acciones.Autenticable;
import guardado.ArchivoTexto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class SistemaBilletera {
    public ArrayList<Usuario> listaUsuarios;
    public HashMap<Integer, Usuario> mapaUsuarios;
    public ArrayList<Gerente> listaGerentes;
    public ArrayList<Cuenta> listaCuentas;
    public ArrayList<Prestamo> listaPrestamos;
    public LinkedList<Notificacion> listaNotificaciones;
    public ArrayList<Gasto> listaGastos;
    private Scanner entradaScanner;


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    private static final String GUARDAR_SEP = " | ";
    private static final String DATA_PATH = "src/datos/";

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public ArrayList<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    public ArrayList<Prestamo> getListaPrestamos() {
        return listaPrestamos;
    }

    public ArrayList<Gasto> getListaGastos() {
        return listaGastos;
    }

    public LinkedList<Notificacion> getListaNotificaciones() {
        return listaNotificaciones;
    }

    public SistemaBilletera() {
        listaUsuarios = new ArrayList<>();
        listaGerentes = new ArrayList<>();
        listaCuentas = new ArrayList<>();
        listaPrestamos = new ArrayList<>();
        mapaUsuarios = new HashMap<>();
        listaNotificaciones = new LinkedList<>();
        listaGastos = new ArrayList<>();
        entradaScanner = new Scanner(System.in);

        cargarUsuarios();
        cargarGerentes();
        cargarCuentas();
        cargarPrestamos();
        cargarNotificaciones();
        cargarGastos();
    }

    private void cargarUsuarios() {
        ArrayList<String> datosUsuarios = ArchivoTexto.leerArchivo(DATA_PATH + "usuarios.txt");
        for (String linea : datosUsuarios) {
            String[] d = linea.split("\\|");
            if (d.length >= 3) {
                int id = Integer.parseInt(d[0].trim());
                String nombre = d[1].trim();
                String pass = d[2].trim();
                double ingreso = (d.length >= 4) ? Double.parseDouble(d[3].trim()) : 0.0;

                Usuario u = new Usuario(id, nombre, pass, ingreso);
                listaUsuarios.add(u);
                mapaUsuarios.put(u.getIdUsuario(), u);
            }
        }
    }

    private void cargarGerentes() {
        ArrayList<String> datosGerentes = ArchivoTexto.leerArchivo(DATA_PATH + "gerentes.txt");
        if (datosGerentes.isEmpty()) {
            listaGerentes.add(new Gerente(1, "admin", "admin123"));
            guardarGerentes();
        } else {
            for (String linea : datosGerentes) {
                String[] d = linea.split("\\|");
                if (d.length >= 3)
                    listaGerentes.add(new Gerente(Integer.parseInt(d[0].trim()), d[1].trim(), d[2].trim()));
            }
        }
    }

    public void guardarGerentes() {
        ArrayList<String> lineas = new ArrayList<>();
        for (Gerente g : listaGerentes) {
            lineas.add(g.getIdGerente() + GUARDAR_SEP + g.getNombreGerente() + GUARDAR_SEP + g.getContraseña());
        }
        ArchivoTexto.sobreescribirArchivo(DATA_PATH + "gerentes.txt", lineas);
    }

    public void guardarUsuarios() {
        ArrayList<String> lineas = new ArrayList<>();
        for (Usuario u : listaUsuarios) {
            lineas.add(u.getIdUsuario() + GUARDAR_SEP + u.getNombreUsuario() + GUARDAR_SEP + u.getContraseña()
                    + GUARDAR_SEP
                    + u.getIngresoMensual());
        }
        ArchivoTexto.sobreescribirArchivo(DATA_PATH + "usuarios.txt", lineas);
    }

    public void guardarCuentas() {
        ArrayList<String> lineas = new ArrayList<>();
        for (Cuenta c : listaCuentas) {
            lineas.add(c.getIdCuenta() + GUARDAR_SEP + c.getIdUsuarioPropietario() + GUARDAR_SEP + c.getSaldoActual());
        }
        ArchivoTexto.sobreescribirArchivo(DATA_PATH + "cuentas.txt", lineas);
    }

    private void cargarCuentas() {
        ArrayList<String> datosCuentas = ArchivoTexto.leerArchivo(DATA_PATH + "cuentas.txt");
        for (String linea : datosCuentas) {
            String[] d = linea.split("\\|");
            if (d.length >= 3) {
                int idC = Integer.parseInt(d[0].trim());
                int idU = Integer.parseInt(d[1].trim());
                double saldo = Double.parseDouble(d[2].trim());
                Cuenta c = new Cuenta(idC, idU, saldo);
                listaCuentas.add(c);
                Usuario pro = mapaUsuarios.get(idU);
                if (pro != null)
                    pro.agregarCuenta(c);
            }
        }
    }

    private void cargarPrestamos() {
        ArrayList<String> datosPrestamos = ArchivoTexto.leerArchivo(DATA_PATH + "prestamos.txt");
        for (String linea : datosPrestamos) {
            String[] d = linea.split("\\|");
            if (d.length >= 9) {
                int id = Integer.parseInt(d[0].trim());
                int idU = Integer.parseInt(d[1].trim());
                double monto = Double.parseDouble(d[2].trim());
                double intPer = Double.parseDouble(d[3].trim());
                int meses = Integer.parseInt(d[4].trim());
                double pagados = Double.parseDouble(d[5].trim());
                double cuota = Double.parseDouble(d[6].trim());
                String est = d[7].trim();
                String tipo = d[8].trim();

                Prestamo p = new Prestamo(id, idU, monto, intPer, meses, pagados, cuota, est, tipo);
                listaPrestamos.add(p);
                Usuario u = mapaUsuarios.get(idU);
                if (u != null)
                    u.agregarPrestamo(p);
            }
        }
    }

    private void cargarNotificaciones() {
        ArrayList<String> datosNotificaciones = ArchivoTexto.leerArchivo(DATA_PATH + "notificaciones.txt");
        for (String linea : datosNotificaciones) {
            String[] d = linea.split("\\|");
            if (d.length >= 3) {
                int id = Integer.parseInt(d[0].trim());
                int idU = Integer.parseInt(d[1].trim());
                // El mensaje puede contener | accidentalmente, así que tomamos la última parte
                // como fecha
                // y el resto como mensaje si d.length > 3
                String mensaje = d[2].trim();
                LocalDate fecha = LocalDate.now();

                if (d.length >= 4) {
                    try {
                        fecha = LocalDate.parse(d[d.length - 1].trim());
                        // Si era mayor a 4, reconstruimos el mensaje con los pipes que pertenecían a él
                        if (d.length > 4) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 2; i < d.length - 1; i++) {
                                sb.append(d[i]);
                                if (i < d.length - 2)
                                    sb.append("|");
                            }
                            mensaje = sb.toString().trim();
                        }
                    } catch (Exception e) {
                        // Si falla el parseo de la última parte, asumimos que no hay fecha y d[2...] es
                        // mensaje
                    }
                }
                listaNotificaciones.add(new Notificacion(id, idU, mensaje, fecha));
            }
        }
    }

    public void guardarNotificaciones() {
        ArrayList<String> lineas = new ArrayList<>();
        for (Notificacion n : listaNotificaciones) {
            lineas.add(n.getIdNotificacion() + GUARDAR_SEP + n.getIdUsuarioPropietario() + GUARDAR_SEP
                    + n.getMensajeNotificacion() + GUARDAR_SEP + n.getFechaNotificacion());
        }
        ArchivoTexto.sobreescribirArchivo(DATA_PATH + "notificaciones.txt", lineas);
    }

    private void cargarGastos() {
        ArrayList<String> datosGastos = ArchivoTexto.leerArchivo(DATA_PATH + "gastos.txt");
        for (String linea : datosGastos) {
            String[] d = linea.split("\\|");
            if (d.length >= 5) {
                Gasto g = new Gasto(Integer.parseInt(d[0].trim()), Integer.parseInt(d[1].trim()),
                        Double.parseDouble(d[2].trim()), d[3].trim(), LocalDate.parse(d[4].trim()));
                listaGastos.add(g);
                listaUsuarios.stream().filter(u -> u.getIdUsuario() == g.getIdUsuario())
                        .forEach(u -> u.agregarGasto(g));
            }
        }
    }

    public void guardarGastos() {
        ArrayList<String> lineas = new ArrayList<>();
        for (Gasto g : listaGastos) {
            lineas.add(g.getIdGasto() + GUARDAR_SEP + g.getIdUsuario() + GUARDAR_SEP +
                    g.getMonto() + GUARDAR_SEP + g.getCategoria() + GUARDAR_SEP + g.getFecha());
        }
        ArchivoTexto.sobreescribirArchivo(DATA_PATH + "gastos.txt", lineas);
    }

    public Usuario buscarUsuarioPorNombre(String nombre) {
        return listaUsuarios.stream().filter(u -> u.getNombreUsuario().equalsIgnoreCase(nombre)).findFirst()
                .orElse(null);
    }

    public Gerente buscarGerentePorNombre(String nombre) {
        return listaGerentes.stream().filter(g -> g.getNombreGerente().equalsIgnoreCase(nombre)).findFirst()
                .orElse(null);
    }

    public Cuenta buscarCuentaPorId(int id) {
        return listaCuentas.stream().filter(c -> c.getIdCuenta() == id).findFirst().orElse(null);
    }

    public Usuario buscarUsuarioPorId(int id) {
        return mapaUsuarios.get(id);
    }

    public void crearUsuario(String nombre, String contraseña, double ingreso) {
        Usuario u = Usuario.crearUsuario(nombre, contraseña, ingreso, listaUsuarios, listaNotificaciones);
        mapaUsuarios.put(u.getIdUsuario(), u);
        guardarUsuarios();
        guardarNotificaciones();
    }

    public void crearGerente(String nombre, String contraseña) {
        Gerente.crearGerente(nombre, contraseña, listaGerentes);
        guardarGerentes();
    }

    public void crearCuenta(Usuario usuario, double saldoInicial) {
        Cuenta.crearCuenta(usuario, saldoInicial, listaCuentas, listaNotificaciones);
        guardarCuentas();
        guardarNotificaciones();
    }

    public void solicitarPrestamo(Usuario usuario, double monto, int meses) {
        System.out.println("--- PERSONALIZACIÓN DE PRÉSTAMO ---");
        System.out.print("Tipo de préstamo (Personal, Hipotecario, etc.): ");
        String tipo = entradaScanner.nextLine();
        System.out.print("Tasa de interés anual sugerida (Ej: 0.10 para 10%): ");
        double tasa = Double.parseDouble(entradaScanner.nextLine());

        Prestamo.crearPrestamo(usuario, monto, meses, tasa, tipo, listaPrestamos, listaNotificaciones);
        actualizarPrestamosArchivo();
        guardarNotificaciones();
    }

    public boolean pagarCuota(Usuario usuario) {
        boolean exito = Prestamo.pagarCuota(usuario, entradaScanner, listaNotificaciones);
        if (exito) {
            actualizarPrestamosArchivo();
            guardarCuentas();
            guardarNotificaciones();
        }
        return exito;
    }

    public boolean transferirDinero(Usuario usuario, Cuenta origen, int idCuentaDestino, double monto) {
        // 1. Buscar la cuenta destino en TODO el sistema
        Cuenta destino = null;
        for (Cuenta c : listaCuentas) {
            if (c.getIdCuenta() == idCuentaDestino) {
                destino = c;
                break;
            }
        }

        if (destino == null)
            return false;
        if (origen.getIdCuenta() == destino.getIdCuenta())
            return false;

        // 2. Ejecutar la operación usando tus métodos originales
        if (origen.retirar(monto, listaNotificaciones)) {
            destino.depositar(monto, listaNotificaciones);

            // 3. Notificación personalizada de transferencia
            Notificacion.crearNotificacion(usuario.getIdUsuario(),
                    "Transferencia enviada de cuenta " + origen.getIdCuenta() + " a " + idCuentaDestino + ": $"
                            + String.format("%.2f", monto),
                    listaNotificaciones);

            // 4. Guardar cambios
            guardarCuentas();
            guardarNotificaciones();
            return true;
        }
        return false;
    }

    public void actualizarPrestamosArchivo() {
        ArrayList<String> lineas = new ArrayList<>();
        for (Prestamo p : listaPrestamos) {
            lineas.add(p.getIdPrestamo() + GUARDAR_SEP + p.getIdUsuarioPropietario() + GUARDAR_SEP +
                    p.getMontoSolicitado() + GUARDAR_SEP + p.getInteres() + GUARDAR_SEP +
                    p.getCantidadMeses() + GUARDAR_SEP + p.getMesesPagados() + GUARDAR_SEP +
                    p.getCuotaMensual() + GUARDAR_SEP + p.getEstadoPrestamo() + GUARDAR_SEP + p.getTipoPrestamo());
        }
        ArchivoTexto.sobreescribirArchivo(DATA_PATH + "prestamos.txt", lineas);
    }

    public void crearNotificacion(int idUsuario, String mensaje) {
        Notificacion.crearNotificacion(idUsuario, mensaje, listaNotificaciones);
    }

    public void verNotificaciones(Usuario usuario) {
        Notificacion.verNotificaciones(usuario, listaNotificaciones);
    }

    public void iniciar() {
        int opcionInicial;
        do {
            System.out.println("\n========== BILLETERA ELECTRÓNICA ==========");
            System.out.println("1. Ingresar como Usuario");
            System.out.println("2. Ingresar como Gerente");
            System.out.println("3. Crear nuevo Usuario");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");

            try {
                opcionInicial = Integer.parseInt(entradaScanner.nextLine());
            } catch (NumberFormatException e) {
                opcionInicial = -1;
            }

            switch (opcionInicial) {
                case 1 -> menuUsuario();
                case 2 -> menuGerente();
                case 3 -> {
                    System.out.print("Nombre de usuario nuevo: ");
                    String nombre = entradaScanner.nextLine();
                    System.out.print("Contraseña: ");
                    String pass = entradaScanner.nextLine();
                    System.out.print("Ingreso mensual: ");
                    double ingreso = Double.parseDouble(entradaScanner.nextLine());
                    crearUsuario(nombre, pass, ingreso);
                    System.out.println("Usuario creado exitosamente.");
                }
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcionInicial != 0);
    }

    private void menuUsuario() {
        System.out.print("Nombre de usuario: ");
        String nombre = entradaScanner.nextLine();
        Usuario usuarioActual = buscarUsuarioPorNombre(nombre);

        if (usuarioActual == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        System.out.print("Contraseña: ");
        if (!usuarioActual.autenticar(entradaScanner.nextLine())) {
            System.out.println("Contraseña incorrecta.");
            return;
        }

        int opcion;
        do {
            System.out.println("\n--- MENÚ USUARIO: " + usuarioActual.getNombreUsuario().toUpperCase() + " ---");
            System.out.println("1. Ver cuentas y saldo total");
            System.out.println("2. Crear cuenta");
            System.out.println("3. Registrar Ingreso (Depósito)");
            System.out.println("4. Registrar Gasto / Transferencia");
            System.out.println("5. Crear Préstamo");
            System.out.println("6. Pagar Cuota de Préstamo");
            System.out.println("7. Ver notificaciones");
            System.out.println("8. Reporte de Salud Financiera");
            System.out.println("0. Volver al inicio");
            System.out.print("Elija opción: ");

            try {
                opcion = Integer.parseInt(entradaScanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> {
                    usuarioActual.getListaCuentas().forEach(c -> System.out
                            .println("Cuenta ID: " + c.getIdCuenta() + " | Saldo: $" + c.getSaldoActual()));
                    System.out.println("SALDO TOTAL: $" + usuarioActual.getSaldoTotal());
                }
                case 2 -> {
                    System.out.print("Saldo inicial: ");
                    crearCuenta(usuarioActual, Double.parseDouble(entradaScanner.nextLine()));
                }
                case 3 -> {
                    System.out.print("ID Cuenta: ");
                    Cuenta c = buscarCuentaPorId(Integer.parseInt(entradaScanner.nextLine()));
                    if (c != null && c.getIdUsuarioPropietario() == usuarioActual.getIdUsuario()) {
                        System.out.print("Monto: ");
                        c.depositar(Double.parseDouble(entradaScanner.nextLine()), listaNotificaciones);
                        guardarCuentas();
                        guardarNotificaciones();
                        System.out.println("Depósito exitoso.");
                    } else
                        System.out.println("Cuenta no encontrada.");
                }
                case 4 -> {
                    System.out.print("ID Cuenta: ");
                    Cuenta c = buscarCuentaPorId(Integer.parseInt(entradaScanner.nextLine()));
                    if (c != null && c.getIdUsuarioPropietario() == usuarioActual.getIdUsuario()) {
                        System.out.println(
                                "Categorías: 1.Alimentación, 2.Transporte, 3.Salud, 4.Hogar, 5.Entretenimiento, 6.Transferencia, 7.Otros");
                        System.out.print("Elija categoría: ");
                        int catOp = Integer.parseInt(entradaScanner.nextLine());
                        String cat = switch (catOp) {
                            case 1 -> "Alimentación";
                            case 2 -> "Transporte";
                            case 3 -> "Salud";
                            case 4 -> "Hogar";
                            case 5 -> "Entretenimiento";
                            case 6 -> "Transferencia";
                            default -> "Otros";
                        };
                        System.out.print("Monto: ");
                        double monto = Double.parseDouble(entradaScanner.nextLine());
                        if (c.retirar(monto, listaNotificaciones)) {
                            int idG = listaGastos.stream().mapToInt(Gasto::getIdGasto).max().orElse(0) + 1;
                            Gasto g = new Gasto(idG, usuarioActual.getIdUsuario(), monto, cat, LocalDate.now());
                            listaGastos.add(g);
                            usuarioActual.agregarGasto(g);
                            guardarGastos();
                            guardarCuentas();
                            guardarNotificaciones();
                            System.out.println("Gasto registrado exitosamente.");
                        } else
                            System.out.println("Saldo insuficiente.");
                    } else
                        System.out.println("Cuenta no encontrada.");
                }
                case 5 -> {
                    System.out.print("Monto para préstamo: ");
                    double m = Double.parseDouble(entradaScanner.nextLine());
                    System.out.print("Meses: ");
                    int meses = Integer.parseInt(entradaScanner.nextLine());
                    solicitarPrestamo(usuarioActual, m, meses);
                }
                case 6 -> pagarCuota(usuarioActual);
                case 7 -> verNotificaciones(usuarioActual);
                case 8 -> {
                    System.out.println(usuarioActual.generarResumen());
                    AnalisisFinanzas.generarConsejosDetallados(usuarioActual).forEach(System.out::println);
                }
                case 0 -> System.out.println("Regresando...");
            }
        } while (opcion != 0 && opcion != -1);
    }

    private void menuGerente() {
        System.out.print("Nombre de gerente: ");
        String nombre = entradaScanner.nextLine();
        Gerente gerenteActual = buscarGerentePorNombre(nombre);

        if (gerenteActual == null) {
            System.out.println("Gerente no encontrado.");
            return;
        }

        System.out.print("Contraseña: ");
        if (!gerenteActual.autenticar(entradaScanner.nextLine())) {
            System.out.println("Contraseña incorrecta.");
            return;
        }

        int opcion;
        do {
            System.out.println("\n--- PANEL DE GERENCIA: " + gerenteActual.getNombreGerente().toUpperCase() + " ---");
            System.out.println("1. Listar Usuarios");
            System.out.println("2. Enviar Mensaje Personalizado");
            System.out.println("0. Volver al inicio");
            System.out.print("Elija opción: ");

            try {
                opcion = Integer.parseInt(entradaScanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> {
                    System.out.println("\n--- LISTADO DE USUARIOS (SALUD FINANCIERA) ---");
                    for (Usuario u : listaUsuarios) {
                        double ingresos = u.getIngresoMensual();
                        double gastos = u.getGastosTotales();
                        double deudas = u.getDeudasTotales();
                        double ratio = (ingresos > 0) ? (gastos / ingresos) : 1.0;

                        String colorSalud = (ratio > 0.8) ? ANSI_RED : (ratio > 0.5) ? ANSI_YELLOW : ANSI_GREEN;
                        String colorId = (deudas > 0) ? ANSI_RED : ANSI_GREEN;

                        System.out.println(colorId + "[ID: " + u.getIdUsuario() + "]" + ANSI_RESET +
                                " | Nombre: " + colorSalud + u.getNombreUsuario().toUpperCase() + ANSI_RESET +
                                " | Ingreso: $" + ingresos + " | Gastos: $" + gastos + " | Deuda: $"
                                + String.format("%.2f", deudas));
                    }
                }
                case 2 -> {
                    System.out.print("ID de usuario destino: ");
                    int id = Integer.parseInt(entradaScanner.nextLine());
                    Usuario uDestino = listaUsuarios.stream().filter(u -> u.getIdUsuario() == id).findFirst()
                            .orElse(null);
                    if (uDestino != null) {
                        System.out.print("Mensaje: ");
                        String msg = entradaScanner.nextLine();
                        gerenteActual.enviarMensajePersonalizado(uDestino, msg, listaNotificaciones);
                        guardarNotificaciones();
                        System.out.println("Mensaje enviado.");
                    } else
                        System.out.println("Usuario no encontrado.");
                }
                case 0 -> System.out.println("Regresando...");
            }
        } while (opcion != 0 && opcion != -1);
    }
}
