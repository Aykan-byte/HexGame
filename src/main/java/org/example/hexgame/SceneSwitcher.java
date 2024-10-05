package org.example.hexgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {
        //This method transitions to an FXML file and passes specific player names to that scene.
        protected static void switchToScene(String fxmlFile, String Playername1, String Playername2, ActionEvent event) throws IOException {

            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlFile));
            Parent root = loader.load();
            GameController controller = loader.getController();
            controller.setPlayerNames(Playername1, Playername2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        }
}
