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
//    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
//    Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//    stage.setTitle("Hello!");
//    stage.setScene(scene);
//    stage.show();

//    Parent root = FXMLLoader.load(
//            Objects.requireNonNull(getClass().getResource("/com/travel/smartcity/login-view.fxml"))
//    );
//    stage.setTitle("Smart City Traveler");
//    stage.setScene(new Scene(root, 800, 600));
//    stage.show();
    Parent root = FXMLLoader.load(
            getClass().getResource("/com/travel/smartcity/login-view.fxml")
    );
    SceneRouter.initialize(root, stage, "/com/travel/smartcity/login-view.fxml", "Smart City Traveler");
  }

  public static void main(String[] args) {
    launch();
  }
}