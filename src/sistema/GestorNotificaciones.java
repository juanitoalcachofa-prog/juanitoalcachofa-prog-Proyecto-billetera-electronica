package sistema;

import acciones.Notificacion;
import acciones.Usuario;
import guardado.ArchivoTexto;
import java.util.ArrayList;

public class GestorNotificaciones {
    private ArrayList<Notificacion> listaNotificaciones;
    private static final String GUARDAR_SEP = " | ";

    public GestorNotificaciones(ArrayList<Notificacion> listaNotificaciones) {
        this.listaNotificaciones = listaNotificaciones;
    }

    public void cargarNotificaciones() {
        ArrayList<String> datosNotificaciones = ArchivoTexto.leerArchivo("datos/notificaciones.txt");
        for (String linea : datosNotificaciones) {
            String[] d = linea.split("\\|");
            if (d.length >= 3) {
                int idNotificacion = Integer.parseInt(d[0].trim());
                int idUsuario = Integer.parseInt(d[1].trim());
                String mensaje = d[2].trim();
                listaNotificaciones.add(new Notificacion(idNotificacion, idUsuario, mensaje));
            }
        }
    }

    public void generarNotificacion(Usuario usuario, String mensaje) {
        int idNueva = listaNotificaciones.stream()
                .mapToInt(Notificacion::getIdNotificacion)
                .max().orElse(0) + 1;
        Notificacion n = new Notificacion(idNueva, usuario.getIdUsuario(), mensaje);
        listaNotificaciones.add(n);
        ArchivoTexto.guardarLinea("datos/notificaciones.txt",
                idNueva + GUARDAR_SEP + usuario.getIdUsuario() + GUARDAR_SEP + mensaje);
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
}