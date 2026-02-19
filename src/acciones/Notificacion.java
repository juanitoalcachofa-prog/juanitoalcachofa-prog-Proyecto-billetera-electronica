package acciones;

import guardado.ArchivoTexto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Notificacion {
    private int idNotificacion;
    private int idUsuarioPropietario;
    private String mensajeNotificacion;
    private LocalDate fechaNotificacion;

    public Notificacion(int idNotificacion, int idUsuarioPropietario, String mensajeNotificacion) {
        this(idNotificacion, idUsuarioPropietario, mensajeNotificacion, LocalDate.now());
    }

    public Notificacion(int idNotificacion, int idUsuarioPropietario, String mensajeNotificacion,
            LocalDate fechaNotificacion) {
        this.idNotificacion = idNotificacion;
        this.idUsuarioPropietario = idUsuarioPropietario;
        this.mensajeNotificacion = mensajeNotificacion;
        this.fechaNotificacion = fechaNotificacion;
    }

    public int getIdNotificacion() {
        return idNotificacion;
    }

    public int getIdUsuarioPropietario() {
        return idUsuarioPropietario;
    }

    public String getMensajeNotificacion() {
        return mensajeNotificacion;
    }

    public LocalDate getFechaNotificacion() {
        return fechaNotificacion;
    }

    @Override
    public String toString() {
        return ">> " + fechaNotificacion + ": " + mensajeNotificacion;
    }

    public static void crearNotificacion(int idUsuario, String mensaje, List<Notificacion> listaNotificaciones) {
        int idNueva = listaNotificaciones.stream().mapToInt(Notificacion::getIdNotificacion).max().orElse(0) + 1;
        Notificacion n = new Notificacion(idNueva, idUsuario, mensaje);
        listaNotificaciones.add(n);
    }

    public static void verNotificaciones(Usuario usuario, List<Notificacion> listaNotificaciones) {
        System.out.println("\n=== NOTIFICACIONES ===");
        boolean hay = false;
        for (Notificacion n : listaNotificaciones) {
            if (n.getIdUsuarioPropietario() == usuario.getIdUsuario()) {
                System.out.println(n);
                hay = true;
            }
        }
        if (!hay)
            System.out.println("No tienes notificaciones nuevas.");
    }
}
