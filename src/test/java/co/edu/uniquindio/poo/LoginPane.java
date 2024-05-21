package co.edu.uniquindio.poo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPane extends GridPane {

    public LoginPane(Unieventos uniEventos, Stage stage, Runnable onSuccess, Runnable onAdminSuccess) {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);
        setAlignment(Pos.CENTER);

        Label lblIdentificacion = new Label("Identificación:");
        TextField txtIdentificacion = new TextField();
        add(lblIdentificacion, 0, 0);
        add(txtIdentificacion, 1, 0);

        Label lblContrasena = new Label("Contraseña:");
        PasswordField txtContrasena = new PasswordField();
        add(lblContrasena, 0, 1);
        add(txtContrasena, 1, 1);

        Button btnLogin = new Button("Login");
        btnLogin.setOnAction(e -> {
            String identificacion = txtIdentificacion.getText();
            String contrasena = txtContrasena.getText();
            
            if (uniEventos.login(identificacion, contrasena)) {
                stage.close();
                if (uniEventos.getUsuarioActual() instanceof Administrador) {
                    onAdminSuccess.run();
                } else {
                    onSuccess.run();
                }
            } else {
                showError("Identificación o contraseña incorrecta, o cuenta no verificada.");
            }
        });
        add(btnLogin, 1, 2);

        Button btnVolver = new Button("Volver");
        btnVolver.setOnAction(e -> stage.close());
        add(btnVolver, 0, 2);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
