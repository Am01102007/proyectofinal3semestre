package co.edu.uniquindio.poo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String contrasena;
    private boolean verificado;
    private String codigoVerificacion;
    private boolean primeraCompra;
    private Map<String, Double> codigosDescuento;

    public Cliente(String identificacion, String nombre, String telefono, String email, String contrasena) {
        super(identificacion, nombre, telefono, email);
        this.contrasena = contrasena;
        this.verificado = false;
        this.codigoVerificacion = CodeGenerator.generateCode();
        this.primeraCompra = false;
        this.codigosDescuento = new HashMap<>();
    }

    // Métodos adicionales

    public boolean isPrimeraCompra() {
        return primeraCompra;
    }

    public void setPrimeraCompra(boolean primeraCompra) {
        this.primeraCompra = primeraCompra;
    }

    public void agregarCodigoDescuento(String codigo, double porcentaje) {
        codigosDescuento.put(codigo, porcentaje);
    }

    public boolean validarCodigoDescuento(String codigo) {
        return codigosDescuento.containsKey(codigo);
    }

    public double obtenerPorcentajeDescuento(String codigo) {
        return codigosDescuento.getOrDefault(codigo, 0.0);
    }

    public void eliminarCodigoDescuento(String codigo) {
        codigosDescuento.remove(codigo);
    }
}
