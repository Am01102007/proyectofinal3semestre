package co.edu.uniquindio.poo;

import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Map;

public class ViewChartsPane extends VBox {

    public ViewChartsPane(Unieventos uniEventos, Stage stage) {
        setPadding(new Insets(10));
        setSpacing(10);

        Map<String, Map<String, Object>> estadisticas = uniEventos.obtenerEstadisticasEventos();

        for (String nombreEvento : estadisticas.keySet()) {
            Map<String, Object> datosEvento = estadisticas.get(nombreEvento);
            Map<String, Double> porcentajeVendidoPorLocalidad = (Map<String, Double>) datosEvento.get("porcentajeVendidoPorLocalidad");
            double totalGanado = (double) datosEvento.get("totalGanado");

            // Gráfico de barras para el porcentaje vendido por localidad
            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Localidad");

            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Porcentaje Vendido");

            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Porcentaje Vendido por Localidad - " + nombreEvento);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(nombreEvento);

            for (Map.Entry<String, Double> entry : porcentajeVendidoPorLocalidad.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            barChart.getData().add(series);
            getChildren().add(barChart);

            // Gráfico de pastel para el total ganado
            PieChart pieChart = new PieChart();
            pieChart.setTitle("Total Ganado - " + nombreEvento);
            pieChart.getData().add(new PieChart.Data("Total Ganado", totalGanado));

            getChildren().add(pieChart);
        }
    }
}
