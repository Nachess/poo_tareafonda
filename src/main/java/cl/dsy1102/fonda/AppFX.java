package cl.dsy1102.fonda;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Punto de entrada de la aplicacion grafica (EA2).
 *
 * Revisa el enunciado en README.md. Ejecuta con: mvn javafx:run
 */
public class AppFX extends Application {

    @Override
    public void init() {
        // TODO 1: trazar el ciclo de vida imprimiendo por consola.
    }

    @Override
    public void start(Stage stage) {
        // TODO 2: crear el DAO JSON y el repositorio de bebidas, y cargar los datos.
        //         Si la carga falla, informar con un Alert y continuar con la lista vacia.
        // TODO 3: cargar la vista principal (FXML), entregarle el repositorio a su
        //         controlador y mostrarla en el Stage recibido (no crear uno con new).
        stage.setTitle("Fonda San Belarmino");
        stage.show();
    }

    @Override
    public void stop() {
        // TODO 4: trazar el cierre de la aplicacion.
    }

    public static void main(String[] args) {
        launch(args);
    }
}
