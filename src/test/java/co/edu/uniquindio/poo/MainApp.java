package co.edu.uniquindio.poo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Unieventos uniEventos;

    @Override
    public void start(Stage primaryStage) {
        uniEventos = new Unieventos();
        primaryStage.setTitle("UniEventos");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new javafx.geometry.Insets(10));
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);

        // Agregar imagen
        ImageView imageView = new ImageView(new Image("file:C:/Users/ASUS/Desktop/concert.jpg"));
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
        gridPane.add(imageView, 0, 0, 2, 1);

        Button btnRegistrarse = new Button("Registrarse");
        btnRegistrarse.setOnAction(e -> showRegisterWindow(primaryStage));
        gridPane.add(btnRegistrarse, 0, 1);

        Button btnLogin = new Button("Iniciar Sesión");
        btnLogin.setOnAction(e -> showLoginWindow(primaryStage));
        gridPane.add(btnLogin, 1, 1);

        Button btnVerificarse = new Button("Verificarse");
        btnVerificarse.setOnAction(e -> showVerificationWindow(primaryStage));
        gridPane.add(btnVerificarse, 0, 2);

        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction(e -> primaryStage.close());
        gridPane.add(btnSalir, 1, 2);

        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showRegisterWindow(Stage primaryStage) {
        Stage stage = new Stage();
        RegisterPane registerPane = new RegisterPane(uniEventos, stage);
        Scene scene = new Scene(registerPane, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Registrarse");
        stage.show();
    }

    private void showLoginWindow(Stage primaryStage) {
        Stage stage = new Stage();
        LoginPane loginPane = new LoginPane(uniEventos, stage, () -> showPostLoginPage(primaryStage), () -> showAdminMainPage(primaryStage));
        Scene scene = new Scene(loginPane, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Iniciar Sesión");
        stage.show();
    }

    private void showVerificationWindow(Stage primaryStage) {
        Stage stage = new Stage();
        VerificationPane verificationPane = new VerificationPane(uniEventos, stage);
        Scene scene = new Scene(verificationPane, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Verificarse");
        stage.show();
    }

    private void showPostLoginPage(Stage primaryStage) {
        Stage stage = new Stage();
        PostLoginPage postLoginPage = new PostLoginPage(uniEventos, stage);
        Scene scene = new Scene(postLoginPage, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Bienvenido");
        stage.show();
    }

    private void showAdminMainPage(Stage primaryStage) {
        Stage stage = new Stage();
        AdminMainPane adminMainPane = new AdminMainPane(uniEventos, stage, () -> showAdminPane(stage), () -> showCreateCouponPane(stage), () -> showViewChartsPane(stage));
        Scene scene = new Scene(adminMainPane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Panel del Administrador");
        stage.show();
    }

    private void showAdminPane(Stage primaryStage) {
        Stage stage = new Stage();
        AdminPane adminPane = new AdminPane(uniEventos, stage, () -> showAdminMainPage(primaryStage));
        Scene scene = new Scene(adminPane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Crear Evento");
        stage.show();
    }

    private void showCreateCouponPane(Stage primaryStage) {
        Stage stage = new Stage();
        CreateCouponPane createCouponPane = new CreateCouponPane(uniEventos, stage);
        Scene scene = new Scene(createCouponPane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Crear Cupón");
        stage.show();
    }

    private void showViewChartsPane(Stage primaryStage) {
        Stage stage = new Stage();
        ViewChartsPane viewChartsPane = new ViewChartsPane(uniEventos, stage);
        Scene scene = new Scene(viewChartsPane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Estadísticas de Eventos");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
