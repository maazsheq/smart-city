package com.travel.smartcity.model;

public class PlaceDetails extends Place {
  private String phoneNumber;
  private double latitude;
  private double longitude;
  private String website;
  private String openingHours; // JSON or formatted string

  public PlaceDetails(int id, String name, String type, String address,
                      String phoneNumber, double latitude, double longitude,
                      String website, String openingHours) {
    super(id, name, type, address);
    this.phoneNumber = phoneNumber;
    this.latitude = latitude;
    this.longitude = longitude;
    this.website = website;
    this.openingHours = openingHours;
  }

  // getters & setters...
}
