package co.edu.uniquindio.poo;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Factura implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private double subtotal;
    private double total;
    private Cliente cliente;
    private Evento evento;
    private Localidad localidad;
    private boolean usoCupon;
    private String codigo;
    private LocalDate fecha = LocalDate.now();
}
