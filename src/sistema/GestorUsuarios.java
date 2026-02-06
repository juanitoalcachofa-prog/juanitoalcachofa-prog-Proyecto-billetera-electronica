package sistema;

import acciones.Usuario;
import guardado.ArchivoTexto;
import java.util.ArrayList;

public class GestorUsuarios {
    private ArrayList<Usuario> listaUsuarios;
    private static final String GUARDAR_SEP = " | ";

    public GestorUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public void cargarUsuarios() {
        ArrayList<String> datosUsuarios = ArchivoTexto.leerArchivo("datos/usuarios.txt");
        for (String linea : datosUsuarios) {
            String[] d = linea.split("\\|");
            if (d.length >= 4)
                listaUsuarios.add(new Usuario(Integer.parseInt(d[0].trim()), d[1].trim(), d[2].trim(), Double.parseDouble(d[3].trim())));
        }
    }

    public Usuario buscarUsuarioPorNombre(String nombre) {
        return listaUsuarios.stream().filter(u -> u.getNombreUsuario().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }

    public void crearUsuario(String nombre, String contrasena, double sueldo) {
        int idNuevo = listaUsuarios.stream().mapToInt(Usuario::getIdUsuario).max().orElse(0) + 1;
        Usuario u = new Usuario(idNuevo, nombre, contrasena, sueldo);
        listaUsuarios.add(u);
        ArchivoTexto.guardarLinea("datos/usuarios.txt", idNuevo + GUARDAR_SEP + nombre + GUARDAR_SEP + contrasena + GUARDAR_SEP + sueldo);
    }
}