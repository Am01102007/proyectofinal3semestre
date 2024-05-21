package co.edu.uniquindio.poo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String identificacion;
    private String nombre;
    private String telefono;
    private String email;

    // Constructor sin argumentos
    public Usuario() {
    }
}
