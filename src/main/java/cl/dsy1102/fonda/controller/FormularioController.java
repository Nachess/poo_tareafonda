package cl.dsy1102.fonda.controller;

import cl.dsy1102.fonda.Navegador;
import cl.dsy1102.fonda.dao.PersistenciaException;
import cl.dsy1102.fonda.model.Bebida;
import cl.dsy1102.fonda.model.BebidaAlcoholica;
import cl.dsy1102.fonda.model.BebidaSinAlcohol;
import cl.dsy1102.fonda.repository.BebidaRepository;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class FormularioController {

    @FXML private ComboBox<String> cbTipo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtVolumen;
    @FXML private TextField txtStock;
    @FXML private VBox boxAlcoholica;
    @FXML private TextField txtGrados;
    @FXML private CheckBox chkCertificada;
    @FXML private CheckBox chkVentaRestringida;

    @FXML private VBox boxSinAlcohol;
    @FXML private TextField txtAzucar;

    private BebidaRepository repository;
    private Navegador navegador;
    private Bebida bebidaEdicion;

    public void setRepository(BebidaRepository repository) {
        this.repository = repository;
    }

    public void setNavegador(Navegador navegador) {
        this.navegador = navegador;
    }

    @FXML
    public void initialize() {
        cbTipo.getItems().setAll("Alcohólica", "Sin alcohol");
        cbTipo.setValue("Alcohólica");

        cbTipo.setOnAction(e -> actualizarVisibilidadCampos());
        actualizarVisibilidadCampos();
    }

    private void actualizarVisibilidadCampos() {
        boolean esAlcoholica = "Alcohólica".equals(cbTipo.getValue());
        if (boxAlcoholica != null) boxAlcoholica.setVisible(esAlcoholica);
        if (boxAlcoholica != null) boxAlcoholica.setManaged(esAlcoholica);

        if (boxSinAlcohol != null) boxSinAlcohol.setVisible(!esAlcoholica);
        if (boxSinAlcohol != null) boxSinAlcohol.setManaged(!esAlcoholica);
    }

    public void cargarBebida(Bebida bebida) {
        this.bebidaEdicion = bebida;
        cbTipo.setDisable(true);

        txtNombre.setText(bebida.getNombre());
        txtVolumen.setText(String.valueOf(bebida.getVolumenML()));
        txtStock.setText(String.valueOf(bebida.getStock()));

        if (bebida instanceof BebidaAlcoholica alc) {
            cbTipo.setValue("Alcohólica");
            txtGrados.setText(String.valueOf(alc.getGradosAlcohol()));
            chkCertificada.setSelected(alc.isCertificada());
            chkVentaRestringida.setSelected(alc.isVentaRestringida());

            // Si la venta ya está restringida, no se puede desmarcar
            if (alc.isVentaRestringida()) {
                chkVentaRestringida.setDisable(true);
            }
        } else if (bebida instanceof BebidaSinAlcohol sinAlc) {
            cbTipo.setValue("Sin alcohol");
            txtAzucar.setText(String.valueOf(sinAlc.getAzucarPorLitro()));
        }
        actualizarVisibilidadCampos();
    }

    @FXML
    private void handleGuardar() {
        try {
            String nombre = txtNombre.getText();
            int ml = Integer.parseInt(txtVolumen.getText());
            int stock = Integer.parseInt(txtStock.getText());

            Bebida bebidaResultado;

            if ("Alcohólica".equals(cbTipo.getValue())) {
                double grados = Double.parseDouble(txtGrados.getText());
                boolean certificada = chkCertificada.isSelected();

                BebidaAlcoholica alc = new BebidaAlcoholica(nombre, ml, stock, grados, certificada);
                if (chkVentaRestringida.isSelected()) {
                    alc.restringirVenta();
                }
                bebidaResultado = alc;
            } else {
                int azucar = Integer.parseInt(txtAzucar.getText());
                bebidaResultado = new BebidaSinAlcohol(nombre, ml, stock, azucar);
            }

            if (bebidaEdicion == null) {
                repository.agregar(bebidaResultado);
            } else {
                repository.actualizar(bebidaEdicion, bebidaResultado);
            }

            if (navegador != null) {
                navegador.navegar("principal-view.fxml", "Fonda San Belarmino");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Formato", "Ingrese valores numéricos válidos en los campos de volumen, stock, grados o azúcar.");
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error de Validación", e.getMessage());
        } catch (PersistenciaException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Persistencia", e.getMessage());
        }
    }

    @FXML
    private void handleVolver() {
        if (navegador != null) {
            navegador.navegar("principal-view.fxml", "Fonda San Belarmino");
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