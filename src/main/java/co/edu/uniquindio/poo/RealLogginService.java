package co.edu.uniquindio.poo;

public class RealLogginService implements LoginService {
    private Unieventos unieventos;

    public RealLogginService(Unieventos unieventos) {
        this.unieventos = unieventos;
    }

    @Override
    public boolean login(String identificacion, String contrasena) {
        Cliente cliente = unieventos.buscarClientePorIdentificacion(identificacion);
        if (cliente != null && cliente.getContrasena().equals(contrasena)) {
            if (!cliente.isVerificado()) {
                System.out.println("Debe verificar su cuenta ingresando el código de verificación.");
                return false;
            }
            unieventos.setUsuarioActual(cliente);
            System.out.println("Login exitoso. Bienvenido, " + cliente.getNombre() + "!");
            return true;
        }
        System.out.println("Identificación o contraseña incorrecta.");
        return false;
    }
}
