package co.edu.uniquindio.poo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CreateCouponPane extends GridPane {

    public CreateCouponPane(Unieventos uniEventos, Stage stage) {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);
        setAlignment(Pos.CENTER);

        Label lblDescripcion = new Label("Descripción:");
        TextField txtDescripcion = new TextField();
        add(lblDescripcion, 0, 0);
        add(txtDescripcion, 1, 0);

        Button btnCrear = new Button("Crear Cupón");
        btnCrear.setOnAction(e -> {
            try {
                String descripcion = txtDescripcion.getText();
                String codigoCupon = CodeGenerator.generateCode();

                // Guardar el cupón con 10% de descuento
                uniEventos.crearCupon(codigoCupon);

                showMessage("Cupón creado con éxito. Código: " + codigoCupon);

                // Regresar al panel del administrador
                stage.close();
                showAdminMainPage(new Stage(), uniEventos);
            } catch (NumberFormatException ex) {
                showError("Ingrese una descripción válida.");
            }
        });
        add(btnCrear, 1, 1);

        Button btnVolver = new Button("Volver");
        btnVolver.setOnAction(e -> {
            stage.close();
            showAdminMainPage(new Stage(), uniEventos);
        });
        add(btnVolver, 0, 1);
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAdminMainPage(Stage primaryStage, Unieventos uniEventos) {
        AdminMainPane adminMainPane = new AdminMainPane(uniEventos, primaryStage, () -> showAdminPane(primaryStage, uniEventos), () -> showCreateCouponPane(primaryStage, uniEventos), () -> showViewChartsPane(primaryStage));
        Scene scene = new Scene(adminMainPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Panel del Administrador");
        primaryStage.show();
    }

    private void showAdminPane(Stage primaryStage, Unieventos uniEventos) {
        Stage stage = new Stage();
        AdminPane adminPane = new AdminPane(uniEventos, stage, () -> showAdminMainPage(primaryStage, uniEventos));
        Scene scene = new Scene(adminPane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Crear Evento");
        stage.show();
    }

    private void showCreateCouponPane(Stage primaryStage, Unieventos uniEventos) {
        Stage stage = new Stage();
        CreateCouponPane createCouponPane = new CreateCouponPane(uniEventos, stage);
        Scene scene = new Scene(createCouponPane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Crear Cupón");
        stage.show();
    }

    private void showViewChartsPane(Stage primaryStage) {
        Stage stage = new Stage();
        ViewChartsPane viewChartsPane = new ViewChartsPane(new Unieventos(), stage);
        Scene scene = new Scene(viewChartsPane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Estadísticas de Eventos");
        stage.show();
    }
}

