package acciones;

import guardado.ArchivoTexto;
import sistema.SistemaBilletera;
import java.util.ArrayList;
import java.util.List;

public class Gerente extends Persona {

    public Gerente(int id, String nombre, String contraseña) {
        super(id, nombre, contraseña);
    }

    public int getIdGerente() {
        return id;
    }

    public String getNombreGerente() {
        return nombre;
    }

    public void enviarMensajePersonalizado(Usuario usuario, String mensaje,
            List<Notificacion> listaNotificaciones) {
        Notificacion.crearNotificacion(usuario.getIdUsuario(), "[GERENCIA]: " + mensaje, listaNotificaciones);
    }

    public static void crearGerente(String nombre, String contraseña, ArrayList<Gerente> listaGerentes) {
        int idNuevo = listaGerentes.stream().mapToInt(Gerente::getIdGerente).max().orElse(0) + 1;
        Gerente g = new Gerente(idNuevo, nombre, contraseña);
        listaGerentes.add(g);
    }
}
