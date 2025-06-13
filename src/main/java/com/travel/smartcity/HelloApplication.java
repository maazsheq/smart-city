package com.travel.smartcity;

import com.travel.smartcity.util.SceneRouter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
  @Override
  public void start(Stage stage) throws Exception {

    Parent root = FXMLLoader.load(
            getClass().getResource("/com/travel/smartcity/login-view.fxml")
    );
    SceneRouter.initialize(root, stage, "/com/travel/smartcity/login-view.fxml", "Smart City Traveler");
  }

  public static void main(String[] args) {
    launch();
  }
}