package co.edu.uniquindio.poo;

public class LoginProxy implements LoginService {
    private RealLogginService realLogginService;

    public LoginProxy(Unieventos unieventos) {
        this.realLogginService = new RealLogginService(unieventos);
    }

    @Override
    public boolean login(String identificacion, String contrasena) {
        // Aquí puedes agregar lógica adicional, como controles de acceso o registro de intentos de login
        return realLogginService.login(identificacion, contrasena);
    }
}