package co.edu.uniquindio.poo;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
class Localidad implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private double precio;
    private int capacidadMaxima;
    private int capacidadMaximaOriginal; // Añadido para mantener la capacidad original

    public Localidad(String nombre, double precio, int capacidadMaxima) {
        this.nombre = nombre;
        this.precio = precio;
        this.capacidadMaxima = capacidadMaxima;
        this.capacidadMaximaOriginal = capacidadMaxima; // Inicializar con la capacidad máxima original
    }
}



