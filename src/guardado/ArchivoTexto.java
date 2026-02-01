package guardado;

import java.io.*;
import java.util.ArrayList;

public class ArchivoTexto {
    public static void verificarArchivo(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            File carpeta = archivo.getParentFile();
            if (carpeta != null && !carpeta.exists()) carpeta.mkdirs();
            if (!archivo.exists()) archivo.createNewFile();
        } catch (Exception errorArchivo) {
            System.out.println("No se pudo crear el archivo o directorio: " + rutaArchivo);
        }
    }

    public static void guardarLinea(String rutaArchivo, String contenido) {
        verificarArchivo(rutaArchivo);
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            escritor.write(contenido);
            escritor.newLine();
        } catch (IOException errorEscritura) {
            System.out.println("Error al guardar en archivo: " + rutaArchivo);
        }
    }

    public static ArrayList<String> leerArchivo(String rutaArchivo) {
        verificarArchivo(rutaArchivo);
        ArrayList<String> lineasArchivo = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String lineaActual;
            while ((lineaActual = lector.readLine()) != null) {
                if (!lineaActual.trim().isEmpty()) lineasArchivo.add(lineaActual);
            }
        } catch (IOException errorLectura) {
            System.out.println("Error al leer archivo: " + rutaArchivo);
        }
        return lineasArchivo;
    }

    public static void sobreescribirArchivo(String rutaArchivo, ArrayList<String> lineas) {
        verificarArchivo(rutaArchivo);
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo, false))) {
            for (String linea : lineas) {
                escritor.write(linea);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al sobrescribir archivo: " + rutaArchivo);
        }
    }
}
