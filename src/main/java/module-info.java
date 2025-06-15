module com.travel.smartcity {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires net.synedra.validatorfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires eu.hansolo.tilesfx;
  requires com.almasb.fxgl.all;
  requires java.sql;
  requires annotations;
  requires java.desktop;

  opens com.travel.smartcity to javafx.fxml;
  opens com.travel.smartcity.controller to javafx.fxml;
  opens com.travel.smartcity.model to javafx.base;
  exports com.travel.smartcity;
}