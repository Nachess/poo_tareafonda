package cl.dsy1102.fonda;

import cl.dsy1102.fonda.controller.PrincipalController;
import cl.dsy1102.fonda.dao.BebidaDao;
import cl.dsy1102.fonda.dao.JsonBebidaDao;
import cl.dsy1102.fonda.dao.PersistenciaException;
import cl.dsy1102.fonda.repository.BebidaRepository;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AppFX extends Application {

    private BebidaRepository repository;

    @Override
    public void init() {
        System.out.println("-> Iniciando AppFX (init)");

        BebidaDao dao = new JsonBebidaDao("data/bebidas.json");
        repository = new BebidaRepository(dao);

        try {
            repository.cargar();
            System.out.println("-> Datos cargados exitosamente desde JSON.");
        } catch (PersistenciaException e) {
            // Si la carga falla, informar con un Alert y continuar con la lista vacia.
            System.err.println("-> Error al cargar datos: " + e.getMessage());
            mostrarAlertaError("Error de Carga", "No se pudieron cargar los datos previos: " + e.getMessage());
        }
    }

    @Override
    public void start(Stage stage) {
        System.out.println("-> Ejecutando AppFX (start)");

        Navegador navegador = new Navegador(stage);

        PrincipalController controller = navegador.navegar("principal-view.fxml", "Fonda San Belarmino");
        if (controller != null) {
            controller.setRepository(repository);
            controller.setNavegador(navegador);
        }
    }

    @Override
    public void stop() {
        System.out.println("-> Cerrando AppFX (stop)");
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}