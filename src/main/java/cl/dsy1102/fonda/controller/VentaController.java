package cl.dsy1102.fonda.controller;

import cl.dsy1102.fonda.Navegador;
import cl.dsy1102.fonda.model.Bebida;
import cl.dsy1102.fonda.model.ConsumoResponsable;
import cl.dsy1102.fonda.repository.BebidaRepository;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VentaController {

    @FXML private ComboBox<Bebida> cbBebidas;
    @FXML private TextField txtEdad;
    @FXML private Label lblPrecioFinal;

    private BebidaRepository repository;
    private Navegador navegador;

    public void setRepository(BebidaRepository repository) {
        this.repository = repository;
        if (repository != null) {
            cbBebidas.setItems(repository.listar());
        }
    }

    public void setNavegador(Navegador navegador) {
        this.navegador = navegador;
    }

    @FXML
    public void initialize() {
        cbBebidas.setOnAction(e -> {
            Bebida seleccionada = cbBebidas.getValue();
            if (seleccionada != null) {
                lblPrecioFinal.setText(String.format("$%.2f", seleccionada.calcularPrecio()));
            } else {
                lblPrecioFinal.setText("$0.00");
            }
        });
    }

    @FXML
    private void handleProcesarVenta() {
        Bebida seleccionada = cbBebidas.getValue();
        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Debe seleccionar una bebida.");
            return;
        }

        try {
            int edad = Integer.parseInt(txtEdad.getText());

            // Validación verificando si implementa ConsumoResponsable o mediante casteo
            if (seleccionada instanceof ConsumoResponsable) {
                ConsumoResponsable responsable = (ConsumoResponsable) seleccionada;
                if (!responsable.esAptoParaConsumo(edad)) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Venta Denegada",
                            "No se puede vender bebidas alcohólicas a menores de 18 años.");
                    return;
                }
            }

            mostrarAlerta(Alert.AlertType.INFORMATION, "Venta Exitosa",
                    "Venta procesada con éxito.\nTotal a pagar: $" + seleccionada.calcularPrecio());

            if (navegador != null) {
                navegador.navegar("principal-view.fxml", "La Fonda San Belarmino");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Entrada", "Ingrese una edad válida en formato numérico.");
        }
    }

    @FXML
    private void handleVolver() {
        if (navegador != null) {
            navegador.navegar("principal-view.fxml", "La Fonda San Belarmino");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}