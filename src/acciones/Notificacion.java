package acciones;

import java.time.LocalDate;

public class Notificacion {
    private int idNotificacion;
    private int idUsuarioPropietario;
    private String mensajeNotificacion;
    private LocalDate fechaNotificacion;

    public Notificacion(int idNotificacion, int idUsuarioPropietario, String mensajeNotificacion) {
        this.idNotificacion = idNotificacion;
        this.idUsuarioPropietario = idUsuarioPropietario;
        this.mensajeNotificacion = mensajeNotificacion;
        this.fechaNotificacion = LocalDate.now();
    }

    public int getIdNotificacion() { return idNotificacion; }
    public int getIdUsuarioPropietario() { return idUsuarioPropietario; }
    public String getMensajeNotificacion() { return mensajeNotificacion; }
    public LocalDate getFechaNotificacion() { return fechaNotificacion; }

    @Override
    public String toString() {
        return ">> " + fechaNotificacion + ": " + mensajeNotificacion;
    }
}
