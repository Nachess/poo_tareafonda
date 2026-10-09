package cl.dsy1102.fonda.controller;

import cl.dsy1102.fonda.Navegador;
import cl.dsy1102.fonda.dao.PersistenciaException;
import cl.dsy1102.fonda.model.Bebida;
import cl.dsy1102.fonda.repository.BebidaRepository;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class PrincipalController {

    @FXML
    private TableView<Bebida> tablaBebidas;

    @FXML
    private TableColumn<Bebida, String> colNombre;

    @FXML
    private TableColumn<Bebida, Integer> colMililitros;

    @FXML
    private TableColumn<Bebida, Double> colPrecioBase;

    @FXML
    private TableColumn<Bebida, Double> colPrecioFinal;

    @FXML
    private TableColumn<Bebida, String> colTipo;

    private BebidaRepository repository;
    private Navegador navegador;

    public void setRepository(BebidaRepository repository) {
        this.repository = repository;
        if (repository != null) {
            tablaBebidas.setItems(repository.listar());
        }
    }

    public void setNavegador(Navegador navegador) {
        this.navegador = navegador;
    }

    @FXML
    public void initialize() {
        // Configuramos las columnas de la tabla de forma estándar
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colMililitros.setCellValueFactory(new PropertyValueFactory<>("mililitros"));
        colPrecioBase.setCellValueFactory(new PropertyValueFactory<>("precioBase"));
        colPrecioFinal.setCellValueFactory(new PropertyValueFactory<>("precioFinal"));

        // Muestra el tipo de bebida (Alcoholica / Sin Alcohol) usando obtenerTipo()
        colTipo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().obtenerTipo()));
    }

    @FXML
    private void handleAgregar() {
        if (navegador != null) {
            FormularioController controller = navegador.navegar("formulario-view.fxml", "Agregar Bebida");
            if (controller != null) {
                controller.setRepository(repository);
                controller.setNavegador(navegador);
            }
        }
    }

    @FXML
    private void handleEditar() {
        Bebida seleccionada = tablaBebidas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Debe seleccionar una bebida para editar.");
            return;
        }

        if (navegador != null) {
            FormularioController controller = navegador.navegar("formulario-view.fxml", "Editar Bebida");
            if (controller != null) {
                controller.setRepository(repository);
                controller.setNavegador(navegador);
                controller.cargarBebida(seleccionada);
            }
        }
    }

    @FXML
    private void handleEliminar() {
        Bebida seleccionada = tablaBebidas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Debe seleccionar una bebida para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de eliminar esta bebida?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> respuesta = confirmacion.showAndWait();

        if (respuesta.isPresent() && respuesta.get() == ButtonType.YES) {
            try {
                repository.eliminar(seleccionada);
            } catch (PersistenciaException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }

    @FXML
    private void handleVenta() {
        if (navegador != null) {
            VentaController controller = navegador.navegar("venta-view.fxml", "Módulo de Ventas");
            if (controller != null) {
                controller.setRepository(repository);
                controller.setNavegador(navegador);
            }
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