package co.edu.uniquindio.poo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AdminHomePane extends GridPane {

    public AdminHomePane(Unieventos uniEventos, Stage stage, Runnable onCrearEvento) {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);
        setAlignment(Pos.CENTER);

        Button btnCrearEvento = new Button("Crear Evento");
        btnCrearEvento.setOnAction(e -> onCrearEvento.run());
        add(btnCrearEvento, 0, 0);

        Button btnCrearCupon = new Button("Crear Cupón");
        add(btnCrearCupon, 0, 1);

        Button btnVerGraficos = new Button("Ver Gráficos");
        add(btnVerGraficos, 0, 2);
    }
}


