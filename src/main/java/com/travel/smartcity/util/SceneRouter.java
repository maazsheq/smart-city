package com.travel.smartcity.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class SceneRouter {

  /**
   * Switches the current Stage to the given FXML view.
   *
   * @param stage    the primary Stage (you can get it via any node: node.getScene().getWindow())
   * @param fxmlPath path to the FXML file, e.g. "/fxml/LoginView.fxml"
   * @param title    title for the window
   * @throws Exception if the FXML cannot be loaded
   */
  public static void switchTo(Stage stage, String fxmlPath, String title) throws Exception {
    Parent root = FXMLLoader.load(SceneRouter.class.getResource(fxmlPath));
    stage.setTitle(title);
    stage.setScene(new Scene(root, 800, 600));
    stage.show();
  }

  private static Scene mainScene;

  public static void initialize(Parent root, Stage stage, String initialFxml, String title) throws Exception {
//    Parent root = FXMLLoader.load(Objects.requireNonNull(SceneRouter.class.getResource(initialFxml)));
    mainScene = new Scene(root, 800, 600);
    stage.setScene(mainScene);
    stage.setTitle(title);
    stage.show();
  }

//  public static void switchTo(String fxmlPath, String title) throws Exception {
//    Parent root = FXMLLoader.load(Objects.requireNonNull(SceneRouter.class.getResource(fxmlPath)));
//    mainScene.setRoot(root);
//    Stage stage = (Stage) mainScene.getWindow();
//    stage.setTitle(title);
//  }
}
