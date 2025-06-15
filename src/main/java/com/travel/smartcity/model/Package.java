package com.travel.smartcity.model;

public class Package {
  private int id;
  private int placeId;
  private String details;
  private String price;

  public Package(int placeId, String details, String price) {
//    this.id = id;
    this.placeId = placeId;
    this.details = details;
    this.price = price;
  }

  public Package(int id, int placeId, String details, String price) {
    this.id = id;
    this.placeId = placeId;
    this.details = details;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getPlaceId() {
    return placeId;
  }

  public void setPlaceId(int placeId) {
    this.placeId = placeId;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }
}
