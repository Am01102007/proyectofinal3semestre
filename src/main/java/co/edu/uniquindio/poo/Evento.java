package co.edu.uniquindio.poo;

import lombok.Getter;
import lombok.Setter;
import javafx.scene.image.Image;
import java.time.LocalDate;
import java.io.File;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String ciudad;
    private String descripcion;
    private TipoEvento tipo;
    private String rutaImagen;
    private LocalDate fecha;
    private String direccion;
    private List<Localidad> localidades;

    public Evento(String nombre, String ciudad, String descripcion, TipoEvento tipo, String rutaImagen, LocalDate fecha, String direccion, List<Localidad> localidades) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.rutaImagen = rutaImagen;
        this.fecha = fecha;
        this.direccion = direccion;
        this.localidades = localidades;
    }

    public Image getImagen() {
        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            File file = new File(rutaImagen);
            if (file.exists()) {
                return new Image(file.toURI().toString());
            }
        }
        return null;
    }
}
