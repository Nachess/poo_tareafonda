package cl.dsy1102.fonda;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navegador {

    private Stage stagePrincipal;

    public Navegador() {
    }

    public Navegador(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }

    public void setStage(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }

    public <T> T navegar(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cl/dsy1102/fonda/view/" + fxml));
            Parent root = loader.load();

            if (stagePrincipal != null) {
                stagePrincipal.setTitle(titulo);
                stagePrincipal.setScene(new Scene(root));
                stagePrincipal.show();
            }

            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}