package co.edu.uniquindio.poo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class PostLoginPage extends VBox {

    private Unieventos uniEventos;
    private Stage stage;

    public PostLoginPage(Unieventos uniEventos, Stage stage) {
        this.uniEventos = uniEventos;
        this.stage = stage;

        setPadding(new Insets(10));
        setSpacing(10);
        setAlignment(Pos.CENTER);

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);

        TextField txtBuscarNombre = new TextField();
        txtBuscarNombre.setPromptText("Buscar por nombre");
        Button btnBuscarNombre = new Button("Buscar por Nombre");

        ComboBox<TipoEvento> cbTipoEvento = new ComboBox<>();
        cbTipoEvento.getItems().setAll(TipoEvento.values());
        cbTipoEvento.setPromptText("Seleccionar tipo de evento");
        Button btnBuscarTipo = new Button("Buscar por Tipo");

        TextField txtBuscarCiudad = new TextField();
        txtBuscarCiudad.setPromptText("Buscar por ciudad");
        Button btnBuscarCiudad = new Button("Buscar por Ciudad");

        searchBox.getChildren().addAll(txtBuscarNombre, btnBuscarNombre, cbTipoEvento, btnBuscarTipo, txtBuscarCiudad, btnBuscarCiudad);

        getChildren().add(searchBox);

        HBox eventsBox = new HBox(10);
        eventsBox.setAlignment(Pos.CENTER);

        btnBuscarNombre.setOnAction(e -> {
            String nombre = txtBuscarNombre.getText().trim();
            List<Evento> eventosFiltrados = buscarEventosPorNombre(nombre);
            mostrarEventos(eventsBox, eventosFiltrados);
        });

        btnBuscarTipo.setOnAction(e -> {
            TipoEvento tipo = cbTipoEvento.getValue();
            List<Evento> eventosFiltrados = buscarEventosPorTipo(tipo);
            mostrarEventos(eventsBox, eventosFiltrados);
        });

        btnBuscarCiudad.setOnAction(e -> {
            String ciudad = txtBuscarCiudad.getText().trim();
            List<Evento> eventosFiltrados = buscarEventosPorCiudad(ciudad);
            mostrarEventos(eventsBox, eventosFiltrados);
        });

        mostrarEventos(eventsBox, uniEventos.getEventos()); // Mostrar todos los eventos inicialmente
        getChildren().add(eventsBox);

        Button btnVerCompras = new Button("Ver Lista de Compras");
        btnVerCompras.setOnAction(e -> showListaComprasWindow());
        getChildren().add(btnVerCompras);
    }

    private List<Evento> buscarEventosPorNombre(String nombre) {
        return uniEventos.getEventos().stream()
                .filter(evento -> evento.getNombre().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());
    }

    private List<Evento> buscarEventosPorTipo(TipoEvento tipo) {
        return uniEventos.getEventos().stream()
                .filter(evento -> evento.getTipo() == tipo)
                .collect(Collectors.toList());
    }

    private List<Evento> buscarEventosPorCiudad(String ciudad) {
        return uniEventos.getEventos().stream()
                .filter(evento -> evento.getCiudad().equalsIgnoreCase(ciudad))
                .collect(Collectors.toList());
    }

    private void mostrarEventos(HBox eventsBox, List<Evento> eventos) {
        eventsBox.getChildren().clear();
        for (Evento evento : eventos) {
            VBox eventBox = new VBox(10);
            eventBox.setAlignment(Pos.CENTER);

            ImageView imageView = new ImageView(new Image(new File(evento.getRutaImagen()).toURI().toString()));
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            Label lblNombre = new Label(evento.getNombre());
            Label lblDescripcion = new Label(evento.getDescripcion());
            Button btnComprar = new Button("Realizar compra");
            btnComprar.setOnAction(e -> showCompraWindow(evento));

            eventBox.getChildren().addAll(imageView, lblNombre, lblDescripcion, btnComprar);
            eventsBox.getChildren().add(eventBox);
        }
    }

    private void showCompraWindow(Evento evento) {
        Stage compraStage = new Stage();
        CompraPane compraPane = new CompraPane(uniEventos, evento, stage);
        Scene scene = new Scene(compraPane, 600, 400);
        compraStage.setScene(scene);
        compraStage.setTitle("Compra de Entradas");
        compraStage.show();
    }

    private void showListaComprasWindow() {
        Cliente clienteActual = (Cliente) uniEventos.getUsuarioActual();
        List<Factura> compras = uniEventos.obtenerComprasCliente(clienteActual.getIdentificacion());

        Stage comprasStage = new Stage();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        int row = 0;
        for (Factura factura : compras) {
            gridPane.add(new Label("Evento: " + factura.getEvento().getNombre()), 0, row);
            gridPane.add(new Label("Localidad: " + factura.getLocalidad().getNombre()), 1, row);
            gridPane.add(new Label("Total: " + factura.getTotal()), 2, row);
            row++;
        }

        Scene scene = new Scene(gridPane, 600, 400);
        comprasStage.setScene(scene);
        comprasStage.setTitle("Lista de Compras");
        comprasStage.show();
    }
}


