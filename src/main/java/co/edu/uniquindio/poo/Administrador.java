package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.util.List;

public class Administrador extends Usuario {
    private static Administrador instance;
    private String contrasena;

    private Administrador(String identificacion, String nombre, String telefono, String email, String contrasena) {
        super(identificacion, nombre, telefono, email);
        this.contrasena = contrasena;
    }

    public static Administrador getInstance() {
        if (instance == null) {
            instance = new Administrador("admin", "Administrador", "0000000000", "admin@unieventos.com", "admin123");
        }
        return instance;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void gestionarEvento(Unieventos uniEventos, String nombre, String ciudad, String descripcion, TipoEvento tipo, String rutaImagen, LocalDate fecha, String direccion, List<Localidad> localidades) {
        Evento evento = new Evento(nombre, ciudad, descripcion, tipo, rutaImagen, fecha, direccion, localidades);
        uniEventos.agregarEvento(evento);
    }
}
