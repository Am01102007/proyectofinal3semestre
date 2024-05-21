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
import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

public class CompraPane extends GridPane {

    private Stage currentStage;
    private Unieventos uniEventos;

    public CompraPane(Unieventos uniEventos, Evento evento, Stage primaryStage) {
        this.uniEventos = uniEventos;
        this.currentStage = primaryStage;

        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);
        setAlignment(Pos.CENTER);

        Label lblEvento = new Label("Evento: " + evento.getNombre());
        add(lblEvento, 0, 0, 2, 1);

        List<TextField> cantidadFields = new ArrayList<>();
        List<Localidad> localidades = evento.getLocalidades();
        for (int i = 0; i < localidades.size(); i++) {
            Localidad localidad = localidades.get(i);
            Label lblLocalidad = new Label(localidad.getNombre() + " - Precio: " + localidad.getPrecio() + " - Capacidad: " + localidad.getCapacidadMaxima());
            TextField txtCantidad = new TextField();
            txtCantidad.setPromptText("Cantidad");
            cantidadFields.add(txtCantidad);
            add(lblLocalidad, 0, i + 1);
            add(txtCantidad, 1, i + 1);
        }

        Label lblCodigoDescuento = new Label("Código de Descuento:");
        TextField txtCodigoDescuento = new TextField();
        add(lblCodigoDescuento, 0, localidades.size() + 1);
        add(txtCodigoDescuento, 1, localidades.size() + 1);

        Button btnVolver = new Button("Volver");
        btnVolver.setOnAction(e -> {
            currentStage.close();
            Stage stage = new Stage();
            PostLoginPage postLoginPage = new PostLoginPage(uniEventos, stage);
            Scene scene = new Scene(postLoginPage, 600, 400);
            stage.setScene(scene);
            stage.setTitle("Bienvenido");
            stage.show();
        });
        add(btnVolver, 0, localidades.size() + 2);

        Button btnComprar = new Button("Comprar");
        btnComprar.setOnAction(e -> {
            try {
                Cliente clienteActual = (Cliente) uniEventos.getUsuarioActual();
                for (int i = 0; i < localidades.size(); i++) {
                    Localidad localidad = localidades.get(i);
                    int cantidad = Integer.parseInt(cantidadFields.get(i).getText());
                    if (cantidad <= 0 || cantidad > localidad.getCapacidadMaxima()) {
                        showError("Cantidad no válida o insuficiente disponibilidad.");
                        return;
                    }
                    double subtotal = localidad.getPrecio() * cantidad;
                    double total = subtotal;
                    boolean usoCupon = false;

                    String codigoCupon = txtCodigoDescuento.getText().trim();
                    if (!codigoCupon.isEmpty() && (clienteActual.validarCodigoDescuento(codigoCupon) || uniEventos.validarCupon(codigoCupon))) {
                        double porcentajeDescuento = clienteActual.obtenerPorcentajeDescuento(codigoCupon);
                        if (porcentajeDescuento == 0.0) {
                            porcentajeDescuento = uniEventos.obtenerPorcentajeDescuentoCupon(codigoCupon);
                            uniEventos.eliminarCupon(codigoCupon);  // Eliminar el cupón de la lista de cupones válidos
                        } else {
                            clienteActual.eliminarCodigoDescuento(codigoCupon);  // Eliminar el cupón del cliente
                        }
                        total = subtotal * (1 - porcentajeDescuento);
                        usoCupon = true;
                    }

                    Factura factura = new Factura(subtotal, total, clienteActual, evento, localidad, usoCupon, UUID.randomUUID().toString(), null);
                    uniEventos.getFacturas().add(factura);
                    localidad.setCapacidadMaxima(localidad.getCapacidadMaxima() - cantidad);
                    showMessage("Compra realizada con éxito");
                    uniEventos.guardarDatos();
                    generarCodigoQR(factura);
                    enviarEmailCompra(clienteActual.getEmail(), factura);

                    // Enviar nuevo código de descuento del 10% después de la primera compra
                    if (!clienteActual.isPrimeraCompra()) {
                        String nuevoCodigoDescuento = CodeGenerator.generateCode();
                        clienteActual.agregarCodigoDescuento(nuevoCodigoDescuento, 0.10);
                        uniEventos.enviarCodigoDescuento(clienteActual.getEmail(), nuevoCodigoDescuento, 10);
                        clienteActual.setPrimeraCompra(true);
                    }
                }
            } catch (NumberFormatException ex) {
                showError("Ingrese una cantidad válida.");
            } catch (WriterException | IOException ex) {
                showError("Error al generar el código QR.");
            } catch (MessagingException ex) {
                showError("Error al enviar el correo de compra.");
            }
        });
        add(btnComprar, 1, localidades.size() + 2);
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

    private void generarCodigoQR(Factura factura) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(factura.getCodigo(), BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        try (FileOutputStream fos = new FileOutputStream("factura_" + factura.getCodigo() + ".png")) {
            fos.write(pngData);
        }
    }

    private void enviarEmailCompra(String email, Factura factura) throws MessagingException {
        String contenido = "Detalles de la compra:\n" +
                "Evento: " + factura.getEvento().getNombre() + "\n" +
                "Localidad: " + factura.getLocalidad().getNombre() + "\n" +
                "Subtotal: " + factura.getSubtotal() + "\n" +
                "Total: " + factura.getTotal() + "\n" +
                "Fecha: " + factura.getFecha() + "\n" +
                "Código de la factura: " + factura.getCodigo();

        uniEventos.getEmailService().sendEmail(email, "Detalles de su compra", contenido);
        System.out.println("Correo de compra enviado a " + email);
    }
}
