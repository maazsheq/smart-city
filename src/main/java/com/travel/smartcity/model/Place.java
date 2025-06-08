package com.travel.smartcity.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Place {
  private int id;
  private StringProperty name;
  private StringProperty type;
  private StringProperty address;

  public Place(int id, String name, String type, String address) {
    this.id = id;
    this.name = new SimpleStringProperty(name);
    this.type = new SimpleStringProperty(type);
    this.address = new SimpleStringProperty(address);
  }

  public StringProperty nameProperty() { return name; }
  public StringProperty typeProperty() { return type; }
  public StringProperty addressProperty() { return address; }

  // getters for id and raw String values
  public int getId() { return id; }
  public String getName() { return name.get(); }
  public String getType() { return type.get(); }
  public String getAddress() { return address.get(); }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public void setType(String type) {
    this.type.set(type);
  }

  public void setAddress(String address) {
    this.address.set(address);
  }
}