package co.edu.uniquindio.poo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterPane extends GridPane {

    public RegisterPane(Unieventos uniEventos, Stage stage) {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);
        setAlignment(Pos.CENTER);

        Label lblIdentificacion = new Label("Identificación:");
        TextField txtIdentificacion = new TextField();
        add(lblIdentificacion, 0, 0);
        add(txtIdentificacion, 1, 0);

        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField();
        add(lblNombre, 0, 1);
        add(txtNombre, 1, 1);

        Label lblTelefono = new Label("Teléfono:");
        TextField txtTelefono = new TextField();
        add(lblTelefono, 0, 2);
        add(txtTelefono, 1, 2);

        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();
        add(lblEmail, 0, 3);
        add(txtEmail, 1, 3);

        Label lblContrasena = new Label("Contraseña:");
        TextField txtContrasena = new TextField();
        add(lblContrasena, 0, 4);
        add(txtContrasena, 1, 4);

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.setOnAction(e -> {
            String identificacion = txtIdentificacion.getText();
            String nombre = txtNombre.getText();
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();
            String contrasena = txtContrasena.getText();
            
            uniEventos.registrarCliente(identificacion, nombre, telefono, email, contrasena);
            stage.close();
        });
        add(btnRegistrar, 1, 5);
    }
}

