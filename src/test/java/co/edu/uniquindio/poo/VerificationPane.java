package co.edu.uniquindio.poo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class VerificationPane extends GridPane {

    public VerificationPane(Unieventos uniEventos, Stage stage) {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);
        setAlignment(Pos.CENTER);

        Label lblIdentificacion = new Label("Identificación:");
        TextField txtIdentificacion = new TextField();
        add(lblIdentificacion, 0, 0);
        add(txtIdentificacion, 1, 0);

        Label lblCodigo = new Label("Código de Verificación:");
        TextField txtCodigo = new TextField();
        add(lblCodigo, 0, 1);
        add(txtCodigo, 1, 1);

        Button btnVerificar = new Button("Verificar");
        btnVerificar.setOnAction(e -> {
            String identificacion = txtIdentificacion.getText();
            String codigo = txtCodigo.getText();
            
            if (uniEventos.verificarCodigo(identificacion, codigo)) {
                stage.close();
                showSuccess("Cuenta verificada exitosamente. Ahora puede iniciar sesión.");
            } else {
                showError("Código de verificación incorrecto.");
            }
        });
        add(btnVerificar, 1, 2);

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

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
